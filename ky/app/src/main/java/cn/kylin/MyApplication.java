package cn.kylin;

import android.app.Application;

import cn.kylin.utils.Utils;

/**
 * Created by kylinhuang on 28/07/2017.
 */

public class MyApplication extends Application {
    private MyApplication mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this ;

        Utils.init(mContext);
    }
}
