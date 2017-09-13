package com.example.runfastshop.bean.coupon;

import java.util.List;

/**
 * Created by huiliu on 2017/9/13.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class CouponBeans {
    private Integer allrow;
    private Integer totalpage;
    private List<CouponBean> rows;

    public Integer getAllrow() {
        return allrow;
    }

    public void setAllrow(Integer allrow) {
        this.allrow = allrow;
    }

    public Integer getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(Integer totalpage) {
        this.totalpage = totalpage;
    }

    public List<CouponBean> getRows() {
        return rows;
    }

    public void setRows(List<CouponBean> rows) {
        this.rows = rows;
    }
}
