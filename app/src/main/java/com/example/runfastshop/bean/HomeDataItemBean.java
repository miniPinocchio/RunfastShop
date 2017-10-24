package com.example.runfastshop.bean;

import com.example.runfastshop.bean.mainmiddle.MiddleSort;
import com.example.runfastshop.bean.maintop.TopImage;
import com.example.runfastshop.bean.maintop.TopImage1;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 天上白玉京 on 2017/10/24.
 */

public class HomeDataItemBean {

    public List<TopImage> imgurl= new ArrayList<>();//轮播图地址

    public List<MiddleSort> middleurl= new ArrayList<>();//中部模块数据

    public List<TopImage1> imgurl1= new ArrayList<>();//轮播图地址

    public BusinessInfo businessInfos;

}
