package com.example.runfastshop.activity.usercenter;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.adapter.BusinessAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.spend.AccountRecords;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.fragment.walletfragmnet.MoneyAllFragment;
import com.example.runfastshop.fragment.walletfragmnet.MoneyExpenditureFragment;
import com.example.runfastshop.fragment.walletfragmnet.MoneyIncomeFragment;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;
import com.example.runfastshop.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 收支明细
 */
public class MoneyDetailActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.tl_list_tab)
    TabLayout mTabLayout;
    @BindView(R.id.vp_list_view)
    ViewPager mViewPager;

    private List<Fragment> mFragments = new ArrayList<>();

    private List<String> mStringList = new ArrayList<>();
    private BusinessAdapter mAdapter;
    private MoneyAllFragment mMoneyAllFragment;
    private MoneyIncomeFragment mMoneyIncomeFragment;
    private MoneyExpenditureFragment mMoneyExpenditureFragment;

    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_detail);
        ButterKnife.bind(this);
        initData();
        setData();
        getNetData();
    }

    private void initData() {
        setTitle();

        mAdapter = new BusinessAdapter(getSupportFragmentManager(), mFragments, mStringList);
    }

    private void setData() {
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_orange_select));
        mTabLayout.setTabTextColors(getResources().getColor(R.color.color_text_gray), getResources().getColor(R.color.color_orange_select));
    }

    private void setTitle() {
        mStringList.add(getResources().getString(R.string.pay_all));
        mStringList.add(getResources().getString(R.string.pay_income));
        mStringList.add(getResources().getString(R.string.pay_expenditure));
    }

    private void getNetData() {
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        CustomApplication.getRetrofit().getListConsume(userInfo.getId(),page,0).enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            Log.d("params","response = "+data);
            ResolveData(data);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        CustomToast.INSTANCE.showToast(this, "网络错误");
    }


    /**
     * 解析数据
     *
     * @param data
     */
    private void ResolveData(String data) {
        AccountRecords accountRecords = GsonUtil.parseJsonWithGson(data, AccountRecords.class);
        if (accountRecords != null && accountRecords.getRows().size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("record", accountRecords);
            mMoneyAllFragment = new MoneyAllFragment();
            mMoneyIncomeFragment = new MoneyIncomeFragment();
            mMoneyExpenditureFragment = new MoneyExpenditureFragment();
            mFragments.add(mMoneyAllFragment);
            mFragments.add(mMoneyIncomeFragment);
            mFragments.add(mMoneyExpenditureFragment);
            mMoneyAllFragment.setArguments(bundle);
            mMoneyExpenditureFragment.setArguments(bundle);
            mMoneyIncomeFragment.setArguments(bundle);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                ViewUtils.setIndicator(mTabLayout, 30, 30);
            }
        });
    }

}
