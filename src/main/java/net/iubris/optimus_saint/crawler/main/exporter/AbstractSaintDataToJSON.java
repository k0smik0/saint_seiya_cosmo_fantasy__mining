package net.iubris.optimus_saint.crawler.main.exporter;

import static net.iubris.optimus_saint.common.StringUtils.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.iubris.optimus_saint.crawler.utils.JsonbUtils;

public abstract class AbstractSaintDataToJSON {
    
    protected JsonbUtils jsonbUtils;
    
    public AbstractSaintDataToJSON(JsonbUtils jsonbUtils) {
        this.jsonbUtils = jsonbUtils;
    }

    static String normalizeDescription(String descr) {
        return descr.trim()
//                .replace(QUOTE, EMPTY)
            .replace(QUOTE, PIPE)
            .replace(MARKS, QUOTE)
            .replace(NEW_LINE, EMPTY)
            ;
    }
    
    public static String normalizeJson(String jsonAsString) {
        return jsonAsString.trim().replaceAll("[\\s]{2,}", "");
    }

    static boolean exists(String descr) {
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
    
    
    static final String m = MARKS; // "
    static final String c = COMMA; // ,
    static final String t = COLONS; // :
//    static final String bo = "{";
//    static final String bc = "}";
//    static final String tagName = "name";
//    static final String tagDescription = "description";
//    static final String tagImageSmall = "imageSmall";
    static final String EN = "en";
    static final String IT = "it";
    static final String MISSING = "MISSING";
    
}
