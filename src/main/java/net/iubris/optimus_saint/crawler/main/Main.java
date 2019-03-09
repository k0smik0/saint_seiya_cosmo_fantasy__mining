package net.iubris.optimus_saint.crawler.main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;

import net.iubris.optimus_saint.crawler.bucket.SaintsDataBucket;
import net.iubris.optimus_saint.crawler.model.SaintData;

public class Main {
	
	public static void main(String[] args) {
		
		Arguments arguments = null;
		try {
			arguments = CommandLineParser.parse(Arguments.class, args, OptionStyle.SIMPLE);
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException e1) {
			e1.printStackTrace();
		}
		
		if ( arguments.download ) {
			Downloader.start();
		}
		
//		Config.UPDATE_PROMOTION_ITEMS_DATASET = true;
		
		if (arguments.load) {
			try {
				Loader.loadFromDataset();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		new CSVPrinter();
		
		Printer simplePrinter = new SimplePrinter();
		
		simplePrinter.print(SaintsDataBucket.INSTANCE.getSaints());
		
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
