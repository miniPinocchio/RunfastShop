package com.example.runfastshop.fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.runfastshop.R;
import com.example.runfastshop.activity.BusinessActivity;
import com.example.runfastshop.activity.DeliveryAddressActivity;
import com.example.runfastshop.activity.SearchProductActivity;
import com.example.runfastshop.adapter.BottomPageAdapter;
import com.example.runfastshop.adapter.BreakfastAdapter;
import com.example.runfastshop.adapter.NormalAdapter;
import com.example.runfastshop.adapter.PageScrollAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.BusinessExercise;
import com.example.runfastshop.bean.BusinessInfo;
import com.example.runfastshop.bean.mainmiddle.MiddleSort;
import com.example.runfastshop.bean.mainmiddle.MiddleSorts;
import com.example.runfastshop.bean.maintop.MapInfo;
import com.example.runfastshop.bean.maintop.MapInfos;
import com.example.runfastshop.bean.maintop.TopImage;
import com.example.runfastshop.bean.maintop.TopImage1;
import com.example.runfastshop.bean.maintop.TopImages;
import com.example.runfastshop.data.IntentFlag;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;
import com.example.runfastshop.view.CustomScrollView;
import com.example.runfastshop.view.recyclerview.HorizontalPageLayoutManager;
import com.example.runfastshop.view.recyclerview.PagingScrollHelper;
import com.example.supportv1.utils.LogUtil;
import com.jude.rollviewpager.RollPagerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 首页
 * A simple {@link Fragment} subclass.
 */
public class TakeOutFoodFragment extends Fragment implements Callback<String>, BGARefreshLayout.BGARefreshLayoutDelegate, View.OnClickListener, PagingScrollHelper.onPageChangeListener {


    Unbinder unbinder;
    @BindView(R.id.roll_view_pager)
    RollPagerView pagerView;
    @BindView(R.id.scrollView)
    CustomScrollView scrollView;
    @BindView(R.id.layout_search_location)
    LinearLayout layoutSearchLocation;
    @BindView(R.id.layout_search_input)
    RelativeLayout layoutSearchInput;

