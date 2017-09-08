package com.example.runfastshop.bean.location;

import java.util.List;

/**
 * Created by huiliu on 2017/9/5.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class LocationInfos {
    private List<LocationInfo> data ;

    public void setData(List<LocationInfo> data){
        this.data = data;
    }
    public List<LocationInfo> getData(){
        return this.data;
    }
}
