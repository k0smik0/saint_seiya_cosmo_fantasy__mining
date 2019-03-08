package net.iubris.optimus_saint.crawler.value.tiers;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.value.LocalizedAdapter;

public class TierAdapter extends LocalizedAdapter<Tier> {

	public TierAdapter() {
		super("tier");
	}
	
	@Override
	public Tier adaptFromJson(JsonObject jsonObject) throws InstantiationException, IllegalAccessException {
		Tier tier = new Tier();
		String string = jsonObject.getString("tier");
		tier.tier = string;
		return tier;
	}
	
}
