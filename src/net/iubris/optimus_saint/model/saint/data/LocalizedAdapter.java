package net.iubris.optimus_saint.model.saint.data;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class LocalizedAdapter<L extends AbstractValue> implements JsonbAdapter<L, JsonObject> {
	
	private final String classNameForInstancing;
	
	public LocalizedAdapter(String classNameForInstancing) {
		this.classNameForInstancing = classNameForInstancing;
	}

	@Override
	public L adaptFromJson(JsonObject jsonObject) throws Exception {
		
		String nameValue = jsonObject.getString("EN");
		
		@SuppressWarnings("unchecked")
		L l = (L) Class.forName(classNameForInstancing).newInstance();
		l.value = nameValue;
		
		return l;
	}

	@Override
	public JsonObject adaptToJson(L l) throws Exception {
		return null;
	}

}
