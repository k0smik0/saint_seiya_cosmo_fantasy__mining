package net.iubris.optimus_saint.crawler.adapters.saints;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonValue;

import net.iubris.optimus_saint.common.StringUtils;
import net.iubris.optimus_saint.crawler.model.Localization;
import net.iubris.optimus_saint.crawler.model.LocalizationUtils;
import net.iubris.optimus_saint.crawler.model.saints.stats.Category;
import net.iubris.optimus_saint.crawler.model.saints.stats.StatValue;
import net.iubris.optimus_saint.crawler.model.saints.stats.StatsGroup;
import net.iubris.optimus_saint.crawler.model.saints.stats.literal.ActiveTime;
import net.iubris.optimus_saint.crawler.model.saints.stats.literal.ClothKind;
import net.iubris.optimus_saint.crawler.model.saints.stats.literal.LiteralStat;
import net.iubris.optimus_saint.crawler.model.saints.stats.literal.PromotionClass;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Accuracy;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Aura;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.AuraGrowthRate;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.CosmoCostReduction;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.CosmoRecovery;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Evasion;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.FuryAttack;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.FuryCritical;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.FuryResistance;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.HPDrain;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.HPRecovery;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Level;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.MaxHP;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.NullFuryResistance;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.NullPhysicalDefense;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.NumericalStat;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.PhysicalAttack;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.PhysicalCritical;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.PhysicalDefense;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Power;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Rarity;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.SilenceResistance;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.TechnicalGrowthRate;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Technique;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.Vitality;
import net.iubris.optimus_saint.crawler.model.saints.stats.numerical.VitalityGrowthRate;

//import static net.iubris.optimus_saint.model.saint.data.SaintData.Saints.LocalizationDefault;

public class StatsArrayAdapter extends AbstractArrayAdapter<StatsGroup> {
	

