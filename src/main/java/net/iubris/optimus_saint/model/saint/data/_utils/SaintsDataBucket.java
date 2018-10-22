package net.iubris.optimus_saint.model.saint.data._utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.iubris.optimus_saint.model.saint.data.SaintData;
import net.iubris.optimus_saint.model.saint.data.promote.SaintPromoteData;

// TODO use annotation
//@Singleton
public enum SaintsDataBucket {
	
	INSTANCE;
	
	private final Map<SaintData, SaintPromoteData> saintsToPromoteMap = new HashMap<>(); 

//	public static List<SaintData> saints;

	public Set<SaintData> getSaints() {
		return saintsToPromoteMap.keySet();
	}

	public void setSaints(List<SaintData> saintsData) {
		saintsToPromoteMap.keySet().clear();
		saintsToPromoteMap.keySet().addAll(saintsData);
	}
}
