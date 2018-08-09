package net.iubris.optimus_saint.model.saint.data.stats.numerical;

public class Rarity extends NumericalStat {

	public static int starsToNumber(String stars) {
		return stars.split("&").length;
	}
}
