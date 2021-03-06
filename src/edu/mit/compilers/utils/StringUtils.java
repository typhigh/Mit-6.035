package edu.mit.compilers.utils;

public class StringUtils {
	
	public static Long parseHexInt(String value) {
//		System.out.print(value);
		if (value.contains("0x") || value.contains("0X")) {
			value = value.substring(2);
		}
		long ret = 0;
		for (int i = 0; i < value.length(); ++i) {
			char now = value.charAt(i);
			int plus;
			if (now >= '0' && now <= '9') {
				plus = now - '0';
			} else if (now >= 'a' && now <= 'f') {
				plus = now - 'a' + 10;
			} else if (now >= 'A' && now <= 'F') {
				plus = now - 'A' + 10;
			} else {
				throw new RuntimeException("Unexpected character of value at position: " + i + " which is: " + now);
			}
			ret = ret * 16 + plus;
		}
		return ret;
	}

	public static String repeat(String value, int count) {
		// use value.repeate(count) more better but my java not supports it
		StringBuilder ret = new StringBuilder();
		for (int i = 0; i < count; ++i) {
			ret.append(value);
		}
		return ret.toString();
	}
}
