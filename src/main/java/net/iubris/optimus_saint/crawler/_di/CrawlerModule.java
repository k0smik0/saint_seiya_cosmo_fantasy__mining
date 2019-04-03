package net.iubris.optimus_saint.crawler._di;

import com.google.inject.AbstractModule;

import net.iubris.optimus_saint.crawler.main.printer.FullDetailsConsoleDataPrinter;
import net.iubris.optimus_saint.crawler.main.printer.SaintsDataPrinter;
import net.iubris.optimus_saint.crawler.utils.Printer;
import net.iubris.optimus_saint.crawler.utils.SimplePrinter;

/**
 * @author Massimiliano Leone - massimiliano.leone@iubris.net
 * @since 11/mar/2019
 * @version 1
 *
 */
public class CrawlerModule extends AbstractModule {
    
    @Override
    protected void configure() {
        bind(Printer.class).to(SimplePrinter.class);
//        bind(SaintsDataPrinter.class).to(SaintsDataSimplePrinter.class);
        bind(SaintsDataPrinter.class).to(FullDetailsConsoleDataPrinter.class);
    }
}
