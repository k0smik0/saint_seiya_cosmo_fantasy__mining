package net.iubris.optimus_saint.model.saint.data;


import java.util.List;

import javax.json.bind.annotation.JsonbProperty;

import net.iubris.optimus_saint.model.saint.data.skills.SkillsGroup;
import net.iubris.optimus_saint.model.saint.data.stats.StatsGroup;
import net.iubris.optimus_saint.model.saint.data.tiers.TiersGroup;
import net.iubris.optimus_saint.model.saint.data.value.lane.Lane;
import net.iubris.optimus_saint.model.saint.data.value.type.Type;

/**
 * 
 * mapped from: http://sscfdb.com/api/saints
 * 
 * @author massimiliano.leone
 *
 */
public class SaintData {

	public int unitId;
	
	public String fyi_name;
	
//	@JsonbProperty(value="goodwith")
	public List<String> goodWith;
	
	// was array
//	@JsonbProperty(value="strongagainst")
	public List<String> strongAgainst;
	
	public TiersGroup tiers;
	
//	public Long id;
	public String id;
	
	public boolean incomplete;

//	@JsonbTypeAdapter(NameAdapter.class)
	public String name;
	
//	@JsonbTypeAdapter(DescriptionAdapter.class)
	public String description;
	
	@JsonbProperty(value="small")
	public String imageSmall;
	@JsonbProperty(value="thumb")
	public String imageThumb;
	@JsonbProperty(value="full")
	public String imageFull;
	
//	@JsonbTypeAdapter(TypeAdapter.class)
//	@JsonbTypeDeserializer(TypeDeserializer.class)
	public Type type;
//	public String type;
	
//	@JsonbTypeAdapter(LaneAdapter.class)
	public Lane lane;
	
//	@JsonbProperty(value="stats")
//	@JsonbTypeAdapter(StatsArrayAdapter.class)
	public StatsGroup stats;
	
//	@JsonbProperty(value="skills")
//	@JsonbTypeAdapter(SkillsAdapter.class)
	public SkillsGroup skills;
	
	public String getId() {
		return id;
	}
}
