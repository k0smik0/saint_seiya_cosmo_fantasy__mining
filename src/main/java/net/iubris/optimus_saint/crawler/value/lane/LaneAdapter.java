package net.iubris.optimus_saint.crawler.value.lane;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.value.LocalizedAdapter;

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
