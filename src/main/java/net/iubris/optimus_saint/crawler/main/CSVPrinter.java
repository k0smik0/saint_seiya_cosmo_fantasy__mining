package net.iubris.optimus_saint.crawler.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import net.iubris.optimus_saint.crawler.model.SaintData;

public class CSVPrinter implements Printer {

	private static final String CSV_FILENAME_PREFIX = "saints_data__";
	
	private static final String DATE_FORMATTER = "yyyyMMddHHmmss";
	
	private final CustomMappingStrategy<SaintData> mappingStrategy = new CustomMappingStrategy<>();
	
	private final DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATTER);

	@Override
	public void print(Collection<SaintData> saintDatas) {
		
		mappingStrategy.setType(SaintData.class);
		
		File reportFile = new File(CSV_FILENAME_PREFIX+dateFormatter.format(new Date()));
		
		try (Writer writer = new PrintWriter(reportFile);) {
			
			StatefulBeanToCsv<SaintData> beanToCsv = new StatefulBeanToCsvBuilder<SaintData>(writer)
				    .withMappingStrategy(mappingStrategy)
				    .build();
				
			ArrayList<SaintData> list = new ArrayList<>(saintDatas);
			beanToCsv.write(list);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (CsvDataTypeMismatchException e) {
			e.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	}

	static class CustomMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {
		private static final String[] HEADER = new String[] { "TradeID", "GWML GUID", "MXML GUID", "GWML File",
			"MxML File", "MxML Counterparty", "GWML Counterparty" };

		@Override
		public String[] generateHeader() {
			return HEADER;
		}
	}

}
