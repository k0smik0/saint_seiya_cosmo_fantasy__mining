package net.iubris.optimus_saint.model.saint.data.skills;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import javax.json.bind.adapter.JsonbAdapter;

import net.iubris.optimus_saint.model.saint.data.LocalizationUtils;
import net.iubris.optimus_saint.model.saint.data.value.description.Description;

public class SkillsAdapter implements JsonbAdapter<SkillsGroup, JsonArray> {

	private static final String ROOT_NODE = "name";
	
	@Override
	public SkillsGroup adaptFromJson(JsonArray jsonArray) {
		
		SkillsGroup skillsGroup = new SkillsGroup();
		
		jsonArray.stream().parallel().map(jv->{
			JsonObject jsonObjectRoot = jv.asJsonObject();
			
			String id = jsonObjectRoot.getString("id");
			
			JsonObject jsonObjectName = jsonObjectRoot.getJsonObject("name");
			String name = LocalizationUtils.getLocalizedValue(jsonObjectName);
			
			JsonObject jsonObjectDescription = jsonObjectRoot.getJsonObject("description");
			String descriptionString = LocalizationUtils.getLocalizedValue(jsonObjectDescription);
			Description description = new Description(); description.value = descriptionString;
			
			
			
			return new SkillsGroup();
			
		});
		
		for (JsonValue jsonValue : jsonArray) {
			
		}
		
		
		return null;
	}

	@Override
	public JsonArray adaptToJson(SkillsGroup arg0) throws Exception {
		return null;
	}

}