    @BindView(R.id.recycler_recommend)
    RecyclerView recyclerView;

    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rl_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.rv_home_middle)
    RecyclerView mRvHomeMiddle;
    @BindView(R.id.rv_home_bottom)
    RecyclerView mRvHomeBottom;

    private List<BusinessInfo> businessInfos = new ArrayList<>();
    private int netType;
    private double pointLat;
    private double pointLon;
    private BreakfastAdapter loadMoreAdapter;
    private int page = 1;
    private LinearLayoutManager linearLayoutManager;
    private List<TopImage> imgurl;//轮播图地址
    private List<TopImage1> imgurl1;//轮播图地址
    private List<MiddleSort> middleurl;//中部模块数据
    private NormalAdapter mNormalAdapter;
    private int mAgentId;//代理商id

    private PageScrollAdapter myAdapter;
    private HorizontalPageLayoutManager horizontalPageLayoutManager;
    private HorizontalPageLayoutManager horizontalPageLayoutManager1;
    private PagingScrollHelper scrollHelper;
    private static final int MROWS = 2;//行数
    private static final int MCOLUMNS = 4;//列数
    private BottomPageAdapter mBottomPageAdapter;
    private Intent mIntent;


    public TakeOutFoodFragment() {
    }

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    if (aMapLocation.getLatitude() != 0.0 && aMapLocation.getLongitude() != 0.0) {
                        tvAddress.setText(aMapLocation.getPoiName());
                        pointLat = aMapLocation.getLatitude();
                        pointLon = aMapLocation.getLongitude();
                        Log.e("AmapError", "pointLat:"
                                + pointLat + ", pointLon:"
                                + pointLon);
                        netPostAddress(aMapLocation.getLongitude(), aMapLocation.getLatitude());
                        if (mLocationClient != null) {
                            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
                        }
                    }
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
            }
        }
    };
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_take_out_food, container, false);
        unbinder = ButterKnife.bind(this, view);
        //这里以ACCESS_COARSE_LOCATION为例
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    10000);//自定义的code
        } else {
            initMap();
        }
        initData();
        initRefreshLayout(view);
        setData();
        setListener();
        return view;
    }

    private void initRefreshLayout(View view) {
        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);
        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(getActivity(), true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_mt_pull_down);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);
        mRefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.e("AmapError", "权限允许");
        switch (requestCode) {
            case 10000:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //用户同意授权
                    initMap();
                } else {
                    //用户拒绝授权

                }
                break;
        }
    }

    private void initMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(true);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    private void setData() {
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(loadMoreAdapter);
    }

    private void setListener() {
        scrollView.setOnScrollListener(new CustomScrollView.OnScrollListener() {
            @Override
            public void onScroll(int scrollY) {
                int height = pagerView.getHeight();
                Log.d("scrollY", "scrollY = " + scrollY + ",height =" + height);
                if (scrollY > height) {
                    layoutSearchLocation.setVisibility(View.GONE);
                    layoutSearchInput.setVisibility(View.VISIBLE);
                } else if (scrollY < 0) {
                    layoutSearchLocation.setVisibility(View.GONE);
                    layoutSearchInput.setVisibility(View.GONE);
                } else {
                    layoutSearchLocation.setVisibility(View.VISIBLE);
                    layoutSearchInput.setVisibility(View.GONE);
                }
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //SCROLL_STATE_IDLE recylerview停止滑动
                if (newState == RecyclerView.SCROLL_STATE_IDLE &&
                        linearLayoutManager.findLastVisibleItemPosition() == businessInfos.size() - 1) {
                    page++;
                    getNearbyBusiness(pointLon, pointLat, page);
                }
            }
        });
    }

    private void initData() {
        //轮播页
        imgurl = new ArrayList<>();
        mNormalAdapter = new NormalAdapter(getActivity(), imgurl);
        pagerView.setAdapter(mNormalAdapter);

        //中部横向滚动
        middleurl = new ArrayList<>();
        myAdapter = new PageScrollAdapter(getActivity(), middleurl);
        mRvHomeMiddle.setAdapter(myAdapter);
        horizontalPageLayoutManager = new HorizontalPageLayoutManager(MROWS, MCOLUMNS);
        mRvHomeMiddle.setLayoutManager(horizontalPageLayoutManager);
        scrollHelper = new PagingScrollHelper();
        scrollHelper.setUpRecycleView(mRvHomeMiddle);
        scrollHelper.setOnPageChangeListener(this);

        recyclerView.setFocusable(false);
        loadMoreAdapter = new BreakfastAdapter(businessInfos, getContext(), this);
        //loadMoreAdapter = new LoadMoreAdapter(getContext(), adapter);
        //loadMoreAdapter.setLoadMoreLitener(this);

        //底部滚动
        imgurl1 = new ArrayList<>();
        horizontalPageLayoutManager1 = new HorizontalPageLayoutManager(1, 3);
        mRvHomeBottom.setLayoutManager(horizontalPageLayoutManager1);
        mBottomPageAdapter = new BottomPageAdapter(getActivity(), imgurl1);
        mRvHomeBottom.setAdapter(mBottomPageAdapter);
    }

    /**
     * 上传经纬度
     */
    private void netPostAddress(Double lon, Double lat) {
        netType = 1;
//        CustomApplication.getRetrofit().postAddress(lon, lat).enqueue(this);
        //TODO 经纬度
        CustomApplication.getRetrofit().postAddress(110.07, 23.38).enqueue(this);
    }


    /**
     * 获取首页轮播图
     */
    private void netHomeImage(int agentId) {
        netType = 2;
        CustomApplication.getRetrofit().getAdvert(agentId).enqueue(this);
    }

    /**
     * 获取首页
     */
    private void netHomePager(int agentId) {
        netType = 3;
        CustomApplication.getRetrofit().getHomePage(0, agentId).enqueue(this);
//        CustomApplication.getRetrofit().getHomePage(0, 4).enqueue(this);
    }

    /**
     * 获取附近商家
     */
    private void getNearbyBusiness(Double lon, Double lat, int pager) {
        netType = 4;
//        CustomApplication.getRetrofit().getNearbyBusinesses(String.valueOf(lon), String.valueOf(lat), pager).enqueue(this);
        CustomApplication.getRetrofit().getNearbyBusinesses("110.07", "23.38", pager).enqueue(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_search_name_two, R.id.layout_select_address, R.id.layout_search_product})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_search_product:
                mIntent = new Intent(getActivity(), SearchProductActivity.class);
                mIntent.putExtra("pointLat", pointLat);
                mIntent.putExtra("pointLon", pointLon);
                startActivity(mIntent);
                break;
            case R.id.tv_search_name_two:
                startActivity(new Intent(getActivity(), SearchProductActivity.class));
                break;
            case R.id.layout_select_address:
                startActivityForResult(new Intent(getActivity(), DeliveryAddressActivity.class), 1001);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != 1002) {
            return;
        }
        String address = data.getStringExtra("address");
        pointLat = data.getDoubleExtra("pointLat", 0.0);
        pointLon = data.getDoubleExtra("pointLon", 0.0);
        tvAddress.setText(address);
        netPostAddress(pointLon, pointLat);
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
        CustomToast.INSTANCE.showToast(getContext(), "网络异常");
        if (mRefreshLayout != null) {
            mRefreshLayout.endRefreshing();
        }
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
                MapInfos mapInfos = GsonUtil.parseJsonWithGson(data, MapInfos.class);
                mAgentId = mapInfos.getAgentId();
                MapInfo map1 = mapInfos.getMap();
                pointLat = map1.getLatitude();
                pointLon = map1.getLongitude();
                page = 1;
                getNearbyBusiness(pointLon, pointLat, page);
            } else if (netType == 2) {
                TopImages topImages = GsonUtil.parseJsonWithGson(data, TopImages.class);
                if (topImages != null && topImages.getRows1().size() > 0) {
                    for (TopImage topImage : topImages.getRows1()) {
                        imgurl.add(topImage);
                    }
                    mNormalAdapter.notifyDataSetChanged();
                }
//                LogUtil.e("getRows2", topImages.getRows2().size() + "");
                if (topImages != null && topImages.getRows2().size() > 0) {
                    for (TopImage1 topImage : topImages.getRows2()) {
                        imgurl1.add(topImage);
                    }
                    mBottomPageAdapter.notifyDataSetChanged();
                }

                netHomePager(mAgentId);
            } else if (netType == 3) {
                MiddleSorts middleSorts = GsonUtil.parseJsonWithGson(data, MiddleSorts.class);
                if (middleSorts != null && middleSorts.getRows().size() > 0) {
                    for (MiddleSort middle : middleSorts.getRows()) {
                        middleurl.add(middle);
                    }
                    myAdapter.notifyDataSetChanged();
                }

            } else if (netType == 4) {
                if (page == 1) {
                    businessInfos.clear();
                }
                JSONArray bus = object.getJSONArray("rows");
                if (bus == null || bus.length() <= 0) {
                    mRefreshLayout.endRefreshing();
                    loadMoreAdapter.notifyDataSetChanged();
                    return;
                }
                for (int i = 0; i < bus.length(); i++) {
                    JSONObject busObject = bus.getJSONObject(i);
                    BusinessInfo info = new BusinessInfo();
                    info.id = busObject.optInt("id");
                    info.mini_imgPath = busObject.optString("mini_imgPath");
                    info.imgPath = busObject.optString("imgPath");
                    info.name = busObject.optString("name");
                    info.distance = busObject.optDouble("distance");
                    info.levelId = busObject.optInt("levelId");
                    info.salesnum = busObject.optInt("levelId");
                    info.startPay = busObject.optDouble("startPay");
                    info.busshowps = busObject.optDouble("busshowps");
                    info.baseCharge = busObject.optDouble("baseCharge");
                    LogUtil.d("baseCharge", info.baseCharge + "");
                    info.isDeliver = busObject.optInt("isDeliver");
                    info.speed = busObject.optString("speed");
                    info.alist = new ArrayList<>();
                    JSONArray alist = busObject.optJSONArray("alist");
                    if (alist != null) {
                        int length1 = alist.length();
                        for (int j = 0; j < length1; j++) {
                            JSONObject alistObject = alist.getJSONObject(j);
                            BusinessExercise exercise = new BusinessExercise();
                            exercise.ptype = alistObject.optInt("ptype");
                            exercise.fulls = alistObject.optDouble("fulls");
                            exercise.lesss = alistObject.optDouble("lesss");
                            exercise.showname = alistObject.optString("showname");
                            info.alist.add(exercise);
                        }
                    }
                    businessInfos.add(info);
                }
                loadMoreAdapter.notifyDataSetChanged();
                mRefreshLayout.endRefreshing();
                netHomeImage(mAgentId);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        netPostAddress(pointLon, pointLat);
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void onClick(View v) {
        Integer positionBusiness = (Integer) v.getTag();
        Intent intent = new Intent(getContext(), BusinessActivity.class);
        intent.setFlags(IntentFlag.MAIN_BOTTOM_PAGE);
        intent.putExtra("business", businessInfos.get(positionBusiness));
        startActivity(intent);
    }

    @Override
    public void onPageChange(int index) {
        switch (index) {
            case 0:
                break;
            case 1:
                break;
        }
    }
}
