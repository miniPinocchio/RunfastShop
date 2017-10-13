package cn.shopex.pay;

/**
 * Created by p on 2016/7/5.
 */
public class AliPayInfo {
    /**商户PID*/
    private  String partner;

    /**商户收款账号*/
    private String seller;

    /**商户私钥，pkcs8格式*/
    private String rsa_private;

    /**支付结果通知*/
    private String notify_url;

    /**支付的商品*/
    private String subject;

    /**对商品的描述*/
    private String body;

    /**价格*/
    private String price;

    /**订单号*/
    private   String orderInfo;
    public AliPayInfo() {
    }

    public AliPayInfo(String partner, String seller, String rsa_private, String notify_url, String subject, String body, String price,String orderInfo) {
        this.partner = partner;
        this.seller = seller;
        this.rsa_private = rsa_private;
        this.notify_url = notify_url;
        this.subject = subject;
        this.body = body;
        this.price = price;
        this.orderInfo=orderInfo;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setRsa_private(String rsa_private) {
        this.rsa_private = rsa_private;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPartner() {
        return partner;
    }

    public String getSeller() {
        return seller;
    }

    public String getRsa_private() {
        return rsa_private;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getPrice() {
        return price;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getOrderInfo() {
        return orderInfo;
    }

    @Override
    public String toString() {
        return "AliPayInfo{" +
                "partner='" + partner + '\'' +
                ", seller='" + seller + '\'' +
                ", rsa_private='" + rsa_private + '\'' +
                ", notify_url='" + notify_url + '\'' +
                ", subject='" + subject + '\'' +
                ", body='" + body + '\'' +
                ", price='" + price + '\'' +
                ", orderInfo='" + orderInfo + '\'' +
                '}';
    }
}
