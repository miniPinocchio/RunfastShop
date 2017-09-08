package com.example.runfastshop.bean.maintop;

import java.io.Serializable;

/**
 * Created by huiliu on 2017/9/1.
 *
 * @email liu594545591@126.com
 * @introduce 轮播图
 */
public class TopImage implements Serializable{
    private int id;//广告id

    private String title;//广告标题

    private String adImages;//广告图片

    private String linkAddr;//链接地址

    private int adRank;//排序

    private int used;//是否启用:1启用,0不启用

    private String typename;//对象名称

    private int adType;//链接类型 1内容 2链接 3商家分类类型、4商家、5商品、6自定义

    private int location;//广告位置 1首页顶部轮播大图广告 2外卖首页中通栏横幅左侧广告 3外卖首页中通栏横幅右侧广告 4外卖首页底部横幅广告

    private String num;//底部位置在第几位插入

    private String content;//广告内容

    private String agentName;//代理商名称

    private int agentId;//代理商Id

    private int type;//广告类型0外卖1商城2一元购

    private String createTime;//添加时间

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setAdImages(String adImages){
        this.adImages = adImages;
    }
    public String getAdImages(){
        return this.adImages;
    }
    public void setLinkAddr(String linkAddr){
        this.linkAddr = linkAddr;
    }
    public String getLinkAddr(){
        return this.linkAddr;
    }
    public void setAdRank(int adRank){
        this.adRank = adRank;
    }
    public int getAdRank(){
        return this.adRank;
    }
    public void setUsed(int used){
        this.used = used;
    }
    public int getUsed(){
        return this.used;
    }
    public void setTypename(String typename){
        this.typename = typename;
    }
    public String getTypename(){
        return this.typename;
    }
    public void setAdType(int adType){
        this.adType = adType;
    }
    public int getAdType(){
        return this.adType;
    }
    public void setLocation(int location){
        this.location = location;
    }
    public int getLocation(){
        return this.location;
    }
    public void setNum(String num){
        this.num = num;
    }
    public String getNum(){
        return this.num;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setAgentName(String agentName){
        this.agentName = agentName;
    }
    public String getAgentName(){
        return this.agentName;
    }
    public void setAgentId(int agentId){
        this.agentId = agentId;
    }
    public int getAgentId(){
        return this.agentId;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return this.type;
    }
    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
}
