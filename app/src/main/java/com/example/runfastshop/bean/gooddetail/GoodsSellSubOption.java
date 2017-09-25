package com.example.runfastshop.bean.gooddetail;

/**
 * Created by huiliu on 2017/9/19.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class GoodsSellSubOption {
    /**
     * 外卖商品选项----子选项
     */
    private Integer id;
    private String name;
    private String barCode;//商品编码
    private String goodsSellName;//商品名称
    private Integer goodsSellId;//商品id
    private Integer sort;//排序

    private Integer optionId;//上选项id
    private String optionName;//上选项名称
    private String businessName;//商家名称
    private Integer businessId;//商家id
    private Integer del;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
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
}
