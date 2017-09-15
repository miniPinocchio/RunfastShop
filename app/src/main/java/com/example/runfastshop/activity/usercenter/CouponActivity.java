package com.example.runfastshop.activity.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.CashCouponActivity;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.adapter.moneyadapter.CouponsAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.coupon.CouponBean;
import com.example.runfastshop.bean.coupon.CouponBeans;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 优惠券
 */
public class CouponActivity extends ToolBarActivity implements View.OnClickListener, Callback<String> {

    @BindView(R.id.view_coupon_list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_use_coupon_num)
    TextView mTvUseCouponNum;
    @BindView(R.id.tv_get_coupon)
    TextView mTvGetCoupon;

    private List<CouponBean> mCouponBeanList;
    private CouponsAdapter mAllAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        ButterKnife.bind(this);
        initData();
        getNetData();
    }

    /**
     * 获取优惠券
     */
    private void getNetData() {
        Integer id = UserService.getUserInfo().getId();
        CustomApplication.getRetrofit().GetMyCoupan(id, 0).enqueue(this);
    }

    private void initData() {
        mCouponBeanList = new ArrayList<>();
        mAllAdapter = new CouponsAdapter(mCouponBeanList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAllAdapter);
    }


    @Override
    public void onClick(View v) {

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
        CouponBeans couponBeans = GsonUtil.parseJsonWithGson(data, CouponBeans.class);
        if (couponBeans.getRows().size() > 0) {
            mTvUseCouponNum.setText(String.valueOf(couponBeans.getRows().size()));
            mCouponBeanList.addAll(couponBeans.getRows());
            mAllAdapter.notifyDataSetChanged();
        }
    }

    @OnClick(R.id.tv_get_coupon)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_get_coupon:
                startActivity(new Intent(this, CashCouponActivity.class));
        }
    }
}
