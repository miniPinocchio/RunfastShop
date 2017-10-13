package cn.shopex.pay.http;

/**
 * Created by p on 2016/12/11.
 */
public class WeiXinPayOutput  {

    private  String  prepayid;
    private String   pay_sign;
    private String   nonce_str;
    private  String   timestamp;
    private String  appid;
    private String   partnerid;
    private String  api_key;
    private String   payment_id;


    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public void setPay_sign(String pay_sign) {
        this.pay_sign = pay_sign;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getPrepayid() {
        return prepayid;
    }

    public String getPay_sign() {
        return pay_sign;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getAppid() {
        return appid;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public String getApi_key() {
        return api_key;
    }

    public String getPayment_id() {
        return payment_id;
    }
}
