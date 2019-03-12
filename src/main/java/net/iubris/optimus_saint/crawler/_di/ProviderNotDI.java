package net.iubris.optimus_saint.crawler._di;

import com.google.inject.Injector;

import net.iubris.optimus_saint.crawler.bucket.SaintsDataBucket;
import net.iubris.optimus_saint.crawler.utils.Printer;

public enum ProviderNotDI {

    INSTANCE;
    
    private Injector injector;
    
    public void setInjector(Injector injector) {
        this.injector = injector;
    }
    
    public SaintsDataBucket getSaintsDataBucket() {
        return injector.getInstance(SaintsDataBucket.class);
    }

    public Printer getPrinter() {
        return injector.getInstance(Printer.class);
    }
}
