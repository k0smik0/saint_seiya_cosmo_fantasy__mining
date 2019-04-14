package net.iubris.optimus_saint.crawler.main.printer;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;

import static net.iubris.optimus_saint.common.StringUtils.*;
import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.saints.skills.Skill;
import net.iubris.optimus_saint.crawler.utils.Printer;


/**
 * @author Massimiliano Leone - massimiliano.leone@iubris.net
 * @since 12/mar/2019
 * @version 1
 *
 */
public class CSVPrinterSaintsDataPrinter extends AbstractConsoleSaintsDataPrinter {
    
    private static final String SEPARATOR = DASH;

    @Inject
    public CSVPrinterSaintsDataPrinter(Printer printer) {
        super(printer);
    }
    
    @Override
    public void print(Collection<SaintData> saintDatas) {
        String collect = saintsDataToStringStream(saintDatas)
        .collect(Collectors.joining(NEW_LINE));
        printer.println(collect);
    }
    
    @Override
    protected String saintDataToString(SaintData saintData) {
        String s = saintData.id
                + SEPARATOR + saintData.name
                + SEPARATOR + saintData.description
                + SEPARATOR + skillNameWithDescriptionToString(saintData.skills.first)
                + SEPARATOR + skillNameWithDescriptionToString(saintData.skills.second)
                + SEPARATOR + skillNameWithDescriptionToString(saintData.skills.third)
                + SEPARATOR + skillNameWithDescriptionToString(saintData.skills.fourth)
                + SEPARATOR + skillNameWithDescriptionToString(saintData.skills.getSeventhSense())
                + SEPARATOR + skillNameWithDescriptionToString(saintData.skills.getCrusade1())
                + SEPARATOR + skillNameWithDescriptionToString(saintData.skills.getCrusade2())
                        // .replace(SEPARATOR+SEPARATOR, SEPARATOR)
                .replaceAll("##", "#")
                + SEPARATOR
                .replaceAll("/[#]{2,}/", "#");
        return s;
    }
    private static String skillNameWithDescriptionToString(Skill skill) {
        String descriptionEN = normalizeDescription(skill);
        String s = skill.getName()+t+t+SPACE+descriptionEN;
        return s;
    }
    private static final String t = COLONS;
    
    private static String normalizeDescription(Skill skill) {
        return skill.description.trim()
                .replace(QUOTE, PIPE)
//                .replace(MARKS, QUOTE)
                .replace(NEW_LINE, SPACE);
    }    
    
}
