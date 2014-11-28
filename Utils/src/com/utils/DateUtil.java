package com.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期工具类
 * 
 * @author Wbaokang
 */
public class DateUtil {

	/**
	 * 获得更新时间yyyy-MM-dd HH:mm
	 * 
	 * @param time
	 * @return
	 */
	public static String getDate(long time) {
		Date date = new Date(time);
		return getString(date);
	}
	
	public static long diffTime(String sysTime,String nowTime){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			return dateFormat.parse(sysTime).getTime()-dateFormat.parse(nowTime).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 得到下一天的字符串表示
	 * @param dateStr
	 * @return 格式 yyyy-MM-dd 如：“2013-12-31”
	 */
	public static String getNextDayStr(String dateStr){
		Date date = getDateObj(dateStr, "yyyy-MM-dd");
		Date dt = new Date(date.getTime()+1*24*60*60*1000);
		return getYMdString(dt);
	}
	/**
	 * 得到前一天的字符串表示
	 * @param dateStr
	 * @return 格式 yyyy-MM-dd 如：“2013-12-31”
	 */
	public static String getPreviousDay(String dateStr){
		Date date = getDateObj(dateStr, "yyyy-MM-dd");
		Date dt = new Date(date.getTime()-1*24*60*60*1000);
		return getYMdString(dt);
	}
	
	/**
	 * 判断开始时间是否晚于结束时间
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static boolean isAfter(String beginTime,String endTime){
		Date date = getDateObj(beginTime, "yyyy-MM-dd HH:mm");
		Date date2 = getDateObj(endTime, "yyyy-MM-dd HH:mm");
		
		if(date == null)
		{
			return false;
		}
		
		if(date2==null)
		{
			return true;
		}
		return date.after(date2);
	}
	
	/**
	 * 获得备份时间"MMddHHmm"
	 * 
	 * @param time
	 * @return
	 */
	public static String getYMdDate(long time) {
		Date date = new Date(time);
		return getYMdString(date);
	}

	public static Date addDate(Date dt1, Date dt2) {
		long dt1ms = dt1.getTime();
		long dt2ms = dt2.getTime();
		return new Date(dt1ms + dt2ms);
	}

	public static Date addDay(Date dt, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		cal.add(5, days);
		return cal.getTime();
	}

	public static Date addDuration(Date dt, int yr, int mon) {
		Date result = dt;
		result = addMonth(result, mon);
		result = addYear(result, yr);
		return result;
	}

	public static Date addDuration(Date dt, int yr, int mon, int dy) {
		dt = addDay(dt, dy);
		dt = addMonth(dt, mon);
		dt = addYear(dt, yr);
		return dt;
	}

	public static Date addDuration(Date dt, int yr, int mon, int dy, int hr) {
		dt = addHour(dt, hr);
		dt = addDay(dt, dy);
		dt = addMonth(dt, mon);
		dt = addYear(dt, yr);
		return dt;
	}

	public static Date addDuration(Date dt, int yr, int mon, int dy, int hr,
			int min) {
		dt = addMinute(dt, min);
		dt = addHour(dt, hr);
		dt = addDay(dt, dy);
		dt = addMonth(dt, mon);
		dt = addYear(dt, yr);
		return dt;
	}

	public static Date addDuration(Date dt, int yr, int mon, int dy, int hr,
			int min, int sec) {
		dt = addSecond(dt, sec);
		dt = addMinute(dt, min);
		dt = addHour(dt, hr);
		dt = addDay(dt, dy);
		dt = addMonth(dt, mon);
		dt = addYear(dt, yr);
		return dt;
	}

	public static Date addHour(Date dt, long hours) {
		long dtms = dt.getTime();
		long hrms = hours * 60L * 60L * 1000L;
		long newdtms = dtms + hrms;
		return new Date(newdtms);
	}

	public static Date addMinute(Date dt, long minutes) {
		long dtms = dt.getTime();
		long minms = minutes * 60L * 1000L;
		long newdtms = dtms + minms;
		return new Date(newdtms);
	}

	public static Date addMonth(Date dt, int months) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int month = cal.get(2);
		month += months;
		int year = month / 12;
		month %= 12;
		cal.set(2, month);
		if (year != 0) {
			int oldYear = cal.get(1);
			cal.set(1, year + oldYear);
		}
		return cal.getTime();
	}

	public static Date addSecond(Date dt, long seconds) {
		long dtms = dt.getTime();
		long secms = seconds * 1000L;
		long newdtms = dtms + secms;
		return new Date(newdtms);
	}

