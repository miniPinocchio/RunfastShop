package com.example.runfastshop.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huiliu on 2017/9/6.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class OrderInfo implements Parcelable{
    private Integer id;
    private String businessName;//商家名称
    private Integer businessId;//商家id
    private String businessAddr;//商家地址
    private String businessAddressLat;// 商家纬度
    private String businessAddressLng;// 商家经度
    private String goodsSellName;//商品名称
    private Integer goodsSellId;//商品id
    private String businessMobile;//商家电话

    private Integer shopperSign;//1已签到
    private Integer ismute;//-1商家未接单订单自动取消 0商家拒单1代理商确认取消2平台确认取消3商家确认取消
    private Integer userId;//客户id（用户）
    private String userName;//客户名称（用户）
    private String userMobile;//用户电话
    private String userPhone;//下单用户电话
    private String userAddress;//送货地址
    private String address;//门牌号
    private Integer userAddressId;//用户地址ID
    private String userAddressLat;// 配送纬度
    private String userAddressLng;// 配送经度
    private Double distance;// 配送距离

    private String  oldShopper;//原配送员姓名
    private Integer oldShopperId; //原配送员ID
    private String  oldShopperMobile; //原配送员电话

    private String shopper;//配送员姓名
    private Integer shopperId; //配送员ID
    private String shopperMobile; //配送员电话
    private Integer goodsTotal;//商品数量
    private Integer isDeliver;//是否商家自己配送0否1是

    private double shopperMoney;//配送员这个订单所得配送费
    private String orderCode;//订单号
    private String createTime;//下订单时间
    private double commisson; //该笔订单骑手分红
    private Integer status;//订单状态编码-3:商家拒单-1：订单取消  0：客户下单，1：客户已付款  2：商家接单  3：骑手接单   4：商品打包 ，5：商品配送 6：商品送达，7:确认收货 ，8：订单完成
    private String statStr;//订单状态编码 -1：订单取消  0：客户下单，1：客户已付款  2：商家接单  3：骑手接单   4：商品打包 ，5：商品配送 6：商品送达，7:确认收货 ，8：订单完成 

    private String content;//备注
    private double price;//总金额 (商家餐费+配送费+打包费)
    private double yhprice;//优惠金额（优惠券）
    private double disprice;//原价的总金额（商品金额）
    private double packing ;//打包费
    private double showps ;//配送费
    private Integer rid;//红包id
    private Integer range1;//使用范围 1 全部 2指定商家3指定代理商
    private String startString;//送达时间
    private String endString;//送达时间
    private double totalpay;//应付金额
    private Integer iswithdraw;//商家是否已提现 0未提现1已提现
    private double businesspay;//商家部分(商家餐费+配送费(自己配送)+打包费)
    private double businessget;//总佣金
    private double agentget;//平台佣金
    private double agentBusget;//所属代理商佣金
    private double agentBusget2;//上级代理商佣金
    private Integer isfirst;//是否2级代理商下的商家1是0否

    private Integer userDel;//用户是否删除 1：已删除 ，其他正常状态
    private Integer businessDel;//商家是否删除 1：已删除 ，其他正常状态
    private Integer isReceive;//是否收货； 1：已收货， 其他未收货
    private Integer isPay;//是否支付； 1：已支付， 其他未支付
    private Integer isRefund;//是否退款； 1：全额退款，2部分退款 ， 其他未退款
    private Integer refundType;//退款人类型1商家2代理商3平台
    private String refundTime;//退款时间
    private Integer isComent;//是否评价；null：未评价 1：已评价商家， 2：全部评价
    private Integer isCancel;//用户提出取消订单 1:用户提出 2:商家同意取消订单3:不同意取消订单
    private double refund;//退款金额
    private String refundcontext;//拒单原因
    private String cityId;//城市
    private String cityName;
    private String countyId;//县份
    private String countyName;
    private String townId;//乡镇
    private String townName;
    private Double noCharge;//免费配送距离
    private double activityprice;//活动优惠金额
    private Integer  activityId;//活动id
    private String activityname;//活动名称
    private String agentName;//代理商名称：
    private Integer agentId;//代理商Id：
    private Integer distributionTime;//大约送达时间(分钟)
    private String disTime;//大约送达时间
    private String payTime;//支付时间
    private String aceptTime;//商家接单时间
    private Integer  isClearing;//是否可以结算1是（用于订单取消状态）
    private double coefficient;//商家提成系数
    private double acoefficient;//一级代理商提成系数
    private double acoefficient2;//二级代理商提成系数
    private double zjzd;//临时字段（用于upString数据库）
    private Integer ptype;//活动内容 1满减  2打折3赠品4特价5满免运费
    private Integer stype;//是否骑手先接单1是
    private String  couponname;//优惠券名称
    private Integer payType;// 支付类型:0支付宝;1微信;2钱包
    private Integer orderNumber;//商家订单序号
    private Integer isaccpt;//是否收到推送 1是
    private Integer pushType;//推送类型0百度推送1小米推送2华为推送
    private String accptTime;//推送到达时间
    private Integer appOrwx;// 登陆方式0微信1APP
    private String readyTime;//订单完成时间
    private Integer errend;// 是否异常结束1是
    private Integer teamid;//分组ID
    private String teamname;//分组名称
    private Integer isTimeRefund;//是否退款； 1是（重复支付，延时支付退款使用）
    private Integer issubsidy;//是否补贴配送费1是0否
    private double subsidy ;//补贴金额
    private double lessps ;//免除的运费
    private String qrcode;//二维码凭证
    private Integer us;//
    private String startTime;//
    private String endTime;//
    private String showacctString;
    private String logo;
    private String showcode;

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

    public String getBusinessAddr() {
        return businessAddr;
    }

    public void setBusinessAddr(String businessAddr) {
        this.businessAddr = businessAddr;
    }

    public String getBusinessAddressLat() {
        return businessAddressLat;
    }

    public void setBusinessAddressLat(String businessAddressLat) {
        this.businessAddressLat = businessAddressLat;
    }

    public String getBusinessAddressLng() {
        return businessAddressLng;
    }

    public void setBusinessAddressLng(String businessAddressLng) {
        this.businessAddressLng = businessAddressLng;
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

    public String getBusinessMobile() {
        return businessMobile;
    }

    public void setBusinessMobile(String businessMobile) {
        this.businessMobile = businessMobile;
    }

    public Integer getShopperSign() {
        return shopperSign;
    }

    public void setShopperSign(Integer shopperSign) {
        this.shopperSign = shopperSign;
    }

    public Integer getIsmute() {
        return ismute;
    }

    public void setIsmute(Integer ismute) {
        this.ismute = ismute;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(Integer userAddressId) {
        this.userAddressId = userAddressId;
    }

    public String getUserAddressLat() {
        return userAddressLat;
    }

    public void setUserAddressLat(String userAddressLat) {
        this.userAddressLat = userAddressLat;
    }

    public String getUserAddressLng() {
        return userAddressLng;
    }

    public void setUserAddressLng(String userAddressLng) {
        this.userAddressLng = userAddressLng;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getOldShopper() {
        return oldShopper;
    }

    public void setOldShopper(String oldShopper) {
        this.oldShopper = oldShopper;
    }

    public Integer getOldShopperId() {
        return oldShopperId;
    }

    public void setOldShopperId(Integer oldShopperId) {
        this.oldShopperId = oldShopperId;
    }

    public String getOldShopperMobile() {
        return oldShopperMobile;
    }

    public void setOldShopperMobile(String oldShopperMobile) {
        this.oldShopperMobile = oldShopperMobile;
    }

    public String getShopper() {
        return shopper;
    }

    public void setShopper(String shopper) {
        this.shopper = shopper;
    }

    public Integer getShopperId() {
        return shopperId;
    }

    public void setShopperId(Integer shopperId) {
        this.shopperId = shopperId;
    }

    public String getShopperMobile() {
        return shopperMobile;
    }

    public void setShopperMobile(String shopperMobile) {
        this.shopperMobile = shopperMobile;
    }

    public Integer getGoodsTotal() {
        return goodsTotal;
    }

    public void setGoodsTotal(Integer goodsTotal) {
        this.goodsTotal = goodsTotal;
    }

    public Integer getIsDeliver() {
        return isDeliver;
    }

    public void setIsDeliver(Integer isDeliver) {
        this.isDeliver = isDeliver;
    }

    public double getShopperMoney() {
        return shopperMoney;
    }

    public void setShopperMoney(double shopperMoney) {
        this.shopperMoney = shopperMoney;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public double getCommisson() {
        return commisson;
    }

    public void setCommisson(double commisson) {
        this.commisson = commisson;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatStr() {
        return statStr;
    }

    public void setStatStr(String statStr) {
        this.statStr = statStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getYhprice() {
        return yhprice;
    }

    public void setYhprice(double yhprice) {
        this.yhprice = yhprice;
    }

    public double getDisprice() {
        return disprice;
    }

    public void setDisprice(double disprice) {
        this.disprice = disprice;
    }

    public double getPacking() {
        return packing;
    }

    public void setPacking(double packing) {
        this.packing = packing;
    }

    public double getShowps() {
        return showps;
    }

    public void setShowps(double showps) {
        this.showps = showps;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getRange1() {
        return range1;
    }

    public void setRange1(Integer range1) {
        this.range1 = range1;
    }

    public String getStartString() {
        return startString;
    }

    public void setStartString(String startString) {
        this.startString = startString;
    }

    public String getEndString() {
        return endString;
    }

    public void setEndString(String endString) {
        this.endString = endString;
    }

    public double getTotalpay() {
        return totalpay;
    }

    public void setTotalpay(double totalpay) {
        this.totalpay = totalpay;
    }

    public Integer getIswithdraw() {
        return iswithdraw;
    }

    public void setIswithdraw(Integer iswithdraw) {
        this.iswithdraw = iswithdraw;
    }

    public double getBusinesspay() {
        return businesspay;
    }

    public void setBusinesspay(double businesspay) {
        this.businesspay = businesspay;
    }

    public double getBusinessget() {
        return businessget;
    }

    public void setBusinessget(double businessget) {
        this.businessget = businessget;
    }

    public double getAgentget() {
        return agentget;
    }

    public void setAgentget(double agentget) {
        this.agentget = agentget;
    }

    public double getAgentBusget() {
        return agentBusget;
    }

    public void setAgentBusget(double agentBusget) {
        this.agentBusget = agentBusget;
    }

    public double getAgentBusget2() {
        return agentBusget2;
    }

    public void setAgentBusget2(double agentBusget2) {
        this.agentBusget2 = agentBusget2;
    }

    public Integer getIsfirst() {
        return isfirst;
    }

    public void setIsfirst(Integer isfirst) {
        this.isfirst = isfirst;
    }

    public Integer getUserDel() {
        return userDel;
    }

    public void setUserDel(Integer userDel) {
        this.userDel = userDel;
    }

    public Integer getBusinessDel() {
        return businessDel;
    }

    public void setBusinessDel(Integer businessDel) {
        this.businessDel = businessDel;
    }

    public Integer getIsReceive() {
        return isReceive;
    }

    public void setIsReceive(Integer isReceive) {
        this.isReceive = isReceive;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public Integer getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(Integer isRefund) {
        this.isRefund = isRefund;
    }

    public Integer getRefundType() {
        return refundType;
    }

    public void setRefundType(Integer refundType) {
        this.refundType = refundType;
    }

    public String getRefundTime() {
        return refundTime;
    }

    public void setRefundTime(String refundTime) {
        this.refundTime = refundTime;
    }

    public Integer getIsComent() {
        return isComent;
    }

    public void setIsComent(Integer isComent) {
        this.isComent = isComent;
    }

    public Integer getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(Integer isCancel) {
        this.isCancel = isCancel;
    }

    public double getRefund() {
        return refund;
    }

    public void setRefund(double refund) {
        this.refund = refund;
    }

    public String getRefundcontext() {
        return refundcontext;
    }

    public void setRefundcontext(String refundcontext) {
        this.refundcontext = refundcontext;
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

    public Double getNoCharge() {
        return noCharge;
    }

    public void setNoCharge(Double noCharge) {
        this.noCharge = noCharge;
    }

    public double getActivityprice() {
        return activityprice;
    }

    public void setActivityprice(double activityprice) {
        this.activityprice = activityprice;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityname() {
        return activityname;
    }

    public void setActivityname(String activityname) {
        this.activityname = activityname;
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

    public Integer getDistributionTime() {
        return distributionTime;
    }

    public void setDistributionTime(Integer distributionTime) {
        this.distributionTime = distributionTime;
    }

    public String getDisTime() {
        return disTime;
    }

    public void setDisTime(String disTime) {
        this.disTime = disTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getAceptTime() {
        return aceptTime;
    }

    public void setAceptTime(String aceptTime) {
        this.aceptTime = aceptTime;
    }

    public Integer getIsClearing() {
        return isClearing;
    }

    public void setIsClearing(Integer isClearing) {
        this.isClearing = isClearing;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public double getAcoefficient() {
        return acoefficient;
    }

    public void setAcoefficient(double acoefficient) {
        this.acoefficient = acoefficient;
    }

    public double getAcoefficient2() {
        return acoefficient2;
    }

    public void setAcoefficient2(double acoefficient2) {
        this.acoefficient2 = acoefficient2;
    }

    public double getZjzd() {
        return zjzd;
    }

    public void setZjzd(double zjzd) {
        this.zjzd = zjzd;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Integer getStype() {
        return stype;
    }

    public void setStype(Integer stype) {
        this.stype = stype;
    }

    public String getCouponname() {
        return couponname;
    }

    public void setCouponname(String couponname) {
        this.couponname = couponname;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getIsaccpt() {
        return isaccpt;
    }

    public void setIsaccpt(Integer isaccpt) {
        this.isaccpt = isaccpt;
    }

    public Integer getPushType() {
        return pushType;
    }

    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    public String getAccptTime() {
        return accptTime;
    }

    public void setAccptTime(String accptTime) {
        this.accptTime = accptTime;
    }

    public Integer getAppOrwx() {
        return appOrwx;
    }

    public void setAppOrwx(Integer appOrwx) {
        this.appOrwx = appOrwx;
    }

    public String getReadyTime() {
        return readyTime;
    }

    public void setReadyTime(String readyTime) {
        this.readyTime = readyTime;
    }

    public Integer getErrend() {
        return errend;
    }

    public void setErrend(Integer errend) {
        this.errend = errend;
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

    public Integer getIsTimeRefund() {
        return isTimeRefund;
    }

    public void setIsTimeRefund(Integer isTimeRefund) {
        this.isTimeRefund = isTimeRefund;
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

    public double getLessps() {
        return lessps;
    }

    public void setLessps(double lessps) {
        this.lessps = lessps;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public Integer getUs() {
        return us;
    }

    public void setUs(Integer us) {
        this.us = us;
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

    public String getShowacctString() {
        return showacctString;
    }

    public void setShowacctString(String showacctString) {
        this.showacctString = showacctString;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getShowcode() {
        return showcode;
    }

    public void setShowcode(String showcode) {
        this.showcode = showcode;
    }

    @Override
    public String toString() {
        return "OrderInfo{" +
                "id=" + id +
                ", businessName='" + businessName + '\'' +
                ", businessId=" + businessId +
                ", businessAddr='" + businessAddr + '\'' +
                ", businessAddressLat='" + businessAddressLat + '\'' +
                ", businessAddressLng='" + businessAddressLng + '\'' +
                ", goodsSellName='" + goodsSellName + '\'' +
                ", goodsSellId=" + goodsSellId +
                ", businessMobile='" + businessMobile + '\'' +
                ", shopperSign=" + shopperSign +
                ", ismute=" + ismute +
                ", userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userMobile='" + userMobile + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", address='" + address + '\'' +
                ", userAddressId=" + userAddressId +
                ", userAddressLat='" + userAddressLat + '\'' +
                ", userAddressLng='" + userAddressLng + '\'' +
                ", distance=" + distance +
                ", oldShopper='" + oldShopper + '\'' +
                ", oldShopperId=" + oldShopperId +
                ", oldShopperMobile='" + oldShopperMobile + '\'' +
                ", shopper='" + shopper + '\'' +
                ", shopperId=" + shopperId +
                ", shopperMobile='" + shopperMobile + '\'' +
                ", goodsTotal=" + goodsTotal +
                ", isDeliver=" + isDeliver +
                ", shopperMoney=" + shopperMoney +
                ", orderCode='" + orderCode + '\'' +
                ", createTime='" + createTime + '\'' +
                ", commisson=" + commisson +
                ", status=" + status +
                ", statStr='" + statStr + '\'' +
                ", content='" + content + '\'' +
                ", price=" + price +
                ", yhprice=" + yhprice +
                ", disprice=" + disprice +
                ", packing=" + packing +
                ", showps=" + showps +
                ", rid=" + rid +
                ", range1=" + range1 +
                ", startString='" + startString + '\'' +
                ", endString='" + endString + '\'' +
                ", totalpay=" + totalpay +
                ", iswithdraw=" + iswithdraw +
                ", businesspay=" + businesspay +
                ", businessget=" + businessget +
                ", agentget=" + agentget +
                ", agentBusget=" + agentBusget +
                ", agentBusget2=" + agentBusget2 +
                ", isfirst=" + isfirst +
                ", userDel=" + userDel +
                ", businessDel=" + businessDel +
                ", isReceive=" + isReceive +
                ", isPay=" + isPay +
                ", isRefund=" + isRefund +
                ", refundType=" + refundType +
                ", refundTime='" + refundTime + '\'' +
                ", isComent=" + isComent +
                ", isCancel=" + isCancel +
                ", refund=" + refund +
                ", refundcontext='" + refundcontext + '\'' +
                ", cityId='" + cityId + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countyId='" + countyId + '\'' +
                ", countyName='" + countyName + '\'' +
                ", townId='" + townId + '\'' +
                ", townName='" + townName + '\'' +
                ", noCharge=" + noCharge +
                ", activityprice=" + activityprice +
                ", activityId=" + activityId +
                ", activityname='" + activityname + '\'' +
                ", agentName='" + agentName + '\'' +
                ", agentId=" + agentId +
                ", distributionTime=" + distributionTime +
                ", disTime='" + disTime + '\'' +
                ", payTime='" + payTime + '\'' +
                ", aceptTime='" + aceptTime + '\'' +
                ", isClearing=" + isClearing +
                ", coefficient=" + coefficient +
                ", acoefficient=" + acoefficient +
                ", acoefficient2=" + acoefficient2 +
                ", zjzd=" + zjzd +
                ", ptype=" + ptype +
                ", stype=" + stype +
                ", couponname='" + couponname + '\'' +
                ", payType=" + payType +
                ", orderNumber=" + orderNumber +
                ", isaccpt=" + isaccpt +
                ", pushType=" + pushType +
                ", accptTime='" + accptTime + '\'' +
                ", appOrwx=" + appOrwx +
                ", readyTime='" + readyTime + '\'' +
                ", errend=" + errend +
                ", teamid=" + teamid +
                ", teamname='" + teamname + '\'' +
                ", isTimeRefund=" + isTimeRefund +
                ", issubsidy=" + issubsidy +
                ", subsidy=" + subsidy +
                ", lessps=" + lessps +
                ", qrcode='" + qrcode + '\'' +
                ", us=" + us +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", showacctString='" + showacctString + '\'' +
                ", logo='" + logo + '\'' +
                ", showcode='" + showcode + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.businessName);
        dest.writeValue(this.businessId);
        dest.writeString(this.businessAddr);
        dest.writeString(this.businessAddressLat);
        dest.writeString(this.businessAddressLng);
        dest.writeString(this.goodsSellName);
        dest.writeValue(this.goodsSellId);
        dest.writeString(this.businessMobile);
        dest.writeValue(this.shopperSign);
        dest.writeValue(this.ismute);
        dest.writeValue(this.userId);
        dest.writeString(this.userName);
        dest.writeString(this.userMobile);
        dest.writeString(this.userPhone);
        dest.writeString(this.userAddress);
        dest.writeString(this.address);
        dest.writeValue(this.userAddressId);
        dest.writeString(this.userAddressLat);
        dest.writeString(this.userAddressLng);
        dest.writeValue(this.distance);
        dest.writeString(this.oldShopper);
        dest.writeValue(this.oldShopperId);
        dest.writeString(this.oldShopperMobile);
        dest.writeString(this.shopper);
        dest.writeValue(this.shopperId);
        dest.writeString(this.shopperMobile);
        dest.writeValue(this.goodsTotal);
        dest.writeValue(this.isDeliver);
        dest.writeDouble(this.shopperMoney);
        dest.writeString(this.orderCode);
        dest.writeString(this.createTime);
        dest.writeDouble(this.commisson);
        dest.writeValue(this.status);
        dest.writeString(this.statStr);
        dest.writeString(this.content);
        dest.writeDouble(this.price);
        dest.writeDouble(this.yhprice);
        dest.writeDouble(this.disprice);
        dest.writeDouble(this.packing);
        dest.writeDouble(this.showps);
        dest.writeValue(this.rid);
        dest.writeValue(this.range1);
        dest.writeString(this.startString);
        dest.writeString(this.endString);
        dest.writeDouble(this.totalpay);
        dest.writeValue(this.iswithdraw);
        dest.writeDouble(this.businesspay);
        dest.writeDouble(this.businessget);
        dest.writeDouble(this.agentget);
        dest.writeDouble(this.agentBusget);
        dest.writeDouble(this.agentBusget2);
        dest.writeValue(this.isfirst);
        dest.writeValue(this.userDel);
        dest.writeValue(this.businessDel);
        dest.writeValue(this.isReceive);
        dest.writeValue(this.isPay);
        dest.writeValue(this.isRefund);
        dest.writeValue(this.refundType);
        dest.writeString(this.refundTime);
        dest.writeValue(this.isComent);
        dest.writeValue(this.isCancel);
        dest.writeDouble(this.refund);
        dest.writeString(this.refundcontext);
        dest.writeString(this.cityId);
        dest.writeString(this.cityName);
        dest.writeString(this.countyId);
        dest.writeString(this.countyName);
        dest.writeString(this.townId);
        dest.writeString(this.townName);
        dest.writeValue(this.noCharge);
        dest.writeDouble(this.activityprice);
        dest.writeValue(this.activityId);
        dest.writeString(this.activityname);
        dest.writeString(this.agentName);
        dest.writeValue(this.agentId);
        dest.writeValue(this.distributionTime);
        dest.writeString(this.disTime);
        dest.writeString(this.payTime);
        dest.writeString(this.aceptTime);
        dest.writeValue(this.isClearing);
        dest.writeDouble(this.coefficient);
        dest.writeDouble(this.acoefficient);
        dest.writeDouble(this.acoefficient2);
        dest.writeDouble(this.zjzd);
        dest.writeValue(this.ptype);
        dest.writeValue(this.stype);
        dest.writeString(this.couponname);
        dest.writeValue(this.payType);
        dest.writeValue(this.orderNumber);
        dest.writeValue(this.isaccpt);
        dest.writeValue(this.pushType);
        dest.writeString(this.accptTime);
        dest.writeValue(this.appOrwx);
        dest.writeString(this.readyTime);
        dest.writeValue(this.errend);
        dest.writeValue(this.teamid);
        dest.writeString(this.teamname);
        dest.writeValue(this.isTimeRefund);
        dest.writeValue(this.issubsidy);
        dest.writeDouble(this.subsidy);
        dest.writeDouble(this.lessps);
        dest.writeString(this.qrcode);
        dest.writeValue(this.us);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.showacctString);
        dest.writeString(this.logo);
        dest.writeString(this.showcode);
    }

    public OrderInfo() {
    }

    protected OrderInfo(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.businessName = in.readString();
        this.businessId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.businessAddr = in.readString();
        this.businessAddressLat = in.readString();
        this.businessAddressLng = in.readString();
        this.goodsSellName = in.readString();
        this.goodsSellId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.businessMobile = in.readString();
        this.shopperSign = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ismute = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userName = in.readString();
        this.userMobile = in.readString();
        this.userPhone = in.readString();
        this.userAddress = in.readString();
        this.address = in.readString();
        this.userAddressId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userAddressLat = in.readString();
        this.userAddressLng = in.readString();
        this.distance = (Double) in.readValue(Double.class.getClassLoader());
        this.oldShopper = in.readString();
        this.oldShopperId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.oldShopperMobile = in.readString();
        this.shopper = in.readString();
        this.shopperId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.shopperMobile = in.readString();
        this.goodsTotal = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isDeliver = (Integer) in.readValue(Integer.class.getClassLoader());
        this.shopperMoney = in.readDouble();
        this.orderCode = in.readString();
        this.createTime = in.readString();
        this.commisson = in.readDouble();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.statStr = in.readString();
        this.content = in.readString();
        this.price = in.readDouble();
        this.yhprice = in.readDouble();
        this.disprice = in.readDouble();
        this.packing = in.readDouble();
        this.showps = in.readDouble();
        this.rid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.range1 = (Integer) in.readValue(Integer.class.getClassLoader());
        this.startString = in.readString();
        this.endString = in.readString();
        this.totalpay = in.readDouble();
        this.iswithdraw = (Integer) in.readValue(Integer.class.getClassLoader());
        this.businesspay = in.readDouble();
        this.businessget = in.readDouble();
        this.agentget = in.readDouble();
        this.agentBusget = in.readDouble();
        this.agentBusget2 = in.readDouble();
        this.isfirst = (Integer) in.readValue(Integer.class.getClassLoader());
        this.userDel = (Integer) in.readValue(Integer.class.getClassLoader());
        this.businessDel = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isReceive = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isPay = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isRefund = (Integer) in.readValue(Integer.class.getClassLoader());
        this.refundType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.refundTime = in.readString();
        this.isComent = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isCancel = (Integer) in.readValue(Integer.class.getClassLoader());
        this.refund = in.readDouble();
        this.refundcontext = in.readString();
        this.cityId = in.readString();
        this.cityName = in.readString();
        this.countyId = in.readString();
        this.countyName = in.readString();
        this.townId = in.readString();
        this.townName = in.readString();
        this.noCharge = (Double) in.readValue(Double.class.getClassLoader());
        this.activityprice = in.readDouble();
        this.activityId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.activityname = in.readString();
        this.agentName = in.readString();
        this.agentId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.distributionTime = (Integer) in.readValue(Integer.class.getClassLoader());
        this.disTime = in.readString();
        this.payTime = in.readString();
        this.aceptTime = in.readString();
        this.isClearing = (Integer) in.readValue(Integer.class.getClassLoader());
        this.coefficient = in.readDouble();
        this.acoefficient = in.readDouble();
        this.acoefficient2 = in.readDouble();
        this.zjzd = in.readDouble();
        this.ptype = (Integer) in.readValue(Integer.class.getClassLoader());
        this.stype = (Integer) in.readValue(Integer.class.getClassLoader());
        this.couponname = in.readString();
        this.payType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.orderNumber = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isaccpt = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pushType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.accptTime = in.readString();
        this.appOrwx = (Integer) in.readValue(Integer.class.getClassLoader());
        this.readyTime = in.readString();
        this.errend = (Integer) in.readValue(Integer.class.getClassLoader());
        this.teamid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.teamname = in.readString();
        this.isTimeRefund = (Integer) in.readValue(Integer.class.getClassLoader());
        this.issubsidy = (Integer) in.readValue(Integer.class.getClassLoader());
        this.subsidy = in.readDouble();
        this.lessps = in.readDouble();
        this.qrcode = in.readString();
        this.us = (Integer) in.readValue(Integer.class.getClassLoader());
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.showacctString = in.readString();
        this.logo = in.readString();
        this.showcode = in.readString();
    }

    public static final Creator<OrderInfo> CREATOR = new Creator<OrderInfo>() {
        @Override
        public OrderInfo createFromParcel(Parcel source) {
            return new OrderInfo(source);
        }

        @Override
        public OrderInfo[] newArray(int size) {
            return new OrderInfo[size];
        }
    };
}
