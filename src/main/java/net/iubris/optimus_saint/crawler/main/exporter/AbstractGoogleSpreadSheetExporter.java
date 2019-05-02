package net.iubris.optimus_saint.crawler.main.exporter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.ClearValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

abstract public class AbstractGoogleSpreadSheetExporter<S> implements Exporter<S> {
	
//	private static final String CLIENT_ID = "329557453456-sc3qimc2sisll4vh80ttuqo82ulhhlpc.apps.googleusercontent.com";
//	private static final String CLIENT_SECRET = "OIMFazf9V42spDZ5wHV8cifI";
	
	protected final String applicationName;
	protected final String spreadsheetId;
	
	public AbstractGoogleSpreadSheetExporter(String applicationName, String spreadsheetId) {
		this.applicationName = applicationName;
		this.spreadsheetId = spreadsheetId;
	}

	protected String clearExistingValues(String rangeToClear) throws GeneralSecurityException, IOException {
		Sheets sheetService = 
//		        GoogleSpreadSheetExporterUtils.getSheetService(applicationName, spreadsheetId, rangeToClear);
		getSheetService();
		
        ClearValuesResponse clearResponse = sheetService.spreadsheets()
            .values()
	        .clear(spreadsheetId, rangeToClear, new ClearValuesRequest())
	        .execute();
        
//        System.out.println( "cleared range: "+clearResponse.getClearedRange() );
        
        return clearResponse.getClearedRange();
    }
	
	protected void removeEmptyRows() throws GeneralSecurityException, IOException {
	    Sheets sheetService = getSheetService();
//sheetService.spreadsheets().sheets()	    
	}
    
    protected String putValuesToSpreadsheet(String rangeToPopulate, List<List<Object>> valuesToAdd) throws GeneralSecurityException, IOException {
    	Sheets sheetService = getSheetService();
    	
    	String putValuesToSpreadsheet = GoogleSpreadSheetExporterUtils.putValuesToSpreadsheet(sheetService, spreadsheetId, rangeToPopulate, valuesToAdd);
    	return putValuesToSpreadsheet;
    }
	
	protected List<List<Object>> getValuesFromSpreadSheet(String rangeToRetrieve) throws GeneralSecurityException, IOException {
		Sheets sheetService = getSheetService();
		ValueRange response = sheetService.spreadsheets().values()
              .get(spreadsheetId, rangeToRetrieve)
              .execute();      
		List<List<Object>> values = response.getValues();
		return values;
	}
	
	protected Sheets getSheetService() throws GeneralSecurityException, IOException {
		return GoogleSpreadSheetExporterUtils.getSheetService(applicationName, GoogleConfig.CLIENT_ID, GoogleConfig.CLIENT_SECRET);
	}
	

}
