package com.example.runfastshop.bean.spend;

import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;

/**
 * Created by huiliu on 2017/9/13.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class AccountRecord implements Parcelable{
    private Integer id;
    private BigDecimal monetary;//金额（正未充值负为消费）
    private BigDecimal balance;//余额
    private String cardnumber;//单号
    private Integer type;//0:消费,1:充值,3:退款,4提现 
    private Integer cid;//用户ID
    private String name;//用户名称
    private String mobile;//电话号码
    private BigDecimal minmonety;//最低提现金额
    private String createTime;//创建时间
    private Integer genreType;// 0用户 1骑手
    private Integer showtype;//0收入1支出
    private BigDecimal beforemonety;//变动前金额
    private String typename;//充值/消费类型

    private String startTime;//查询时使用
    private String endTime;//查询时使用
    private String keyword;//查询时使用

    private BigDecimal showmonetary;//金额（正未充值负为消费）

    private Integer bankid;//银行卡id
    private String banktype;//银行卡名
    private String account;//银行卡号

    public Integer getBankid() {
        return bankid;
    }

    public void setBankid(Integer bankid) {
        this.bankid = bankid;
    }

    public String getBanktype() {
        return banktype;
    }

    public void setBanktype(String banktype) {
        this.banktype = banktype;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getMonetary() {
        return monetary;
    }

    public void setMonetary(BigDecimal monetary) {
        this.monetary = monetary;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getMinmonety() {
        return minmonety;
    }

    public void setMinmonety(BigDecimal minmonety) {
        this.minmonety = minmonety;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getGenreType() {
        return genreType;
    }

    public void setGenreType(Integer genreType) {
        this.genreType = genreType;
    }

    public Integer getShowtype() {
        return showtype;
    }

    public void setShowtype(Integer showtype) {
        this.showtype = showtype;
    }

    public BigDecimal getBeforemonety() {
        return beforemonety;
    }

    public void setBeforemonety(BigDecimal beforemonety) {
        this.beforemonety = beforemonety;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public BigDecimal getShowmonetary() {
        return showmonetary;
    }

    public void setShowmonetary(BigDecimal showmonetary) {
        this.showmonetary = showmonetary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeSerializable(this.monetary);
        dest.writeSerializable(this.balance);
        dest.writeString(this.cardnumber);
        dest.writeValue(this.type);
        dest.writeValue(this.cid);
        dest.writeString(this.name);
        dest.writeString(this.mobile);
        dest.writeSerializable(this.minmonety);
        dest.writeString(this.createTime);
        dest.writeValue(this.genreType);
        dest.writeValue(this.showtype);
        dest.writeSerializable(this.beforemonety);
        dest.writeString(this.typename);
        dest.writeString(this.startTime);
        dest.writeString(this.endTime);
        dest.writeString(this.keyword);
        dest.writeSerializable(this.showmonetary);
    }

    public AccountRecord() {
    }

    protected AccountRecord(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.monetary = (BigDecimal) in.readSerializable();
        this.balance = (BigDecimal) in.readSerializable();
        this.cardnumber = in.readString();
        this.type = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cid = (Integer) in.readValue(Integer.class.getClassLoader());
        this.name = in.readString();
        this.mobile = in.readString();
        this.minmonety = (BigDecimal) in.readSerializable();
        this.createTime = in.readString();
        this.genreType = (Integer) in.readValue(Integer.class.getClassLoader());
        this.showtype = (Integer) in.readValue(Integer.class.getClassLoader());
        this.beforemonety = (BigDecimal) in.readSerializable();
        this.typename = in.readString();
        this.startTime = in.readString();
        this.endTime = in.readString();
        this.keyword = in.readString();
        this.showmonetary = (BigDecimal) in.readSerializable();
    }

    public static final Creator<AccountRecord> CREATOR = new Creator<AccountRecord>() {
        @Override
        public AccountRecord createFromParcel(Parcel source) {
            return new AccountRecord(source);
        }

        @Override
        public AccountRecord[] newArray(int size) {
            return new AccountRecord[size];
        }
    };
}
