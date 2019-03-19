package net.iubris.optimus_saint.crawler.main.exporter;

import static net.iubris.optimus_saint.common.StringUtils.COLONS;
import static net.iubris.optimus_saint.common.StringUtils.COMMA;
import static net.iubris.optimus_saint.common.StringUtils.DASH;
import static net.iubris.optimus_saint.common.StringUtils.MARKS;
import static net.iubris.optimus_saint.common.StringUtils.NEW_LINE;
import static net.iubris.optimus_saint.common.StringUtils.QUOTE;
import static net.iubris.optimus_saint.common.StringUtils.SPACE;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.inject.Inject;

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
import com.google.api.services.sheets.v4.model.ValueRange;

import net.iubris.optimus_saint.common.StringUtils;
import net.iubris.optimus_saint.crawler.bucket.SaintsDataBucket;
import net.iubris.optimus_saint.crawler.main.exporter.Exporter.ExporterStatus;
import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.saints.skills.Skill;

public class GoogleSpreadSheetExporter implements Exporter<ExporterStatus> {
	
	private static final String SPREADSHEET_ID = "1b-ZlA_4nnLgFGfxhufL7kDJ8o7_kTwjuWRIAxmhhokA";
	private static final String SHEET_NAME = "saints";
	private static final String RANGE = SHEET_NAME+"!A1:O";
	
	private final SaintsDataBucket saintsDataBucket;
	
	@Inject
	public GoogleSpreadSheetExporter(SaintsDataBucket saintsDataBucket) {
        this.saintsDataBucket = saintsDataBucket;
    }

    @Override
	public ExporterStatus export(Collection<SaintData> saintDataCollection) {
		try {
			List<List<Object>> rowsFromSpreadsheet = getValuesFromSpreadSheet( getSheetService() );
			
			if (rowsFromSpreadsheet == null || rowsFromSpreadsheet.isEmpty()) {
				System.out.println("No data found.");
				return ExporterStatus.KO;
			}
			
			int lastRow = rowsFromSpreadsheet.size();
			int indexFromStartInt = lastRow-1;
			AtomicInteger indexFromStartWhich = new AtomicInteger(indexFromStartInt);
			
			// get(0) -> id
			Set<String> idAlreadyPresentSet = rowsFromSpreadsheet.stream().map(r->(String)r.get(0)).collect(Collectors.toSet());
			
		    List<List<Object>> valuesToAdd = saintsDataBucket.getSaints()/* .getIdToSaintsMap().entrySet()*/
			.stream()
			.filter(sd->!idAlreadyPresentSet.contains( sd.id ) )
			.sorted(Comparator.comparing(SaintData::getId))
			.map(sd->saintDataToList(indexFromStartWhich, sd))
			.collect(Collectors.toList());
			

		    boolean puttedALl = putValuesToSpreadsheet(getSheetService(), valuesToAdd);
		    if (puttedALl) {
		        return ExporterStatus.OK;
		    }
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return ExporterStatus.KO;
	}
    
    private static List<Object> saintDataToList(AtomicInteger index, SaintData sd) {
        List<Object> list = new ArrayList<>();
        list.add(index.incrementAndGet());
        list.add(sd.id);
        list.add("{"+"\"name\":\""+sd.name+"\",\"imageSmall\":\""+sd.imageSmall+"\"}");
        list.add(sd.type.name().toLowerCase());
        list.add(sd.lane.name().toLowerCase());
        list.add(DescriptionBuilder.skillToJsonString(sd.skills.first));
        list.add(DescriptionBuilder.skillToJsonString(sd.skills.second));
        list.add(DescriptionBuilder.skillToJsonString(sd.skills.third));
        list.add(DescriptionBuilder.skillToJsonString(sd.skills.fourth));
        list.add(DescriptionBuilder.skillToJsonString(sd.skills.getSeventhSense()));
        list.add(DescriptionBuilder.skillToJsonString(sd.skills.getCrusade()));
        list.add(sd.keywords.stream().sorted().collect(Collectors.joining(StringUtils.COMMA+StringUtils.EMPTY)));
        
        return list;
    }
    private static class DescriptionBuilder {
    
        private static String skillToJsonString(Skill skill) {
            String descriptionEN = normalizeDescription(skill);
            String s = b 
                        +m+name+m+t+m+skill.name+m+c
                        +m+description+m+t
                        +b;
            if (hasDescription(descriptionEN)) {
                            s+=m+EN+m+t+m+descriptionEN+m+c;
                            s+=m+IT+m+t+m+MISSING+m;
            } else {
                            s+=m+EN+m+t+m+DASH+m+c;
                            s+=m+IT+m+t+m+DASH+m;
            }
                      s+=e+c;
                      s+=m+imageSmall+m+t+m+skill.imageSmall+m
                     +e;
    //        s=s.replace(QUOTE, MARKS);
            return s;
        }
        
        private static String normalizeDescription(Skill skill) {
            return skill.description.trim()
//                    .replace(QUOTE, EMPTY)
                    .replace(MARKS, QUOTE)
                    .replace(NEW_LINE, SPACE);
        }
    
        private static boolean hasDescription(String descriptionEN) {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(descriptionEN)) {
                return true;
            }
            return false;
        }
        
        private static final String m = MARKS;
        private static final String c = COMMA;
        private static final String t = COLONS;
        private static final String b = "{";
        private static final String e = "}";
        private static final String name = "name";
        private static final String description = "description";
        private static final String imageSmall = "imageSmall";
        private static final String EN = "en";
        private static final String IT = "it";
        private static final String MISSING = "MISSING";
    }

    
    private static boolean putValuesToSpreadsheet(Sheets sheetService, List<List<Object>> valuesToAdd) throws IOException {
//        String first = "=Rows($A$1:A2)";
//        List<Object> rowAsList = Arrays.asList("Total", "=E1+E4");
        ValueRange appendBody = new ValueRange()
                .setValues(valuesToAdd);
        AppendValuesResponse appendResult = sheetService.spreadsheets().values()
                .append(SPREADSHEET_ID, RANGE, appendBody)
                .setValueInputOption("USER_ENTERED")
                .setInsertDataOption("INSERT_ROWS")
                .setIncludeValuesInResponse(true)
                .execute();

        ValueRange totalSent = appendResult.getUpdates().getUpdatedData();
        
//        System.out.println( "updated cells: "+appendResult.getUpdates().getUpdatedCells() );
        System.out.println( "updated range: "+appendResult.getUpdates().getUpdatedRange() );
        
//        totalSent.getValues().stream().forEach(o->{
//            System.out.println(o.get(0)+" "+o.get(1)+" "+o.get(2));
//        });
        
        boolean OK = valuesToAdd.size() == totalSent.getValues().size();
        
        return OK;
        
//        assertThat(total.getValues().get(0).get(1)).isEqualTo("65");
        
//        new BatchUpdateValuesRequest().p
//        sheetService.spreadsheets(). get(spreadsheetId, range).
    }
	
	private static List<List<Object>> getValuesFromSpreadSheet(Sheets sheetService) throws GeneralSecurityException, IOException {
      ValueRange response = sheetService.spreadsheets().values()
              .get(SPREADSHEET_ID, RANGE)
              .execute();      
      List<List<Object>> values = response.getValues();
      return values;
	}
	
	private static Sheets getSheetService() throws GeneralSecurityException, IOException {
	    NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	    Sheets sheetService = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
        .setApplicationName(APPLICATION_NAME)
        .build();
	    return sheetService;
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
