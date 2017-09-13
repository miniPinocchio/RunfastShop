package com.example.runfastshop.bean;


public class TypeBean {
	private Integer id;
	private String  businessName;//商家名称
	private Integer businessId;//商家id

	private String name;//分类名称
	private String imgPath;//图片
	private String content;//分类说明

	private String createTime;//创建时间

	private String agentName;//代理商名称：
	private Integer agentId;//代理商Id：


	private Integer sort;//排序

//	private List<GoodsSell> glist;//页面显示使用
	private Integer size;
	private Integer lid;
	private Integer isdz;//是否有打折商品

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public Integer getLid() {
		return lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public Integer getIsdz() {
		return isdz;
	}

	public void setIsdz(Integer isdz) {
		this.isdz = isdz;
	}
}
