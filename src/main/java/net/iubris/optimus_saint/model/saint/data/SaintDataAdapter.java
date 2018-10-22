package net.iubris.optimus_saint.model.saint.data;

import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import net.iubris.optimus_saint.model.saint.data.stats.StatsArrayAdapter;
import net.iubris.optimus_saint.model.saint.data.stats.StatsGroup;
import net.iubris.optimus_saint.model.saint.data.tiers.TiersGroup;
import net.iubris.optimus_saint.model.saint.data.tiers.TiersGroupAdapter;
import net.iubris.optimus_saint.model.saint.data.value.description.Description;
import net.iubris.optimus_saint.model.saint.data.value.description.DescriptionAdapter;
import net.iubris.optimus_saint.model.saint.data.value.name.Name;
import net.iubris.optimus_saint.model.saint.data.value.name.NameAdapter;
import net.iubris.optimus_saint.model.saint.data.value.type.Type;
import net.iubris.optimus_saint.model.saint.data.value.type.TypeAdapter;

public class SaintDataAdapter implements JsonbAdapter<SaintData, JsonObject> {
	
	private static final NameAdapter NAME_ADAPTER = new NameAdapter();
	private static final DescriptionAdapter DESCRIPTION_ADAPTER = new DescriptionAdapter();
	private static final TypeAdapter TYPE_ADAPTER = new TypeAdapter();
	private static final StatsArrayAdapter STATS_ARRAY_ADAPTER = new StatsArrayAdapter();
	private static final TiersGroupAdapter TIERS_GROUP_ADAPTER = new TiersGroupAdapter();
	
//	private static final Executor ITEMS_DOWNLOADER_EXECUTOR = Executors.newFixedThreadPool(30);
	
	@Override
	public SaintData adaptFromJson(JsonObject saintAsJsonObject) throws Exception {
		SaintData saintData = new SaintData();
			
		int unitId = saintAsJsonObject.getInt("unitId");
		saintData.unitId = unitId;
			
		String fyi_name = saintAsJsonObject.getString("fyi_name");
		saintData.fyi_name = fyi_name;
			
		List<String> goodWith = saintAsJsonObject.getJsonArray("goodwith")
				.stream().map(jv->jv.toString()).collect(Collectors.toList());
		saintData.goodWith = goodWith;
			
		List<String> strongAgainst = saintAsJsonObject.getJsonArray("strongagainst")
				.stream().map(jv->jv.toString()).collect(Collectors.toList());
		saintData.strongAgainst = strongAgainst;

		JsonObject jsonObjectTiers = saintAsJsonObject.getJsonObject("tiers");
		TiersGroup tiers = TIERS_GROUP_ADAPTER.adaptFromJson(jsonObjectTiers);
		saintData.tiers = tiers;
			
		String id = saintAsJsonObject.getString("id");
		saintData.id = id;
			
		try {
			Name name = NAME_ADAPTER.adaptFromJson(saintAsJsonObject.getJsonObject("name"));
			saintData.name = name;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Description description = DESCRIPTION_ADAPTER.adaptFromJson(saintAsJsonObject.getJsonObject("description"));
			saintData.description = description;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Type type = TYPE_ADAPTER.adaptFromJson(saintAsJsonObject.getJsonObject("type"));
			saintData.type = type;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			StatsGroup sg = STATS_ARRAY_ADAPTER.adaptFromJson(saintAsJsonObject.getJsonArray("stats"));
			saintData.stats = sg;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return saintData;
	}

	@Override
	public JsonObject adaptToJson(SaintData arg0) throws Exception {
		return null;
	}

}
