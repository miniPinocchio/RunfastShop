package com.example.runfastshop.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.adapter.BusinessAdapter;
import com.example.runfastshop.fragment.BusinessFragment;
import com.example.runfastshop.fragment.BusinessInfoFragment;
import com.example.runfastshop.fragment.EvaluateFragment;
import com.example.runfastshop.util.CustomUtils;
import com.example.runfastshop.view.MaxHeightRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BusinessInfoActivity extends AppCompatActivity {

    @BindView(R.id.iv_title_bg)
    ImageView ivTitleBg;
    @BindView(R.id.tv_business_title)
    TextView tvBusinessTitle;
    @BindView(R.id.iv_sub_price)
    ImageView ivSubPrice;
    @BindView(R.id.tv_sub_price)
    TextView tvSubPrice;
    @BindView(R.id.tv_activity_num)
    TextView tvActivityNum;
    @BindView(R.id.iv_activity_bottom)
    ImageView ivActivityBottom;
    @BindView(R.id.layout_activity)
    LinearLayout layoutActivity;
    @BindView(R.id.layout_activity_first)
    LinearLayout layoutActivityFirst;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.layout_activity_show)
    LinearLayout layoutActivityShow;
    @BindView(R.id.iv_business_logo)
    ImageView ivBusinessLogo;
    @BindView(R.id.tv_business_name)
    TextView tvBusinessName;
    @BindView(R.id.layout_business_info)
    LinearLayout layoutBusinessInfo;
    @BindView(R.id.tv_sale_startPay)
    TextView tvSaleStartPay;
    @BindView(R.id.tv_sale_price)
    TextView tvSalePrice;
    @BindView(R.id.tv_business_sales_num)
    TextView tvBusinessSalesNum;
    @BindView(R.id.tv_sale_time)
    TextView tvSaleTime;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.business_title)
    TextView businessTitle;
    @BindView(R.id.iv_collection)
    ImageView ivCollection;
    @BindView(R.id.tb_collapsing_test)
    Toolbar tbCollapsingTest;
    @BindView(R.id.tool_bar)
    CollapsingToolbarLayout toolBar;
    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.tl_list_tab)
    TabLayout mTabLayout;
    @BindView(R.id.vp_list_view)
    ViewPager mViewPager;
    @BindView(R.id.blackview)
    View blackview;
    @BindView(R.id.car_recyclerview)
    MaxHeightRecyclerView carRecyclerview;
    @BindView(R.id.car_container)
    LinearLayout carContainer;
    @BindView(R.id.car_nonselect)
    TextView carNonselect;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.amount_container)
    LinearLayout amountContainer;
    @BindView(R.id.car_limit)
    TextView carLimit;
    @BindView(R.id.car_rl)
    RelativeLayout carRl;
    @BindView(R.id.iv_shop_car)
    ImageView ivShopCar;
    @BindView(R.id.car_badge)
    TextView carBadge;
    @BindView(R.id.layout_shop_car)
    FrameLayout layoutShopCar;
    @BindView(R.id.rootview)
    CoordinatorLayout rootview;

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
        setTitle();
        BusinessFragment businessFragment = new BusinessFragment();
        mFragments.add(businessFragment);
        mFragments.add(new EvaluateFragment());
        mFragments.add(new BusinessInfoFragment());

        mAdapter = new BusinessAdapter(getSupportFragmentManager(), mFragments, mStringList);

        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_orange_select));
        mTabLayout.setTabTextColors(getResources().getColor(R.color.color_text_gray_two), getResources().getColor(R.color.color_orange_select));
    }

    private void setTitle() {
        mStringList.add(getResources().getString(R.string.text_commodity));
        mStringList.add(getResources().getString(R.string.text_evaluate));
        mStringList.add(getResources().getString(R.string.text_business));
    }


    public void toggleCar(View view) {

    }

    public void goAccount(View view) {

    }
}
