package net.iubris.optimus_saint.model.saint.data.promote;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;
import javax.naming.OperationNotSupportedException;

public class SaintDataPromoteObjectAdapter implements JsonbAdapter<SaintDataPromoteObject, JsonObject> {
	
	private static final PromoteAdapter PROMOTE_ADAPTER = new PromoteAdapter();
	private static final ItemAdapter ITEM_ADAPTER = new ItemAdapter();

	@Override
	public SaintDataPromoteObject adaptFromJson(JsonObject jo) throws Exception {
		JsonObject joPromotes = jo.getJsonObject("promotes");
		
		// the real promotes
		// we would store these in some db...
		List<Promote> promotes = joPromotes.getJsonArray("promotes").stream()
			.map(jv -> {
				try {
					Promote promote = PROMOTE_ADAPTER.adaptFromJson(jv.asJsonObject());
					return promote;
				} catch (Exception e) {
					return new Promote();
				}
			})
			.filter(p -> p.id > 0)
			.collect(Collectors.toList());
		
		// we would store also these in some db...
		Set<Item> items = joPromotes.getJsonObject("items").values().stream()
			.map(itemAsJsonValue -> {
				try {
					Item item = ITEM_ADAPTER.adaptFromJson(itemAsJsonValue.asJsonObject());
					return item;
				} catch (Exception e) {
					return new Item();
				}
			})
			.filter(i -> i.id > 0)
			.filter(i -> i.available)
			.collect(Collectors.toSet());
		
		SaintDataPromoteObject saintDataPromoteObject = new SaintDataPromoteObject();
		
		return null;
	}

	@Override
	public JsonObject adaptToJson(SaintDataPromoteObject arg0) throws Exception {
		throw new OperationNotSupportedException();
	}
}
