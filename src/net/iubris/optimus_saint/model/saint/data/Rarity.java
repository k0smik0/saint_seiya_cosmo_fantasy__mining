package net.iubris.optimus_saint.model.saint.data;

public class Rarity extends NumericalStat {

	public static int starsToNumber(String stars) {
		return stars.split("&").length;
	}
}
