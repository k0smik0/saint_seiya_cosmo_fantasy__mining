package net.iubris.optimus_saint.crawler.main.exporter;

import static net.iubris.optimus_saint.common.StringUtils.COMMA;
import static net.iubris.optimus_saint.common.StringUtils.EMPTY;

import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.utils.JsonbUtils;

public class SheetCrusadeSkillJsonExporter extends SaintDataToJSON {
    public SheetCrusadeSkillJsonExporter(JsonbUtils jsonbUtils) {
        super(jsonbUtils);
    }

    public static final String saintToJson(SaintData saintData) {
        String s = "";
        s+=bo
            +m+tagName+m+t+m+saintData.name.replace(COMMA, EMPTY)+m+c
            +m+tagImageSmall+m+t+m+saintData.imageSmall+m+c
            +m+"crusade_skill_1"+m+t+bo
                +m+tagName+m+t+m+saintData.skills.getCrusade1().getName()+m+c
                +m+tagDescription+m+t+m+normalizeDescription(saintData.skills.getCrusade1().description)+m+c;
            String crusade1DescriptionIT = saintData.skills.getCrusade1().descriptionIT;
            if (exists(crusade1DescriptionIT)) {
                s+=m+tagDescription+m+t+m+normalizeDescription(crusade1DescriptionIT)+m+c;
            }
                s+=m+tagImageSmall+m+t+m+saintData.skills.getCrusade1().imageSmall+m;
            s+=bc;
            if (saintData.skills.hasCrusade2()) {
                s+=c+m+"crusade_skill_2"+m+t+bo
                        +m+tagName+m+t+m+saintData.skills.getCrusade2().getName()+m+c
                        +m+tagDescription+m+t+m+normalizeDescription(saintData.skills.getCrusade2().description)+m+c;
                    String crusade2DescriptionIT = saintData.skills.getCrusade2().descriptionIT;
                    if (exists(crusade2DescriptionIT)) {
                      s+=m+tagDescription+m+t+m+normalizeDescription(crusade2DescriptionIT)+m+c;
                    }
                    s+=m+tagImageSmall+m+t+m+saintData.skills.getCrusade2().imageSmall+m;
                s+=bc;
            }
        s+=bc;
        return s;
    }
}