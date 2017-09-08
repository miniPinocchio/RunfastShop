package com.example.runfastshop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.BusinessActivity;
import com.example.runfastshop.application.CustomApplication;
import com.shizhefei.fragment.LazyFragment;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 商家
 * A simple {@link Fragment} subclass.
 */
public class BusinessInfoFragment extends LazyFragment implements Callback<String> {

    TextView tvBusinessAddress;

    TextView tvBusinessPhone;

    TextView tvBusinessRemark;

    TextView tvDistributionTime;

    Unbinder unbinder;

    public BusinessInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreateViewLazy(Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_business_info);
        iniView();
        getBusinessEvaluate(((BusinessActivity) getActivity()).getBusinessId());
    }

    private void iniView() {
        tvBusinessAddress = (TextView) findViewById(R.id.tv_business_address);
        tvBusinessPhone = (TextView) findViewById(R.id.tv_business_phone);
        tvBusinessRemark = (TextView) findViewById(R.id.tv_business_remark);
        tvDistributionTime = (TextView) findViewById(R.id.tv_distribution_time);
    }

    /**
     * 获取商家评价
     */
    private void getBusinessEvaluate(int id) {
        CustomApplication.getRetrofit().getBusinessInfo(id).enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        Log.d("params", "response = " + response.isSuccessful() + ",data = " + data);
        if (response.isSuccessful()) {
            ResolveData(data);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

    }

    private void ResolveData(String data) {
        try {
            JSONObject object = new JSONObject(data);
            JSONObject business = object.getJSONObject("business");
            tvBusinessAddress.setText(business.optString("address"));
            tvBusinessPhone.setText(business.optString("mobile"));
            tvBusinessRemark.setText(business.optString("content"));
            tvDistributionTime.setText(business.optString("wordTime"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
