package net.iubris.optimus_saint.crawler.main;

import static net.iubris.optimus_saint.crawler.main.Config.Dataset.Saints.SAINTS_DATASET_FILE;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.optimus_saint.crawler.model.SaintsData;
import net.iubris.optimus_saint.crawler.utils.JsonbUtils;

@Singleton
public class Loader {

    private final JsonbUtils jsonbUtils;
    
    @Inject
    public Loader(JsonbUtils jsonbUtils) {
        this.jsonbUtils = jsonbUtils;
//        System.out.println( LogManager.getFormatterLogger() );
    }
    
	public void loadFromDataset() throws FileNotFoundException, IOException {
	    if (!new File(SAINTS_DATASET_FILE).exists()) {
	        System.err.println("saints dataset not exists as file at location: "+SAINTS_DATASET_FILE+" - exiting");
	        return;
	    }
		try ( FileInputStream fis = new FileInputStream(Config.Dataset.Saints.SAINTS_DATASET_FILE); ) {
			jsonbUtils.getEngine().fromJson(fis, SaintsData.class);
		} finally {
			try {
				jsonbUtils.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}