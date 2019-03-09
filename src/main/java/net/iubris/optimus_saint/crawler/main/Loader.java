package net.iubris.optimus_saint.crawler.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import net.iubris.optimus_saint.crawler.model.SaintsData;
import net.iubris.optimus_saint.crawler.utils.JsonbUtils;

public class Loader {
	public static void loadFromDataset() throws FileNotFoundException, IOException {
		try ( FileInputStream fis = new FileInputStream("data"+File.separator+"saints.json"); ) {
			JsonbUtils.INSTANCE.getParser().fromJson(fis, SaintsData.class);
		} finally {
			try {
				JsonbUtils.INSTANCE.closeParser();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}