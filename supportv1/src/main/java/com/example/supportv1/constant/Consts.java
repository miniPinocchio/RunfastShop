package com.example.supportv1.constant;

public class Consts {
    // 地图类型:百度，google
    public static final String MAPTYPE_BAIDU = "baidu";
    public static final String MAPTYPE_GOOGLE = "google";
    // 定位成功标识
    public static final int LOCATION_SUCCESS = 1;
    // 定位失败标识
    public static final int LOCATION_FAILED = 2;
    // 定位返回纬度标识
    public static final String LOCATION_LAT = "latitude";
    // 定位返回经度标识
    public static final String LOCATION_LON = "lontitude";

    // app缺省接口请求地址
    public static String APP_URL = "http://192.168.1.210:18080/application/application";
    //带文件上传请求地址
    public static String APP_UPLOAD_URL = "http://192.168.1.210:18080/application/cmdUploadLocal";


    // 数据库加密的缺省密钥
    public static final String DEFAULT_DB_SECRET_KEY = "aracy123";

    public static boolean LOG_DEBUG = true;

    public static String CER_STR = "";

    public static String CER_NAME = "server.cer";

    public static String KEY_NMAE = "client.bks";

    public static String KEY_PASSWORD = "aracy123";

}
