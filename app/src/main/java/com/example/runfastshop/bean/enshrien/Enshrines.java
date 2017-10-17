package com.example.runfastshop.bean.enshrien;

import java.util.List;

/**
 * Created by huiliu on 2017/9/14.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class Enshrines {

    private List<Enshrine> enshrine;

    private Integer totalPage;

    public List<Enshrine> getEnshrine() {
        return enshrine;
    }

    public void setEnshrine(List<Enshrine> enshrine) {
        this.enshrine = enshrine;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
