package net.iubris.optimus_saint.crawler.main;

import java.util.Collection;

import net.iubris.optimus_saint.crawler.model.SaintData;

interface Printer {
	void print(Collection<SaintData> saintDatas);
}