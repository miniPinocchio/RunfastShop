package com.example.runfastshop.activity.ordercenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.WeiXinPayBean;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;
import com.example.runfastshop.util.MD5Util;
import com.example.supportv1.utils.LogUtil;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.shopex.pay.AliPayHandle;
import cn.shopex.pay.AliPayInfo;
import cn.shopex.pay.Contants;
import cn.shopex.pay.Md5;
import cn.shopex.pay.WeChatPayHandle;
import cn.shopex.pay.WeiXinPayInfo;
import cn.shopex.pay.client.PayResult;
import cn.shopex.pay.http.WeiXinPayOutput;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayChannelActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.cb_weixin_pay)
    CheckBox mCbWeixinPay;
    @BindView(R.id.cb_wallet_pay)
    CheckBox mCbWalletPay;
    @BindView(R.id.cb_ali_pay)
    CheckBox mCbAliPay;
    @BindView(R.id.btn_to_pay)
    Button mBtnToPay;
    @BindView(R.id.rl_wallet_pay)
    RelativeLayout rlWalletPay;
    @BindView(R.id.rl_weixin_pay)
    RelativeLayout rlWeixinPay;
    @BindView(R.id.rl_ali_pay)
    RelativeLayout rlAliPay;

    private int payType = 0;//0钱包 1微信 2支付宝
    private int mOrderId;
    private double mPrice;
    private User userInfo;
    private IWXAPI wxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_channel);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        wxapi = WeChatPayHandle.createWXAPI(this, Contants.WEI_XIN_ID);
        userInfo = UserService.getUserInfo(this);
        mOrderId = getIntent().getIntExtra("orderId", 0);
        mPrice = getIntent().getDoubleExtra("price", 0.0);
        mBtnToPay.setText("确认支付 ¥ " + mPrice);
    }

    private void requestAliPay() {
        CustomApplication.getRetrofit().aliPay(mOrderId, "1").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    String orderInfo = jsonObject.optString("orderInfo");
                    alipay(orderInfo);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void requestWeiXinPay() {
        CustomApplication.getRetrofit().weiXintPay(mOrderId, "0").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String data = response.body();
                WeiXinPayBean weiXinPayBean = GsonUtil.fromJson(data, WeiXinPayBean.class);
                if (weiXinPayBean.success) {
                    requestSign(weiXinPayBean);
//                    wechatpay(weiXinPayBean);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void requestSign(final WeiXinPayBean weiXinPayBean) {
        CustomApplication.getRetrofit().weiXintSign(weiXinPayBean.prepay_id, weiXinPayBean.map.nonceStr, weiXinPayBean.map.timeStamp, "Sign=WXPay").enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String body = response.body();
                try {
                    JSONObject jsonObject = new JSONObject(body);
                    String sign = jsonObject.optString("sign");
                    wechatpay(weiXinPayBean, sign);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            ResolveData(data);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        CustomToast.INSTANCE.showToast(this, "网络异常");
    }

    /**
     * 解析数据
     *
     * @param data
     */
    private void ResolveData(String data) {
        try {
            JSONObject object = new JSONObject(data);
            CustomToast.INSTANCE.showToast(this, object.optString("msg"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @OnClick({R.id.rl_wallet_pay, R.id.rl_weixin_pay, R.id.rl_ali_pay, R.id.btn_to_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_wallet_pay:
                payType = 0;
                mCbWalletPay.setBackgroundResource(R.drawable.pay_type_check);
                mCbWeixinPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                mCbAliPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                break;
            case R.id.rl_weixin_pay:
                payType = 1;
                mCbWeixinPay.setBackgroundResource(R.drawable.pay_type_check);
                mCbWalletPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                mCbAliPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                break;
            case R.id.rl_ali_pay:
                payType = 2;
                mCbAliPay.setBackgroundResource(R.drawable.pay_type_check);
                mCbWeixinPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                mCbWalletPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                break;
            case R.id.btn_to_pay:
                if (userInfo == null) {
                    return;
                }
                switch (payType) {
                    case 0:
                        CustomApplication.getRetrofit().walletPay(mOrderId, MD5Util.MD5(userInfo.getPassword())).enqueue(this);
                        break;
                    case 1:
                        requestWeiXinPay();
//                        requestWeiXinSign();
//                        wechatpay();
                        break;
                    case 2:
                        requestAliPay();
                        break;
                }
                break;
        }
    }

    // 微信支付
    public void wechatpay(WeiXinPayBean weiXinPayBean, String sign) {

        WeChatPayHandle weChatPayHandle = new WeChatPayHandle(this);
        /**
         * @param orderid      订单号
         * @param total_amount 订单总金额
         * @param pay_app_id   服务端返回的预支付交易会话ID
         * @param token        应用token  用于发送至服务器的数据签名用
         * @param method       请求获取服务器prepayid方法名,如:mobileapi.pay.weixinpay
         * @param url          请求URL前缀 如:http://fenxiao.wyaopeng.com/index.php/api
         * @return
         */
        WeiXinPayOutput weiXinPayOutput = new WeiXinPayOutput();
        weiXinPayOutput.setAppid(weiXinPayBean.map.appId);
        weiXinPayOutput.setPay_sign(sign);
        weiXinPayOutput.setApi_key(Contants.WEI_XIN_SECRET);
        weiXinPayOutput.setNonce_str(weiXinPayBean.map.nonceStr);
        weiXinPayOutput.setTimestamp(weiXinPayBean.map.timeStamp);
        weiXinPayOutput.setPartnerid(Contants.WEI_XIN_BUSINESS_ID);
        weiXinPayOutput.setPrepayid(weiXinPayBean.prepay_id);
        weChatPayHandle.pay(weiXinPayOutput);
    }

    // 支付宝支付
    public void alipay(final String orderInfo) {
        LogUtil.d("devon", "orderInfo-----" + orderInfo);
        AliPayHandle aliPayHandle = new AliPayHandle(this);
        try {
            aliPayHandle.alipay(orderInfo);

        } catch (AliPayHandle.APliPaySetingInfoNullException e) {
            e.printStackTrace();
        }
//        AliPayInfo aliPayInfo = new AliPayInfo();
//        aliPayInfo.setPartner("2088121635967741");
//        aliPayInfo.setSeller("shopexlanlian@shopex.cn");
//        aliPayInfo.setRsa_private("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANrcJWntLzth1WrTATvFWEpgZbGr0GB87dmfCX2X5k6xY8YyrX/7yn2ku+2O3RqU2JzbHRn80LOneXv6vW5CwPn87IgDZm+Jlm1a4MXGUdLFK5AxnKnWPn8/pn2d0q36f+ffCZFz5vvttIiFxL1DK/FEBihbucEILjsTflzxLTEfAgMBAAECgYAQTanz1BlaoRUW2hUTcMX89GVe8N+a8HN/bJt2YaRkZE58azYFfKo+5dCiB6xs2H2yYZgvYWfimi0wkSMfXZWmZlPB2H5jSFpVc3Hm3x7P8v62Q++2TPx50w92YW3XkRcgb/yxPcEnFtVhySYpKgjN+xPZAFc/eIw/HFqAezT40QJBAP/NRhuelKQA4Q4pB/pV8u8NUYdwW3XVw1A5TXdbAVLR2ZRgY5nnMsWlnQK6fttO7IqgT6J3MgkaeDTksVzNkKMCQQDbB4vvHBTal6fktzNO4nBCa13iLXKFmVqH2OyQYS3P6mI898VfYSuihxMFnLzf8mmD66m60e7CDgLP1UzkhtlVAkEApJ22c0m/QDesnU88ahZrqvk4MV/WC+PPuE3YE2pGVMTlL35EOqO1YcotW7cmsf19bcyy0svAMGmPWAKgPSew3wJBAKzdi+evdhX/05aDsI710DvbmUFFNTmUCwtkfXCGAi4ygk40DyZz/ohLqwum5LqrC/P+LvsvbQzjGf0GD6Xdd/ECQCOpH8Hli51DwGzCzXLYju52Dhk94C6A7ChvJnhk4Syew/g/k4F4ES5nZWpgQ1toxvGKe/CXS3LVnbbEAnRj9ok=");
//        aliPayInfo.setNotify_url("http://shop.wyaopeng.com/index.php/openapi/ectools_payment/parse/wap/wap_payment_plugin_malipay_server/callback1/");
//        aliPayInfo.setSubject("测试商品");
//        aliPayInfo.setBody("测试商品");
//        aliPayInfo.setPrice("0.01");
//        try {
//            AliPayHandle aliPayHandle = new AliPayHandle(this, aliPayInfo);
//        } catch (AliPayHandle.APliPaySetingInfoNullException e) {
//            e.printStackTrace();
//        }

    }
}
