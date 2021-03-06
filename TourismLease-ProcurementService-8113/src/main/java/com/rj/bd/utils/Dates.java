package com.rj.bd.utils;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 获得当前日期
 */
@SuppressWarnings("serial")
public final class Dates implements Serializable {

	public static final String CurrentTime() {
		String curTime = "";
		// 格式化时间开始
		SimpleDateFormat formatter;
		Date currentDate = new Date();
		formatter = new SimpleDateFormat("yyyy-MM-dd");
		currentDate = Calendar.getInstance().getTime();
		// 格式化时间结束
		curTime = formatter.format(currentDate);
		return curTime;
	}

	public static final String CurrentYMDTime() {
		String curTime = "";
		// 格式化时间开始
		SimpleDateFormat formatter;
		Date currentDate = new Date();
		formatter = new SimpleDateFormat("yyyy年MM月dd日");
		currentDate = Calendar.getInstance().getTime();
		// 格式化时间结束
		curTime = formatter.format(currentDate);
		return curTime;
	}

	public static final String CurrentYMDTime(String date) {
		String curTime = "";
		// 格式化时间开始
		SimpleDateFormat formatter;
		Date currentDate = new Date();
		formatter = new SimpleDateFormat("yyyy年MM月dd日");
		try {
			currentDate = formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 格式化时间结束
		curTime = formatter.format(currentDate);
		return curTime;
	}

	public static final String CurrentYMDHSMTime() {
		String curTime = "";
		// 格式化时间开始
		SimpleDateFormat formatter;
		Date currentDate = new Date();
		formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		currentDate = Calendar.getInstance().getTime();
		// 格式化时间结束
		curTime = formatter.format(currentDate);
		return curTime;
	}

	public static final String N() {
		String curTime = "";
		// 格式化时间开始
		SimpleDateFormat formatter;
		Date currentDate = new Date();
		formatter = new SimpleDateFormat("yyyy");
		currentDate = Calendar.getInstance().getTime();
		// 格式化时间结束
		curTime = formatter.format(currentDate);
		return curTime;
	}
}
