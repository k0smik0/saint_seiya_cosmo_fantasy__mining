package net.iubris.optimus_saint.crawler.main.exporter;

import static net.iubris.optimus_saint.common.StringUtils.PIPE;
import static net.iubris.optimus_saint.common.StringUtils.QUOTE;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.json.bind.Jsonb;

import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.saints.skills.Skill;
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
        Skill crusade1 = saintData.skills.getCrusade1();
        toJsonDTO.crusade_skill_1.name = crusade1.getName(); 
        toJsonDTO.crusade_skill_1.description = normalizeDescription( crusade1.description );
        toJsonDTO.crusade_skill_1.image_small = crusade1.imageSmall;
        toJsonDTO.crusade_skill_1.descriptionIT = MISSING;
        String crusade1DescriptionIT = crusade1.descriptionIT;
        if (exists(crusade1DescriptionIT)) {
            toJsonDTO.crusade_skill_1.descriptionIT = normalizeDescription( crusade1DescriptionIT );
        }
        if (saintData.skills.hasCrusade2()) {
            Skill crusade2 = saintData.skills.getCrusade2();
            toJsonDTO.crusade_skill_2.name = crusade2.getName();
            toJsonDTO.crusade_skill_2.description = normalizeDescription( crusade2.description );
            toJsonDTO.crusade_skill_2.image_small = crusade2.imageSmall;
            String crusade2DescriptionIT = crusade2.descriptionIT;
            toJsonDTO.crusade_skill_2.descriptionIT = MISSING;
            if (exists(crusade2DescriptionIT)) {
                toJsonDTO.crusade_skill_2.descriptionIT = normalizeDescription( crusade2DescriptionIT );
            }
        }
        Jsonb engine = jsonbUtils.getEngine();
        String toJson = engine.toJson(toJsonDTO);
        toJson = toJson.replace(QUOTE, PIPE).trim();
        return toJson;
    }
    public static class ToJsonDTO {
//        public String saint_name;
//        public String saint_image_small;
        public SaintToJson saint = new SaintToJson();
        public CrusadeSkillToJson crusade_skill_1 = new CrusadeSkillToJson();
        public CrusadeSkillToJson crusade_skill_2 = new CrusadeSkillToJson();
        
        public ToJsonDTO(String name, String imageSmall) {
            this.saint.name = name;
            this.saint.image_small = imageSmall;
        }
        public ToJsonDTO() {};
    }
    
    public static abstract class AbstractToJson {
    	public String name;
    	public String image_small;
    }
    public static class SaintToJson extends AbstractToJson {}    
    public static class CrusadeSkillToJson extends AbstractToJson{
        public String description;
        public String descriptionIT;
        public List<String> effects;
    }
}