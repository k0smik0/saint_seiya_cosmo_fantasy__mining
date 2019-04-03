package net.iubris.optimus_saint.crawler.main.printer;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;

import net.iubris.optimus_saint.crawler.model.SaintData;
import net.iubris.optimus_saint.crawler.utils.Printer;

/**
 * @author Massimiliano Leone - massimiliano.leone@iubris.net
 * @since 12/mar/2019
 * @version 1
 *
 */
public abstract class AbstractConsoleSaintsDataPrinter implements SaintsDataPrinter {

    protected final Printer printer;
    
    public AbstractConsoleSaintsDataPrinter(Printer printer) {
        this.printer = printer;
    }
    
    @Override
    public void print(Collection<SaintData> saintDatas) {
        saintsDataToStringStream(saintDatas)
            .forEach(sd -> {
                // String reflectionToString = ToStringBuilder.reflectionToString(sd);
                // System.out.println(reflectionToString+"\n");
                // System.out.println( sd.name/*.value*/+" - id:"+sd.id );
//                print(sd);
                printer.println(sd);
            });
    }
    
    protected Stream<String> saintsDataToStringStream(Collection<SaintData> saintDatas) {
        Stream<String> stringStream = saintDatas.stream()
        .sorted(Comparator.comparing(SaintData::getId))
        .map(sd->saintDataToString(sd));
        return stringStream;
    }
    
    protected abstract String saintDataToString(SaintData saintData);
}
