package net.iubris.optimus_saint.crawler.model.promote;

import java.util.HashSet;
import java.util.Set;

import javax.json.JsonArray;

import net.iubris.optimus_saint.crawler.adapters.saints.AbstractArrayAdapter;

public class MaterialArrayAdapter extends AbstractArrayAdapter<Set<Long>> {

//	private static final MaterialAdapter MATERIAL_ADAPTER = new MaterialAdapter();
	
	@Override
	public Set<Long> adaptFromJson(JsonArray materialArray) throws Exception {
		Set<Long> materialIds = new HashSet<>();
		materialArray.forEach(m->{
			
		});
		return materialIds;
	}
}
