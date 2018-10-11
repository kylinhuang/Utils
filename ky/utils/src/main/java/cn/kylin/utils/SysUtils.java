package cn.kylin.utils;

import android.content.Context;
import android.provider.Settings;

public class SysUtils {

    /**
     * @param context
     * @return  0 - 竖屏  1 -  自动
     *
     */
    public static int getRotationStatus(Context context) {
        int status = 0;
        try {
            status = Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
        } catch (Settings.SettingNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return status;
    }
}
