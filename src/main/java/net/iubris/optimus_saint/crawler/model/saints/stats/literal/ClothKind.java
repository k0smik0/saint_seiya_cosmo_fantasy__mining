package net.iubris.optimus_saint.crawler.model.saints.stats.literal;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.model.Localization;
import net.iubris.optimus_saint.crawler.model.saints.stats.StatsValue;

public class ClothKind extends LiteralStat {

	@Override
	public StatsValue findByInternalEnum(JsonObject jsonObject, String what) {
		return ClothKindEnum.valueOf( buildEnumeration(jsonObject, what) );
	}
	
	public static enum ClothKindEnum implements StatsValue {
		NO_CLOTH, // 0
		BLACK_SAINT, // 1
		BRONZE_SAINT,
		SILVER_SAINT, // 3
		GOLD_SAINT,
		GOD,
		SPECTER,
		MARINA, // 4
		GOD_WARRIOR,
		MASTER,
      ANGEL,
      STEEL_SAINT;
	    
        @Override
		public String getLocalized(Localization localization) {
//			return StringUtils.toCamelCase(name());
			return name();
		}
				
	}
}
