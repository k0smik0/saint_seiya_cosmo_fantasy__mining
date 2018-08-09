package net.iubris.optimus_saint.model.saint.data.tiers;

import javax.json.bind.annotation.JsonbProperty;

public class Tiers {

	@JsonbProperty(value="PVE")
	private Tier tierPVE;
	@JsonbProperty(value="PVP")
	private Tier tierPVP;
	@JsonbProperty(value="Crusade")
	private Tier tierCrusade;
}
