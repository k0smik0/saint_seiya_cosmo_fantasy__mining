package net.iubris.optimus_saint.crawler.main;

import javax.inject.Inject;

import org.apache.commons.text.StringSubstitutor;

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

    @Inject
    public CSVPrinterSaintsDataPrinter(Printer printer) {
        super(printer);
    }
    
    private static final String SEPARATOR = StringUtils.DASH;
    
    @Override
    protected void print(SaintData saintData) {
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
//        System.out.println(s);
        printer.println(s);
    }
    
    private String skillToString(Skill skill) {
        String s = "{'name':'"+skill.name+"',description:'"+(skill.description.trim().replace(StringUtils.NEW_LINE, StringUtils.SPACE))+"'}";
        return s;
    }

}
