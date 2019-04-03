package net.iubris.optimus_saint.crawler.model.saints.stats.numerical;

import net.iubris.optimus_saint.crawler.model.saints.stats.AbstractStat;

public class NumericalStat extends AbstractStat {
	
//	@JsonbProperty(value="min")
	public float min;
	
//	@JsonbProperty(value="max")
	public float max;
	
	@Override
    public String toString() {
        return "min:"+min+",max:"+max;
    }
	
}
