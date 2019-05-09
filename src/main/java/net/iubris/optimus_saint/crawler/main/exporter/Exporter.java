package net.iubris.optimus_saint.crawler.main.exporter;

import java.util.Collection;

import net.iubris.optimus_saint.crawler.model.SaintData;

public interface Exporter<R> {

	R export(Collection<SaintData> saintDataCollection, boolean overwrite);
	
	TestStatus test(Collection<SaintData> saintDataCollection);
	
	public static enum ExporterStatus {
	    OK,
	    KO;
	}
	
	public static enum TestStatus {
        OK,
        KO;
    }
}
