package com.kylin.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouxiyuan on 15/11/9.
 *
 * 耳机插入播出
 */
public class HeadSetCallBack {


    private static HeadSetCallBack sInstance;
    private final Context mContext;

    private List<HeadsetPlugCallBack> callBackList = new ArrayList<HeadsetPlugCallBack>();
    private HeadsetPlugReceiver headsetPlugReceiver = null ;

    private HeadSetCallBack(Context context) {
        mContext = context ;
        registerHeadsetPlugReceiver(context);

    }

    public static HeadSetCallBack getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new HeadSetCallBack(context);
        }
        return sInstance;
    }

    public  void registerCallBack(HeadsetPlugCallBack mHeadsetPlugCallBack) {
        if(!callBackList.contains(mHeadsetPlugCallBack)){
            callBackList.add(mHeadsetPlugCallBack);
        }
    }

    public  void unregisterCallBack(HeadsetPlugCallBack mHeadsetPlugCallBack) {
        if(callBackList.contains(mHeadsetPlugCallBack)){
            callBackList.remove(mHeadsetPlugCallBack);
        }
    }

    private void registerHeadsetPlugReceiver(Context context){
        headsetPlugReceiver  = new HeadsetPlugReceiver ();
        IntentFilter  filter = new IntentFilter();
        filter.addAction("android.intent.action.HEADSET_PLUG");
        context.registerReceiver(headsetPlugReceiver, filter);
    }



    private  void unregisterHeadsetPlugReceiver(Context context) {
        if (null != headsetPlugReceiver) {
            context.unregisterReceiver(headsetPlugReceiver);
        }
    }


    /** state ---        0代表拔出，1代表插入
    name--- 字符串，代表headset的类型。
    microphone --   1代表这个headset有麦克风，0则没有。
     */
    public class HeadsetPlugReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.hasExtra("state")){
                if(intent.getIntExtra("state", 0)==0){
                    notifyHeadsetPlug(false);
                } else if(intent.getIntExtra("state", 0)==1){
                    notifyHeadsetPlug(true);
                }
            }
        }

    }

    public interface HeadsetPlugCallBack{
        void connect(Boolean isConnect);
    }

    /** Home key 短按Home键 */
    private void notifyHeadsetPlug(Boolean isConnect) {
        if (callBackList != null && callBackList.size() > 0) {
            for (HeadsetPlugCallBack callBack : callBackList) {
                callBack.connect(isConnect);
            }
        }
    }

}
