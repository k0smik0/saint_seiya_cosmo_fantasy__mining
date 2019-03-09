package net.iubris.optimus_saint.crawler.model.saints;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.model.LocalizationUtils;

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
