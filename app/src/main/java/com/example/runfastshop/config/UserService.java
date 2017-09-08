package com.example.runfastshop.config;

import android.content.Context;
import android.text.TextUtils;

import com.example.runfastshop.bean.User;
import com.example.runfastshop.impl.constant.CustomConstant;
import com.example.runfastshop.util.CustomToast;
import com.example.supportv1.app.BaseApplication;
import com.example.supportv1.utils.SharedPreferencesUtil;
import com.google.gson.Gson;

/**
 * 用户信息
 *
 * @author Sun.bl
 * @version [1.0, 2016/6/24]
 */
public class UserService {


    private static final String USER_INFO = "userInfo";

    private static final String INIT_INFO = "initConfig";

    private static final String AUTO_LOGIN = "autoLogin";

    private static final String TASK_CURRENT = "taskCurrent";

    private static final String SAVE_LNG = "saveLng";

    private static final String ORDER_NO = "autoLogin";

    /**
     * 存储初始化设置
     *
     * @param initBean
     */
//    public static void saveInitConfig(InitBean initBean) {
//
//        if (initBean == null) {
//            return;
//        }
//
//        Gson gson = new Gson();
//        String initJson = gson.toJson(initBean);
//        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BaseApplication.APP_CONTEXT, CustomConstant.sp_name);
//        sharedPreferencesUtil.setData(INIT_INFO, initJson);
//    }

    /***
     * 获取初始化信息
     *
     * @return
     */
//    public static InitBean getInitConfig() {
//
//        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BaseApplication.APP_CONTEXT, CustomConstant.sp_name);
//        String initJson = (String) sharedPreferencesUtil.getData(INIT_INFO);
//
//        if (TextUtils.isEmpty(initJson)) {
//            return null;
//        }
//
//        Gson gson = new Gson();
//        InitBean initBean = gson.fromJson(initJson, InitBean.class);
//
//        return initBean;
//    }

    /**
     * 存储用户信息
     *
     * @param user
     */
    public static void saveUserInfo(User user) {
        if (user == null) {
            return;
        }
        //CustomConstant.TOKEN = user.token;
        Gson gson = new Gson();
        String userJson = gson.toJson(user);

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BaseApplication.APP_CONTEXT, CustomConstant.sp_name);
        sharedPreferencesUtil.setData(USER_INFO, userJson);

    }
    /**
     * 获取用户id
     *
     * @param
     */
    public static Integer getUserId(Context context) {
        User userInfo = getUserInfo();
        if (userInfo == null) {
            CustomToast.INSTANCE.showToast(context,"请先登录");
            return -1;
        }else {
            return userInfo.getId();
        }

    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public static User getUserInfo() {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BaseApplication.APP_CONTEXT, CustomConstant.sp_name);
        String userJson = (String) sharedPreferencesUtil.getData(USER_INFO);

        if (TextUtils.isEmpty(userJson)) {
            return null;
        }

        Gson gson = new Gson();
        User user = gson.fromJson(userJson, User.class);

        return user;
    }


    /***
     * 设置自动登录
     */
    public static void setAutoLogin(String autoLogin) {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BaseApplication.APP_CONTEXT, CustomConstant.sp_name);
        sharedPreferencesUtil.setData(AUTO_LOGIN, autoLogin);

    }

    /**
     * 判断是否要自动登录
     *
     * @return
     */
    public static boolean isAutoLogin() {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BaseApplication.APP_CONTEXT, CustomConstant.sp_name);
        String autoLoginJson = (String) sharedPreferencesUtil.getData(AUTO_LOGIN);

        return TextUtils.equals("1", autoLoginJson);

    }


    /**
     * 可选更新登录一次提醒一次，在使用过程中只要不退出就不提醒。如果强制更新则在使用过程中是提示出来的 设置更新提醒
     */
    public static void setCheckAppUpdate() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BaseApplication.APP_CONTEXT, CustomConstant.sp_name);
        sharedPreferencesUtil.setData("checkAppUpdate", "1");
    }

    /**
     * 得到更新提醒标识
     */
    public static String getCheckAppUpdate() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BaseApplication.APP_CONTEXT, CustomConstant.sp_name);
        return (String) sharedPreferencesUtil.getData("checkAppUpdate");
    }

    public static String getSkipUpdateTime() {
        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BaseApplication.APP_CONTEXT, CustomConstant.sp_name);

        return (String) sharedPreferencesUtil.getData("skipUpdateTime");
    }

    public static void setSkipUpdateTime(long skipTime) {

        SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(BaseApplication.APP_CONTEXT, CustomConstant.sp_name);

        sharedPreferencesUtil.setData("skipUpdateTime", String.valueOf(skipTime));

    }


}
