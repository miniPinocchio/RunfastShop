package cn.shopex.pay;

/**
 * Created by p on 2016/12/11.
 */
public abstract  class PlatformPayment {

    abstract public String getPayPlatformName();
    abstract public void pay(Object  o) throws AliPayHandle.APliPaySetingInfoNullException;
}
