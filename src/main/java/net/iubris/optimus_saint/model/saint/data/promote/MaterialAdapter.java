package net.iubris.optimus_saint.model.saint.data.promote;

import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class MaterialAdapter implements JsonbAdapter<List<String>, JsonObject> {

	@Override
	public List<String> adaptFromJson(JsonObject jo) throws Exception {
		JsonArray jsonArray = jo.asJsonArray();
		List<String> materialIds = jsonArray.stream().map(jv->jv.asJsonObject().getString("id")).collect(Collectors.toList());
		return materialIds;
	}

	@Override
	public JsonObject adaptToJson(List<String> arg0) throws Exception {
		System.out.println("STILL TODO");
		return null;
	}

}
