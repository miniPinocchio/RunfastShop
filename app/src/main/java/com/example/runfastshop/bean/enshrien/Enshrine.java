package com.example.runfastshop.bean.enshrien;

/**
 * Created by huiliu on 2017/9/14.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class Enshrine {

    public Integer id;
    public Integer cid;//用户ID
    public Integer type;//收藏类型0商品1商家
    public Integer shopId;//收藏对象ID
    public String name;//用户名称
    public String mobile;//用户电话号码
    public String shopname;//商品名称 //商店名称
    public String openid;//微信openid
    public String createTime;//添加时间
    public String imgPath;//图片
    public double startPay; //起送价

    public String startTime;//查询时使用
    public String endTime;//查询时使用
    public String keyword;//查询时使用
}
