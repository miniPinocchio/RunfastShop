package com.example.runfastshop.bean.coupon;

/**
 * Created by huiliu on 2017/9/13.
 *
 * @email liu594545591@126.com
 * @introduce 优惠券
 */
public class CouponBean {
    private Integer id;
    private Integer couponId; //优惠券id
    private Integer cuserId; //领取用户id
    private String cuserName; //领取用户名称
    private String couponName; //优惠券名称
    private double price;//优惠金额
    private Integer quantity;//领取张数
    private String start;//有效开始时间
    private String end;//结束时间
    private double full; //满多少使用
    private String createTime;
    private Integer userd; //0未使用 1已使用
    private Integer range1;//使用范围 1 全部 2指定商家3指定代理商
    private Integer rangeId;//指定商家Id
    private String businessName;//商家名称
    private Integer islimited;//是否限时使用1是
    private String startuse;//使用开始时间
    private String enduse;//使用结束时间
    private String agentName;//代理商名称：
    private Integer agentId;//代理商Id：
    private Integer type;//优惠券类型1商城0外卖
    private Integer yiyuangou;// 1一元购等价返券

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getCuserId() {
        return cuserId;
    }

    public void setCuserId(Integer cuserId) {
        this.cuserId = cuserId;
    }

    public String getCuserName() {
        return cuserName;
    }

    public void setCuserName(String cuserName) {
        this.cuserName = cuserName;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public double getFull() {
        return full;
    }

    public void setFull(double full) {
        this.full = full;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getUserd() {
        return userd;
    }

    public void setUserd(Integer userd) {
        this.userd = userd;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getYiyuangou() {
        return yiyuangou;
    }

    public void setYiyuangou(Integer yiyuangou) {
        this.yiyuangou = yiyuangou;
    }
}
