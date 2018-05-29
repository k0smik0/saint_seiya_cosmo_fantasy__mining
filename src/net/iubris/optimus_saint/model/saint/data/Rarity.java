package net.iubris.optimus_saint.model.saint.data;

public class Rarity extends NumericStat {

	public static int starsToNumber(String stars) {
		return stars.split("&").length;
	}
}
