package cn.shopex.pay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import cn.shopex.pay.client.PayResult;
import cn.shopex.pay.client.SignUtils;


/**
 * Created by p on 2016/7/4.
 */
public class AliPayHandle {

    /**
     * 商户PID
     */
    public static String PARTNER = "";

    /**
     * 商户收款账号
     */
    public static String SELLER = "";

    /**
     * 商户私钥，pkcs8格式
     */
    public static String RSA_PRIVATE = "";

    /**
     * 支付宝公钥
     */
    public static final String RSA_PUBLIC = "";

    public static final int SDK_PAY_FLAG = 1;

    private Activity context;

    private AliPayInfo aliPayInfo;
    //支付结果通知
    private String NOTIFY_URL;
    //回调监听
    private PayResultListen payResultListen;


    private boolean isStat = true;

    //用来处理接口回调
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {

                        if (isStat) {
                            Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();
                        }
                        //如果没有设置回调监听那么就不回调
                        if (payResultListen != null) {
                            payResultListen.paymentSuccess(resultStatus, payResult);
                        }
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            if (isStat) {
                                Toast.makeText(context, "支付结果确认中", Toast.LENGTH_SHORT).show();

                            }

                            if (payResultListen != null) {
                                payResultListen.paymentConfirmation(resultStatus, payResult);
                            }
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            if (isStat || payResultListen != null) {
                                Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();

                            }

                            if (payResultListen != null) {
                                payResultListen.PaymentFailure(resultStatus, payResult);
                            }
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    //设置支付结果后是否Toast显示结果状态
    public void setPayResulIsToast(boolean isState) {
        isStat = isState;
    }

    //支付宝回调结果
    interface PayResultListen {
        /**
         * 支付成功
         */
        void paymentSuccess(String resultStatus, PayResult payResult);

        /**
         * 支付确认中
         */
        void paymentConfirmation(String resultStatus, PayResult payResult);

        /**
         * 支付失败
         */
        void PaymentFailure(String resultStatus, PayResult payResult);
    }

    /**
     * 设置支付回调监听
     */
    public void setPayResultListen(PayResultListen payResultListen) {
        this.payResultListen = payResultListen;
    }

    /**
     * 配置信息直接从外界获得，以及商品，商品描述，商品价格，订单号
     */
    //构造方法
    public AliPayHandle(Activity context, AliPayInfo aliPayInfo) throws APliPaySetingInfoNullException {
        this.context = context;
        this.aliPayInfo = aliPayInfo;
        if (aliPayInfo == null) {
            Log.i("aliPayInfo", aliPayInfo.toString() + "+++++++++++++++++++");
            throw new APliPaySetingInfoNullException("对象AliPayInfo为null，没有设值，请检查");
        }
        Log.i("aliPayInfo", aliPayInfo.toString() + "+++++++++++++++++++");
        PARTNER = aliPayInfo.getPartner();
        SELLER = aliPayInfo.getSeller();
        RSA_PRIVATE = aliPayInfo.getRsa_private();
        NOTIFY_URL = aliPayInfo.getNotify_url();
        pay(aliPayInfo.getSubject(), aliPayInfo.getBody(), aliPayInfo.getPrice());
    }

    public AliPayHandle(Activity context) {
        this.context = context;
    }

    //自定义异常
    public class APliPaySetingInfoNullException extends Exception {
        public APliPaySetingInfoNullException(String msg) {
            super(msg);
        }
    }


    /**
     * call alipay sdk pay. 调用SDK支付
     * subject  支付的商品
     * body     对商品的描述
     * price     价格
     */
    public void pay(String subject, String body, String price) throws APliPaySetingInfoNullException {
        /**
         * PARTNER 商户PID;
         * RSA_PRIVATE  商户私钥;
         * pkcs8格式;
         * SELLER  商户收款账号
         * 公钥是可以不配置的
         * */
        if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE) || TextUtils.isEmpty(SELLER)) {
            throw new APliPaySetingInfoNullException("配置信息有误，请检查PARTNER or RSA_PRIVATE or SELLER" + PARTNER + RSA_PRIVATE + SELLER);
        }

        //订单信息
        String orderInfo = getOrderInfo(subject, body, price);


        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         *
         * 订单加密  对订单做RSA 签名
         */
        String sign = sign(orderInfo);
        try {
            /**
             * 仅需对sign 做URL编码
             */
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        final String payInfo = orderInfo + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(context);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }


    /**
     * get the sdk version. 获取SDK版本号
     */
    public void getSDKVersion() {
        PayTask payTask = new PayTask(context);
        String version = payTask.getVersion();
        Toast.makeText(context, version, Toast.LENGTH_SHORT).show();
    }


    /**
     * create the order info. 创建订单信息
     * subject  商品名称
     * body     商品详情
     * price    价格
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + aliPayInfo.getOrderInfo() + "\"";
        //orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        /**支付完成后，支付宝会给我们两个通知一个我们可以直接获得，也就是result，另外一个就是回调返回给我们的服务器*/
        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + NOTIFY_URL + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }


    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }


    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    public void alipay(final String orderInfo) throws APliPaySetingInfoNullException {

        if (orderInfo == null) {
            Log.i("aliPayInfo", orderInfo.toString() + "+++++++++++++++++++");
            throw new APliPaySetingInfoNullException("对象AliPayInfo为null，没有设值，请检查");
        }

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask payTask = new PayTask(context);
                String result = payTask.pay(orderInfo, true);
                // 调用支付接口，获取支付结果

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                handler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
}
