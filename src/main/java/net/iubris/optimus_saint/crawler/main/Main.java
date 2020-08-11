package net.iubris.optimus_saint.crawler.main;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import net.iubris.optimus_saint.analyzer_new.SaintsDataAnalyzer;
import net.iubris.optimus_saint.crawler._di.CrawlerModule;
import net.iubris.optimus_saint.crawler._di.ProviderNotDI;
import net.iubris.optimus_saint.crawler.bucket.SaintsDataBucket;
import net.iubris.optimus_saint.crawler.main.Config.Dataset.Saints;
import net.iubris.optimus_saint.crawler.main.exporter.Exporter.ExporterStatus;
import net.iubris.optimus_saint.crawler.main.exporter.Exporter.TestStatus;
import net.iubris.optimus_saint.crawler.main.exporter.SaintsDataByBBAGoogleExporter;
import net.iubris.optimus_saint.crawler.main.exporter.SaintsDataGoogleSpreadSheetExporter;
import net.iubris.optimus_saint.crawler.main.printer.CSVPrinterSaintsDataPrinter;
import net.iubris.optimus_saint.crawler.main.printer.JsonPrinterSaintsDataPrinter;
import net.iubris.optimus_saint.crawler.main.printer.SaintsDataPrinter;
import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.SaintsData;
import net.iubris.optimus_saint.crawler.model.promote.SaintsPromoteDataLoader;
import net.iubris.optimus_saint.crawler.utils.JsonbUtils;
import net.iubris.optimus_saint.crawler.utils.Printer;

public class Main {

	private final Downloader downloader;
	private final Loader loader;
	private final SaintsDataPrinter saintsDataPrinter;
	private final SaintsDataAnalyzer saintsDataAnalyzer;
	private final SaintsDataGoogleSpreadSheetExporter googleSpreadSheetExporter;
	private final CSVPrinterSaintsDataPrinter csvPrinterSaintsDataPrinter;
	private final JsonPrinterSaintsDataPrinter jsonPrinterSaintsDataPrinter;
	private final Printer printer;
	private final SaintsDataBucket saintsDataBucket;
	private final SaintsDataByBBAGoogleExporter saintsDataByBBAGoogleExporter;

	@Inject
	public Main(final Downloader downloader, final Loader loader, final SaintsDataPrinter saintsDataPrinter,
			final SaintsDataAnalyzer saintsDataAnalyzer,
			final SaintsDataGoogleSpreadSheetExporter saintsDataGoogleSpreadSheetExporter,
			final SaintsDataByBBAGoogleExporter saintsDataByBBAGoogleExporter,
			final JsonPrinterSaintsDataPrinter jsonPrinterSaintsDataPrinter,
			final CSVPrinterSaintsDataPrinter csvPrinterSaintsDataPrinter,
			final SaintsDataBucket saintsDataBucket,
			final Printer printer) {
		this.downloader = downloader;
		this.loader = loader;
		this.saintsDataPrinter = saintsDataPrinter;
		this.saintsDataAnalyzer = saintsDataAnalyzer;
		this.googleSpreadSheetExporter = saintsDataGoogleSpreadSheetExporter;
		this.saintsDataByBBAGoogleExporter = saintsDataByBBAGoogleExporter;
		this.jsonPrinterSaintsDataPrinter = jsonPrinterSaintsDataPrinter;
		this.csvPrinterSaintsDataPrinter = csvPrinterSaintsDataPrinter;
		this.saintsDataBucket = saintsDataBucket;
		this.printer = printer;

		initCommandLineOptionActionsMap();
	}

	public static CommandLine parseArgs(final String[] args) throws ParseException {
		CommandLineOptions.init();
		CommandLineParser parser = new DefaultParser();
		// parse the command line arguments
//		System.out.println("args: "+Arrays.asList(args));
		CommandLine commandLineOptions = parser.parse(CommandLineOptions.getOptions(), args);
//		System.out.println("args: "+args);
		return commandLineOptions;
	}

