package com.example.runfastshop.bean.order;

import java.util.List;

/**
 * Created by huiliu on 2017/9/14.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class OrderDetail {

    /**
     * istel : 2
     * cusmob : 0775-2995588
     * goodsSellRecord : {"id":527432,"businessName":"阿蒙羊庄","businessId":180,"businessAddr":"桂平大乐购二楼","businessAddressLat":"23.388727","businessAddressLng":"110.075504","goodsSellName":"瓦煲羊骨汤58元/煲","goodsSellId":5298,"businessMobile":"3375009","shopperSign":null,"ismute":null,"userId":471551,"userName":"你好","userMobile":"13277777777","userPhone":"15871720785","userAddress":"湖北省武汉市洪山区梨园街道武铁佳苑(才林东路)","address":"13","userAddressId":145322,"userAddressLat":"30.589062","userAddressLng":"114.353473","distance":906699,"oldShopper":null,"oldShopperId":null,"oldShopperMobile":null,"shopper":null,"shopperId":null,"shopperMobile":null,"goodsTotal":1,"isDeliver":0,"shopperMoney":3,"orderCode":"w201709260001","createTime":"2017-09-26 11:30:56","commisson":null,"status":-1,"statStr":"订单已取消","content":null,"price":63,"yhprice":0,"disprice":null,"packing":0,"showps":5,"rid":0,"range1":null,"startDate":null,"endDate":null,"totalpay":63,"iswithdraw":0,"businesspay":58,"businessget":8.7,"agentget":1.74,"agentBusget":6.96,"agentBusget2":0,"isfirst":0,"userDel":null,"businessDel":null,"isReceive":null,"isPay":null,"isRefund":null,"refundType":null,"refundTime":null,"isComent":null,"isCancel":null,"refund":null,"refundcontext":null,"cityId":"gg","cityName":"贵港","countyId":"","countyName":"贵港","townId":"","townName":null,"noCharge":null,"activityprice":0,"activityId":null,"activityname":null,"agentName":"桂平代理","agentId":4,"distributionTime":null,"disTime":null,"payTime":null,"aceptTime":null,"isClearing":null,"coefficient":15,"acoefficient":3,"acoefficient2":0,"zjzd":null,"ptype":null,"stype":null,"couponname":null,"payType":null,"orderNumber":1,"isaccpt":null,"pushType":null,"accptTime":null,"appOrwx":1,"readyTime":null,"errend":null,"teamid":0,"teamname":null,"isTimeRefund":null,"issubsidy":null,"subsidy":null,"lessps":null,"qrcode":"/runfast/toQRcode/w201709260001","us":null,"startTime":null,"endTime":null,"goodsSellRecordChildren":null,"showacctdate":null,"logo":"/upload/_mini1490066146561.png","goodsSellOutStatus":null,"showcode":"1"}
     */

    public int istel;
    public String cusmob;
    public GoodsSellRecordBean goodsSellRecord;
    public List<GoodsSellRecordChildren> goodsSellRecordChildren;

    public static class GoodsSellRecordBean {
        /**
         * id : 527432
         * businessName : 阿蒙羊庄
         * businessId : 180
         * businessAddr : 桂平大乐购二楼
         * businessAddressLat : 23.388727
         * businessAddressLng : 110.075504
         * goodsSellName : 瓦煲羊骨汤58元/煲
         * goodsSellId : 5298
         * businessMobile : 3375009
         * shopperSign : null
         * ismute : null
         * userId : 471551
         * userName : 你好
         * userMobile : 13277777777
         * userPhone : 15871720785
         * userAddress : 湖北省武汉市洪山区梨园街道武铁佳苑(才林东路)
         * address : 13
         * userAddressId : 145322
         * userAddressLat : 30.589062
         * userAddressLng : 114.353473
         * distance : 906699.0
         * oldShopper : null
         * oldShopperId : null
         * oldShopperMobile : null
         * shopper : null
         * shopperId : null
         * shopperMobile : null
         * goodsTotal : 1
         * isDeliver : 0
         * shopperMoney : 3.0
         * orderCode : w201709260001
         * createTime : 2017-09-26 11:30:56
         * commisson : null
         * status : -1
         * statStr : 订单已取消
         * content : null
         * price : 63.0
         * yhprice : 0
         * disprice : null
         * packing : 0.0
         * showps : 5.0
         * rid : 0
         * range1 : null
         * startDate : null
         * endDate : null
         * totalpay : 63.0
         * iswithdraw : 0
         * businesspay : 58.0
         * businessget : 8.7
         * agentget : 1.74
         * agentBusget : 6.96
         * agentBusget2 : 0.0
         * isfirst : 0
         * userDel : null
         * businessDel : null
         * isReceive : null
         * isPay : null
         * isRefund : null
         * refundType : null
         * refundTime : null
         * isComent : null
         * isCancel : null
         * refund : null
         * refundcontext : null
         * cityId : gg
         * cityName : 贵港
         * countyId :
         * countyName : 贵港
         * townId :
         * townName : null
         * noCharge : null
         * activityprice : 0.0
         * activityId : null
         * activityname : null
         * agentName : 桂平代理
         * agentId : 4
         * distributionTime : null
         * disTime : null
         * payTime : null
         * aceptTime : null
         * isClearing : null
         * coefficient : 15.0
         * acoefficient : 3.0
         * acoefficient2 : 0.0
         * zjzd : null
         * ptype : null
         * stype : null
         * couponname : null
         * payType : null
         * orderNumber : 1
         * isaccpt : null
         * pushType : null
         * accptTime : null
         * appOrwx : 1
         * readyTime : null
         * errend : null
         * teamid : 0
         * teamname : null
         * isTimeRefund : null
         * issubsidy : null
         * subsidy : null
         * lessps : null
         * qrcode : /runfast/toQRcode/w201709260001
         * us : null
         * startTime : null
         * endTime : null
         * goodsSellRecordChildren : null
         * showacctdate : null
         * logo : /upload/_mini1490066146561.png
         * goodsSellOutStatus : null
         * showcode : 1
         */

        public int id;
        public String businessName;
        public int businessId;
        public String businessAddr;
        public String businessAddressLat;
        public String businessAddressLng;
        public String goodsSellName;
        public int goodsSellId;
        public String businessMobile;
        public Object shopperSign;
        public Object ismute;
        public int userId;
        public String userName;
        public String userMobile;
        public String userPhone;
        public String userAddress;
        public String address;
        public int userAddressId;
        public String userAddressLat;
        public String userAddressLng;
        public String distance;
        public Object oldShopper;
        public Object oldShopperId;
        public Object oldShopperMobile;
        public Object shopper;
        public Object shopperId;
        public Object shopperMobile;
        public int goodsTotal;
        public int isDeliver;
        public double shopperMoney;
        public String orderCode;
        public String createTime;
        public Object commisson;
        public int status;
        public String statStr;
        public String content;
        public double price;
        public int yhprice;
        public Object disprice;
        public double packing;
        public double showps;
        public int rid;
        public Object range1;
        public Object startDate;
        public Object endDate;
        public double totalpay;
        public int iswithdraw;
        public double businesspay;
        public double businessget;
        public double agentget;
        public double agentBusget;
        public double agentBusget2;
        public int isfirst;
        public Object userDel;
        public Object businessDel;
        public Object isReceive;
        public int isPay;
        public Object isRefund;
        public Object refundType;
        public Object refundTime;
        public Object isComent;
        public Object isCancel;
        public Object refund;
        public Object refundcontext;
        public String cityId;
        public String cityName;
        public String countyId;
        public String countyName;
        public String townId;
        public Object townName;
        public Object noCharge;
        public double activityprice;
        public Object activityId;
        public Object activityname;
        public String agentName;
        public int agentId;
        public Object distributionTime;
        public Object disTime;
        public Object payTime;
        public Object aceptTime;
        public Object isClearing;
        public double coefficient;
        public double acoefficient;
        public double acoefficient2;
        public Object zjzd;
        public Object ptype;
        public Object stype;
        public Object couponname;
        public int payType;
        public int orderNumber;
        public Object isaccpt;
        public Object pushType;
        public Object accptTime;
        public int appOrwx;
        public String readyTime;
        public Object errend;
        public int teamid;
        public Object teamname;
        public Object isTimeRefund;
        public Object issubsidy;
        public Object subsidy;
        public Object lessps;
        public String qrcode;
        public Object us;
        public String startTime;
        public String endTime;
        public Object showacctdate;
        public String logo;
        public Object goodsSellOutStatus;
        public String showcode;

    }
}