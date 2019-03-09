package net.iubris.optimus_saint.crawler.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import net.iubris.optimus_saint.crawler.utils.HttpUtils;

public class Downloader {

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