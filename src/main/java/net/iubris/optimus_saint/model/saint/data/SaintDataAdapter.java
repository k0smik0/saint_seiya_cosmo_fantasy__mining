package net.iubris.optimus_saint.model.saint.data;

import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import net.iubris.optimus_saint.model.saint.data.stats.StatsArrayAdapter;
import net.iubris.optimus_saint.model.saint.data.stats.StatsGroup;
import net.iubris.optimus_saint.model.saint.data.value.description.Description;
import net.iubris.optimus_saint.model.saint.data.value.description.DescriptionAdapter;
import net.iubris.optimus_saint.model.saint.data.value.name.Name;
import net.iubris.optimus_saint.model.saint.data.value.name.NameAdapter;
import net.iubris.optimus_saint.model.saint.data.value.type.Type;
import net.iubris.optimus_saint.model.saint.data.value.type.TypeAdapter;

public class SaintDataAdapter implements JsonbAdapter<SaintData, JsonObject> {
	
	private static final NameAdapter nameAdapter = new NameAdapter();
	private static final DescriptionAdapter descriptionAdapter = new DescriptionAdapter();
	
	@Override
	public SaintData adaptFromJson(JsonObject saintAsJsonObject) throws Exception {
//		JsonArray asJsonArray = jsonObject.asJsonArray();
//		
//		asJsonArray.stream().map(eJV->{
			
//			JsonObject saintAsJsonObject = eJV.asJsonObject();
			
//			boolean incomplete = saintAsJsonObject.getBoolean("incomplete");
			
			int unitId = saintAsJsonObject.getInt("unitId");
			String fyi_name = saintAsJsonObject.getString("fyi_name");
			
			List<String> goodWith = saintAsJsonObject.getJsonArray("goodwith")
					.stream().map(jv->jv.toString()).collect(Collectors.toList());
			
			List<String> strongAgainst = saintAsJsonObject.getJsonArray("strongagainst")
					.stream().map(jv->jv.toString()).collect(Collectors.toList());
			
			JsonObject jsonObjectTiers = saintAsJsonObject.getJsonObject("tiers");
			
			String id = saintAsJsonObject.getString("id");
			
			SaintData saintData = new SaintData();
			saintData.unitId = unitId;
			saintData.fyi_name = fyi_name;
			saintData.goodWith = goodWith;
			saintData.strongAgainst = strongAgainst;
			saintData.id = id;
//			saintData.tiers = tiers;
			
			try {
				Name name = nameAdapter.adaptFromJson(saintAsJsonObject.getJsonObject("name"));
				saintData.name = name;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				Description description = descriptionAdapter.adaptFromJson(saintAsJsonObject.getJsonObject("description"));
				saintData.description = description;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				Type type = new TypeAdapter().adaptFromJson(saintAsJsonObject.getJsonObject("type"));
				saintData.type = type;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			try {
				StatsGroup sg = new StatsArrayAdapter().adaptFromJson(saintAsJsonObject.getJsonArray("stats"));
				saintData.stats = sg;
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return saintData;
//		});
		
//		return null;
	}

	@Override
	public JsonObject adaptToJson(SaintData arg0) throws Exception {
		return null;
	}

}
