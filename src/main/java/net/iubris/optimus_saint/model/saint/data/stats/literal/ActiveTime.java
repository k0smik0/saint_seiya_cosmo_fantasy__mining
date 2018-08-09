package net.iubris.optimus_saint.model.saint.data.stats.literal;

import java.util.HashMap;
import java.util.Map;

import javax.json.JsonObject;

import net.iubris.optimus_saint.model.saint.data.Localization;
import net.iubris.optimus_saint.model.saint.data.stats.StatsValue;

public class ActiveTime extends LiteralStat {

	@Override
	public StatsValue findByInternalEnum(JsonObject jsonObject, String what) {
		return ActiveTimeEnum.valueOf( buildEnumeration(jsonObject, what) );
	}
	
	private static enum ActiveTimeEnum implements StatsValue {
		A_LONG_TIME_AGO {
			@Override
			public String getLocalized(Localization localization) {
				return _aLongTimeAgoMap.get(localization);
			}
		};
		private static Map<Localization,String> _aLongTimeAgoMap = new HashMap<>();
		static {
			_aLongTimeAgoMap.put(Localization.EN, "A long time ago...");
		}
	}

}
