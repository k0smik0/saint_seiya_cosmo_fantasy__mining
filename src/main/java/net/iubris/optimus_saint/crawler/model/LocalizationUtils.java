package net.iubris.optimus_saint.crawler.model;

import javax.json.JsonObject;

import net.iubris.optimus_saint.common.StringUtils;

public class LocalizationUtils {
	
	public static boolean isLocalization(String localizationKey) {
		try {
			Localization.valueOf(localizationKey);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	public static Localization getLocalization() {
		return Localization.valueOf(net.iubris.optimus_saint.crawler.main.Config.Localization.LocalizationDefault);
	}
	
	public static String getLocalizedValue(JsonObject jsonObjectParent, String jsonObjectChildName) {
		JsonObject jsonObject = jsonObjectParent.getJsonObject(jsonObjectChildName);
//		JsonObject jsonObjectLocalized = jsonObject.asJsonObject();
		JsonObject jsonObjectLocalized = jsonObject;
		String localizedValue = getLocalizedValue(jsonObjectLocalized);
		return localizedValue;
	}

	public static String getLocalizedValue(JsonObject jsonObject) {
		if (jsonObject==null) {
//			System.err.println("NULL");
			return StringUtils.EMPTY;
		}
		if (jsonObject.isEmpty()) {
//			System.err.println("EMPTY");
//			System.err.println("");
			return StringUtils.EMPTY;
		}
		return jsonObject.getString(net.iubris.optimus_saint.crawler.main.Config.Localization.LocalizationDefault);
	}
}
