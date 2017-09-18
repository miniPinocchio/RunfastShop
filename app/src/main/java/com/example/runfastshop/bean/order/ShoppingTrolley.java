package com.example.runfastshop.bean.order;

import java.math.BigDecimal;

/**
 * Created by huiliu on 2017/9/15.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class ShoppingTrolley {
    private Integer id;

    private Integer cid;//用户ID
    private String cname;//用户名称
    private Integer businessId;//商家id
    private String businessName;//商家名称
    private String barCode;//商品编码
    private String goodsSellName;//商品名称
    private Integer goodsSellId;//商品id
    private Integer goodsSellStandardId;//商品规格ID
    private String goodsSellStandardName;//商品规格名称
    private Integer goodsSellOptionId;//商品选项ID
    private String goodsSellOptionName;//商品选项名称
    private String optionIds;//商品选项ID
    private Integer num;//数量
    private BigDecimal price;//单价
    private String openid;//微信openid
    private BigDecimal packing;//打包费
    private Integer ptype;//是否收取打包费
    private BigDecimal pricedis;//优惠价
    private BigDecimal disprice;//优惠的总金额
    private Integer GoodsSellRecordId;//在买一单标志
    private Integer islimited;//是否限购1是
    private Integer limitNum;//限购数量

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

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getGoodsSellName() {
        return goodsSellName;
    }

    public void setGoodsSellName(String goodsSellName) {
        this.goodsSellName = goodsSellName;
    }

    public Integer getGoodsSellId() {
        return goodsSellId;
    }

    public void setGoodsSellId(Integer goodsSellId) {
        this.goodsSellId = goodsSellId;
    }

    public Integer getGoodsSellStandardId() {
        return goodsSellStandardId;
    }

    public void setGoodsSellStandardId(Integer goodsSellStandardId) {
        this.goodsSellStandardId = goodsSellStandardId;
    }

    public String getGoodsSellStandardName() {
        return goodsSellStandardName;
    }

    public void setGoodsSellStandardName(String goodsSellStandardName) {
        this.goodsSellStandardName = goodsSellStandardName;
    }

    public Integer getGoodsSellOptionId() {
        return goodsSellOptionId;
    }

    public void setGoodsSellOptionId(Integer goodsSellOptionId) {
        this.goodsSellOptionId = goodsSellOptionId;
    }

    public String getGoodsSellOptionName() {
        return goodsSellOptionName;
    }

    public void setGoodsSellOptionName(String goodsSellOptionName) {
        this.goodsSellOptionName = goodsSellOptionName;
    }

    public String getOptionIds() {
        return optionIds;
    }

    public void setOptionIds(String optionIds) {
        this.optionIds = optionIds;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public BigDecimal getPacking() {
        return packing;
    }

    public void setPacking(BigDecimal packing) {
        this.packing = packing;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public BigDecimal getPricedis() {
        return pricedis;
    }

    public void setPricedis(BigDecimal pricedis) {
        this.pricedis = pricedis;
    }

    public BigDecimal getDisprice() {
        return disprice;
    }

    public void setDisprice(BigDecimal disprice) {
        this.disprice = disprice;
    }

    public Integer getGoodsSellRecordId() {
        return GoodsSellRecordId;
    }

    public void setGoodsSellRecordId(Integer goodsSellRecordId) {
        GoodsSellRecordId = goodsSellRecordId;
    }

    public Integer getIslimited() {
        return islimited;
    }

    public void setIslimited(Integer islimited) {
        this.islimited = islimited;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }
}
