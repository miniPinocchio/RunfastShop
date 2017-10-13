package com.example.runfastshop.activity.ordercenter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.adapter.OrderGoodsAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.order.OrderDetail;
import com.example.runfastshop.bean.order.OrderInfo;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.impl.MyCallback;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;
import com.example.supportv1.utils.ImageLoaderUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends ToolBarActivity {

    @BindView(R.id.tv_order_man_state)
    TextView mTvOrderManState;
    @BindView(R.id.ll_man_deliver_time)
    LinearLayout mLlManDeliverTime;
    @BindView(R.id.tv_man_deliver_time)
    TextView mTvManDeliverTime;
    @BindView(R.id.btn_buy_again)
    Button mBtnBuyAgain;
    @BindView(R.id.btn_pay_now)
    Button mBtnPayNow;
    @BindView(R.id.btn_confirm_completed)
    Button mBtnConfirmCompleted;
    @BindView(R.id.btn_cancel_order)
    Button mBtnCancelOrder;
    @BindView(R.id.btn_contact_business)
    Button mBtnContactBusiness;
    @BindView(R.id.btn_contact_man)
    Button mBtnContactMan;
    @BindView(R.id.btn_appraise)
    Button mBtnAppraise;
    @BindView(R.id.iv_order_detail_business_img)
    ImageView mIvOrderDetailBusinessImg;
    @BindView(R.id.tv_order_detail_business_name)
    TextView mTvOrderDetailBusinessName;
    @BindView(R.id.ll_contain_product)
    LinearLayout llContainProduct;
    @BindView(R.id.tv_order_detail_price)
    TextView mTvOrderDetailPrice;
    @BindView(R.id.tv_order_detail_coupon_price)
    TextView mTvOrderDetailCouponPrice;
    @BindView(R.id.tv_order_detail_sub_price)
    TextView mTvOrderDetailSubPrice;
    @BindView(R.id.tv_order_detail_deliver_type)
    TextView mTvOrderDetailDeliverType;
    @BindView(R.id.tv_order_detail_deliver_time)
    TextView mTvOrderDetailDeliverTime;
    @BindView(R.id.tv_order_detail_man_info)
    TextView mTvOrderDetailManInfo;
    @BindView(R.id.tv_order_detail_man_phone)
    TextView mTvOrderDetailManPhone;
    @BindView(R.id.tv_order_detail_number)
    TextView mTvOrderDetailNumber;
    @BindView(R.id.rl_order_detail_pay_type)
    RelativeLayout mRlOrderDetailPatType;
    @BindView(R.id.tv_order_detail_pat_type)
    TextView mTvOrderDetailPatType;
    @BindView(R.id.tv_order_detail_remark)
    TextView mTvOrderDetailRemark;
    @BindView(R.id.tv_order_detail_showps)
    TextView tvOrderDetailShowps;
    @BindView(R.id.tv_order_detail_packaging)
    TextView tvOrderDetailPackaging;
    @BindView(R.id.tv_order_detail_coupons)
    TextView tvOrderDetailCoupons;

    private List<String> mStrings;
    private OrderInfo mOrderInfo;
    private OrderDetail orderDetailInfo;
    private User userInfo;
    private Integer orderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        ButterKnife.bind(this);
        initData();
        getNetData();
    }

    private void initData() {
        mOrderInfo = getIntent().getParcelableExtra("orderInfo");
    }

