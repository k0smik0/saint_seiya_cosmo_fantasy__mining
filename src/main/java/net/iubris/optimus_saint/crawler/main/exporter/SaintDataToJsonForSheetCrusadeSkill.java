package net.iubris.optimus_saint.crawler.main.exporter;

import static net.iubris.optimus_saint.common.StringUtils.PIPE;
import static net.iubris.optimus_saint.common.StringUtils.QUOTE;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;

import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.utils.JsonbUtils;

@Singleton
public class SaintDataToJsonForSheetCrusadeSkill extends AbstractSaintDataToJSON {
    
    @Inject
    public SaintDataToJsonForSheetCrusadeSkill(JsonbUtils jsonbUtils) {
        super(jsonbUtils);
    }

    public String saintToJson(SaintData saintData) {
        /*String s = "";
        
         {
             name: ...,
             imageSmall: ...,
             crusade_skill_1: {
                 name: ...
                 description: ...
                 descriptionIT: ...
                 effects: [...],
                 imageSmall: ...
             },
             crusade_skill_2: {
                 name: ...
                 description: ...
                 descriptionIT: ...
                 effects: [...],
                 imageSmall: ...
             }
         }
         */
        
        /*s+=bo
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
        */
        
        ToJsonDTO toJsonDTO = new ToJsonDTO(saintData.getName(), saintData.imageSmall);
        toJsonDTO.crusade_skill_1 = new CrusadeSkillToJson();
        toJsonDTO.crusade_skill_1.description = normalizeDescription( saintData.skills.getCrusade1().description );
        toJsonDTO.crusade_skill_1.imageSmall = saintData.skills.getCrusade1().imageSmall;
        String crusade1DescriptionIT = saintData.skills.getCrusade1().descriptionIT;
        if (exists(crusade1DescriptionIT)) {
            toJsonDTO.crusade_skill_1.descriptionIT = normalizeDescription( crusade1DescriptionIT );
        } else {
            toJsonDTO.crusade_skill_1.descriptionIT = MISSING;
        }
        if (saintData.skills.hasCrusade2()) {
            toJsonDTO.crusade_skill_2 = new CrusadeSkillToJson();
            toJsonDTO.crusade_skill_2.description = normalizeDescription( saintData.skills.getCrusade2().description );
            toJsonDTO.crusade_skill_2.imageSmall = saintData.skills.getCrusade2().imageSmall;
            String crusade2DescriptionIT = saintData.skills.getCrusade2().descriptionIT;
            if (exists(crusade2DescriptionIT)) {
                toJsonDTO.crusade_skill_2.descriptionIT = normalizeDescription( crusade2DescriptionIT );
            } else {
                toJsonDTO.crusade_skill_2.descriptionIT = MISSING;
            }
        }
        Jsonb engine = jsonbUtils.getEngine();
        String toJson = engine.toJson(toJsonDTO);
        toJson = toJson.replace(QUOTE, PIPE);
        return toJson;
    }
    public static class ToJsonDTO {
        public String name;
        public String imageSmall;
        public CrusadeSkillToJson crusade_skill_1;
        public CrusadeSkillToJson crusade_skill_2;
        
        public ToJsonDTO(String name, String imageSmall) {
            this.name = name;
            this.imageSmall = imageSmall;
        }
        public ToJsonDTO() {};
    }
    
    public static class CrusadeSkillToJson {
        public String name;
        public String description;
        public String descriptionIT;
        public List<String> effects;
        public String imageSmall;
    }
}