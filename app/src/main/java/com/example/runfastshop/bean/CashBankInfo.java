package com.example.runfastshop.bean;

import java.io.Serializable;

/**
 * Created by 天上白玉京 on 2017/10/16.
 */

public class CashBankInfo implements Serializable{

    /**
     * id : 2
     * userName :
     * userMobile : 15978220307
     * userId : 488993
     * type : 3
     * account : 6228485029051191977
     * banktype : 中国农业银行
     * name : 邓美娜
     * createbank :
     * createTime : 2016-10-31 10:12:42
     */

    private Integer id;
    private String userName;
    private String userMobile;
    private Integer userId;
    private Integer type;
    private String account;
    private String banktype;
    private String name;
    private String createbank;
    private String createTime;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBanktype() {
        return banktype;
    }

    public void setBanktype(String banktype) {
        this.banktype = banktype;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatebank() {
        return createbank;
    }

    public void setCreatebank(String createbank) {
        this.createbank = createbank;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
