package com.example.runfastshop.bean.message;

/**
 * Created by huiliu on 2017/9/5.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class MessageInfo {
    private String agentName;

    private String agentId;
    private Integer id;
    private Integer type;//消息类型1系统消息2商家消息3骑手消息4用户消息5确认订单提示消息
    private Integer userId;//用户ID
    private String userName;//用户名称
    private String title;//消息标题
    private String content;//消息内容
    private String createTime;//发布时间
    private String start;//有效期开始时间
    private String end;//有效期结束时间
    private String cityId;//城市
    private String cityName;
    private String countyId;//县份
    private String countyName;
    private String townId;//乡镇
    private String townName;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setType(int type){
        this.type = type;
    }
    public int getType(){
        return this.type;
    }
    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserId(){
        return this.userId;
    }
    public void setUserName(String userName){
        this.userName = userName;
    }
    public String getUserName(){
        return this.userName;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){
        return this.title;
    }
    public void setContent(String content){
        this.content = content;
    }
    public String getContent(){
        return this.content;
    }
    public void setCreateTime(String createTime){
        this.createTime = createTime;
    }
    public String getCreateTime(){
        return this.createTime;
    }
    public void setStart(String start){
        this.start = start;
    }
    public String getStart(){
        return this.start;
    }
    public void setEnd(String end){
        this.end = end;
    }
    public String getEnd(){
        return this.end;
    }
    public void setCityId(String cityId){
        this.cityId = cityId;
    }
    public String getCityId(){
        return this.cityId;
    }
    public void setCityName(String cityName){
        this.cityName = cityName;
    }
    public String getCityName(){
        return this.cityName;
    }
    public void setCountyId(String countyId){
        this.countyId = countyId;
    }
    public String getCountyId(){
        return this.countyId;
    }
    public void setCountyName(String countyName){
        this.countyName = countyName;
    }
    public String getCountyName(){
        return this.countyName;
    }
    public void setTownId(String townId){
        this.townId = townId;
    }
    public String getTownId(){
        return this.townId;
    }
    public void setTownName(String townName){
        this.townName = townName;
    }
    public String getTownName(){
        return this.townName;
    }
    public void setAgentName(String agentName){
        this.agentName = agentName;
    }
    public String getAgentName(){
        return this.agentName;
    }
    public void setAgentId(String agentId){
        this.agentId = agentId;
    }
    public String getAgentId(){
        return this.agentId;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                "id=" + id +
                ", type=" + type +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countyId='" + countyId + '\'' +
                ", countyName='" + countyName + '\'' +
                ", townId='" + townId + '\'' +
                ", townName='" + townName + '\'' +
                ", agentName='" + agentName + '\'' +
                ", agentId='" + agentId + '\'' +
                '}';
    }
}
