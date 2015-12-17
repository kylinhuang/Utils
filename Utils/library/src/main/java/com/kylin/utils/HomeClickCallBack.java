package com.kylin.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouxiyuan on 15/11/3.
 */
public class HomeClickCallBack {

    private static HomeClickCallBack sInstance;
    private final Context mContext;
    private static HomeWatcherReceiver mHomeKeyReceiver = null;
    private List<HomeCallBack> callBackList = new ArrayList<HomeCallBack>();

    private HomeClickCallBack(Context context) {
        mContext = context ;
        registerHomeKeyReceiver(context);

    }

    public static HomeClickCallBack getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new HomeClickCallBack(context);
        }
        return sInstance;
    }

    public  void registerCallBack(HomeCallBack mHomeCallBack) {
        if(!callBackList.contains(mHomeCallBack)){
            callBackList.add(mHomeCallBack);
        }
    }

    public  void unregisterCallBack(HomeCallBack mHomeCallBack) {
        if(callBackList.contains(mHomeCallBack)){
            callBackList.remove(mHomeCallBack);
        }
    }


    private void registerHomeKeyReceiver(Context context) {
        mHomeKeyReceiver = new HomeWatcherReceiver();
        final IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.registerReceiver(mHomeKeyReceiver, homeFilter);
    }

    private static void unregisterHomeKeyReceiver(Context context) {
        if (null != mHomeKeyReceiver) {
            context.unregisterReceiver(mHomeKeyReceiver);
        }
    }

    public interface HomeCallBack{
        void clickHome();
    }


    class HomeWatcherReceiver extends BroadcastReceiver {
        private static final String LOG_TAG = "Voip";
        private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
        private static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
        private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        private static final String SYSTEM_DIALOG_REASON_LOCK = "lock";
        private static final String SYSTEM_DIALOG_REASON_ASSIST = "assist";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                    // 短按Home键
                    notifyHomekey();
                } else if (SYSTEM_DIALOG_REASON_RECENT_APPS.equals(reason)) {
                    notifyHomekey();
                    // 长按Home键 或者 activity切换键
                } else if (SYSTEM_DIALOG_REASON_LOCK.equals(reason)) {
                    // 锁屏
                } else if (SYSTEM_DIALOG_REASON_ASSIST.equals(reason)) {
                    // samsung 长按Home键
                }

            }
        }
        /** Home key 短按Home键 */
        private void notifyHomekey() {
            if (callBackList != null && callBackList.size() > 0) {
                for (HomeCallBack callBack : callBackList) {
                    callBack.clickHome();
                }
            }
        }
    }
}
