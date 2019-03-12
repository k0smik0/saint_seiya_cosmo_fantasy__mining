package net.iubris.optimus_saint.crawler.main;

import com.github.jankroken.commandline.annotations.LongSwitch;
import com.github.jankroken.commandline.annotations.Option;
import com.github.jankroken.commandline.annotations.ShortSwitch;
import com.github.jankroken.commandline.annotations.Toggle;

public class Arguments {
	boolean download;
	boolean load;
	boolean printToCSV;
	boolean print;
	boolean spreadsheet;
	boolean help;

	@Option
	@LongSwitch("download")
	@ShortSwitch("d")
	@Toggle(true)
	public void setDownload(boolean download) {
		this.download = download;
	}

	@Option
	@LongSwitch("load")
	@ShortSwitch("l")
	@Toggle(true)
	public void setLoad(boolean load) {
		this.load = load;
	}

	@Option
	@LongSwitch("print")
	@ShortSwitch("p")
	@Toggle(true)
	public void setPrint(boolean print) {
		this.print = print;
	}

	@Option
	@LongSwitch("spreadsheet")
	@ShortSwitch("s")
	@Toggle(true)
	public void setSpreadsheet(boolean spreadsheet) {
		this.spreadsheet = spreadsheet;
	}

	@Option
	@LongSwitch("csv")
	@ShortSwitch("c")
	@Toggle(true)
	public void setPrintToCSV(boolean printToCSV) {
		this.printToCSV = printToCSV;
	}
	
	@Option
	@LongSwitch("help")
	@ShortSwitch("h")
	@Toggle(true)
	public void setHelp(boolean help) {
		this.help = help;
	}

	public boolean areAllFalse() {
		return !download && !load && !printToCSV && !print && !spreadsheet;
	}
}