package net.iubris.optimus_saint.crawler.main;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public enum CommandLineOptions {

	DOWNLOAD("d", "download", "download saints definition from http://www.sscfdb.com") {
		@Override
		public String getPrintMessage() {
			return "download";
		}
	},
//	LOAD("l", "load", "load data saints from file - mandatory to execute any among, except 'download'") {
//		@Override
//		public String getPrintMessage() {
//			return "load";
//		}
//	},
	PRINT("p", "print", "print loaded data") {
		@Override
		public String getPrintMessage() {
			return "sample print";
		}
	},
	SPREADSHEET("s", "spreadsheet", "export saints data google spreadsheet (sheet: 'saints'), appending") {
		@Override
		public String getPrintMessage() {
			return "google spreadsheet exporter";
		}
	},
	SPREADSHEET_OVERWRITE("S", "spreadsheet-overwrite", "export saints data google spreadsheet (sheet: 'saints'), overwriting existing data") {
		@Override
		public String getPrintMessage() {
			return "google spreadsheet exporter (overwriting)";
		}
	},
	JSON("j", "json", "print as json string") {
		@Override
		public String getPrintMessage() {
			return "json print";
		}
	},
	CSV("c", "csv", "export data on csv file") {
		@Override
		public String getPrintMessage() {
			return "csv print";
		}
	},
	MINIMAL_ANALYSIS("a", "analysis", "minimal stats analysis") {
		@Override
		public String getPrintMessage() {
			return "sample minimal analysis";
		}
	},

	SPREADSHEET_BBA_TEST("b", "bba-classifier-test", "test the classified crusade skills data exporter to google spreadsheet (sheet: 'crusade skills')") {
		@Override
		public String getPrintMessage() {
			return "google spreadsheet exporter classified by bba: TEST";
		}
	},
	SPREADSHEET_BBA("B", "bba-classifier", "export classified crusade skills data to google spreadsheet (sheet: 'crusade skills') - careful! it overwrites existing data!") {
		@Override
		public String getPrintMessage() {
			return "google spreadsheet exporter classified by bba";
		}
	},

	HELP("h", "help", "show this help menu") {
		@Override
		public String getPrintMessage() {
			return "";
		}
	};

	private final String shortOption;
	private final String longOption;
	private final String description;

	private static final Options options = new Options();
	private static boolean setup = false;

	private CommandLineOptions(final String shortOption, final String longOption, final String description) {
		this.shortOption = shortOption;
		this.longOption = longOption;
		this.description = description;
	}

	public abstract String getPrintMessage();

	public static void init() {
		if (!setup) {
			for (CommandLineOptions optionsEnum : values()) {
				Option option = new Option(optionsEnum.shortOption, optionsEnum.longOption, false, optionsEnum.description);
//				System.out.println("adding "+option);
				options.addOption(option);
			}
			setup = true;
		}
	}

	public static Options getOptions() {
		return options;
	}

	public static boolean hasOption(final CommandLine commandLine, final CommandLineOptions optionEnum) {
		return commandLine.hasOption(optionEnum.shortOption);
	}

	public static void printFormattedHelp() {
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.setWidth(150);
		helpFormatter.printHelp("Saints Data Crawler/Exporter", options);
	}

	public static boolean areAllFalse(final CommandLine commandLineOptions) {
		boolean allFalse = true;
		for (CommandLineOptions optionsEnum : values()) {
			allFalse = allFalse && !commandLineOptions.hasOption(optionsEnum.shortOption);
		}
		return allFalse;
	}
}
