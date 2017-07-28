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

}
