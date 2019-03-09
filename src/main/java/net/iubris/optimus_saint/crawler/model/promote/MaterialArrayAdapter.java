package net.iubris.optimus_saint.crawler.model.promote;

import java.util.HashSet;
import java.util.Set;

import javax.json.JsonArray;
import javax.json.bind.adapter.JsonbAdapter;

public class MaterialArrayAdapter implements JsonbAdapter<Set<Long>, JsonArray> {

	private static final MaterialAdapter MATERIAL_ADAPTER = new MaterialAdapter();
	
	@Override
	public Set<Long> adaptFromJson(JsonArray materialArray) throws Exception {
		Set<Long> materialIds = new HashSet<>();
		materialArray.forEach(m->{
			
		});
		return materialIds;
	}

	@Override
	public JsonArray adaptToJson(Set<Long> arg0) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	
}
