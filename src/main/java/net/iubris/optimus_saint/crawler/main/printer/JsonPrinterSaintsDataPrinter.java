package net.iubris.optimus_saint.crawler.main.printer;

import static net.iubris.optimus_saint.common.StringUtils.EMPTY;
import static net.iubris.optimus_saint.common.StringUtils.NEW_LINE;

import javax.inject.Inject;
import javax.inject.Singleton;

import net.iubris.optimus_saint.crawler.main.exporter.AbstractSaintDataToJSON;
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
        String s = "-----------------------------------------------------"+NEW_LINE;
        s+="SAINT:"+saintDataToJsonForSheetSaint.saintRichNameToJsonString(saintData)+NEW_LINE;
        s+="SKILL_1:"+saintDataToJsonForSheetSaint.skillToJsonString(saintData.skills.first)+NEW_LINE;
        s+="SKILL_2:"+saintDataToJsonForSheetSaint.skillToJsonString(saintData.skills.second)+NEW_LINE;
        s+="SKILL_3:"+saintDataToJsonForSheetSaint.skillToJsonString(saintData.skills.third)+NEW_LINE;
        s+="SKILL_4:"+saintDataToJsonForSheetSaint.skillToJsonString(saintData.skills.fourth)+NEW_LINE;
        s+="SKILL_7:"+saintDataToJsonForSheetSaint.skillToJsonString(saintData.skills.getSeventhSense())+NEW_LINE;
        s+="SKILL_C1:"+saintDataToJsonForSheetSaint.skillToJsonString(saintData.skills.getCrusade1())+NEW_LINE;
        s+="SKILL_C2:"+saintDataToJsonForSheetSaint.skillToJsonString(saintData.skills.getCrusade2())+NEW_LINE;
        s+="-------------------------------------------------------------"+NEW_LINE;
        s = AbstractSaintDataToJSON.normalizeJson(s);
        return s;
    }

}
