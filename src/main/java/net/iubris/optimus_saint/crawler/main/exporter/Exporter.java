package net.iubris.optimus_saint.crawler.main.exporter;

import java.util.Collection;

import net.iubris.optimus_saint.crawler.model.SaintData;

public interface Exporter<R> {

	public R export(Collection<SaintData> saintDataCollection, boolean overwrite);
	
	public static enum ExporterStatus {
	    OK,
	    KO;
	}
}
