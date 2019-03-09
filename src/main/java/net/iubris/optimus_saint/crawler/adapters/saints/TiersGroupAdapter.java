package net.iubris.optimus_saint.crawler.adapters.saints;

import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import net.iubris.optimus_saint.crawler.model.saints.value.tiers.Tier;
import net.iubris.optimus_saint.crawler.model.saints.value.tiers.TiersGroup;

public class TiersGroupAdapter implements JsonbAdapter<TiersGroup, JsonObject> {

	private static final TierAdapter TIER_ADAPTER = new TierAdapter();
	
	@Override
	public TiersGroup adaptFromJson(JsonObject jsonObject) throws Exception {
		TiersGroup tiersGroup = new TiersGroup();
		
		JsonObject pveJO = jsonObject.getJsonObject("PVE");
		Tier pve = TIER_ADAPTER.adaptFromJson(pveJO);
		tiersGroup.playerVersusEnemy = pve;
		
		JsonObject pvpJO = jsonObject.getJsonObject("PVP");
		Tier pvp = TIER_ADAPTER.adaptFromJson(pvpJO);
		tiersGroup.playerVersusPlayer = pvp;
		
		JsonObject crusadeJO = jsonObject.getJsonObject("Crusade");
		Tier crusade = TIER_ADAPTER.adaptFromJson(crusadeJO);
		tiersGroup.crusade = crusade;
		
		return tiersGroup;
	}

	@Override
	public JsonObject adaptToJson(TiersGroup arg0) throws Exception {
		return null;
	}
}
