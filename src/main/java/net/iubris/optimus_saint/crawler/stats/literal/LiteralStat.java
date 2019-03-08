package net.iubris.optimus_saint.crawler.stats.literal;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.LocalizationUtils;
import net.iubris.optimus_saint.crawler.stats.AbstractStat;
import net.iubris.optimus_saint.crawler.stats.StatValue;
import net.iubris.optimus_saint.crawler.stats.StatsValue;
import net.iubris.optimus_saint.utils.StringUtils;

public abstract class LiteralStat extends AbstractStat {

	public StatValue min = new StatValue();
	public StatValue max = new StatValue();

	public abstract StatsValue findByInternalEnum(JsonObject jsonObject, String what);

	protected static String buildEnumeration(JsonObject jsonObject, String what) {
		JsonObject jo = jsonObject.getJsonObject(what);
		String value = LocalizationUtils.getLocalizedValue(jo);
		String returning = value
			.toUpperCase()
			.replaceAll(StringUtils.SPACE, StringUtils.UNDERSCORE)
			.replaceAll("\\"+StringUtils.DOT, StringUtils.EMPTY);
		return returning;
	}
}
