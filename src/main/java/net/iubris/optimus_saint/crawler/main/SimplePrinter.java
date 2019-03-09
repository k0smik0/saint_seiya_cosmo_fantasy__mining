package net.iubris.optimus_saint.crawler.main;

import java.util.Collection;
import java.util.Comparator;

import net.iubris.optimus_saint.crawler.model.SaintData;

class SimplePrinter implements Printer {
	
	@Override
	public void print(Collection<SaintData> saintDatas) {
		saintDatas.stream()
		.sorted(Comparator.comparing(SaintData::getId))
		.forEach(sd->{
//			String reflectionToString = ToStringBuilder.reflectionToString(sd);
//			System.out.println(reflectionToString+"\n");
//			System.out.println( sd.name/*.value*/+" - id:"+sd.id );
			print(sd);
		});
	}
		
		private static final String SEPARATOR = " # ";
		private static void print(SaintData saintData) {
			String s = 
					saintData.id
				+SEPARATOR+saintData.name
				+SEPARATOR+saintData.description
//				+SEPARATOR
				+saintData.skills.first.description.replace("\n", "")
				+SEPARATOR+saintData.skills.second.description.replace("\n", "")
				+SEPARATOR+saintData.skills.third.description.replace("\n", "")
				+SEPARATOR+saintData.skills.fourth.description.replace("\n", "")
				+SEPARATOR+saintData.skills.seventhSense.description.replace("\n", "")
//				.replace(SEPARATOR+SEPARATOR, SEPARATOR)
				.replaceAll("/[#]{2,}/", "#");
			System.out.println(s);
		}		
	}