package com.example.runfastshop.impl;


import com.example.runfastshop.impl.constant.UrlConstant;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/6/17.
 */
public interface NetInterface {
    //@GET 代表发送的GET请求
    //article/list/text?page=1 请求的路径(并且加上了基地址)
    //Call<T> 代表返回值的类型，ResponseBody 是Retrofit的原生的返回值
    @GET("article/list/text?page=1")
    Call<ResponseBody> getNetData();

    /**
     * 可以通过Retrofit 直接去解析获取到的json 得到的数据可以直接开始使用
     * TestModle 是gson生成的实体类
     *
     * @return
     */
    @GET("article/list/text?page=1")
    Call<ResponseBody> getNetModle();

    /**
     * 如果是get请求的参数 必须是加了@Field("page") 这个注解 括号里面试服务器接收的参数名字
     * 必须和服务器保持统一
     *
     * @param page
     * @return
     */
    @GET("article/list/text?")
    Call<ResponseBody> getNetModleWithParams(@Field("page") String page);

    /**
     * 动态替换地址
     * 需要替换的地址必须要用"{}"包裹起来，然后通过@path 来声明需要替换的参数
     *
     * @param contents
     * @return
     */
    @GET("article/list/{content}?page=1")
    Call<ResponseBody> getNetModleWithPath(@Path("content") String contents);

    @GET("article/{name}/text?")
    Call<ResponseBody> getNetModlePathWithParams(@Path("name") String list,
                                                 @Field("page") String path);

    /**
     * 通过map传递多个参数
     *
     * @param map
     * @return
     */
    @GET("article/list/text?")
    Call<ResponseBody> getNetModleWithMap(@FieldMap Map<String, String> map);

//-----------------------------------------------------------------------------------------------

    /**
     * 获取商家列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.GET_BUSINESS_LIST)
    Call<String> getBusiness(@Field("longitude") String longitude,
                             @Field("latitude") String latitude,
                             @Field("page") Integer page);


    /**
     * 获取附近商家
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.GET_BUSINESS)
    Call<String> getNearbyBusinesses(@Field("longitude") String longitude,
                                     @Field("latitude") String latitude,
                                     @Field("page") Integer page);

    /**
     * 搜索商品
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.SEARCH_GOODS)
    Call<String> searchGoodsType(@Field("name") String name);

    /**
     * 分类查询商家商品
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.SEARCH_GOODS_TYPE)
    Call<String> searchGoods(@Field("page") Integer page,
                             @Field("rows") Integer rows,
                             @Field("longitude") Double longitude,
                             @Field("latitude") Double latitude,
                             @Field("name") String name,
                             @Field("sorting") Integer sort,
                             @Field("agentId") Integer agentId);

    /**
     * 获取商品列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.GET_GOODS_LIST)
    Call<String> getBusinessGoods(@Field("id") Integer id);

    /**
     * 获取商品规格
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.GET_GUIGE)
    Call<String> getGoodsSpec(@Field("id") Integer id);

    /**
     * 获取商品详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.GOODS_DETAIL)
    Call<String> getGoodsDetail(@Field("id") Integer id);

    /**
     * 商家收藏
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.GET_IS_SHOUCANG)
    Call<String> getIsShoucang(@Field("busId") Integer busId,
                               @Field("uId") Integer uId);

    /**
     * 获取商家评价
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.BUSINESS_COMMENT)
    Call<String> getBusinessEvaluate(@Field("id") Integer id,
                                     @Field("page") Integer page);

    /**
     * 获取商家信息
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.BUSINESS_INFO)
    Call<String> getBusinessInfo(@Field("id") Integer id);


    /**
     * 上传地址
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.GET_ADDRESS)
    Call<String> postAddress(@Field("longitude") Double longitude,
                             @Field("latitude") Double latitude);

    /**
     * 获取首页轮播图
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.GET_ADVERT)
    Call<String> getAdvert(@Field("agentId") int agentId);

    /**
     * 获取首页
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.HOME_PAGE)
    Call<String> getHomePage(@Field("noshow") Integer noshow,
                             @Field("agentId") Integer agentId);

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.EDIT_PASSWORD)
    Call<String> updatePassword(@Field("id") Integer id,
                                @Field("old") String old,
                                @Field("password") String password,
                                @Field("codetype") Integer codetype,
                                @Field("code") String code);

    /**
     * 修改密码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.FORGOT_PWD)
    Call<String> updateForgotPwd(@Field("mobile") String mobile,
                                 @Field("xcode") String xcode,
                                 @Field("pwd") String pwd);

    /**
     * 获取修改密码验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.QUERY_EDIT_PWD_CODE)
    Call<String> getEditPwdCode(@Field("id") Integer id);

    /**
     * 获取注册验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.QUERY_AUTH_CODE)
    Call<String> getCode(@Field("mobile") String mobile);

    /**
     * 获取注册验证码
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.FORGET_PASSWORD)
    Call<String> getForgetCode(@Field("mobile") String mobile);

    /**
     * 登录请求
     * 发送post请求的时候，必须要加上@FormUrlEncoded，不然就会报错
     *
     * @param mobile
     * @param password
     * @return
     */

