package net.iubris.optimus_saint.crawler.main.exporter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.inject.Inject;

import net.iubris.optimus_saint.common.StringUtils;
import net.iubris.optimus_saint.crawler.bucket.SaintsDataBucket;
import net.iubris.optimus_saint.crawler.main.exporter.Exporter.ExporterStatus;
import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.utils.Printer;

public class SaintsDataGoogleSpreadSheetExporter extends AbstractGoogleSpreadSheetExporter<ExporterStatus> {

	private static final String SHEET_NAME = "saints";
	private static final String RANGE_TO_POPULATE = SHEET_NAME + "!A1:P";
	private static final String RANGE_TO_CLEAR = SHEET_NAME + "!A2:P";

	private final SaintsDataBucket saintsDataBucket;
	private final SaintDataToJsonForSheetSaint sheetSaintsJsonExporter;
	private final Printer printer;

	@Inject
	public SaintsDataGoogleSpreadSheetExporter(final SaintsDataBucket saintsDataBucket,
			final SaintDataToJsonForSheetSaint sheetSaintsJsonExporter,
			final Printer printer) {
		super(GoogleConfig.APPLICATION_NAME, GoogleConfig.SPREADSHEET_ID);
		this.saintsDataBucket = saintsDataBucket;
		this.sheetSaintsJsonExporter = sheetSaintsJsonExporter;
		this.printer = printer;
	}

	@Override
	public ExporterStatus export(final Collection<SaintData> saintDataCollection, final boolean overwrite) {
		try {
			if (overwrite) {
				clearExistingValues(RANGE_TO_CLEAR);
			}

			List<List<Object>> rowsFromSpreadsheet = getValuesFromSpreadSheet(RANGE_TO_POPULATE);

			if (rowsFromSpreadsheet == null || rowsFromSpreadsheet.isEmpty()) {
				printer.println("No data found.");
				return ExporterStatus.KO;
			}

			int lastRow = rowsFromSpreadsheet.size();
			int indexFromStartInt = lastRow - 1;
			AtomicInteger indexFromStartWhich = new AtomicInteger(indexFromStartInt);

			// get(1) -> id
			Set<String> alreadyPresentIdsSet = rowsFromSpreadsheet.stream()
				.filter(r -> r.size() > 0)
				.map(r -> (String) r.get(1)).collect(Collectors.toSet());
//			printer.println("alreadyPresentIdsSet.size: "+alreadyPresentIdsSet.size());
//			printer.println(alreadyPresentIdsSet.stream().sorted().collect(Collectors.joining(", ")));

			List<List<Object>> valuesToAdd = saintsDataBucket.getSaints()/* .getIdToSaintsMap().entrySet() */
				.stream()
				.filter(sd -> {
					if (overwrite) {
						return true;
					}
					if (alreadyPresentIdsSet.contains(sd.id)) {
						printer.println(sd.id + " " + sd.name + " already present - skip");
						return false;
					}
					return true;
				})
				.sorted(Comparator.comparing(SaintData::getId))
				.map(sd -> saintDataToList(indexFromStartWhich, sd))
				.collect(Collectors.toList());
//		    printer.println("valuesToAdd.size: "+valuesToAdd.size());
//		    printer.println("valuesToAdd: "+valuesToAdd.stream().map(l->l.get(1)+"").collect(Collectors.joining(", ")));

			if (valuesToAdd.size() == 0) {
				printer.println("valuesToAdd.size: 0 - nothing to add");
				return ExporterStatus.OK;
			}

			String puttedAll = putValuesToSpreadsheet(RANGE_TO_POPULATE, valuesToAdd);
			if (puttedAll != null) {
				return ExporterStatus.OK;
			}
		} catch (GeneralSecurityException e) {
			System.err.println(e.getMessage());
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		return ExporterStatus.KO;
	}

	private List<Object> saintDataToList(final AtomicInteger index, final SaintData sd) {
		List<Object> list = new ArrayList<>();
		// index
		list.add(index.incrementAndGet());
		// id as fresher
		list.add(sd.id);
		// name+image+description
		list.add(sheetSaintsJsonExporter.saintRichNameToJsonString(sd).replace(StringUtils.NEW_LINE, StringUtils.SPACE)); // "{"+"\"name\":\""+sd.name+"\",\"imageSmall\":\""+sd.imageSmall+"\"}");
		// type
		list.add(sd.type.name().toLowerCase());
		// lane
		list.add(sd.lane.name().toLowerCase());
		list.add(sheetSaintsJsonExporter.skillToJsonString(sd.skills.first).replace(StringUtils.NEW_LINE, StringUtils.SPACE));
		list.add(sheetSaintsJsonExporter.skillToJsonString(sd.skills.second).replace(StringUtils.NEW_LINE, StringUtils.SPACE));
		list.add(sheetSaintsJsonExporter.skillToJsonString(sd.skills.third).replace(StringUtils.NEW_LINE, StringUtils.SPACE));
		list.add(sheetSaintsJsonExporter.skillToJsonString(sd.skills.fourth).replace(StringUtils.NEW_LINE, StringUtils.SPACE));
		list.add(sheetSaintsJsonExporter.skillToJsonString(sd.skills.getSeventhSense()).replace(StringUtils.NEW_LINE, StringUtils.SPACE));
		list.add(sheetSaintsJsonExporter.skillToJsonString(sd.skills.getCrusade1()).replace(StringUtils.NEW_LINE, StringUtils.SPACE));
		list.add(sheetSaintsJsonExporter.skillToJsonString(sd.skills.getCrusade2()).replace(StringUtils.NEW_LINE, StringUtils.SPACE));
		list.add(sd.keywords.stream().sorted().collect(Collectors.joining(StringUtils.COMMA + StringUtils.SPACE)));
		list.add(sd.tiers.value);

		return list;
	}

}
