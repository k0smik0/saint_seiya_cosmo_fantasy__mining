package net.iubris.optimus_saint.crawler.main;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public enum CommandLineOptions {

	DOWNLOAD("d","download","download saints definition from http://www.sscfdb.com"),
	LOAD("l","load","load data saints from file"),
	PRINT("p","print","print loaded data"),
	SPREADSHEET("s","spreadsheet","export data into spreadsheet"),
	CSV("c","csv","export data on csv file"),
	
	HELP("h","help","show this help menu");
	
	private final String shortOption;
	private final String longOption;
	private final String description;
	
	private static final Options options = new Options();
	private static boolean setup = false;

	private CommandLineOptions(String shortOption, String longOption, String description) {
		this.shortOption = shortOption;
		this.longOption = longOption;
		this.description = description;
	}
	
	public static void init() {
		if (!setup) {
			for (CommandLineOptions optionsEnum : values()) {
				Option option = new Option(optionsEnum.shortOption, optionsEnum.longOption, false, optionsEnum.description);
				options.addOption(option);
			}
			setup = true;
		}
	}
	
	public static Options getOptions() {
		return options;
	}
	
	public static boolean hasOption(CommandLine commandLineOptions, CommandLineOptions optionEnum) {
		return commandLineOptions.hasOption(optionEnum.shortOption);
	}
	
	public static void printFormattedHelp() {
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.printHelp("Saints Data Crawler"/*Main.class.getSimpleName()*/, options);
	}
	
	public static boolean areAllFalse(CommandLine commandLineOptions) {
		boolean allFalse = true;
		for (CommandLineOptions optionsEnum : values()) {
			allFalse = allFalse && !commandLineOptions.hasOption(optionsEnum.shortOption);
		}
		return allFalse;
	}
}
