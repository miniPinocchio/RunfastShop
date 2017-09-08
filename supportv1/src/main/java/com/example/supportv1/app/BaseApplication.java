package com.example.supportv1.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.example.supportv1.assist.netWork.OFHttpUrlWork;
import com.example.supportv1.constant.Consts;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;


public class BaseApplication extends Application {
    // app上下文
    public static Context APP_CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();
        APP_CONTEXT = getApplicationContext();
//        initSSL(this);
    }

    /***
     * 初始化证书信息
     *
     * @param context 上下文环境
     */
    protected void initSSL(Context context) {

        try {
            //获取默认的信任证书管理器
            InputStream cerInputStream = null;
            if (!TextUtils.isEmpty(Consts.CER_STR)) {
                cerInputStream = new ByteArrayInputStream(Consts.CER_STR.getBytes());
            }
            if (cerInputStream == null) {
                cerInputStream = getAssetsFileStream(context, Consts.CER_NAME);
            }
            TrustManager[] trustManagers = OFHttpUrlWork.getTrustManagerArray(cerInputStream);
            //获取默认的KEY管理器
            InputStream keyInputStream = getAssetsFileStream(context, Consts.KEY_NMAE);
            if (keyInputStream == null && cerInputStream == null) {
                return;
            }
            KeyManager[] keyManagers = OFHttpUrlWork.getKeyManagerArray(Consts.KEY_PASSWORD, keyInputStream);
            //初始化SSL
            OFHttpUrlWork.sSSLContext = OFHttpUrlWork.initSSLContext(keyManagers, trustManagers);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 获取Assets文件夹中的文件流
     *
     * @param context 上下文环境
     * @param name    文件名称
     * @return 文件流
     */
    private InputStream getAssetsFileStream(Context context, String name) {

        try {
            return context.getAssets().open(name);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
