package com.mapbar.carlimit.util;

public class IpUtil {
	public static String getIpNoNull(String str) {
		if (str == null || str.equals("null") || str.length() == 0) {
			str = " ";
		}
		return str;
	}

	public static String getIpNoNull(String str, String defaultValue) {
		if (str == null || str.equals("null") || str.length() == 0) {
			str = defaultValue;
		}
		return str;
	}
}
