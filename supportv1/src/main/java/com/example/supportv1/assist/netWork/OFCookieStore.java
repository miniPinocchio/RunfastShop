package com.example.supportv1.assist.netWork;

import android.support.v4.util.ArrayMap;

import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Cookie存储
 *
 * @author Sun.bl
 * @version [1.0, 2016/4/8]
 */
public class OFCookieStore implements CookieStore {


    /**
     * 所有的Cookie列表
     */
    private Map<URI, List<HttpCookie>> allCookies;

    @Override
    public void add(URI uri, HttpCookie cookie) {
        //Cookie列表初始化判断
        if (allCookies == null) {
            allCookies = new ArrayMap<>();
        }


        List<HttpCookie> oldCookies = allCookies.get(uri);

        //旧Cookie列表判断
        if (oldCookies == null) {
            oldCookies = new ArrayList<>();
            oldCookies.add(cookie);
            allCookies.put(uri, oldCookies);
            return;
        }
        //如果有旧的Cookie列表就移除相同的Cookie
        HttpCookie removeCookie = null;
        for (HttpCookie oldCookie : oldCookies) {
            if (!cookie.getName().equals(oldCookie.getName())) {
                continue;
            }
            removeCookie = oldCookie;
            break;
        }
        if (removeCookie != null) {
            oldCookies.remove(removeCookie);
        }
        //添加Cookie
        oldCookies.add(cookie);
    }

    @Override
    public List<HttpCookie> get(URI uri) {

        if (allCookies != null) {
            return allCookies.get(uri);
        }
        return new ArrayList<>();
    }

    @Override
    public List<HttpCookie> getCookies() {
        if (allCookies != null) {
            List<HttpCookie> cookieList = new ArrayList<>();
            Set<URI> keySet = allCookies.keySet();
            for (URI uri : keySet) {
                cookieList.addAll(allCookies.get(uri));
            }
            return cookieList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<URI> getURIs() {
        return allCookies == null ? new ArrayList<URI>() : new ArrayList<>(allCookies.keySet());
    }

    /***
     * 移除Cookie
     *
     * @param uri
     * @param cookie
     * @return
     */
    @Override
    public boolean remove(URI uri, HttpCookie cookie) {
        if (!allCookies.containsKey(uri)) {
            return false;
        }
        List<HttpCookie> cookieList = allCookies.get(uri);
        for (HttpCookie httpCookie : cookieList) {
            if (!cookie.getName().equals(httpCookie.getName())) {
                continue;
            }
            cookieList.remove(cookie);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll() {
        allCookies.clear();
        return true;
    }


}
