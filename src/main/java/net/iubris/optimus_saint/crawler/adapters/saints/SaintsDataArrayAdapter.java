package net.iubris.optimus_saint.crawler.adapters.saints;

import java.util.List;
import java.util.stream.Collectors;

import javax.json.JsonArray;
import javax.json.JsonObject;

import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.model.promote.SaintsPromoteDataLoader;

public class SaintsDataArrayAdapter extends AbstractArrayAdapter<List<SaintData>> {

	// TODO use this
	// @Inject
	// SaintsDataBucketPatch saintsDataBucketPatch;

//    private final Printer printer;

	// no more used
//    private final SaintsDataBucket saintsDataBucket;

	private final SaintDataAdapter saintDataAdapter;
	private final SaintsPromoteDataLoader saintsDataUpgrader;

	public SaintsDataArrayAdapter() {
//        this.saintsDataBucket = ProviderNotDI.INSTANCE.getSaintsDataBucket();
		this.saintDataAdapter = new SaintDataAdapter();
		this.saintsDataUpgrader = SaintsPromoteDataLoader.INSTANCE;
	}

	@Override
	public List<SaintData> adaptFromJson(final JsonArray jsonArray) throws Exception {
//        saintsDataUpgrader.reset();

		List<SaintData> saints = jsonArray.stream()
			.filter(jv -> {
				JsonObject saintAsJsonObject = jv.asJsonObject();
				boolean incomplete = saintAsJsonObject.getBoolean("incomplete");
				String id = saintAsJsonObject.getString("id");
//            JsonObject jsonObjectName = saintAsJsonObject.getString("name");
				String name = saintAsJsonObject.getString("name");
				String fyi_name = saintAsJsonObject.getString("fyi_name");

				// OLD
				/*
				 * try {
				 * String name = new NameAdapter().adaptFromJson(jsonObjectName);
				 * if (incomplete) {
				 * String s = "";
				 * if (StringUtils.isNotBlank(name)) {
				 * s = name + " :: " + fyi_name;
				 * } else {
				 * s = saintAsJsonObject.getString("fyi_name");
				 * if (StringUtils.isBlank(s)) {
				 * s = id;
				 * }
				 * }
				 * //printer.println("skipping '" + s + "' (" + id + ")" + ": incomplete");
				 * }
				 * } catch (Exception e) {
				 * System.err.println(e.getMessage());
				 * }
				 */

				// NEW
				/*
				 * if (incomplete) {
				 * String s = "";
				 * if (StringUtils.isNotBlank(name)) {
				 * s = name;
				 * } else if (StringUtils.isBlank(fyi_name)) {
				 * s = fyi_name;
				 * } else {
				 * s = id;
				 * }
				 * printer.println("skipping '" + s + "' (" + id + ")" + ": incomplete");
				 * }
				 */

				return !incomplete;
			})
			.map(jv -> {
				JsonObject saint = jv.asJsonObject();
				SaintData saintData = new SaintData();
				try {
					saintData = saintDataAdapter.adaptFromJson(saint);
					saintsDataUpgrader.handleItemToUpdate(saintData.id);
					return saintData;
				} catch (Exception e) {
//                System.err.println(e.getMessage());
					System.err.println(e.getMessage());
				}
				return saintData;
			})
			.filter(sd -> !sd.incomplete)
			.collect(Collectors.toList());

//        saintsDataBucket.setSaints(saints);
//
//        saintsDataUpgrader.prepare(saints.size()).start();

		System.out.println("returning " + saints.size() + " saints");

		return saints;
	}

}
