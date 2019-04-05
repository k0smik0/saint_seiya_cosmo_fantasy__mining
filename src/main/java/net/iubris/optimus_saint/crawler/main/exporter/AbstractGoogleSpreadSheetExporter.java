package net.iubris.optimus_saint.crawler.main.exporter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.ClearValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class AbstractGoogleSpreadSheetExporter {
	
//	private static final String CLIENT_ID = "329557453456-sc3qimc2sisll4vh80ttuqo82ulhhlpc.apps.googleusercontent.com";
//	private static final String CLIENT_SECRET = "OIMFazf9V42spDZ5wHV8cifI";
	
	protected final String applicationName;
	protected final String spreadsheetId;
	
	public AbstractGoogleSpreadSheetExporter(String applicationName, String spreadsheetId) {
		this.applicationName = applicationName;
		this.spreadsheetId = spreadsheetId;
	}

	protected String clearExistingValues(String rangeToClear) throws GeneralSecurityException, IOException {
		Sheets sheetService = GoogleSpreadSheetExporterUtils.getSheetService(applicationName, spreadsheetId, rangeToClear);
		
        ClearValuesResponse clearResponse = sheetService.spreadsheets().values()
	        .clear(spreadsheetId, rangeToClear, new ClearValuesRequest())
	        .execute();
        
        System.out.println( "cleared range: "+clearResponse.getClearedRange() );
        
        return clearResponse.getClearedRange();
    }
    
    protected boolean putValuesToSpreadsheet(List<List<Object>> valuesToAdd, String rangeToPopulate) throws GeneralSecurityException, IOException {
    	Sheets sheetService = getSheetService();
//        String first = "=Rows($A$1:A2)";
//        List<Object> rowAsList = Arrays.asList("Total", "=E1+E4");
        ValueRange appendBody = new ValueRange()
                .setValues(valuesToAdd);
        AppendValuesResponse appendResult = sheetService.spreadsheets().values()
                .append(spreadsheetId, rangeToPopulate, appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();

        ValueRange totalSent = appendResult.getUpdates().getUpdatedData();
        
        System.out.println( "updated range: "+appendResult.getUpdates().getUpdatedRange() );
        
        boolean OK = valuesToAdd.size() == totalSent.getValues().size();
        
        return OK;
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
