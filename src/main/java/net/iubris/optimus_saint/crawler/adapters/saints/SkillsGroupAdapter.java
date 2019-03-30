package net.iubris.optimus_saint.crawler.adapters.saints;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

import net.iubris.optimus_saint.crawler.model.LocalizationUtils;
import net.iubris.optimus_saint.crawler.model.saints.skills.Skill;
import net.iubris.optimus_saint.crawler.model.saints.skills.SkillsGroup;

public class SkillsGroupAdapter extends AbstractArrayAdapter<SkillsGroup> {
	
	private static final Collector<Skill, SkillsGroupBuilder, SkillsGroupBuilder> collector = 
			Collector.of(SkillsGroupBuilder::new, 
					SkillsGroupBuilder::accept, 
					(a,b)->null, 
					a->a, 
					Characteristics.IDENTITY_FINISH );

    private static final String FIELD_IMAGE_SMALL = "small";
	
	private static final Function<JsonValue, Skill> jsonValueToSkill = jv->{
		JsonObject jsonObjectRoot = jv.asJsonObject();
		
		String idS = jsonObjectRoot.getString(FIELD_ID);
//		long id = Long.parseLong(idS);
		
//		JsonObject jsonObjectName = jsonObjectRoot.getJsonObject(FIELD_NAME);
		String name = 
		        // LocalizationUtils.getLocalizedValue(jsonObjectName);
		        jsonObjectRoot.getString(FIELD_NAME);
		
		/*if (idS.endsWith("100")) {
			System.out.println(idS+" "+name);
		}*/
		
//		JsonObject jsonObjectDescription = jsonObjectRoot.getJsonObject(FIELD_DESCRIPTION);
//		String descriptionString = LocalizationUtils.getLocalizedValue(jsonObjectDescription);
		String descriptionString = jsonObjectRoot.getString(FIELD_DESCRIPTION);
		
		Skill skill = new Skill();
		skill.id = idS;
		skill.description = descriptionString;
		skill.name = name;
		skill.imageSmall = jsonObjectRoot.getString(FIELD_IMAGE_SMALL);
		
		return skill;
	};
	
	@Override
	public SkillsGroup adaptFromJson(JsonArray jsonArray) {
		
//		SkillsGroup skillsGroup = new SkillsGroup();
		
//		Supplier<SkillsGroup> skSupplier = () -> new SkillsGroup();

		SkillsGroupBuilder skillsGroupBuilder = jsonArray.stream()
				.map(jsonValueToSkill)
				.sorted(Comparator.comparing(Skill::getId))
				.collect(collector);
		
		SkillsGroup skillsGroup = skillsGroupBuilder.get();

		return skillsGroup;
	}
	
	private static class SkillsGroupBuilder implements Consumer<Skill> {
		private SkillsGroup skillsGroup;
		
		public SkillsGroupBuilder() {
			this.skillsGroup = new SkillsGroup();
		}
		
		@Override
		public void accept(Skill t) {
			if ((t.id).endsWith("001")) {
				skillsGroup.first = t;
			}
			else if ((t.id).endsWith("002")) {
				skillsGroup.second = t;
			}
			else if ((t.id).endsWith("003")) {
				skillsGroup.third = t;
			}
			else if ((t.id).endsWith("004")) {
				skillsGroup.fourth = t;
			}
			else if ((t.id).endsWith("007")) {
				skillsGroup.setSeventhSense(t);
			}
			else if ((t.id).endsWith("100")) {
				skillsGroup.setCrusade1(t);
			}
			else if ((t.id).endsWith("101")) {
                skillsGroup.setCrusade2(t);
            }
		}
		
		SkillsGroup get() {
			return skillsGroup;
		}
	}
}
