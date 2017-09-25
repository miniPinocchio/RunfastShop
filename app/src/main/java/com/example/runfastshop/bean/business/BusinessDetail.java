package com.example.runfastshop.bean.business;

/**
 * Created by huiliu on 2017/9/6.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class BusinessDetail {
    private Integer id;
    private String name;//商家名称
    private String longitude;//经度
    private String latitude;//纬度
    private String cityId;//城市
    private String cityName;
    private String countyId;//县份
    private String countyName;
    private String townId;//乡镇
    private String townName;
    private String address;//详细地址
    private String mobile;//联系电话
    private Integer levelId;//商家等级
    private Integer sort;//等级排序1-99
    private String  levelName;//等级名称
    private Integer typeId;//商家类型
    private String typestr;//商家类型
    private String content;//商家介绍
    private String saleRange;//销售范围
    private String saleTime;//营业时间段
    private String imgPath;//商家图片logo
    private String mini_imgPath;//商家图片logo缩略图
    private double startPay;//起送价
    private double packing ;//打包费
    private Integer salesnum;//月销量
    private String worktoday; //工作日
    private String startwork;//营业开始时间1
    private String endwork;//营业结束时间 1
    private String startwork2;//营业开始时间第二时间
    private String endwork2;//营业结束时间 第二是时间
    private String saleDayTime;//营业天数（在一周中选择）
    private String createTime;//创建时间
    private Integer recommend; //推荐排序 降序
    private Integer speed;//送餐速度
    private Integer isDeliver;//是否商家自己配送0否1是
    private Integer status;//0营业1暂停 //后台管理员设置
    private String account;//提现账号
    private Integer bank;
    /*提现银行1中国工商银行2中国建设银行3招商银行4中国农业银行5交通银行6中国银行7广发银行8华夏银行
    9兴业银行10光大银行11浦东发展银行12邮储储蓄13北部湾银行14桂林银行15农村合作信用社16柳州银行17徽商银行18民生银行*/
    private String establishbank;//开户行
    private String establishname;//开户名
    private Integer statu;//0营业1休息//商家设置
    private Integer statusx;//商家状态 -1：冻结 其他正常
    private Integer packTime; //打包时间 （分钟）
    private Integer distributionTime;//大约送达时间(分钟)
    private double minmonety;//最低提现金额
    private Integer period;//提现周期
    private double coefficient;//平台佣金系数即每100扣除的费用
    private double busshowps ;//商家配送费
    private String agentName;//代理商名称：
    private Integer agentId;//代理商Id：
    private Integer isopen;//是否营业0营业1休息
    private Integer isCharge;//是否特殊配送商家0否1是
    private double baseCharge;//基础配送费
    private String code;  //编码
    private Integer teamid;//分组ID
    private String teamname;//分组名称
    private Integer visitnum;//访问次数
    private Integer issubsidy;//是否补贴配送费1是0否
    private double subsidy ;//补贴金额
    private String  typeName;//商家类型名称
