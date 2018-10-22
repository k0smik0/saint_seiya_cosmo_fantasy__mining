package net.iubris.optimus_saint.model.saint.data;

import javax.json.JsonObject;

import net.iubris.optimus_saint.model.saint.data._utils.Config;
import net.iubris.optimus_saint.utils.StringUtils;

public class LocalizationUtils {
	
	public static boolean isLocalization(String localizationKey) {
		try {
			Localization valueOf = Localization.valueOf(localizationKey);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	public static Localization getLocalization() {
		return Localization.valueOf(Config.LocalizationDefault);
	}

	public static String getLocalizedValue(JsonObject jsonObject) {
		if (jsonObject==null) {
			System.err.println("NULL");
			return StringUtils.EMPTY;
		}
		if (jsonObject.isEmpty()) {
			System.err.println("EMPTY");
			System.err.println("");
			return StringUtils.EMPTY;
		}
		return jsonObject.getString(Config.LocalizationDefault);
	}
}
