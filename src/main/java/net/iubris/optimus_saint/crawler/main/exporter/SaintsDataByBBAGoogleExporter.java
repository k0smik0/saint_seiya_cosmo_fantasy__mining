package net.iubris.optimus_saint.crawler.main.exporter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.inject.Inject;

import net.iubris.optimus_saint.common.StringUtils;
import net.iubris.optimus_saint.crawler.main.exporter.Exporter.ExporterStatus;
import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.saints.skills.Skill;
import net.iubris.optimus_saint.crawler.model.saints.stats.literal.ClothKind.ClothKindEnum;
import net.iubris.optimus_saint.crawler.utils.Printer;

public class SaintsDataByBBAGoogleExporter extends AbstractGoogleSpreadSheetExporter<ExporterStatus> {

	private final Printer printer;
	
	private static final DateFormat dateFormat__YYYMMDDHHmm = new SimpleDateFormat("YYYYMMddHHmm");
	
	private static final Set<ClothKindEnum> usefulCloths = new HashSet<>();
	private static final Map<String,String> skillToRemapStringsMap = new HashMap<>();
	private static final Map<String, String> skillToColumnRangeMap = new HashMap<>();
	private static final String SHEET_NAME = "crusade skills - private";
	static {
		usefulCloths.addAll( Arrays.asList( ClothKindEnum.values() ) );
        usefulCloths.remove(ClothKindEnum.NO_CLOTH);
        usefulCloths.remove(ClothKindEnum.BLACK_SAINT);
        usefulCloths.remove(ClothKindEnum.MARINA);
		
		skillToRemapStringsMap.put("Score Plus BBA","BBA");
		skillToRemapStringsMap.put("Score Plus BT","BT");
		skillToRemapStringsMap.put("Combo Plus","Combo");
		skillToRemapStringsMap.put("Cosmo Charge","Cosmo Charge");
		skillToRemapStringsMap.put("Damage Cut","Damage Cut");
		skillToRemapStringsMap.put("HP Boost","HP Boost");
		skillToRemapStringsMap.put("PHYS ATK Boost","PHYS ATK Boost");
		skillToRemapStringsMap.put("RES Boost","RES Boost");
		skillToRemapStringsMap.put("Recovery","Recovery");
		
		skillToColumnRangeMap.put("BBA",SHEET_NAME+"!A1:BG1");
        skillToColumnRangeMap.put("BT",SHEET_NAME+"!A2:BG2");
        skillToColumnRangeMap.put("Combo",SHEET_NAME+"!A3:BG3");
        skillToColumnRangeMap.put("Cosmo Charge",SHEET_NAME+"!A4:BG4");
        skillToColumnRangeMap.put("Damage Cut",SHEET_NAME+"!A5:BG5");
        skillToColumnRangeMap.put("HP Boost",SHEET_NAME+"!A6:BG6");
        skillToColumnRangeMap.put("PHYS ATK Boost",SHEET_NAME+"!A7:BG7");
        skillToColumnRangeMap.put("RES Boost",SHEET_NAME+"!A8:BG8");
        skillToColumnRangeMap.put("Recovery",SHEET_NAME+"!A9:BG9");
	}
	
    private boolean reallyAct = true;

	@Inject
	public SaintsDataByBBAGoogleExporter(Printer printer) {
		super(GoogleConfig.APPLICATION_NAME, GoogleConfig.SPREADSHEET_ID);
        this.printer = printer;
    }
	
