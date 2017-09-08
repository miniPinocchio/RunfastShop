package com.example.supportv1.utils;

import java.util.regex.Pattern;

/**
 * 校验类
 *
 * @date 2014年6月25日
 **/
public class ValidateUtil {

    private ValidateUtil() {
        throw new AssertionError("Util class");
    }

    /**
     * 判断是否是数字
     *
     * @param str 需要校验字符串
     * @return true是, false 否
     */
    public static boolean isNumeric(String str) {
        if (null == str) {
            return false;
        }
        String reg = "^(\\d+)|(\\d+\\.?\\d+)$";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否固定电话,带区号或不带区号
     *
     * @param phoneNo 需要校验固定电话号码(区号+座机号码+分机号码):“0”+2位或3位数字+“-” 1位数字+6或7位数字
     *                “-”+1到4位分机号
     * @return boolean true是,false否
     */
    public static boolean isTelephone(String phoneNo) {
        if (null == phoneNo) {
            return false;
        }
        String phone = "(0[0-9]{2,3}-)?([2-9][0-9]{6,7})+(-[0-9]{1,4})?";
        Pattern p = Pattern.compile(phone);
        return p.matcher(phoneNo).matches();
    }

    /**
     * 是否是手机号
     *
     * @param mobileNo 手机号:13[0-9] 15没有4 18[0-9] 170 145 147
     * @return boolean true是,false 否
     */
    public static boolean isMobileNo(String mobileNo) {
        if (null == mobileNo) {
            return false;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[012356789])|170|147|145)\\d{8}$");
        return p.matcher(mobileNo).matches();
    }

    /**
     * 是否为固定电话或手机
     *
     * @param mobileNo 固定电话或手机
     * @return boolean true是,false 否
     */
    public static boolean isTelOrMob(String mobileNo) {
        if (null == mobileNo) {
            return false;
        }
        return isMobileNo(mobileNo) || isTelephone(mobileNo);
    }

    /**
     * 判断是否是邮箱地址
     *
     * @param email 邮箱地址
     * @return boolean true是,false 否
     */
    public static boolean isEmail(String email) {
        if (null == email) {
            return false;
        }
        String strPattern = "^[a-zA-Z0-9\\w\\.-]*@[a-zA-Z0-9\\w\\.-]*[a-zA-Z0-9]$";
        Pattern p = Pattern.compile(strPattern);
        return p.matcher(email).matches();
    }

}
