package com.example.runfastshop.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.runfastshop.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 支付详情
 */
public class PayDetailActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_detail);
        ButterKnife.bind(this);
    }
}
