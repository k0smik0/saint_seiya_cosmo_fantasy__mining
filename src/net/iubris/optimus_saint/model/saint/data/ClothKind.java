package net.iubris.optimus_saint.model.saint.data;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

public class ClothKind extends LiteralStat {

	@Override
	StatsValue findByInternalEnum(JsonObject jsonObject, String what, String localization) {
		return ClothKindEnum.valueOf( buildEnumeration(jsonObject, what, localization) );
	}
	
	private static enum ClothKindEnum implements StatsValue {
		BRONZE_SAINT {
			@Override
			public String getLocalized(Localization localization) {
				return _bronzeSaintMap.get(localization);
			}
		};
		private static Map<Localization,String> _bronzeSaintMap = new HashMap<>();
		static {
			_bronzeSaintMap.put(Localization.EN, "Bronze Saint");
		}
	}

}
