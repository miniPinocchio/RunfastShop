package com.example.runfastshop.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Devon on 2017/9/30.
 */

public class WeiXinPayBean {


    /**
     * success : true
     * map : {"sign":"385F7F86BB74A3639872974077F41A99","appId":"wx7b0ceb559269b805","timeStamp":"1506757474993","signType":"MD5","package":"prepay_id=wx20170930154441b01bef95600693547731","nonceStr":"9MzsWKqCCS8gahzEivr1","success":"yes"}
     * prepay_id : wx20170930154441b01bef95600693547731
     */

    public boolean success;
    public MapBean map;
    public String prepay_id;

    public static class MapBean {
        /**
         * sign : 385F7F86BB74A3639872974077F41A99
         * appId : wx7b0ceb559269b805
         * timeStamp : 1506757474993
         * signType : MD5
         * package : prepay_id=wx20170930154441b01bef95600693547731
         * nonceStr : 9MzsWKqCCS8gahzEivr1
         * success : yes
         */

        public String sign;
        public String appId;
        public String timeStamp;
        public String signType;
        @SerializedName("package")
        public String packageX;
        public String nonceStr;
        public String success;

    }

    public WeiXinPayBean() {
    }

}
