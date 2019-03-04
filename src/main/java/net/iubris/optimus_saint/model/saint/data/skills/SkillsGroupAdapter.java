package net.iubris.optimus_saint.model.saint.data.skills;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.stream.Collector;
import java.util.stream.Collector.Characteristics;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.bind.adapter.JsonbAdapter;

import net.iubris.optimus_saint.model.saint.data.LocalizationUtils;

public class SkillsGroupAdapter implements JsonbAdapter<SkillsGroup, JsonArray> {

	private static final String ROOT_NODE = "name";
	
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
			
			String idS = jsonObjectRoot.getString("id");
			long id = Long.parseLong(idS);
			
			JsonObject jsonObjectName = jsonObjectRoot.getJsonObject("name");
			String name = LocalizationUtils.getLocalizedValue(jsonObjectName);
			
			JsonObject jsonObjectDescription = jsonObjectRoot.getJsonObject("description");
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
		.collect(collector)
		;
		
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
				skillsGroup.seventhSense = t;
			}
		}		
		
		SkillsGroup get() {
			return skillsGroup;
		}
	}

	@Override
	public JsonArray adaptToJson(SkillsGroup arg0) throws Exception {
		return null;
	}
}
