package cn.kylin.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

/**
 * Created by kylinhuang on 11/07/2017.
 */

public class Utils {

    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(@NonNull final Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {

        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    /**
     * 获取主线程 Handler
     * @return
     */
    public static Handler getMainHandler() {
        Handler mHandler = new Handler(Looper.getMainLooper());
        return mHandler;
    }

}
