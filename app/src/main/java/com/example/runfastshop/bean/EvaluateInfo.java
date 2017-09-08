package com.example.runfastshop.bean;

import java.math.BigDecimal;

/**
 * 评价信息
 * Created by 天上白玉京 on 2017/8/17.
 */

public class EvaluateInfo {

    public Integer userId;//评论人id

    public String userName;//评论人名称

    public Double score;//打分

    public String shangstr;//点评

    public String content;//评论内容

    public String recontent;//追评

    public BigDecimal cost;//金额

    public String businessName;//商家名称

    public Integer businessId;//商家id

    public Integer goodsSellId;//商品id
    public String goodsSellName;//商品名称
    public String orderCode;//订单号
    public Integer goodsSellRecordId;//订单id

    public String feedback;//回复内容

    public String createTime;//评价时间
    public String delicerTime;//配送时间
    public String orderTime;//下单时间
    public String feedTime;//回复时间
    public String delicerName;//配送人
    public Integer delicerId;//配送人
    public String recreateTime;//追评时间

    public Double delicerScore;//配送员评分
    public String delicerContent;//配送员评价

    public String pic;//用户头像地址

    public  Integer isDeliver; //0平台配送 1商家配送
    public  Integer isrecoment; //0先追评1商家先回复
    public String qishoustr;//骑手点评

    public int evaluateNum;//评价人数
    public String zb;//评价分数


}
