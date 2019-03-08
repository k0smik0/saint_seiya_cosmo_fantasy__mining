package net.iubris.optimus_saint.crawler.stats.literal;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.Localization;
import net.iubris.optimus_saint.crawler.stats.StatsValue;
import net.iubris.optimus_saint.utils.StringUtils;

public class ClothKind extends LiteralStat {

	@Override
	public StatsValue findByInternalEnum(JsonObject jsonObject, String what) {
		return ClothKindEnum.valueOf( buildEnumeration(jsonObject, what) );
	}
	
	private static enum ClothKindEnum implements StatsValue {
		NO_CLOTH {
			@Override
			public String getLocalized(Localization localization) {
				return _noClothMap.get(localization);
			}
		},
		BLACK_SAINT {
			@Override
			public String getLocalized(Localization localization) {
				return _blackClothMap.get(localization);
			}
		},
		BRONZE_SAINT {
			@Override
			public String getLocalized(Localization localization) {
				return _bronzeClothMap.get(localization);
			}
		},
		SILVER_SAINT {
			@Override
			public String getLocalized(Localization localization) {
				return _silverClothMap.get(localization);				
			}
		},
		GOLD_SAINT {
			@Override
			public String getLocalized(Localization localization) {
				return _goldClothMap.get(localization);
			}
		},
		GOD {
			@Override
			public String getLocalized(Localization localization) {
				return _godClothMap.get(localization);
			}
		},
		SPECTER {
			@Override
			public String getLocalized(Localization localization) {
				return _specterMap.get(localization);
			}
		},
		MARINA {
			@Override
			public String getLocalized(Localization localization) {
				return _marinaMap.get(localization);
			}
		},
		GOD_WARRIOR,
		MASTER;
		
		@Override
		public String getLocalized(Localization localization) {
			return StringUtils.toCamelCase(name());
		}
		
		private static Map<Localization,String> _noClothMap = new HashMap<>();
		static {
			_noClothMap.put(Localization.EN, "No Cloth");
		}
		private static Map<Localization,String> _blackClothMap = new HashMap<>();
		static {
			_blackClothMap.put(Localization.EN, "Black Cloth");
		}
		private static Map<Localization,String> _bronzeClothMap = new HashMap<>();
		static {
			_bronzeClothMap.put(Localization.EN, "Bronze Cloth");
		}
		private static Map<Localization,String> _silverClothMap = new HashMap<>();
		static {
			_silverClothMap.put(Localization.EN, "Silver Cloth");
		}
		private static Map<Localization,String> _goldClothMap = new HashMap<>();
		static {
			_goldClothMap.put(Localization.EN, "Gold Cloth");
		}
		private static Map<Localization,String> _godClothMap = new HashMap<>();
		static {
			_godClothMap.put(Localization.EN, "God Cloth Saint");
		}
		private static Map<Localization,String> _specterMap = new HashMap<>();
		static {
			_specterMap.put(Localization.EN, "Specter");
		}
		private static Map<Localization,String> _marinaMap = new HashMap<>();
		static {
			_marinaMap.put(Localization.EN, "Marina");
		}
		
	}
}
