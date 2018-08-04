package net.iubris.optimus_saint.model.saint.data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.bind.adapter.JsonbAdapter;

import net.iubris.optimus_saint.utils.StringUtils;

import static net.iubris.optimus_saint.model.saint.data.SaintData.Saints.LocalizationDefault;

public class StatsArrayAdapter implements JsonbAdapter<StatsGroup, JsonArray> {
	

	private static final String ROOT_NODE = "name";
	
	private static final String MIN = "min";
	private static final String MAX = "max";
	
	private static final String TYPE_GRAPHIC__RARITY = "Rarity";
	private static final String TYPE_LITERAL__CLASS_PROMOTION = "Class Pr.";
	private static final String TYPE_LITERAL__CLOTH_KIND = "Cloth kind";
	private static final String TYPE_LITERAL__ACTIVE_TIME = "Active time";
	private static final String TYPE_NUMERICAL_COMPLEX__CATEGORY = "Category";
	
	private Map<String,Action> statsToHandlerMap = new HashMap<>();
	private Map<String,Class<? extends NumericalStat>> numericalClassesMap = new HashMap<>();
	
	public StatsArrayAdapter() {		
		initActionsMap();
		initNumericalClassMap();
	}
	
	@Override
	public StatsGroup adaptFromJson(JsonArray jsonArray) {
		
		StatsGroup stats = new StatsGroup();
		
//		JsonArray jsonArray = jo.asJsonArray();
		for (JsonValue jsonValue : jsonArray) {
//			LocalizedData localizedData = jsonb.fromJson(jsonValue.asJsonObject().toString(), LocalizedData.class);
			
			JsonObject jsonObject = jsonValue.asJsonObject();
			String nameValue = jsonObject.getJsonObject(ROOT_NODE).getString(LocalizationDefault);
			
			/*
			 * if (nameValue.equals(Rarity))
			 * else if nameValue.equals(Category)
			 * else if
			 * 	 literal zone
			 *		 Class Pr., Cloth kind, Active Time -- Lane and Type are skipped
			 *
			 */
			Action action = statsToHandlerMap.get(nameValue);
			if (action!=null) {
				action.adapt(jsonObject, nameValue, stats);
			}
			
			// numeric zone:
			/*
			 Level, Power, Vitality Growth Rate, Aura Growth Rate, Tech. Growth Rate, Vitality, Aura, Technique, Max HP, Phys. Attack, 
			 Fury Attack, Phys. Defense, Fury Resistance, Phys. Critical, Fury Critical, Null Phys. Defense, Null Fury Resistance, 
			 HP Drain, Accuracy, Evasion, HP Recovery, Cosmo Recovery, Cosmo Cost Reduction, Silence Resistance
			*/
			else {
				Class<? extends NumericalStat> extendingNumericalStatClass = numericalClassesMap.get(nameValue);
				if (extendingNumericalStatClass!=null) {
					NumericalStat numericalStat = parseAnExtendingNumericalStat(jsonObject, nameValue, extendingNumericalStatClass);
					parseAnExtendingNumericalStat(jsonObject, nameValue, extendingNumericalStatClass);
					
					String fieldName = firstCharToLowerCase( extendingNumericalStatClass.getSimpleName() );
					try {
						stats.getClass().getField(fieldName).set(stats, numericalStat);
					} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
						e.printStackTrace();
					}
				}
			}
			
			// old, specific
			/*if (nameValue.equals("Level")) {
				Level level = new Level();
				level.name.EN = nameValue;
				level.min = Float.parseFloat(jsonObject.getString("min"));
				level.max = Float.parseFloat(jsonObject.getString("max"));
			}*/
		}
		
		return stats;
	}
	
	
