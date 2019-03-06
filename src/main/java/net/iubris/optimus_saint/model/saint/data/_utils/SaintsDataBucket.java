package net.iubris.optimus_saint.model.saint.data._utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.iubris.optimus_saint.model.saint.data.SaintData;
import net.iubris.optimus_saint.model.saint.data.promote.SaintPromoteData;

// TODO use annotation
//@Singleton
public enum SaintsDataBucket {
	
	INSTANCE;
	
	private final Map<String, SaintData> idToSaintMap = new HashMap<>();
	private final Map<String, SaintPromoteData> idToSaintPromoteMap = new HashMap<>();

//	public static List<SaintData> saints;

	public Collection<SaintData> getSaints() {
		return idToSaintMap.values();
	}

	public void setSaints(List<SaintData> saintsData) {
		saintsData.forEach(sd->{
			idToSaintMap.put(sd.id, sd);
			SaintPromoteData saintPromoteData = new SaintPromoteData();
			saintPromoteData.id = sd.id;
			idToSaintPromoteMap.put(sd.id, saintPromoteData);
		});
		
//		saintsToPromoteMap.keySet().clear();
//		saintsToPromoteMap.keySet().addAll(saintsData);
	}
}
