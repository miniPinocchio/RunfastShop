package com.example.runfastshop.application;

import android.content.Context;

import com.example.runfastshop.BuildConfig;
import com.example.runfastshop.data.ApiServiceFactory;
import com.example.runfastshop.data.DataLayer;
import com.example.runfastshop.impl.NetInterface;
import com.example.runfastshop.impl.constant.UrlConstant;
import com.example.supportv1.app.BaseApplication;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.xutils.x;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 * @author Sun.bl
 * @version [1.0, 2016/06/19]
 */

public class CustomApplication extends BaseApplication {

    //public static Place currentPosition; //当前位置

    //public final static String PAY_APP_ID = "wx1de37406286c1bb4";

    private static Context context;

    private static NetInterface mInterface;

    //public static IWXAPI sIWXAPIPay;

    //public static LocationClient mLocationClient;


    @Override
    public void onCreate() {
        super.onCreate();
        //MobclickAgent.setDebugMode(true);
        //registerPayToWx();
        //SDKInitializer.initialize(getApplicationContext());
        //ShareSDK.initSDK(this);
        //初始化 xutils的网络请求部分
        x.Ext.init(this);

        context = getApplicationContext();
//        initNet();
        //初始化网络
        DataLayer.init(this);
        Logger.addLogAdapter(new AndroidLogAdapter() {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        ImagePipelineConfig frescoConfig = ImagePipelineConfig.newBuilder(this).setDownsampleEnabled(true).build();
        Fresco.initialize(this, frescoConfig);
    }

    private void initNet() {
        Retrofit mRetrofit = new Retrofit.Builder()
                //添加网络请求的基地址
                .baseUrl(UrlConstant.BaseUrl)
                //增加返回值为String的支持
                .addConverterFactory(ScalarsConverterFactory.create())
                //添加转换工厂，用于解析json并转化为javaBean
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mInterface = mRetrofit.create(NetInterface.class);
    }


    public static NetInterface getRetrofit(){
        return ApiServiceFactory.getApi();
    }

    public static Context getContext(){
        return  context;
    }

    /**
     * 程序退出的时候 调用这个方法
     */
    @Override
    public void onTerminate() {
        super.onTerminate();

    }

    /**
     * 当程序运行内存不足的时候就会回调这个方法
     */
    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    private void registerPayToWx() {
        //通过工厂类获取对象
        //sIWXAPIPay = WXAPIFactory.createWXAPI(this, PAY_APP_ID);

        //将应用的APP_ID注册到微信
        //sIWXAPIPay.registerApp(PAY_APP_ID);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }
}
