package cn.kylin.utils;

import java.util.regex.Pattern;

/**
 * Created by kylinhuang on 30/06/2017.
 */

public class StringUtils {


    /**
     * 是否 包含空格
     * @param s
     * @return
     */
    public static boolean isSpace(final String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    private final static Pattern mNumberPattern = Pattern.compile("[0-9]*");
    /**
     * 判断是否为数字
     *
     * @param str
     *            传入的字符串
     * @return 是数字返回true, 否则返回false
     */
    public static boolean isNumber(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        return mNumberPattern.matcher(str).matches();
    }
}
