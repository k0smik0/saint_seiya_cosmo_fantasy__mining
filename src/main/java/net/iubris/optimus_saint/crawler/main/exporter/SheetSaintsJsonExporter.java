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
public class SheetSaintsJsonExporter extends SaintDataToJSON {

    @Inject
    public SheetSaintsJsonExporter(JsonbUtils jsonbUtils) {
        super(jsonbUtils);
    }
    public String saintRichNameToJsonString(SaintData saintData) {
        String nameImageDescriptionToJsonString = nameImageDescriptionToJsonString(saintData.name, saintData.description, saintData.descriptionIT, saintData.imageSmall);
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
        SaintToStringDTO saintToStringDTO = new SaintToStringDTO(name, imageSmall);
        if (exists(descriptionEN)) {
            saintToStringDTO.descriptionEN = normalizeDescription(descriptionEN);
            if (exists(descriptionIT)) {
                saintToStringDTO.descriptionIT = normalizeDescription(descriptionIT);
            } else {
                saintToStringDTO.descriptionIT = MISSING;
            }
        }
        Jsonb engine = jsonbUtils.getEngine();
        String toJson = engine.toJson(saintToStringDTO);
        toJson = toJson.replace(QUOTE, PIPE);
        return toJson;
    }
    private static class SaintToStringDTO {
        public String name;
        public String descriptionEN = DASH;
        public String descriptionIT = DASH;
        public String imageSmall;
        public SaintToStringDTO(String name, String imageSmall) {
            this.name = name;
            this.descriptionEN = descriptionEN;
            this.descriptionIT = descriptionIT;
            this.imageSmall = imageSmall;
        }
        
        public SaintToStringDTO() {}
    }
    
    @SuppressWarnings("resource")
    public String skillToJsonString(Skill skill) {
//            String skillNameImageDescriptionWithEffectsToJsonString = skillToJsonString(skill.getName(), 
//                    skill.description, 
//                    skill.descriptionIT, 
//                    skill.imageSmall);
        SheetSaintsJsonExporter.SkillToStringDTO skillToStringDTO = new SkillToStringDTO(skill.getName(), 
                normalizeDescription(skill.description), skill.effects, skill.imageSmall);
        if (exists(skill.descriptionIT)) {
            skillToStringDTO.descriptionIT = normalizeDescription(skill.descriptionIT);
        }
        Jsonb engine = jsonbUtils.getEngine();
        String toJson = engine.toJson(skillToStringDTO);
        toJson = toJson.replace(QUOTE, PIPE);
        return toJson; //skillNameImageDescriptionWithEffectsToJsonString;
    }
    private static class SkillToStringDTO {
        public String name;
        public String description;
        public String descriptionIT = MISSING;
        public String imageSmall;
        private List<String> effects;
        
        public SkillToStringDTO(String name, String description, List<String> effects, String imageSmall) {
            this.name = name;
            this.description = description;
            this.effects = effects;
            this.imageSmall = imageSmall;
            this.descriptionIT = descriptionIT;
        }
        
        public SkillToStringDTO() {}
    }
    }