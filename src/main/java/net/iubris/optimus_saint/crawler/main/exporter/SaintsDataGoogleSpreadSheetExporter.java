package net.iubris.optimus_saint.crawler.main.exporter;

import static net.iubris.optimus_saint.common.StringUtils.COLONS;
import static net.iubris.optimus_saint.common.StringUtils.COMMA;
import static net.iubris.optimus_saint.common.StringUtils.DASH;
import static net.iubris.optimus_saint.common.StringUtils.MARKS;
import static net.iubris.optimus_saint.common.StringUtils.NEW_LINE;
import static net.iubris.optimus_saint.common.StringUtils.PIPE;
import static net.iubris.optimus_saint.common.StringUtils.QUOTE;
import static net.iubris.optimus_saint.common.StringUtils.SPACE;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.inject.Inject;

import net.iubris.optimus_saint.common.StringUtils;
import net.iubris.optimus_saint.crawler.bucket.SaintsDataBucket;
import net.iubris.optimus_saint.crawler.main.exporter.Exporter.ExporterStatus;
import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.saints.skills.Skill;
import net.iubris.optimus_saint.crawler.utils.Printer;

public class SaintsDataGoogleSpreadSheetExporter extends AbstractGoogleSpreadSheetExporter<ExporterStatus> {
	
	private static final String SHEET_NAME = "saints";
	private static final String RANGE_TO_POPULATE = SHEET_NAME+"!A1:O";
	private static final String RANGE_TO_CLEAR = SHEET_NAME+"!A2:O";
	
	private final SaintsDataBucket saintsDataBucket;
    private final Printer printer;
	
	@Inject
	public SaintsDataGoogleSpreadSheetExporter(SaintsDataBucket saintsDataBucket, Printer printer) {
		super(GoogleConfig.APPLICATION_NAME, GoogleConfig.SPREADSHEET_ID);
        this.saintsDataBucket = saintsDataBucket;
        this.printer = printer;
    }

    @Override
	public ExporterStatus export(Collection<SaintData> saintDataCollection, boolean overwrite) {
		try {
		    if (overwrite) {
                clearExistingValues( RANGE_TO_CLEAR );
            }
		    
			List<List<Object>> rowsFromSpreadsheet = getValuesFromSpreadSheet( RANGE_TO_POPULATE );
			
			if (rowsFromSpreadsheet == null || rowsFromSpreadsheet.isEmpty()) {
				printer.println("No data found.");
				return ExporterStatus.KO;
			}
			
			int lastRow = rowsFromSpreadsheet.size();
			int indexFromStartInt = lastRow-1;
			AtomicInteger indexFromStartWhich = new AtomicInteger(indexFromStartInt);
			
			// get(1) -> id
			Set<String> alreadyPresentIdsSet = rowsFromSpreadsheet.stream()
			        .filter(r->r.size()>0)
			        .map(r->(String)r.get(1)).collect(Collectors.toSet());
//			printer.println("alreadyPresentIdsSet.size: "+alreadyPresentIdsSet.size());
//			printer.println(alreadyPresentIdsSet.stream().sorted().collect(Collectors.joining(", ")));
			
		    List<List<Object>> valuesToAdd = saintsDataBucket.getSaints()/* .getIdToSaintsMap().entrySet()*/
                .stream()
                .filter(sd -> {
                    if (overwrite) {
                        return true;
                    }
                    if (alreadyPresentIdsSet.contains(sd.id)) {
                        printer.println(sd.id+" "+sd.name+" already present - skip");
                        return false;
                    }
                    return true;
                })
    			.sorted(Comparator.comparing(SaintData::getId))
    			.map(sd->saintDataToList(indexFromStartWhich, sd))
    			.collect(Collectors.toList());
//		    printer.println("valuesToAdd.size: "+valuesToAdd.size());
//		    printer.println("valuesToAdd: "+valuesToAdd.stream().map(l->l.get(1)+"").collect(Collectors.joining(", ")));
		    
		    if (valuesToAdd.size()==0) {
		        printer.println("valuesToAdd.size: 0 - nothing to add");
		        return ExporterStatus.OK;
		    }

		    boolean puttedALl = putValuesToSpreadsheet(valuesToAdd, RANGE_TO_POPULATE);
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
        // index
        list.add(index.incrementAndGet());
        // id
        list.add(sd.id);
        // name
        list.add("{"+"\"name\":\""+sd.name+"\",\"imageSmall\":\""+sd.imageSmall+"\"}");
        // type
        list.add(sd.type.name().toLowerCase());
        // lane
        list.add(sd.lane.name().toLowerCase());
        list.add(DescriptionBuilder.skillToJsonString(sd.skills.first));
        list.add(DescriptionBuilder.skillToJsonString(sd.skills.second));
        list.add(DescriptionBuilder.skillToJsonString(sd.skills.third));
        list.add(DescriptionBuilder.skillToJsonString(sd.skills.fourth));
        list.add(DescriptionBuilder.skillToJsonString(sd.skills.getSeventhSense()));
        list.add(DescriptionBuilder.skillToJsonString(sd.skills.getCrusade1()));
        list.add(DescriptionBuilder.skillToJsonString(sd.skills.getCrusade2()));
        list.add(sd.keywords.stream().sorted().collect(Collectors.joining(StringUtils.COMMA+StringUtils.SPACE)));
        
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
            s=s.replace(QUOTE, PIPE);
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
   
}
