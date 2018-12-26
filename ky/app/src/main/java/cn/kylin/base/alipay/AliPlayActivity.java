package cn.kylin.base.alipay;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.kylin.IMessageHander;
import cn.kylin.service.RemindService;
import cn.kylin.service.NotificationMonitorService;
import cn.kylin.R;
import cn.kylin.base.BaseActivity;
import cn.kylin.utils.Utils;


public class AliPlayActivity extends BaseActivity implements View.OnClickListener {
    private boolean payListenerStarted;

    private TextView tv_test;

    public static void actionStart(Context mContext) {
        Intent intent = new Intent(mContext, AliPlayActivity.class);
        mContext.startActivity(intent);
    }


    @Override
    public void initData(@Nullable Bundle bundle) {
        Utils.init(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_aliplay;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        tv_test = (TextView)findViewById(R.id.tv_test);
        if (Settings.Secure.getString(this.getContentResolver(), "enabled_notification_listeners").contains(getApplicationContext().getPackageName())) {
            //Add the code to launch the NotificationService Listener here.
            tv_test.setText("已经拥有获取通知的权限");
            if (!payListenerStarted) {
                //开启服务
                ComponentName name = startService(new Intent(this, NotificationMonitorService.class));
                if(name ==null) {
//                    swt_service.setChecked(false);
                    Toast.makeText(getApplicationContext(), "服务开启失败", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(this, RemindService.class);
                intent.putExtra("from", "MainActive");
                bindService(intent, conn, BIND_AUTO_CREATE);



                payListenerStarted = true;
            }
        } else {
            //Launch notification access in the settings...
            tv_test.setText("请设置通知权限");
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        }



    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()){
//            case R.id.text:
//                SysRotationActivity.actionStart(this);
//                break;
//            case R.id.device:
//                DeviceActivity.actionStart(this);
//                break;
//            case R.id.wifi:
//                WifiActivity.actionStart(this);
//                break;
//            case R.id.alipay:
//                WifiActivity.actionStart(this);
//                break;

        }
    }

    private RemindService service;
    private IMessageHander msgHander = new IMessageHander() {
        @Override
        public void handMessage(Message msg) {
            Log.i(" msgHander ",msg.obj.toString());
        }
    };
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            RemindService.MyBinder myBinder = (RemindService.MyBinder) binder;
            service = myBinder.getService();
            service.setMessageHander(msgHander);
            Log.i(" ServiceConnection ", "MainActive - onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(" ServiceConnection ", "MainActive - onServiceDisconnected");
        }
    };
}










