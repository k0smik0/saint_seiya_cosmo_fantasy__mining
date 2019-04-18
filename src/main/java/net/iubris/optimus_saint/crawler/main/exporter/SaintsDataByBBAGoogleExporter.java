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
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	private static final Set<String> isAGoodGoldSaint = new HashSet<>();
	private static final Set<ClothKindEnum> usefulCloths = new HashSet<>();
	private static final Map<String,String> skillToRemapStringsMap = new HashMap<>();
	private static final Map<String, String> skillToColumnRangeMap = new HashMap<>();
	private static final Map<String, Integer> skillToPriorityMap = new HashMap<>();
	private static final String SHEET_NAME = "crusade skills";
	private static final String GLOBAL_RANGE= SHEET_NAME+"!A1:K";
	
	static {
	    isAGoodGoldSaint.add("God");
	    isAGoodGoldSaint.add("OCE");
	    isAGoodGoldSaint.add("Odin");
	    isAGoodGoldSaint.add("A.E.");
	    isAGoodGoldSaint.add("Aries Shion");
	    
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
        
        skillToPriorityMap.put("BBA",0);
        skillToPriorityMap.put("BT",1);
        skillToPriorityMap.put("Combo",2);
        skillToPriorityMap.put("Cosmo Charge",3);
        skillToPriorityMap.put("Damage Cut",4);
        skillToPriorityMap.put("HP Boost",5);
        skillToPriorityMap.put("PHYS ATK Boost",6);
        skillToPriorityMap.put("RES Boost",7);
        skillToPriorityMap.put("Recovery",8);
	}
	
    private boolean reallyAct = true;

	@Inject
	public SaintsDataByBBAGoogleExporter(Printer printer) {
		super(GoogleConfig.APPLICATION_NAME, GoogleConfig.SPREADSHEET_ID);
        this.printer = printer;
    }
	
	boolean prettyPrint = false;
	
	@Override
	public ExporterStatus export(Collection<SaintData> saintDataCollection, boolean overwrite) {		
	    Date now = new Date();
	    
		Map<Skill, List<SaintData>> mergedByStreamAndMerge = mergeByStreamAndMerge(saintDataCollection);
//		String mergedByStreamAndMergeToPrint = mapToString(mergedByStreamAndMerge);
		
		if (prettyPrint) {
		    printer.println("prettyPrint - begin");
		    /*Map<Skill, List<List<Object>>> skillWithSaintsMapByColumn = skillToSaintsMapToSkillToSkillWithSaintsMapByColumn(mergedByStreamAndMerge);
		    
		    String collect = skillWithSaintsMapByColumn.entrySet().stream()
                    .map(e->{
                        Skill skill = e.getKey();
                        String range = skillToColumnRangeMap.get(skill.getShortName());
                        List<List<Object>> value = e.getValue();
    
                        String s = skill.getShortName()+":: "+range+":: "
                                +value.get(0).stream().map(o->o+StringUtils.EMPTY).collect(Collectors.joining(","));
            
                        return s;
                    })
            .collect(Collectors.joining("\n\n"));*/
		    
		    String collect = mergedByStreamAndMerge.entrySet().stream()
		        .map(e->{
		            Skill skill = e.getKey();
                    String range = skillToColumnRangeMap.get(skill.getShortName());
                    List<SaintData> value = e.getValue();
                    
                    String s = skill.getShortName()+":: "+range+":: ";
                    s+=value.stream().map(sd->sd.name).collect(Collectors.joining(","));
                    
                    return s;
		        })
		        .collect(Collectors.joining("\n\n"));
            
    //      writeOnFile("mergedByStreamAndMergeToPrint",now, mergedByStreamAndMergeToPrint);
		    printer.println(collect+"\npretty print - end");
		}
		
		    
		 // this list of list is the structure google spreadsheet accepts
        List<List<Object>> skillNameOnHeaderWithSaintDataToJsonOnNextRows = transposeAndTransformSkillWithSaintDataToListOfListsOfString(mergedByStreamAndMerge);

		if (reallyAct) {
			try {
				String clearedExistingValues = clearExistingValues(GLOBAL_RANGE);
				printer.println("cleared: "+clearedExistingValues);
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		
		    try {
		        String range = GLOBAL_RANGE;
		        printer.println(range+": begin");
		        printer.print(range+": ");
                String putValuesToSpreadsheet = putValuesToSpreadsheet(range, skillNameOnHeaderWithSaintDataToJsonOnNextRows);
                printer.println(putValuesToSpreadsheet);
                printer.println(range+": end\n");
		    } catch (GeneralSecurityException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		return ExporterStatus.OK;
	}
	
	
	/*
	 * the filter and merge zone - begin
	 */
	private Map<Skill, List<SaintData>> mergeManually(Collection<SaintData> saintDataCollection) {
	    printer.println("mergeManually - begin");
	    Function<SaintData,Skill> byCrusadeSkill1Classifier = t -> t.skills.getCrusade1();
	    Map<Skill, List<SaintData>> saintsByCrusadeSkill1 = saintDataCollection.parallelStream()
            .filter(filterNotUsefulSaints)
            .map(crusadeSkill1NameFlattingRemapper)
            .sorted(comparatorBySaintSkill1PriorityDescending)
            .collect(Collectors.groupingBy(byCrusadeSkill1Classifier, 
                    concurrentSkipListMapWithSkillComparatorSupplier, Collectors.toList()));
	    
	    // only saints having skill2
	    Function<SaintData,Skill> byCrusadeSkill2Classifier = t -> t.skills.getCrusade2();
	    Map<Skill, List<SaintData>> saintsByCrusadeSkill2 = saintDataCollection.parallelStream()
            .filter(filterNotUsefulSaints)
            .map(crusadeSkill1NameFlattingRemapper)
            .sorted(comparatorBySaintSkill2PriorityDescending)
            .collect(Collectors.groupingBy(byCrusadeSkill2Classifier, 
                    concurrentSkipListMapWithSkillComparatorSupplier, Collectors.toList()));
	    
	    saintsByCrusadeSkill2.entrySet().parallelStream().forEach(e->{
//	        e.ge
	    });
	    
	    
	    printer.println("mergeManually - end");
	    return saintsByCrusadeSkill1;
	}
	
	private Map<Skill, List<SaintData>> mergeByStreamAndMerge(Collection<SaintData> saintDataCollection) {
	    printer.println("merge - begin");
	    Function<SaintData,Skill> byCrusadeSkill1Classifier = t -> t.skills.getCrusade1();
        

        Map<Skill, List<SaintData>> saintsByCrusadeSkill1 = saintDataCollection.parallelStream()
                .filter(filterNotUsefulSaints)
                .map(crusadeSkill1NameFlattingRemapper)
                .sorted(comparatorBySaintSkill1PriorityDescending)
                .collect(Collectors.groupingBy(byCrusadeSkill1Classifier, 
                        /*ConcurrentSkipListMap::new*/concurrentSkipListMapWithSkillComparatorSupplier, Collectors.toList()));
//        printer.println("saintsByCrusadeSkill1.skills: "
//                +saintsByCrusadeSkill1.keySet().stream().map(s->s.getShortName()).collect(Collectors.joining(", ")));
        printSkillsToSaints(saintsByCrusadeSkill1, "saintsByCrusadeSkill1.skills");

        Function<SaintData,Skill> byCrusadeSkill2Classifier = t -> t.skills.getCrusade2();
        Map<Skill, List<SaintData>> saintsByCrusadeSkill2ThenGlobal = saintDataCollection.parallelStream()
                .filter(filterNotUsefulSaints)
                .filter(sd->sd.skills.hasCrusade2())
                .map(crusadeSkill2NameFlattingRemapper)
                .sorted(comparatorBySaintSkill2PriorityDescending)
                .collect(Collectors.groupingBy(byCrusadeSkill2Classifier, 
                        /*ConcurrentSkipListMap::new*/concurrentSkipListMapWithSkillComparatorSupplier, Collectors.toList()));
        printSkillsToSaints(saintsByCrusadeSkill2ThenGlobal, "saintsByCrusadeSkill2.skills");
        
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
//        printer.println("post-merge: saintsByCrusadeSkill1.skills: "+saintsByCrusadeSkill1.keySet().stream().map(s->s.getShortName()).collect(Collectors.joining(", ")));
//        printer.println("post-merge: saintsByCrusadeSkill2.skills: "+saintsByCrusadeSkill2ThenGlobal.keySet().stream().map(s->s.getShortName()).collect(Collectors.joining(", ")));
        
        Map<Skill, List<SaintData>> toReturn = saintsByCrusadeSkill2ThenGlobal.entrySet().stream()
            .filter(e->!e.getKey().getShortName().isEmpty())        
            .sorted(comparatorEntryBySkillPriorityDescending)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, 
                    (v1,v2)->v1,/*ConcurrentSkipListMap::new*/concurrentSkipListMapWithSkillComparatorSupplier) );
        
        printer.println("\nskills: "+toReturn.keySet().stream().map(s->s.getShortName()).collect(Collectors.joining(",")));
    
        printer.println("merge - end");
        return toReturn;
	};
	private void printSkillsToSaints(Map<Skill, List<SaintData>> saintsByCrusadeSkill, String message) {
	    printer.println(message+":: "
                +saintsByCrusadeSkill.keySet().stream().map(s->s.getShortName()).collect(Collectors.joining(", ")));
        printer.println(message+".skills <-> saints: "+"\n"+
                    saintsByCrusadeSkill.entrySet().stream()
                        .map(e->{
                            Skill skill = e.getKey();
                            List<SaintData> value = e.getValue();
                            String s = skill.getShortName()+":: ";
                            s+=value.stream().map(sd->sd.name).collect(Collectors.joining(","));
                            return s;
                        })
                        .collect(Collectors.joining("\n"))
                        +"\n"
                );
	}
	private static final Supplier<Map<Skill, List<SaintData>>> concurrentSkipListMapWithSkillComparatorSupplier = new Supplier<Map<Skill,List<SaintData>>>() {
        @Override
        public Map<Skill, List<SaintData>> get() {
            Map<Skill,List<SaintData>> m = new ConcurrentSkipListMap<Skill,List<SaintData>>(comparatorSkillByPriorityDescending);
            return m;
        }
    };
    private final Predicate<SaintData> filterNotUsefulSaints = sd -> {
        // SSE
        boolean isSSE = sd.name.contains("SSE");
        if (isSSE) return true;
        
        // remove simple bronze saints, some silver, etc
        int overBronzeThreshold = 10004000; // this id is a threshold to exclude bronze, silver, etc
        int saintId = Integer.parseInt(sd.id);
        if (saintId < overBronzeThreshold) {
            return false;
        }        
        
        ClothKindEnum clothKindEnum = ClothKindEnum.valueOf( sd.stats.clothKind.max.value.toUpperCase()
                .replace(StringUtils.SPACE, StringUtils.UNDERSCORE) );
        
        // gold saint - we keep only god gold cloth saints
        if (ClothKindEnum.GOLD_SAINT.equals(clothKindEnum)) {
            if (isAGoodGold(sd.name)) {
                return true;
            }
//            printer.println("removing Gold Saint: "+sd.name);
            return false;
        }
        
        // default, by cloth - not keeping black saints, marina, etc
        return usefulCloths.contains(clothKindEnum);
    };
    // tipically: God, OCE, Athena Exclamation
    private static final boolean isAGoodGold(String saintName) {
        boolean present = isAGoodGoldSaint.stream().parallel().filter(s->saintName.contains(s)).findFirst().isPresent();
        return present;
    }
	private static final Function<SaintData, SaintData> crusadeSkill1NameFlattingRemapper = sd -> {
		crusadeSkillNameToShortNameRemapper(sd.skills.getCrusade1());
        return sd;
	};
	private static final Function<SaintData, SaintData> crusadeSkill2NameFlattingRemapper = sd -> {
		crusadeSkillNameToShortNameRemapper(sd.skills.getCrusade2());
        return sd;
	};
	private static final void crusadeSkillNameToShortNameRemapper(Skill crusadeSkill) {
        skillToRemapStringsMap.entrySet().parallelStream()
        .filter(n->crusadeSkill.getShortName().contains(n.getKey()) )
        .map(n->n.getValue()).findFirst().ifPresent(s->{
            crusadeSkill.setShortName(s);
        });
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
    private static final Comparator<SaintData> comparatorBySaintSkill1PriorityDescending = new Comparator<SaintData>() {
        @Override
        public int compare(SaintData o1, SaintData o2) {
            Skill o1Skill = o1.skills.getCrusade1();
            Integer o1Priority = skillToPriorityMap.get(o1Skill.getShortName());
            Skill o2Skill = o2.skills.getCrusade1();
            Integer o2Priority = skillToPriorityMap.get(o2Skill.getShortName());
            
            if (o1Priority.intValue() > o2Priority.intValue()) {
                return -1;
            }
            if (o1Priority.intValue() < o2Priority.intValue()) {
                return 1;
            }
            return 0;
        }
    };
    private static final Comparator<SaintData> comparatorBySaintSkill2PriorityDescending = new Comparator<SaintData>() {
        @Override
        public int compare(SaintData sd1, SaintData sd2) {
            Skill sd1Skill = sd1.skills.getCrusade2();
            Skill sd2Skill = sd2.skills.getCrusade2();
            
            if (sd1Skill.isExistant() /*getShortName().isEmpty()*/ && sd2Skill.isExistant() /*getShortName().isEmpty()*/) {
                return 0;
            }
            
            if (!sd1Skill.hasShortName() && sd2Skill.hasShortName()) {
                return 1;
            }
            if (sd2Skill.hasShortName() && !sd2Skill.hasShortName()) {
                return -1;
            }
            
            Integer sd1Priority = skillToPriorityMap.get(sd1Skill.getShortName());
            Integer sd2Priority = skillToPriorityMap.get(sd2Skill.getShortName());
            
            if (sd1Priority==null) {
                return -1;
            }
            if (sd2Priority==null) {
                return 1;
            }
            
            if (sd1Priority.intValue() > sd2Priority.intValue()) {
                return -1;
            }
            if (sd1Priority.intValue() < sd2Priority.intValue()) {
                return 1;
            }
            return 0;
        }
    };
    private static final Comparator<Entry<Skill,List<SaintData>>> comparatorEntryBySkillPriorityDescending = (e1, e2) -> {
        Skill skill1 = e1.getKey();
        Integer s1Priority = skillToPriorityMap.get(skill1.getShortName());
        Skill skill2 = e2.getKey();
        Integer s2Priority = skillToPriorityMap.get(skill2.getShortName());
        
        if (s1Priority.intValue() > s2Priority.intValue()) {
            return -1;
        }
        if (s1Priority.intValue() < s2Priority.intValue()) {
            return 1;
        }
        return 0;
    };
    private static final Comparator<Skill> comparatorSkillByPriorityDescending = new Comparator<Skill>() {
        @Override
        public int compare(Skill s1, Skill s2) {
            if (s1.getShortName().isEmpty() && s2.getShortName().isEmpty()) {
                return 0;
            }
            
            if (!s1.hasShortName()) {
                return -1;
            }
            if (!s2.hasShortName()) {
                return 1;
            }
            
            Integer s1Priority = skillToPriorityMap.get(s1.getShortName());
            Integer s2Priority = skillToPriorityMap.get(s2.getShortName());
            
            if (s1Priority==null) {
                return -1;
            }
            if (s2Priority==null) {
                return 1;
            }
            
            if (s1Priority.intValue() > s2Priority.intValue()) {
                return 1;
            }
            if (s1Priority.intValue() < s2Priority.intValue()) {
                return -1;
            }
            return 0;
        }
    };
    /*
     * the filter and merge zone - end
     */
		
    // only for console pretty print
	private static Map<Skill, List<List<Object>>> skillToSaintsMapToSkillToSkillWithSaintsMapByColumn(Map<Skill, List<SaintData>> merged) {
	        Function<Map.Entry<Skill, List<SaintData>>, List<List<Object>>> valueMapper = t -> {
                List<Object> skillWithSaints = new ArrayList<>();
                Skill skill = t.getKey();
                String skillAsString = "{'name':'" + skill.getName()+ "', 'imageSmall':'" + skill.imageSmall + "'}";
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
					String saintToJson = SaintDataToJSON.SheetCrusadeSkill.saintToJson(sd);
					return saintToJson;
				})
				.collect(Collectors.toList());
        return saintToJsonList;
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
	
	private List<List<Object>> transposeAndTransformSkillWithSaintDataToListOfListsOfString(Map<Skill, List<SaintData>> skillsToSaintsListMap) {
	    printer.println("- transposeAndTransformSkillWithSaintDataToString: begin");
	    List<List<Object>> externalList = new ArrayList<>();
	    
	    externalList.add(new ArrayList<Object>() );
	    List<Object> header = externalList.get(0);
	    header.add("index - do not touch");
	    
	    Set<Entry<Skill, List<SaintData>>> entrySet = skillsToSaintsListMap.entrySet();
	    
	    int columnsIndex = 1;
	    
	    for (Entry<Skill, List<SaintData>> entry : entrySet) {
            Skill skill = entry.getKey();
            header.add( skill.getShortName());
            
            List<SaintData> saintsListPerSkill = entry.getValue();
            
            // eventually increment the rows until the max of values, 
            // that is the max the new column could reach
            int futureColumnSize = saintsListPerSkill.size();
            int externalListSize = externalList.size();
//            printer.println("skill: "+skill.name+", saints: "+futureColumnSize);
            if (futureColumnSize>externalListSize) {
//                printer.print("futureColumnSize:"+futureColumnSize+" > externalListSize"+":"+externalListSize+" -> ");
                int diff = futureColumnSize-externalListSize+1;
                for (int d=1;d<=diff;d++) {
                    externalList.add( new ArrayList<Object>() );
                }
//                printer.println("added "+diff+" lists - externalList.size:"+externalList.size());
            }

            // here the business
            for (int i=0;i<saintsListPerSkill.size();i++) {
                int rowIndex = i+1;
                List<Object> row = externalList.get(rowIndex);
//                printer.print("row:"+(rowIndex)+":: ");
                if (row.size()==0) {
//                    printer.print("adding "+(rowIndex)+" at externalList["+(rowIndex)+"][0] - ");
                    row.add(""+rowIndex);
//                    printer.print("["+rowIndex+"][0] ");
                }
                SaintData saintData = saintsListPerSkill.get(i);
//                String saintName = saintsListPerSkill.get(i).name;
                String saintDataAsJson = SaintDataToJSON.SheetCrusadeSkill.saintToJson(saintData);
                boolean isJsonValid = SaintDataToJSON.isJSONValid(saintDataAsJson);
//                printer.print("adding "+saintName+" at externalList["+rowIndex+"]["+columnsIndex+"] ("+skill.name+")");
//                printer.print(" -- rowSize:"+row.size());
                String data = "{\"error\":\"json not valid for "+saintData.name+"\"}";
                if (!isJsonValid) {
                    printer.println("   * json not valid for "+saintData.name+" *");
                    printer.println("not valid: "+saintDataAsJson);
                } else {
                    data = saintDataAsJson;
                }
                if (row.size() < columnsIndex) {
                    row.add(data);
                } else {
                    row.add(columnsIndex, data);
                }
//                printer.print("["+rowIndex+"]["+columnsIndex+"] ");
//                printer.println(".");
            }
//            printer.println("");
            
            columnsIndex++;
        }
	    
	    header.add("last - do not touch");
	    
	    /*externalList.stream().forEach(l->{
	        l.forEach(ll->System.out.print("# "));
	        System.out.println("");
	    });*/
	    
	    printer.println("- transposeAndTransformSkillWithSaintDataToString: end");
	    
	    return externalList;
	}

}
