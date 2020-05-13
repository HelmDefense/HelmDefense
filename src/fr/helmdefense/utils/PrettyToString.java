package fr.helmdefense.utils;

public class PrettyToString {
	private PrettyToString() {}
	
	public static String pretty(Object obj) {
		return obj == null ? "null" : pretty(obj.toString());
	}
	
	public static String pretty(String str) {
		if (str == null)
			return "null";
		String prettified = "";
		boolean comma = false;
		char c;
		int indent = 0;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			
			if (c == '{' || c == '[') {
				prettified += c;
				indent++;
			}
			else if (c == '}' || c == ']') {
				indent--;
				prettified += c;
			}
			else if (c == '=')
				prettified += ": ";
			else if (c != ' ' || ! comma)
				prettified += c;
			if (i < str.length() - 1)
				if (str.charAt(i + 1) == '}' || str.charAt(i + 1) == ']')
					prettified += "\n" + mul("\t", indent - 1);
				else if (c == '{' || c == '[' || c == ',' || (str.charAt(i + 1) != ',' && (c == '}' || c == ']')))
					prettified += "\n" + mul("\t", indent);
			
			comma = c == ',';
		}
		return prettified;
	}
	
	public static String pretty(int i) {
		return Integer.toString(i);
	}
	
	public static String pretty(long l) {
		return Long.toString(l);
	}
	
	public static String pretty(float f) {
		return Float.toString(f);
	}
	
	public static String pretty(double d) {
		return Double.toString(d);
	}
	
	public static String pretty(boolean b) {
		return Boolean.toString(b);
	}
	
	public static String pretty(char c) {
		return Character.toString(c);
	}
	
	public static void disp(Object obj) {
		System.out.println(pretty(obj));
	}
	
	public static void disp(String str) {
		System.out.println(pretty(str));
	}
	
	public static void disp(int i) {
		System.out.println(pretty(i));
	}
	
	public static void disp(long l) {
		System.out.println(pretty(l));
	}
	
	public static void disp(float f) {
		System.out.println(pretty(f));
	}
	
	public static void disp(double d) {
		System.out.println(pretty(d));
	}
	
	public static void disp(boolean b) {
		System.out.println(pretty(b));
	}
	
	public static void disp(char c) {
		System.out.println(pretty(c));
	}
	
	private static String mul(String s, int count) {
		String str = "";
		for (int i = 0; i < count; i++)
			str += s;
		return str;
	}
}