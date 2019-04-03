package net.iubris.optimus_saint.crawler.model.saints.value.tiers;

public class TiersGroup {

//	@JsonbProperty(value="PVE")
	public Tier playerVersusEnemy;
//	@JsonbProperty(value="PVP")
	public Tier playerVersusPlayer;
//	@JsonbProperty(value="Crusade")
	public Tier crusade;
	
	@Override
    public String toString() {
	    return   "PVE:"+playerVersusEnemy.value
	            +",PVP:"+playerVersusPlayer.value
	            +",Crusade:"+crusade.value;
    }
}