//    private void initView() {
//        mStrings = new ArrayList<>();
//        OrderDetailAdapter adapter = new OrderDetailAdapter(this, mStrings);
//        mRvOrderDetail.setAdapter(adapter);
//    }

    private void getNetData() {
        orderId = mOrderInfo.getId();
        userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        getOrderDetail(orderId, userInfo);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNetData();
    }

    private void getOrderDetail(Integer id, User userInfo) {
        CustomApplication.getRetrofit().getOrderDetail(id).enqueue(new MyCallback<String>() {

            @Override
            public void onSuccessResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String data = response.body();
                    fillView(data);
                }
            }

            @Override
            public void onFailureResponse(Call<String> call, Throwable t) {
                CustomToast.INSTANCE.showToast("网络异常");
            }
        });
    }

    /**
     * 解析数据
     *
     * @param data
     */
    private void fillView(String data) {
        orderDetailInfo = GsonUtil.fromJson(data, OrderDetail.class);
        mTvOrderManState.setText(orderDetailInfo.goodsSellRecord.statStr);

        if (orderDetailInfo.goodsSellRecord.status == 2 ||
                orderDetailInfo.goodsSellRecord.status == 3 ||
                orderDetailInfo.goodsSellRecord.status == 4 ||
                orderDetailInfo.goodsSellRecord.status == 5) {
            mLlManDeliverTime.setVisibility(View.VISIBLE);
            mTvManDeliverTime.setText(orderDetailInfo.goodsSellRecord.readyTime);
        } else {
            mLlManDeliverTime.setVisibility(View.INVISIBLE);
        }

        showBtnStatus(orderDetailInfo.goodsSellRecord.status);

        ImageLoaderUtil.displayImage(this, mIvOrderDetailBusinessImg, orderDetailInfo.goodsSellRecord.logo);
        mTvOrderDetailBusinessName.setText(orderDetailInfo.goodsSellRecord.businessName);

        OrderGoodsAdapter orderGoodsAdapter = new OrderGoodsAdapter(this, orderDetailInfo.goodsSellRecordChildren);
        llContainProduct.removeAllViews();
        for (int i = 0; i < orderDetailInfo.goodsSellRecordChildren.size(); i++) {
            llContainProduct.addView(orderGoodsAdapter.getView(i, null, null));
        }
        tvOrderDetailShowps.setText("¥ " + orderDetailInfo.goodsSellRecord.showps);
        tvOrderDetailPackaging.setText("¥ " + orderDetailInfo.goodsSellRecord.packing);
        tvOrderDetailCoupons.setText("-¥ " + orderDetailInfo.goodsSellRecord.yhprice);
        mTvOrderDetailPrice.setText("¥ " + orderDetailInfo.goodsSellRecord.price);
        mTvOrderDetailCouponPrice.setText("-¥ " + orderDetailInfo.goodsSellRecord.yhprice);
        mTvOrderDetailSubPrice.setText("¥ " + orderDetailInfo.goodsSellRecord.totalpay);
        mTvOrderDetailDeliverType.setText(orderDetailInfo.goodsSellRecord.isDeliver == 0 ? "快车专送" : "商家配送");
//        mTvOrderDetailDeliverTime.setText(orderDetailInfo.goodsSellRecord.);
        mTvOrderDetailManInfo.setText(orderDetailInfo.goodsSellRecord.userAddress + orderDetailInfo.goodsSellRecord.address);
        mTvOrderDetailManPhone.setText(orderDetailInfo.goodsSellRecord.userName + "  " + orderDetailInfo.goodsSellRecord.userMobile);
        mTvOrderDetailNumber.setText(orderDetailInfo.goodsSellRecord.orderCode);
        if (orderDetailInfo.goodsSellRecord.isPay == 1) {
            mRlOrderDetailPatType.setVisibility(View.VISIBLE);
            if (orderDetailInfo.goodsSellRecord.payType == 0) {
                mTvOrderDetailPatType.setText("支付宝");
            } else if (orderDetailInfo.goodsSellRecord.payType == 1) {
                mTvOrderDetailPatType.setText("微信");
            } else if (orderDetailInfo.goodsSellRecord.payType == 2) {
                mTvOrderDetailPatType.setText("钱包");
            }
        } else {
            mRlOrderDetailPatType.setVisibility(View.GONE);
        }
        mTvOrderDetailRemark.setText(orderDetailInfo.goodsSellRecord.content);
    }

    private void showBtnStatus(int status) {

//        if (status == -3) {
//        } else
        if (status == -1) {
            mBtnBuyAgain.setVisibility(View.VISIBLE);

            mBtnAppraise.setVisibility(View.GONE);
            mBtnCancelOrder.setVisibility(View.GONE);
            mBtnPayNow.setVisibility(View.GONE);
            mBtnConfirmCompleted.setVisibility(View.GONE);
            mBtnContactBusiness.setVisibility(View.GONE);
            mBtnContactMan.setVisibility(View.GONE);
        } else if (status == 0) {
            mBtnPayNow.setVisibility(View.VISIBLE);
            mBtnCancelOrder.setVisibility(View.VISIBLE);

            mBtnBuyAgain.setVisibility(View.GONE);
            mBtnConfirmCompleted.setVisibility(View.GONE);
            mBtnContactBusiness.setVisibility(View.GONE);
            mBtnContactMan.setVisibility(View.GONE);
            mBtnAppraise.setVisibility(View.GONE);
        } else if (status == 1 || status == 2) {
            mBtnCancelOrder.setVisibility(View.VISIBLE);
            mBtnContactBusiness.setVisibility(View.VISIBLE);

            mBtnPayNow.setVisibility(View.GONE);
            mBtnConfirmCompleted.setVisibility(View.GONE);
            mBtnBuyAgain.setVisibility(View.GONE);
            mBtnContactMan.setVisibility(View.GONE);
            mBtnAppraise.setVisibility(View.GONE);
        } else if (status == 3) {
            mBtnCancelOrder.setVisibility(View.VISIBLE);
            mBtnContactBusiness.setVisibility(View.VISIBLE);
            mBtnContactMan.setVisibility(View.VISIBLE);

            mBtnConfirmCompleted.setVisibility(View.GONE);
            mBtnPayNow.setVisibility(View.GONE);
            mBtnBuyAgain.setVisibility(View.GONE);
            mBtnAppraise.setVisibility(View.GONE);
        }
//        else if (status == 4) {
//        }
        else if (status == 5) {
            mBtnConfirmCompleted.setVisibility(View.VISIBLE);
            mBtnContactBusiness.setVisibility(View.VISIBLE);
            mBtnContactMan.setVisibility(View.VISIBLE);

            mBtnCancelOrder.setVisibility(View.GONE);
            mBtnPayNow.setVisibility(View.GONE);
            mBtnBuyAgain.setVisibility(View.GONE);
            mBtnAppraise.setVisibility(View.GONE);
        }
//        else if (status == 6) {
//        } else if (status == 7) {
//        }
        else if (status == 8) {
            mBtnBuyAgain.setVisibility(View.VISIBLE);
            mBtnAppraise.setVisibility(View.VISIBLE);

            mBtnCancelOrder.setVisibility(View.GONE);
            mBtnPayNow.setVisibility(View.GONE);
            mBtnConfirmCompleted.setVisibility(View.GONE);
            mBtnContactBusiness.setVisibility(View.GONE);
            mBtnContactMan.setVisibility(View.GONE);
        } else {
            mBtnBuyAgain.setVisibility(View.VISIBLE);

            mBtnAppraise.setVisibility(View.GONE);
            mBtnCancelOrder.setVisibility(View.GONE);
            mBtnPayNow.setVisibility(View.GONE);
            mBtnConfirmCompleted.setVisibility(View.GONE);
            mBtnContactBusiness.setVisibility(View.GONE);
            mBtnContactMan.setVisibility(View.GONE);
        }

    }

    @OnClick({R.id.btn_buy_again, R.id.btn_pay_now, R.id.btn_confirm_completed, R.id.btn_cancel_order, R.id.btn_contact_business, R.id.btn_contact_man, R.id.btn_appraise})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //再次购买
            case R.id.btn_buy_again:
                break;
            //立即支付
            case R.id.btn_pay_now:
                Intent payChannelIntent = new Intent(this, PayChannelActivity.class);
                payChannelIntent.putExtra("orderId", orderId);
                payChannelIntent.putExtra("price", orderDetailInfo.goodsSellRecord.price);

                startActivity(payChannelIntent);
                break;
            //确认完成
            case R.id.btn_confirm_completed:
                requestConfirmCompleted();
                break;
            //取消订单
            case R.id.btn_cancel_order:
                requestCancelOrder();
                break;
            //联系商家
            case R.id.btn_contact_business:
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + orderDetailInfo.goodsSellRecord.businessMobile));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
                break;
            //联系骑手
            case R.id.btn_contact_man:
                Intent data = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + orderDetailInfo.goodsSellRecord.shopperMobile));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(data);
                break;
            //评价
            case R.id.btn_appraise:
                break;
        }
    }

    private void requestConfirmCompleted() {
        CustomApplication.getRetrofit().receiveOrder(orderId, userInfo.getId()).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {


            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                CustomToast.INSTANCE.showToast("网络异常");
            }
        });

    }

    private void requestCancelOrder() {
        CustomApplication.getRetrofit().cancelOrder(orderId).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                getOrderDetail(orderId, userInfo);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                CustomToast.INSTANCE.showToast("网络异常");
            }
        });

    }
}
