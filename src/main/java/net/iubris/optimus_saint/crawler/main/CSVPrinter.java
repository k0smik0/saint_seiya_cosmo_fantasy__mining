package net.iubris.optimus_saint.crawler.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import com.opencsv.CSVWriter;

import net.iubris.optimus_saint.crawler.model.SaintData;

public class CSVPrinter implements Printer {

	private static final String CSV_FILENAME_PREFIX = "saints_data__";
	
	private static final String DATE_FORMATTER = "yyyyMMddHHmmss";
	
//	private final CustomMappingStrategy<SaintData> mappingStrategy = new CustomMappingStrategy<>();
	
	private final DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATTER);

	@Override
	public void print(Collection<SaintData> saintDatas) {
		
		File csvFile = new File(CSV_FILENAME_PREFIX+dateFormatter.format(new Date()));
		
		try (Writer writer = new PrintWriter(csvFile);) {
		
			/*mappingStrategy.setType(SaintData.class);
			StatefulBeanToCsv<SaintData> beanToCsv = new StatefulBeanToCsvBuilder<SaintData>(writer)
				    .withMappingStrategy(mappingStrategy)
				    .build();
				
			ArrayList<SaintData> list = new ArrayList<>(saintDatas);
			beanToCsv.write(list);*/
			
			CSVWriter csvWriter = new CSVWriter(new FileWriter("yourfile.csv"), '#');
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}

/*	static class CustomMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {
		private static final String[] HEADER = new String[] { "TradeID", "GWML GUID", "MXML GUID", "GWML File",
			"MxML File", "MxML Counterparty", "GWML Counterparty" };

		@Override
		public String[] generateHeader() {
			return HEADER;
		}
	}*/

}
