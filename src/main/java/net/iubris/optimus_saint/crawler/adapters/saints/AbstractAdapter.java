package net.iubris.optimus_saint.crawler.adapters.saints;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;
import javax.naming.OperationNotSupportedException;

public abstract class AbstractAdapter<A> implements JsonbAdapter<A, JsonObject> {

	@Override
	public JsonObject adaptToJson(A arg0) throws Exception {
		throw new OperationNotSupportedException(); 
	}
}
