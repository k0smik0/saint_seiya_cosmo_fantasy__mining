package net.iubris.optimus_saint.model.saint.data;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import net.iubris.optimus_saint.model.saint.data.value.AbstractValue;
import net.iubris.optimus_saint.utils.StringUtils;

public class LocalizedAdapter<L extends AbstractValue> implements JsonbAdapter<L, JsonObject> {
	
	private final Class<L> classToInstance;
	
	public LocalizedAdapter(Class<L> classToInstance) {
		this.classToInstance = classToInstance;
	}

	@Override
	public L adaptFromJson(JsonObject jsonObject) throws InstantiationException, IllegalAccessException {
		
		L l = classToInstance.newInstance();
		
		if (jsonObject.isEmpty()) {
			l.value = StringUtils.EMPTY;
		} else {
			String value = LocalizationUtils.getLocalizedValue(jsonObject);
			l.value = value;	
		}
		
		return l;
	}

	@Override
	public JsonObject adaptToJson(L l) throws Exception {
		return null;
	}

}
