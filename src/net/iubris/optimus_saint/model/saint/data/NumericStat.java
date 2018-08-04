package net.iubris.optimus_saint.model.saint.data;

import javax.json.bind.annotation.JsonbProperty;

public class NumericStat extends AbstractStat {
	
//	private String minAsString;
//	private String maxAsString;
	
	@JsonbProperty(value="min")
	float min = -1;
	
	@JsonbProperty(value="max")
	float max = -1;
	
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
