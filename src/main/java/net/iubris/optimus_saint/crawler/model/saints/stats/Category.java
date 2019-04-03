package net.iubris.optimus_saint.crawler.model.saints.stats;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.iubris.optimus_saint.common.StringUtils;

public class Category extends AbstractStat {

	public List<Integer> min = new ArrayList<>();
	public List<Integer> max = new ArrayList<>();
	
	@Override
	public String toString() {
	    return   "min:"+min.stream().map(i->i!=null ? StringUtils.EMPTY+i.intValue() : StringUtils.EMPTY+-1)
	                .collect(Collectors.joining(StringUtils.COMMA))
	            +"max:"+max.stream().map(i->i!=null ? StringUtils.EMPTY+i.intValue() : StringUtils.EMPTY+-1)
	                .collect(Collectors.joining(",")
	                        );
	}
}
