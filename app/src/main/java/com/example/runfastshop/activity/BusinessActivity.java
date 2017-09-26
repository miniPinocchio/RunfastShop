package com.example.runfastshop.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.adapter.BusinessAdapter;
import com.example.runfastshop.adapter.shopcaradater.SpecCarAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.BusinessExercise;
import com.example.runfastshop.bean.BusinessInfo;
import com.example.runfastshop.bean.FoodBean;
import com.example.runfastshop.bean.OptionBean;
import com.example.runfastshop.bean.SpecBean;
import com.example.runfastshop.bean.SubOptionBean;
import com.example.runfastshop.bean.TypeBean;
import com.example.runfastshop.bean.business.BusinessDetail;
import com.example.runfastshop.bean.business.BusinessDetails;
import com.example.runfastshop.bean.maintop.TopImage;
import com.example.runfastshop.bean.maintop.TopImage1;
import com.example.runfastshop.bean.order.ShoppingCarBean;
import com.example.runfastshop.bean.order.ShoppingTrolley;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.NetConfig;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.fragment.BusinessFragment;
import com.example.runfastshop.fragment.BusinessInfoFragment;
import com.example.runfastshop.fragment.EvaluateFragment;
import com.example.runfastshop.impl.constant.UrlConstant;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.CustomUtils;
import com.example.runfastshop.util.GsonUtil;
import com.example.runfastshop.util.ViewUtils;
import com.example.runfastshop.view.AddWidget;
import com.example.runfastshop.view.AppBarStateChangeListener;
import com.example.runfastshop.view.HeadZoomScrollView;
import com.example.runfastshop.view.ZFlowLayout;
import com.example.supportv1.utils.LogUtil;
import com.github.florent37.viewanimator.AnimationListener;
import com.github.florent37.viewanimator.ViewAnimator;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import shopex.cn.sharelibrary.ShareViewDataSource;
import shopex.cn.sharelibrary.SharedView;

import static java.lang.Double.NaN;

/**
 * 商家界面
 */
public class BusinessActivity extends ToolBarActivity implements AddWidget.OnAddClick, View.OnClickListener, Callback<String>, SpecCarAdapter.UpdateSpecCountImp {

    @BindView(R.id.tl_list_tab)
    TabLayout mTabLayout;
    @BindView(R.id.vp_list_view)
    ViewPager mViewPager;
    @BindView(R.id.iv_back)
    ImageView ivBack;

    @BindView(R.id.tv_business_name)
    TextView tvBusinessName;
    @BindView(R.id.tv_sub_price)
    TextView tvSubPrice;
    @BindView(R.id.tv_activity_num)
    TextView tvActivityNum;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.layout_activity_first)
    LinearLayout layoutActivityFirst;
    @BindView(R.id.layout_activity_show)
    LinearLayout layoutActivityShow;
    @BindView(R.id.layout_activity)
    LinearLayout layoutActivity;
    @BindView(R.id.iv_activity_bottom)
    ImageView ivActivityBottom;
    @BindView(R.id.iv_sub_price)
    ImageView ivSubPrice;
    @BindView(R.id.iv_business_logo)
    ImageView ivBusinessLogo;
    @BindView(R.id.tv_sale_startPay)
    TextView tvSaleStartPay;
    @BindView(R.id.tv_sale_price)
    TextView tvSalePrice;
    @BindView(R.id.tv_sale_time)
    TextView tvSaleTime;
    @BindView(R.id.tv_business_sales_num)
    TextView tvSalesNum;
    @BindView(R.id.add_widget)
    AddWidget addWidgetDetail;
    @BindView(R.id.iv_title_bg)
    ImageView ivTitleBg;
    @BindView(R.id.iv_collection)
    ImageView mIvCollection;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    private boolean isShowBottom;

    private TextView car_badge, car_limit, tv_amount;

    private ImageView iv_shop_car;

