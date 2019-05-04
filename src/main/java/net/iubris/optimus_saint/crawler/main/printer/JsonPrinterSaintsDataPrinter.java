package net.iubris.optimus_saint.crawler.main.printer;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.optimus_saint.crawler.main.exporter.SaintDataToJsonForSheetSaint;
import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.utils.Printer;

/**
 * 
 * @author Massimiliano Leone - massimiliano.leone@iubris.net
 * @since 04/05/2019
 * @version 1
 *
 */
@Singleton
public class JsonPrinterSaintsDataPrinter extends AbstractConsoleSaintsDataPrinter {
    
    private final SaintDataToJsonForSheetSaint saintDataToJsonForSheetSaint;

    @Inject
    public JsonPrinterSaintsDataPrinter(SaintDataToJsonForSheetSaint saintDataToJsonForSheetSaint, Printer printer) {
        super(printer);
        this.saintDataToJsonForSheetSaint = saintDataToJsonForSheetSaint;
    }

    @Override
    protected String saintDataToString(SaintData saintData) {
        String s = "-----------------------------------------------------";
        s+="SAINT:"+saintDataToJsonForSheetSaint.saintRichNameToJsonString(saintData)+"\n";
        s+="SKILL_1:"+saintDataToJsonForSheetSaint.skillToJsonString(saintData.skills.first)+"\n";
        s+="SKILL_2:"+saintDataToJsonForSheetSaint.skillToJsonString(saintData.skills.second)+"\n";
        s+="SKILL_3:"+saintDataToJsonForSheetSaint.skillToJsonString(saintData.skills.third)+"\n";
        s+="SKILL_4:"+saintDataToJsonForSheetSaint.skillToJsonString(saintData.skills.fourth)+"\n";
        s+="SKILL_7:"+saintDataToJsonForSheetSaint.skillToJsonString(saintData.skills.getSeventhSense())+"\n";
        s+="SKILL_C1:"+saintDataToJsonForSheetSaint.skillToJsonString(saintData.skills.getCrusade1())+"\n";
        s+="SKILL_C2:"+saintDataToJsonForSheetSaint.skillToJsonString(saintData.skills.getCrusade2())+"\n";
        s+="------------------------------------------------------------\n";
        return s;
    }

}
