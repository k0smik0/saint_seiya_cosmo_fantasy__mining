package net.iubris.optimus_saint.crawler.main.exporter;

import static net.iubris.optimus_saint.common.StringUtils.COLONS;
import static net.iubris.optimus_saint.common.StringUtils.COMMA;
import static net.iubris.optimus_saint.common.StringUtils.DASH;
import static net.iubris.optimus_saint.common.StringUtils.EMPTY;
import static net.iubris.optimus_saint.common.StringUtils.MARKS;
import static net.iubris.optimus_saint.common.StringUtils.NEW_LINE;
import static net.iubris.optimus_saint.common.StringUtils.PIPE;
import static net.iubris.optimus_saint.common.StringUtils.QUOTE;
import static net.iubris.optimus_saint.common.StringUtils.SPACE;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    
    private static String normalizeDescription(String descr) {
        return descr.trim()
//                .replace(QUOTE, EMPTY)
                .replace(QUOTE, PIPE)
                .replace(MARKS, QUOTE)
                .replace(NEW_LINE, SPACE);
    }

    private static boolean exists(String descr) {
        if (org.apache.commons.lang3.StringUtils.isNotBlank(descr)) {
            return true;
        }
        return false;
    }
    
    public static boolean isJSONValid(String s) {
        try {
            new JSONObject(s);
            return true;
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(s);
                return true;
            } catch (JSONException ex1) {
                return false;
            }
        }
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
