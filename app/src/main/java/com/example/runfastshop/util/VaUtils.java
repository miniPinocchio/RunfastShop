package com.example.runfastshop.util;

import java.util.regex.Pattern;

/**
 * 验证工具类
 *
 * @author Sun.bl
 * @version [1.0, 2016/8/5]
 */
public class VaUtils {

    private VaUtils() {
        throw new AssertionError("Utils Class");
    }


    /**
     * 是否是手机号
     *
     * @param mobileNo 手机号:13[0-9] 15没有4 18[0-9] 170
     * @return boolean true是,false 否
     */
    public static boolean isMobileNo(String mobileNo) {
        if (null == mobileNo) {
            return false;
        }
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[012356789])|(17[012356789])|(14[012356789]))\\d{8}$");
        return p.matcher(mobileNo).matches();
    }

    /** * 检测是否有emoji表情 * @param source * @return */
    public static boolean containsEmoji(String source) { //两种方法限制emoji
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmojiCharacter(codePoint)) { //如果不能匹配,则该字符是Emoji表情
                return true;
            }
        }
        return false;
    }

    /** * 判断是否是Emoji * @param codePoint 比较的单个字符 * @return */
    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

}
