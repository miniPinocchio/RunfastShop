package com.example.supportv1.utils;

import android.text.TextUtils;
import android.util.Log;

import com.example.supportv1.constant.Consts;

/***
 * Log的工具类
 *
 * @author Sun.bl
 * @version [1.0, 2016/2/29]
 */
public class LogUtil {

    private LogUtil() {
        throw new AssertionError("this is util class");
    }


    public static void i(String tag, String msg) {
        if (!Consts.LOG_DEBUG) {
            return;
        }
        msg = judgeMsg(msg);
        Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (!Consts.LOG_DEBUG) {
            return;
        }
        msg = judgeMsg(msg);
        Log.e(tag, msg);
    }

    public static void w(String tag, String msg) {

        if (!Consts.LOG_DEBUG) {
            return;
        }
        msg = judgeMsg(msg);
        Log.w(tag, msg);

    }

    public static void v(String tag, String msg) {

        if (!Consts.LOG_DEBUG) {
            return;
        }
        msg = judgeMsg(msg);
        Log.v(tag, msg);

    }

    public static void d(String tag, String msg) {

        if (!Consts.LOG_DEBUG) {
            return;
        }
        msg = judgeMsg(msg);
        Log.d(tag, msg);

    }

    public static void d(String tag, String msg, Throwable tr) {
        if (!Consts.LOG_DEBUG) {
            return;
        }
        msg = judgeMsg(msg);
        Log.d(tag, msg, tr);
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (!Consts.LOG_DEBUG) {
            return;
        }
        msg = judgeMsg(msg);
        Log.e(tag, msg, tr);
    }

    private static String judgeMsg(String msg) {

        if (msg == null) {
            return "msg为空指针";
        }
        if (TextUtils.equals(msg, "")) {
            return "msg为空字符";
        }

        return msg;

    }


}
