package com.example.runfastshop.bean.mainmiddle;

import java.util.List;

/**
 * Created by huiliu on 2017/9/4.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class ClassTypeInfos {
    private String url;

    private List<ClassTypeInfo> bustype ;

    private TypeMapInfo map;

    private String name;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ClassTypeInfo> getBustype() {
        return bustype;
    }

    public void setBustype(List<ClassTypeInfo> bustype) {
        this.bustype = bustype;
    }

    public TypeMapInfo getMap() {
        return map;
    }

    public void setMap(TypeMapInfo map) {
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