//	@SuppressWarnings("unchecked")
	private static <eNS extends NumericalStat> eNS parseAnExtendingNumericalStat(JsonObject jsonObject, String nameValue, Class<eNS> eSClass) {
		eNS esInstance = null;
		try {
			esInstance = (eNS)eSClass.newInstance();
			esInstance.min = jsonObject.getJsonNumber(MIN).bigDecimalValue().floatValue();
			esInstance.max = jsonObject.getJsonNumber(MAX).bigDecimalValue().floatValue();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return esInstance;
	}
	
	@Override
	public JsonArray adaptToJson(StatsGroup statsGroup) throws Exception {
		System.out.println("adaptToJson: not implemented");
		return null;
	}
	
	private void initActionsMap() {
		statsToHandlerMap.put(TYPE_GRAPHIC__RARITY, new Action() {
			@Override
			public void adapt(JsonObject jsonObject, String nameValue, StatsGroup stats) {
				Rarity rarity = new Rarity();
				rarity.min = Rarity.starsToNumber(jsonObject.getString(MIN));
				rarity.max = Rarity.starsToNumber(jsonObject.getString(MAX));
				stats.rarity = rarity;
			}
		});
		
		statsToHandlerMap.put(TYPE_NUMERICAL_COMPLEX__CATEGORY, (jsonObject, nameValue, stats) -> {
			Category category = new Category();
			category.min = jsonObject.getJsonArray(MIN).getValuesAs(JsonNumber.class).stream().map(f-> f.intValue() ).collect(Collectors.toList());
			category.max = jsonObject.getJsonArray(MAX).getValuesAs(JsonNumber.class).stream().map(f-> f.intValue() ).collect(Collectors.toList());
			stats.category = category;
		});
		
		statsToHandlerMap.put(TYPE_LITERAL__CLASS_PROMOTION, (jsonObject, nameValue, stats) -> {
			handleGenericalLiteralStat(jsonObject, nameValue, PromotionClass.class, stats, "promotionClass");
		});
		statsToHandlerMap.put(TYPE_LITERAL__CLOTH_KIND, (jsonObject, nameValue, stats) -> {
			handleGenericalLiteralStat(jsonObject, nameValue, ClothKind.class, stats, "clothKind");
		});
		statsToHandlerMap.put(TYPE_LITERAL__ACTIVE_TIME, (jsonObject, nameValue, stats) -> {
			handleGenericalLiteralStat(jsonObject, nameValue, ActiveTime.class, stats, "activeTime");
		});
	}
	private static interface Action {
		void adapt(JsonObject jsonObject, String nameValue, StatsGroup stats);
	}
	private static <eLS extends LiteralStat> void handleGenericalLiteralStat(JsonObject jsonObject, String nameValue, 
			Class<eLS> extendingLiteralStatClass, StatsGroup stats, String statsField) {
		try {
			eLS eLS = handleLiteralStat(jsonObject, nameValue, extendingLiteralStatClass, LocalizationDefault);
			stats.getClass().getField(statsField).set(stats, eLS);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	private static <LS extends LiteralStat> LS handleLiteralStat(JsonObject jsonObject, String value, 
			Class<LS> extendingLiteralStatClass, String localization) throws InstantiationException, IllegalAccessException {
		LS literalStat = extendingLiteralStatClass.newInstance();
		
		Localization localizationE = Localization.valueOf(localization);
		String min = literalStat.findByInternalEnum(jsonObject, MIN, localization).getLocalized(localizationE);
		literalStat.min.value = min;
		
		String max = literalStat.findByInternalEnum(jsonObject, MAX, localization).getLocalized(localizationE);
		literalStat.max.value = max;
		
		return literalStat;
	}
	
	private void initNumericalClassMap() {
		numericalClassesMap.put("Level", Level.class);
		numericalClassesMap.put("Power", Power.class);
		numericalClassesMap.put("Vitality Growth Rate", Vitality_Growth_Rate.class);
		numericalClassesMap.put("Aura Growth Rate", Aura_Growth_Rate.class);
		numericalClassesMap.put("Tech. Growth Rate", Technical_Growth_Rate.class);
		numericalClassesMap.put("Vitality", Vitality.class);
		numericalClassesMap.put("Aura", Aura.class);
		numericalClassesMap.put("Technique", Technique.class);
		numericalClassesMap.put("Max HP", Max_HP.class);
		numericalClassesMap.put("Phys. Attack", Physical_Attack.class);
		numericalClassesMap.put("Fury Attack", Fury_Attack.class);
		numericalClassesMap.put("Phys. Defense", Physical_Defense.class);
		numericalClassesMap.put("Fury Resistance", Fury_Resistance.class);
		numericalClassesMap.put("Phys. Critical", Physical_Critical.class);
		numericalClassesMap.put("Fury Critical", Fury_Critical.class);
		numericalClassesMap.put("Null Phys. Defense", Null_Physical_Defense.class);
		numericalClassesMap.put("Null Fury Resistance", Null_Fury_Resistance.class);
		numericalClassesMap.put("HP Drain", HP_Drain.class);
		numericalClassesMap.put("Accuracy", Accuracy.class);
		numericalClassesMap.put("Evasion", Evasion.class);
		numericalClassesMap.put("HP Recovery", HP_Recovery.class);
		numericalClassesMap.put("Cosmo Recovery", Cosmo_Recovery.class);
		numericalClassesMap.put("Cosmo Cost Reduction", Cosmo_Cost_Reduction.class);
		numericalClassesMap.put("Silence Resistance", Silence_Resistance.class);
	}

	

/*	@Override
	public Stat adaptFromJson(JsonObject arg0) throws Exception {
		Stat c = new Stat();
      c.name = arg0.getJsonObject("name");
      c.min = arg0.getString("min").split("&").length;
      c.max = arg0.getString("max").split("&").length;
      return c;
	}

	@Override
	public JsonObject adaptToJson(Stat arg0) throws Exception {
		return Json.createObjectBuilder()
            .add("name", arg0.name)
            .add("min", arg0.min)
            .add("max", arg0.max)
            .build();
	}*/
	
	private static String firstCharToLowerCase(String string) {
		char c[] = string.toCharArray();
		c[0] += 32;
		String s = new String(c);
		return s;
	}

}
