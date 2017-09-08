package com.example.runfastshop.bean.business;

/**
 * Created by huiliu on 2017/9/6.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class Imgs {
    private int id;//商家食品安全档案id

    private String imgUrl;//图片

    private int busID;//所属商家

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setImgUrl(String imgUrl){
        this.imgUrl = imgUrl;
    }
    public String getImgUrl(){
        return this.imgUrl;
    }
    public void setBusID(int busID){
        this.busID = busID;
    }
    public int getBusID(){
        return this.busID;
    }

}
