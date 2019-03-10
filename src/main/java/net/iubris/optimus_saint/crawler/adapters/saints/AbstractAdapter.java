package net.iubris.optimus_saint.crawler.adapters.saints;

import javax.json.bind.adapter.JsonbAdapter;
import javax.naming.OperationNotSupportedException;

public abstract class AbstractAdapter<Destination, SourceFromJson> implements JsonbAdapter<Destination, SourceFromJson> {
	
	protected static final String FIELD_ID = "id";
	protected static final String FIELD_NAME = "name";
	protected static final String FIELD_DESCRIPTION = "description";
	
	@Override
	public SourceFromJson adaptToJson(Destination arg0) throws Exception {
		throw new OperationNotSupportedException();
	}

}
