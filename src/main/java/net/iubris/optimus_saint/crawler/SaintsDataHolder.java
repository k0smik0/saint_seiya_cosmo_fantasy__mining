package net.iubris.optimus_saint.crawler;

import java.util.List;

import javax.json.bind.annotation.JsonbTypeAdapter;

public class SaintsDataHolder {
		
	@JsonbTypeAdapter(SaintsDataArrayAdapter.class)
	private List<SaintData> saints;
	
	public SaintsDataHolder() {}
	
	public SaintsDataHolder(List<SaintData> saints) {
		this.saints = saints;
	}
	
	public void loadFromDataset() {
		
	}
	


	/*public static void mainNO(String[] args) {		
//		JsonbConfig jc = new JsonbConfig().withFormatting(true);
		
		try ( FileInputStream fis = new FileInputStream("data"+File.separator+"saints.json"); 
//				Jsonb jsonb = JsonbBuilder.create(jc); 
				) {
//			Jsonb jsonb = JsonbBuilder.create(jc);
			
			Config.UPDATE_PROMOTION_ITEMS_DATASET = true;
			
			JsonbUtils.INSTANCE.getParser().fromJson(fis, SaintsDataHolder.class);
			
			SaintsDataBucket.INSTANCE.getSaints().stream()
			.sorted(Comparator.comparing(SaintData::getId))
			.forEach(sd->{
//				String reflectionToString = ToStringBuilder.reflectionToString(sd);
//				System.out.println(reflectionToString+"\n");
//				System.out.println( sd.name .value+" - id:"+sd.id );
				guildPrint(sd);
			});
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				JsonbUtils.INSTANCE.closeParser();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		System.exit(0);
	}
	
	private static final String SEPARATOR = "#";
	private static void guildPrint(SaintData saintData) {
		String s = 
				saintData.id
			+SEPARATOR+saintData.name
			+SEPARATOR+saintData.description
//			+SEPARATOR
			+saintData.skills.first.description.replace("\n", "")
			+SEPARATOR+saintData.skills.second.description.replace("\n", "")
			+SEPARATOR+saintData.skills.third.description.replace("\n", "")
			+SEPARATOR+saintData.skills.fourth.description.replace("\n", "")
			+SEPARATOR+saintData.skills.seventhSense.description.replace("\n", "")
//			.replace(SEPARATOR+SEPARATOR, SEPARATOR)
			.replaceAll("/[#]{2,}/", "#");
		System.out.println(s);
	}*/
}