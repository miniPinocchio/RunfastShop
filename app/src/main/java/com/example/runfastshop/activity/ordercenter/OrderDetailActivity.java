package com.example.runfastshop.activity.ordercenter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.order.OrderInfo;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.view.MaxHeightRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.tv_order_man_state)
    TextView mTvOrderManState;
    @BindView(R.id.tv_man_deliver_time)
    TextView mTvManDeliverTime;
    @BindView(R.id.btn_cancel_order)
    Button mBtnCancelOrder;
    @BindView(R.id.btn_contact_business)
    Button mBtnContactBusiness;
    @BindView(R.id.btn_contact_man)
    Button mBtnContactMan;
    @BindView(R.id.iv_order_detail_business_img)
    ImageView mIvOrderDetailBusinessImg;
    @BindView(R.id.tv_order_detail_business_name)
    TextView mTvOrderDetailBusinessName;
    @BindView(R.id.recycler_product_list)
    MaxHeightRecyclerView mRecyclerProductList;
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
    @BindView(R.id.tv_order_detail_pat_type)
    TextView mTvOrderDetailPatType;
    @BindView(R.id.tv_order_detail_remark)
    TextView mTvOrderDetailRemark;

    private List<String> mStrings;
    private OrderInfo mOrderInfo;

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
        Integer id = mOrderInfo.getId();
        CustomApplication.getRetrofit().getOrderDetail(id,1).enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            String data = response.body();
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

    }


    @OnClick({R.id.btn_cancel_order, R.id.btn_contact_business, R.id.btn_contact_man})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel_order:
                break;
            case R.id.btn_contact_business:
                break;
            case R.id.btn_contact_man:
                break;
        }
    }
}
