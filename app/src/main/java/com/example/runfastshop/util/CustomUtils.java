package com.example.runfastshop.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runfastshop.fragment.BusinessFragment;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by 天上白玉京 on 2017/8/8.
 */

public class CustomUtils {

    public static BusinessFragment fragment;

    private static ImageView ivHead;
    private static TextView tvName;
    private static List<Activity> activities = new ArrayList<>();
    //public static ArrayList<StationInfo> busInfoArrayList;

    private CustomUtils() {
        throw new AssertionError("Util class");
    }


    public static String getVersionName(Context context) {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = packInfo.versionName;
        return version;
    }


    public static String formatTime(String time) {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        String formatTime = "";
        String[] splitTime = time.split(":");

        if (splitTime.length >= 1) {
            formatTime = splitTime[splitTime.length - 1] + formatTime;
        }
        if (splitTime.length >= 2) {
            formatTime = splitTime[splitTime.length - 2] + formatTime;
        }
        if (splitTime.length >= 3) {
            formatTime = splitTime[splitTime.length - 3] + formatTime;
        }

        return formatTime;
    }

    public static String secondToTime(int second) {
        if (second < 60) {
            return second + "秒";
        }
        int minute = second / 60;
        if (minute < 60) {
            return minute + "分" + second % 60 + "秒";
        }
        int hour = second / 3600;
        int minute3 = (second - hour * 3600) / 60;
        int second3 = (second - hour * 3600 - minute3 * 60) % 60;
        return hour + "时" + minute3 + "分" + second3 + "秒";
    }

    @SuppressWarnings("deprecation")
    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    public static void configureWebView(WebView webView) {
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setLightTouchEnabled(true);
        webSettings.setSaveFormData(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
    }

    /**
     * 显示提示框
     *
     * @param fragmentManager 管理器
     * @param dialogFragment  fragment
     */
    public static synchronized void showDialogFragment(FragmentManager fragmentManager, DialogFragment dialogFragment) {
        if (fragmentManager == null || dialogFragment == null
                || dialogFragment.isAdded() || dialogFragment.isResumed()) {
            return;
        }
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(dialogFragment, dialogFragment.getClass().getSimpleName());
        transaction.commitAllowingStateLoss();
    }

    /**
     * 取消加载框
     */
    public static synchronized void dismissDialogFragment(DialogFragment dialogFragment) {
        if (dialogFragment == null || dialogFragment.isDetached() || !dialogFragment.isAdded()
                || dialogFragment.isHidden() || !dialogFragment.isVisible()) {
            return;
        }
        dialogFragment.dismiss();
    }

    /**
     * 获取手机IP地址
     * @return
     */
    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    //Enumeration<NetworkInterface> en=NetworkInterface.getNetworkInterfaces();
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
        }
        return null;
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    public static void saveActivitys(Activity activity){
        activities.add(activity);
    }

    public static void clearActivitys(Activity activity){
        activities.remove(activity);
    }

    public static void clearAllActivitys(){
        List<Activity> activities = CustomUtils.getActivities();
        int size = activities.size();
        for (int i = 0; i < size; i++) {
            activities.get(i).finish();
        }
        activities.clear();
    }

    public static List<Activity> getActivities(){
        return activities;
    }
}
