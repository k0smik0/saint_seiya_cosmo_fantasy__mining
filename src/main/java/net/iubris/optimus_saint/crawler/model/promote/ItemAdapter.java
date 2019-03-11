package net.iubris.optimus_saint.crawler.model.promote;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.adapters.saints.AbstractObjectAdapter;
import net.iubris.optimus_saint.crawler.model.LocalizationUtils;

public class ItemAdapter extends AbstractObjectAdapter<Item> {

	private static final MaterialAdapter MATERIAL_ADAPTER = new MaterialAdapter(); 
	
	@Override
	public Item adaptFromJson(JsonObject jsonObjectItem) throws Exception {
		Item item = new Item();
		
//		item.id = Long.parseLong( jsonObjectItem.getString(FIELD_ID) );
		item.id = jsonObjectItem.getString(FIELD_ID);
		
		item.name = LocalizationUtils.getLocalizedValue(jsonObjectItem, FIELD_NAME);
		
		item.equipmentLevel = jsonObjectItem.getInt("equipLvl");
		
		item.available = jsonObjectItem.getBoolean("available");
		
		item.materialIds = MATERIAL_ADAPTER.adaptFromJson( jsonObjectItem.getJsonObject("materials") );
		
		return item;
	}

}
