package net.iubris.optimus_saint.crawler.main;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public enum CommandLineOptions {

	DOWNLOAD("d","download","download saints definition from http://www.sscfdb.com"),
	LOAD("l","load","load data saints from file"),
	PRINT("p","print","print loaded data"),
	SPREADSHEET("s","spreadsheet","export saints data google spreadsheet (sheet: 'saints'), appending"),
	SPREADSHEET_OVERWRITE("S","spreadsheet-overwrite","export saints data google spreadsheet (sheet: 'saints'), overwriting existing data"),
	JSON("j","json", "print as json string"),
	CSV("c","csv","export data on csv file"),
	MINIMAL_ANALYSIS("a", "analysis", "minimal stats analysis"),
	HELP("h","help","show this help menu"), 
	SPREADSHEET_BBA("B", "bba classifier", "export classified crusade skills data to google spreadsheet (sheet: 'crusade skills')")
	;
	
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
//				System.out.println("adding "+option);
				options.addOption(option);
			}
			setup = true;
		}
	}
	
	public static Options getOptions() {
		return options;
	}
	
	public static boolean hasOption(CommandLine commandLine, CommandLineOptions optionEnum) {
		return commandLine.hasOption(optionEnum.shortOption);
	}
	
	public static void printFormattedHelp() {
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.setWidth(150);
		helpFormatter.printHelp("Saints Data Crawler/Exporter", options);
	}
	
	public static boolean areAllFalse(CommandLine commandLineOptions) {
		boolean allFalse = true;
		for (CommandLineOptions optionsEnum : values()) {
			allFalse = allFalse && !commandLineOptions.hasOption(optionsEnum.shortOption);
		}
		return allFalse;
	}
}
