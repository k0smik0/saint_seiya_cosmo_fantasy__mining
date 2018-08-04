package net.iubris.optimus_saint.model.saint.data;


import java.io.FileInputStream;
import java.io.IOException;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTypeAdapter;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * mapped from: http://sscfdb.com/api/saints
 * 
 * @author massimiliano.leone
 *
 */
public class SaintData {

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
	
	@JsonbProperty(value="stats")
	@JsonbTypeAdapter(StatsArrayAdapter.class)
	public StatsGroup stats;
	
	@JsonbTypeAdapter(SkillsAdapter.class)
	public Skills skills;
	
	
	public static class Saints {		
		
		private static final String LocalizationEN = "EN";
		public static final String LocalizationDefault = LocalizationEN;
		
		private SaintData[] saints;
	}
	
	public static void main(String[] args) {
//		JsonbConfig config = new JsonbConfig();
//			    .withAdapters(new LocalizedAdapter<Name>("Name"));
		
//		System.out.println( new File("saints.json").getAbsolutePath() );
		
		Jsonb jsonb = null;
		try (FileInputStream fis = new FileInputStream("saints.json");  ) {
			JsonbConfig jc = new JsonbConfig()
//					.withAdapters(new StatsAdapter(), new TypeAdapter())
					.withFormatting(true);

			jsonb = JsonbBuilder.create(jc);
			
			Saints saints = jsonb.fromJson(fis, Saints.class);
			
			String reflectionToString = ToStringBuilder.reflectionToString(saints);
			System.out.println(reflectionToString);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (jsonb!=null) {
				try {
					jsonb.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
