package net.iubris.optimus_saint.crawler.model.saints;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.adapters.saints.AbstractAdapter;
import net.iubris.optimus_saint.crawler.model.LocalizationUtils;

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
