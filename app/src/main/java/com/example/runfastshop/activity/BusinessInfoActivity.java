package com.example.runfastshop.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.runfastshop.R;
import com.example.runfastshop.adapter.BusinessAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.BusinessInfo;
import com.example.runfastshop.bean.FoodBean;
import com.example.runfastshop.bean.TypeBean;
import com.example.runfastshop.fragment.BusinessFragment;
import com.example.runfastshop.fragment.BusinessInfoFragment;
import com.example.runfastshop.fragment.EvaluateFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BusinessInfoActivity extends AppCompatActivity implements Callback<String> {

    @BindView(R.id.vp_list_view)
    ViewPager mViewPager;
    private List<Fragment> mFragments = new ArrayList<>();

    private List<String> mStringList = new ArrayList<>();
    private BusinessAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_info);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {

        BusinessInfo business = (BusinessInfo) getIntent().getSerializableExtra("business");
        if (business != null) {
            getBusiness(business.id);
        }

        setTitle();
        BusinessFragment businessFragment = new BusinessFragment();
        mFragments.add(businessFragment);
        mFragments.add(new EvaluateFragment());
        mFragments.add(new BusinessInfoFragment());

        mAdapter = new BusinessAdapter(getSupportFragmentManager(), mFragments, mStringList);

        mViewPager.setAdapter(mAdapter);

//        mTabLayout.setupWithViewPager(mViewPager);
//        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
//        //mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
//        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_orange_select));
//        mTabLayout.setTabTextColors(getResources().getColor(R.color.color_text_gray_two), getResources().getColor(R.color.color_orange_select));
    }

    private void setTitle() {
        mStringList.add(getResources().getString(R.string.text_commodity));
        mStringList.add(getResources().getString(R.string.text_evaluate));
        mStringList.add(getResources().getString(R.string.text_business));
    }

    /**
     * 获取商品列表
     */
    private void getBusiness(int id) {
        CustomApplication.getRetrofit().getBusinessGoods(id).enqueue(this);
    }


    public void toggleCar(View view) {

    }

    public void goAccount(View view) {

    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            Log.d("params", "response = " + data);
            ResolveData(data);
        }
    }

    private void ResolveData(String data) {
        try {
            JSONObject object = new JSONObject(data);
            BusinessFragment fragment = (BusinessFragment) mFragments.get(0);
            JSONArray gtlist = object.getJSONArray("gtlist");
            if (gtlist == null || gtlist.length() <= 0) {
                return;
            }
            int length = gtlist.length();
            for (int i = 0; i < length; i++) {
                JSONObject listObject = gtlist.getJSONObject(i);
                TypeBean typeBean = new TypeBean();
                typeBean.setName(listObject.optString("name"));
                fragment.getTypeBeanList().add(typeBean);

                JSONArray glistArray = listObject.optJSONArray("glist");
                if (glistArray != null) {
                    int length1 = glistArray.length();
                    for (int j = 0; j < length1; j++) {
                        JSONObject jsonObject = glistArray.getJSONObject(j);
                        FoodBean foodBean = new FoodBean();
                        foodBean.setId(jsonObject.optInt("id"));
                        foodBean.setName(jsonObject.optString("name"));
                        foodBean.setIcon(jsonObject.optString("imgPath"));
                        foodBean.setType(jsonObject.optString("sellTypeName"));
                        foodBean.setShowprice(jsonObject.optString("showprice"));
                        foodBean.setIsonly(jsonObject.optInt("isonly"));
                        foodBean.setPrice(BigDecimal.valueOf(jsonObject.optDouble("price")).setScale(1, BigDecimal.ROUND_HALF_DOWN));
                        foodBean.setSale(String.valueOf(jsonObject.optInt("salesnum")));
                        foodBean.setBusinessId(jsonObject.optInt("businessId"));
                        foodBean.setBusinessName(jsonObject.optString("businessName"));
                        foodBean.setAgentId(jsonObject.optInt("agentId"));
                        fragment.getFoodBeanList().add(foodBean);
                    }
                }
            }
            if (fragment.getFoodAdapter() != null) {
                fragment.getFoodAdapter().notifyDataSetChanged();
            }
            if (fragment.getTypeAdapter() != null) {
                fragment.getTypeAdapter().notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

    }
}
