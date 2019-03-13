package net.iubris.optimus_saint.crawler.main;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.inject.Inject;

import net.iubris.optimus_saint.common.StringUtils;
import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.saints.skills.Skill;
import net.iubris.optimus_saint.crawler.utils.Printer;

/**
 * @author Massimiliano Leone - massimiliano.leone@dedalus.eu
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
                + SEPARATOR + skillToString(saintData.skills.first)
                + SEPARATOR + skillToString(saintData.skills.second)
                + SEPARATOR + skillToString(saintData.skills.third)
                + SEPARATOR + skillToString(saintData.skills.fourth)
                + SEPARATOR + skillToString(saintData.skills.getSeventhSense())
                + SEPARATOR + skillToString(saintData.skills.getCrusade())
                        // .replace(SEPARATOR+SEPARATOR, SEPARATOR)
                .replaceAll("##", "#")
                + SEPARATOR
                .replaceAll("/[#]{2,}/", "#");
        return s;
    }
    private static String skillToString(Skill skill) {
        String descriptionEN = normalizeDescription(skill);
        String s = "{"
                        +"'name':'"+skill.name+"',"
                        +"'description':{"
                            +"'en':'"+descriptionEN+"',";
        if (org.apache.commons.lang3.StringUtils.isNotBlank(descriptionEN)) {
            s+=              "'it':'MISSING'";
        } else {
            s+=              "'it':''";
        }
        s+=               "}"
                    +"}";
        return s;
    }
    private static String normalizeDescription(Skill skill) {
        return skill.description.trim()
                .replace(StringUtils.QUOTE, StringUtils.EMPTY)
                .replace(StringUtils.NEW_LINE, StringUtils.SPACE);
    }

}
