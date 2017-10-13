package com.example.runfastshop.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by 天上白玉京 on 2017/8/12.
 */

public class GsonUtil {

    //将Json数据解析成相应的映射对象
    public static <T> T parseJsonWithGson(String jsonData, Class<T> type) {
        Gson gson = new Gson();
        T result = gson.fromJson(jsonData, type);
        return result;
    }

    //将Json数据解析成相应的映射对象
    public static String parseJsonWithBean(List<Object> type) {
        Gson gson = new Gson();
        String json = gson.toJson(type);
        return json;
    }

    public static <T> T fromJson(String str, Class<T> type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }

    public static <T> T fromJson(String str, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(str, type);
    }

}
