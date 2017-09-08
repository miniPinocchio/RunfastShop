package com.example.runfastshop.bean;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class FoodBean implements Serializable{

	private int id;
	private String name;//名
	private String sale;//销量
	private String isCommand;//是否推荐
	private BigDecimal price;//价格
	private String cut;//打折
	private String type;//类
	private String icon;//图片

	private Integer isonly;//规格 0：没有，1：有规格
	private long selectCount;
	private String showprice;//活动

	private String goodsSpec;
	private String goodsType;
	private String goodsTypeTwo;

	public String getGoodsSpec() {
		return goodsSpec;
	}

	public void setGoodsSpec(String goodsSpec) {
		this.goodsSpec = goodsSpec;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getGoodsTypeTwo() {
		return goodsTypeTwo;
	}

	public void setGoodsTypeTwo(String goodsTypeTwo) {
		this.goodsTypeTwo = goodsTypeTwo;
	}

	public Integer getIsonly() {
		return isonly;
	}

	public void setIsonly(Integer isonly) {
		this.isonly = isonly;
	}

	public String getShowprice() {
		return showprice;
	}

	public void setShowprice(String showprice) {
		this.showprice = showprice;
	}

	public long getSelectCount() {
		return selectCount;
	}

	public void setSelectCount(long selectCount) {
		this.selectCount = selectCount;
	}

	public String getIcon() {
		return icon;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSale() {
		return sale;
	}

	public void setSale(String sale) {
		this.sale = sale;
	}

	public String getIsCommand() {
		return isCommand;
	}

	public void setIsCommand(String isCommand) {
		this.isCommand = isCommand;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getCut() {
		return cut;
	}

	public void setCut(String cut) {
		this.cut = cut;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
