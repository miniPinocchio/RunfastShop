package com.example.runfastshop.bean.maintop;

import java.util.List;

/**
 * Created by huiliu on 2017/9/1.
 *
 * @email liu594545591@126.com
 * @introduce 轮播图
 */
public class TopImages {
    private List<TopImage> rows1 ;

    private List<TopImage1> rows2 ;

    private List<TopImage2> rows3 ;

    public List<TopImage> getRows1() {
        return rows1;
    }

    public void setRows1(List<TopImage> rows1) {
        this.rows1 = rows1;
    }

    public List<TopImage1> getRows2() {
        return rows2;
    }

    public void setRows2(List<TopImage1> rows2) {
        this.rows2 = rows2;
    }

    public List<TopImage2> getRows3() {
        return rows3;
    }

    public void setRows3(List<TopImage2> rows3) {
        this.rows3 = rows3;
    }
}
