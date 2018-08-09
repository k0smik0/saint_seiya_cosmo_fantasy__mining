package net.iubris.optimus_saint.model.saint.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.annotation.JsonbTypeAdapter;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class SaintsData {		
		
	@JsonbTypeAdapter(SaintsDataArrayAdapter.class)
	private List<SaintData> saints;
	
	public static void main(String[] args) {		
		JsonbConfig jc = new JsonbConfig().withFormatting(true);
		
		try ( FileInputStream fis = new FileInputStream("data"+File.separator+"saints.json"); Jsonb jsonb = JsonbBuilder.create(jc); ) {
			jsonb.fromJson(fis, SaintsData.class);
			System.out.println("saintsData.saints: "+SaintsDataBucketPatch.saints);
			
			SaintsDataBucketPatch.saints.stream()
			.forEach(sd->{ 
				String reflectionToString = ToStringBuilder.reflectionToString(sd);
				System.out.println(reflectionToString+"\n");
			} );
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}