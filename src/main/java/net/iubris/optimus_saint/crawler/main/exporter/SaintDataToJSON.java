package net.iubris.optimus_saint.crawler.main.exporter;

import static net.iubris.optimus_saint.common.StringUtils.COLONS;
import static net.iubris.optimus_saint.common.StringUtils.COMMA;
import static net.iubris.optimus_saint.common.StringUtils.MARKS;
import static net.iubris.optimus_saint.common.StringUtils.NEW_LINE;
import static net.iubris.optimus_saint.common.StringUtils.PIPE;
import static net.iubris.optimus_saint.common.StringUtils.QUOTE;
import static net.iubris.optimus_saint.common.StringUtils.SPACE;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.iubris.optimus_saint.crawler.utils.JsonbUtils;

public abstract class SaintDataToJSON {
    
    protected JsonbUtils jsonbUtils;
    
    public SaintDataToJSON(JsonbUtils jsonbUtils) {
        this.jsonbUtils = jsonbUtils;
    }

    static String normalizeDescription(String descr) {
        return descr.trim()
//                .replace(QUOTE, EMPTY)
                .replace(QUOTE, PIPE)
                .replace(MARKS, QUOTE)
                .replace(NEW_LINE, SPACE);
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
    static final String bo = "{";
    static final String bc = "}";
    static final String tagName = "name";
    static final String tagDescription = "description";
    static final String tagImageSmall = "imageSmall";
    static final String EN = "en";
    static final String IT = "it";
    static final String MISSING = "MISSING";
    
}
