package com.example.supportv1.bean;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

/**
 * 请求报文返回结果类
 *
 * @date 2014年7月9日
 **/
public class ResultJson {
    /*
     * 返回结果 0成功，其它为失败
     */
    @SerializedName("result")
    public int result = Integer.MIN_VALUE;
    /*
     * 返回结果描述
     */
    @SerializedName("resultNote")
    public String resultNote = "";
    /*
     * 返回结果明细
     */
    @SerializedName("detail")
    public JSONObject detail;
    /*
     * 总记录数
     */
    @SerializedName("totalRecordNum")
    public int totalRecordNum;
    /*
     * 页码即第几页
     */
    @SerializedName("pageNo")
    public int pageNo;
    /*
     * 总页数
     */
    @SerializedName("pages")
    public int pages;
    /*
     * 整个响应报文
     */
    @SerializedName("jsonroot")
    public String jsonroot;
}
