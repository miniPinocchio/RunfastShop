package com.example.runfastshop.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 商家信息
 * Created by 天上白玉京 on 2017/8/10.
 */

public class BusinessInfo implements Serializable{

    public int id;//商家id

    public String mini_imgPath;//商家图片logo缩略图

    public String name;//商家名称

    public String imgPath;//商家图片logo

    public int isopen;//是否营业 0营业 1休息

    public int agentId;//代理商Id

    public Double distance;//距离

    public Double subsidy;//补贴金额

    public int isDeliver;//是否商家自己配送 0否 1是

    public String cityId;//城市

    public int salesnum;//月销量

    public String speed;//送餐速度

    public int levelId;//商家等级

    public Double startPay;//起送价

    public Double busshowps;//商家配送费

    public Double baseCharge;//基础配送费

    public List<BusinessExercise> alist;



}
