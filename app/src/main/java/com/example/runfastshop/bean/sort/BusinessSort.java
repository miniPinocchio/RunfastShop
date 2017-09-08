package com.example.runfastshop.bean.sort;

import com.example.runfastshop.bean.business.BusinessDetail;

import java.util.List;

/**
 * Created by huiliu on 2017/9/7.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class BusinessSort {
    private List<BusinessDetail> bus ;

    private int totalpage;

    public void setBus(List<BusinessDetail> bus){
        this.bus = bus;
    }
    public List<BusinessDetail> getBus(){
        return this.bus;
    }
    public void setTotalpage(int totalpage){
        this.totalpage = totalpage;
    }
    public int getTotalpage(){
        return this.totalpage;
    }

}
