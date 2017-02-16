package com.sgb.gank.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IntDef;
import android.telephony.TelephonyManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

public class NetworkUtils {
    /** sim 卡类型, 未知 */
    public static final int TYPE_SIM_UNKNOWN = 0;
    /** sim 卡类型, 移动 */
    public static final int TYPE_SIM_MOBILE = 1;
    /** sim 卡类型, 联通 */
    public static final int TYPE_SIM_UNI_COM = 2;
    /** sim 卡类型, 电信 */
    public static final int TYPE_SIM_TELE_COM = 3;
    /** 网络类型, 无效 */
    public static final int TYPE_NET_INVALID = 0;
    /** 网络类型, 2G */
    public static final int TYPE_NET_2G = 1;
    /** 网络类型, 3G */
    public static final int TYPE_NET_3G = 2;
    /** 网络类型, 4G */
    public static final int TYPE_NET_4G = 3;
    /** 网络类型, WIFI */
    public static final int TYPE_NET_WIFI = 4;

    @IntDef({TYPE_SIM_UNKNOWN, TYPE_SIM_MOBILE, TYPE_SIM_UNI_COM, TYPE_SIM_TELE_COM})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SimType {
    }

    @IntDef({TYPE_NET_INVALID, TYPE_NET_2G, TYPE_NET_3G, TYPE_NET_4G, TYPE_NET_WIFI})
    @Retention(RetentionPolicy.SOURCE)
    public @interface NetType {
    }

    private static final HashMap<Integer, ArrayList<String>> MAP_SIM_TYPE = new HashMap<>(3);

    static {
        final ArrayList<String> mobiles = new ArrayList<String>() {{
            add("46000");
            add("46002");
        }};

        final ArrayList<String> unicoms = new ArrayList<String>() {{
            add("46001");
        }};

        final ArrayList<String> telecoms = new ArrayList<String>() {{
            add("46003");
        }};

        MAP_SIM_TYPE.put(TYPE_SIM_MOBILE, mobiles);
        MAP_SIM_TYPE.put(TYPE_SIM_UNI_COM, unicoms);
        MAP_SIM_TYPE.put(TYPE_SIM_TELE_COM, telecoms);
    }

    public static final String[] NETWORK_TYPE_VALUES = {"unreachable", "2G", "3G", "4G", "WIFI"};

    /**
     * 获取当前活动NetworkInfo
     *
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context) {
        return AppUtils.checkPermissions(context, "android.permission.ACCESS_NETWORK_STATE")
                ? AppUtils.getConnectyvityManager(context).getActiveNetworkInfo()
                : null;
    }

    /**
     * 获取 Mobile 类型NetworkInfo
     *
     * @param context
     * @return
     */
    public static NetworkInfo getMobileNetworkInfo(Context context) {
        return AppUtils.checkPermissions(context, "android.permission.ACCESS_NETWORK_STATE")
                ? AppUtils.getConnectyvityManager(context).getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                : null;
    }

    /**
     * 获取 WIFI 类型NetworkInfo
     *
     * @param context
     * @return
     */
    public static NetworkInfo getWifiNetworkInfo(Context context) {
        return AppUtils.checkPermissions(context, "android.permission.ACCESS_NETWORK_STATE")
                ? AppUtils.getConnectyvityManager(context).getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                : null;
    }

    /**
     * 检查当前网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        final NetworkInfo info;
        return context != null && (info = getNetworkInfo(context)) != null && info.isConnected();
    }

    /**
     * 获取 sim 卡类型
     * @param context
     * @return
     */
    public static int getSimType(Context context) {
        final TelephonyManager telephonyManager = AppUtils.getTelephonyManager(context);
        if (telephonyManager != null) {
            final String opString = telephonyManager.getSimOperator();
            for (int key : MAP_SIM_TYPE.keySet()) {
                if (MAP_SIM_TYPE.get(key).contains(opString)) {
                    return key;
                }
            }
        }
        return TYPE_SIM_UNKNOWN;
    }

    /**
     * 获取 sim 卡类型 (名称)
     *
     * @param context
     * @return
     */
    public static String getSimName(Context context) {
        final TelephonyManager telephonyManager = AppUtils.getTelephonyManager(context);
        if (telephonyManager != null) {
            final String opString = telephonyManager.getSimOperator();
            for (int key : MAP_SIM_TYPE.keySet()) {
                if (MAP_SIM_TYPE.get(key).contains(opString)) {
                    return opString;
                }
            }
        }
        return "UNKNOWN";
    }

    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    @NetType
    public static int getType(Context context) {
        final NetworkInfo info = getNetworkInfo(context);

        if (info == null || !info.isConnectedOrConnecting()) {
            return TYPE_NET_INVALID;
        }
        switch (info.getType()) {
            case ConnectivityManager.TYPE_WIFI:
            case ConnectivityManager.TYPE_WIMAX:
            case ConnectivityManager.TYPE_ETHERNET:
                return TYPE_NET_WIFI;
            case ConnectivityManager.TYPE_MOBILE:
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_LTE: // 4G
                        return TYPE_NET_4G;
                    case TelephonyManager.NETWORK_TYPE_UMTS: // 3G
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return TYPE_NET_3G;
                    case TelephonyManager.NETWORK_TYPE_GPRS: // 2G
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return TYPE_NET_2G;
                    default:
                        return TYPE_NET_INVALID;
                }
        }
        return TYPE_NET_INVALID;
    }

    /**
     * 获取网络类型（名称)
     *
     * @param context
     * @return
     */
    public static String getTypeName(Context context) {
        return getTypeName(getType(context));
    }

    /**
     * 获取网络类型 (名称)
     *
     * @param typeId
     * @return
     */
    public static String getTypeName(@NetType int typeId) {
        return NETWORK_TYPE_VALUES[typeId];
    }

    /**
     * 用来获取手机拨号上网（包括CTWAP和CTNET）时由PDSN分配给手机终端的源IP地址。
     */
    public static String getPdsnIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            return "UNKNOWN";
        }
        return "127.0.0.1";
    }
}
