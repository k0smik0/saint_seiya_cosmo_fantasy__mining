package net.iubris.optimus_saint.crawler.main.printer;

import javax.inject.Inject;

import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.saints.skills.Skill;
import net.iubris.optimus_saint.crawler.utils.Printer;

/**
 * @author  Massimiliano Leone - massimiliano.leone@iubris.net
 * @since   12/mar/2019
 * @version 1
 *
 */
public class SaintsDataWithSkillsPrinter extends AbstractConsoleSaintsDataPrinter {

	@Inject
	public SaintsDataWithSkillsPrinter(final Printer printer) {
		super(printer);
	}

	private static final String NEW_LINE = "\n";
	private static final String LINE = "----------------------------------------------";

	@Override
	protected String saintDataToString(final SaintData saintData) {
		/*
		 * if (saintData.name.contains("LG")) {
		 * System.out.println("ahaaaaah! an LG!");
		 * }
		 */
		String s = LINE + NEW_LINE
				+ "id: " + saintData.id + NEW_LINE
				+ "name: " + saintData.name + NEW_LINE
				+ "description: " + saintData.description + NEW_LINE
				+ "type: " + saintData.type + NEW_LINE
				+ "lane: " + saintData.lane + NEW_LINE
				+ "tiers: " + saintData.tiers.value + NEW_LINE
//				+ "goodWith: " + saintData.goodWith.stream().collect(Collectors.joining(",")) + NEW_LINE
//				+ "strongAgainst: " + saintData.strongAgainst.stream().collect(Collectors.joining(",")) + NEW_LINE
				+ "skills:" + NEW_LINE
				+ "  - 1^ skill: '" + saintData.skills.first.getName() + "': " + (saintData.skills.first.description.replace("\n", " ")) + NEW_LINE
				+ "  - 2^ skill: '" + saintData.skills.second.getName() + "': " + (saintData.skills.second.description.replace("\n", " ")) + NEW_LINE
				+ "  - 3^ skill: '" + saintData.skills.third.getName() + "': " + (saintData.skills.third.description.replace("\n", " ")) + NEW_LINE
				+ "  - 4^ skill: '" + saintData.skills.fourth.getName() + "': " + (saintData.skills.fourth.description.replace("\n", " ")) + NEW_LINE
//				+ "keywords: " + saintData.keywords
		;

		if (saintData.skills.hasSeventhSense()) {
			Skill seventhSense = saintData.skills.getSeventhSense();
			s += "  - 7^sense skill:" + seventhSense.getName() + ": " + (seventhSense.description.replace("\n", " ")) + NEW_LINE;
		}

		s += "  - Crusade skill 1:" + saintData.skills.getCrusade1().getName() + ": " + (saintData.skills.getCrusade1().description.replace("\n", " ")) + NEW_LINE;

		if (saintData.skills.hasCrusade2()) {
			Skill crusade2 = saintData.skills.getCrusade2();
			s += "  - Crusade skill 2:" + crusade2.getName() + ": " + (crusade2.description.replace("\n", " "));
		}

		s = // .replace(SEPARATOR+SEPARATOR, SEPARATOR)
				s.replaceAll("/[#]{2,}/", "#")
					.replaceAll("/\\.\\[/", " [")
					.replaceAll("/\\] /", "]. ")
					.replaceAll("/\\.N/", ". N")
						+ NEW_LINE + LINE + NEW_LINE;

		return s;
	}

}
