package net.iubris.optimus_saint.model.saint.data.tiers;

import javax.json.JsonObject;

import net.iubris.optimus_saint.model.saint.data.LocalizedAdapter;

public class TierAdapter extends LocalizedAdapter<Tier> {

	public TierAdapter() {
		super(Tier.class);
	}
	
	@Override
	public Tier adaptFromJson(JsonObject jsonObject) throws InstantiationException, IllegalAccessException {
		Tier tier = super.adaptFromJson(jsonObject);
		String string = jsonObject.getString("tier");
		tier.tier = string;
		return tier;
	}
	
}
