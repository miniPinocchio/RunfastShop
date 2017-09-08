package com.example.runfastshop.bean.mainmiddle;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huiliu on 2017/9/1.
 *
 * @email liu594545591@126.com
 * @introduce 首页分类
 */
public class MiddleSort implements Parcelable{
    private int id;

    private String name;

    private String icon;

    private int typelink;

    private String link;

    private int sort;

    private int will;

    private String createTime;

    private String typename;

    private String agentName;

    private int agentId;

    private String startTime;

    private String endTime;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }
    public String getIcon(){
        return this.icon;
    }
    public void setTypelink(int typelink){
        this.typelink = typelink;
    }
    public int getTypelink(){
        return this.typelink;
    }
    public void setLink(String link){
        this.link = link;
    }
    public String getLink(){
        return this.link;
    }
    public void setSort(int sort){
        this.sort = sort;
    }
    public int getSort(){
        return this.sort;
    }
    public void setWill(int will){
        this.will = will;
    }
    public int getWill(){
        return this.will;
    }
    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public void setTypename(String typename){
        this.typename = typename;
    }
    public String getTypename(){
        return this.typename;
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
    public void setStartTime(String startTime){
        this.startTime = startTime;
    }
    public String getStartTime(){
        return this.startTime;
    }
    public void setEndTime(String endTime){
        this.endTime = endTime;
    }
    public String getEndTime(){
        return this.endTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.icon);
        dest.writeInt(this.typelink);
        dest.writeString(this.link);
        dest.writeInt(this.sort);
        dest.writeInt(this.will);
        dest.writeString(this.createTime);
        dest.writeString(this.typename);
        dest.writeString(this.agentName);
        dest.writeInt(this.agentId);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
    }

    public MiddleSort() {
    }

    protected MiddleSort(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.icon = in.readString();
        this.typelink = in.readInt();
        this.link = in.readString();
        this.sort = in.readInt();
        this.will = in.readInt();
        this.createTime = in.readString();
        this.typename = in.readString();
        this.agentName = in.readString();
        this.agentId = in.readInt();
        this.startTime = in.readString();
        this.endTime = in.readString();
    }

    public static final Creator<MiddleSort> CREATOR = new Creator<MiddleSort>() {
        @Override
        public MiddleSort createFromParcel(Parcel source) {
            return new MiddleSort(source);
        }

        @Override
        public MiddleSort[] newArray(int size) {
            return new MiddleSort[size];
        }
    };
}
