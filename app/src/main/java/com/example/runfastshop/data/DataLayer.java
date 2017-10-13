package com.example.runfastshop.data;

import android.app.Application;
import android.content.Intent;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

import com.example.runfastshop.BuildConfig;
import com.example.runfastshop.activity.LoginActivity;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.util.MD5Util;
import com.example.supportv1.assist.netWork.CookieManger;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.paperdb.Paper;
import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * 数据层
 * Created by Berial on 2017/8/24.
 */
public final class DataLayer {

    private static Application aplication; // 缓存文件
    private static final String RESPONSE_CACHE_FILE = "responseCache"; // 缓存文件

    private static final long RESPONSE_CACHE_SIZE = 10 * 1024 * 1024L; // 缓存大小
    private static final long HTTP_CONNECT_TIMEOUT = 10L; // 连接超时时间
    private static final long HTTP_READ_TIMEOUT = 30L; // 读取超时时间
    private static final long HTTP_WRITE_TIMEOUT = 10L; // 写入超时时间

    private final Gson gson = new Gson();

    private OkHttpClient mOkHttpClient;

    private DataLayer() {
    }

    public static DataLayer getInstance() {
        return DataLayerHolder.INSTANCE;
    }

    private static class DataLayerHolder {
        private static final DataLayer INSTANCE = new DataLayer();
    }

    public static OkHttpClient getClient() {
        return getInstance().mOkHttpClient;
    }

    public static Gson getGson() {
        return getInstance().gson;
    }

    public static void init(Application app) {
        aplication = app;
        Paper.init(app);

        File cacheFile = new File(app.getCacheDir(), RESPONSE_CACHE_FILE);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    message = message.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
//                    Log.d("devon", "OkHttp====Message111:" + message);
                    message = URLDecoder.decode(message, "UTF-8");
                    Log.d("devon", "OkHttp====Message:" + message);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }
        });

        logging.setLevel(BuildConfig.DEBUG ?
                HttpLoggingInterceptor.Level.BODY :
                HttpLoggingInterceptor.Level.NONE
        );

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(new Cache(cacheFile, RESPONSE_CACHE_SIZE))
                .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(new CookieManger(app.getApplicationContext()))
                .addInterceptor(new NetworkIntercept())
                .addInterceptor(logging);

        getInstance().mOkHttpClient = builder.build();
    }

    private static class NetworkIntercept implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);

            String json = response.body().string();
//            json = json.replace("{\"relogin\":1}", "");

//            Logger.json(json);
            return response.newBuilder()
                    .body(ResponseBody.create(MediaType.parse("application/json"), json))
                    .build();
        }
    }

    private static class CookieStore implements CookieJar {

        private SimpleArrayMap<String, List<Cookie>> map = new SimpleArrayMap<>();

        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            map.put(url.toString(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = map.get(url.toString());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    }
}
