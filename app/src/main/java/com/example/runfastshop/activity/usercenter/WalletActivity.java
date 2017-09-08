package com.example.runfastshop.activity.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 钱包
 */
public class WalletActivity extends ToolBarActivity {


    @BindView(R.id.tv_wallet_money)
    TextView tvWalletMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        ButterKnife.bind(this);
        setRightMsg("收支明细");
    }

    @OnClick({R.id.tv_right_title, R.id.btn_wallet_recharge, R.id.btn_wallet_withdrawals, R.id.layout_withdrawals_record, R.id.layout_withdrawals_account})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_right_title:
                startActivity(new Intent(this,MoneyDetailActivity.class));
                break;
            case R.id.btn_wallet_recharge:
                startActivity(new Intent(this,WalletRechargeActivity.class));
                break;
            case R.id.btn_wallet_withdrawals:
                startActivity(new Intent(this,CashActivity.class));
                break;
            case R.id.layout_withdrawals_record:
                startActivity(new Intent(this,CashRecordActivity.class));
                break;
            case R.id.layout_withdrawals_account:
                break;
        }
    }
}
