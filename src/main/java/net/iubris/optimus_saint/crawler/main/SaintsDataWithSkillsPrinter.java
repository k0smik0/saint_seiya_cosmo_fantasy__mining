package net.iubris.optimus_saint.crawler.main;

import javax.inject.Inject;

import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.saints.skills.Skill;
import net.iubris.optimus_saint.crawler.utils.Printer;

/**
 * @author Massimiliano Leone - massimiliano.leone@dedalus.eu
 * @since 12/mar/2019
 * @version 1
 *
 */
public class SaintsDataWithSkillsPrinter extends AbstractConsoleSaintsDataPrinter {
    
    @Inject
    public SaintsDataWithSkillsPrinter(Printer printer) {
        super(printer);
    }
    
    private static final String NEW_LINE = "\n";
    private static final String LINE = "----------------------------------------------";
    @Override
    protected void print(SaintData saintData) {
        /*if (saintData.name.contains("LG")) {
            System.out.println("ahaaaaah! an LG!");
        }*/
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
            
        s = // .replace(SEPARATOR+SEPARATOR, SEPARATOR)
            s.replaceAll("/[#]{2,}/", "#")
            .replaceAll("/\\.\\[/"," [")
            .replaceAll("/\\] /","]. ")
            .replaceAll("/\\.N/",". N")
            +NEW_LINE+LINE+NEW_LINE;
        printer.println(s);
    }

}
