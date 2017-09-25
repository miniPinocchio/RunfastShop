package com.example.runfastshop.bean.gooddetail;

import java.util.List;

/**
 * Created by huiliu on 2017/9/19.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class GoodsSellOption {
    /**
     * 外卖商品选项
     */
    private Integer id;
    private Integer businessId;//商家id
    private String businessName;//商家名称
    private String barCode;//商品编码
    private String goodsSellName;//商品名称
    private Integer goodsSellId;//商品id
    private String name;//名称

    private Integer del;
    private List<GoodsSellSubOption> subOption;;
}
