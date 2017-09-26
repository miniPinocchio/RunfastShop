package shopex.cn.sharelibrary.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

/**
 * Created by p on 2016/6/23.
 */
public class Util {

    public static final String TAG = "shopexShared";

    public static final String SDCardPath = android.os.Environment
            .getExternalStorageDirectory().getAbsolutePath();

    public static final String doFolder = buildString(SDCardPath,
            File.separator, TAG, File.separator);
    /**
     * 用来提示Tost信息
     * @param context  上下文
     * @param str      msg
     */
    public static final void alert(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 打印日志
     * @param tag
     * @param msg
     */
    public static void  LogUtils(String tag,String msg)
    {
        Log.i(tag,"shopex.cn.sharelibary"+"++++++++"+"["+msg+"]"+"+++++++++++++");
    }
    /**
     * 创建字符串方法，当需要组装2个以上的字符串时请使用这个方法
     *
     * @param element
     * @return
     */
    public static String buildString(Object... element) {
        StringBuffer sb = new StringBuffer();
        for (Object str : element) {
            sb.append(str);
        }
        return sb.toString();
    }
    public static final String doImageCacheFolder = buildString(doFolder, "ImageCache",
            File.separator);
    public static String getImageCacheFile(String url) {
        return Util.buildString(doImageCacheFolder, Md5.getMD5(url));
    }
}
