package net.iubris.optimus_saint.crawler.utils;

/**
 * @author Massimiliano Leone - massimiliano.leone@iubris.net
 * @since 11/mar/2019
 * @version 1
 *
 */
public class SimplePrinter implements Printer {

    @Override
    public void print(Object o) {
        System.out.print(o);   
    }

    @Override
    public void println(Object o) {
        System.out.println(o);
    }
    
}
