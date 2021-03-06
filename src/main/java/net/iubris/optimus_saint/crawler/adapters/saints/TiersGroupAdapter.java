package net.iubris.optimus_saint.crawler.adapters.saints;

import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.model.saints.value.tiers.Tiers;
import net.iubris.optimus_saint.crawler.model.saints.value.tiers.TiersGroup;

public class TiersGroupAdapter extends AbstractObjectAdapter<TiersGroup> {

//	private static final TierAdapter TIER_ADAPTER = new TierAdapter();

	@Override
	public TiersGroup adaptFromJson(final JsonObject jsonObject) throws Exception {
		TiersGroup tiersGroup = new TiersGroup();

		// OLD
		/*
		 * JsonObject pveJO = jsonObject.getJsonObject("PVE");
		 * Tier pve = TIER_ADAPTER.adaptFromJson(pveJO);
		 * tiersGroup.playerVersusEnemy = pve;
		 *
		 * JsonObject pvpJO = jsonObject.getJsonObject("PVP");
		 * Tier pvp = TIER_ADAPTER.adaptFromJson(pvpJO);
		 * tiersGroup.playerVersusPlayer = pvp;
		 *
		 * JsonObject crusadeJO = jsonObject.getJsonObject("Crusade");
		 * Tier crusade = TIER_ADAPTER.adaptFromJson(crusadeJO);
		 * tiersGroup.crusade = crusade;
		 */

		/*-
		String pveJO = jsonValue.getString("PVE");
		Tier pve = new Tier();// TIER_ADAPTER.adaptFromJson(pveJO);
		pve.value = pveJO;
		tiersGroup.playerVersusEnemy = pve;
		
		String pvpJO = jsonValue.getString("PVP");
		Tier pvp = new Tier(); // TIER_ADAPTER.adaptFromJson(pvpJO);
		pvp.value = pvpJO;
		tiersGroup.playerVersusPlayer = pvp;
		
		String crusadeJO = jsonValue.getString("Crusade");
		Tier crusade = new Tier(); // TIER_ADAPTER.adaptFromJson(crusadeJO);
		crusade.value = crusadeJO;
		tiersGroup.crusade = crusade;
		*/

		String pveJO = jsonObject.getString("PVE");
		Tiers pve = new Tiers();// TIER_ADAPTER.adaptFromJson(pveJO);
		pve.value = pveJO;
		tiersGroup.playerVersusEnemy = pve;

		String pvpJO = jsonObject.getString("PVP");
		Tiers pvp = new Tiers(); // TIER_ADAPTER.adaptFromJson(pvpJO);
		pvp.value = pvpJO;
		tiersGroup.playerVersusPlayer = pvp;

		String crusadeJO = jsonObject.getString("Crusade");
		Tiers crusade = new Tiers(); // TIER_ADAPTER.adaptFromJson(crusadeJO);
		crusade.value = crusadeJO;
		tiersGroup.crusade = crusade;

		return tiersGroup;
	}

	@Override
	public JsonObject adaptToJson(final TiersGroup arg0) throws Exception {
		return null;
	}
}
