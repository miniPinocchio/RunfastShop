package com.example.runfastshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ordercenter.OrderRemarkActivity;
import com.example.runfastshop.activity.ordercenter.PayChannelActivity;
import com.example.runfastshop.activity.usercenter.AddressSelectActivity;
import com.example.runfastshop.activity.usercenter.CouponActivity;
import com.example.runfastshop.adapter.moneyadapter.BalanceProductAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.BalanceInfo;
import com.example.runfastshop.bean.FoodBean;
import com.example.runfastshop.bean.address.AddressInfo;
import com.example.runfastshop.bean.address.AddressInfos;
import com.example.runfastshop.bean.coupon.CouponBean;
import com.example.runfastshop.bean.coupon.CouponBeans;
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
    @BindView(R.id.layout_address_user_info)
    LinearLayout layoutAddressUserInfo;
    @BindView(R.id.layout_coupons)
    LinearLayout layoutCoupons;
    @BindView(R.id.iv_business_icon)
    ImageView ivBusinessIcon;
    @BindView(R.id.tv_business_name)
    TextView tvBusinessName;

    private List<BalanceInfo> balanceInfoList = new ArrayList<>();
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
    private int businessId;
    private BalanceProductAdapter adapter;
    private double salePrice;
    private BigDecimal countPrice;

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

        if (flist != null) {
            tvBusinessName.setText(flist.get(0).getBusinessName());
            List<Integer> optsubids = new ArrayList<>();
            for (int i = 0; i < flist.size(); i++) {
                BalanceInfo info = new BalanceInfo();
                info.name = flist.get(i).getName();
                info.number = String.valueOf(flist.get(i).getSelectCount());
                info.price = flist.get(i).getPrice();
                balanceInfoList.add(info);
                mAgentId = flist.get(i).getAgentId();
                mBusinessId = flist.get(i).getBusinessId();
                optsubids.add(flist.get(i).getId());
            }
            mChildren.setOptsubids(optsubids);
        }
        mPrice = (BigDecimal) getIntent().getSerializableExtra("price");
        businessId = getIntent().getIntExtra("businessId", 0);
        salePrice = getIntent().getDoubleExtra("salePrice", 0.0);
        BalanceInfo info = new BalanceInfo();
        info.name = "配送费";
        info.number = "";
        info.price = BigDecimal.valueOf(salePrice);
        balanceInfoList.add(info);

        computePrice();
        adapter = new BalanceProductAdapter(balanceInfoList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }

    /**
     * 计算价格
     */
    private void computePrice() {
        if (mPrice != null) {
            tvOrderPrice.setText(String.valueOf(mPrice));
            BigDecimal decimal = mPrice.add(BigDecimal.valueOf(salePrice));
            if (mDecimalCoupon != null) {
                layoutCoupons.setVisibility(View.VISIBLE);
                tvCouponPrice.setText(String.valueOf(mDecimalCoupon));
                countPrice = decimal.subtract(mDecimalCoupon);
                tvSubPrice.setText(String.valueOf(countPrice));//减掉优惠券
            } else {
                layoutCoupons.setVisibility(View.GONE);
                countPrice = decimal;
                tvSubPrice.setText(String.valueOf(decimal));//减掉优惠券
            }
            tvTotalPrice.setText(String.valueOf(countPrice));
        }
    }


    private void getRedData() {
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        mNetType = 1;
        CustomApplication.getRetrofit().postRedPackage(userInfo.getId(), businessId).enqueue(this);
    }

    /**
     * 获取优惠券
     */
    private void getCouponData() {
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        mNetType = 2;
        CustomApplication.getRetrofit().GetMyCoupan(userInfo.getId(), 0).enqueue(this);
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
                startActivityForResult(new Intent(this, CouponActivity.class).putExtra("order", "startOrder"), 1001);
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
            //TODO  参数含义
            CustomApplication.getRetrofit().createOrder(businessId, mAddressId, 0, 0,
                    countPrice.doubleValue(),
//                    mSubtract.doubleValue(),
                    0.01,
                    "", goodsJson).enqueue(this);
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
            layoutAddressUserInfo.setVisibility(View.VISIBLE);
        } else {
            tvUserAddress.setText("还没有地址，点击添加");
            layoutAddressUserInfo.setVisibility(View.GONE);
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
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1001:
                if (data == null) {
                    return;
                }
                CouponBean couponBean = (CouponBean) data.getSerializableExtra("coupon");
                BalanceInfo info = new BalanceInfo();
                info.name = "优惠券";
                info.number = "";
                info.price = BigDecimal.valueOf(couponBean.getPrice());
                info.isDelete = true;
                balanceInfoList.add(info);
                adapter.notifyDataSetChanged();

                mDecimalCoupon = BigDecimal.valueOf(couponBean.getPrice());
                computePrice();

                tvCashCoupon.setTextColor(getResources().getColor(R.color.color_balance_price));
                tvCashCoupon.setText("- ¥ " + couponBean.getPrice());
                break;
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
                CouponBeans couponBeans = GsonUtil.parseJsonWithGson(data, CouponBeans.class);
                if (couponBeans.getRows() != null) {
                    int number = couponBeans.getRows().size() > 0 ? couponBeans.getRows().size() : 0;
                    tvCashCoupon.setText("可用优惠券" + number + "个");
                    return;
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
                    mIntent.putExtra("price", codeInfo.getGoodsSellRecord().getTotalpay());
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
