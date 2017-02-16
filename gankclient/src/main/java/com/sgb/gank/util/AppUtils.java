package com.sgb.gank.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

import java.util.List;

/**
 * Created by panda on 16/9/2 下午3:13.
 */
public class AppUtils {

    public AppUtils() {
    }

    /**
     * checkPermissions
     *
     * @param context
     * @param permission
     * @return
     */
    public static boolean checkPermissions(Context context, String permission) {
        PackageManager localPackageManager = context.getPackageManager();
        return localPackageManager.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * 获取TelephonyManager
     *
     * @param context
     * @return
     */
    public static TelephonyManager getTelephonyManager(Context context) {
        return checkPermissions(context, "android.permission.READ_PHONE_STATE")
                ? (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)
                : null;
    }

    /**
     * 获取WifiManager.
     *
     * @param context
     * @return
     */
    public static WifiManager getWifiManager(Context context) {
        return (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
    }

    /**
     * 获取ConnectivityManager
     *
     * @param context
     * @return
     */
    public static ConnectivityManager getConnectyvityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * 获取设备ID
     *
     * @param context
     * @return
     */
    public static String getDeviceID(Context context) {
        TelephonyManager tm = getTelephonyManager(context);
        return tm != null ? tm.getDeviceId() : "";
    }

    /**
     * 获取Mac地址
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        if (checkPermissions(context, "android.permission.ACCESS_WIFI_STATE")) {
            WifiInfo info = getWifiManager(context).getConnectionInfo();
            return info != null ? info.getMacAddress() : "";
        }
        return "";
    }

    /**
     * 获取进程名称
     *
     * @param context
     * @return
     */
    public static String getProcessName(Context context) {

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppList = am.getRunningAppProcesses();
        if (runningAppList == null || runningAppList.isEmpty()) {
            return null;
        }

        for (ActivityManager.RunningAppProcessInfo processInfo : runningAppList) {
            if (processInfo.pid == android.os.Process.myPid()) {
                return processInfo.processName;
            }
        }

        return null;
    }

}
