package com.example.runfastshop.bean.mainmiddle;

import java.util.List;

/**
 * Created by huiliu on 2017/9/5.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class MiddleBreakfasts {
    private List<MiddleBreakfast> bus ;

    private int totalpage;

    public void setBus(List<MiddleBreakfast> bus){
        this.bus = bus;
    }
    public List<MiddleBreakfast> getBus(){
        return this.bus;
    }
    public void setTotalpage(int totalpage){
        this.totalpage = totalpage;
    }
    public int getTotalpage(){
        return this.totalpage;
    }
}
