package net.iubris.optimus_saint.crawler.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpUtils {

	static class CustomUrlConnection implements Serializable, AutoCloseable {
		private static final long serialVersionUID = -740737899650531442L;
		private final URLConnection conn;
	
		public CustomUrlConnection(String url) throws IOException {
			conn = new URL(url).openConnection();
			conn.connect();
		}
		public CustomUrlConnection(URL url) throws IOException {
			conn = url.openConnection();
			conn.connect();
		}
	
		public InputStream getInputStream() throws IOException {
			return conn.getInputStream();
		}
	
		@Override
		public void close() throws IOException {
			getInputStream().close();
		}
	}

	private static void httpDownloader_1(URL website, String id) throws FileNotFoundException, IOException {
		try (
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				FileOutputStream fos = new FileOutputStream("data" + File.separator + "promote" + File.separator + id + ".json");
		) {
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		}
	}

	public static int httpDownloader_2(URL website, String outputFilePath) throws NoSuchAlgorithmException, KeyManagementException, FileNotFoundException, IOException {
			// Activate the new trust manager
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, HttpUtils.TRUST_ALL_CERTS, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
			
			int downloaded = 0;
			// And as before now you can use URL and URLConnection
			try (
					HttpUtils.CustomUrlConnection connection = new HttpUtils.CustomUrlConnection(website);
					InputStream is = connection.getInputStream();
					OutputStream os = new FileOutputStream(outputFilePath);
					)
			{
				// Limiting byte written to file per loop
				byte[] buffer = new byte[2048];
				// Increments file size
				int length;
				// Looping until server finishes
				while ((length = is.read(buffer)) != -1) {
					os.write(buffer, 0, length);
					downloaded += length;
				}
	//			System.out.println("Download Status: " + (downloaded * 100) / (length * 1.0) + "%"+": "+downloaded);
			}
			return downloaded;
		}

	// Create a new trust manager that trust all certificates
	private static final TrustManager[] TRUST_ALL_CERTS = new TrustManager[]{
	    new X509TrustManager() {
	        public X509Certificate[] getAcceptedIssuers() {
	            return null;
	        }
	        public void checkClientTrusted(
	            X509Certificate[] certs, String authType) {
	        }
	        public void checkServerTrusted(
	            X509Certificate[] certs, String authType) {
	        }
	    }
	};


}
