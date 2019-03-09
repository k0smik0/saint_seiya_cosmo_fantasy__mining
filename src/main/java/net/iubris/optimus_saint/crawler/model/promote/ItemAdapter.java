package net.iubris.optimus_saint.crawler.model.promote;

import javax.json.JsonArray;
import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.adapters.saints.AbstractAdapter;
import net.iubris.optimus_saint.crawler.model.LocalizationUtils;

public class ItemAdapter extends AbstractAdapter<Item> {

	private static final MaterialAdapter MATERIAL_ADAPTER = new MaterialAdapter(); 
	
	@Override
	public Item adaptFromJson(JsonObject jsonObjectItem) throws Exception {
		Item item = new Item();
		
		item.id = Long.parseLong( jsonObjectItem.getString("id") );
		
		item.name = LocalizationUtils.getLocalizedValue(jsonObjectItem, "name");
		
		item.equipmentLevel = jsonObjectItem.getInt("equipLvl");
		
		item.available = jsonObjectItem.getBoolean("available");
		
		item.materialIds = MATERIAL_ADAPTER.adaptFromJson( jsonObjectItem.getJsonObject("materials") );
		
		return item;
	}

}
