package net.iubris.optimus_saint.crawler.model;

import java.util.List;

import javax.json.bind.annotation.JsonbTypeAdapter;

import net.iubris.optimus_saint.crawler.adapters.saints.SaintsDataArrayAdapter;

public class SaintsData {
		
	@JsonbTypeAdapter(SaintsDataArrayAdapter.class)
	private List<SaintData> saints;
	
	public SaintsData() {}
	
	public SaintsData(List<SaintData> saints) {
		this.saints = saints;
	}
	
}