	public static Date addYear(Date dt, int years) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int oldyear = cal.get(1);
		cal.set(1, years + oldyear);
		return cal.getTime();
	}

	/**
     * 
     */
	public static long diffDate(int interval, Date dt1, Date dt2) {
		Calendar cal = Calendar.getInstance();
		int offset = cal.getTimeZone().getRawOffset();
		long time1 = dt1.getTime() + offset;
		long time2 = dt2.getTime() + offset;
		switch (interval) {
		case 0:
			return getYear(dt1) - getYear(dt2);
		case 1:
			return (getYear(dt1) - getYear(dt2)) * 12
					+ (getMonth(dt1) - getMonth(dt2));
		case 2:
			int weekDay1 = getWeekDay(dt1);
			int weekDay2 = getWeekDay(dt2);
			Date week1Start = addDay(dt1, -1 * weekDay1);
			Date week2Start = addDay(dt2, -1 * weekDay2);
			return diffDate(3, week1Start, week2Start) / 7L;
		case 3:
			return time1 / 86400000L - time2 / 86400000L;
		case 4:
			return time1 / 3600000L - time2 / 3600000L;
		case 5:
			return time1 / 60000L - time2 / 60000L;
		case 6:
			return time1 / 1000L - time2 / 1000L;
		case 7:

			return dt1.getTime() - dt2.getTime();
		}
		throw new IllegalArgumentException(
				"com.yupstudio.lib.util.DateUtil.diffDate(): "
						+ "unknown interval format.");
	}

	public static String getString(Date dt) {
		return getString(dt, "yyyy-MM-dd HH:mm");
	}
	
	public static String getYMdString(Date dt) {
		return getString(dt, "yyyy-MM-dd");
	}
	
	public static String getHmString(Date dt){
		return getString(dt, "HH:mm");
	}

	public static String getString(Date dt, String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		if (dt == null) {
			return "";
		}
		return formatter.format(dt);
	}

	public static int getDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal.get(5);
	}

	public static int getHour(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal.get(11);
	}

	public static int getMinute(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal.get(12);
	}

	public static int getSecond(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal.get(13);
	}

	public static String getWeekDayName(int dy) throws IllegalArgumentException {
		if ((dy >= 1) && (dy <= 7)) {
			Calendar cal = Calendar.getInstance();
			cal.set(7, dy);
			return getString(cal.getTime(), "EEEE");
		}
		throw new IllegalArgumentException(
				"com.yupstudio.lib.util.DateKit.getDayName(): "
						+ "day value must be between 1 and 7!");
	}

	public static String getWeekDayName(Date dt) {
		return getString(dt, "EEEE");
	}

	public static int getMonth(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal.get(2) + 1;
	}

	public static String getMonthName(int mon) throws IllegalArgumentException {
		mon--;
		if ((mon >= 0) && (mon <= 11)) {
			Calendar cal = Calendar.getInstance();
			cal.set(5, 1);
			cal.set(2, mon);
			return getString(cal.getTime(), "MMMM");
		}
		throw new IllegalArgumentException(
				"com.yupstudio.lib.util.DateKit.getMonthName(): "
						+ "month value must be between 1 and 12!");
	}

	public static String getMonthName(Date dt) {
		return getString(dt, "MMMM");
	}

	/**
	 * 获取时区信息
	 * 
	 * @param dt
	 * @return
	 */
	public static String getTimeZone(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		TimeZone tz = cal.getTimeZone();
		return tz.getID();
	}

	public static int getWeek(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int week = cal.get(3);
		return week;
	}

	public static int getWeekDay(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal.get(7);
	}

	public static int getYear(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int yr = cal.get(1);
		int era = cal.get(0);
		if (era == 0) {
			return -1 * yr;
		}
		return yr;
	}

	/**
	 * 是否为闰年
	 * 
	 * @param year
	 * @return
	 */
	public static boolean isLeapyear(int year) {
		boolean div4 = year % 4 == 0;
		boolean div100 = year % 100 == 0;
		boolean div400 = year % 400 == 0;
		return (div4) && ((!div100) || (div400));
	}

	public static Date getDateObj(String s, String pattern) {
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		try {
			Date theDate = formatter.parse(s);
			return theDate;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static Date getSystemDate() {

		TimeZone timeZone = TimeZone.getDefault();
		long offsetTime = timeZone.getOffset(new Date().getTime());
		long nowTime = new Date().getTime();
		Date sysDate = new Date(nowTime + (8 * 3600 * 1000 - offsetTime));
		return sysDate;
	}
	
	/* 
     * 使用TextClock显示时间日期，布局文件textc.xml,API 4.2后使用 
     */  
    public void setTextClock(){  
        // 设置12时制显示格式  
//        textclock.setFormat12Hour("EEEE, MMMM dd, yyyy h:mmaa");   
        // 设置24时制显示格式  
//        textclock.setFormat24Hour("yyyy-MM-dd hh:mm, EEEE");  
    }
}
