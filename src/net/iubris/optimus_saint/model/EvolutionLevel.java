package net.iubris.optimus_saint.model;

public enum EvolutionLevel {
	
	YELLOW,
	BLUE_0,
	BLUE_1,
	BLUE_2,
	BRONZE_0,
	BRONZE_1,
	BRONZE_2,
	SILVER,
	SILVER_1,
	SILVER_2,
	SILVER_3,
	SILVER_4,
	GOLD,
	GOLD_1,
	GOLD_2,
	GOLD_3,
	GOLD_4;
	
	public EvolutionLevel next() {
		return EvolutionLevel.values()[ ordinal()+1 ];
	};
}