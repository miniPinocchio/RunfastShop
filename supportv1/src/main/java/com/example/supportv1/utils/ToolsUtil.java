package com.example.supportv1.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;

public class ToolsUtil {
    
    public static final String TAG = "ToolsUtil";

    private ToolsUtil() {
        throw new AssertionError("Util class");
    }

    /**
     * 得到应用版本号
     *
     * @return
     * @throws Exception
     */
    public static String getAppVersion(Context mContext) {
        try {
            PackageInfo info = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);

            // 当前版本的版本号  
            int versionCode = info.versionCode;
            return String.valueOf(versionCode);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 得到清单文件AndroidManifest.xml中的元数据
     * 形式为：
     * <application...>
     * .....
     * <meta-data
     * android:name="api_key"
     * android:value="" />
     * </application>
     *
     * @param context
     * @param metaKey
     * @return
     */
    public static String getMetaValue(Context context, String metaKey) {
        Bundle metaData = null;
        String apiKey = null;
        if (context == null || metaKey == null) {
            return null;
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            if (null != ai) {
                metaData = ai.metaData;
            }
            if (null != metaData) {
                apiKey = metaData.getString(metaKey);
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "getMetaValue error" + e.getMessage());
        }
        return apiKey;
    }
}
