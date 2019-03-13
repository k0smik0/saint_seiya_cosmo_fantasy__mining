package net.iubris.optimus_saint.crawler.main.exporter;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collection;
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
import com.google.api.services.sheets.v4.model.ValueRange;

import net.iubris.optimus_saint.crawler.model.SaintData;

public class GoogleSpreadSheetExporter implements Exporter<Void> {
	
	private final String spreadsheetId = "1b-ZlA_4nnLgFGfxhufL7kDJ8o7_kTwjuWRIAxmhhokA";
	private final String sheetName = "saints_traduzioni";
	private final String range = sheetName+"!A2:J";

	@Override
	public Void export(Collection<SaintData> saintDataCollection) {
		try {
			List<List<Object>> valuesFromSpreadSheet = getValuesFromSpreadSheet();
			
			if (valuesFromSpreadSheet == null || valuesFromSpreadSheet.isEmpty()) {
				System.out.println("No data found.");
			} else {
				System.out.println("Name, Major");
				for (@SuppressWarnings("rawtypes") List row : valuesFromSpreadSheet) {
				// Print columns A and E, which correspond to indices 0 and 4.
				System.out.printf("%s, %s\n", row.get(0), row.get(4));
				}
			}
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private List<List<Object>> getValuesFromSpreadSheet() throws GeneralSecurityException, IOException {
	// Build a new authorized API client service.
      final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      
      Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
              .setApplicationName(APPLICATION_NAME)
              .build();
      ValueRange response = service.spreadsheets().values()
              .get(spreadsheetId, range)
              .execute();
      List<List<Object>> values = response.getValues();
      return values;
	}
	
	
	/**
	 * internal to drive
	 */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
   private static final String TOKENS_DIRECTORY_PATH = "tokens";
	private static final String APPLICATION_NAME = "Saint Seiya Searcher Engine Translations";
	/**
    * Global instance of the scopes required by this quickstart.
    * If modifying these scopes, delete your previously saved tokens/ folder.
    */
   private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
//   private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	
   
   private static final String CLIENT_ID = "329557453456-sc3qimc2sisll4vh80ttuqo82ulhhlpc.apps.googleusercontent.com";
   private static final String CLIENT_SECRET = "OIMFazf9V42spDZ5wHV8cifI";
   
	
	/**
    * Creates an authorized Credential object.
    * @param HTTP_TRANSPORT The network HTTP Transport.
    * @return An authorized Credential object.
    * @throws IOException If the credentials.json file cannot be found.
    */
   private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
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
   
   /**
    * Prints the names and majors of students in a sample spreadsheet:
    * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
    */
   /*public static void main(String... args) throws IOException, GeneralSecurityException {
       // Build a new authorized API client service.
       final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
       
       Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
               .setApplicationName(APPLICATION_NAME)
               .build();
       ValueRange response = service.spreadsheets().values()
               .get(spreadsheetId, range)
               .execute();
       List<List<Object>> values = response.getValues();
       if (values == null || values.isEmpty()) {
           System.out.println("No data found.");
       } else {
           System.out.println("Name, Major");
           for (List row : values) {
               // Print columns A and E, which correspond to indices 0 and 4.
               System.out.printf("%s, %s\n", row.get(0), row.get(4));
           }
       }
   }*/


}
