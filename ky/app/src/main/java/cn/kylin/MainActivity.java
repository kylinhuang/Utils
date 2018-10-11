package cn.kylin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import cn.kylin.base.BaseActivity;
import cn.kylin.system.DeviceActivity;
import cn.kylin.system.SysRotationActivity;
import cn.kylin.utils.Utils;


public class MainActivity extends BaseActivity implements View.OnClickListener {


    @Override
    public void initData(@Nullable Bundle bundle) {
        Utils.init(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {
        findViewById(R.id.text).setOnClickListener(this);
        findViewById(R.id.device).setOnClickListener(this);
    }

    @Override
    public void doBusiness() {

    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()){
            case R.id.text:
                SysRotationActivity.actionStart(this);
                break;
            case R.id.device:
                DeviceActivity.actionStart(this);
                break;
        }
    }
}










