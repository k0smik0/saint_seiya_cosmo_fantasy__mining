package net.iubris.optimus_saint.crawler.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.json.bind.annotation.JsonbProperty;

import net.iubris.optimus_saint.crawler.model.saints.skills.SkillsGroup;
import net.iubris.optimus_saint.crawler.model.saints.stats.StatsGroup;
import net.iubris.optimus_saint.crawler.model.saints.value.lane.Lane;
import net.iubris.optimus_saint.crawler.model.saints.value.tiers.Tiers;
import net.iubris.optimus_saint.crawler.model.saints.value.type.Type;

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
	public List<String> goodWith = new ArrayList<>();

	// was array
//	@JsonbProperty(value="strongagainst")
	public List<String> strongAgainst = new ArrayList<>();

//	public TiersGroup tiers;
	public Tiers tiers = new Tiers();

//	public Long id;
	public String id;

	public boolean incomplete;

//	@JsonbTypeAdapter(NameAdapter.class)
	public String name;

//	@JsonbTypeAdapter(DescriptionAdapter.class)
	public String description;

	public String descriptionIT;

	@JsonbProperty(value = "small")
	public String imageSmall;
	@JsonbProperty(value = "thumb")
	public String imageThumb;
	@JsonbProperty(value = "full")
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

	public Set<String> keywords;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public StatsGroup getStats() {
		return stats;
	}

	public int getRawStatsSum() {
		return stats.rawSum();
	}
}
