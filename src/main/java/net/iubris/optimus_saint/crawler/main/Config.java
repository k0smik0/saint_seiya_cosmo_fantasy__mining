package net.iubris.optimus_saint.crawler.main;

import java.io.File;

public class Config {

    public static class Localization {

        public static final String LocalizationEN = "EN";
        public static String LocalizationDefault = LocalizationEN;
    }

    public static class Dataset {
        public static class Saints {
            public static boolean UPDATE_SAINTS_DATASET = false;

            public static boolean isSaintsDatasetToUpdate() {
                return UPDATE_SAINTS_DATASET;
            }

            public static String SAINTS_DATASET_DIR = "data";
            public static String SAINTS_DATASET_FILE = SAINTS_DATASET_DIR + File.separator + "saints" + ".json";
        }

        public static class Promotions {

            public static boolean UPDATE_PROMOTION_ITEMS_DATASET = false;

            public static boolean isPromotionItemsDatasetToUpdate() {
                return UPDATE_PROMOTION_ITEMS_DATASET;
            }
        }
    }
}
