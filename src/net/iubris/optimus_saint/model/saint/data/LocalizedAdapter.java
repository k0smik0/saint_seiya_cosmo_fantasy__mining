package net.iubris.optimus_saint.model.saint.data;

import static net.iubris.optimus_saint.model.saint.data.SaintData.Saints.LocalizationDefault;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

public class LocalizedAdapter<L extends AbstractValue> implements JsonbAdapter<L, JsonObject> {
	
	private final Class<L> classToInstance;
	
	public LocalizedAdapter(Class<L> classToInstance) {
		this.classToInstance = classToInstance;
	}

	@Override
	public L adaptFromJson(JsonObject jsonObject) throws Exception {
		
		String nameValue = jsonObject.getString(LocalizationDefault);
		
		L l = classToInstance.newInstance();
		l.value = nameValue;
		
		return l;
	}

	@Override
	public JsonObject adaptToJson(L l) throws Exception {
		return null;
	}

}
