package cn.kylin.activity.tools;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import cn.kylin.AppConst;
import cn.kylin.service.BootRecevier;
import cn.kylin.IMessageHander;
import cn.kylin.service.RemindService;
import cn.kylin.service.NotificationMonitorService;
import cn.kylin.R;
import cn.kylin.base.BaseActivity;


public class RemindActivity extends BaseActivity {

    private Switch mSwitchPermission;
    private Switch mSwitchService;

    private Handler handler;


    public static void actionStart(Context mContext) {
        Intent intent = new Intent(mContext, RemindActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_remind;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

        mSwitchPermission = (Switch) findViewById(R.id.service_permission);
        mSwitchService = (Switch) findViewById(R.id.service);

        mSwitchPermission.setChecked(false);
        mSwitchService.setChecked(false);

        handler = new Handler();
        try {
            AppConst.version = getPackageManager().getPackageInfo(getPackageName(),0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        IntentFilter filter = new IntentFilter(AppConst.IntentAction);
        registerReceiver(receiver,filter);


        boolean enabled = isEnabled();
        mSwitchPermission.setChecked(enabled);

        if(enabled){
            Intent intent = new Intent(this, RemindService.class);
            intent.putExtra("from", "MainActive");
            bindService(intent, conn, BIND_AUTO_CREATE);
        }else {
            mSwitchService.setEnabled(false);
        }

        mSwitchPermission.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked != enabedPrivileges){
                    openNotificationListenSettings();
                }
            }
        });

        mSwitchService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkStatus();
                }
            }
        });

    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
//            case R.id.copy:
//                ClipboardUtils.copyText(tv_device.getText().toString());
//                ToastUtils.showShort("copy");
//                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    private boolean enabedPrivileges;
    private void checkStatus() {
        //权限开启.才能启动服务
        boolean enabled = isEnabled();
        enabedPrivileges = enabled;

        mSwitchPermission.setChecked(enabled);
        if (!enabled) {
            mSwitchService.setEnabled(false);
            return;
        }
        mSwitchService.setEnabled(true);

        //开启服务
        ComponentName name = startService(new Intent(this, NotificationMonitorService.class));
        if (name == null) {
            mSwitchService.setChecked(false);
            Toast.makeText(getApplicationContext(), "服务开启失败", Toast.LENGTH_LONG).show();
            return;
        }

        toggleNotificationListenerService();
        mSwitchService.setChecked(true);

        //微信支付宝开启

    }

    private boolean isEnabled() {
        String str = getPackageName();
        String localObject = Settings.Secure.getString(getContentResolver(), "enabled_notification_listeners");
        if (!TextUtils.isEmpty(localObject)) {
            String[] strArr = (localObject).split(":");
            int i = 0;
            while (i < strArr.length) {
                ComponentName localComponentName = ComponentName.unflattenFromString(strArr[i]);
                if ((localComponentName != null) && (TextUtils.equals(str, localComponentName.getPackageName())))
                    return true;
                i += 1;
            }
        }
        return false;
    }

    /**
     * 打开通知权限设置.一般手机根本找不到哪里设置
     */
    private void openNotificationListenSettings() {
        try {
            Intent intent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            } else {
                intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            }
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void toggleNotificationListenerService() {
        PackageManager localPackageManager = getPackageManager();
        localPackageManager.setComponentEnabledSetting(new ComponentName(this, NotificationMonitorService.class),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        localPackageManager.setComponentEnabledSetting(new ComponentName(this, NotificationMonitorService.class),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }


    //不知到什么原因.广播收不到
    private BroadcastReceiver receiver = new BootRecevier() ;



    private IMessageHander msgHander = new IMessageHander() {
        @Override
        public void handMessage(Message msg) {
            Log.i(" IMessageHander ",msg.obj.toString());
        }
    };

    private RemindService service;
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}










