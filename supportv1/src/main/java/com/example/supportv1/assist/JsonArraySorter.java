package com.example.supportv1.assist;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 * JSONArray的比较器
 */
public class JsonArraySorter implements Comparator<JSONObject> {

    private static final String TAG = "JsonArraySorter";

    final public static int INT_TYPE = 0;
    final public static int STRING_TYPE = 1;
    /**
     * 比较方式（0数据类型，1字符串类型）
     */
    private int valueType;

    public int getValueType() {
        return valueType;
    }

    public void setValueType(int valueType) {
        this.valueType = valueType;
    }

    /**
     * 要排序的标签名称
     */
    private String label;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int compare(JSONObject arg0, JSONObject arg1) {
        int result = 0;
        if (arg0 == null && arg1 == null) {
            return 0;
        } else if (arg0 == null) {
            return 1;
        } else if (arg1 == null) {
            return -1;
        }
        try {
            if (valueType == 0) {
                long param0 = arg0.getLong(label);
                long param1 = arg1.getLong(label);
                if (param0 < param1) {
                    result = -1;
                } else if (param0 > param1) {
                    result = 1;
                }
            } else if (valueType == 1) {
                result = Collator.getInstance(Locale.CHINESE).compare(arg0.getString(label), arg1.getString(label));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

}