	public void doStuff(final CommandLine commandLineOptions) {
		printer.println("** STARTING **\n");

		if (CommandLineOptions.hasOption(commandLineOptions, CommandLineOptions.HELP)) {
			CommandLineOptions.printFormattedHelp();
			printer.println("** FINISHING **");
			return;
		}

		if (Saints.isSaintsDatasetToUpdate()
				|| CommandLineOptions.hasOption(commandLineOptions, CommandLineOptions.DOWNLOAD)) {
			printer.println("* download phase - begin *");
			downloader.start();
			printer.println("* download phase - end *\n");
		}

//		Config.UPDATE_PROMOTION_ITEMS_DATASET = true;

//		if (CommandLineOptions.hasOption(commandLineOptions, CommandLineOptions.LOAD)) {
		// always do load
		try {
			printer.println("* load phase - begin *");

			SaintsPromoteDataLoader saintsPromoteDataLoader = SaintsPromoteDataLoader.INSTANCE;

			saintsPromoteDataLoader.reset();

			SaintsData saintsData = loader.loadFromDataset();
			List<SaintData> saints = saintsData.getSaints();
			saintsDataBucket.setSaints(saints);

			saints.forEach(sd -> {
				saintsPromoteDataLoader.handleItemToUpdate(sd.id);
			});
			saintsPromoteDataLoader.prepare(saints.size()).start();

			printer.println("* load phase - end *\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
//		}

		Collection<SaintData> saints = saintsDataBucket.getSaints();

		if (saints.size() == 0 && CommandLineOptions.areAllFalse(commandLineOptions)) {
//          printer.println("PRINT HELP?");
			CommandLineOptions.printFormattedHelp();
		}

		else if (saints.size() > 0) {
			commandLineOptionActionsMap.forEach((clo, cloa) -> {
				if (CommandLineOptions.hasOption(commandLineOptions, clo)) {
					printer.println("* '" + clo.getPrintMessage() + "' phase - begin *");
					cloa.doAction(saints);
					printer.println("* '" + clo.getPrintMessage() + "' phase - end*\n");
				}
			});
		}

		printer.println("** FINISHING **");
	}

	@FunctionalInterface
	private interface CommandLineOptionAction {
		void doAction(Collection<SaintData> saints);
	}

	private final Map<CommandLineOptions, CommandLineOptionAction> commandLineOptionActionsMap = new LinkedHashMap<>();

	private void initCommandLineOptionActionsMap() {
		commandLineOptionActionsMap.put(CommandLineOptions.PRINT, saints -> {
			saintsDataPrinter.print(saints);
		});
		commandLineOptionActionsMap.put(CommandLineOptions.MINIMAL_ANALYSIS, saints -> {
			saintsDataAnalyzer.minimal(saints);
		});
		commandLineOptionActionsMap.put(CommandLineOptions.CSV, saints -> {
			csvPrinterSaintsDataPrinter.print(saints);
		});
		commandLineOptionActionsMap.put(CommandLineOptions.JSON, saints -> {
			jsonPrinterSaintsDataPrinter.print(saints);
		});
		commandLineOptionActionsMap.put(CommandLineOptions.SPREADSHEET, saints -> {
			ExporterStatus export = googleSpreadSheetExporter.export(saints, false);
			printer.println("spreadsheet exporter status: " + export);
		});
		commandLineOptionActionsMap.put(CommandLineOptions.SPREADSHEET, saints -> {
			ExporterStatus export = googleSpreadSheetExporter.export(saints, false);
			printer.println("spreadsheet exporter status: " + export);
		});
		commandLineOptionActionsMap.put(CommandLineOptions.SPREADSHEET_OVERWRITE, saints -> {
			ExporterStatus export = googleSpreadSheetExporter.export(saints, true);
			printer.println("spreadsheet exporter (overwriting) status: " + export);
		});
		commandLineOptionActionsMap.put(CommandLineOptions.SPREADSHEET_BBA_TEST, saints -> {
			TestStatus status = saintsDataByBBAGoogleExporter.test(saints);
			printer.println("spreadsheet test status: " + status);
		});
		commandLineOptionActionsMap.put(CommandLineOptions.SPREADSHEET_BBA, saints -> {
			ExporterStatus export = saintsDataByBBAGoogleExporter.export(saints, true);
			printer.println("spreadsheet exporter status: " + export);
		});
	}

	public static void main(final String[] args) {

		try {
			long start = System.currentTimeMillis();
			CommandLine commandLineOptions = Main.parseArgs(args);
			long end = System.currentTimeMillis();
//System.out.println("parsed command line options in: " + (end - start) + "ms");

			if (CommandLineOptions.hasOption(commandLineOptions, CommandLineOptions.HELP)) {
				CommandLineOptions.printFormattedHelp();
				return;
			}

			if (CommandLineOptions.areAllFalse(commandLineOptions)) {
				CommandLineOptions.printFormattedHelp();
				return;
			}

			start = System.currentTimeMillis();
			System.out.println("** PREPARING - START **");
			Injector injector = Guice.createInjector(new CrawlerModule());
			ProviderNotDI.INSTANCE.setInjector(injector);
			Main main = injector.getInstance(Main.class);
			System.out.println("** PREPARING - END **");
			end = System.currentTimeMillis();
//System.out.println("create injected instance in: " + (end - start) + "ms");

			main.doStuff(commandLineOptions);

			JsonbUtils jsonbUtils = injector.getInstance(JsonbUtils.class);
			jsonbUtils.close();
		} catch (ParseException e) {
			System.out.println(args + " are not valid options");
			CommandLineOptions.printFormattedHelp();
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
