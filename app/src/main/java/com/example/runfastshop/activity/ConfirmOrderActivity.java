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
import com.example.runfastshop.activity.ordercenter.PayChannelActivity;
import com.example.runfastshop.activity.usercenter.AddressSelectActivity;
import com.example.runfastshop.adapter.moneyadapter.BalanceProductAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.BalanceInfo;
import com.example.runfastshop.bean.FoodBean;
import com.example.runfastshop.bean.address.AddressInfo;
import com.example.runfastshop.bean.address.AddressInfos;
import com.example.runfastshop.bean.coupon.MerchantCoupons;
import com.example.runfastshop.bean.order.GoodsSellRecordChildren;
import com.example.runfastshop.bean.order.OrderCodeInfo;
import com.example.runfastshop.bean.redpackage.RedPackages;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.IntentConfig;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.data.IntentFlag;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;
import com.example.runfastshop.view.MaxHeightRecyclerView;
import com.example.supportv1.utils.LogUtil;
import com.google.gson.Gson;

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
    private Integer mAddressId;
    private BigDecimal mPrice;
    private BigDecimal mDecimalCoupon;
    private GoodsSellRecordChildren mChildren;
    private List<GoodsSellRecordChildren> mGoodsSellRecordChildrens;
    private Intent mIntent;
    private BigDecimal mSubtract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        initData();
        getRedData();
    }

    private void initData() {
        mChildren = new GoodsSellRecordChildren();
        mGoodsSellRecordChildrens = new ArrayList<>();
        List<FoodBean> flist = (List<FoodBean>) getIntent().getSerializableExtra("foodBean");
        for (int i = 0; i < flist.size(); i++) {
            LogUtil.d("购物清单", flist.get(i).toString());
        }

        if (flist != null) {
            List<Integer> optsubids = new ArrayList<>();
            for (int i = 0; i < flist.size(); i++) {
                BalanceInfo info = new BalanceInfo();
                info.name = flist.get(i).getName();
                info.number = String.valueOf(flist.get(i).getSelectCount());
                info.price = flist.get(i).getPrice();
                data.add(info);
                mAgentId = flist.get(i).getAgentId();
                mBusinessId = flist.get(i).getBusinessId();
                optsubids.add(flist.get(i).getId());
            }
            mChildren.setOptsubids(optsubids);
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
        mPrice = (BigDecimal) getIntent().getSerializableExtra("price");
        mDecimalCoupon = new BigDecimal("6");
        Log.d("price", "price =" + mPrice);
        if (mPrice != null) {
            tvOrderPrice.setText(String.valueOf(mPrice));
            tvCouponPrice.setText(String.valueOf(mDecimalCoupon));
            tvSubPrice.setText(String.valueOf(mPrice.subtract(mDecimalCoupon)));//减掉优惠券
            tvTotalPrice.setText(tvSubPrice.getText().toString().trim());
        }
        BalanceProductAdapter adapter = new BalanceProductAdapter(data, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }


    private void getRedData() {
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        mNetType = 1;
        CustomApplication.getRetrofit().postRedPackage(userInfo.getId(), mBusinessId).enqueue(this);
    }

    private void getCouponData() {
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        mNetType = 2;
        CustomApplication.getRetrofit().GetCoupan(userInfo.getId(), mAgentId).enqueue(this);
    }

    private void getAddressData() {
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        mNetType = 3;
        CustomApplication.getRetrofit().postListAddress(userInfo.getId()).enqueue(this);
    }

    @OnClick({R.id.layout_user_address, R.id.layout_pay_mode, R.id.layout_red_packet, R.id.layout_cash_coupon, R.id.layout_flavor, R.id.tv_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_user_address:
                Intent intent = new Intent(this, AddressSelectActivity.class);
                intent.setFlags(IntentFlag.ORDER_ADDRESS);
                startActivityForResult(intent, IntentConfig.REQUEST_CODE);
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
                } else {
                    mIntent.putExtra("remark_data", mTvOrderRemark.getText().toString());
                    startActivityForResult(mIntent, IntentConfig.REQUEST_CODE);
                }
                break;
            case R.id.tv_pay:
                toPay();
                break;
        }
    }

    /**
     *
     */
    private void toPay() {
        User userInfo = UserService.getUserInfo(this);
        if (userInfo != null) {
            mNetType = 4;
            mGoodsSellRecordChildrens.add(mChildren);
            Gson gson = new Gson();
            String goodsJson = gson.toJson(mGoodsSellRecordChildrens);
            mSubtract = mPrice.subtract(mDecimalCoupon);
            //TODO  参数含义
            CustomApplication.getRetrofit().createOrder(userInfo.getId(), mBusinessId, mAddressId, 0, 0,
                    mSubtract.doubleValue(), mSubtract.doubleValue(), "", goodsJson).enqueue(this);
        }
    }

    /**
     * @param addr
     * @param isShow 是否有地址
     */
    private void updateAddr(AddressInfo addr, boolean isShow) {
        if (isShow) {
            tvUserAddress.setText(addr.getUserAddress());
            tvUserName.setText(addr.getName());
            tvUserPhone.setText(addr.getPhone());
        } else {
            tvUserAddress.setText("还没有地址，点击添加");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IntentConfig.REQUEST_CODE && resultCode == IntentConfig.REMARK_RESULT_CODE) {
            if (data != null)
                mTvOrderRemark.setText(data.getStringExtra("order_remark"));
        } else if (requestCode == IntentConfig.REQUEST_CODE && resultCode == IntentConfig.ADDRESS_SELECT) {
            AddressInfo addressInfo = data.getParcelableExtra("addressInfo");
            updateAddr(addressInfo, true);
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
                if (redPackages.getRedPackets() != null) {
                    int mSize = redPackages.getRedPackets().size();
                    int number = mSize > 0 ? mSize : 0;
                    tvRedPacket.setText("可用红包" + number + "个");
                }
                tvRedPacket.setText("暂无可用红包");
            } else {
                tvRedPacket.setText("暂无可用红包");
            }
            getCouponData();
        }
        if (mNetType == 2) {
            if (!TextUtils.isEmpty(data)) {
                MerchantCoupons merchantCoupons = GsonUtil.parseJsonWithGson(data, MerchantCoupons.class);
                if (merchantCoupons.getRows() != null) {
                    int number = merchantCoupons.getRows().size() > 0 ? merchantCoupons.getRows().size() : 0;
                    tvCashCoupon.setText("可用优惠券" + number + "个");
                }
                tvCashCoupon.setText("暂无可用优惠券");
            } else {
                tvCashCoupon.setText("暂无可用优惠券");
            }
            getAddressData();
        }

        if (mNetType == 3) {
            AddressInfos addressInfos = GsonUtil.parseJsonWithGson(data, AddressInfos.class);
            if (addressInfos != null) {
                if (addressInfos.getRows() != null && addressInfos.getRows().size() > 0) {
                    updateAddr(addressInfos.getRows().get(0), true);
                    mAddressId = addressInfos.getRows().get(0).getId();
                } else {
                    updateAddr(new AddressInfo(), false);
                }
            }
        }
        if (mNetType == 4) {
            OrderCodeInfo codeInfo = GsonUtil.parseJsonWithGson(data, OrderCodeInfo.class);
            if (codeInfo != null) {
                if (codeInfo.isSuccess()) {
                    mIntent = new Intent(this, PayChannelActivity.class);
                    mIntent.putExtra("orderId", codeInfo.getId());
                    mIntent.putExtra("price", mSubtract.doubleValue());
                    startActivity(mIntent);
                } else {
                    CustomToast.INSTANCE.showToast(this, codeInfo.getMsg());
                }
            } else {
                CustomToast.INSTANCE.showToast(this, "下单失败，请重新选择商品");
            }
        }
    }
}