    @FormUrlEncoded
    @POST(UrlConstant.LOGIN)
    Call<String> postLogin(@Field("mobile") String mobile,
                           @Field("password") String password,
                           @Field("bptype") Integer bptype);

    /**
     * 注册请求
     *
     * @param code
     * @return
     */

    @FormUrlEncoded
    @POST(UrlConstant.REGISTER)
    Call<String> postRegister(@Field("mobile") String mobile,
                              @Field("password") String password,
                              @Field("code") String code);

    @FormUrlEncoded
    @POST(UrlConstant.USER_INFO)
    Call<String> postUserInfo();

    /**
     * 用户投诉
     * <p>
     * aram businessId
     *
     * @param email
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.MINE_COMPLAINT)
    Call<String> postMineComplaint(@Field("userEmail") String email,
                                   @Field("content") String content);

    /**
     * 订单投诉
     * <p>
     * aram businessId
     *
     * @param uId
     * @param userEmail
     * @param content
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.ORDER_COMPLAINT)
    Call<String> postOrderComplaint(@Field("uId") Integer uId,
                                    @Field("businessId") Integer businessId,
                                    @Field("goodsSellRecordId") Integer goodsSellRecordId,
                                    @Field("userEmail") String userEmail,
                                    @Field("content") String content);

    /**
     * 合作加盟
     *
     * @param businessName
     * @param mobile
     * @param contacts
     * @param address
     * @param remark
     * @param local
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.JOIN_LEAGUE)
    Call<String> postJoinLeague(@Field("businessName") String businessName,
                                @Field("moblie") String mobile,
                                @Field("contacts") String contacts,
                                @Field("address") String address,
                                @Field("remark") String remark,
                                @Field("local") String local);


    /**
     * 收藏
     *
     * @param shopId
     * @param type   0商品  1商家
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.SAVE_SHOP)
    Call<String> postSaveShop(@Field("uId") Integer uId,
                              @Field("shopId") Integer shopId,
                              @Field("type") Integer type);

    /**
     * 添加收货地址
     *
     * @param uId
     * @param name
     * @param phone
     * @param userAddress
     * @param address
     * @param longitude
     * @param latitude
     * @param provinceName
     * @param cityName
     * @param countyName
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.ADD_USER_ADDRESS)
    Call<String> postAddAddress(@Field("uId") Integer uId,
                                @Field("name") String name,
                                @Field("phone") String phone,
                                @Field("userAddress") String userAddress,
                                @Field("address") String address,
                                @Field("longitude") String longitude,
                                @Field("latitude") String latitude,
                                @Field("provinceName") String provinceName,
                                @Field("cityName") String cityName,
                                @Field("countyName") String countyName);

    /**
     * 修改地址
     *
     * @param id
     * @param uId
     * @param name
     * @param phone
     * @param userAddress
     * @param address
     * @param longitude
     * @param latitude
     * @param provinceName
     * @param cityName
     * @param countyName
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.EDIT_USER_ADDRESS)
    Call<String> postEditAddress(@Field("id") Integer id,
                                 @Field("uId") Integer uId,
                                 @Field("name") String name,
                                 @Field("phone") String phone,
                                 @Field("userAddress") String userAddress,
                                 @Field("address") String address,
                                 @Field("longitude") String longitude,
                                 @Field("latitude") String latitude,
                                 @Field("provinceName") String provinceName,
                                 @Field("cityName") String cityName,
                                 @Field("countyName") String countyName);

    /**
     * 地址详情
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.DETAIL_USER_ADDRESS)
    Call<String> postDetailAddress(@Field("id") Integer id);

    /**
     * 删除地址
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.DELETE_USER_ADDRESS)
    Call<String> postDeleteAddress(@Field("id") Integer id,
                                   @Field("uId") Integer uId);

    /**
     * 地址列表
     *
     * @param uId
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.USER_ADDRESS_LIST)
    Call<String> postListAddress(@Field("uId") Integer uId);

    /**
     * 消息列表
     *
     * @param page
     * @param rows
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.MESSAGE_LIST)
    Call<String> postMessageList(@Field("uId") Integer uId,
                                 @Field("page") Integer page,
                                 @Field("rows") Integer rows);

    /**
     * 订单列表
     *
     * @param page
     * @param rows
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.ORDER_LIST)
    Call<String> postOrderList(@Field("uId") Integer uId,
                               @Field("page") Integer page,
                               @Field("rows") Integer rows);

    /**
     * 修改昵称
     *
     * @param nickname
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.CHANGE_NAME)
    Call<String> postChangeName(@Field("id") Integer id,@Field("nickname") String nickname);

    /**
     * 修改头像
     *
     * @param imgpath
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.QUERY_EDIT_HEAD)
    Call<String> updateHead(@Field("uId") Integer uId,@Field("imgpath") String imgpath);

    /**
     * 绑定邮箱
     *
     * @param email
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.CHANGE_EMAIL)
    Call<String> postChangeEmail(@Field("id") Integer id,@Field("email") String email);

    /**
     * 红包
     *
     * @param uId
     * @param businessId
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.RED_PACKAGE)
    Call<String> postRedPackage(@Field("uId") Integer uId,
                                @Field("businessId") Integer businessId);

    /**
     * 优惠券
     *
     * @param agentId
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.GET_COUPON)
    Call<String> GetCoupan(@Field("businessId") Integer businessId,
                           @Field("agentId") Integer agentId);

    /**
     * 我的优惠券
     *
     * @param type 优惠券类型(1.商城、0.外卖)
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.MY_CONPON)
    Call<String> GetMyCoupan(@Field("uId") Integer uId,@Field("type") Integer type);

    /**
     * 优惠券领取中心
     *
     * @param type 优惠券类型(1.商城、0.外卖)
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.RECEIVE_CONPON)
    Call<String> receiveCoupan(@Field("uId") Integer uId,
                               @Field("agentId") Integer agentId,
                               @Field("type") Integer type);
    /**
     * 领取优惠券
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.GET_CONPON)
    Call<String> getCoupan(@Field("uId") Integer uId,
                               @Field("couponId") Integer couponId);

    /**
     * 我的积分
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.SCORE_DATA)
    Call<String> getScore(@Field("uId") Integer uId);

    /**
     * 积分明细
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.LIST_SCORE)
    Call<String> getListScore(@Field("uId") Integer uId,@Field("page") Integer page,@Field("rows") Integer rows);

    /**
     * 收支明细
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.LIST_CONSUME)
    Call<String> getListConsume(@Field("uId") Integer uId,
                                @Field("page") Integer page,
                                @Field("type") Integer type);

    /**
     * 提现
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.CASH_SEND)
    Call<String> getCashSend(@Field("uId") Integer uId,
                             @Field("monetary") Double monetary,
                             @Field("type") Integer type,
                             @Field("bankid") Integer bankid);
    /**
     * 提现账号
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.BANK_INFO)
    Call<String> getBankUser(@Field("uId") Integer uId,
                             @Field("page") Integer page);

    /**
     * 删除提现账号
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.DELETE_BANK_INFO)
    Call<String> deleteBankUser(@Field("uId") Integer uId,
                             @Field("id") Integer id);

    /**
     * 提现银行卡列表
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.WATHDRAWALL_LIST)
    Call<String> getWathdrawallList(@Field("uId") Integer uId);

    /**
     * 获取银行卡
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.GET_BANK_NAME)
    Call<String> getBankName(@Field("cardNo") String cardNo);

    /**
     * 添加银行卡
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.ADD_BANK)
    Call<String> addBank(@Field("uId") Integer uId,
                         @Field("name") String name,
                         @Field("banktype") String banktype,
                         @Field("account") String account);

    /**
     * 提现记录
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.CASH_RECORD)
    Call<String> getCashRecord(@Field("uId") Integer uId,
                                @Field("page") Integer page);

    /**
     * 订单详情
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.ORDER_DETAIL)
    Call<String> getOrderDetail(@Field("uId") Integer uId,
                                @Field("id") Integer id);

    /**
     * 订单详情
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.ORDER_DETAIL_INFO)
    Call<String> getOrderDetailInfo();

    /**
     * 我的收藏
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.MY_ENSHRINE)
    Call<String> getEnshrine(@Field("uId") Integer id);


    /**
     * 确认订单
     *
     * @param businessId
     * @param userAddressId
     * @param rid
     * @param yhprice
     * @param businesspay
     * @param totalpay
     * @param orderCode
     * @param t
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.CREATE_ORDER)
    Call<String> createOrder(@Field("userId") Integer userId,
                             @Field("businessId") Integer businessId,
                             @Field("userAddressId") Integer userAddressId,
                             @Field("rid") Integer rid,
                             @Field("yhprice") double yhprice,
                             @Field("businesspay") double businesspay,
                             @Field("totalpay") double totalpay,
                             @Field("orderCode") String orderCode,
                             @Field("t") String t);

    /**
     * 添加购物车
     *
     * @param t
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.ORDER_CAR)
    Call<String> OrderCar(@Field("t") String t);

    /**
     * 余额支付
     *
     * @param id
     * @param pass
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.WALLET_PAY)
    Call<String> walletPay(@Field("id") Integer id,
                           @Field("password") String pass);

    /**
     * 微信支付
     *
     * @param id
     * @param type 0-跑腿快车外卖款|1-跑腿快车商城款|2-跑腿快车手机充值款
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.WEIXIN_PAY)
    Call<String> weiXintPay(@Field("id") Integer id,
                            @Field("type") String type);

    /**
     * 微信签名
     *
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.WEIXIN_SIGN)
    Call<String> weiXintSign(@Field("prepayId") String prepayId,
                             @Field("nonceStr") String nonceStr,
                             @Field("timeStamp") String timeStamp,
                             @Field("packageValue") String packageValue);

    /**
     * 支付宝支付
     *
     * @param id
     * @param orderType 1-支付宝付款|2-支付宝充值
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.ALIPAY_PAY)
    Call<String> aliPay(@Field("id") Integer id,
                        @Field("orderType") String orderType);

    /**
     * 确认收货
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.ORDER_RECEIVE)
    Call<String> receiveOrder(@Field("id") Integer id,
                              @Field("uId") Integer uId);

    /**
     * 确认收货
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST(UrlConstant.ORDER_CANCEL)
    Call<String> cancelOrder(@Field("id") Integer id);
}
