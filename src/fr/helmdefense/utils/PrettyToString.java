package fr.helmdefense.utils;

public class PrettyToString {
	private PrettyToString() {}
	
	public static void disp(Object obj) {
		System.out.println(pretty(obj));
	}
	
	public static String pretty(Object obj) {
		return obj == null ? null : pretty(obj.toString());
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
			
			switch (c) {
			case '{':
			case '[':
				prettified += c;
				indent++;
				break;
			case '}':
			case ']':
				prettified += c;
				indent--;
				break;
			case '=':
				prettified += ": ";
				break;
			default:
				if (c != ' ' || ! comma)
					prettified += c;
			}
			if (i < str.length() - 1 && (c != '[' || str.charAt(i + 1) != ']') && i != 0 && (c != ']' || str.charAt(i - 1) != '['))
				if (str.charAt(i + 1) == '}' || str.charAt(i + 1) == ']')
					prettified += "\n" + mul("\t", indent - 1);
				else if (c == '{' || c == '[' || c == ',' || (str.charAt(i + 1) != ',' && (c == '}' || c == ']')))
					prettified += "\n" + mul("\t", indent);
			
			comma = c == ',';
		}
		return prettified;
	}
	
	private static String mul(String s, int count) {
		String str = "";
		for (int i = 0; i < count; i++)
			str += s;
		return str;
	}
}