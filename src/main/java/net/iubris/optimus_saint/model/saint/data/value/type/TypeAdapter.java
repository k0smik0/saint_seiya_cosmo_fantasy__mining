package net.iubris.optimus_saint.model.saint.data.value.type;

import net.iubris.optimus_saint.model.saint.data.LocalizedAdapter;

public class TypeAdapter extends LocalizedAdapter<Type> {
	public TypeAdapter() {
		super(Type.class);
	}
	
	/*@Override
	public Type adaptFromJson(JsonObject jsonObject) throws InstantiationException, IllegalAccessException {
		Type adaptFromJson = super.adaptFromJson(jsonObject);
		System.out.println(adaptFromJson.value);
		System.out.println("");
		return adaptFromJson;
	}*/
}
