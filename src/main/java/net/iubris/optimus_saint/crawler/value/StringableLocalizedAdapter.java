package net.iubris.optimus_saint.crawler.value;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.LocalizationUtils;

public class StringableLocalizedAdapter extends LocalizedAdapter<String> {

	public StringableLocalizedAdapter(String jsobObjectToParseAndLocalize) {
		super(jsobObjectToParseAndLocalize);
	}
	
	@Override
	public String adaptFromJson(JsonObject arg0) throws Exception {
		String localizedValue = LocalizationUtils.getLocalizedValue(arg0, jsobObjectToParseAndLocalize);
		return localizedValue;
	}

}
