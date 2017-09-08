package com.example.runfastshop.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.runfastshop.R;
import com.example.runfastshop.adapter.BusinessAdapter;
import com.example.runfastshop.fragment.CashCouponsFragment;
import com.example.runfastshop.fragment.walletfragmnet.MoneyAllFragment;
import com.example.runfastshop.fragment.walletfragmnet.MoneyExpenditureFragment;
import com.example.runfastshop.fragment.walletfragmnet.MoneyIncomeFragment;
import com.example.runfastshop.util.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 代金券
 */
public class CashCouponActivity extends ToolBarActivity {

    @BindView(R.id.tl_list_tab)
    TabLayout mTabLayout;
    @BindView(R.id.vp_list_view)
    ViewPager mViewPager;

    private List<Fragment> mFragments = new ArrayList<>();

    private List<String> mStringList = new ArrayList<>();
    private BusinessAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_coupon);
        ButterKnife.bind(this);
        initData();
        setData();
    }
    private void initData() {
        setTitle();
        for (int i = 0; i < mStringList.size(); i++) {
            mFragments.add(new CashCouponsFragment());
        }
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
        mStringList.add(getResources().getString(R.string.coupons_selected));
        mStringList.add(getResources().getString(R.string.coupons_tea));
        mStringList.add(getResources().getString(R.string.coupons_supermarket));
        mStringList.add(getResources().getString(R.string.coupons_fresh));
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
