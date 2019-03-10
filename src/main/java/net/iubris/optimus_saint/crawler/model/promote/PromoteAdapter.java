package net.iubris.optimus_saint.crawler.model.promote;

import java.util.Set;
import java.util.stream.Collectors;

import javax.json.JsonNumber;
import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.adapters.saints.AbstractObjectAdapter;
import net.iubris.optimus_saint.crawler.model.LocalizationUtils;

public class PromoteAdapter extends AbstractObjectAdapter<Promote> {
	@Override
	public Promote adaptFromJson(JsonObject jsonObject) throws InstantiationException, IllegalAccessException {
		Promote promote = new Promote();
		
		Long id = Long.parseLong( jsonObject.getString("id") );
		promote.id = id;
		
		String name = LocalizationUtils.getLocalizedValue(jsonObject, "name");
		promote.name = name;
		
		int level = jsonObject.getInt("level");
		promote.level = level;
		
		Set<Integer> promotingItemIds = jsonObject.getJsonArray("items").getValuesAs(JsonNumber.class).stream().map(jn->jn.intValue()).collect(Collectors.toSet());
		promote.promotingItemIds = promotingItemIds;
		
		return promote;
	}
}
