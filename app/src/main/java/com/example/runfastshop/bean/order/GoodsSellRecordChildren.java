package com.example.runfastshop.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huiliu on 2017/9/14.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class GoodsSellRecordChildren implements Parcelable{

    /**
     * 外卖商品订单记录子类
     */
    private static final long serialVersionUID = -5528726128932465873L;

    private Integer id;
    private Integer pid;//父ID---订单id
    private Integer businessId;//商家id
    private String businessName;//商家名称
    private String goodsSellName;//商品名称
    private Integer goodsSellId;//商品id
    private Integer num;//数量
    private BigDecimal price;//原价 
    private BigDecimal disprice;//优惠价
    private BigDecimal totalprice;//总金额 
    private String orderCode;//订单号
    private Integer goodsSellStandardId;//商品规格ID
    private String goodsSellStandardName;//商品规格名称
    private Integer goodsSellOptionId;//商品选项ID
    private String goodsSellOptionName;//商品选项名称
    private String optionIds;//商品选项ID(多项以逗号隔开)
    private Integer userId;//用户id
    private Integer status;//订单状态
    private String createTime;
    private Integer activity;//是否参与活动1是
    private Integer activityId;//活动Id
    private String activityName;//活动名称
    private String goods;//赠送的商品：
    private Integer activityType;//活动类型 1满减  2打折3赠品4特价
    private Integer ptype;//是否收取打包费0是1否
    private Integer errend;// 是否异常结束1是
    private String startTime;//
    private String endTime;//
    private String showname;
    private String showactivity;
    private String showoption;
    private BigDecimal yhprice;//优惠总金额
    private List<Integer> optsubids; //商品id集合

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public BigDecimal getDisprice() {
        return disprice;
    }

    public void setDisprice(BigDecimal disprice) {
        this.disprice = disprice;
    }

    public BigDecimal getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(BigDecimal totalprice) {
        this.totalprice = totalprice;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Integer getPtype() {
        return ptype;
    }

    public void setPtype(Integer ptype) {
        this.ptype = ptype;
    }

    public Integer getErrend() {
        return errend;
    }

    public void setErrend(Integer errend) {
        this.errend = errend;
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

    public String getShowname() {
        return showname;
    }

    public void setShowname(String showname) {
        this.showname = showname;
    }

    public String getShowactivity() {
        return showactivity;
    }

    public void setShowactivity(String showactivity) {
        this.showactivity = showactivity;
    }

    public String getShowoption() {
        return showoption;
    }

    public void setShowoption(String showoption) {
        this.showoption = showoption;
    }

    public BigDecimal getYhprice() {
        return yhprice;
    }

    public void setYhprice(BigDecimal yhprice) {
        this.yhprice = yhprice;
    }

    public List<Integer> getOptsubids() {
        return optsubids;
    }

    public void setOptsubids(List<Integer> optsubids) {
        this.optsubids = optsubids;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.pid);
        dest.writeValue(this.businessId);
        dest.writeString(this.businessName);
        dest.writeString(this.goodsSellName);
        dest.writeValue(this.goodsSellId);
        dest.writeValue(this.num);
        dest.writeSerializable(this.price);
        dest.writeSerializable(this.disprice);
        dest.writeSerializable(this.totalprice);
        dest.writeString(this.orderCode);
        dest.writeValue(this.goodsSellStandardId);
        dest.writeString(this.goodsSellStandardName);
        dest.writeValue(this.goodsSellOptionId);
        dest.writeString(this.goodsSellOptionName);
        dest.writeString(this.optionIds);
        dest.writeValue(this.userId);
        dest.writeValue(this.status);
        dest.writeString(this.createTime);
        dest.writeValue(this.activity);
        dest.writeValue(this.activityId);
        dest.writeString(this.activityName);
        dest.writeString(this.goods);
        dest.writeValue(this.activityType);
        dest.writeValue(this.ptype);
        dest.writeValue(this.errend);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.showname);
        dest.writeString(this.showactivity);
        dest.writeString(this.showoption);
        dest.writeSerializable(this.yhprice);
        dest.writeList(this.optsubids);
    }

    public GoodsSellRecordChildren() {
    }

    protected GoodsSellRecordChildren(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.pid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.businessId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.businessName = in.readString();
        this.goodsSellName = in.readString();
        this.goodsSellId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.num = (Integer) in.readValue(Integer.class.getClassLoader());
        this.price = (BigDecimal) in.readSerializable();
        this.disprice = (BigDecimal) in.readSerializable();
        this.totalprice = (BigDecimal) in.readSerializable();
        this.orderCode = in.readString();
        this.goodsSellStandardId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.goodsSellStandardName = in.readString();
        this.goodsSellOptionId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.goodsSellOptionName = in.readString();
        this.optionIds = in.readString();
        this.userId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createTime = in.readString();
        this.activity = (Integer) in.readValue(Integer.class.getClassLoader());
        this.activityId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.activityName = in.readString();
        this.goods = in.readString();
        this.activityType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.ptype = (Integer) in.readValue(Integer.class.getClassLoader());
        this.errend = (Integer) in.readValue(Integer.class.getClassLoader());
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.showname = in.readString();
        this.showactivity = in.readString();
        this.showoption = in.readString();
        this.yhprice = (BigDecimal) in.readSerializable();
        this.optsubids = new ArrayList<Integer>();
        in.readList(this.optsubids, Integer.class.getClassLoader());
    }

    public static final Creator<GoodsSellRecordChildren> CREATOR = new Creator<GoodsSellRecordChildren>() {
        @Override
        public GoodsSellRecordChildren createFromParcel(Parcel source) {
            return new GoodsSellRecordChildren(source);
        }

        @Override
        public GoodsSellRecordChildren[] newArray(int size) {
            return new GoodsSellRecordChildren[size];
        }
    };
}
