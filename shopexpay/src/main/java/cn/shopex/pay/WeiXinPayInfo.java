package cn.shopex.pay;

/**
 * Description :
 * Author : Liun
 * Date   : 2016 16/7/10 17:23.
 * Email  : liun_coolman@foxmail.com
 */
public class WeiXinPayInfo {

    /**
     * @param orderid      订单号
     * @param total_amount 订单总金额
     * @param pay_app_id   服务端返回的预支付交易会话ID
     * @param token        应用token  用于发送至服务器的数据签名用
     * @param method       请求获取服务器prepayid方法名,如:mobileapi.pay.weixinpay
     * @param url          请求URL前缀 如:http://fenxiao.wyaopeng.com/index.php/api
     * @return
     */

    private String orderid;
    private String total_amount;
    private String pay_app_id;
    private String token;
    private String method;
    private String url;

    public WeiXinPayInfo() {
    }

    public WeiXinPayInfo(String orderid, String total_amount, String pay_app_id, String token, String method, String url) {
        this.orderid = orderid;
        this.total_amount = total_amount;
        this.pay_app_id = pay_app_id;
        this.token = token;
        this.method = method;
        this.url = url;
    }

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getPay_app_id() {
        return pay_app_id;
    }

    public void setPay_app_id(String pay_app_id) {
        this.pay_app_id = pay_app_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "WeiXinPayInfo{" +
                "orderid='" + orderid + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", pay_app_id='" + pay_app_id + '\'' +
                ", token='" + token + '\'' +
                ", method='" + method + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
