package net.iubris.optimus_saint.crawler.model.saints.stats.literal;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.model.Localization;
import net.iubris.optimus_saint.crawler.model.saints.stats.StatsValue;

public class ClothKind extends LiteralStat {

	@Override
	public StatsValue findByInternalEnum(final JsonObject jsonObject, final String what) {
		return ClothKindEnum.valueOf(buildEnumeration(jsonObject, what));
	}

	public enum ClothKindEnum implements StatsValue {
		NO_CLOTH, // 0
		BLACK_SAINT, // 1
		BLACK_SAINTS,
		BRONZE_SAINT,
		BRONZE_SAINTS,
		SILVER_SAINT, // 3
		SILVER_SAINTS,
		GOLD_SAINT,
		GOLD_SAINTS,
		GOD,
		GODS,
		SPECTER,
		SPECTERS,
		MARINA, // 4
		MARINAS,
		GOD_WARRIOR,
		GOD_WARRIORS,
		MASTER,
		ANGEL,
		ANGELS,
		STEEL_SAINT,
		STEEL_SAINTS,
		DRYAD,
		DRYADS,
		GHOST,
		GHOSTS,
		OMEGA,
		OTHER;

		@Override
		public String getLocalized(final Localization localization) {
//			return StringUtils.toCamelCase(name());
			return name();
		}

	}
}
