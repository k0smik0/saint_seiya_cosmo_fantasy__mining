package net.iubris.optimus_saint.crawler.adapters.saints;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.saints.skills.SkillsGroup;
import net.iubris.optimus_saint.crawler.model.saints.stats.StatsGroup;
import net.iubris.optimus_saint.crawler.model.saints.value.lane.Lane;
import net.iubris.optimus_saint.crawler.model.saints.value.tiers.TiersGroup;
import net.iubris.optimus_saint.crawler.model.saints.value.type.Type;

public class SaintDataAdapter extends AbstractObjectAdapter<SaintData> {
	
//	private static final NameAdapter NAME_ADAPTER = new NameAdapter();
//	private static final DescriptionAdapter DESCRIPTION_ADAPTER = new DescriptionAdapter();
//	private static final TypeAdapter TYPE_ADAPTER = new TypeAdapter();
//	private static final LaneAdapter LANE_ADAPTER = new LaneAdapter();
	private static final StatsArrayAdapter STATS_ARRAY_ADAPTER = new StatsArrayAdapter();
	private static final TiersGroupAdapter TIERS_GROUP_ADAPTER = new TiersGroupAdapter();
	private static final SkillsGroupAdapter SKILLS_GROUP_ADAPTER = new SkillsGroupAdapter();
	private static final KeywordsAdapter KEYWORDS_ADAPTER = new KeywordsAdapter();
	
//	private static final Executor ITEMS_DOWNLOADER_EXECUTOR = Executors.newFixedThreadPool(30);
	
	@Override
	public SaintData adaptFromJson(JsonObject saintAsJsonObject) throws Exception {
		SaintData saintData = new SaintData();
		
		String stringId = saintAsJsonObject.getString("id");
//		long id = Long.parseLong(stringId);
//		saintData.id = id;
		saintData.id = stringId;
			
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
			
		/*try {
			String name = NAME_ADAPTER.adaptFromJson(saintAsJsonObject);
			        saintAsJsonObject.getString("name");
			saintData.name = name;
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		String name = saintAsJsonObject.getString("name");
		saintData.name = name;
		
		/*try {
			String description = DESCRIPTION_ADAPTER.adaptFromJson(saintAsJsonObject);
			saintData.description = description;
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		String description = saintAsJsonObject.getString("description");
        saintData.description = description;
		
		try {
//			Type type = TYPE_ADAPTER.adaptFromJson(saintAsJsonObject);
			Type type = Type.valueOf( saintAsJsonObject.getString("type").toUpperCase() );
			saintData.type = type;
		} catch (Exception e) {
			logger.error("error retrieving TYPE: ",e);
		}
		
		try {
//			Lane lane = LANE_ADAPTER.adaptFromJson(saintAsJsonObject);
			Lane lane = Lane.valueOf( saintAsJsonObject.getString("lane").toUpperCase() );
			saintData.lane = lane;
		} catch (Exception e) {
			logger.error("error retrieving LANE: ",e);
		}
		
		try {
			StatsGroup sg = STATS_ARRAY_ADAPTER.adaptFromJson(saintAsJsonObject.getJsonArray("stats"));
			saintData.stats = sg;
		} catch (Exception e) {
			logger.error("error retrieving STATS: ",e);
		}
		
		try {
			SkillsGroup sg =  SKILLS_GROUP_ADAPTER.adaptFromJson(saintAsJsonObject.getJsonArray("skills"));
			saintData.skills = sg;
		} catch (Exception e) {
			logger.error("error retrieving SKILLS: ",e);
		}
		
		try {
            Set<String> keywords =  KEYWORDS_ADAPTER.adaptFromJson(saintAsJsonObject.getJsonArray("keywords"));
            saintData.keywords = keywords;
        } catch (Exception e) {
            logger.error("error retrieving KEYWORDS: ",e);
        }
		
		saintData.imageSmall = saintAsJsonObject.getString("small");
//		SkillsAdapter

		return saintData;
	}
}
