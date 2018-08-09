package net.iubris.optimus_saint.model.saint.data;

import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import net.iubris.optimus_saint.model.saint.data.value.name.Name;
import net.iubris.optimus_saint.model.saint.data.value.name.NameAdapter;

public class SaintsDataArrayAdapter implements JsonbAdapter<List<SaintData>, JsonArray> {

//	@Inject
//	SaintsDataBucketPatch saintsDataBucketPatch;
	
	private static final SaintDataAdapter saintDataAdapter = new SaintDataAdapter();
	
	@Override
	public List<SaintData> adaptFromJson(JsonArray jsonArray) throws Exception {
   	List<SaintData> saints = jsonArray.stream()
   	.filter(jv->{
   		JsonObject saint = jv.asJsonObject();
   		boolean incomplete = saint.getBoolean("incomplete");
   		JsonObject jsonObjectName = saint.getJsonObject("name");
   		try {
   			Name name = new NameAdapter().adaptFromJson(jsonObjectName);
   			if (incomplete) {
   				String s = "";
   				if (org.apache.commons.lang3.StringUtils.isNotBlank(name.value)) {
   					s = name.value;
   				} else {
   					s = saint.getString("fyi_name");
   					if (org.apache.commons.lang3.StringUtils.isBlank(s)) {
   						s = saint.getString("id");
   					}
   				}
   				System.out.println("skipping '"+s+"': incomplete\n");
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
				System.out.println(saintData.name.value);
				System.out.println(saintData.type.value);
				System.out.println("");
				return saintData;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return saintData;
   	})
   	.filter(sd->!sd.incomplete)
   	.collect(Collectors.toList());
      
   	System.out.println(saints.size());
		
   	SaintsDataBucketPatch.saints = saints;
   	
		return saints;
	}
	
	@Override
	public JsonArray adaptToJson(List<SaintData> arg0) throws Exception {
		return null;
	}

}