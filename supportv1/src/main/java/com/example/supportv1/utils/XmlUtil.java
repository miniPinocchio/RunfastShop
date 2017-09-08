package com.example.supportv1.utils;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class XmlUtil {

    private static final String TAG = "XmlUtil";

    /**
     * xml转成对象<br>
     * 要求格式为： <br>
     * &lt; ?xml version="1.0" encoding="UTF-8"?&gt;<br>
     * &lt;students&gt;<br>
     * &lt;total&gt;33&lt;/total&gt;<br>
     * &lt;list&gt;<br>
     * &lt;user&gt;<br>
     * &lt;id&gt;1&lt;/id&gt;&lt;userName&gt;张三&lt;/userName&gt;&lt;email&gt;dsss@xxx.com&lt;/email&gt;<br>
     * &lt;/user&gt;<br>
     * &lt;user id="2"&gt;<br>
     * &lt;userName&gt;李四&lt;/userName&gt;&lt;email&gt;lisi@xxx.com&lt;/email&gt;<br>
     * &lt;/user&gt;<br>
     * &lt;user id="3"&gt;<br>
     * &lt;userName&gt;王五&lt;/userName&gt;&lt;email&gt;wangwu@xxx.com&lt;/email&gt;</br>
     * &lt;/user&gt;</br>&lt;/list&gt;</br>&lt;/students&gt;
     *
     * @param inputStream xml输入流
     * @param valueType   对象类名
     * @return 对象
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> T xml2Object(InputStream inputStream, Class<T> clazz) {
        XmlPullParser parser = Xml.newPullParser();
        Object object = null;
        List list = null;
        // 子标签对应的类
        Class<?> subClass = null;
        Object subObject = null;
        // List标签里直属下级子标签名称
        String tagName = "";
        try {
            parser.setInput(inputStream, "UTF-8");
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        object = clazz.newInstance();
                        break;
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();

                        // 这里得到赋值List子标签名称
                        if ("java.util.List".equals(tagName)) {
                            tagName = name;
                        }

                        Field[] f = null;
                        if (subClass == null) {
                            f = object.getClass().getDeclaredFields();

                            int count = parser.getAttributeCount();
                            for (int j = 0; j < count; j++)
                                setXmlValue(object, parser.getAttributeName(j), parser.getAttributeValue(j));
                        } else {
                            if (subObject == null) {
                                subObject = subClass.newInstance();
                            }
                            f = subObject.getClass().getDeclaredFields();
                        }

                        for (int i = 0; i < f.length; i++) {
                            if (f[i].getName().equalsIgnoreCase(name)) {
                                if (f[i].getType().getName().equals("java.util.List")) {
                                    // 先赋值List标签
                                    tagName = "java.util.List";
                                    Type type = f[i].getGenericType();
                                    if (type instanceof ParameterizedType) {
                                        subClass = (Class<?>) ((ParameterizedType) type).getActualTypeArguments()[0];
                                        subObject = subClass.newInstance();

                                        int count = parser.getAttributeCount();
                                        for (int j = 0; j < count; j++)
                                            setXmlValue(subObject, parser.getAttributeName(j), parser.getAttributeValue(j));

                                        if (list == null) {
                                            list = new ArrayList<Object>();
                                            f[i].setAccessible(true);
                                            f[i].set(object, list);
                                        }
                                    }
                                } else {
                                    if (subObject != null) {
                                        setXmlValue(subObject, name, parser.nextText());
                                    } else {
                                        setXmlValue(object, name, parser.nextText());
                                    }
                                }
                                break;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (subObject != null && tagName.equalsIgnoreCase(parser.getName())) {
                            list.add(subObject);
                            subObject = null;
                        }
                        break;
                }
                eventType = parser.next();

            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
        return (T) object;
    }

    /**
     * 把xml标签的值，转换成对象里属性的值
     *
     * @param t     对象
     * @param name  xml标签名
     * @param value xml标签名对应的值
     */
    private static void setXmlValue(Object t, String name, String value) {
        try {
            Field[] f = t.getClass().getDeclaredFields();
            for (int i = 0; i < f.length; i++) {
                if (f[i].getName().equalsIgnoreCase(name)) {
                    f[i].setAccessible(true);
                    // 获得属性类型
                    Class<?> fieldType = f[i].getType();

                    if (fieldType == String.class) {
                        f[i].set(t, value);
                    } else if (fieldType == Integer.TYPE) {
                        f[i].set(t, Integer.parseInt(value));
                    } else if (fieldType == Float.TYPE) {
                        f[i].set(t, Float.parseFloat(value));
                    } else if (fieldType == Double.TYPE) {
                        f[i].set(t, Double.parseDouble(value));
                    } else if (fieldType == Long.TYPE) {
                        f[i].set(t, Long.parseLong(value));
                    } else if (fieldType == Short.TYPE) {
                        f[i].set(t, Short.parseShort(value));
                    } else if (fieldType == Boolean.TYPE) {
                        f[i].set(t, Boolean.parseBoolean(value));
                    } else {
                        f[i].set(t, value);
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
