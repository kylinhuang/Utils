package cn.kylin.utils;

import android.util.Log;

/**
 * Created by kylinhuang on 13/07/2017.
 */

public class LogUtils {


    static String IS_OPEN_LOG = "IS_OPEN_LOG";

    static String className;//类名
    static String methodName;//方法名
    static int lineNumber;//行数

    private LogUtils(){
        /* Protect from instantiations */
    }

    public static boolean isOpenLog() {
        return SPUtils.getInstance().getBoolean(IS_OPEN_LOG,false);
    }

    public static void isOpenLog(Boolean isOpenLog) {
         SPUtils.getInstance().put(IS_OPEN_LOG,isOpenLog);
    }

    private static String createLog( String log ) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")");
        buffer.append(log);
        return buffer.toString();
    }
    private static void getMethodNames(StackTraceElement[] sElements){
        className  = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }
    public static void e(String message){
        if (!isOpenLog())
            return;

        getMethodNames(new Throwable().getStackTrace());
        Log.e(className, createLog(message));
    }
    public static void i(String message){
        if (!isOpenLog())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.i(className, createLog(message));
    }
    public static void d(String message){
        if (!isOpenLog())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.d(className, createLog(message));
    }
    public static void v(String message){
        if (!isOpenLog())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.v(className, createLog(message));
    }
    public static void w(String message){
        if (!isOpenLog())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.w(className, createLog(message));
    }
    public static void wtf(String message){
        if (!isOpenLog())
            return;
        getMethodNames(new Throwable().getStackTrace());
        Log.wtf(className, createLog(message));
    }

}
