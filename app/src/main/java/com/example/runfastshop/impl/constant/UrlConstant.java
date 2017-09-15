package com.example.runfastshop.impl.constant;


import com.example.runfastshop.BuildConfig;

/**
 * 常量
 *
 * @author Sun.bl
 * @version [1.0, 2016/6/23]
 */
public class UrlConstant {

    public static final String BaseUrl = BuildConfig.BASE_URL;

    public static final String ImageBaseUrl = "http://www.gxptkc.com";

//    public static final String BaseUrl = "http://115.28.39.61:28080/wanglu/";

//    public static final String BaseUrl = "http://api.wanglu114.com/wanglu/";

//    public static final String BaseUrl = "http://saasapi.wanglu114.com:19081/wanglu/";

    /**
     * 初始化
     */
    public static final String INIT = BuildConfig.BASE_URL + "init";

    /***
     * 上传文件
     */
    public static final String UPLOAD_FILE = "uploadfile";

    /**
     * 检查更新
     */
    public static final String QUERY_APP_UPDATE = "getLatestVersion";

    /**
     * 获取首页轮播图
     */
    public static final String GET_ADVERT = "getAdvert.do";

    /**
     * 登录
     */
    public static final String LOGIN = "login.do";

    /**
     * 注册
     */
    public static final String REGISTER = "register.do";

    /**
     * 个人中心
     */
    public static final String USER_INFO = "user/index.do";

    /**
     * 找回密码
     */
    public static final String FORGET_PASSWORD = "findpwd.do";

    /**
     * 首页
     */
    public static final String INDEX = "index.do";

    /**
     * 主页
     */
    public static final String HOME_PAGE = "getHomepage.do";

    /**
     * 商家列表
     */
    public static final String GET_BUSINESS_LIST = "getBusiness.do";

    /**
     * 设置当前位置页
     */
    public static final String SET_CURRENT_ADDRESS = "address/setAddress.do";

    /***
     * 获取注册验证码
     */
    public static final String QUERY_AUTH_CODE = "getSmsCode.do";

    /***
     * 获取修改密码验证码
     */
    public static final String QUERY_EDIT_PWD_CODE = "user/getcode.do";

    /***
     *  商家列表
     */
    public static final String GET_BUSINESS = "getBusiness.do";
    /***
     *  分类商家
     */
    public static final String BUSINESS_LIST = "business/list.do";
    /***
     * 优惠券
     */
    public static final String GET_COUPON = "getCoupon.do";

    /***
     * 商家商品列表
     */
    public static final String GET_GOODS_LIST = "business/getGoods.do";

    /**
     * 地图定位上传
     */
    public static final String GET_ADDRESS = "getaddress.do";


    /**
     * 商品详情
     */
    public static final String GOODS_DETAIL = "business/deail.do";

    /***
     * 商家评价
     */
    public static final String BUSINESS_COMMENT = "business/businessComment.do";

    /***
     * 商家信息
     */
    public static final String BUSINESS_INFO = "business/showBusiness.do";

    /**
     * 商品搜索
     */
    public static final String SEARCH_GOODS = "search/lists.do";

    /**
     * 商品分类搜索
     */
    public static final String SEARCH_GOODS_TYPE = "search/list.do";

    /**
     * 忘记密码
     */
    public static final String FORGOT_PWD =  "sendpwd.do";
    /**
     * 修改密码
     */
    public static final String EDIT_PASSWORD =  "user/editPassword.do";


    /***
     * 获取规格数据
     */
    public static final String GET_GUIGE = "business/getguige.do";

    /***
     * 未读新闻数
     */
    public static final String UN_READ_NEWS = "unReadNews";

    /**
     * 反馈
     */
    public static final String ADD_FEEDBACK = "getAlarmInfo";
    /**
     * 二维码
     */
    public static final String APP_DOWNLOAD = "AppDownload";
    /**
     * 通讯录
     */
    public static final String GET_MAIL_LIST = "getMailList";

    /**
     * 历史
     */
    public static final String GET_HISTORY_ALARMS = "historyAlarms";

    /**
     * 通讯录
     */
    public static final String SAVE_POSITION = "savePosition";

    /**
     * 工区坐标点
     */
    public static final String GET_PROJECT_POINTS = "getProjectPoints";

    /**
     * 工友坐标点
     */
    public static final String GET_ORTHER_USER_POSITIONS = "getEmployeePositions";

    /**
     * 文件上传路径
     */
    public static final String KMZ_UPLOAD = "KMZ_upload";

    /**
     * 获取KMZ路径
     */
    public static final String GET_KMZ_PATH = "getKMZList";
    /**
     * 用户投诉
     */
    public static final String MINE_COMPLAINT = "userComplain/add.do";
    /**
     * 加盟合作
     */
    public static final String JOIN_LEAGUE = "user/join.do";
    /**
     * 收藏商家/商品
     */
    public static final String SAVE_SHOP = "shopping/saveenshrine.do";
    /**
     * 收货地址列表
     */
    public static final String USER_ADDRESS_LIST = "cuserAddress/index.do";
    /**
     * 添加收货地址
     */
    public static final String ADD_USER_ADDRESS = "cuserAddress/postadd.do";
    /**
     * 修改收货地址
     */
    public static final String EDIT_USER_ADDRESS = "cuserAddress/edit.do";
    /**
     * 收货地址详情
     */
    public static final String DETAIL_USER_ADDRESS = "cuserAddress/deail.do";
    /**
     * 删除收货地址
     */
    public static final String DELETE_USER_ADDRESS = "cuserAddress/delete.do";
    /**
     * 消息列表
     */
    public static final String MESSAGE_LIST = "messge/getdate.do";
    /**
     * 订单列表
     */
    public static final String ORDER_LIST = "userOrder/getPageBean.do";
    /**
     * 修改昵称
     */
    public static final String CHANGE_NAME = "user/editNickname.do";
    /**
     * 绑定邮箱
     */
    public static final String CHANGE_EMAIL = "user/editEmail.do";
    /**
     * 获取红包
     */
    public static final String RED_PACKAGE = "pay/getConpon.do";
    /**
     * 我的优惠券
     */
    public static final String MY_CONPON = "coupon/mycoupon.do";
    /**
     * 积分明细
     */
    public static final String LIST_SCORE = "userScore/getdate.do";
    /**
     * 收支明细
     */
    public static final String LIST_CONSUME = "wallet/record.do";
    /**
     * 订单详情
     */
    public static final String ORDER_DETAIL = "userOrder/getGoodsSellRecord.do";
    /**
     * 我的收藏
     */
    public static final String MY_ENSHRINE = "myenshrine/index.do";
    /**
     * 生成订单
     */
    public static final String CREATE_ORDER = "pay/addGoodsSellRecord.do";
  /**
     * 添加购物车
     */
    public static final String ORDER_CAR = "shopping/addShopping.do";

}
