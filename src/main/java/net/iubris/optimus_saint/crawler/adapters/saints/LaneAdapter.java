package net.iubris.optimus_saint.crawler.adapters.saints;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.model.saints.LocalizedAdapter;
import net.iubris.optimus_saint.crawler.model.saints.value.lane.Lane;

public class LaneAdapter extends LocalizedAdapter<Lane> {

	public LaneAdapter() {
		super("lane");
	}

	@Override
	public Lane adaptFromJson(JsonObject arg0) throws Exception {
		String localizedValue = getLocalizedValue(arg0);
		Lane valueOf = Lane.valueOf(localizedValue.toUpperCase());
		return valueOf;
	}

}
