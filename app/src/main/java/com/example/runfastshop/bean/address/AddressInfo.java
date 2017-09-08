package com.example.runfastshop.bean.address;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huiliu on 2017/9/6.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class AddressInfo implements Parcelable{
    private Integer id;
    private Integer cid;//用户ID
    private String cname;//用户名称
    private String mobile;//电话号码
    private String name;//收货人名称
    private String phone;//收货人电话号码
    private String userAddress;//地址
    private String address;//详细地址
    private Integer isChoose;//是否默认地址，1：默认地址 其他不是
    private String latitude;// 纬度
    private String longitude;// 经度
    private String createTime;//注册时间
    private String provinceName;//省
    private String cityName;//市
    private String countyName;//县
    private String startTime;
    private String endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public Integer getIsChoose() {
        return isChoose;
    }

    public void setIsChoose(Integer isChoose) {
        this.isChoose = isChoose;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.cid);
        dest.writeString(this.cname);
        dest.writeString(this.mobile);
        dest.writeString(this.name);
        dest.writeString(this.phone);
        dest.writeString(this.userAddress);
        dest.writeString(this.address);
        dest.writeValue(this.isChoose);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.createTime);
        dest.writeString(this.provinceName);
        dest.writeString(this.cityName);
        dest.writeString(this.countyName);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
    }

    public AddressInfo() {
    }

    protected AddressInfo(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cname = in.readString();
        this.mobile = in.readString();
        this.name = in.readString();
        this.phone = in.readString();
        this.userAddress = in.readString();
        this.address = in.readString();
        this.isChoose = (Integer) in.readValue(Integer.class.getClassLoader());
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.createTime = in.readString();
        this.provinceName = in.readString();
        this.cityName = in.readString();
        this.countyName = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
    }

    public static final Creator<AddressInfo> CREATOR = new Creator<AddressInfo>() {
        @Override
        public AddressInfo createFromParcel(Parcel source) {
            return new AddressInfo(source);
        }

        @Override
        public AddressInfo[] newArray(int size) {
            return new AddressInfo[size];
        }
    };
}
