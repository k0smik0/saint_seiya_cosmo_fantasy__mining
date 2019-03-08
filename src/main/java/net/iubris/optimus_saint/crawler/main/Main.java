package net.iubris.optimus_saint.crawler.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Comparator;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.github.jankroken.commandline.CommandLineParser;
import com.github.jankroken.commandline.OptionStyle;
import com.github.jankroken.commandline.annotations.LongSwitch;
import com.github.jankroken.commandline.annotations.Option;
import com.github.jankroken.commandline.annotations.ShortSwitch;
import com.github.jankroken.commandline.annotations.Toggle;

import net.iubris.optimus_saint.crawler.SaintData;
import net.iubris.optimus_saint.crawler.SaintsDataHolder;
import net.iubris.optimus_saint.crawler._utils.HttpUtils;
import net.iubris.optimus_saint.crawler._utils.JsonbUtils;
import net.iubris.optimus_saint.crawler.bucket.SaintsDataBucket;

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
		
		SimplePrinter simplePrinter = new SimplePrinter();
		
		SaintsDataBucket.INSTANCE.getSaints().stream()
		.sorted(Comparator.comparing(SaintData::getId))
		.forEach(sd->{
//			String reflectionToString = ToStringBuilder.reflectionToString(sd);
//			System.out.println(reflectionToString+"\n");
//			System.out.println( sd.name/*.value*/+" - id:"+sd.id );
			simplePrinter.print(sd);
		});
	}

/*	public enum Params {
		DOWNLOAD("download", "d"),
		LOAD("load", "l");
		private String longOption;
		private String shortOption;
		private Params(String longOption, String shortOption) {
			this.longOption = longOption;
			this.shortOption = shortOption;
		}
		
		public String getLongOption() {
			return longOption;
		}
		public String getShortOption() {
			return shortOption;
		}
	}*/
	
	public static class Arguments {
		private boolean download;
		private boolean load;
		
		@Option
		@LongSwitch("download")
		@ShortSwitch("d")
		@Toggle(true)
		public void setDownload(boolean download) {
			this.download = download;
		}
		
		@Option
		@LongSwitch("load")
		@ShortSwitch("l")
		@Toggle(true)
		public void setLoad(boolean load) {
			this.load = load;
		}
	} 
	
	
	public static class Loader {
		public static void loadFromDataset() throws FileNotFoundException, IOException {
			try ( FileInputStream fis = new FileInputStream("data"+File.separator+"saints.json"); ) {
				JsonbUtils.INSTANCE.getParser().fromJson(fis, SaintsDataHolder.class);
			} finally {
				try {
					JsonbUtils.INSTANCE.closeParser();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static class Downloader {
	
		private static final String SAINTS_DATASET_UPDATE_URL_PREFIX = "https://sscfdb.com/api/saints/";
		private static final ExecutorService oneFixedThreadPool;
		
		static {
			oneFixedThreadPool = Executors.newFixedThreadPool(1);
		}
		
		public static void start() {
			if (!Config.isSaintsDatasetToUpdate()) {
				return;
			}
			try {
				Callable<Void> task = buildDownloadingTask();
				Future<Void> future = oneFixedThreadPool.submit(task);
				oneFixedThreadPool.shutdown();
				future.get();
			} catch (MalformedURLException | FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		private static Callable<Void> buildDownloadingTask() throws MalformedURLException, FileNotFoundException, IOException {
			Callable<Void> callable = new Callable<Void>() {
				@Override
				public Void call() {
					try {
						URL website = new URL(SAINTS_DATASET_UPDATE_URL_PREFIX);
						String outputFilePath = "data" + File.separator + "saints" + ".json";
						int downloaded = HttpUtils.httpDownloader_2(website, outputFilePath);
						double size = Math.floor(downloaded/1024/1024f);
	System.out.println("Downloaded "+outputFilePath+": "+String.format("%.2f", size)+"MB");
	//					toPrint = ""+downloading.get();
					} catch (Exception e) {
						e.printStackTrace();
	//					toPrint = "x";
					}
	//				updateComplete(toPrint);
					return null;
				}			
			};
			
			return callable;
		}
	}
	
	
	private static interface Printer {
		void print(SaintData saintData);
	}
	private static class SimplePrinter implements Printer {
		
		private static final String SEPARATOR = "#";
		public void print(SaintData saintData) {
			String s = 
					saintData.id
				+SEPARATOR+saintData.name
				+SEPARATOR+saintData.description
//				+SEPARATOR
				+saintData.skills.first.description.replace("\n", "")
				+SEPARATOR+saintData.skills.second.description.replace("\n", "")
				+SEPARATOR+saintData.skills.third.description.replace("\n", "")
				+SEPARATOR+saintData.skills.fourth.description.replace("\n", "")
				+SEPARATOR+saintData.skills.seventhSense.description.replace("\n", "")
//				.replace(SEPARATOR+SEPARATOR, SEPARATOR)
				.replaceAll("/[#]{2,}/", "#");
			System.out.println(s);
		}		
	}

}
