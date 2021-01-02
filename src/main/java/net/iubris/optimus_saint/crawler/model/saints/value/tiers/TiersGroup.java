package net.iubris.optimus_saint.crawler.model.saints.value.tiers;

public class TiersGroup {

//	@JsonbProperty(value="PVE")
	public Tiers playerVersusEnemy;
//	@JsonbProperty(value="PVP")
	public Tiers playerVersusPlayer;
//	@JsonbProperty(value="Crusade")
	public Tiers crusade;

	@Override
	public String toString() {
		return "PVE:" + playerVersusEnemy.value
				+ ",PVP:" + playerVersusPlayer.value
				+ ",Crusade:" + crusade.value;
	}
}
