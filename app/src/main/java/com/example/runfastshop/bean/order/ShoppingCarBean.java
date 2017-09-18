package com.example.runfastshop.bean.order;

import java.util.List;

/**
 * Created by huiliu on 2017/9/15.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class ShoppingCarBean {
    private Integer businessId;
    private Integer cid;
    private String cname;
    private List<ShoppingTrolley> list;

    public Integer getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
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

    public List<ShoppingTrolley> getList() {
        return list;
    }

    public void setList(List<ShoppingTrolley> list) {
        this.list = list;
    }
}
