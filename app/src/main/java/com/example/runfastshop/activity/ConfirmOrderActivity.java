package com.example.runfastshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ordercenter.OrderRemarkActivity;
import com.example.runfastshop.activity.usercenter.AddressSelectActivity;
import com.example.runfastshop.adapter.moneyadapter.BalanceProductAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.BalanceInfo;
import com.example.runfastshop.bean.FoodBean;
import com.example.runfastshop.bean.coupon.MerchantCoupons;
import com.example.runfastshop.bean.redpackage.RedPackages;
import com.example.runfastshop.config.IntentConfig;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;
import com.example.runfastshop.view.MaxHeightRecyclerView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 确认订单界面
 */
public class ConfirmOrderActivity extends ToolBarActivity implements Callback<String> {

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
    @BindView(R.id.tv_order_remark)
    TextView mTvOrderRemark;

    private List<BalanceInfo> data = new ArrayList<>();
    private Integer mAgentId;
    private Integer mBusinessId;
    private int mNetType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        initData();
        getRedData();
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
                mAgentId = flist.get(i).getAgentId();
                mBusinessId = flist.get(i).getBusinessId();
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


    private void getRedData() {
        Integer userId = UserService.getUserId(this);
        mNetType = 1;
        CustomApplication.getRetrofit().postRedPackage(1, mBusinessId).enqueue(this);
    }

    private void getDiscountData() {
        Integer userId = UserService.getUserId(this);
        mNetType = 2;
        CustomApplication.getRetrofit().GetCoupan(1, mAgentId).enqueue(this);
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
                Intent mIntent = new Intent(this, OrderRemarkActivity.class);
                if (mTvOrderRemark.getText().toString().equals("口味备注等要求（选填）")) {
                    startActivityForResult(mIntent, IntentConfig.REQUEST_CODE);
                }else {
                    mIntent.putExtra("remark_data",mTvOrderRemark.getText().toString());
                    startActivityForResult(mIntent, IntentConfig.REQUEST_CODE);
                }
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
            if (data != null)
                mTvOrderRemark.setText(data.getStringExtra("order_remark"));
        }
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
        if (mNetType == 1) {
            if (!TextUtils.isEmpty(data)) {
                RedPackages redPackages = GsonUtil.parseJsonWithGson(data, RedPackages.class);
                int mSize = redPackages.getRedPackets().size();
                int number = mSize > 0 ? mSize : 0;
                tvRedPacket.setText("可用红包" + number + "个");
            } else {
                tvRedPacket.setText("暂无可用红包");
            }
            getDiscountData();
        }
        if (mNetType == 2) {
            if (!TextUtils.isEmpty(data)) {
                MerchantCoupons merchantCoupons = GsonUtil.parseJsonWithGson(data, MerchantCoupons.class);
                int number = merchantCoupons.getRows().size() > 0 ? merchantCoupons.getRows().size() : 0;
                tvRedPacket.setText("可用优惠券" + number + "个");
            }else {
                tvRedPacket.setText("暂无可用优惠券");
            }
        }
    }
}
