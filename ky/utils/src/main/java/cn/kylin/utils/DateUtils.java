package cn.kylin.utils;

import java.util.Date;

/**
 * Created by kylinhuang on 30/06/2017.
 */

public class DateUtils {

    public static String getWeek() {
        Date date = new Date();
        String week = String.format("%ta", date);
        return week;
    }

    /**
     * @param time
     * 星期  缩写
     * @return
     */
    public static String getWeek(long time ) {
        Date date = new Date(time);
        String week = String.format("%ta", date);
        return week;
    }

    /**
     * @param time
     * 星期
     * @return
     */
    public static String getWeekAll(long time ) {
        Date date = new Date(time);
        String week = String.format("%tA", date);
        return week;
    }

    /**
     * @param time
     * @return
     */
    public static String getMonth(long time ) {
        Date date = new Date(time);
        String week = String.format("%tb", date);
        return week;
    }


    /**
     * @param time
     * @return
     */
    public static String getMonthAll(long time ) {
        Date date = new Date(time);
        String week = String.format("%tB", date);
        return week;
    }
}
