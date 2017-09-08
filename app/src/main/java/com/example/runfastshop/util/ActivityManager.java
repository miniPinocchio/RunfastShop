package com.example.runfastshop.util;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 管理activity
 * Created by 天上白玉京 on 2017/8/12.
 */

public class ActivityManager {

    private static List<Activity> activityStack = new ArrayList<>();

    //退出Activity
    public static void popActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    //将当前Activity加入集合
    public static void pushActivity(Activity activity) {
        activityStack.add(activity);
    }
    //退出所有Activity
    public static void popAllActivityExceptOne() {
        for (int i = 0; i < activityStack.size(); i++) {
            activityStack.get(i).finish();
        }
        activityStack.clear();
    }
}
