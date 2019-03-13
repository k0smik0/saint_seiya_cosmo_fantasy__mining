package net.iubris.optimus_saint.crawler.main;

import java.io.IOException;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

import com.google.inject.Guice;
import com.google.inject.Injector;

import net.iubris.optimus_saint.crawler._di.CrawlerModule;
import net.iubris.optimus_saint.crawler._di.ProviderNotDI;
import net.iubris.optimus_saint.crawler.bucket.SaintsDataBucket;
import net.iubris.optimus_saint.crawler.main.Config.Dataset.Saints;
import net.iubris.optimus_saint.crawler.main.exporter.GoogleSpreadSheetExporter;
import net.iubris.optimus_saint.crawler.main.printer.CSVPrinterSaintsDataPrinter;
import net.iubris.optimus_saint.crawler.main.printer.SaintsDataPrinter;
import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.utils.Printer;

public class Main {
	
	private final Downloader downloader;
	private final Loader	loader;
	private final SaintsDataPrinter saintsDataPrinter;
	private final GoogleSpreadSheetExporter googleSpreadSheetExporter;
	private final Printer printer;
	private final SaintsDataBucket saintsDataBucket;
	
	@Inject
	public Main(Downloader downloader, Loader loader, SaintsDataPrinter saintsDataPrinter,
			GoogleSpreadSheetExporter googleSpreadSheetExporter, SaintsDataBucket saintsDataBucket,
			Printer printer) {
		this.downloader = downloader;
		this.loader = loader;
		this.saintsDataPrinter = saintsDataPrinter;
		this.googleSpreadSheetExporter = googleSpreadSheetExporter;
		this.saintsDataBucket = saintsDataBucket;
		this.printer = printer;
	}
	
	public static CommandLine parseArgs(String[] args) throws ParseException {
		CommandLineOptions.init();
		CommandLineParser parser = new DefaultParser();
		// parse the command line arguments
		CommandLine commandLineOptions = parser.parse(CommandLineOptions.getOptions(), args);
		return commandLineOptions;
	}

	public void doStuff(CommandLine commandLineOptions) {
		printer.println("** STARTING **\n");
		
		if ( Saints.isSaintsDatasetToUpdate() || CommandLineOptions.hasOption(commandLineOptions, CommandLineOptions.DOWNLOAD) /*arguments.download*/ ) {
		    printer.println("* download phase - begin *");
		    downloader.start();
		    printer.println("* download phase - end *\n");
		}
		
//		Config.UPDATE_PROMOTION_ITEMS_DATASET = true;
		
		if (CommandLineOptions.hasOption(commandLineOptions, CommandLineOptions.LOAD)) {
			try {
			    printer.println("* load phase - begin *");
				loader.loadFromDataset();
				printer.println("* load phase - end *\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Collection<SaintData> saints = saintsDataBucket.getSaints();
		
		if (saints.size()>0) {
		    if (CommandLineOptions.hasOption(commandLineOptions, CommandLineOptions.PRINT)) {
		        printer.println("* sample print phase - begin *");
		        saintsDataPrinter.print(saints);
		        printer.println("* sample print phase - end *\n");
		    }
		    
		    if (CommandLineOptions.hasOption(commandLineOptions, CommandLineOptions.CSV)) {
		        printer.println("* csv phase - begin *");
		        ProviderNotDI.INSTANCE.getInjector().getInstance(CSVPrinterSaintsDataPrinter.class).print(saints);
		        printer.println("* csv print phase - end *\n");
		    }
		    
		    if (CommandLineOptions.hasOption(commandLineOptions, CommandLineOptions.SPREADSHEET)) {
	           googleSpreadSheetExporter.export(saints);    
		    }
		}
		
		if (saints.size()==0 && CommandLineOptions.areAllFalse(commandLineOptions)) {
//			printer.println("PRINT HELP?");
			CommandLineOptions.printFormattedHelp();
		}
		
		printer.println("** FINISHED **");
	}

	public static void main(String[] args) {
		
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
        } catch (ParseException e) {
            System.out.println(args + " are not valid options");
            CommandLineOptions.printFormattedHelp();
            return;
        }
	}
}
