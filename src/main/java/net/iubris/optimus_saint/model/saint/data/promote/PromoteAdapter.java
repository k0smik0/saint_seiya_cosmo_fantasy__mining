package net.iubris.optimus_saint.model.saint.data.promote;

import java.util.Set;
import java.util.stream.Collectors;

import javax.json.JsonNumber;
import javax.json.JsonObject;

import net.iubris.optimus_saint.model.saint.data.LocalizedAdapter;

public class PromoteAdapter extends LocalizedAdapter<Promote> {

	public PromoteAdapter() {
		super(Promote.class);
	}
	
	@Override
	public Promote adaptFromJson(JsonObject jsonObject) throws InstantiationException, IllegalAccessException {
		Promote promote = super.adaptFromJson(jsonObject);
		
		int id = Integer.parseInt( jsonObject.getString("id") );
		promote.id = id;
		int level = jsonObject.getInt("level");
		promote.level = level;
		Set<Integer> promotingItemIds = jsonObject.getJsonArray("items").getValuesAs(JsonNumber.class).stream().map(jn->jn.intValue()).collect(Collectors.toSet());
		promote.promotingItemIds = promotingItemIds;
		
		return promote;
	}

}
