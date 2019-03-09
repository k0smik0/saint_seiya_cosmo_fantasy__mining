package net.iubris.optimus_saint.crawler.model.saints.stats.numerical;

public class Rarity extends NumericalStat {

	public static int starsToNumber(String stars) {
		return stars.split("&").length;
	}
}