//    private CarAdapter carAdapter;

    private SpecCarAdapter carAdapter;

    private List<Fragment> mFragments = new ArrayList<>();

    private List<String> mStringList = new ArrayList<>();

    private BusinessAdapter mAdapter;

    public View blackView, scroll_container;

    private int shopWidth;
    private int[] carLoc;

    public BottomSheetBehavior behavior;

    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private boolean sheetScrolling;
    private CoordinatorLayout rootview;
    private Toolbar mToolbar;
    private FrameLayout layoutShopCar;
    private AppBarLayout appBarLayout;
    private TextView business_title;
    private BigDecimal decimal;
    private RelativeLayout layoutProductDetail;

    //商品详情视图
    private TextView tvProductName;
    private TextView tvProductSale;
    private TextView tvProductPrice;
    //private TextView tvProductCount;
    //private ImageView ivSub;
    private ImageView ivGoodsHead;
    private TextView tvSpec;
    private TextView tvSpecNum;
    private RelativeLayout layoutSpec1;

    //商品集合
    private List<FoodBean> foodBeens = new ArrayList<>();
    private List<FoodBean> carFoods = new ArrayList<>();
    //类型集合
    private List<TypeBean> types = new ArrayList<>();

    private int netType;
    private int businessId;
    //规格选择弹框------------
    private AlertDialog specDialog;
    private TextView tvSpecTitle;
    private TextView tvSpecProperty;
    private TextView tvSpecPrice;
    private ZFlowLayout layoutSpec;
    private ZFlowLayout layoutProduct;

    List<SpecBean> specBeanList = new ArrayList<>();
    List<OptionBean> optionBeenList = new ArrayList<>();
    private Integer positionSpec;
    private TextView tvSpecSelect;
    private ImageView tvAdd;
    private TextView tvSpecPropertyTwo;
    private ZFlowLayout layoutProductTwo;
    private LinearLayout layoutGood;
    private Integer point = 0;
    private FoodBean foodBeanDetail;
    private String specName;
    private String typeName;
    private String typeNameTwo;
    private boolean isShowDetail;
    private FoodBean mFoodBean;
    private Integer specId;//规格id

    //-----------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        ButterKnife.bind(this);
        initView();
        initProductView();
        initData();
        initShopCar();
        setData();
        setListener();
        initDialog();
    }

    /**
     * 初始化规格弹框界面
     */
    private void initDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_spec_select, null);
        tvSpecTitle = (TextView) view.findViewById(R.id.tv_spec_title);
        tvSpecProperty = (TextView) view.findViewById(R.id.tv_spec_property);
        tvSpecPropertyTwo = (TextView) view.findViewById(R.id.tv_spec_property_two);
        tvSpecPrice = (TextView) view.findViewById(R.id.tv_spec_price);
        tvAdd = (ImageView) view.findViewById(R.id.iv_add);

        layoutSpec = (ZFlowLayout) view.findViewById(R.id.flow_layout_spec);
        layoutProduct = (ZFlowLayout) view.findViewById(R.id.flow_product_property);
        layoutProductTwo = (ZFlowLayout) view.findViewById(R.id.flow_product_property_two);

        layoutGood = (LinearLayout) view.findViewById(R.id.layout_product_two);
        tvSpecSelect = (TextView) view.findViewById(R.id.tv_select_spec);
        ImageView ivCloseSpec = (ImageView) view.findViewById(R.id.iv_close_spec);

        tvSpecSelect.setOnClickListener(this);
        ivCloseSpec.setOnClickListener(this);
        specDialog = new AlertDialog.Builder(this).setView(view).create();
    }

    /**
     * 初始化商品视图
     */
    private void initProductView() {
        HeadZoomScrollView scrollView = (HeadZoomScrollView) findViewById(R.id.scrollView);
        tvProductName = (TextView) findViewById(R.id.tv_food_name);
        tvProductSale = (TextView) findViewById(R.id.tv_food_sale);
        tvProductPrice = (TextView) findViewById(R.id.tv_food_price);

        layoutSpec1 = (RelativeLayout) findViewById(R.id.layout_detail_spec);
        tvSpec = (TextView) findViewById(R.id.tv_detail_spec);
        tvSpecNum = (TextView) findViewById(R.id.tv_detail_spec_num);
        //tvProductCount = (TextView) findViewById(R.id.tv_product_count);
        //ivSub = (ImageView) findViewById(R.id.iv_sub);
        ivGoodsHead = (ImageView) findViewById(R.id.iv_product_head);
        layoutSpec1.setOnClickListener(this);
    }


    private void initShopCar() {
        layoutShopCar = (FrameLayout) findViewById(R.id.layout_shop_car);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        iv_shop_car = (ImageView) findViewById(R.id.iv_shop_car);
        car_badge = (TextView) findViewById(R.id.car_badge);
        business_title = (TextView) findViewById(R.id.business_title);
        car_limit = (TextView) findViewById(R.id.car_limit);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        RecyclerView carRecView = (RecyclerView) findViewById(R.id.car_recyclerview);
        carRecView.setLayoutManager(new LinearLayoutManager(this));
        ((DefaultItemAnimator) carRecView.getItemAnimator()).setSupportsChangeAnimations(false);
//        carAdapter = new CarAdapter(carFoods, this);
        carAdapter = new SpecCarAdapter(carFoods, this, this);
//        carAdapter.bindToRecyclerView(carRecView);
        carRecView.setAdapter(carAdapter);
        blackView = findViewById(R.id.blackview);
        behavior = BottomSheetBehavior.from(findViewById(R.id.car_container));
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                sheetScrolling = false;
                if (newState == BottomSheetBehavior.STATE_COLLAPSED || newState == BottomSheetBehavior.STATE_HIDDEN) {
                    blackView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                sheetScrolling = true;
                blackView.setVisibility(View.VISIBLE);
                ViewCompat.setAlpha(blackView, slideOffset);
            }
        });
    }

    private void initView() {
        layoutProductDetail = (RelativeLayout) findViewById(R.id.layout_product_detail);
        rootview = (CoordinatorLayout) findViewById(R.id.rootview);
        mToolbar = (Toolbar) findViewById(R.id.tb_collapsing_test);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
    }

    private void setListener() {
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                if (position == 1 || position == 2) {
                    layoutShopCar.setVisibility(View.GONE);
                } else {
                    layoutShopCar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state, int verticalOffset) {
                Log.d("STATE", state.name());
                if (state == State.EXPANDED) {
                    business_title.setText("");
                    //展开状态
                    ivBack.setImageResource(R.drawable.icon_back_white);
                    ivBack.setAlpha(1f);
                } else if (state == State.COLLAPSED) {
                    business_title.setText(tvBusinessName.getText().toString());
                    ivBack.setImageResource(R.drawable.icon_back_black);
                    //折叠状态
                    ivBack.setAlpha(1f);
                } else {
                    business_title.setText("");
                    //中间状态
                    Log.d("STATE", "verticalOffset =" + verticalOffset);
                    ivBack.setImageResource(R.drawable.icon_back_black);
                    ivBack.setAlpha(0.5f);
                }
            }
        });


    }

    private void setData() {
        mViewPager.setAdapter(mAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_orange_select));
        mTabLayout.setTabTextColors(getResources().getColor(R.color.color_text_gray_two), getResources().getColor(R.color.color_orange_select));
    }

    private void initData() {
        int flags = getIntent().getFlags();
        if (flags == 0) {
            TopImage business = (TopImage) getIntent().getSerializableExtra("business");
            if (business != null) {
                String[] split = business.getLinkAddr().split("=");
                businessId = Integer.parseInt(split[1]);
                getBusinessDetailFromBanner(businessId);
            }
        } else if (flags == 1) {
            BusinessInfo business = (BusinessInfo) getIntent().getSerializableExtra("business");
            if (business != null) {
                businessId = business.id;
                getBusiness(business.id);
                setTitleUi(business);
            }
        } else if (flags == 2) {
            businessId = getIntent().getIntExtra("orderInfo", 0);
            getBusinessDetailFromOrder(businessId);
        } else if (flags == 3) {
            businessId = getIntent().getIntExtra("search", 0);
            getBusinessDetailFromOrder(businessId);
        } else if (flags == 4) {
            TopImage1 business = getIntent().getParcelableExtra("business");
            if (business != null) {
                String[] split = business.getLinkAddr().split("=");
                businessId = Integer.parseInt(split[1]);
                getBusinessDetailFromBanner(businessId);
            }
        }
        setTitle();
        BusinessFragment businessFragment = new BusinessFragment();
        CustomUtils.fragment = businessFragment;
        mFragments.add(businessFragment);
        mFragments.add(new EvaluateFragment());
        mFragments.add(new BusinessInfoFragment());

        mAdapter = new BusinessAdapter(getSupportFragmentManager(), mFragments, mStringList);
    }

    private void getBusinessDetailFromBanner(int id) {
        netType = 6;
        CustomApplication.getRetrofit().getBusinessInfo(id).enqueue(this);
    }

    private void getBusinessDetailFromOrder(int id) {
        netType = 7;
        LogUtil.e("订单", id + "");
        CustomApplication.getRetrofit().getBusinessInfo(id).enqueue(this);
    }

    public int getBusinessId() {
        return businessId;
    }

    /**
     * 设置ui
     *
     * @param businessInfo
     */
    private <T> void setTitleUi(BusinessInfo businessInfo) {
        tvBusinessName.setText(businessInfo.name);
        x.image().bind(ivBusinessLogo, UrlConstant.ImageBaseUrl + businessInfo.mini_imgPath, NetConfig.optionsPagerCache);
        if (!TextUtils.isEmpty(businessInfo.imgPath)) {
            x.image().bind(ivTitleBg, UrlConstant.ImageBaseUrl + businessInfo.imgPath, NetConfig.optionsPagerCache);
        }
        tvSaleStartPay.setText(businessInfo.startPay == NaN ? "¥ 0元起送" : "¥ " + String.valueOf(businessInfo.startPay) + "起送");
        tvSalePrice.setText(businessInfo.baseCharge == NaN ? "配送费¥0" : "配送费¥" + String.valueOf(businessInfo.baseCharge));
        tvSaleTime.setText("快车专送·约" + businessInfo.speed);
        tvSalesNum.setText("月售" + String.valueOf(businessInfo.salesnum) + "单");

        List<BusinessExercise> alist = businessInfo.alist;
        if (alist != null && alist.size() > 0) {
            int size = alist.size();
            tvActivityNum.setText(size + "个活动");
            if (size > 1) {
                for (int i = 0; i < alist.size(); i++) {
                    BusinessExercise exercise = alist.get(i);
                    switch (exercise.ptype) {
                        case 1:
                            tvSubPrice.setText(exercise.showname);
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            tvDiscount.setText(exercise.showname);
                            break;
                    }
                }
            } else {
                BusinessExercise exercise = alist.get(0);
                layoutActivity.setVisibility(View.GONE);
                switch (exercise.ptype) {
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        ivSubPrice.setImageResource(R.drawable.icon_fracture);
                        break;
                }
                tvSubPrice.setText(exercise.showname);
            }
        } else {
            ivActivityBottom.setVisibility(View.GONE);
            ivSubPrice.setVisibility(View.GONE);
            tvSubPrice.setVisibility(View.GONE);
            tvActivityNum.setText("暂无活动");
        }
    }

    /**
     * 获取商品列表
     */
    private void getBusiness(int id) {
        netType = 1;
        CustomApplication.getRetrofit().getBusinessGoods(id).enqueue(this);
    }

    private void setTitle() {
        mStringList.add(getResources().getString(R.string.text_commodity));
        mStringList.add(getResources().getString(R.string.text_evaluate));
        mStringList.add(getResources().getString(R.string.text_business));
    }

    @OnClick({R.id.iv_back, R.id.iv_product_back, R.id.iv_share, R.id.iv_collection, R.id.layout_activity, R.id.layout_business_info, R.id.car_limit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_product_back:
                layoutProductDetail.setVisibility(View.INVISIBLE);
                appBarLayout.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_share:
                SharedView sharedView = new SharedView(this);

                sharedView.setSharedDataSource(
                        new ShareViewDataSource(
//                                getApplication().getCurrentSelectedShop().shop_name,
                                "aksjdkasjd",
//                                getH5ShopShareUrl(),
                                "www.baidu.com",
//                                getApplication().getCurrentSelectedShop().desc,
                                "23231231232",
//                                getH5ShopLogoUrl(),// 微信分享 shareImageUrl不能为空
                                "www.baidu.com",// 微信分享 shareImageUrl不能为空
//                                getH5ShopShareUrl(),
                                "www.baidu.com",
                                SharedView.SHARE_TEXT_IMAGE));
                sharedView.setSharedStateListen(new SharedView.SharedStateListen() {
                    @Override
                    public void shareState(int state) {

                    }
                });

                sharedView.show();
                break;
            case R.id.iv_collection:
                saveFavorite();
                break;
            case R.id.layout_activity:
                if (!isShowBottom) {
                    layoutActivityShow.setVisibility(View.VISIBLE);
                    isShowBottom = true;
                } else {
                    layoutActivityShow.setVisibility(View.GONE);
                    isShowBottom = false;
                }
                break;
            case R.id.layout_business_info:

                break;
            case R.id.car_limit:
                User userInfo = UserService.getUserInfo(this);
                if (userInfo == null) {
                    return;
                }
                saveOrderCar(userInfo);
                break;
        }
    }

    /**
     * 喜爱的商家
     */
    private void saveFavorite() {
        netType = 5;
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }

        CustomApplication.getRetrofit().postSaveShop(businessId, 1, userInfo.getId()).enqueue(this);
    }

    /**
     * 购物车
     */
    private void saveOrderCar(User userInfo) {
        netType = 8;
        List<ShoppingTrolley> trolleys = new ArrayList<>();
        for (int i = 0; i < carFoods.size(); i++) {
            mFoodBean = carFoods.get(i);
            ShoppingTrolley trolley = new ShoppingTrolley();
            trolley.setGoodsSellId(mFoodBean.getId());
            if (carFoods.get(i).getStandardList() != null && carFoods.get(i).getStandardList().size() > 0) {
                trolley.setGoodsSellStandardId(mFoodBean.getGoodsSpecId());
            }
            //TODO 子选项
//            trolley.setOptionIds(carFoods.get(i).get);
            trolley.setNum((int) mFoodBean.getSelectCount());
            trolleys.add(trolley);
        }

        ShoppingCarBean carBean = new ShoppingCarBean();
        carBean.setBusinessId(businessId);
//        carBean.setBusinessId(1);
        carBean.setCid(userInfo.getId());
        carBean.setCname(userInfo.getName());
        carBean.setList(trolleys);

        String goodJson = new Gson().toJson(carBean);
        LogUtil.e("购物车", goodJson);
        CustomApplication.getRetrofit().OrderCar(goodJson).enqueue(this);
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

    public void toggleCar(View view) {
        if (sheetScrolling) {
            return;
        }
        if (carAdapter.getItemCount() == 0) {
            return;
        }
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }


    /**
     * 处理购物车
     *
     * @param foodBean
     */
    private void dealCar(FoodBean foodBean) {
        HashMap<String, Long> typeSelect = new HashMap<>();
        BigDecimal amount = new BigDecimal(0.0);
        int total = 0;
        boolean hasFood = false;
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            ((BusinessFragment) mFragments.get(0)).getFoodAdapter().notifyDataSetChanged();
        }
        //List<FoodBean> flist = carFoods;
        int p = -1;
        for (int i = 0; i < carFoods.size(); i++) {
            FoodBean fb = carFoods.get(i);
            if (typeSelect.containsKey(fb.getType())) {
                typeSelect.put(fb.getType(), typeSelect.get(fb.getType()) + fb.getSelectCount());
            } else {
                typeSelect.put(fb.getType(), fb.getSelectCount());
            }
            if (fb.getId() == foodBean.getId()) {
                hasFood = true;
                if (foodBean.getSelectCount() == 0) {
                    p = i;
                } else {
                    fb.setSelectCount(foodBean.getSelectCount());
                }
            }
            amount = amount.add(fb.getPrice().multiply(BigDecimal.valueOf(fb.getSelectCount())));
            total += fb.getSelectCount();
        }
        if (p >= 0) {
            carFoods.remove(p);
        } else if (!hasFood) {
            carFoods.add(foodBean);
            if (typeSelect.containsKey(foodBean.getType())) {
                typeSelect.put(foodBean.getType(), typeSelect.get(foodBean.getType()) + foodBean.getSelectCount());
            } else {
                typeSelect.put(foodBean.getType(), foodBean.getSelectCount());
            }
            amount = amount.add(foodBean.getPrice().multiply(BigDecimal.valueOf(foodBean.getSelectCount())));
            total += foodBean.getSelectCount();
        }
        carAdapter.notifyDataSetChanged();
        if (total > 0) {
            car_badge.setVisibility(View.VISIBLE);
            car_badge.setText(total + "");
        } else {
            car_badge.setVisibility(View.INVISIBLE);
        }
        ((BusinessFragment) mFragments.get(0)).getTypeAdapter().updateBadge(typeSelect);
        updateAmount(amount);
    }

    private void updateAmount(BigDecimal amount) {
        if (amount.compareTo(new BigDecimal(0.0)) == 0) {
            car_limit.setText("¥20 起送");
            car_limit.setTextColor(Color.parseColor("#a8a8a8"));
            car_limit.setBackgroundColor(Color.parseColor("#535353"));
            findViewById(R.id.car_nonselect).setVisibility(View.VISIBLE);
            findViewById(R.id.amount_container).setVisibility(View.GONE);
            iv_shop_car.setImageResource(R.drawable.icon_not_shop_car);
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            car_limit.setEnabled(false);
        } else if (amount.compareTo(new BigDecimal(20.0)) < 0) {
            car_limit.setText("还差 ¥" + (new BigDecimal(20.0).subtract(amount)) + " 起送");
            car_limit.setTextColor(Color.parseColor("#a8a8a8"));
            car_limit.setBackgroundColor(Color.parseColor("#535353"));
            findViewById(R.id.car_nonselect).setVisibility(View.GONE);
            findViewById(R.id.amount_container).setVisibility(View.VISIBLE);
            iv_shop_car.setImageResource(R.drawable.icon_not_shop_car);
            car_limit.setEnabled(false);
        } else {
            car_limit.setText("去结算");
            car_limit.setTextColor(Color.WHITE);
            car_limit.setBackgroundColor(Color.parseColor("#59d178"));
            findViewById(R.id.car_nonselect).setVisibility(View.GONE);
            findViewById(R.id.amount_container).setVisibility(View.VISIBLE);
            iv_shop_car.setImageResource(R.drawable.icon_shop_car);
            car_limit.setEnabled(true);
        }
        tv_amount.setText("¥" + amount);
        decimal = amount;
    }

    public void clearCar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView tv = new TextView(this);
        tv.setText("清空购物车?");
        tv.setTextSize(14);
        tv.setPadding(ViewUtils.dip2px(this, 16), ViewUtils.dip2px(this, 16), 0, 0);
        tv.setTextColor(Color.parseColor("#757575"));
        AlertDialog alertDialog = builder
                .setNegativeButton("取消", null)
                .setCustomTitle(tv)
                .setPositiveButton("清空", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < carFoods.size(); i++) {
                            FoodBean fb = carFoods.get(i);
                            fb.setSelectCount(0);
                        }
                        carFoods.clear();
                        for (int i = 0; i < foodBeens.size(); i++) {
                            foodBeens.get(i).setSelectCount(0);
                        }
                        BusinessFragment fragment = (BusinessFragment) mFragments.get(0);
                        fragment.getFoodAdapter().notifyDataSetChanged();
                        car_badge.setVisibility(View.INVISIBLE);
                        fragment.getTypeAdapter().updateBadge(new HashMap<String, Long>());
                        updateAmount(new BigDecimal(0.0));
                    }
                })
                .show();
        Button nButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        nButton.setTextColor(ContextCompat.getColor(this, R.color.dodgerblue));
        nButton.setTypeface(Typeface.DEFAULT_BOLD);
        Button pButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        pButton.setTextColor(ContextCompat.getColor(this, R.color.dodgerblue));
        pButton.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void goAccount(View view) {
    }

    /**
     * 添加商品
     *
     * @param view
     * @param fb
     */
    @Override
    public void onAddClick(View view, FoodBean fb) {
        dealCar(fb);
        int[] addLoc = new int[2];
        view.getLocationInWindow(addLoc);
        if (shopWidth == 0) {
            carLoc = new int[2];
            shopWidth = iv_shop_car.getWidth() / 2;
            iv_shop_car.getLocationInWindow(carLoc);
            carLoc[0] = carLoc[0] + shopWidth - view.getWidth() / 2;
        }

        Path path = new Path();
        path.moveTo(addLoc[0], addLoc[1]);
        path.quadTo(addLoc[0] - 500, addLoc[1] - 200, carLoc[0], carLoc[1]);

        final TextView textView = new TextView(this);
        textView.setBackgroundResource(R.drawable.circle_red);
        textView.setText("1");
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(view.getWidth(), view.getHeight());
        rootview.addView(textView, lp);
        ViewAnimator.animate(textView).path(path).accelerate().duration(400).onStop(new AnimationListener.Stop() {
            @Override
            public void onStop() {
                rootview.removeView(textView);
            }
        }).start();
    }


    public void onAddClickSpec(View view, FoodBean fb) {

        dealCarSpec(fb);
        List<FoodBean> data = ((BusinessFragment) mFragments.get(0)).getFoodAdapter().getData();
        data.get(positionSpec).setSelectCount(data.get(positionSpec).getSelectCount() + 1);
        ((BusinessFragment) mFragments.get(0)).getFoodAdapter().setData(positionSpec, data.get(positionSpec));

        tvSpecNum.setText(data.get(positionSpec).getSelectCount() + "");
        tvSpecNum.setVisibility(data.get(positionSpec).getSelectCount() > 0 ? View.VISIBLE : View.GONE);

        int[] addLoc = new int[2];
        view.getLocationInWindow(addLoc);
        if (shopWidth == 0) {
            carLoc = new int[2];
            shopWidth = iv_shop_car.getWidth() / 2;
            iv_shop_car.getLocationInWindow(carLoc);
            carLoc[0] = carLoc[0] + shopWidth - view.getWidth() / 2;
        }

        Path path = new Path();
        path.moveTo(addLoc[0], addLoc[1]);
        path.quadTo(addLoc[0] - 500, addLoc[1] - 200, carLoc[0], carLoc[1]);

        final TextView textView = new TextView(this);
        textView.setBackgroundResource(R.drawable.circle_red);
        textView.setText("1");
        textView.setTextColor(Color.WHITE);
        textView.setGravity(Gravity.CENTER);
        CoordinatorLayout.LayoutParams lp = new CoordinatorLayout.LayoutParams(view.getWidth(), view.getHeight());
        rootview.addView(textView, lp);
        ViewAnimator.animate(textView).path(path).accelerate().duration(400).onStop(new AnimationListener.Stop() {
            @Override
            public void onStop() {
                rootview.removeView(textView);
            }
        }).start();
    }

    /**
     * 规格--"选好了"点击事件
     *
     * @param foodBean
     */
    private void dealCarSpec(FoodBean foodBean) {
        HashMap<String, Long> typeSelect = new HashMap<>();
        BigDecimal amount = new BigDecimal(0.0);
        int total = 0;
        boolean hasFood = false;
        boolean isEquals = false;
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            ((BusinessFragment) mFragments.get(0)).getFoodAdapter().notifyDataSetChanged();
        }
        //List<FoodBean> flist = carFoods;
        int p = -1;
        for (int i = 0; i < carFoods.size(); i++) {
            FoodBean fb = carFoods.get(i);

            if (fb.getId() == foodBean.getId()) {
                hasFood = true;
                if (foodBean.getSelectCount() == 0 && fb.getPrice().equals(foodBean.getPrice())
                        && TextUtils.equals(fb.getGoodsSpec(), foodBean.getGoodsSpec())
                        && TextUtils.equals(fb.getGoodsType(), foodBean.getGoodsType())
                        && TextUtils.equals(fb.getGoodsTypeTwo(), foodBean.getGoodsTypeTwo())) {
                    p = i;
                } else {
                    Log.d("price", fb.getPrice() + " = fb");
                    Log.d("price", foodBean.getPrice() + " = foodBean");

                    if (fb.getPrice().equals(foodBean.getPrice())
                            && TextUtils.equals(fb.getGoodsSpec(), foodBean.getGoodsSpec())
                            && TextUtils.equals(fb.getGoodsType(), foodBean.getGoodsType())
                            && TextUtils.equals(fb.getGoodsTypeTwo(), foodBean.getGoodsTypeTwo())) {
                        isEquals = true;
                        fb.setSelectCount(foodBean.getSelectCount());
                    } else if (!isEquals) {
                        if (i == carFoods.size() - 1) {
                            hasFood = false;
                        }
                    }
                }
            }
            if (typeSelect.containsKey(fb.getType())) {
                typeSelect.put(fb.getType(), typeSelect.get(fb.getType()) + fb.getSelectCount());
            } else {
                typeSelect.put(fb.getType(), fb.getSelectCount());
            }
            amount = amount.add(fb.getPrice().multiply(BigDecimal.valueOf(fb.getSelectCount())));
            total += fb.getSelectCount();
        }
        if (p >= 0) {
            carFoods.remove(p);
        } else if (!hasFood) {
            carFoods.add(foodBean);
            if (typeSelect.containsKey(foodBean.getType())) {
                typeSelect.put(foodBean.getType(), typeSelect.get(foodBean.getType()) + foodBean.getSelectCount());
            } else {
                typeSelect.put(foodBean.getType(), foodBean.getSelectCount());
            }
            amount = amount.add(foodBean.getPrice().multiply(BigDecimal.valueOf(foodBean.getSelectCount())));
            total += foodBean.getSelectCount();
        }

        if (total > 0) {
            car_badge.setVisibility(View.VISIBLE);
            car_badge.setText(total + "");
        } else {
            car_badge.setVisibility(View.INVISIBLE);
        }
        carAdapter.notifyDataSetChanged();
        ((BusinessFragment) mFragments.get(0)).getTypeAdapter().updateBadge(typeSelect);
        updateAmount(amount);
    }

    /**
     * 减少商品
     *
     * @param fb
     */
    @Override
    public void onSubClick(FoodBean fb) {
        dealCar(fb);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            switch (v.getId()) {
                case R.id.food_main://商品item
                    positionSpec = (Integer) v.getTag();
                    Log.d("position", "position =" + positionSpec);
                    foodBeanDetail = foodBeens.get(positionSpec);
                    //positionDetail = position;
                    isShowDetail = true;
                    if (foodBeanDetail.getIsonly() == 1) {
                        layoutSpec1.setVisibility(View.VISIBLE);
                        addWidgetDetail.setVisibility(View.GONE);
                        tvSpecNum.setText(foodBeanDetail.getSelectCount() + "");
                        tvSpecNum.setVisibility(foodBeanDetail.getSelectCount() > 0 ? View.VISIBLE : View.GONE);
                    } else {
                        layoutSpec1.setVisibility(View.GONE);
                        addWidgetDetail.setVisibility(View.VISIBLE);
                    }
                    addWidgetDetail.setData(((BusinessFragment) mFragments.get(0)).getFoodAdapter(), positionSpec, this);
                    layoutProductDetail.setVisibility(View.VISIBLE);
                    rotateyAnimShow(layoutProductDetail);
                    appBarLayout.setVisibility(View.GONE);
                    if (foodBeanDetail != null) {
                        tvProductName.setText(foodBeanDetail.getName());
                        tvProductSale.setText("月售" + foodBeanDetail.getSale());
                        tvProductPrice.setText(foodBeanDetail.getPrice() + "");
                        if (foodBeanDetail.getSelectCount() > 0) {
                            //ivSub.setVisibility(View.VISIBLE);
                            //tvProductCount.setText(foodBean.getSelectCount() + "");
                        }
                        getGoodsDetail(foodBeanDetail.getId());
                    }
                    break;
                case R.id.layout_spec://商品item
                    positionSpec = (Integer) v.getTag();
                    Log.d("position", "position =" + positionSpec);
                    FoodBean foodBeanSpec = foodBeens.get(positionSpec);
                    tvSpecTitle.setText(foodBeanSpec.getName());
                    tvSpecPrice.setText(foodBeanSpec.getPrice() + "");
                    getGoodsSpec(foodBeanSpec.getId());
                    specDialog.show();
                    break;
                case R.id.layout_detail_spec://商品详情框
                    tvSpecTitle.setText(foodBeanDetail.getName());
                    tvSpecPrice.setText(foodBeanDetail.getPrice() + "");
                    getGoodsSpec(foodBeanDetail.getId());
                    specDialog.show();
                    break;
                case R.id.tv_select_spec://规格框
                    boolean isCarFoodData = false;
                    FoodBean fbSpec = ((BusinessFragment) mFragments.get(0)).getFoodAdapter().getItem(positionSpec);
                    if (fbSpec != null) {
                        for (int i = 0; i < carFoods.size(); i++) {
                            if (fbSpec.getId() == carFoods.get(i).getId()
                                    && carFoods.get(i).getPrice().equals(BigDecimal.valueOf(Double.parseDouble(tvSpecPrice.getText().toString().trim())))
                                    && TextUtils.equals(carFoods.get(i).getGoodsSpec(), specName)
                                    && TextUtils.equals(carFoods.get(i).getGoodsType(), typeName)
                                    && TextUtils.equals(carFoods.get(i).getGoodsTypeTwo(), typeNameTwo)) {
                                fbSpec = carFoods.get(i);
                                isCarFoodData = true;
                            }
                        }
                        FoodBean foodBeanTwo = new FoodBean();
                        foodBeanTwo.setId(fbSpec.getId());
                        if (!isCarFoodData) {
                            foodBeanTwo.setSelectCount(1);
                        } else {
                            foodBeanTwo.setSelectCount(fbSpec.getSelectCount() + 1);
                        }
                        foodBeanTwo.setName(fbSpec.getName());
                        foodBeanTwo.setSale(fbSpec.getSale());
                        foodBeanTwo.setIsCommand(fbSpec.getIsCommand());
                        foodBeanTwo.setPrice(BigDecimal.valueOf(Double.parseDouble(tvSpecPrice.getText().toString().trim())));
                        foodBeanTwo.setIcon(fbSpec.getIcon());
                        foodBeanTwo.setCut(fbSpec.getCut());
                        foodBeanTwo.setType(fbSpec.getType());
                        foodBeanTwo.setIsonly(fbSpec.getIsonly());
                        foodBeanTwo.setShowprice(fbSpec.getShowprice());
                        foodBeanTwo.setGoodsSpec(specName);
                        foodBeanTwo.setGoodsType(typeName);
                        foodBeanTwo.setGoodsTypeTwo(typeNameTwo);
                        foodBeanTwo.setGoodsSpecId(specId);

                        onAddClickSpec(tvAdd, foodBeanTwo);
                    }
                    isShowDetail = false;
                    specDialog.dismiss();
                    break;
                case R.id.iv_close_spec:
                    isShowDetail = false;
                    specDialog.dismiss();
                    break;
            }
        }
    }

    /**
     * 获取商品规格
     */
    private void getGoodsSpec(int id) {
        netType = 3;
        CustomApplication.getRetrofit().getGoodsSpec(id).enqueue(this);
    }

    /**
     * 规格选项
     *
     * @param data
     * @param flowLayout
     */
    @SuppressWarnings("ResourceType")
    public void addItem(final List<SpecBean> data, final ZFlowLayout flowLayout) {
        flowLayout.removeAllViews();
        final ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 10, 10);// 设置边距

        for (int i = 0; i < data.size(); i++) {

            final TextView textView = new TextView(this);
            textView.setText(data.get(i).getName());
            if (i == 0) {
                textView.setTextColor(getResources().getColor(R.color.color_white));
                textView.setBackgroundResource(R.drawable.product_spec_background_select);
            } else {
                textView.setBackgroundResource(R.drawable.product_spec_background);
                textView.setTextColor(getResources().getColor(R.color.color_text_gray));
            }
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            specName = data.get(0).getName();
            textView.setTag(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    point = (Integer) v.getTag();
                    tvSpecPrice.setText(specBeanList.get(point).getPrice() + "");
                    specName = data.get(point).getName();
                    specId = specBeanList.get(point).getId();
                    for (int j = 0; j < flowLayout.getChildCount(); j++) {
                        if (j == point) {
                            ((TextView) flowLayout.getChildAt(j)).setTextColor(getResources().getColor(R.color.color_white));
                            flowLayout.getChildAt(j).setBackgroundResource(R.drawable.product_spec_background_select);
                        } else {
                            ((TextView) flowLayout.getChildAt(j)).setTextColor(getResources().getColor(R.color.color_text_gray));
                            flowLayout.getChildAt(j).setBackgroundResource(R.drawable.product_spec_background);
                        }
                    }
                }
            });
            flowLayout.addView(textView, layoutParams);
        }
    }

    /**
     * 规格选项
     *
     * @param data
     * @param flowLayout
     */
    @SuppressWarnings("ResourceType")
    public void addItemOption(final List<SubOptionBean> data, final ZFlowLayout flowLayout) {
        flowLayout.removeAllViews();
        final ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 10, 10);// 设置边距

        for (int i = 0; i < data.size(); i++) {

            final TextView textView = new TextView(this);
            textView.setText(data.get(i).name);
            if (i == 0) {
                textView.setTextColor(getResources().getColor(R.color.color_white));
                textView.setBackgroundResource(R.drawable.product_spec_background_select);
            } else {
                textView.setBackgroundResource(R.drawable.product_spec_background);
                textView.setTextColor(getResources().getColor(R.color.color_text_gray));
            }
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            typeName = data.get(0).name;
            textView.setTag(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer point = (Integer) v.getTag();
                    typeName = data.get(point).name;
                    for (int j = 0; j < flowLayout.getChildCount(); j++) {
                        if (j == point) {
                            ((TextView) flowLayout.getChildAt(j)).setTextColor(getResources().getColor(R.color.color_white));
                            flowLayout.getChildAt(j).setBackgroundResource(R.drawable.product_spec_background_select);
                        } else {
                            ((TextView) flowLayout.getChildAt(j)).setTextColor(getResources().getColor(R.color.color_text_gray));
                            flowLayout.getChildAt(j).setBackgroundResource(R.drawable.product_spec_background);
                        }
                    }

                }
            });
            flowLayout.addView(textView, layoutParams);
        }
    }

    /**
     * 规格选项
     *
     * @param data
     * @param flowLayout
     */
    @SuppressWarnings("ResourceType")
    public void addItemOptionTwo(final List<SubOptionBean> data, final ZFlowLayout flowLayout) {
        flowLayout.removeAllViews();
        final ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 10, 10);// 设置边距

        for (int i = 0; i < data.size(); i++) {

            final TextView textView = new TextView(this);
            textView.setText(data.get(i).name);
            if (i == 0) {
                textView.setTextColor(getResources().getColor(R.color.color_white));
                textView.setBackgroundResource(R.drawable.product_spec_background_select);
            } else {
                textView.setBackgroundResource(R.drawable.product_spec_background);
                textView.setTextColor(getResources().getColor(R.color.color_text_gray));
            }
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            typeNameTwo = data.get(0).name;
            textView.setTag(i);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer point = (Integer) v.getTag();
                    typeNameTwo = data.get(point).name;
                    for (int j = 0; j < flowLayout.getChildCount(); j++) {
                        if (j == point) {
                            ((TextView) flowLayout.getChildAt(j)).setTextColor(getResources().getColor(R.color.color_white));
                            flowLayout.getChildAt(j).setBackgroundResource(R.drawable.product_spec_background_select);
                        } else {
                            ((TextView) flowLayout.getChildAt(j)).setTextColor(getResources().getColor(R.color.color_text_gray));
                            flowLayout.getChildAt(j).setBackgroundResource(R.drawable.product_spec_background);
                        }
                    }
                }
            });
            flowLayout.addView(textView, layoutParams);
        }
    }

    /**
     * 动画显示商品详情
     *
     * @param view
     */
    public void rotateyAnimShow(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha",
                0f, 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX",
                0, 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY",
                0, 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(500).start();
    }

    /**
     * 获取商品详情
     */
    private void getGoodsDetail(int id) {
        netType = 2;
        CustomApplication.getRetrofit().getGoodsDetail(id).enqueue(this);
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
        try {
            JSONObject object = new JSONObject(data);
            if (netType == 1) {
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
                    types.add(typeBean);
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
                            foodBean.setAgentId(jsonObject.optInt("agentId"));
                            foodBeens.add(foodBean);
                            fragment.getFoodBeanList().add(foodBean);
                        }
                    }
                }
                fragment.getFoodAdapter().notifyDataSetChanged();
                fragment.getTypeAdapter().notifyDataSetChanged();
            }
            if (netType == 2) {
                JSONObject goodsSell = object.getJSONObject("goodsSell");
                String imgPath = goodsSell.optString("imgPath");
                x.image().bind(ivGoodsHead, UrlConstant.ImageBaseUrl + imgPath, NetConfig.optionsPagerCache);
            }
            if (netType == 3) {
                specBeanList.clear();
                JSONObject goodsSell = object.getJSONObject("goods");
                JSONArray standardList = goodsSell.getJSONArray("standardList");
                JSONArray optionList = goodsSell.getJSONArray("optionList");
                int standarLength = standardList.length();
                int optionLength = optionList.length();
                if (standarLength > 0) {
                    for (int i = 0; i < standarLength; i++) {
                        SpecBean specBean = GsonUtil.parseJsonWithGson(standardList.getJSONObject(i).toString(), SpecBean.class);
                        specBeanList.add(specBean);
                    }
                    addItem(specBeanList, layoutSpec);
                    tvSpecPrice.setText(specBeanList.get(0).getPrice() + "");
                }
                if (optionLength > 0) {
                    for (int i = 0; i < optionLength; i++) {
                        JSONObject optionObject = optionList.getJSONObject(i);
                        OptionBean optionBean = new OptionBean();
                        optionBean.id = optionObject.optInt("id");
                        optionBean.name = optionObject.optString("name");
                        optionBean.subOption = new ArrayList<>();
                        JSONArray subOption = optionObject.getJSONArray("subOption");
                        int subLength = subOption.length();
                        if (subLength > 0) {
                            for (int j = 0; j < subLength; j++) {
                                JSONObject subObject = subOption.getJSONObject(j);
                                SubOptionBean subOptionBean = new SubOptionBean();
                                subOptionBean.id = subObject.optInt("id");
                                subOptionBean.name = subObject.optString("name");
                                subOptionBean.sort = subObject.optInt("sort");
                                optionBean.subOption.add(subOptionBean);
                            }
                        }
                        optionBeenList.add(optionBean);
                    }
                    tvSpecProperty.setText(optionBeenList.get(0).name + "：");
                    addItemOption(optionBeenList.get(0).subOption, layoutProduct);
                    if (optionLength > 1) {
                        layoutGood.setVisibility(View.VISIBLE);
                        tvSpecPropertyTwo.setText(optionBeenList.get(1).name + "：");
                        addItemOptionTwo(optionBeenList.get(1).subOption, layoutProductTwo);
                    } else {
                        layoutGood.setVisibility(View.GONE);
                    }
                }
            }
            if (netType == 5) {
                if (object.optString("succ").equals("收藏成功！")) {
                    mIvCollection.setImageResource(R.drawable.icon_collection_yes);
                } else if (object.optString("succ").equals("已取消收藏！")) {
                    mIvCollection.setImageResource(R.drawable.icon_collection_no);
                }
                CustomToast.INSTANCE.showToast(this, object.getString("succ"));
            }

            if (netType == 6 || netType == 7) {
                BusinessDetails businessDetails = GsonUtil.parseJsonWithGson(data, BusinessDetails.class);
                BusinessDetail business = businessDetails.getBusiness();
                BusinessInfo info = new BusinessInfo();
                info.name = business.getName();
                info.imgPath = business.getImgPath();
                info.startPay = (double) business.getStartPay();
                info.baseCharge = (double) business.getBaseCharge();
                info.speed = String.valueOf(business.getSpeed());
                info.salesnum = business.getSalesnum();
                getBusiness(business.getId());
                setTitleUi(info);
            }
            if (netType == 8) {
                if ((object.optBoolean("success"))) {
                    List<FoodBean> flist = carFoods;
                    startActivity(new Intent(this, ConfirmOrderActivity.class).putExtra("foodBean", (Serializable) flist).putExtra("price", decimal));
                } else {
                    CustomToast.INSTANCE.showToast(this, object.optString("msg"));
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(View view, FoodBean foodBean) {
        if (foodBean.getIsonly() == 1) {
            onAddClickSpec(view, foodBean);
        } else {
            onAddClick(view, foodBean);
            if (isShowDetail) {
                addWidgetDetail.setData(((BusinessFragment) mFragments.get(0)).getFoodAdapter(), positionSpec, this);
            }
        }
    }

    @Override
    public void sub(FoodBean fb) {
        dealCarSpec(fb);
        if (fb.getIsonly() == 1) {
            List<FoodBean> data = ((BusinessFragment) mFragments.get(0)).getFoodAdapter().getData();
            for (int i = 0; i < data.size(); i++) {
                if (fb.getId() == data.get(i).getId()) {
                    data.get(i).setSelectCount(data.get(i).getSelectCount() - 1);
                    ((BusinessFragment) mFragments.get(0)).getFoodAdapter().setData(i, data.get(i));
                    if (positionSpec == i) {
                        tvSpecNum.setText(data.get(i).getSelectCount() + "");
                        tvSpecNum.setVisibility(data.get(i).getSelectCount() > 0 ? View.VISIBLE : View.GONE);
                    }
                }
            }
        } else if (isShowDetail) {
            addWidgetDetail.setData(((BusinessFragment) mFragments.get(0)).getFoodAdapter(), positionSpec, this);
        }
    }
}
