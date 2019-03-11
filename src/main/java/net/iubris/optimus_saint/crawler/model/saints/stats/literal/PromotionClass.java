package net.iubris.optimus_saint.crawler.model.saints.stats.literal;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import net.iubris.optimus_saint.common.StringUtils;
import net.iubris.optimus_saint.crawler.model.Localization;
import net.iubris.optimus_saint.crawler.model.saints.stats.StatsValue;

public class PromotionClass extends LiteralStat {

    @Override
	public StatsValue findByInternalEnum(JsonObject jsonObject, String what) {
	    String enumeration = buildEnumeration(jsonObject, what);
		try {
		    PromotionClassEnum promotionClassEnum = PromotionClassEnum.valueOf( enumeration );
		    return promotionClassEnum;
		} catch (IllegalArgumentException e) {
		    System.err.println("no '"+what+"' found in jsonObject '"+jsonObject+"'");
		    return PromotionClassEnum.NO_FOUND;
		}
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
		},
		ARAYASHIKI_1 {
            @Override
            public String getLocalized(Localization localization) {
                return _arayashiki1Map.get(localization);
            }
        },
        
        NO_FOUND {
            @Override
            public String getLocalized(Localization localization) {
                return StringUtils.EMPTY;
            }
        };
		private static Map<Localization,String> _greenMap = new HashMap<>();
		private static Map<Localization,String> _arayashikiMap = new HashMap<>();
		private static Map<Localization,String> _arayashiki1Map = new HashMap<>();
		static {
			_greenMap.put(Localization.EN, "Green");
			_arayashikiMap.put(Localization.EN, "Arayashiki");
			_arayashiki1Map.put(Localization.EN, "Arayashiki +1");
		}
		public abstract String getLocalized(Localization localization);
	}
}
