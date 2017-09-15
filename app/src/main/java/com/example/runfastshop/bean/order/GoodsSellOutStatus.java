package com.example.runfastshop.bean.order;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huiliu on 2017/9/14.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class GoodsSellOutStatus implements Parcelable{
    private Integer id;
    private String businessName;//商家名称
    private Integer businessId;//商家id

    private Integer goodsSellRecordId;//商品订单id
    private String goodsSellRecordName;//商品订单名称
    private String goodsSellRecordCode;//商品订单编码
    private Integer sort;//订单状态编码 -1：订单取消  0：客户下单，1：客户已付款  2：商家接单  3：骑手接单   4：商品打包 ，5：商品配送 6：商品送达，7:确认收货 ，8：订单完成，11:骑手到达
    private String statStr;
    private Integer type;//操作人类型 0：后台管理员，1：商家  ，2:快递员  ，3：用户,4:代理商
    private Integer operationId;//操作人id
    private String operationName;//操作人名称

    private String createTime;//生成时间

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

    public Integer getGoodsSellRecordId() {
        return goodsSellRecordId;
    }

    public void setGoodsSellRecordId(Integer goodsSellRecordId) {
        this.goodsSellRecordId = goodsSellRecordId;
    }

    public String getGoodsSellRecordName() {
        return goodsSellRecordName;
    }

    public void setGoodsSellRecordName(String goodsSellRecordName) {
        this.goodsSellRecordName = goodsSellRecordName;
    }

    public String getGoodsSellRecordCode() {
        return goodsSellRecordCode;
    }

    public void setGoodsSellRecordCode(String goodsSellRecordCode) {
        this.goodsSellRecordCode = goodsSellRecordCode;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getStatStr() {
        return statStr;
    }

    public void setStatStr(String statStr) {
        this.statStr = statStr;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getOperationId() {
        return operationId;
    }

    public void setOperationId(Integer operationId) {
        this.operationId = operationId;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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
        dest.writeValue(this.goodsSellRecordId);
        dest.writeString(this.goodsSellRecordName);
        dest.writeString(this.goodsSellRecordCode);
        dest.writeValue(this.sort);
        dest.writeString(this.statStr);
        dest.writeValue(this.type);
        dest.writeValue(this.operationId);
        dest.writeString(this.operationName);
        dest.writeString(this.createTime);
    }

    public GoodsSellOutStatus() {
    }

    protected GoodsSellOutStatus(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.businessName = in.readString();
        this.businessId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.goodsSellRecordId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.goodsSellRecordName = in.readString();
        this.goodsSellRecordCode = in.readString();
        this.sort = (Integer) in.readValue(Integer.class.getClassLoader());
        this.statStr = in.readString();
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.operationId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.operationName = in.readString();
        this.createTime = in.readString();
    }

    public static final Creator<GoodsSellOutStatus> CREATOR = new Creator<GoodsSellOutStatus>() {
        @Override
        public GoodsSellOutStatus createFromParcel(Parcel source) {
            return new GoodsSellOutStatus(source);
        }

        @Override
        public GoodsSellOutStatus[] newArray(int size) {
            return new GoodsSellOutStatus[size];
        }
    };
}
