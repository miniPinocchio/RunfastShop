package com.example.runfastshop.bean;

import java.io.Serializable;

/**
 * 商家活动
 * Created by 天上白玉京 on 2017/8/11.
 */

public class BusinessExercise implements Serializable{

    public int ptype;//活动内容 1满减  2打折3赠品4特价5满减免运费

    public Double fulls;//满多少

    public Double lesss;//减多少

    public String showname;//活动名称

}
