package com.example.runfastshop.bean.mainmiddle;

import java.util.List;

/**
 * Created by huiliu on 2017/9/1.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class MiddleSorts {
    private List<TopHome> TopHome ;

    private List<MiddleSort> rows ;

    public void setTopHome(List<TopHome> TopHome){
        this.TopHome = TopHome;
    }
    public List<TopHome> getTopHome(){
        return this.TopHome;
    }
    public void setRows(List<MiddleSort> rows){
        this.rows = rows;
    }
    public List<MiddleSort> getRows(){
        return this.rows;
    }

}
