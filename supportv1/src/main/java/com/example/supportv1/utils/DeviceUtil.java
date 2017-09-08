package com.example.supportv1.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class DeviceUtil {

    private DeviceUtil() {
        throw new AssertionError("this is util class");
    }

    /**
     * 得到设备型号:MI 2S,iphone 5S
     *
     * @return
     */
    public static String getDeviceMode() {
        return android.os.Build.MODEL;
    }

    /**
     * 得到设备类型<br>
     * android:phone,tab<br>
     * ios:iPhone,iPod touch
     *
     * @return
     */
    public static String getDeviceType(Context context) {
        Boolean mflag = (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        if (mflag) {
            return "tab";
        } else {
            return "phone";
        }
    }

    /**
     * 得到设备EMSI号
     *
     * @return
     */
    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String _imsi = tm.getDeviceId();
        if (_imsi != null && !_imsi.equals("")) {
            return _imsi;
        }
        return "";
    }

    /**
     * 得到设备mac地址
     *
     * @param context
     * @return
     */
    public static String getMacAddress(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    /**
     * 得到系统名称
     *
     * @return
     */
    public static String getOSName() {
        return System.getProperty("os.name");
    }

    /**
     * 得到系统版本号
     *
     * @return
     */
    public static String getOSVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 得到APP版本号
     *
     * @return
     */
    public static String getAPPVersion(Context mContext) {

        return null;
    }

    /**
     * 设备是否连接wifi
     *
     * @param context
     * @return boolean
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        State wifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();

        if (wifi == State.CONNECTED) {
            return true;
        }
        return false;
    }

    /**
     * 设备是否打开Gps
     *
     * @param context
     * @return boolean
     */
    public static boolean isGps(Context context) {
        LocationManager mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * 设备是否存在网络连接
     *
     * @param context
     * @return boolean
     */
    public static boolean isNetwork(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 得到设备的物理像素：宽和高
     *
     * @param activity
     * @return int[]
     */
    public static int[] getDisplaySize(Activity activity) {
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return new int[]{dm.widthPixels, dm.heightPixels};
    }

    /**
     * 设备独立像素到物理像素转换
     *
     * @param context
     * @param dipValue 设备独立像素
     * @return int 物理像素
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 物理像素到设备独立像素转换
     *
     * @param context
     * @param pxValue 物理像素
     * @return int 设备独立像素
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
