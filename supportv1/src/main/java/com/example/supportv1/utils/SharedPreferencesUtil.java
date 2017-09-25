package com.example.supportv1.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class SharedPreferencesUtil
{
    /**
     * SharedPreferences对象
     */
    private SharedPreferences pref = null;
    /**
     * 默认文件名
     */
    private String name = "initSPre";

    /**
     * 构造函数
     * 
     * @param context
     *            下上文环境
     * @param name
     *            SharedPreferences文件名,如果为空则为“initSPre”
     */
    public SharedPreferencesUtil(Context context, String name)
    {
        this.name = StringUtil.isBlank(name) ? "initSPre" : name;
        pref = getSharedPreferences(context, this.name);
    }

    /**
     * 构造函数
     * 
     * @param context
     *            下上文环境
     * @param name
     *            SharedPreferences文件名,如果为空则为“initSPre”
     * @param mode
     *            文件权限
     */
    public SharedPreferencesUtil(Context context, String name, int mode)
    {
        this.name = StringUtil.isBlank(name) ? "initSPre" : name;
        pref = getSharedPreferences(context, this.name, mode);
    }

    /**
     * 生成SharedPreferences对象,默认为私有
     * 
     * @param context
     *            下上文环境
     * @param name
     *            SharedPreferences文件名,如果为空则为“initSPre”
     * @return SharedPreferences
     */
    private SharedPreferences getSharedPreferences(Context context, String name)
    {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 生成SharedPreferences对象
     * 
     * @param context
     *            下上文环境
     * @param name
     *            SharedPreferences文件名,如果为空则为“initSPre”
     * @param mode
     *            文件权限
     * @return SharedPreferences
     */
    private SharedPreferences getSharedPreferences(Context context, String name, int mode)
    {
        return context.getSharedPreferences(name, mode);
    }

    /**
     * 设置多组值
     * 
     * @param map
     */
    public void setDatas(Map<String, Object> map)
    {
        if (map != null)
        {
            Iterator<Entry<String, Object>> it = map.entrySet().iterator();
            while (it.hasNext())
            {
                Entry<String, Object> entry = it.next();
                setData(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * 设置一组值
     * 
     * @param key
     * @param object
     */
    public void setData(String key, Object object)
    {
        try
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);

            String personBase64 = new String(Base64.encode(baos.toByteArray(), Base64.DEFAULT));
            Editor editor = pref.edit();
            editor.putString(key, personBase64);
            editor.commit();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * 得到单个值
     * 
     * @param key
     * @return Object
     */
    public Object getData(String key)
    {
        try
        {
            String personBase64 = pref.getString(key, "");
            if (StringUtil.isBlank(personBase64))
            {
                return "";
            }
            byte[] base64Bytes = Base64.decode(personBase64.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object object = (Object) ois.readObject();
            return object;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 得到多组值
     * 
     * @param key
     *            可变化key
     * @return Map<String, Object>
     */
    public Map<String, Object> getDatas(String... key)
    {
        Map<String, Object> retMaps = new HashMap<String, Object>();
        for (String temp : key)
        {
            Object retObject = getData(temp);
            if (retObject != null)
            {
                retMaps.put(temp, retObject);
            }
        }
        return retMaps;
    }

    /**
     * 得到Map数据
     * 
     * @param key
     *            键值key
     * @return Map<String, Object>
     */
    @SuppressWarnings("unchecked")
    public Map<String, String> getDatasMap(String key)
    {
        try
        {
            String personBase64 = pref.getString(key, null);
            if (StringUtil.isBlank(personBase64))
            {
                return null;
            }
            byte[] base64Bytes = Base64.decode(personBase64.getBytes(), Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object object = (Object) ois.readObject();
            return (Map<String, String>) object;

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 清空
     */
    public void clear()
    {
        Editor editor = pref.edit();
        editor.clear();
        editor.apply();
    }

}
