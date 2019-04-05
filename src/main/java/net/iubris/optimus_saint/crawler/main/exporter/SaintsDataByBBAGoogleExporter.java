package net.iubris.optimus_saint.crawler.main.exporter;

import java.util.Collection;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.inject.Inject;

import net.iubris.optimus_saint.crawler.main.exporter.Exporter.ExporterStatus;
import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.saints.skills.Skill;
import net.iubris.optimus_saint.crawler.utils.Printer;

public class SaintsDataByBBAGoogleExporter extends AbstractGoogleSpreadSheetExporter<ExporterStatus> {

	private Printer printer;

	@Inject
	public SaintsDataByBBAGoogleExporter(Printer printer) {
		super(GoogleConfig.APPLICATION_NAME, GoogleConfig.SPREADSHEET_ID);
        this.printer = printer;
    }

	@Override
	public ExporterStatus export(Collection<SaintData> saintDataCollection, boolean overwrite) {
		
		Function<SaintData,Skill> bySkillClassifier = new Function<SaintData, Skill>() {
			@Override
			public Skill apply(SaintData t) {
				return t.skills.getCrusade1();
			}
		};
		
		saintDataCollection.stream()
		.collect(Collectors.groupingBy(bySkillClassifier, TreeMap::new, Collectors.toList()))
				.forEach((t, u) -> {
					System.out.println(t.description+": "+u.stream().collect(Collectors.toList()));
				});
		
		return null;
	}

}
