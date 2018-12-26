package cn.kylin.system;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import cn.kylin.R;
import cn.kylin.base.BaseActivity;
import cn.kylin.utils.ToastUtils;
import cn.kylin.utils.WifiHelper;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class WifiActivity extends BaseActivity implements  EasyPermissions.PermissionCallbacks{

    private Button check_wifi;
    private Button scan_wifi;
    private Button open_wifi;
    private Button close_wifi;
    private ListView mlistView;
    private List<ScanResult> mWifiList;

    public static void actionStart(Context mContext) {
        Intent intent = new Intent(mContext, WifiActivity.class);
        mContext.startActivity(intent);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_wifi;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView(Bundle savedInstanceState, View contentView) {

        check_wifi = (Button) findViewById(R.id.check_wifi);
        open_wifi = (Button) findViewById(R.id.open_wifi);
        close_wifi = (Button) findViewById(R.id.close_wifi);
        scan_wifi = (Button) findViewById(R.id.scan_wifi);
        mlistView = (ListView) findViewById(R.id.wifi_list);

        check_wifi.setOnClickListener(this);
        open_wifi.setOnClickListener(this);
        close_wifi.setOnClickListener(this);
        scan_wifi.setOnClickListener(this);


    }

    @Override
    public void doBusiness() {
    }

    @Override
    public void onWidgetClick(View view) {
        switch (view.getId()) {
            case R.id.check_wifi:
                int WifiState = WifiHelper.getInstance().getWifiState();

                if (WifiState == 0) {
                    Toast.makeText(mActivity,"Wifi正在关闭", Toast.LENGTH_SHORT).show();
                }  else if (WifiState == 1) {
                    Toast.makeText(mActivity,"Wifi已经关闭", Toast.LENGTH_SHORT).show();
                } else if (WifiState == 2) {
                    Toast.makeText(mActivity,"Wifi正在开启", Toast.LENGTH_SHORT).show();
                } else if (WifiState == 3) {
                    Toast.makeText(mActivity,"Wifi已经开启", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity,"没有获取到WiFi状态", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.open_wifi:
                WifiHelper.getInstance().openWifi(this);
                break;
            case R.id.close_wifi:
                WifiHelper.getInstance().closeWifi(this);
                break;
            case R.id.scan_wifi:
                requeCoarseLocation();
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


    public class MyAdapter extends BaseAdapter {
        LayoutInflater inflater;
        List<ScanResult> list;

        public MyAdapter(Context context, List<ScanResult> list) {
            this.inflater = LayoutInflater.from(context);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint({"ViewHolder", "InflateParams"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            view = inflater.inflate(R.layout.wifi_listitem, null);
            ScanResult scanResult = list.get(position);
            TextView wifi_ssid = (TextView) view.findViewById(R.id.ssid);
            ImageView wifi_level = (ImageView) view.findViewById(R.id.wifi_level);
            wifi_ssid.setText(scanResult.SSID);
            Log.i(TAG, "scanResult.SSID=" + scanResult);
            int level = WifiManager.calculateSignalLevel(scanResult.level, 5);
            if (scanResult.capabilities.contains("WEP") || scanResult.capabilities.contains("PSK") ||
                    scanResult.capabilities.contains("EAP")) {
//                wifi_level.setImageResource(R.drawable.b);
            } else {
//                wifi_level.setImageResource(R.drawable.a);
            }
            wifi_level.setImageLevel(level);
            //判断信号强度，显示对应的指示图标
            return view;
        }
    }

    /*设置listview的高度*/
    public class Utility {
        public void setListViewHeightBasedOnChildren(ListView listView) {
            ListAdapter listAdapter = listView.getAdapter();
            if (listAdapter == null) {
                return;
            }
            int totalHeight = 0;
            for (int i = 0; i < listAdapter.getCount(); i++) {
                View listItem = listAdapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
            listView.setLayoutParams(params);
        }
    }

   void startScan(){
       WifiHelper.getInstance().startScan(this);
       mWifiList = WifiHelper.getInstance().getWifiList();
       if (mWifiList != null) {
           mlistView.setAdapter(new MyAdapter(this, mWifiList));
       }
    }

    private static final int RC_COARSE_LOCATION = 101;
    @SuppressLint("InlinedApi")
    @AfterPermissionGranted(RC_COARSE_LOCATION)
    public void requeCoarseLocation() {
        if (EasyPermissions.hasPermissions(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            startScan();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_COARSE_LOCATION), RC_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        startScan();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        ToastUtils.showShort("Permissions Denied");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}












