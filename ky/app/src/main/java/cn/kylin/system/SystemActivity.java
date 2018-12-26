package cn.kylin.system;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import cn.kylin.R;
import cn.kylin.base.BaseActivity;
import cn.kylin.utils.ClipboardUtils;
import cn.kylin.utils.DeviceUtils;
import cn.kylin.utils.SystemUtil;
import cn.kylin.utils.ToastUtils;
import cn.kylin.utils.Utils;


public class SystemActivity extends BaseActivity  {

    private TextView tv_device;

    public static void actionStart(Context mContext) {
        Intent intent = new Intent(mContext, SystemActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_system;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

        tv_device = (TextView) findViewById(R.id.tv_device);

        findViewById(R.id.share).setOnClickListener(this);
        findViewById(R.id.copy).setOnClickListener(this);


        findViewById(R.id.shutdown).setOnClickListener(this);
        findViewById(R.id.reboot).setOnClickListener(this);
        findViewById(R.id.reboot2Recovery).setOnClickListener(this);
        findViewById(R.id.reboot2Bootloader).setOnClickListener(this);

    }

    @Override
    public void doBusiness() {
        String enter = Utils.getContext().getResources().getString(R.string.enter);


        StringBuffer  sbf = new  StringBuffer();

        sbf.append("系统语言 : ");
        sbf.append(SystemUtil.getSystemLanguage());
        sbf.append(enter);
        sbf.append(enter);

        sbf.append("SystemVersion : ");
        sbf.append(SystemUtil.getSystemVersion());
        sbf.append(enter);
        sbf.append(enter);



        sbf.append("手机型号 : ");
        sbf.append(SystemUtil.getSystemModel());
        sbf.append(enter);
        sbf.append(enter);


        sbf.append("手机厂商 : ");
        sbf.append(SystemUtil.getDeviceBrand());
        sbf.append(enter);
        sbf.append(enter);


        Log.e("TAG" ,sbf.toString() );


        sbf.toString();

        tv_device.setText(sbf.toString());
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()){
            case R.id.copy:
                ClipboardUtils.copyText(tv_device.getText().toString());
                ToastUtils.showShort("copy");
                break;
            case R.id.share:
                Intent textIntent = new Intent(Intent.ACTION_SEND);
                textIntent.setType("text/plain");
                textIntent.putExtra(Intent.EXTRA_TEXT, tv_device.getText().toString());
                startActivity(Intent.createChooser(textIntent, "分享"));
                break;
            case R.id.shutdown:

                if (DeviceUtils.isDeviceRooted()){
                    DeviceUtils.shutdown();
                    Toast.makeText(mActivity,"shutdown",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mActivity," need root ",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.reboot:

                if (DeviceUtils.isDeviceRooted()){

                    DeviceUtils.reboot();
                    Toast.makeText(mActivity,"reboot",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mActivity," need root ",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.reboot2Recovery:
                if (DeviceUtils.isDeviceRooted()){

                    DeviceUtils.reboot2Recovery();
                    Toast.makeText(mActivity,"reboot2Recovery",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(mActivity," need root ",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.reboot2Bootloader:
                if (DeviceUtils.isDeviceRooted()){
                    DeviceUtils.reboot2Bootloader();
                    Toast.makeText(mActivity,"reboot2Bootloader",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(mActivity," need root ",Toast.LENGTH_SHORT).show();
                }

                break;

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

}










