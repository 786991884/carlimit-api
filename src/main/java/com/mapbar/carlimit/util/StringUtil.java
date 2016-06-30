package com.mapbar.carlimit.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	public static String getNoNull(String str) {
		if (str == null || str.equals("null")) {
			str = "";
		}
		return str;
	}

	public static String getNoNull(String str, String deafult) {
		if (str == null || str.equals("null") || str.length() == 0) {
			str = deafult;
		}
		return str;
	}

	// 字符串转换为xml标准格式
	public static String getXmlFormat(String infomation) {

		String tmpInfo = (infomation == null) ? "" : infomation;
		try {
			tmpInfo = tmpInfo.replaceAll("\\|", " ");
			// tmpInfo = tmpInfo.replaceAll("\\n", " ");
			tmpInfo = tmpInfo.replaceAll("\\r", " ");
			// 去除全部html标签
			Pattern p = Pattern.compile("<[a-z|A-Z|/|\"|'| |=|:]+>", Pattern.CASE_INSENSITIVE);// 正则表达式，后面的参数指定忽略大小写
			Matcher m = p.matcher(tmpInfo);// 匹配的字符串
			tmpInfo = m.replaceAll("");

			// 去除全部html特殊字符
			p = Pattern.compile("&[a-zA-Z]+;", Pattern.CASE_INSENSITIVE);// 正则表达式，后面的参数指定忽略大小写
			m = p.matcher(tmpInfo);// 匹配的字符串
			tmpInfo = m.replaceAll(" ");

			// 去除全部Null字符
			p = Pattern.compile("null", Pattern.CASE_INSENSITIVE);// 正则表达式，后面的参数指定忽略大小写
			m = p.matcher(tmpInfo);// 匹配的字符串
			tmpInfo = m.replaceAll(" ");

		} catch (Exception e) {
		}
		if (tmpInfo.length() < 1)
			tmpInfo = " ";

		return tmpInfo;
	}

	// 字符串转换为int
	public static int getIntFormString(String xstr, int defaultNum) {
		int t = defaultNum;
		try {
			t = Integer.parseInt(xstr);
		} catch (Exception e) {
		}
		return t;
	}

	// 字符串转换为double
	public static double getDoubleFormString(String xstr, double defaultNum) {
		double t = defaultNum;
		try {
			t = Double.parseDouble(xstr);
		} catch (Exception e) {
		}
		return t;
	}

	public static String toNumber(String str) {
		if (str.indexOf(".") > 0) {
			str = str.substring(0, str.indexOf("."));
		}
		return str;
	}

	// 快速上线特色数据xml格式获取
	public static String featuresDataXml(String str) {
		str = str.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"GB2312\" ?>\n");
		sb.append("<root>");
		sb.append(str);
		sb.append("</root>");

		return sb.toString();
	}

	public static String encodeStr(String str, String encode) {
		try {
			return new String(str.getBytes("ISO-8859-1"), encode);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String encodeStr(String str) {
		return encodeStr(str, "UTF-8");
	}
}