	@Override
	public ExporterStatus export(Collection<SaintData> saintDataCollection, boolean overwrite) {		
	    Date now = new Date();
	    
		Map<Skill, List<SaintData>> mergedByStreamAndMerge = mergeByStreamAndMerge(saintDataCollection);
//		String mergedByStreamAndMergeToPrint = mapToString(mergedByStreamAndMerge);
		// this list of list is the structure google spreadsheet accepts
		Map<Skill, List<List<Object>>> skillWithSaintsMapByColumn = skillToSaintsMapToSkillToSkillWithSaintsMapByColumn(mergedByStreamAndMerge);

		if (reallyAct) {
			try {
				String clearedExistingValues = clearExistingValues(SHEET_NAME+"!A1:BG");
				printer.println("cleared: "+clearedExistingValues);
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
    		skillWithSaintsMapByColumn.forEach((skill,saintDataList)->{
    		    String range = skillToColumnRangeMap.get(skill.name);
    		    try {
    		        printer.println(range+": begin");
    		        printer.print(range+": ");
                    String putValuesToSpreadsheet = putValuesToSpreadsheet(range, saintDataList);
                    printer.println(putValuesToSpreadsheet);
                    printer.println(range+": end\n");
    		    } catch (GeneralSecurityException e) {
    				e.printStackTrace();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		});
		} else {
    		String collect = skillWithSaintsMapByColumn.entrySet().stream()
    		        .map(e->{
    		            Skill skill = e.getKey();
    		            String range = skillToColumnRangeMap.get(skill.name);
    		            List<List<Object>> value = e.getValue();
    
    		            String s = skill.name+":: "+range+":: "
    		                    +value.get(0).stream().map(o->o+StringUtils.EMPTY).collect(Collectors.joining(","));
    		
    		            return s;
    		        })
    		.collect(Collectors.joining("\n\n"));
    		
    //		writeOnFile("mergedByStreamAndMergeToPrint",now, mergedByStreamAndMergeToPrint);
    		System.out.println(collect);
		}
		
		return ExporterStatus.OK;
	}
	
	private static Map<Skill, List<SaintData>> mergeByStreamAndMerge(Collection<SaintData> saintDataCollection) {
	    Function<SaintData,Skill> byCrusadeSkill1Classifier = t -> t.skills.getCrusade1();
        Function<SaintData,Skill> byCrusadeSkill2Classifier = t -> t.skills.getCrusade2();
                
        Map<Skill, List<SaintData>> saintsByCrusadeSkill1 = saintDataCollection.parallelStream()
                .filter(filterNotUsefulSaints)
                .map(crusadeSkill1NameFlattingRemapper)
                .collect(Collectors.groupingBy(byCrusadeSkill1Classifier, ConcurrentSkipListMap::new, Collectors.toList()));
        
        Map<Skill, List<SaintData>> saintsByCrusadeSkill2ThenGlobal = saintDataCollection.parallelStream()
                .filter(filterNotUsefulSaints)
                .map(crusadeSkill2NameFlattingRemapper)
                .collect(Collectors.groupingBy(byCrusadeSkill2Classifier, ConcurrentSkipListMap::new, Collectors.toList()));
        
        BiFunction<? super List<SaintData>, ? super List<SaintData>, ? extends List<SaintData>> valuesRemappingFunction = (v1, v2) -> {
            Set<SaintData> set = new TreeSet<>(saintsComparatorByIdDescending);
            set.addAll(v1);
            set.addAll(v2);
            return new ArrayList<>(set);
        };
        saintsByCrusadeSkill1
            .entrySet()
            .parallelStream()
            .forEach(e -> saintsByCrusadeSkill2ThenGlobal.merge(e.getKey(), e.getValue(), valuesRemappingFunction));
        
        Map<Skill, List<SaintData>> toReturn = saintsByCrusadeSkill2ThenGlobal.entrySet().parallelStream()
        .filter(e->!e.getKey().name.isEmpty())        
//        .map(skillNameRemapper)
        .collect(Collectors.toMap(Map.Entry::getKey, 
//        		e->e.getValue().stream().sorted(saintsComparatorByIdDescending).collect(Collectors.toList())
        		Map.Entry::getValue
        		))
        ;
        return toReturn;
	};	
	
	// God, OCE, Athena Exclamation
    private static final boolean isAGoodGold(String saintName) {
        return saintName.contains("God") || saintName.contains("OCE") || saintName.contains("Odin") || saintName.contains("A.E.") || saintName.equalsIgnoreCase("Aries Shion");
    }
    private static final Predicate<SaintData> filterNotUsefulSaints = sd -> {
        // SSE
        boolean isSSE = sd.name.contains("SSE");
        if (isSSE) return true;
        
        // remove simple bronze saints, some silver, etc
        int overBronzeThreshold = 10004000; // this id is a threshold to exclude bronze, silver, etc
        int saintId = Integer.parseInt(sd.id);
        if (saintId < overBronzeThreshold) {
            return false;
        }        
        
        ClothKindEnum clothKindEnum = ClothKindEnum.valueOf( sd.stats.clothKind.max.value.toUpperCase().replace(StringUtils.SPACE, StringUtils.UNDERSCORE) );
        
        // God Gold Cloth
        boolean isGodGoldCloth = ClothKindEnum.GOLD_SAINT.equals(clothKindEnum) && isAGoodGold(sd.name);
        if (isGodGoldCloth) return true;
        if (ClothKindEnum.GOLD_SAINT.equals(clothKindEnum) && !isAGoodGold(sd.name)) {
            System.out.println("removing Gold Saint: "+sd.name);
            return false;
        }        
        
        // default, by cloth
        return usefulCloths.contains(clothKindEnum);
    };
	private static final Function<SaintData, SaintData> crusadeSkill1NameFlattingRemapper = sd -> {
		crusadeSkillNameFlattingRemapper(sd.skills.getCrusade1());
        return sd;
	};
	private static final Function<SaintData, SaintData> crusadeSkill2NameFlattingRemapper = sd -> {
		crusadeSkillNameFlattingRemapper(sd.skills.getCrusade2());
        return sd;
	};
	private static final void crusadeSkillNameFlattingRemapper(Skill crusadeSkill) {
        skillToRemapStringsMap.entrySet().parallelStream()
        .filter(n->crusadeSkill.name.contains(n.getKey()) )
        .map(n->n.getValue()).findFirst().ifPresent(s->crusadeSkill.name = s);
	}
	private static final Comparator<SaintData> saintsComparatorByIdDescending = new Comparator<SaintData>() {
        @Override
        public int compare(SaintData o1, SaintData o2) {
            long o1L = Long.parseLong(o1.id);
            long o2L = Long.parseLong(o2.id);
            if (o1L > o2L) return -1;
            if (o1L < o2L) return 1;
            return 0;
        }
    };
		
	private static Map<Skill, List<List<Object>>> skillToSaintsMapToSkillToSkillWithSaintsMapByColumn(Map<Skill, List<SaintData>> merged) {
//	    List<List<Object>> skillWithSaintsByColumns = 
	        Function<Map.Entry<Skill, List<SaintData>>, List<List<Object>>> valueMapper = t -> {
                List<Object> skillWithSaints = new ArrayList<>();
                Skill skill = t.getKey();
                String skillAsString = "{'name':'" + skill.name + "', 'imageSmall':'" + skill.imageSmall + "'}";
                skillWithSaints.add(skillAsString);
                List<String> saintsToJsonList = saintsToJsonList(t.getValue());
                skillWithSaints.addAll(saintsToJsonList);
                
                List<List<Object>> externalSkillWithSaints = new ArrayList<>();
                externalSkillWithSaints.add(skillWithSaints);
                
                return externalSkillWithSaints;
            };
            
            Map<Skill, List<List<Object>>>result = merged.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, e->valueMapper.apply(e)));
	    
            return result;
	}
	private static final List<String> saintsToJsonList(List<SaintData> saints) {
        List<String> saintToJsonList = saints.stream()
				.sorted(saintsComparatorByIdDescending)
				.map(sd -> {
					String saintToJson = saintToJson(sd);
					return saintToJson;
				})
				.collect(Collectors.toList());
        return saintToJsonList;
	}
	private static final String saintToJson(SaintData saintData) {
	    String s = "";
	    s+="{"
	            +"'name':'"+saintData.name.replace(StringUtils.COMMA, StringUtils.EMPTY)+"'"+","
	            +"'crusade_skill_1':{"
	                +"'name':'"+saintData.skills.getCrusade1().name+"',"
	                +"'description':'"+saintData.skills.getCrusade1().description
	                    .replace(StringUtils.NEW_LINE, StringUtils.SPACE)
	                    .replace(StringUtils.QUOTE, StringUtils.PIPE)+"',"
                    +"'imageSmall':'"+saintData.skills.getCrusade1().imageSmall+"'"
                +"}";
	    if (saintData.skills.hasCrusade2()) {
    	        s+="'crusade_skill_2':{"
                        +"'name':'"+saintData.skills.getCrusade2().name+"',"
                        +"'description':'"+saintData.skills.getCrusade2().description
                            .replace(StringUtils.NEW_LINE, StringUtils.SPACE)
                            .replace(StringUtils.QUOTE, StringUtils.PIPE)+"',"
                        +"'imageSmall':'"+saintData.skills.getCrusade2().imageSmall+"'"
                    +"}";
	    }
	    s+="}";
	    return s;
	}
	
	private static void writeOnFile(String fileName, Date date, String stringToPrint) {
	    String realFileName = dateFormat__YYYMMDDHHmm.format(date)+"__"+fileName+".txt";
	    try (PrintWriter printWriter = new PrintWriter(realFileName);) {
            printWriter.write(stringToPrint);
            System.out.println("wrote: "+realFileName);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	}

}
