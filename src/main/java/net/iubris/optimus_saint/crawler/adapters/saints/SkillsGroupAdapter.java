package net.iubris.optimus_saint.crawler.adapters.saints;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import javax.json.JsonArray;
import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.model.LocalizationUtils;
import net.iubris.optimus_saint.crawler.model.saints.skills.Skill;
import net.iubris.optimus_saint.crawler.model.saints.skills.SkillsGroup;

public class SkillsGroupAdapter extends AbstractArrayAdapter<SkillsGroup> {

//	private static final String NAME = "name";
//	private static final String DESCRIPTION = "description";
//	private static final String ID = "id";
	
	@Override
	public SkillsGroup adaptFromJson(JsonArray jsonArray) {
		
//		SkillsGroup skillsGroup = new SkillsGroup();
		
//		Supplier<SkillsGroup> skSupplier = () -> new SkillsGroup();
		
		Collector<Skill, SkillsGroupBuilder, SkillsGroupBuilder> collector = 
				Collector.of(SkillsGroupBuilder::new, 
						SkillsGroupBuilder::accept, 
						(a,b)->null, a->a, 
						Characteristics.IDENTITY_FINISH );
		
		
		SkillsGroupBuilder skillsGroupBuilder = jsonArray.stream().map(jv->{
			JsonObject jsonObjectRoot = jv.asJsonObject();
			
			String idS = jsonObjectRoot.getString(FIELD_ID);
			long id = Long.parseLong(idS);
			
			JsonObject jsonObjectName = jsonObjectRoot.getJsonObject(FIELD_NAME);
			String name = LocalizationUtils.getLocalizedValue(jsonObjectName);
			
			JsonObject jsonObjectDescription = jsonObjectRoot.getJsonObject(FIELD_DESCRIPTION);
			String descriptionString = LocalizationUtils.getLocalizedValue(jsonObjectDescription);
//			Description description = new Description(); 
//			description.value = descriptionString;
			
			Skill skill = new Skill();
			skill.id = id;
			skill.description = descriptionString;
			skill.name = name;
			
			return skill;
		})
		.sorted(Comparator.comparing(Skill::getId))
		.collect(collector);
		
		SkillsGroup skillsGroup = skillsGroupBuilder.get();
		
//		for (JsonValue jsonValue : jsonArray) {}
		
		return skillsGroup;
	}
	
	private static class SkillsGroupBuilder implements Consumer<Skill> {
		private SkillsGroup skillsGroup;
		
		public SkillsGroupBuilder() {
			this.skillsGroup = new SkillsGroup();
		}
		
		@Override
		public void accept(Skill t) {
			if ((""+t.id).endsWith("1")) {
				skillsGroup.first = t;
			}
			else if ((""+t.id).endsWith("2")) {
				skillsGroup.second = t;
			}
			else if ((""+t.id).endsWith("3")) {
				skillsGroup.third = t;
			}
			else if ((""+t.id).endsWith("4")) {
				skillsGroup.fourth = t;
			}
			else if ((""+t.id).endsWith("7")) {
				skillsGroup.setSeventhSense(t);
			}
		}		
		
		SkillsGroup get() {
			return skillsGroup;
		}
	}
}
