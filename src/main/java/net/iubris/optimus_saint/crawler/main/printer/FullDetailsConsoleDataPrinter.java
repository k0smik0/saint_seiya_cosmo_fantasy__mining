package net.iubris.optimus_saint.crawler.main.printer;

import javax.inject.Inject;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.utils.Printer;

public class FullDetailsConsoleDataPrinter extends AbstractConsoleSaintsDataPrinter {

    @Inject
    public FullDetailsConsoleDataPrinter(Printer printer) {
        super(printer);
    }

    @Override
    protected String saintDataToString(SaintData saintData) {
        return ReflectionToStringBuilder.toString(saintData);
    }
}
