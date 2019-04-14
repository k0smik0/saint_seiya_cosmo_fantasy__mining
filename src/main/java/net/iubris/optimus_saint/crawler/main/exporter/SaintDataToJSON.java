package net.iubris.optimus_saint.crawler.main.exporter;

import static net.iubris.optimus_saint.common.StringUtils.*;

import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.saints.skills.Skill;

public class SaintDataToJSON {

    public static class SheetSaints {
        static String saintRichNameToJsonString(SaintData saintData) {
            String nameImageDescriptionToJsonString = nameImageDescriptionToJsonString(saintData.name, saintData.description, saintData.descriptionIT, saintData.imageSmall);
            return nameImageDescriptionToJsonString;
        }
        
        static String skillToJsonString(Skill skill) {
            String skillNameImageDescriptionToJsonString = nameImageDescriptionToJsonString(skill.getName(), skill.description, skill.descriptionIT, skill.imageSmall);
            return skillNameImageDescriptionToJsonString;
        }
        
        private static String nameImageDescriptionToJsonString(String name, String descriptionEN, String descriptionIT, String imageSmall) {
            String s = bo 
                        +m+tagName+m+t+m+name+m+c
                        +m+tagDescription+m+t
                        +bo;
            if (exists(descriptionEN)) {
                            s+=m+EN+m+t+m+descriptionEN+m+c;
                            if (exists(descriptionIT)) {
                                s+=m+IT+m+t+m+normalizeDescription(descriptionIT)+m;
                            } else {
                                s+=m+IT+m+t+m+MISSING+m;
                            }
            } else {
                            s+=m+EN+m+t+m+DASH+m+c;
                            s+=m+IT+m+t+m+DASH+m;
            }
                      s+=bc+c;
                      s+=m+tagImageSmall+m+t+m+imageSmall+m
                     +bc;
            s=s.replace(QUOTE, PIPE);
            return s;
        }
        
    }
    public static class SheetCrusadeSkill {
        public static final String saintToJson(SaintData saintData) {
            String s = "";
            s+=bo
                +m+tagName+m+t+m+saintData.name.replace(COMMA, EMPTY)+m+c
                +m+tagImageSmall+m+t+m+saintData.imageSmall+m+c
                +m+"crusade_skill_1"+m+t+bo
                    +m+tagName+m+t+m+saintData.skills.getCrusade1().getName()+m+c
                    +m+tagDescription+m+t+m+saintData.skills.getCrusade1().description
                                .replace(NEW_LINE, SPACE)
                                .replace(QUOTE, PIPE)+m+c;                        
                if (exists(saintData.skills.getCrusade1().descriptionIT)) {
                    s+=m+tagDescription+m+t+m+saintData.skills.getCrusade1().descriptionIT
                                .replace(NEW_LINE, SPACE)
                                .replace(QUOTE, PIPE)+m+c;
                }
                    s+=m+tagImageSmall+m+t+m+saintData.skills.getCrusade1().imageSmall+m;
                s+=bc;
                if (saintData.skills.hasCrusade2()) {
                    s+=c+m+"crusade_skill_2"+m+t+m+bo
                        +m+tagName+m+t+m+saintData.skills.getCrusade2().getName()+m+c
                        +m+tagDescription+m+t+m+saintData.skills.getCrusade2().description
                                .replace(NEW_LINE, SPACE)
                                .replace(QUOTE, PIPE)+m+c;
                        if (exists(saintData.skills.getCrusade2().descriptionIT)) {
                            s+=m+tagDescription+m+t+m+saintData.skills.getCrusade2().descriptionIT
                                        .replace(NEW_LINE, SPACE)
                                        .replace(QUOTE, PIPE)+m+c;
                        }
                        s+=m+tagImageSmall+m+t+m+saintData.skills.getCrusade2().imageSmall+m;
                        s+=bc;
                }
            s+=bc;
            return s;
        }
    }
    
    private static String normalizeDescription(String descr) {
        return descr.trim()
//                .replace(QUOTE, EMPTY)
                .replace(MARKS, QUOTE)
                .replace(NEW_LINE, SPACE);
    }

    private static boolean exists(String descr) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(descr)) {
            return true;
        }
        return false;
    }
    
    
    private static final String m = MARKS; // "
    private static final String c = COMMA; // ,
    private static final String t = COLONS; // :
    private static final String bo = "{";
    private static final String bc = "}";
    private static final String tagName = "name";
    private static final String tagDescription = "description";
    private static final String tagImageSmall = "imageSmall";
    private static final String EN = "en";
    private static final String IT = "it";
    private static final String MISSING = "MISSING";
    
}
