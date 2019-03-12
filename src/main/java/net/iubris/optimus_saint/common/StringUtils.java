package net.iubris.optimus_saint.common;

public class StringUtils {

	public static final String EMPTY = "";
	public static final String UNDERSCORE = "_";
	public static final String COMMA = ",";
	public static final String DOT = ".";
	public static final String SPACE = " ";
    public static final String PLUS = "+";
    public static final String NEW_LINE = "\n";
    public static final String DASH = "#";
	
	public static String toCamelCase(String string) {
		char c[] = string.toCharArray();
		c[0] += 32;
		String s = new String(c);
		return s;
	}
	
	public static String toCapitalCase(String string) {
		char c[] = string.toLowerCase().toCharArray();
		c[0] -= 32;
		String s = new String(c);
		return s;
	}

}
