package net.iubris.optimus_saint.model.saint.data._utils;

public class Config {

	public static final String LocalizationEN = "EN";
	
	public static String LocalizationDefault = LocalizationEN;
	
	public static boolean UPDATE_SAINTS_DATASET = false;
	
	public static boolean isSaintsDatasetToUpdate() {
		return UPDATE_SAINTS_DATASET;
	}
	
	public static boolean UPDATE_PROMOTION_ITEMS_DATASET = false;
	
	public static boolean isPromotionItemsDatasetToUpdate() {
		return UPDATE_PROMOTION_ITEMS_DATASET;
	}
}
