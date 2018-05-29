package net.iubris.optimus_saint.model.saint.data;

import java.util.HashMap;
import java.util.Map;

public enum PromotionClassEnum {

	GREEN {
		@Override
		public String getLocalized(Localization localization) {
			return _greenMap.get(localization);
		}
	},
	ARAYASHIKI {
		@Override
		public String getLocalized(Localization localization) {
			return _arayashikiMap.get(localization);
		}
	};
	
	private static Map<Localization,String> _greenMap = new HashMap<>();
	private static Map<Localization,String> _arayashikiMap = new HashMap<>();
	
	static {
		_greenMap.put(Localization.EN, "Green");
		_arayashikiMap.put(Localization.EN, "Arayashiki");
	}

	public abstract String getLocalized(Localization localization);
}
