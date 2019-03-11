package net.iubris.optimus_saint.crawler.main;

import java.util.Collection;
import java.util.Comparator;

import javax.inject.Inject;

import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.saints.skills.Skill;
import net.iubris.optimus_saint.crawler.utils.Printer;

public class SaintsDataSimplePrinter implements SaintsDataPrinter {

    private final Printer printer;
    
    @Inject
    public SaintsDataSimplePrinter(Printer printer) {
        this.printer = printer;
    }

	@Override
	public void print(Collection<SaintData> saintDatas) {
		saintDatas.stream()
			.sorted(Comparator.comparing(SaintData::getId))
			.forEach(sd -> {
				// String reflectionToString = ToStringBuilder.reflectionToString(sd);
				// System.out.println(reflectionToString+"\n");
				// System.out.println( sd.name/*.value*/+" - id:"+sd.id );
				print(sd);
			});
	}
	
	private static final String NEW_LINE = "\n";
	private static final String LINE = "----------------------------------------------";
	private void print(SaintData saintData) {
		if (saintData.name.contains("LG")) {
			System.out.println("ahaaaaah! an LG!");
		}
		String s =
				LINE+NEW_LINE
				+"id: "+saintData.id+NEW_LINE
			+ "name: "+saintData.name+NEW_LINE
			+ "description: "+saintData.description+NEW_LINE
			+ "  - 1^ skill: '"+saintData.skills.first.name+"': "+(saintData.skills.first.description.replace("\n", " "))+NEW_LINE
			+ "  - 2^ skill: '"+saintData.skills.second.name+"': "+(saintData.skills.second.description.replace("\n", " "))+NEW_LINE
			+ "  - 3^ skill: '"+saintData.skills.third.name+"': "+(saintData.skills.third.description.replace("\n", " "))+NEW_LINE
			+ "  - 4^ skill: '"+saintData.skills.fourth.name+"': "+(saintData.skills.fourth.description.replace("\n", " "))+NEW_LINE;
			
		
		if (saintData.skills.hasSeventhSense()) {
			Skill seventhSense = saintData.skills.getSeventhSense();
			s+="  - 7^sense skill:"+seventhSense.name+": "+(seventhSense.description.replace("\n", " "))+NEW_LINE;
		}
		
		if (saintData.skills.hasCrusade()) {
			Skill other = saintData.skills.getCrusade();
			s+="  - Crusade skill:"+other.name+": "+(other.description.replace("\n", " "));
		}
			
		s=
					// .replace(SEPARATOR+SEPARATOR, SEPARATOR)
			s.replaceAll("/[#]{2,}/", "#")
			.replaceAll("/\\.\\[/"," [")
			.replaceAll("/\\] /","]. ")
			.replaceAll("/\\.N/",". N")
			+NEW_LINE+LINE+NEW_LINE;
		printer.println(s);
	}

	private static final String SEPARATOR = " # ";

	private static void printAsCSV(SaintData saintData) {
		String s = saintData.id
			+ SEPARATOR + saintData.name
			+ SEPARATOR + saintData.description
			// +SEPARATOR
			+ saintData.skills.first.description.replace("\n", "")
			+ SEPARATOR + saintData.skills.second.description.replace("\n", "")
			+ SEPARATOR + saintData.skills.third.description.replace("\n", "")
			+ SEPARATOR + saintData.skills.fourth.description.replace("\n", "")
//			+ SEPARATOR + saintData.skills.seventhSense.description.replace("\n", "")
					// .replace(SEPARATOR+SEPARATOR, SEPARATOR)
					.replaceAll("/[#]{2,}/", "#");
		System.out.println(s);
	}
}