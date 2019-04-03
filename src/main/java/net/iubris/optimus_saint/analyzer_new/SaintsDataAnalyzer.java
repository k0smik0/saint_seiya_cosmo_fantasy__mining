package net.iubris.optimus_saint.analyzer_new;

import java.util.Collection;

import javax.inject.Inject;

import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.utils.Printer;

public class SaintsDataAnalyzer {

	private final Printer printer;
	
	@Inject
	public SaintsDataAnalyzer(Printer printer) {
		this.printer = printer;
	}

	public void minimal(Collection<SaintData> saints) {
		saints.stream()
		.sorted((o1, o2) -> (o1.getStats().rawSum() >= o2.getStats().rawSum()) ? 1 : -1)
		.forEach(s->{
			printer.println(s.getRawStatsSum()+"\t"+s.getName());
		});
	}

}
