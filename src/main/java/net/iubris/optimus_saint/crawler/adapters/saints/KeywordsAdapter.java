package net.iubris.optimus_saint.crawler.adapters.saints;

import java.util.HashSet;
import java.util.Set;

import javax.json.JsonArray;
import javax.json.JsonValue;

import net.iubris.optimus_saint.common.StringUtils;

public class KeywordsAdapter extends AbstractArrayAdapter<Set<String>> {

    @Override
    public Set<String> adaptFromJson(JsonArray jsonArray) throws Exception {
        Set<String> set = new HashSet<>();
        for (JsonValue jsonValue : jsonArray) {
            String s = jsonValue.toString().replace(StringUtils.MARKS, StringUtils.EMPTY);
//            System.out.println(s1);
            set.add(s);
        }
//        System.out.println("");
        return set;
    }

}
