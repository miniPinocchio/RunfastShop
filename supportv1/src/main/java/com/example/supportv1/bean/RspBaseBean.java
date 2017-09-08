package com.example.supportv1.bean;

import com.google.gson.annotations.SerializedName;

/**
 * 响应报文对象
 *
 * @date 2014年7月9日
 **/
public class RspBaseBean {
    /*
     * 命令字
     */
    @SerializedName("cmd")
    public String cmd;
    /*
     * 返回结果 0成功，其它为失败
     */
    @SerializedName("result")
    public int result;

    /*
     * 返回结果 true 成功，其它为失败
     */
    @SerializedName("success")
    public boolean success;
    /*
     * 返回结果描述
     */
    @SerializedName("resultNote")
    public String resultNote;
    /*
     * 总页数
     */
    @SerializedName("pages")
    public int pages;
    /*
     * 页码即第几页
     */
    @SerializedName("pageNo")
    public int pageNo;
    /*
     * 总记录数
     */
    @SerializedName("totalRecordNum")
    public int totalRecordNum;

    /*
     * 返回结果明细
     */
    @SerializedName("detailJsonString")
    public String detailJsonString;
}
