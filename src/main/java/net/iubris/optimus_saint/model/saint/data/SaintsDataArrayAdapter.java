package net.iubris.optimus_saint.model.saint.data;

import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import org.apache.commons.lang3.StringUtils;

import net.iubris.optimus_saint.model.saint.data.value.name.Name;
import net.iubris.optimus_saint.model.saint.data.value.name.NameAdapter;

public class SaintsDataArrayAdapter implements JsonbAdapter<List<SaintData>, JsonArray> {

//	@Inject
//	SaintsDataBucketPatch saintsDataBucketPatch;
	
	private final SaintDataAdapter saintDataAdapter;
	private final SaintsDataUpgrader saintsDataUpgrader;
	
	public SaintsDataArrayAdapter() {
		this.saintDataAdapter = new SaintDataAdapter();
		this.saintsDataUpgrader = SaintsDataUpgrader.INSTANCE;
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
   			Name name = new NameAdapter().adaptFromJson(jsonObjectName);
   			if (incomplete) {
   				String s = "";
   				if (StringUtils.isNotBlank(name.value)) {
   					s = name.value+" :: "+fyi_name;
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
      
   	SaintsDataBucketPatch.saints = saints;
   	
   	saintsDataUpgrader.prepare( saints.size() ).start();
   	
		return saints;
	}
	
	@Override
	public JsonArray adaptToJson(List<SaintData> arg0) throws Exception {
		return null;
	}

}