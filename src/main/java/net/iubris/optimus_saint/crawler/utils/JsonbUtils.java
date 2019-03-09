package net.iubris.optimus_saint.crawler.utils;

import javax.inject.Singleton;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import net.iubris.optimus_saint.crawler.model.promote.MaterialArrayAdapter;

// TODO use this
@Singleton
public enum JsonbUtils {
	INSTANCE;
	
	private Jsonb jsonb;

	private JsonbUtils() {
		JsonbConfig jc = new JsonbConfig()
				.withAdapters(new MaterialArrayAdapter())
				.withFormatting(true);
		jsonb = JsonbBuilder.create(jc);
	}
	
	public Jsonb getParser() {
		return jsonb;
	}
	
	public void closeParser() throws Exception {
		if (jsonb!=null) {
			jsonb.close();
		}
	}
}
