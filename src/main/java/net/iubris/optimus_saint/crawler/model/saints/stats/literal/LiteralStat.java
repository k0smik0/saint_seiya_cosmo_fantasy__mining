package net.iubris.optimus_saint.crawler.model.saints.stats.literal;

import javax.json.JsonObject;

import net.iubris.optimus_saint.common.StringUtils;
import net.iubris.optimus_saint.crawler.model.saints.stats.AbstractStat;
import net.iubris.optimus_saint.crawler.model.saints.stats.StatValue;
import net.iubris.optimus_saint.crawler.model.saints.stats.StatsValue;

public abstract class LiteralStat extends AbstractStat {

	public StatValue min = new StatValue();
	public StatValue max = new StatValue();

	public abstract StatsValue findByInternalEnum(JsonObject jsonObject, String what);

	protected static String buildEnumeration(JsonObject jsonObject, String what) {
//		JsonObject jo = jsonObject.getJsonObject(what);
//		String value = LocalizationUtils.getLocalizedValue(jo);
	    
//		JsonObject jo = jsonObject.getJsonObject(what);
        String value = 
//                LocalizationUtils.getLocalizedValue(jo);
                jsonObject.getString(what);
		String returning = value
			.toUpperCase()
			.replaceAll(StringUtils.SPACE, StringUtils.UNDERSCORE)
			.replaceAll("\\"+StringUtils.DOT, StringUtils.EMPTY)
		    .replaceAll("\\"+StringUtils.PLUS, StringUtils.EMPTY);
		return returning;
	}
	
	@Override
	public String toString() {
	    return   "min:"+min.value
            +",max:"+max.value;
	}
}
