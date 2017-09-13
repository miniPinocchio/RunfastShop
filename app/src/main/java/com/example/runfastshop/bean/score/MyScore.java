package com.example.runfastshop.bean.score;

import java.math.BigDecimal;

/**
 * Created by huiliu on 2017/9/13.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class MyScore {
    private Integer id;
    private Integer cid;//用户ID
    private String mobile;//电话号码
    private String name;//用户名称
    private Integer type;//积分类型1注册积分、2消费积分(下单消费)、3推荐积分、4分享积分、5收藏积分、6评论积分、7广告积分、8购物消费
    private Double score;//积分值

    private String cardnumber;//消费单号（消费类型才有）
    private BigDecimal monetary;//消费金额

    private String createTime;//时间

    private String startTime;//查询时使用
    private String endTime;//查询时使用
    private String keyword;//查询时使用

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public BigDecimal getMonetary() {
        return monetary;
    }

    public void setMonetary(BigDecimal monetary) {
        this.monetary = monetary;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
