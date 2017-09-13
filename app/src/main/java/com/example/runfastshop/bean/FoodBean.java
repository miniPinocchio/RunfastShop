package com.example.runfastshop.bean;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class FoodBean implements Serializable{

	private String sale;//销量
	private String isCommand;//是否推荐
	private String cut;//打折
	private String type;//类
	private String icon;//图片

	private long selectCount;

	private String goodsSpec;
	private String goodsType;
	private String goodsTypeTwo;

	private Integer id;
	private String businessName;//商家名称
	private Integer businessId;//商家id

	private String name;//商品名称
	private String imgPath;//原图
	private String mini_imgPath;//压缩图
	private BigDecimal price;//价格

	private String content;//商品描述
	private Integer typeId;//商品分类
	private String typeName;//商品分类
	private Integer sellTypeId;//商品类型（店内）
	private String  sellTypeName;//商品类型（店内）
	private Integer status;//状态 0正常销售 1：暂停销售 2：商品下架
	private String createTime;//
	private Integer star;//商品星级
	private Integer salesnum;//月销量
	private Integer num;//数量
	private Integer ptype;//是否收取打包费0是1否

	private String agentName;//代理商名称：
	private Integer agentId;//代理商Id：

	private Integer islimited;//是否限购1是 0否
	private Integer limittype;//超出后是否允许原价购买0否 1是
	private Integer limitNum;//限购数量
	private String limiStartTime;//限购开始时间
	private String limiEndTime;//限购结束时间
	private BigDecimal discount;//优惠价
	private BigDecimal disprice;//优惠金额
//	private List<GoodsSellOption> optionList;//商品选项
//	private List<GoodsSellStandard> standardList;//商品规格
	private Integer shownum;//数量
	private Integer lid;
	private List<Integer> goodsellids; //商品id集合
	private Integer sid;//单规格 规格ID


	private String showprice;//活动名称

	private Integer isonly;//是否单规格单选项1否0是

//	private List<GoodsSellSubOption> opsubList;//商品选项

	
	private String showzs;//赠送活动
	private Integer isdz;//是否有打折商品
	private String showlimit;//限购



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

	public void setId(Integer id) {
		this.id = id;
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

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

	public String getMini_imgPath() {
		return mini_imgPath;
	}

	public void setMini_imgPath(String mini_imgPath) {
		this.mini_imgPath = mini_imgPath;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public Integer getSellTypeId() {
		return sellTypeId;
	}

	public void setSellTypeId(Integer sellTypeId) {
		this.sellTypeId = sellTypeId;
	}

	public String getSellTypeName() {
		return sellTypeName;
	}

	public void setSellTypeName(String sellTypeName) {
		this.sellTypeName = sellTypeName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public Integer getSalesnum() {
		return salesnum;
	}

	public void setSalesnum(Integer salesnum) {
		this.salesnum = salesnum;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getPtype() {
		return ptype;
	}

	public void setPtype(Integer ptype) {
		this.ptype = ptype;
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

	public Integer getLimittype() {
		return limittype;
	}

	public void setLimittype(Integer limittype) {
		this.limittype = limittype;
	}

	public Integer getLimitNum() {
		return limitNum;
	}

	public void setLimitNum(Integer limitNum) {
		this.limitNum = limitNum;
	}

	public String getLimiStartTime() {
		return limiStartTime;
	}

	public void setLimiStartTime(String limiStartTime) {
		this.limiStartTime = limiStartTime;
	}

	public String getLimiEndTime() {
		return limiEndTime;
	}

	public void setLimiEndTime(String limiEndTime) {
		this.limiEndTime = limiEndTime;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getDisprice() {
		return disprice;
	}

	public void setDisprice(BigDecimal disprice) {
		this.disprice = disprice;
	}

	public Integer getShownum() {
		return shownum;
	}

	public void setShownum(Integer shownum) {
		this.shownum = shownum;
	}

	public Integer getLid() {
		return lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public List<Integer> getGoodsellids() {
		return goodsellids;
	}

	public void setGoodsellids(List<Integer> goodsellids) {
		this.goodsellids = goodsellids;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getShowzs() {
		return showzs;
	}

	public void setShowzs(String showzs) {
		this.showzs = showzs;
	}

	public Integer getIsdz() {
		return isdz;
	}

	public void setIsdz(Integer isdz) {
		this.isdz = isdz;
	}

	public String getShowlimit() {
		return showlimit;
	}

	public void setShowlimit(String showlimit) {
		this.showlimit = showlimit;
	}
}
