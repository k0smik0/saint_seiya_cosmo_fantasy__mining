package net.iubris.optimus_saint.crawler.adapters.saints;

import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;

import org.apache.commons.lang3.StringUtils;

import net.iubris.optimus_saint.crawler.bucket.SaintsDataBucket;
import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.promote.SaintsPromoteDataLoader;

public class SaintsDataArrayAdapter extends AbstractArrayAdapter<List<SaintData>> {

	// TODO use this
//	@Inject
//	SaintsDataBucketPatch saintsDataBucketPatch;
	
	private final SaintDataAdapter saintDataAdapter;
	private final SaintsPromoteDataLoader saintsDataUpgrader;
	
	public SaintsDataArrayAdapter() {
		this.saintDataAdapter = new SaintDataAdapter();
		this.saintsDataUpgrader = SaintsPromoteDataLoader.INSTANCE;
	}
	
	@Override
	public List<SaintData> adaptFromJson(JsonArray jsonArray) throws Exception {
		saintsDataUpgrader.reset();
		
   	List<SaintData> saints = jsonArray.stream()
   	.filter(jv->{
   		JsonObject saintAsJsonObject = jv.asJsonObject();
   		boolean incomplete = saintAsJsonObject.getBoolean("incomplete");
   		String id = saintAsJsonObject.getString("id");
   		JsonObject jsonObjectName = saintAsJsonObject.getJsonObject("name");
   		String fyi_name = saintAsJsonObject.getString("fyi_name");
   		try {
   			String name = new NameAdapter().adaptFromJson(jsonObjectName);
   			if (incomplete) {
   				String s = "";
   				if (StringUtils.isNotBlank(name/*.value*/)) {
   					s = name/*.value*/+" :: "+fyi_name;
   				} else {
   					s = saintAsJsonObject.getString("fyi_name");
   					if (StringUtils.isBlank(s)) {
   						s = id;
   					}
   				}
   				System.out.println("skipping '"+s+"' ("+id+")"+": incomplete");
   			}
			} catch (Exception e) {
				e.printStackTrace();
			}
   		return !incomplete;
   	})
   	.map(jv->{
   		JsonObject saint = jv.asJsonObject();
   		SaintData saintData = new SaintData();
			try {
				saintData = saintDataAdapter.adaptFromJson(saint);
				saintsDataUpgrader.handleItemToUpdate(saintData.id);
				return saintData;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return saintData;
   	})
   	.filter(sd->!sd.incomplete)
   	.collect(Collectors.toList());
      
   	SaintsDataBucket.INSTANCE.setSaints(saints);
   	
   	saintsDataUpgrader.prepare( saints.size() ).start();
   	
		return saints;
	}

}