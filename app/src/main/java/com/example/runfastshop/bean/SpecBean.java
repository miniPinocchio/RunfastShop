package com.example.runfastshop.bean;

import java.math.BigDecimal;

/**
 * Created by 天上白玉京 on 2017/8/22.
 */

public class SpecBean {

    /**
     * id : 331522
     * name : 二两
     * discount : 6.3
     * price : 7
     * goodsSellId : 161789
     * businessName : 御膳香（桂林米粉）
     * businessId : 3447
     */

    private Integer id;//规格id
    private String name;
    private BigDecimal discount;
    private BigDecimal price;
    private Integer goodsSellId;//商品id

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

    @Override
    public String toString() {
        return "SpecBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discount=" + discount +
                ", price=" + price +
                ", goodsSellId=" + goodsSellId +
                '}';
    }
}
