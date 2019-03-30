package net.iubris.optimus_saint.crawler.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.common.io.Files;

import net.iubris.optimus_saint.crawler.utils.HttpUtils;
import net.iubris.optimus_saint.crawler.utils.Printer;

@Singleton
public class Downloader {

	private static final String SAINTS_DATASET_UPDATE_URL_PREFIX = "https://sscfdb.com/api/saints/";
	
	private final ExecutorService oneFixedThreadPool;
	
    private final Printer printer;

    protected DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd");
    
    @Inject
    public Downloader(Printer printer) {
        this.printer = printer;
        this.oneFixedThreadPool = Executors.newFixedThreadPool(1);
    }
	
	public void start() {
		try {
			Callable<Void> task = downloadCallable;/* buildDownloadingTask();*/
			Future<Void> future = oneFixedThreadPool.submit(task);
			oneFixedThreadPool.shutdown();
			future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} catch (Exception e) {
		    e.printStackTrace();
		}
	}
	
	
	private final Callable<Void> downloadCallable = new Callable<Void>() {

        @Override
        public Void call() {
            /*throws MalformedURLException, FileNotFoundException, IOException, KeyManagementException, NoSuchAlgorithmException*/
            try {
                URL website = new URL(SAINTS_DATASET_UPDATE_URL_PREFIX);
                
                File sddir = new File(Config.Dataset.Saints.SAINTS_DATASET_DIR);
                if (!sddir.isDirectory()) {
                    sddir.mkdir();
                }
                String outputFilePath = Config.Dataset.Saints.SAINTS_DATASET_FILE;
                File outputFile = new File(outputFilePath);
                if (outputFile.exists()) {
                    outputFile.delete();
                }
                String outputFilePathWithTimestamp = outputFilePath+"."+dateFormatter.format(new Date());
                File outputFileWithTimestamp = new File(outputFilePathWithTimestamp);
                long begin = System.currentTimeMillis();
                int downloaded = HttpUtils.httpDownloader_2(website, outputFilePathWithTimestamp);                                                                                                                                              
                long end = System.currentTimeMillis();
                double size = downloaded/1024f;
//System.out.println("Downloaded "+outputFilePath+": "+String.format("%.2f", size)+"MB");
                Date fileDate = new Date(outputFileWithTimestamp.lastModified());
                printer.println("Downloaded '"+SAINTS_DATASET_UPDATE_URL_PREFIX+"' to: "+outputFilePathWithTimestamp
                        +": "+String.format("%.2f", size)+"KB"+" in "+((end-begin)/1000.0f)+"s"+" - file modified at: "+fileDate);
                Files.copy(outputFileWithTimestamp, outputFile);
                printer.println("copied "+outputFilePathWithTimestamp+" to "+outputFilePath);
//              toPrint = ""+downloading.get();
            } catch(MalformedURLException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } 
            /*catch (Exception e) {
                e.printStackTrace();
//              toPrint = "x";
            }*/
//          updateComplete(toPrint);
            return null;
        }           
    };
	
	/*private Callable<Void> buildDownloadingTask() {
		return downloadCallable;
	}*/
}