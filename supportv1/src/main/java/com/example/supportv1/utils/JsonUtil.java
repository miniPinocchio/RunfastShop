package com.example.supportv1.utils;

import android.util.Log;

import com.example.supportv1.assist.JsonArraySorter;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

;

/**
 * 存放JSON的处理方法
 */
public class JsonUtil {
    private static final String TAG = "JsonUtil";

    /**
     * 将json转换成对象
     *
     * @param json      传入的Gson对象
     * @param valueType 要转换的对象类名
     * @return 转换后的对象
     */
    public static <T> T json2Object(String json, Class<T> valueType) {
        T bean = null;
        try {
            Gson gson = new Gson();
            Type type = TypeToken.get(valueType).getType();
            bean = gson.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }

        return bean;
    }

    /**
     * 将json转换成对象
     *
     * @param json     传入的Gson对象
     * @param classOfT 要转换的类型名
     * @return 转换后的对象
     */
    public static <T> T json2Object(String json, Type classOfT) {
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

    /**
     * 将对象转换成jsonstring
     *
     * @param valueType 要传化的对象
     * @return json字符串
     */
    public static String object2Json(Object valueType) {
        String json = "";
        Gson gson = new Gson();
        json = gson.toJson(valueType);
        return json;
    }

    /**
     * 将JSONArray数组按field字段进行排序，order为排序类型（ASC为顺序及DESC倒序）
     *
     * @param ja        排序JSONArray数组
     * @param field     排序字段
     * @param valueType 排序字段类型：0数据类型，1字符串类型
     * @param order     ASC为升序,DESC降序
     * @return JSONArray 排序后的JSONArray数组
     */
    public static JSONArray sortJSONArray(JSONArray ja, String field, String valueType, String order) {
        JSONArray jSONArray = new JSONArray();
        JsonArraySorter sorter = new JsonArraySorter();
        try {
            if (ja != null && ja.length() > 0 && ja.getJSONObject(0) != null && ja.getJSONObject(0).has(field)) {

                List<JSONObject> list = new ArrayList<JSONObject>();

                for (int i = 0; i < ja.length(); i++) {
                    list.add(ja.getJSONObject(i));
                }
                String orderType = order;
                if (!"DESC".equalsIgnoreCase(order)) {
                    orderType = "ASC";
                }
                if ("0".equalsIgnoreCase(valueType)) {
                    sorter.setValueType(JsonArraySorter.INT_TYPE);
                } else {
                    sorter.setValueType(JsonArraySorter.STRING_TYPE);
                }
                sorter.setLabel(field);
                Collections.sort(list, sorter);
                if ("DESC".equalsIgnoreCase(orderType)) {
                    Collections.reverse(list);
                }

                for (JSONObject jsonObject : list) {
                    jSONArray.put(jsonObject);
                }

                return jSONArray;

            }
            return jSONArray;
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return jSONArray;
    }

}
