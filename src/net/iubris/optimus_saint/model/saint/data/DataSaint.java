package net.iubris.optimus_saint.model.saint.data;


import javax.json.bind.JsonbConfig;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;

/**
 * 
 * mapped from: http://sscfdb.com/api/saints
 * 
 * @author massimiliano.leone
 *
 */
public class DataSaint {

	public long unitId;
	
	public String fyi_name;
	
	// was array
	@JsonbProperty(value="strongagainst")
	public String[] strongAgainst;
	
	public Tiers tiers;
	
	public String id;
	
	public boolean incomplete;

	@JsonbTypeAdapter(NameAdapter.class)
	public Name name;
	
	@JsonbTypeAdapter(DescriptionAdapter.class)
	public Description description;
	
	@JsonbProperty(value="small")
	public String imageSmall;
	@JsonbProperty(value="thumb")
	public String imageThumb;
	@JsonbProperty(value="full")
	public String imageFull;
	
	@JsonbTypeAdapter(TypeAdapter.class)
	public Type type;
	
	@JsonbTypeAdapter(LaneAdapter.class)
	public Lane lane;
	
	@JsonbTypeAdapter(StatsAdapter.class)
	public Stats stats;
	
	@JsonbTypeAdapter(SkillsAdapter.class)
	public Skills skills;
	
	
	public static void main(String[] args) {
		
		JsonbConfig config = new JsonbConfig();
//			    .withAdapters(new LocalizedAdapter<Name>("Name"));
		
	}
}
