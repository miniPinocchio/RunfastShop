package cn.shopex.pay;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.LinkedList;
import java.util.List;

import cn.shopex.pay.http.WeiXinPayOutput;

/**
 * Description :
 * Author : Liun
 * Date   : 2016 16/7/5 15:34.
 * Email  : liun_coolman@foxmail.com
 */

public class WeChatPayHandle extends PlatformPayment{

    private Context context;
    private String paymentid;
    public String wxpaySign;
    StringBuffer sb;
    private static IWXAPI wxapi;
    private PayReq req;
    private static String appId;
    private ClientIsNotInstalledListener notInstalledListener;
    private OnEmptyOrderListener orderListener;
    private OnlineSignEorrListener eorrListener;
    private boolean emptyOrderIsToast = true;

    public WeChatPayHandle(Context context) {
        this.context = context;
        sb = new StringBuffer();
        req = new PayReq();
    }


    /**
     * @param context
     * @param appid   在官网注册生成的appid
     * @return
     */
    public static IWXAPI createWXAPI(Context context, String appid) {
        wxapi = WXAPIFactory.createWXAPI(context, appid);
        appId = appid;
        wxapi.registerApp(appid);
        return wxapi;
    }


    @Override
    public String getPayPlatformName() {
        return "weixin_pay";
    }

    /**
     * 预存款充值：微信支付
     * orderid      订单号
     * total_amount 订单总金额
     * pay_app_id   服务端返回的预支付交易会话ID
     * token        用于发送至服务器的数据签名用
     * method       请求获取服务器prepayid方法名,如:mobileapi.pay.weixinpay
     * url          请求URL前缀 如:http://fenxiao.wyaopeng.com/index.php/api
     *
     * @param o
     */
    @Override
    public void pay(Object o) {
        if (!wxapi.isWXAppInstalled() && wxapi.isWXAppSupportAPI()) {
            Toast.makeText(context, "未安装微信客户端,请安装后再试!", Toast.LENGTH_LONG).show();
            if (notInstalledListener != null) {
                notInstalledListener.onNotInstalledListener();
            }
        }
        WeiXinPayOutput weiXinPayOutput = (WeiXinPayOutput) o;
        String prepayid = weiXinPayOutput.getPrepayid();
        String pay_sign = weiXinPayOutput.getPay_sign();
        String nonce_str = weiXinPayOutput.getNonce_str();
        String timestamp = weiXinPayOutput.getTimestamp();
        String partnerid = weiXinPayOutput.getPartnerid();
        String apikey = weiXinPayOutput.getApi_key();
        paymentid = weiXinPayOutput.getPayment_id();
        if (!TextUtils.isEmpty(prepayid) && !TextUtils.isEmpty(apikey) && !TextUtils.isEmpty(pay_sign) && !TextUtils.isEmpty(nonce_str) && !TextUtils.isEmpty(timestamp) && !TextUtils.isEmpty(partnerid)) {
            // 开始支付
            genPayReq(prepayid, pay_sign, nonce_str, partnerid, timestamp, apikey);// 生成参数
        } else {
            Toast.makeText(context, "订单信息出错了，请重试！", Toast.LENGTH_LONG).show();
            if (eorrListener != null) {
                eorrListener.onlineSignEorr();
            }
            return;
        }
    }


    private void genPayReq(String prepay_id, String sign, String noncestr, String partnerid, String time, String apikey) {

        req.appId = Contants.WEI_XIN_ID;
        req.partnerId = partnerid;
        req.prepayId = prepay_id;
        req.packageValue = "Sign=WXPay";
        req.nonceStr = noncestr;
        req.timeStamp = time;

        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
        signParams.add(new BasicNameValuePair("appid", req.appId));
        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
        signParams.add(new BasicNameValuePair("package", req.packageValue));
        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));

//        req.sign = genAppSign(signParams, apikey);
//        Log.d("devon", "appSign----1111--=" + req.sign);
        req.sign = sign;
//        Log.d("devon", "sign--222222----=" + req.sign);
        wxpaySign = req.sign;

        sb.append("sign\n" + req.sign + "\n\n");
        wxapi.sendReq(req);
    }

    private String genAppSign(List<NameValuePair> params, String apikey) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(apikey);

        Log.d("devon", "sb.toString()------=" + sb.toString());
        this.sb.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = Md5.getMessageDigest(sb.toString().getBytes()).toUpperCase();


        return appSign;
    }

    public String getWxpaySign() {
        return wxpaySign;
    }

    public String getPaymentid() {
        return paymentid;
    }


    //设置不允许订单错误信息的时候toast提示
    public void setEmptyOrderIsToast(boolean emptyOrderIsToast) {
        this.emptyOrderIsToast = emptyOrderIsToast;
    }


    /**客户端未安装监听*/
    /**************************************************************************************/
    public void setOnClientIsNotInstalledListener(ClientIsNotInstalledListener installedListener) {
        notInstalledListener = installedListener;
    }

    public interface ClientIsNotInstalledListener {
        void onNotInstalledListener();
    }
    /**************************************************************************************/


    /**订单信息空*/
    /**************************************************************************************/
    public void setOnEmptyOrderListener(OnEmptyOrderListener emptyOrderListener) {
        orderListener = emptyOrderListener;
    }

    public interface OnEmptyOrderListener {
        void onEmptyOrder();
    }
    /**************************************************************************************/


    /**订单信息空*/
    /**************************************************************************************/
    public void setOnlineSignEorrListener(OnlineSignEorrListener signEorrListener) {
        eorrListener = signEorrListener;
    }

    public interface OnlineSignEorrListener {
        void onlineSignEorr();
    }
    /**************************************************************************************/


}
