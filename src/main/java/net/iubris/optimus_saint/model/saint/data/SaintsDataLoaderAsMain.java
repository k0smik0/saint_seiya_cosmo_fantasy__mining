package net.iubris.optimus_saint.model.saint.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.json.bind.annotation.JsonbTypeAdapter;

import net.iubris.optimus_saint.model.saint.data._utils.Config;
import net.iubris.optimus_saint.model.saint.data._utils.JsonbUtils;
import net.iubris.optimus_saint.model.saint.data._utils.SaintsDataBucket;

public class SaintsDataLoaderAsMain {
		
	@JsonbTypeAdapter(SaintsDataArrayAdapter.class)
	private List<SaintData> saints;
	
	public SaintsDataLoaderAsMain() {}
	
	public SaintsDataLoaderAsMain(List<SaintData> saints) {
		this.saints = saints;
	}

	public static void main(String[] args) {		
//		JsonbConfig jc = new JsonbConfig().withFormatting(true);
		
		try ( FileInputStream fis = new FileInputStream("data"+File.separator+"saints.json"); 
				/*Jsonb jsonb = JsonbBuilder.create(jc);*/ ) {
//			Jsonb jsonb = JsonbBuilder.create(jc);
			
//			Config.UPDATE_ITEMS = true;
			
			JsonbUtils.INSTANCE.getParser().fromJson(fis, SaintsDataLoaderAsMain.class);
			
			SaintsDataBucket.INSTANCE.getSaints().stream()
			.sorted(Comparator.comparing(SaintData::getId))
			.forEach(sd->{
//				String reflectionToString = ToStringBuilder.reflectionToString(sd);
//				System.out.println(reflectionToString+"\n");
//				System.out.println( sd.name/*.value*/+" - id:"+sd.id );
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
	}
}