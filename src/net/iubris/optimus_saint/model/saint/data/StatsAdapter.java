package net.iubris.optimus_saint.model.saint.data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.adapter.JsonbAdapter;

public class StatsAdapter implements JsonbAdapter<Stats, JsonObject> {
	
	Jsonb jsonb = JsonbBuilder.create();
	
	@Override
	public Stats adaptFromJson(JsonObject jo) throws Exception {
		
		Stats stats = new Stats();
		
		JsonArray jsonArray = jo.asJsonArray();
		for (JsonValue jsonValue : jsonArray) {
//			LocalizedData localizedData = jsonb.fromJson(jsonValue.asJsonObject().toString(), LocalizedData.class);
			
			JsonObject jsonObject = jsonValue.asJsonObject();
			String nameValue = jsonObject.getJsonObject("name").getString("EN");
			
			if (nameValue.equals("Rarity")) {
				Rarity rarity = new Rarity();
				rarity.name.value = nameValue;				
				rarity.min = Rarity.starsToNumber(jsonObject.getString("min"));
				rarity.max = Rarity.starsToNumber(jsonObject.getString("max"));
				
				stats.rarity = rarity;
			}
			// literal zone
			// Class Pr., Lane, Type, Cloth kind, Active Time
			
			
			else if (nameValue.equals("Class Pr.")) {
				
				Map<String,Class<? extends Stat>> literalStat = new HashMap<>();
				literalStat.put("Class Pr.",PromotionClass.class);
				literalStat.put("Lane", Lane.class);
				
				"Type", "Cloth kind", "Active Time"}));
				
				PromotionClass promotionClass = new PromotionClass();
				promotionClass.name.value = nameValue;
				
				Localization localizationEN = Localization.valueOf("EN");
				String min = PromotionClassEnum.valueOf(jsonObject.getJsonObject("min").getString("EN").toUpperCase()).getLocalized(localizationEN);
				promotionClass.min.value = min;
				
				String max = PromotionClassEnum.valueOf(jsonObject.getJsonObject("max").getString("EN").toUpperCase()).getLocalized(localizationEN);
				promotionClass.max.value = max;
				
				stats.promotionClass = promotionClass;
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
			
			// Category
			
			// old, specific
			/*if (nameValue.equals("Level")) {
				Level level = new Level();
				level.name.EN = nameValue;
				level.min = Float.parseFloat(jsonObject.getString("min"));
				level.max = Float.parseFloat(jsonObject.getString("max"));
			}*/
			
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private static <eS extends NumericStat> eS parseNumericStat(JsonObject jsonObject, String nameValue, Class<?> eSClass) {
		eS esInstance = null;
		try {
			esInstance = (eS)eSClass.newInstance();
			esInstance.name.value = nameValue;
			esInstance.min = Float.parseFloat(jsonObject.getString("min"));
			esInstance.max = Float.parseFloat(jsonObject.getString("max"));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return esInstance;
	}
	
	@Override
	public JsonObject adaptToJson(Stats st) throws Exception {
		return null;
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
