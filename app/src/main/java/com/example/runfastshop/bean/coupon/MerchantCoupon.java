package com.example.runfastshop.bean.coupon;

import java.math.BigDecimal;

/**
 * Created by huiliu on 2017/9/13.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class MerchantCoupon {
    private Integer id;
    private String name; //名称
    private String start;//有效开始时间
    private String end;//结束时间
    private BigDecimal full; //满多少使用
    private String createTime;
    private Integer range1;//使用范围 1 全部 2指定商家3指定代理商
    private Integer rangeId;//指定商家Id
    private String businessName;//商家名称
    private BigDecimal price;//优惠金额
    private Integer quantity;//数量
    private Integer limitedNum;//限领数量

    private String agentName;//代理商名称：
    private Integer agentId;//代理商Id：
    private Integer islimited;//是否限时使用1是
    private String startuse;//使用开始时间
    private String enduse;//使用结束时间
    private Integer type;//优惠券类型1商城0外卖
    private  Integer isget;//是否领取 1：领取 2：未领取

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public BigDecimal getFull() {
        return full;
    }

    public void setFull(BigDecimal full) {
        this.full = full;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getRange1() {
        return range1;
    }

    public void setRange1(Integer range1) {
        this.range1 = range1;
    }

    public Integer getRangeId() {
        return rangeId;
    }

    public void setRangeId(Integer rangeId) {
        this.rangeId = rangeId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getLimitedNum() {
        return limitedNum;
    }

    public void setLimitedNum(Integer limitedNum) {
        this.limitedNum = limitedNum;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }

    public Integer getIslimited() {
        return islimited;
    }

    public void setIslimited(Integer islimited) {
        this.islimited = islimited;
    }

    public String getStartuse() {
        return startuse;
    }

    public void setStartuse(String startuse) {
        this.startuse = startuse;
    }

    public String getEnduse() {
        return enduse;
    }

    public void setEnduse(String enduse) {
        this.enduse = enduse;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsget() {
        return isget;
    }

    public void setIsget(Integer isget) {
        this.isget = isget;
    }
}
