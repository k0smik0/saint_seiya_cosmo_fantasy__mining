package net.iubris.optimus_saint.model.saint.data.promote;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;
import javax.naming.OperationNotSupportedException;

public class SaintDataPromoteObjectAdapter implements JsonbAdapter<SaintDataPromoteObject, JsonObject> {
	
	private static final PromoteAdapter PROMOTE_ADAPTER = new PromoteAdapter();

	@Override
	public SaintDataPromoteObject adaptFromJson(JsonObject jo) throws Exception {
		JsonObject joPromotes = jo.getJsonObject("promotes");
		
		// the real promotes
		List<Promote> promotes = joPromotes.getJsonArray("promotes").stream()
			.map(jv-> {
				try {
					Promote promote = PROMOTE_ADAPTER.adaptFromJson(jv.asJsonObject());
					return promote;
				} catch (Exception e) {
					return new Promote();
				}
			}).filter(p->p.id>0).collect(Collectors.toList());
		
		JsonObject joItems = joPromotes.getJsonObject("items");
		
		
//		Set<Item> collect = joItems.values().stream().map(item->{ 
//				return null;
//			})
//			.collect(Collectors.toList());
		
		return null;
	}

	@Override
	public JsonObject adaptToJson(SaintDataPromoteObject arg0) throws Exception {
		throw new OperationNotSupportedException();
	}
}
