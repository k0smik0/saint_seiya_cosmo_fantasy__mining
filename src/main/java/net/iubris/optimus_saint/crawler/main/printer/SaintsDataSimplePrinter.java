package net.iubris.optimus_saint.crawler.main.printer;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.utils.Printer;

public class SaintsDataSimplePrinter extends AbstractConsoleSaintsDataPrinter {

    @Inject
    public SaintsDataSimplePrinter(Printer printer) {
        super(printer);
    }

    private final AtomicInteger counter = new AtomicInteger(0);
	@Override
	public void print(Collection<SaintData> saintDatas) {
	    counter.set(0);
	    super.print(saintDatas);
	}

    @Override
    protected String saintDataToString(SaintData saintData) {
        String s = counter.addAndGet(1)+": "+
                "id:"+saintData.id+", name: "+saintData.name ;
        return s;
    }
}