package net.iubris.optimus_saint.crawler.main.exporter;

import static net.iubris.optimus_saint.common.StringUtils.DASH;
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
public class SaintDataToJsonForSheetSaint extends AbstractSaintDataToJSON {

    @Inject
    public SaintDataToJsonForSheetSaint(JsonbUtils jsonbUtils) {
        super(jsonbUtils);
    }
    
    public String saintRichNameToJsonString(SaintData saintData) {
        String nameImageDescriptionToJsonString = nameImageDescriptionToJsonString(saintData.name, 
                saintData.description, saintData.descriptionIT, saintData.imageSmall);
        return nameImageDescriptionToJsonString;
    }
    @SuppressWarnings("resource")
    private String nameImageDescriptionToJsonString(String name, String descriptionEN, String descriptionIT, String imageSmall) {
        /*
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
                  */
        ToJsonDTO saintToStringDTO = new ToJsonDTO(name, imageSmall);
        if (exists(descriptionEN)) {
            saintToStringDTO.description.EN = normalizeDescription(descriptionEN);
            if (exists(descriptionIT)) {
                saintToStringDTO.description.IT = normalizeDescription(descriptionIT);
            } else {
                saintToStringDTO.description.IT = MISSING;
            }
        }
        Jsonb engine = jsonbUtils.getEngine();
        String toJson = engine.toJson(saintToStringDTO);
        toJson = toJson.replace(QUOTE, PIPE);
//        toJson = normalizeJson(toJson);
        return toJson;
    }
    public static class ToJsonDTO {
        private String name;
        private DescriptionDTO description = new DescriptionDTO();
        private String imageSmall;
        public ToJsonDTO(String name, String imageSmall) {
            this.name = name;
            this.imageSmall = imageSmall;
        }
        public ToJsonDTO() {}
        public String getName() {
            return name;
        }
        public DescriptionDTO getDescription() {
            return description;
        }
        public String getImageSmall() {
            return imageSmall;
        }
    }
    public static class DescriptionDTO {
        private String EN = DASH;
        private String IT = DASH;
        public final String getEN() {
            return EN;
        }
        public final String getIT() {
            return IT;
        }
    }
    
    @SuppressWarnings("resource")
    public String skillToJsonString(Skill skill) {
        SaintDataToJsonForSheetSaint.SkillToStringDTO skillToStringDTO = new SkillToStringDTO(skill.getName(), 
                normalizeDescription(skill.description), skill.effects, skill.imageSmall);
        if (exists(skill.descriptionIT)) {
            skillToStringDTO.descriptionIT = normalizeDescription(skill.descriptionIT);
        }
        Jsonb engine = jsonbUtils.getEngine();
        String toJson = engine.toJson(skillToStringDTO);
        toJson = toJson.replace(QUOTE, PIPE);
        return toJson; //skillNameImageDescriptionWithEffectsToJsonString;
    }
    public static class SkillToStringDTO {
        private String name;
        private String description;
        private String descriptionIT = MISSING;
        private String imageSmall;
        private List<String> effects;
        
        public SkillToStringDTO(String name, String description, List<String> effects, String imageSmall) {
            this.name = name;
            this.description = description;
            this.effects = effects;
            this.imageSmall = imageSmall;
//            this.descriptionIT = descriptionIT;
        }
        public SkillToStringDTO() {}
        public final String getName() {
            return name;
        }
        public final String getDescription() {
            return description;
        }
        public final String getDescriptionIT() {
            return descriptionIT;
        }
        public final String getImageSmall() {
            return imageSmall;
        }
        public final List<String> getEffects() {
            return effects;
        }
    }
}