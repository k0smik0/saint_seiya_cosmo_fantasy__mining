package net.iubris.optimus_saint.model.saint.data;

import java.util.List;

//@Singleton
public class SaintsDataBucketPatch {

	public static List<SaintData> saints;

	public static SaintsDataLoader getSaints() {
		return new SaintsDataLoader(saints);
	}
}