//    private List<SafetyRecordimg>imgs;
    private Integer sorting;//排序方式 1 推荐排序 2 月销量排序 3 距离排序 4评分排序
    private  double  distance;
    private double showpacking ;//打包费
    private double showps ;//配送费
    private Integer showSalesnum;//月销量
    private double showstartPay;//起送价
    private String showDeliver;//配送时间  客户端显示使用
    private String state;//营业或休息
    private Integer newsHopper;//当前配送情况  0可配送 1无发配送
    private String banktype;//银行类型：银行类型

    private String startTime;
    private String endTime;
    private String maxdistance;//最大配送范围
    private String wordTime;//营业时间
    //商家配送
    private String startTime1;//配送开始时间
    private String endTime1;//配送结束时间
    private String startTime2;//配送开始时间
    private String endTime2;//配送结束时间
    private String keyword;//查询使用
    private String showActity;//商家活动

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

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getTownId() {
        return townId;
    }

    public void setTownId(String townId) {
        this.townId = townId;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getLevelId() {
        return levelId;
    }

    public void setLevelId(Integer levelId) {
        this.levelId = levelId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypestr() {
        return typestr;
    }

    public void setTypestr(String typestr) {
        this.typestr = typestr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSaleRange() {
        return saleRange;
    }

    public void setSaleRange(String saleRange) {
        this.saleRange = saleRange;
    }

    public String getSaleTime() {
        return saleTime;
    }

    public void setSaleTime(String saleTime) {
        this.saleTime = saleTime;
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

    public double getStartPay() {
        return startPay;
    }

    public void setStartPay(double startPay) {
        this.startPay = startPay;
    }

    public double getPacking() {
        return packing;
    }

    public void setPacking(double packing) {
        this.packing = packing;
    }

    public Integer getSalesnum() {
        return salesnum;
    }

    public void setSalesnum(Integer salesnum) {
        this.salesnum = salesnum;
    }

    public String getWorktoday() {
        return worktoday;
    }

    public void setWorktoday(String worktoday) {
        this.worktoday = worktoday;
    }

    public String getStartwork() {
        return startwork;
    }

    public void setStartwork(String startwork) {
        this.startwork = startwork;
    }

    public String getEndwork() {
        return endwork;
    }

    public void setEndwork(String endwork) {
        this.endwork = endwork;
    }

    public String getStartwork2() {
        return startwork2;
    }

    public void setStartwork2(String startwork2) {
        this.startwork2 = startwork2;
    }

    public String getEndwork2() {
        return endwork2;
    }

    public void setEndwork2(String endwork2) {
        this.endwork2 = endwork2;
    }

    public String getSaleDayTime() {
        return saleDayTime;
    }

    public void setSaleDayTime(String saleDayTime) {
        this.saleDayTime = saleDayTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getIsDeliver() {
        return isDeliver;
    }

    public void setIsDeliver(Integer isDeliver) {
        this.isDeliver = isDeliver;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getBank() {
        return bank;
    }

    public void setBank(Integer bank) {
        this.bank = bank;
    }

    public String getEstablishbank() {
        return establishbank;
    }

    public void setEstablishbank(String establishbank) {
        this.establishbank = establishbank;
    }

    public String getEstablishname() {
        return establishname;
    }

    public void setEstablishname(String establishname) {
        this.establishname = establishname;
    }

    public Integer getStatu() {
        return statu;
    }

    public void setStatu(Integer statu) {
        this.statu = statu;
    }

    public Integer getStatusx() {
        return statusx;
    }

    public void setStatusx(Integer statusx) {
        this.statusx = statusx;
    }

    public Integer getPackTime() {
        return packTime;
    }

    public void setPackTime(Integer packTime) {
        this.packTime = packTime;
    }

    public Integer getDistributionTime() {
        return distributionTime;
    }

    public void setDistributionTime(Integer distributionTime) {
        this.distributionTime = distributionTime;
    }

    public double getMinmonety() {
        return minmonety;
    }

    public void setMinmonety(double minmonety) {
        this.minmonety = minmonety;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public double getBusshowps() {
        return busshowps;
    }

    public void setBusshowps(double busshowps) {
        this.busshowps = busshowps;
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

    public Integer getIsopen() {
        return isopen;
    }

    public void setIsopen(Integer isopen) {
        this.isopen = isopen;
    }

    public Integer getIsCharge() {
        return isCharge;
    }

    public void setIsCharge(Integer isCharge) {
        this.isCharge = isCharge;
    }

    public double getBaseCharge() {
        return baseCharge;
    }

    public void setBaseCharge(double baseCharge) {
        this.baseCharge = baseCharge;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getTeamid() {
        return teamid;
    }

    public void setTeamid(Integer teamid) {
        this.teamid = teamid;
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public Integer getVisitnum() {
        return visitnum;
    }

    public void setVisitnum(Integer visitnum) {
        this.visitnum = visitnum;
    }

    public Integer getIssubsidy() {
        return issubsidy;
    }

    public void setIssubsidy(Integer issubsidy) {
        this.issubsidy = issubsidy;
    }

    public double getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(double subsidy) {
        this.subsidy = subsidy;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getSorting() {
        return sorting;
    }

    public void setSorting(Integer sorting) {
        this.sorting = sorting;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getShowpacking() {
        return showpacking;
    }

    public void setShowpacking(double showpacking) {
        this.showpacking = showpacking;
    }

    public double getShowps() {
        return showps;
    }

    public void setShowps(double showps) {
        this.showps = showps;
    }

    public Integer getShowSalesnum() {
        return showSalesnum;
    }

    public void setShowSalesnum(Integer showSalesnum) {
        this.showSalesnum = showSalesnum;
    }

    public double getShowstartPay() {
        return showstartPay;
    }

    public void setShowstartPay(double showstartPay) {
        this.showstartPay = showstartPay;
    }

    public String getShowDeliver() {
        return showDeliver;
    }

    public void setShowDeliver(String showDeliver) {
        this.showDeliver = showDeliver;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getNewsHopper() {
        return newsHopper;
    }

    public void setNewsHopper(Integer newsHopper) {
        this.newsHopper = newsHopper;
    }

    public String getBanktype() {
        return banktype;
    }

    public void setBanktype(String banktype) {
        this.banktype = banktype;
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

    public String getMaxdistance() {
        return maxdistance;
    }

    public void setMaxdistance(String maxdistance) {
        this.maxdistance = maxdistance;
    }

    public String getWordTime() {
        return wordTime;
    }

    public void setWordTime(String wordTime) {
        this.wordTime = wordTime;
    }

    public String getStartTime1() {
        return startTime1;
    }

    public void setStartTime1(String startTime1) {
        this.startTime1 = startTime1;
    }

    public String getEndTime1() {
        return endTime1;
    }

    public void setEndTime1(String endTime1) {
        this.endTime1 = endTime1;
    }

    public String getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(String startTime2) {
        this.startTime2 = startTime2;
    }

    public String getEndTime2() {
        return endTime2;
    }

    public void setEndTime2(String endTime2) {
        this.endTime2 = endTime2;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getShowActity() {
        return showActity;
    }

    public void setShowActity(String showActity) {
        this.showActity = showActity;
    }
}
