package net.iubris.optimus_saint.crawler.model.promote;

import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.adapters.saints.AbstractObjectAdapter;

public class MaterialAdapter extends AbstractObjectAdapter<List<String>> {

	@Override
	public List<String> adaptFromJson(JsonObject jo) throws Exception {
		JsonArray jsonArray = jo.asJsonArray();
		List<String> materialIds = jsonArray.stream().map(jv->jv.asJsonObject().getString(FIELD_ID)).collect(Collectors.toList());
		return materialIds;
	}

}
