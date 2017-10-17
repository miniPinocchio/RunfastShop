package com.example.runfastshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.runfastshop.R;
import com.example.runfastshop.adapter.BusinessAdapter;
import com.example.runfastshop.adapter.moneyadapter.CashCouponAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.coupon.CouponInfo;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.runfastshop.config.IntentConfig.AGENT_ID;

/**
 * 代金券
 */
public class CashCouponActivity extends ToolBarActivity implements Callback<String>, View.OnClickListener {

    @BindView(R.id.view_coupon_list)
    RecyclerView recyclerView;

    private CashCouponAdapter mAdapter;

    private List<CouponInfo> couponInfoList = new ArrayList<>();
    private int netType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_coupon);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mAdapter = new CashCouponAdapter(couponInfoList, this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        getNetData();
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            Integer position = (Integer) v.getTag();
            CouponInfo couponInfo = couponInfoList.get(position);
            if (couponInfo != null) {
                getCoupon(couponInfo.getId());
            }
        }
    }

    /**
     * 获取优惠券
     */
    private void getNetData() {
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        netType = 1;
        CustomApplication.getRetrofit().receiveCoupan(userInfo.getId(), AGENT_ID, 0).enqueue(this);
    }

    /**
     * 领取优惠券
     */
    private void getCoupon(Integer id) {
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        netType = 2;
        CustomApplication.getRetrofit().getCoupan(userInfo.getId(), id).enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            Log.d("params", "response = " + data);
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
        if (TextUtils.isEmpty(data)) {
            return;
        }
        try {
            JSONObject object = new JSONObject(data);
            if (netType == 2){
                String msg = object.optString("ale");
                CustomToast.INSTANCE.showToast(this, msg);
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
                return;
            }
            JSONArray list = object.getJSONArray("list");
            if (list == null || list.length()<=0){
                recyclerView.setVisibility(View.GONE);
                return;
            }
            int length = list.length();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = list.getJSONObject(i);
                CouponInfo couponInfo = GsonUtil.parseJsonWithGson(jsonObject.toString(),CouponInfo.class);
                couponInfoList.add(couponInfo);
            }
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