	private static final String NAME_NODE = "name";
	
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
		initOtherStatsClassesToHandlerMap();
		initNumericalStatsClassesToHandlerMap();
	}
	
	@Override
	public StatsGroup adaptFromJson(JsonArray statsJsonArray) {
		
		StatsGroup stats = new StatsGroup();
		
//		JsonArray jsonArray = jo.asJsonArray();
		for (JsonValue jsonValue : statsJsonArray) {
//			LocalizedData localizedData = jsonb.fromJson(jsonValue.asJsonObject().toString(), LocalizedData.class);
			
			
			JsonObject jsonObject = jsonValue.asJsonObject();
			String nameValue = LocalizationUtils.getLocalizedValue( jsonObject.getJsonObject(NAME_NODE) ); 
			
			Set<String> keySet = jsonObject.keySet();
			keySet.stream().forEach(v->{
				/*
				 * handling rarity, category, LiteralStats (Class Pr., Cloth kind, Active Time -- Lane and Type are skipped)
				 */
				Action action = statsToHandlerMap.get(nameValue);
				if (action!=null) {
					action.adapt(jsonObject, nameValue, stats);
				}
				/*
				 * handling numericStats:
				 * 	Level, Power, Vitality Growth Rate, Aura Growth Rate, Tech. Growth Rate, Vitality, Aura, Technique, Max HP, 
				 * 	Phys. Attack, Fury Attack, Phys. Defense, Fury Resistance, Phys. Critical, Fury Critical, Null Phys. Defense, 
				 * 	Null Fury Resistance, HP Drain, Accuracy, Evasion, HP Recovery, Cosmo Recovery, Cosmo Cost Reduction, 
				 * 	Silence Resistance
				 */
				else {
					Class<? extends NumericalStat> extendingNumericalStatClass = numericalClassesMap.get(nameValue);
					if (extendingNumericalStatClass!=null) {
						NumericalStat numericalStat = parseAnExtendingNumericalStat(jsonObject, nameValue, extendingNumericalStatClass);
						parseAnExtendingNumericalStat(jsonObject, nameValue, extendingNumericalStatClass);
						
						String fieldName = StringUtils.toCamelCase( extendingNumericalStatClass.getSimpleName() );
						try {
							stats.getClass().getField(fieldName).set(stats, numericalStat);
						} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
							e.printStackTrace();
						}
					}
				}
				
				
			});
			
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
			float min = jsonObject.getJsonNumber(MIN).bigDecimalValue().floatValue();
			float max = jsonObject.getJsonNumber(MAX).bigDecimalValue().floatValue();
			
			esInstance = eSClass.newInstance();
			esInstance.min = min;
			esInstance.max = max;
		} catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException  e) {
			e.printStackTrace();
		}
		return esInstance;
	}
	
	private void initOtherStatsClassesToHandlerMap() {
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
//			eLS eLS = handleLiteralStat(jsonObject, nameValue, extendingLiteralStatClass);
//			stats.getClass().getField(statsField).set(stats, eLS);
			
			eLS extendingLiteralStat = extendingLiteralStatClass.newInstance();
			
			Localization localizationE = LocalizationUtils.getLocalization();
			String minAsString = extendingLiteralStat.findByInternalEnum(jsonObject, MIN).getLocalized(localizationE);
			StatValue min = new StatValue();
			min.value = minAsString;
			extendingLiteralStat.min = min;
			
			String maxAsString = extendingLiteralStat.findByInternalEnum(jsonObject, MAX).getLocalized(localizationE);
			StatValue max = new StatValue();
			max.value = maxAsString;
			extendingLiteralStat.max = max;
			
			stats.getClass().getField(statsField).set(stats, extendingLiteralStat);
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
	/*private static <LS extends LiteralStat> LS handleLiteralStat(JsonObject jsonObject, String value, 
			Class<LS> extendingLiteralStatClass) throws InstantiationException, IllegalAccessException {
		LS literalStat = extendingLiteralStatClass.newInstance();
		
		Localization localizationE = LocalizationUtils.getLocalization();
		String min = literalStat.findByInternalEnum(jsonObject, MIN).getLocalized(localizationE);
		literalStat.min = min;
		
		String max = literalStat.findByInternalEnum(jsonObject, MAX).getLocalized(localizationE);
		literalStat.max = max;
		
		return literalStat;
	}*/
	
	private void initNumericalStatsClassesToHandlerMap() {
		numericalClassesMap.put("Level", Level.class);
		numericalClassesMap.put("Power", Power.class);
		numericalClassesMap.put("Vitality Growth Rate", VitalityGrowthRate.class);
		numericalClassesMap.put("Aura Growth Rate", AuraGrowthRate.class);
		numericalClassesMap.put("Tech. Growth Rate", TechnicalGrowthRate.class);
		numericalClassesMap.put("Vitality", Vitality.class);
		numericalClassesMap.put("Aura", Aura.class);
		numericalClassesMap.put("Technique", Technique.class);
		numericalClassesMap.put("Max HP", MaxHP.class);
		numericalClassesMap.put("Phys. Attack", PhysicalAttack.class);
		numericalClassesMap.put("Fury Attack", FuryAttack.class);
		numericalClassesMap.put("Phys. Defense", PhysicalDefense.class);
		numericalClassesMap.put("Fury Resistance", FuryResistance.class);
		numericalClassesMap.put("Phys. Critical", PhysicalCritical.class);
		numericalClassesMap.put("Fury Critical", FuryCritical.class);
		numericalClassesMap.put("Null Phys. Defense", NullPhysicalDefense.class);
		numericalClassesMap.put("Null Fury Resistance", NullFuryResistance.class);
		numericalClassesMap.put("HP Drain", HPDrain.class);
		numericalClassesMap.put("Accuracy", Accuracy.class);
		numericalClassesMap.put("Evasion", Evasion.class);
		numericalClassesMap.put("HP Recovery", HPRecovery.class);
		numericalClassesMap.put("Cosmo Recovery", CosmoRecovery.class);
		numericalClassesMap.put("Cosmo Cost Reduction", CosmoCostReduction.class);
		numericalClassesMap.put("Silence Resistance", SilenceResistance.class);
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
	
	/*private static String firstCharToLowerCase(String string) {
		char c[] = string.toCharArray();
		c[0] += 32;
		String s = new String(c);
		return s;
	}*/

}
