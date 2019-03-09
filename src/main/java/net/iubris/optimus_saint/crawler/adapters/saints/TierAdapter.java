package net.iubris.optimus_saint.crawler.adapters.saints;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.model.saints.LocalizedAdapter;
import net.iubris.optimus_saint.crawler.model.saints.value.tiers.Tier;

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
