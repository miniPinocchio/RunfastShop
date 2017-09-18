package com.example.runfastshop.bean.order;

/**
 * Created by huiliu on 2017/9/16.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class OrderCodeInfo {
    private boolean success;

    private String orderCode;

    private int id;//订单号

    private OrderInfo goodsSellRecord;

    private String msg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderInfo getGoodsSellRecord() {
        return goodsSellRecord;
    }

    public void setGoodsSellRecord(OrderInfo goodsSellRecord) {
        this.goodsSellRecord = goodsSellRecord;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
