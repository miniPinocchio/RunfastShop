package com.example.runfastshop.impl.constant;

import android.os.Environment;

import java.io.File;

/**
 * 常量
 *
 * @author Sun.bl
 * @version [1.0, 2016/6/23]
 */
public class CustomConstant {

    public static String TOKEN = "";

    public static String keyStore = "";

    public static final String sp_name = "passenger";

    public static final String sp_order_no = "orderNo";

    public static final String start_address_name = "startAddressData";

    public static final String end_address_name = "endAddressData";

    public static final String start_address_num = "startAddressNum";

    public static final String end_address_num = "endAddressNum";

    public static final String BANK_LIST_INFO = "bankListInfo";

    public static final String location_address_info = "locationAddressInfo";

    //更新APP
    public final static String APP_UPDATE_NO = "0";//不更新

    public final static String APP_UPDATE_CHOOSE = "1";//选择更新

    public final static String APP_UPDATE_MUST = "2";//强制更新

    public final static int RESULT_OK = 10000001;//强制更新

    public static String FILE_DIR;// 应用存放文件的统一目录

    public static String CAMERA_DIR;// 拍照目录

    public static final int IMAGE_HEAD = 10001;//返回更新头像

    static
    {
        FILE_DIR = Environment.getExternalStorageDirectory().getPath() + "/huelead";
        CAMERA_DIR = FILE_DIR + "/photo";

        if (!new File(CAMERA_DIR).exists())
        {
            new File(CAMERA_DIR).mkdirs();
        }
    }

    // api访问地址。
    public static final String APP_URL = "http://www.tr-studio.cn/application/uploadfile";

    // SharedPreferences文件名,这个用来存放全局信息：用户信息等
    public static final String spName = "sp_shoprepair";

    // SharedPreferences文件名,用来存放初始化信息
    public static final String spInitName = "sp_initPre";

    // SharedPreferences文件名,用来存放历史查询条件
    public static final String spHistoryName = "sp_historyPre";

    // 数据库名称,存放维修项目
    public static final String DATABASE_NAME = "shoprepair";

    // 数据库密钥
    public static final String DATABASE_SECRETKEY = "zpshoprepair@#$%";

    // db version
    public static final int version = 2;

    // 当报修部位和内容没有时，用此显示
    public static final String OTHER_NAME = "其它";

    // 当报修部位和内容没有时,ID为other
    public static final String OTHER_ID = "other";

    // 品牌编号：用来标识是优衣库还是其它品牌，方便APP针对性布局
    public static final String YYK = "yyk";

    // 压缩图片最大宽和高及充许空间大小KB
    public static final int COMPRESS_IMAGE_MAX_WIDTH = 480;
    public static final int COMPRESS_IMAGE_MAX_HEIGHT = 800;

    // 压缩图片最大宽和高及充许空间大小KB
    public static final int MARK_IMAGE_MAX_WIDTH = 800;
    public static final int MARK_IMAGE_MAX_HEIGHT = 800;

    public static final int COMPRESS_IMAGE_MAX_SIZE = 512;

    // 密码md5加密附加码
    public static final String MD5_ADDITATION_CODE = "ynnus";

    public static final int CONN_TIMEOUT = 10000;// 设置连接超时时间最长10s
}
