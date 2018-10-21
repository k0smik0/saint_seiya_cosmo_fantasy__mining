package net.iubris.optimus_saint.model.saint.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.annotation.JsonbTypeAdapter;

public class SaintsDataLoader {
		
	@JsonbTypeAdapter(SaintsDataArrayAdapter.class)
	private List<SaintData> saints;
	
	public SaintsDataLoader() {}
	
	public SaintsDataLoader(List<SaintData> saints) {
		this.saints = saints;
	}

	public static void main(String[] args) {		
		JsonbConfig jc = new JsonbConfig().withFormatting(true);
		
		try ( FileInputStream fis = new FileInputStream("data"+File.separator+"saints.json"); 
				Jsonb jsonb = JsonbBuilder.create(jc); ) {
			Config.UPDATE_ITEMS = true;
			
			SaintsDataLoader saintsDataLoader = jsonb.fromJson(fis, SaintsDataLoader.class);
			saintsDataLoader = SaintsDataBucketPatch.getSaints(); 
			saintsDataLoader.saints.stream()
			.forEach(sd->{
//				String reflectionToString = ToStringBuilder.reflectionToString(sd);
//				System.out.println(reflectionToString+"\n");
				System.out.println( sd.name.value+" - id:"+sd.id );
			} );
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		System.exit(0);
	}
}