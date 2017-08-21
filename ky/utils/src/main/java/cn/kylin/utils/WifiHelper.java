package cn.kylin.utils;

import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import static android.content.Context.WIFI_SERVICE;

/**
 * Created by kylinhuang on 11/07/2017.
 */

public class WifiHelper {

    private static WifiHelper mInstance;
    private final WifiManager mWifiManager;

    private WifiHelper() {
        mWifiManager = (WifiManager) Utils.getContext().getSystemService(WIFI_SERVICE);
    }

    public static synchronized WifiHelper getInstance() {
        if (mInstance == null) {
            mInstance = new WifiHelper();
        }
        return mInstance;
    }

    /**
     * 获取当前连接 wifi
     * android.permission.ACCESS_WIFI_STATE
     */
    public WifiInfo getConnectionInfo() {
        WifiInfo wifiInfo = mWifiManager.getConnectionInfo();
        return wifiInfo;
    }

    /**
     * 通过 freq 判断频率 wifi is5GHz
     * 参考android 源码 WifiInfo  ScanResult.is5GHz
     *
     * android 5.0 之后有效
     * @param freq
     * @return
     */
    public static boolean is5GHz(int freq) {
        return freq > 4900 && freq < 5900;
    }


    /** 得到接入点的BSSID */
    public String getConnectionBssid() {
        WifiInfo wifiInfo = getConnectionInfo();
        return (wifiInfo == null) ? null : wifiInfo.getBSSID();
    }

    /** 得到接入点的SSID */
    public String getConnectionSsid() {
        WifiInfo wifiInfo = getConnectionInfo();
        String ssid = (wifiInfo == null) ? null : wifiInfo.getSSID();
        // 获取的ssid有双引号，需要去除
        if (!TextUtils.isEmpty(ssid) && ssid.length() > 2 && ssid.startsWith("\"")) {
            ssid = ssid.substring(1, ssid.length() - 1);
        }
        return ssid;
    }


    /** 得到连接的DHCP */
    public DhcpInfo getDhcpInfo() {
        return mWifiManager.getDhcpInfo();
    }
}
