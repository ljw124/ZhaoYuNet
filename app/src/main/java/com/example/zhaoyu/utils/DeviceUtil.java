package com.example.zhaoyu.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by Ljw on 2019/5/24.
 */
public class DeviceUtil {

    private DeviceUtil(){

    }

    /**
     * 获取设备的唯一标识，deviceId
     */
    public static String getDeviceID(Context context) {
        if (context == null) {
            return null;
        }
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        return tm.getDeviceId();
    }

    /**
     * 获取当前版本号
     */
    public static String getAppVersion(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);

            versionName = packageInfo.versionName;
            if (TextUtils.isEmpty(versionName)) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
