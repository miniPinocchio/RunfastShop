package com.example.runfastshop.bean.message;

import java.util.List;

/**
 * Created by huiliu on 2017/9/5.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class MessageInfos {

    private int totalPage;

    private List<MessageInfo> messge ;

    public void setTotalPage(int totalPage){
        this.totalPage = totalPage;
    }
    public int getTotalPage(){
        return this.totalPage;
    }
    public void setMessge(List<MessageInfo> messge){
        this.messge = messge;
    }
    public List<MessageInfo> getMessge(){
        return this.messge;
    }

}
