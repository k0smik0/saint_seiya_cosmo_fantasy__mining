package net.iubris.optimus_saint.crawler.main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import net.iubris.optimus_saint.crawler._di.CrawlerModule;
import net.iubris.optimus_saint.crawler.bucket.SaintsDataBucket;
import net.iubris.optimus_saint.crawler.main.Config.Dataset.Saints;

import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class Main {
	
	public static void main(String[] args) {
	    
	    Injector injector = Guice.createInjector(new CrawlerModule());
		
		Arguments arguments = null;
		try {
			arguments = CommandLineParser.parse(Arguments.class, args, OptionStyle.SIMPLE);
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		
		if ( Saints.isSaintsDatasetToUpdate() || arguments.download ) {
		    injector.getInstance(Downloader.class)
            /*Downloader*/.start();
		}
		
//		Config.UPDATE_PROMOTION_ITEMS_DATASET = true;
		
		if (arguments.load) {
			try {
			    injector.getInstance(Loader.class)
				/*Loader*/.loadFromDataset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
//		new CSVPrinter();
		
		SaintsDataPrinter saintsDataPrinter = injector.getInstance(SaintsDataPrinter.class)/* new SaintsDataSimplePrinter()*/;
		saintsDataPrinter.print(SaintsDataBucket.INSTANCE.getSaints());
		
		
		Exporter<Void> googleSpreadSheetExporter = new GoogleSpreadSheetExporter();
		googleSpreadSheetExporter.export(SaintsDataBucket.INSTANCE.getSaints());
		
		
//		SaintsDataBucket.INSTANCE.getSaints().stream()
//		.sorted(Comparator.comparing(SaintData::getId))
//		.forEach(sd->{
////			String reflectionToString = ToStringBuilder.reflectionToString(sd);
////			System.out.println(reflectionToString+"\n");
////			System.out.println( sd.name/*.value*/+" - id:"+sd.id );
//			simplePrinter.print(sd);
//		});
		
		
	}

}
