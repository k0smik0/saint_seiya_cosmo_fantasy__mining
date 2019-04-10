package net.iubris.optimus_saint.crawler.main.exporter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.AppendValuesResponse;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.ClearValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

public class GoogleSpreadSheetExporterUtils {
	
	protected static String clearExistingValues(Sheets sheetService, String spreadsheetId, String rangeToClear) throws IOException {
        ClearValuesResponse clearResponse = sheetService.spreadsheets().values()
        .clear(spreadsheetId, rangeToClear, new ClearValuesRequest())
        .execute();
        
        System.out.println( "cleared range: "+clearResponse.getClearedRange() );
        
        return clearResponse.getClearedRange();
    }
    
    protected static boolean putValuesToSpreadsheet(Sheets sheetService, String spreadsheetId, String rangeToPopulate, List<List<Object>> valuesToAdd) throws IOException {
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
	
	protected static List<List<Object>> getValuesFromSpreadSheet(Sheets sheetService, String spreadsheetId, String rangeToRetrieve) throws GeneralSecurityException, IOException {
      ValueRange response = sheetService.spreadsheets().values()
              .get(spreadsheetId, rangeToRetrieve)
              .execute();      
      List<List<Object>> values = response.getValues();
      return values;
	}
	
	protected static Sheets getSheetService(String applicationName, String CLIENT_ID, String CLIENT_SECRET) throws GeneralSecurityException, IOException {
	    NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	    Sheets sheetService = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, CLIENT_ID, CLIENT_SECRET))
        .setApplicationName(applicationName)
        .build();
	    return sheetService;
	}
	
	
	/**
	 * internal to drive
	 */
	protected static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	protected static final String TOKENS_DIRECTORY_PATH = "tokens";
//	protected static final String APPLICATION_NAME = "Saint Seiya Searcher Engine Translations";
	/**
    * Global instance of the scopes required by this quickstart.
    * If modifying these scopes, delete your previously saved tokens/ folder.
    */
   protected static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
//   protected static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	
   
	/**
    * Creates an authorized Credential object.
    * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @param CLIENT_ID 
	 * @param CLIENT_SECRET 
    * @return An authorized Credential object.
    * @throws IOException If the credentials.json file cannot be found.
    */
   private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, String CLIENT_ID, String CLIENT_SECRET) throws IOException {
       // Load client secrets.
       /*InputStream in = SheetsQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
       GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

       // Build flow and trigger user authorization request.
       GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
               HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
               .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
               .setAccessType("offline")
               .build();*/
   	
   	GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, SCOPES)
       	.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
       	.setAccessType("offline")
       	.build();
       
       LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
       return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
   }


}
