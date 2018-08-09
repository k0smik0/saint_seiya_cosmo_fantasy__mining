package net.iubris.optimus_saint.model.saint.data.stats.literal;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import net.iubris.optimus_saint.model.saint.data.Localization;
import net.iubris.optimus_saint.model.saint.data.stats.StatsValue;

public class PromotionClass extends LiteralStat {

	public StatsValue findByInternalEnum(JsonObject jsonObject, String what) {
		return PromotionClassEnum.valueOf( buildEnumeration(jsonObject, what) );
	}

	public static enum PromotionClassEnum implements StatsValue {
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
}
