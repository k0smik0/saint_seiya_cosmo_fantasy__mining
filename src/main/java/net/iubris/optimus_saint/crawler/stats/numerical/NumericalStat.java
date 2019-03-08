package net.iubris.optimus_saint.crawler.stats.numerical;

import net.iubris.optimus_saint.crawler.stats.AbstractStat;

public class NumericalStat extends AbstractStat {
	
//	private String minAsString;
//	private String maxAsString;
	
//	@JsonbProperty(value="min")
	public float min;
	
//	@JsonbProperty(value="max")
	public float max;
	
	
	/*public int getMin() {
		if (min==-1) {
			min = extractNumber(minAsString);
		}
		return min;
	}
	
	public int getMax() {
		if (max==-1) {
			max = extractNumber(maxAsString);
		}
		return max;
	}
	
	private static int extractNumber(String s) {
		int value = -1; 
		try {
			value = Integer.parseInt(s);
		} catch(NumberFormatException e) {
			value = s.split("&").length;
		}
		return value;
	}*/
}
