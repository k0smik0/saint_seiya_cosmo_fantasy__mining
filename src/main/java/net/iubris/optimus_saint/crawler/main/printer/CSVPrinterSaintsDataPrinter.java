package net.iubris.optimus_saint.crawler.main.printer;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;

import net.iubris.optimus_saint.common.StringUtils;
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
    
    private static final String SEPARATOR = StringUtils.DASH;

    @Inject
    public CSVPrinterSaintsDataPrinter(Printer printer) {
        super(printer);
    }
    
    @Override
    public void print(Collection<SaintData> saintDatas) {
        String collect = saintsDataToStringStream(saintDatas)
        .collect(Collectors.joining(StringUtils.NEW_LINE));
        printer.println(collect);
    }
    
    @Override
    protected String saintDataToString(SaintData saintData) {
        String s = saintData.id
                + SEPARATOR + saintData.name
                + SEPARATOR + saintData.description
                + SEPARATOR + skillToJsonString(saintData.skills.first)
                + SEPARATOR + skillToJsonString(saintData.skills.second)
                + SEPARATOR + skillToJsonString(saintData.skills.third)
                + SEPARATOR + skillToJsonString(saintData.skills.fourth)
                + SEPARATOR + skillToJsonString(saintData.skills.getSeventhSense())
                + SEPARATOR + skillToJsonString(saintData.skills.getCrusade())
                        // .replace(SEPARATOR+SEPARATOR, SEPARATOR)
                .replaceAll("##", "#")
                + SEPARATOR
                .replaceAll("/[#]{2,}/", "#");
        return s;
    }
    public static String skillToJsonString(Skill skill) {
        String descriptionEN = normalizeDescription(skill);
        String s = "{"
                    +"'name':'"+skill.name+"',"
                    +"'description':{";
        if (org.apache.commons.lang3.StringUtils.isNotBlank(descriptionEN)) {
                                s+="'en':'"+descriptionEN+"',";
                                s+="'it':'MISSING'";
        } else {
                                s+="'en':'-',";
                                s+="'it':'-'";
        }
                            s+="}"
                 +"}";
        return s;
    }
    private static String normalizeDescription(Skill skill) {
        return skill.description.trim()
                .replace(StringUtils.QUOTE, StringUtils.EMPTY)
                .replace(StringUtils.NEW_LINE, StringUtils.SPACE);
    }

}
