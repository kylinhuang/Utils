package cn.kylin.system;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.kylin.R;
import cn.kylin.base.BaseActivity;
import cn.kylin.utils.SysUtils;


public class SysRotationActivity extends BaseActivity  {

    private TextView mRotationButton;

    public static void actionStart(Context mContext) {
        Intent intent = new Intent(mContext, SysRotationActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_sys_rotation;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

        findViewById(R.id.text).setOnClickListener(this);

        //创建观察类对象
        mRotationObserver = new RotationObserver(new Handler());

        mRotationButton = (TextView) findViewById(R.id.text);
        refreshButton();
        mRotationButton.setOnClickListener(this);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()){
            case R.id.text:
//                设置  权限 修改  不能直接调用
//                if (getRotationStatus(this) == 1) {
//                    setRotationStatus(getContentResolver(), 0);
//                } else {
//                    setRotationStatus(getContentResolver(), 1);
//                }
                break;
        }

    }


    private RotationObserver mRotationObserver;


    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        //解除观察变化
        mRotationObserver.stopObserver();
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //注册观察变化
        mRotationObserver.startObserver();
    }


    //更新按钮状态
    private void refreshButton() {
        if (SysUtils.getRotationStatus(this) == 1) {
            mRotationButton.setText("OFF");
//            mRotationButton.setText(R.string.rotation_off);
        } else {
            mRotationButton.setText("ON");
//            mRotationButton.setText(R.string.rotation_on);
        }
    }

    //得到屏幕旋转的状态


    private void setRotationStatus(ContentResolver resolver, int status) {
        //得到uri
        Uri uri = Settings.System.getUriFor("accelerometer_rotation");
        //沟通设置status的值改变屏幕旋转设置
        Settings.System.putInt(resolver, "accelerometer_rotation", status);
        //通知改变
        resolver.notifyChange(uri, null);
    }




    //观察屏幕旋转设置变化，类似于注册动态广播监听变化机制
    private class RotationObserver extends ContentObserver {
        ContentResolver mResolver;

        public RotationObserver(Handler handler) {
            super(handler);
            mResolver = getContentResolver();
            // TODO Auto-generated constructor stub
        }

        //屏幕旋转设置改变时调用
        @Override
        public void onChange(boolean selfChange) {
            // TODO Auto-generated method stub
            super.onChange(selfChange);
            //更新按钮状态
            refreshButton();

            Toast.makeText(SysRotationActivity.this, "旋转屏幕设置有变化",
                    Toast.LENGTH_SHORT).show();
        }

        public void startObserver() {
            mResolver.registerContentObserver(Settings.System.getUriFor(Settings.System.ACCELEROMETER_ROTATION), false,this);
        }


        public void stopObserver() {
            mResolver.unregisterContentObserver(this);
        }


    }


}










