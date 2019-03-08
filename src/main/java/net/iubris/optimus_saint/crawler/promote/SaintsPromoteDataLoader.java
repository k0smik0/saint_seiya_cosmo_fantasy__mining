package net.iubris.optimus_saint.crawler.promote;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import net.iubris.optimus_saint.crawler._utils.HttpUtils;
import net.iubris.optimus_saint.crawler.main.Config;
import net.iubris.optimus_saint.utils.StringUtils;

public enum SaintsPromoteDataLoader {
	INSTANCE;
	
//	private static final DirectDownloader DIRECT_DOWNLOADER = new DirectDownloader(30);
	private static final String ITEM_UPDATE_URL_PREFIX = "https://sscfdb.com/api/promote/";
	
	protected int amount;
	protected final AtomicInteger downloading = new AtomicInteger();
	
	private final List<Callable<Void>> tasks = new ArrayList<>();
	
	private static final int THREADS = 3;
	
	protected ExecutorService newFixedThreadPool;
	
//	protected int progressPercentage;
	
	public static void main(String[] args) {
		SaintsPromoteDataLoader saintsDataUpgrader = SaintsPromoteDataLoader.INSTANCE;
		saintsDataUpgrader.reset();
		saintsDataUpgrader.prepare(9);
		IntStream.rangeClosed(1, 9)
			.mapToObj(value -> new String("10000"+value+"01"))
			.forEach(s->saintsDataUpgrader.handleItemToUpdate(s));
		saintsDataUpgrader.start();
	}
	
	public SaintsPromoteDataLoader reset() {
		tasks.clear();
		if (newFixedThreadPool!=null) {
			newFixedThreadPool.shutdownNow();
		}
		return this;
	}
	public SaintsPromoteDataLoader prepare(int amount) {
		this.amount = amount;
System.out.println("total to download: "+amount);
		newFixedThreadPool = Executors.newFixedThreadPool(THREADS);
		this.downloading.set(0);
		return this;
	}
	
	public SaintsPromoteDataLoader handleItemToUpdate(String id) {
		if (!Config.isPromotionItemsDatasetToUpdate()) {
			return this;
		}
		String idAsString = ""+id;
		String grabbedId = idAsString.substring(0, idAsString.length()-2).replaceFirst("1", StringUtils.EMPTY);
		int urlIntId = Integer.parseInt( grabbedId );
		try {
			Callable<Void> task = buildDownloadingTask(urlIntId, idAsString);
			tasks.add(task);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return this;
	}
	
	private Callable<Void> buildDownloadingTask(int urlIntId, String id) throws MalformedURLException, FileNotFoundException, IOException {
		Callable<Void> callable = new Callable<Void>() {
			private String toPrint = "";
			@Override
			public Void call() {
				downloading.incrementAndGet();
				try {
					URL website = new URL(ITEM_UPDATE_URL_PREFIX + urlIntId);
//System.out.println("downloading from: "+website);
					String outputFilePath = "data" + File.separator + "promote" + File.separator + id + ".json";
					int downloaded = HttpUtils.httpDownloader_2(website, outputFilePath);
					double size = Math.floor(downloaded/1024/1024f);
System.out.println("Downloaded "+id+": "+String.format("%.2f", size)+"MB");
					toPrint = ""+downloading.get();
				} catch (Exception e) {
					e.printStackTrace();
					toPrint = "x";
				}
//				updateComplete(toPrint);
				return null;
			}			
		};
		
		return callable;
	}
	
	
	
	
	/*private final DownloadListener downloadListener = new DownloadListener() {
		@Override
		public void onUpdate(int bytes, int totalDownloaded) {}
		@Override
		public void onStart(String fname, int fsize) {}
		@Override
		public void onComplete() {
			updateComplete(++completed);
		}
		@Override
		public void onCancel() {};
	};*/
	
	private static void updateComplete(String progressPercentage) {
		/*final int width = amount;

		System.out.print("\r[");
		int i = 0;
		for (; i <= (int) (progressPercentage * width); i++) {
			System.out.print(".");
		}
		for (; i < width; i++) {
			System.out.print(" ");
		}
		System.out.print("]");*/
		
		System.out.println( progressPercentage+" ");		
	}
	
	public void start() {
		try {
			newFixedThreadPool.invokeAll(tasks);
			newFixedThreadPool.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/*public void start() {
		if (!Config.UPDATE_ITEMS) {
			newFixedThreadPool.submit(DIRECT_DOWNLOADER);
			
//			Thread thread = new Thread(DIRECT_DOWNLOADER);
//			thread.start();
//			try {
//				thread.join();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}*/

}
