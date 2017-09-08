package com.example.runfastshop.bean.maintop;

/**
 * Created by huiliu on 2017/9/4.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class MapInfo {
    private double latitude;

    private String name;

    private String content;

    private double longitude;

    public void setLatitude(double latitude){
        this.latitude = latitude;
    }
    public double getLatitude(){
        return this.latitude;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }
    public double getLongitude(){
        return this.longitude;
    }

}
