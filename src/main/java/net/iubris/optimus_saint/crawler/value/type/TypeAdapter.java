package net.iubris.optimus_saint.crawler.value.type;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.value.LocalizedAdapter;

public class TypeAdapter extends LocalizedAdapter<Type> {

	public TypeAdapter() {
		super("type");
	}

	@Override
	public Type adaptFromJson(JsonObject jsonObject) throws Exception {
		String localizedValue = getLocalizedValue(jsonObject);
		Type type = Type.valueOf(localizedValue.toUpperCase());
		return type;
	}
	
	/*@Override
	public Type adaptFromJson(JsonObject jsonObject) throws InstantiationException, IllegalAccessException {
		Type adaptFromJson = super.adaptFromJson(jsonObject);
		System.out.println(adaptFromJson.value);
		System.out.println("");
		return adaptFromJson;
	}*/
}
