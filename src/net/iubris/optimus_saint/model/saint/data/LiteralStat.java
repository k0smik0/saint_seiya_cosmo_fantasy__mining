package net.iubris.optimus_saint.model.saint.data;

import javax.json.JsonObject;

import net.iubris.optimus_saint.utils.StringUtils;

public abstract class LiteralStat extends AbstractStat {

	StatValue min;
	StatValue max;

	abstract StatsValue findByInternalEnum(JsonObject jsonObject, String what, String localization);

	protected static String buildEnumeration(JsonObject jsonObject, String what, String localization) {
		return jsonObject.getJsonObject(what).getString(localization)
			.toUpperCase()
			.replaceAll(StringUtils.SPACE, StringUtils.UNDERSCORE)
			.replaceAll(StringUtils.DOT, StringUtils.EMPTY);
	}
}
