package com.example.runfastshop.bean.order;

import java.util.List;

/**
 * Created by huiliu on 2017/9/6.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class OrderInfos {

    private int totalPage;
    private List<OrderInfo> rows ;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<OrderInfo> getRows() {
        return rows;
    }

    public void setRows(List<OrderInfo> rows) {
        this.rows = rows;
    }
}
