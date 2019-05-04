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
    
	public SaintsData loadFromDataset() throws FileNotFoundException, IOException {
	    if (!new File(SAINTS_DATASET_FILE).exists()) {
	        String msg = "saints dataset not exists as file at location: "+SAINTS_DATASET_FILE+" - exiting";
	        System.err.println(msg);
	        throw new FileNotFoundException(msg);
	    }
		try ( FileInputStream fis = new FileInputStream(Config.Dataset.Saints.SAINTS_DATASET_FILE); ) {
			SaintsData saintsData = jsonbUtils.getEngine().fromJson(fis, SaintsData.class);
			return saintsData;
		} finally {
			try {
				jsonbUtils.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}