package net.iubris.optimus_saint.crawler.main.exporter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
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

	private Printer printer;
	
	private static final Set<ClothKindEnum> usefulCloths = new HashSet<>();
	private static final Map<String,String> skillToRemapStringsMap = new HashMap<>();
	static {
		usefulCloths.addAll( Arrays.asList( ClothKindEnum.values() ) );
        usefulCloths.remove(ClothKindEnum.NO_CLOTH);
        usefulCloths.remove(ClothKindEnum.BLACK_SAINT);
        usefulCloths.remove(ClothKindEnum.MARINA);
		
		skillToRemapStringsMap.put("BBA","BBA");
		skillToRemapStringsMap.put("BT ","BT");
		skillToRemapStringsMap.put("Combo","Combo");
		skillToRemapStringsMap.put("Cosmo Charge","Cosmo Charge");
		skillToRemapStringsMap.put("Damage","Damage Cut");
		skillToRemapStringsMap.put("HP Boost","HP Boost");
		skillToRemapStringsMap.put("PHYS ATK Boost","PHYS ATK Boost");
		skillToRemapStringsMap.put("RES Boost","RES Boost");
		skillToRemapStringsMap.put("Recovery","Recovery");		
	}

	@Inject
	public SaintsDataByBBAGoogleExporter(Printer printer) {
		super(GoogleConfig.APPLICATION_NAME, GoogleConfig.SPREADSHEET_ID);
        this.printer = printer;
    }
	
	private static final DateFormat dateFormat__YYYMMDDHHmm = new SimpleDateFormat("YYYYMMddHHmm");

	@Override
	public ExporterStatus export(Collection<SaintData> saintDataCollection, boolean overwrite) {		
	    Date now = new Date();
	    
		Map<Skill, List<SaintData>> mergedByStreamAndMerge = mergeByStreamAndMerge(saintDataCollection);
//		String mergedByStreamAndMergeToPrint = mapToString(mergedByStreamAndMerge);
		// this list of list is the structure google spreadsheet accepts
		List<List<String>> mapToListOfListsByColumn = mapToListOfListsByColumn(mergedByStreamAndMerge);
		
		String collect = mapToListOfListsByColumn.stream()
		.map(el->el.stream().collect(Collectors.joining(",")))
		.collect(Collectors.joining("\n\n"));
		
//		writeOnFile("mergedByStreamAndMergeToPrint",now, mergedByStreamAndMergeToPrint);
		System.out.println(collect);
		
//		Map<Skill, List<SaintData>> mergedByOldWay = mergeByOldWay(saintDataCollection);
//		String mergedByOldWayToPrint = mapToString(mergedByOldWay);
//		writeOnFile("mergedByOldWayToPrint",now, mergedByOldWayToPrint);
		
		return ExporterStatus.OK;
	}
	
	private static Map<Skill, List<SaintData>> mergeByStreamAndMerge(Collection<SaintData> saintDataCollection) {
	    Function<SaintData,Skill> byCrusadeSkill1Classifier = t -> t.skills.getCrusade1();
        Function<SaintData,Skill> byCrusadeSkill2Classifier = t -> t.skills.getCrusade2();
                
        Map<Skill, List<SaintData>> saintsByCrusadeSkill1 = saintDataCollection.stream()
                .filter(filterNotUsefulSaints)
                .map(crusadeSkill1NameFlattingRemapper)
                .collect(Collectors.groupingBy(byCrusadeSkill1Classifier, ConcurrentSkipListMap::new, Collectors.toList()));
        
        Map<Skill, List<SaintData>> saintsByCrusadeSkill2ThenGlobal = saintDataCollection.stream()
                .filter(filterNotUsefulSaints)
                .map(crusadeSkill2NameFlattingRemapper)
                .collect(Collectors.groupingBy(byCrusadeSkill2Classifier, ConcurrentSkipListMap::new, Collectors.toList()));
        
        BiFunction<? super List<SaintData>, ? super List<SaintData>, ? extends List<SaintData>> valuesRemappingFunction = (v1, v2) -> {
            Set<SaintData> set = new TreeSet<>(comparatorByIdDescending);
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
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
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
		Skill crusadeSkill1 = sd.skills.getCrusade1();
		skillToRemapStringsMap.entrySet().parallelStream()
		.filter(n->crusadeSkill1.name.contains(n.getKey()) )
		.map(n->n.getValue()).findFirst().ifPresent(s->crusadeSkill1.name = s);
        return sd;
	};
	private static final Function<SaintData, SaintData> crusadeSkill2NameFlattingRemapper = sd -> {
		Skill crusadeSkill2 = sd.skills.getCrusade2();
		skillToRemapStringsMap.entrySet().parallelStream()
		.filter(n->crusadeSkill2.name.contains(n.getKey()) )
		.map(n->n.getValue()).findFirst().ifPresent(s->crusadeSkill2.name = s);
        return sd;
	};
	private static final Comparator<SaintData> comparatorByIdDescending = new Comparator<SaintData>() {
        @Override
        public int compare(SaintData o1, SaintData o2) {
            long o1L = Long.parseLong(o1.id);
            long o2L = Long.parseLong(o2.id);
            if (o1L > o2L) return 1;
            if (o1L < o2L) return -1;
            return 0;
        }
    };
		
	private static List<List<String>> mapToListOfListsByColumn(Map<Skill, List<SaintData>> merged) {
	    List<List<String>> skillWithSaintsByColumns = merged.entrySet().stream()
            .map(e->{	
            	List<String> skillWithSaints = new ArrayList<>();
				Skill skill = e.getKey();
				String skillAsString = "{'name':'" + skill.name + "','imageSmall':'" + skill.imageSmall + "'}";
				skillWithSaints.add(skillAsString);
				List<String> saintsToJsonList = saintsToJsonList(e.getValue());
				skillWithSaints.addAll(saintsToJsonList);
				return skillWithSaints;
            })
            .collect(Collectors.toList());
	    return skillWithSaintsByColumns;
	}
	private static final List<String> saintsToJsonList(List<SaintData> saints) {
        List<String> saintToJsonList = saints.stream()
				.sorted(comparatorByIdDescending)
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
