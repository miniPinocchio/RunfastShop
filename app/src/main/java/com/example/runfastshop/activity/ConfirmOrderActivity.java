package com.example.runfastshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ordercenter.OrderRemarkActivity;
import com.example.runfastshop.activity.usercenter.AddressSelectActivity;
import com.example.runfastshop.adapter.moneyadapter.BalanceProductAdapter;
import com.example.runfastshop.bean.BalanceInfo;
import com.example.runfastshop.bean.FoodBean;
import com.example.runfastshop.config.IntentConfig;
import com.example.runfastshop.view.MaxHeightRecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 确认订单界面
 */
public class ConfirmOrderActivity extends ToolBarActivity {

    @BindView(R.id.tv_user_address)
    TextView tvUserAddress;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.tv_red_packet)
    TextView tvRedPacket;
    @BindView(R.id.tv_cash_coupon)
    TextView tvCashCoupon;
    @BindView(R.id.recycler_product_list)
    MaxHeightRecyclerView recyclerView;
    @BindView(R.id.tv_total_price)
    TextView tvTotalPrice;
    @BindView(R.id.tv_order_price)
    TextView tvOrderPrice;
    @BindView(R.id.tv_coupon_price)
    TextView tvCouponPrice;
    @BindView(R.id.tv_sub_price)
    TextView tvSubPrice;

    private List<BalanceInfo> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        List<FoodBean> flist = (List<FoodBean>) getIntent().getSerializableExtra("foodBean");
        if (flist != null) {
            for (int i = 0; i < flist.size(); i++) {
                BalanceInfo info = new BalanceInfo();
                info.name = flist.get(i).getName();
                info.number = String.valueOf(flist.get(i).getSelectCount());
                info.price = flist.get(i).getPrice();
                data.add(info);
            }

            BalanceInfo info = new BalanceInfo();
            info.name = "配送费";
            info.number = "";
            info.price = BigDecimal.valueOf(4);
            data.add(info);
            info = new BalanceInfo();
            info.name = "包装费";
            info.number = "";
            info.price = BigDecimal.valueOf(4);
            data.add(info);
            info = new BalanceInfo();
            info.name = "优惠券";
            info.number = "";
            info.price = BigDecimal.valueOf(6);
            info.isDelete = true;
            data.add(info);
        }
        BigDecimal price = (BigDecimal) getIntent().getSerializableExtra("price");
        BigDecimal decimalCoupon = new BigDecimal("6");
        Log.d("price", "price =" + price);
        if (price != null) {
            tvOrderPrice.setText(price + "");
            tvCouponPrice.setText("" + decimalCoupon);
            tvSubPrice.setText("" + price.subtract(decimalCoupon));
            tvTotalPrice.setText(tvSubPrice.getText().toString().trim());
        }
        BalanceProductAdapter adapter = new BalanceProductAdapter(data, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.layout_user_address, R.id.layout_pay_mode, R.id.layout_red_packet, R.id.layout_cash_coupon, R.id.layout_flavor, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_user_address:
                startActivity(new Intent(this, AddressSelectActivity.class));
                break;
            case R.id.layout_pay_mode:
                break;
            case R.id.layout_red_packet:
                break;
            case R.id.layout_cash_coupon:
                startActivity(new Intent(this, CashCouponActivity.class));
                break;
            case R.id.layout_flavor:
                startActivityForResult(new Intent(this, OrderRemarkActivity.class), IntentConfig.REQUEST_CODE);
                break;
            case R.id.tv_pay:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // TODO: 2017/8/30 将备注内容加入
        if (requestCode == IntentConfig.REQUEST_CODE && resultCode == IntentConfig.REMARK_RESULT_CODE) {

        }
    }
}
