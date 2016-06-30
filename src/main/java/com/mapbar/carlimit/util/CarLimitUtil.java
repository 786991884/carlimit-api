package com.mapbar.carlimit.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

public class CarLimitUtil {

	public static final FastDateFormat dateFormat = FastDateFormat.getInstance("yyyyMMdd");
	public static final FastDateFormat dateWeek = FastDateFormat.getInstance("E");

	public static String getToDate() {
		Date date = new Date();
		String sysdate = dateFormat.format(Calendar.getInstance().getTime());
		try {
			date = dateFormat.parse(sysdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateWeek.format(date);
	}

	public static String getToDate(String sysdate) {
		Date date = new Date();
		try {
			date = dateFormat.parse(sysdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return dateWeek.format(date);
	}

}
