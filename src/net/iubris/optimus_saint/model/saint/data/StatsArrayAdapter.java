package net.iubris.optimus_saint.model.saint.data;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.bind.adapter.JsonbAdapter;

import static net.iubris.optimus_saint.model.saint.data.SaintData.Saints.LocalizationDefault;

public class StatsArrayAdapter implements JsonbAdapter<StatsGroup, JsonArray> {
	

	private static final String ROOT_NODE = "name";
	
	private static final String MIN = "min";
	private static final String MAX = "max";
	
	private static final String TYPE_GRAPHIC__RARITY = "Rarity";
	private static final String TYPE_LITERAL__CLASS_PROMOTION = "Class Pr.";
	private static final String TYPE_LITERAL__CLOTH_KIND = "Cloth kind";
	private static final String TYPE_LITERAL__ACTIVE_TIME = "Active time";
	
	private Map<String,Action> actionsMap = new HashMap<>();
	
	private interface Action {
		void adapt(JsonObject jsonObject, String nameValue, StatsGroup stats);
	}
	
	public StatsArrayAdapter() {		
		initActionsMap();
	}
	
	
	@Override
	public StatsGroup adaptFromJson(JsonArray jsonArray) throws Exception {
		
		StatsGroup stats = new StatsGroup();
		
//		JsonArray jsonArray = jo.asJsonArray();
		for (JsonValue jsonValue : jsonArray) {
//			LocalizedData localizedData = jsonb.fromJson(jsonValue.asJsonObject().toString(), LocalizedData.class);
			
			JsonObject jsonObject = jsonValue.asJsonObject();
			String nameValue = jsonObject.getJsonObject(ROOT_NODE).getString(LocalizationDefault);
			
			Action action = actionsMap.get(nameValue);
			
			/*
			 * if (nameValue.equals(TYPE_GRAPHIC__RARITY))
			 * else if
			 * 	 literal zone
			 *		 Class Pr., Cloth kind, Active Time -- Lane and Type are skipped
			 *
			 */
			if (action!=null) {
				action.adapt(jsonObject, nameValue, stats);
			}
			// Category
			else if (nameValue.equals("Category")) {
				
			}
			
			// numeric zone:
			// Level, Power, 
			// Vitality Growth Rate, Aura Growth Rate, Tech. Growth Rate,
			// Vitality, Aura, Technique, Max HP, Phys. Attack, Fury Attack, Phys. Defense,
			// Fury Resistance, Phys. Critical, Fury Critical, Null Phys. Defense, Null Fury Resistance,
			// HP Drain, Accuracy, Evasion, HP Recovery, Cosmo Recovery, Cosmo Cost Reduction
			// Silence Resistance
			else {
				String className = nameValue.replace(".", "").replace(" ", "_");
				NumericStat parseNumericStat = parseNumericStat(jsonObject, nameValue, Class.forName(className));
				stats.getClass().getField(className.toLowerCase()).set(stats, parseNumericStat);
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
	private static <LS extends LiteralStat> LS handleLiteralStat(JsonObject jsonObject, String value, 
			Class<LS> extendingLiteralStatClass, String localization) throws InstantiationException, IllegalAccessException {
		LS literalStat = extendingLiteralStatClass.newInstance();
//		literalStat.name.value = value;
		
		Localization localizationE = Localization.valueOf(localization);
		String min = literalStat.findByInternalEnum(jsonObject, MIN, localization).getLocalized(localizationE);
		literalStat.min.value = min;
		
		String max = literalStat.findByInternalEnum(jsonObject, MAX, localization).getLocalized(localizationE);
		literalStat.max.value = max;
		
		return literalStat;
	}
	
	@SuppressWarnings("unchecked")
	private static <eS extends NumericStat> eS parseNumericStat(JsonObject jsonObject, String nameValue, Class<?> eSClass) {
		eS esInstance = null;
		try {
			esInstance = (eS)eSClass.newInstance();
//			esInstance.name.value = nameValue;
			esInstance.min = Float.parseFloat(jsonObject.getString("min"));
			esInstance.max = Float.parseFloat(jsonObject.getString("max"));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return esInstance;
	}
	
	@Override
	public JsonArray adaptToJson(StatsGroup st) throws Exception {
		System.out.println("adaptToJson: not implemented");
		return null;
	}
	
	private void initActionsMap() {
		actionsMap.put(TYPE_GRAPHIC__RARITY, new Action() {
			@Override
			public void adapt(JsonObject jsonObject, String nameValue, StatsGroup stats) {
				Rarity rarity = new Rarity();
//				rarity.name.value = nameValue;				
				rarity.min = Rarity.starsToNumber(jsonObject.getString(MIN));
				rarity.max = Rarity.starsToNumber(jsonObject.getString(MAX));
				stats.rarity = rarity;
			}
		});
		
		actionsMap.put(TYPE_LITERAL__CLASS_PROMOTION, (jsonObject, nameValue, stats) -> {
			try {
				PromotionClass promotionClass = handleLiteralStat(jsonObject, nameValue, PromotionClass.class, LocalizationDefault);
				stats.promotionClass = promotionClass;
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		/*actionsMap.put(TYPE_LITERAL__LANE, (jsonObject, nameValue, stats) -> {
			try {
				Lane ls = handleLiteralStat(jsonObject, nameValue, Lane.class, LocalizationEN);
				stats.lane = ls;
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		actionsMap.put(TYPE_LITERAL__TYPE, (jsonObject, nameValue, stats) -> {
			try {
				Type ls = handleLiteralStat(jsonObject, nameValue, Type.class, LocalizationEN);
				stats.type = ls;
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});*/
		actionsMap.put(TYPE_LITERAL__CLOTH_KIND, (jsonObject, nameValue, stats) -> {
			try {
				ClothKind ls = handleLiteralStat(jsonObject, nameValue, ClothKind.class, LocalizationDefault);
				stats.clothKind = ls;
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
		actionsMap.put(TYPE_LITERAL__ACTIVE_TIME, (jsonObject, nameValue, stats) -> {
			try {
				ActiveTime ls = handleLiteralStat(jsonObject, nameValue, ActiveTime.class, LocalizationDefault);
				stats.activeTime = ls;
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		});
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

}
