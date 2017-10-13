package com.gxuc.runfast.shop.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.supportv1.utils.LogUtil;
import com.lljjcoder.citylist.Toast.ToastUtils;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import cn.shopex.pay.Contants;


public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;

    private static PayStateCallBack callBack = null;

    public interface PayStateCallBack {
        void weixinPayResult(boolean state);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        View view = new View(this);
//        view.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
//        setContentView(view);
        Log.d(TAG, "WXPayEntryActivity被调用");
        api = WXAPIFactory.createWXAPI(this, Contants.WEI_XIN_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
        Toast.makeText(this, "openid = " + req.openId, Toast.LENGTH_SHORT).show();

        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_LAUNCH_BY_WX:
                break;
            default:
                break;
        }
    }

    @Override
    public void onResp(BaseResp resp) {
        Log.d("devon", "onPayFinish, errCode = " + resp.errCode + "  " + resp.errStr);
        // 回调中errCode值列表：
        // 0 成功 展示成功页面
        // -1 错误 可能的原因：签名错误、未注册APPID、项目设置APPID不正确、注册的APPID与设置的不匹配、其他异常等。
        // -2 用户取消 无需处理。发生场景：用户不支付了，点击取消，返回APP。

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int errorCode = resp.errCode;
            String msg = resp.errStr;
            LogUtil.d("onResp 微信支付回调", msg + "");

//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("微信支付回调");
//            builder.setMessage("微信支付结果：" + String.valueOf(resp.errCode) + "onResp 微信支付回调: " + msg);
//            builder.show();

            switch (errorCode) {
                case 0:
                    ToastUtils.showShortToast(this,"支付成功");
                    finish();
                    break;
                case -1:
                    ToastUtils.showShortToast(this,"支付失败");
                    finish();
                    break;
                case -2:
                    ToastUtils.showShortToast(this,"支付取消");
                    finish();
                    break;
                default:
                    ToastUtils.showShortToast(this,"支付失败");
//                    setResult(RESULT_OK);
                    finish();
                    break;
            }

//            if (errorCode == 0 && null != callBack) {
//
//                callBack.weixinPayResult(true);
//
//            } else {
//                callBack.weixinPayResult(false);
//            }

//            finish();

        }
    }
}