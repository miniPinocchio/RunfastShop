package com.example.runfastshop.bean.gooddetail;

import java.math.BigDecimal;

/**
 * Created by huiliu on 2017/9/19.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class GoodsSellStandard {
    /**
     * 外卖商品规格
     */
    private Integer id;
    private String name;//规格名称
    private BigDecimal discount;//优惠价
    private BigDecimal price;//售价
    private Integer goodsSellId;//所属商品
    private String proCode;//条形码
    private String barCode;//商品编码
    private String businessName;//商家名称
    private Integer businessId;//商家id
    private Integer del;
    private String keyword;
    private Integer istj;//是否特价

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

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getGoodsSellId() {
        return goodsSellId;
    }

    public void setGoodsSellId(Integer goodsSellId) {
        this.goodsSellId = goodsSellId;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    public Integer getDel() {
        return del;
    }

    public void setDel(Integer del) {
        this.del = del;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getIstj() {
        return istj;
    }

    public void setIstj(Integer istj) {
        this.istj = istj;
    }
}
