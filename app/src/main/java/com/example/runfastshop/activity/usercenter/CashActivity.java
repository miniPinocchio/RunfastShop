package com.example.runfastshop.activity.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现
 */
public class CashActivity extends ToolBarActivity {

    @BindView(R.id.tv_bank_mode)
    TextView tvBankMode;
    @BindView(R.id.et_account_money)
    EditText etAccountMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_bank_mode, R.id.tv_cash_all, R.id.btn_cash_mode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bank_mode:
                startActivity(new Intent(this,SelectBankActivity.class));
                break;
            case R.id.tv_cash_all:
                break;
            case R.id.btn_cash_mode:
                break;
        }
    }
}
