package net.iubris.optimus_saint.crawler.utils;

import javax.inject.Singleton;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

// TODO use this
@Singleton
public class JsonbUtils {
//	INSTANCE;
	
    private final JsonbConfig jc;
    
    private Jsonb jsonb;
	
	/*public void init() {
	    if (!setup) {
	        
	    }
	}*/

	public JsonbUtils() {
		jc = new JsonbConfig()
//				.withAdapters(new MaterialArrayAdapter())
				.withFormatting(true);
//		jsonb = JsonbBuilder.create(jc);
	}
	
	public Jsonb getParser() {
	    jsonb = JsonbBuilder.create(jc);
		return jsonb;
	}
	
	public void closeParser() throws Exception {
		if (jsonb!=null) {
			jsonb.close();
		}
	}
}
