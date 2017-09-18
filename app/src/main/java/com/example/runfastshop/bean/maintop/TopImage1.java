package com.example.runfastshop.bean.maintop;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huiliu on 2017/9/3.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class TopImage1 implements Parcelable{
    private int id;

    private String title;

    private String adImages;

    private String linkAddr;

    private int adRank;

    private int used;

    private String typename;

    private int adType;

    private int location;

    private int num;

    private String content;

    private String agentName;

    private int agentId;

    private int type;

    private String createTime;

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
    public void setNum(int num){
        this.num = num;
    }
    public int getNum(){
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.adImages);
        dest.writeString(this.linkAddr);
        dest.writeInt(this.adRank);
        dest.writeInt(this.used);
        dest.writeString(this.typename);
        dest.writeInt(this.adType);
        dest.writeInt(this.location);
        dest.writeInt(this.num);
        dest.writeString(this.content);
        dest.writeString(this.agentName);
        dest.writeInt(this.agentId);
        dest.writeInt(this.type);
        dest.writeString(this.createTime);
    }

    public TopImage1() {
    }

    protected TopImage1(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.adImages = in.readString();
        this.linkAddr = in.readString();
        this.adRank = in.readInt();
        this.used = in.readInt();
        this.typename = in.readString();
        this.adType = in.readInt();
        this.location = in.readInt();
        this.num = in.readInt();
        this.content = in.readString();
        this.agentName = in.readString();
        this.agentId = in.readInt();
        this.type = in.readInt();
        this.createTime = in.readString();
    }

    public static final Creator<TopImage1> CREATOR = new Creator<TopImage1>() {
        @Override
        public TopImage1 createFromParcel(Parcel source) {
            return new TopImage1(source);
        }

        @Override
        public TopImage1[] newArray(int size) {
            return new TopImage1[size];
        }
    };
}
