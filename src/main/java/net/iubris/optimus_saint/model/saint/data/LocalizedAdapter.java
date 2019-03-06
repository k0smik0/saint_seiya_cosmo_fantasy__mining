package net.iubris.optimus_saint.model.saint.data;

import javax.json.JsonObject;

public abstract class LocalizedAdapter<T> extends AbstractAdapter<T> {
	
	protected final String jsobObjectToParseAndLocalize;

	public LocalizedAdapter(String jsobObjectToParseAndLocalize) {
		this.jsobObjectToParseAndLocalize = jsobObjectToParseAndLocalize;
	}
	
	protected String getLocalizedValue(JsonObject arg0) {
		String localizedValue = LocalizationUtils.getLocalizedValue(arg0, jsobObjectToParseAndLocalize);
		return localizedValue;
	}
	
}
