package com.example.runfastshop.activity.ordercenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayChannelActivity extends ToolBarActivity implements Callback<String>, View.OnClickListener {

    @BindView(R.id.cb_weixin_pay)
    CheckBox mCbWeixinPay;
    @BindView(R.id.cb_wallet_pay)
    CheckBox mCbWalletPay;
    @BindView(R.id.cb_ali_pay)
    CheckBox mCbAliPay;
    @BindView(R.id.btn_to_pay)
    Button mBtnToPay;

    private int payType;//0 微信 1 钱包 2支付宝
    private int mOrderId;
    private double mPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_channel);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mOrderId = getIntent().getIntExtra("orderId", 0);
        mPrice = getIntent().getDoubleExtra("price", 0.0);
        mCbAliPay.setOnClickListener(this);
        mCbWeixinPay.setOnClickListener(this);
        mCbWalletPay.setOnClickListener(this);
        mBtnToPay.setText("确认支付 ¥ " + mPrice);
    }

    @OnClick({R.id.btn_to_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_to_pay:
                String password = UserService.getUserInfo().getPassword();
                Integer id = UserService.getUserInfo().getId();
                CustomApplication.getRetrofit().walletPay(mOrderId, password, id).enqueue(this);
                break;
        }
    }

    private CompoundButton.OnCheckedChangeListener cbListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.cb_weixin_pay:
                    payType = 0;
                    mCbWalletPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                    mCbAliPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                    break;
                case R.id.cb_wallet_pay:
                    payType = 1;
                    mCbWeixinPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                    mCbAliPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                    break;
                case R.id.cb_ali_pay:
                    payType = 2;
                    mCbWeixinPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                    mCbWalletPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                    break;
            }
        }
    };

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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_weixin_pay:
                payType = 0;
                mCbWeixinPay.setBackgroundResource(R.drawable.pay_type_check);
                mCbWalletPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                mCbAliPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                break;
            case R.id.cb_wallet_pay:
                payType = 1;
                mCbWalletPay.setBackgroundResource(R.drawable.pay_type_check);
                mCbWeixinPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                mCbAliPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                break;
            case R.id.cb_ali_pay:
                payType = 2;
                mCbAliPay.setBackgroundResource(R.drawable.pay_type_check);
                mCbWeixinPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                mCbWalletPay.setBackgroundResource(R.drawable.pay_type_nocheck);
                break;
        }
    }
}
