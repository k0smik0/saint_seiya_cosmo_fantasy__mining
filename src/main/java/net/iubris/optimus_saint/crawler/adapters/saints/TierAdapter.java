package net.iubris.optimus_saint.crawler.adapters.saints;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.model.saints.LocalizedAdapter;
import net.iubris.optimus_saint.crawler.model.saints.value.tiers.Tiers;

public class TierAdapter extends LocalizedAdapter<Tiers> {

	public TierAdapter() {
		super("tier");
	}
	
	@Override
	public Tiers adaptFromJson(JsonObject jsonObject) throws InstantiationException, IllegalAccessException {
		Tiers tier = new Tiers();
		String string = jsonObject.getString("tier");
		tier.value = string;
		return tier;
	}
	
}
