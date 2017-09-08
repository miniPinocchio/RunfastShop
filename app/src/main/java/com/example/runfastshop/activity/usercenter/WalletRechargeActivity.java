package com.example.runfastshop.activity.usercenter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.PayDetailActivity;
import com.example.runfastshop.activity.ToolBarActivity;
import com.lljjcoder.citypickerview.widget.CityPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 钱包充值
 */
public class WalletRechargeActivity extends ToolBarActivity {

    @BindView(R.id.tv_pay_mode)
    TextView tvPayMode;
    @BindView(R.id.et_account_money)
    EditText etMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_recharge);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.tv_pay_mode, R.id.btn_pay_mode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_pay_mode:
                showPayMode();
                break;
            case R.id.btn_pay_mode:
                startActivity(new Intent(this,PayDetailActivity.class));
                break;
        }
    }

    /**
     * 弹出选择方式
     */
    private void showPayMode(){
        CityPicker cityPicker = new CityPicker.Builder(this)
                .textSize(16)
                .setData(new String[]{"微信支付","支付宝支付"})
                .title("支付方式")
                .titleBackgroundColor("#ffffff")
                .titleTextColor("#333333")
                .backgroundPop(0xa0333333)
                .confirTextColor("#fc9153")
                .cancelTextColor("#999999")
                .textColor(Color.parseColor("#333333"))
                .itemPadding(10)
                .onlyShowProvinceAndCity(true)
                .build();
        cityPicker.show();

        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];
                tvPayMode.setText(province);
            }

            @Override
            public void onCancel() {
            }
        });
    }
}
