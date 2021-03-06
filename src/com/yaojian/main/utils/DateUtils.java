package com.yaojian.main.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	public static final String SIMPLEDATEFORMAT_STRING = "";

	public static boolean compareNowDate(String dateString) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date nowDate = new Date();
		String nowDateString = format.format(nowDate);
		double offset = 1576800000000d;
		try {
			Date date = format.parse(dateString);
			nowDate = format.parse(nowDateString);
			double nowtime = nowDate.getTime() + offset;
			if (date.getTime() < (nowtime)) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
}
