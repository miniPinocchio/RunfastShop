package com.example.runfastshop.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.example.runfastshop.R;
import com.example.runfastshop.adapter.AddressLocationAdapter;
import com.example.runfastshop.adapter.AddressSearchAdapter;
import com.example.runfastshop.adapter.LoadMoreAdapter;
import com.example.runfastshop.bean.Address;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 首页地址选择
 */
public class DeliveryAddressActivity extends ToolBarActivity implements GeocodeSearch.OnGeocodeSearchListener, View.OnClickListener, LoadMoreAdapter.LoadMoreApi {

    @BindView(R.id.tv_location_city)
    TextView tvLocationCity;
    @BindView(R.id.et_search_name)
    EditText etSearchName;
    @BindView(R.id.tv_current_address)
    TextView tvCurrentAddress;
    @BindView(R.id.view_nearby_address)
    RecyclerView recyclerViewAddress;
    @BindView(R.id.layout_select_address)
    LinearLayout layoutSelectAddress;
    @BindView(R.id.view_search_address)
    RecyclerView recyclerViewSearchAddress;

    private List<Address> addresses = new ArrayList<>();
    private List<Address> addressSearch = new ArrayList<>();

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            if (aMapLocation != null) {
                if (aMapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    regeocodeSearch(aMapLocation.getLatitude(), aMapLocation.getLongitude(), 2000);
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
    private GeocodeSearch geocodeSearch;
    private AddressLocationAdapter adapter;

    private PoiSearch.Query query;
    private PoiSearch poiSearch;
    private int pageNo = 1;
    private LoadMoreAdapter searchAdapter;

    private boolean isSearch;//是否是搜索查询

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address);
        ButterKnife.bind(this);
        //这里以ACCESS_COARSE_LOCATION为例
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1001);//自定义的code
        } else {
            initMap();
        }
        initData();
        setListener();
    }



    private void initData() {
        adapter = new AddressLocationAdapter(addresses, this, this);
        recyclerViewAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewAddress.setAdapter(adapter);

        AddressSearchAdapter adapter1 = new AddressSearchAdapter(addressSearch, this, this);
        searchAdapter = new LoadMoreAdapter(this,adapter1);
        searchAdapter.setLoadMoreListener(this);
        recyclerViewSearchAddress.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewSearchAddress.setAdapter(searchAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initMap();
    }

    private void initMap() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
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
        //反编译地址查询
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);
    }

    @OnClick({R.id.tv_location_city, R.id.iv_refresh, R.id.tv_search_name,R.id.tv_cancel_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_location_city:
                break;
            case R.id.iv_refresh:
                break;
            case R.id.tv_search_name:
                isSearch = true;
                layoutSelectAddress.setVisibility(View.GONE);
                break;
            case R.id.tv_cancel_search:
                isSearch = false;
                layoutSelectAddress.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void setListener() {
        etSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //检索查询地址
                query = new PoiSearch.Query(s.toString(), "");
                //keyWord表示搜索字符串，
                //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
                //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
                query.setPageSize(10);// 设置每页最多返回多少条poiitem
                query.setPageNum(1);//设置查询页码
                pageNo = 1;
                poiSearch = new PoiSearch(DeliveryAddressActivity.this, query);
                poiSearch.setOnPoiSearchListener(poiSearchImp);
                poiSearch.searchPOIAsyn();
            }
        });
    }

    /**
     * 检索查询地址
     */
    private PoiSearch.OnPoiSearchListener poiSearchImp = new PoiSearch.OnPoiSearchListener() {

        @Override
        public void onPoiSearched(PoiResult poiResult, int a) {
            List<PoiItem> pois = poiResult.getPois();//获取周围兴趣点
            if (pageNo == 1) {
                addressSearch.clear();
                searchAdapter.resetLoadState();
            }
            if (pois == null || pois.size() == 0) {
                searchAdapter.loadAllDataCompleted();
                return;
            }
            for (int i = 0; i < pois.size(); i++) {
                String title = pois.get(i).getTitle();
                String adName = pois.get(i).getAdName();
                String snippet = pois.get(i).getSnippet();
                LatLonPoint latLonPoint = pois.get(i).getLatLonPoint();
                double latitude = latLonPoint.getLatitude();
                double longitude = latLonPoint.getLongitude();
                Address addressInfo = new Address();
                addressInfo.title = title;
                addressInfo.address = snippet;
                addressInfo.latLng = new LatLng(latitude, longitude);
                if (pageNo == 1 && i == 0) {
                    tvCurrentAddress.setText(title);
                }
                addressSearch.add(addressInfo);
                String ps = ",title=" + title + ",adName=" + adName + ",snippet=" + snippet;
                Log.d("point", "point = " + ps);
            }
            searchAdapter.loadCompleted();
        }

        @Override
        public void onPoiItemSearched(PoiItem poiItem, int i) {

        }
    };

    /**
     * 通过经纬度来获取附近兴趣点
     * @param lat
     * @param lng
     * @param radius
     */
    private void regeocodeSearch(double lat, double lng, float radius) {
        LatLonPoint point = new LatLonPoint(lat, lng);

        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(point, radius, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {
        if (1000 == rCode) {
            RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
            String city = address.getCity();
            String subLoc = address.getDistrict();//区或县或县级市
            Log.d("city", "city = " + city + "，subLoc = " + subLoc);
            if (!TextUtils.isEmpty(city)) {
                tvLocationCity.setText(city);
            } else if (!TextUtils.isEmpty(subLoc)) {
                tvLocationCity.setText(subLoc);
            }
            List<PoiItem> pois = address.getPois();//获取周围兴趣点
            if (pois != null && pois.size() > 0) {
                addresses.clear();
                addressSearch.clear();
                for (int i = 0; i < pois.size(); i++) {
                    String title = pois.get(i).getTitle();
                    String adName = pois.get(i).getAdName();
                    String snippet = pois.get(i).getSnippet();
                    LatLonPoint latLonPoint = pois.get(i).getLatLonPoint();
                    double latitude = latLonPoint.getLatitude();
                    double longitude = latLonPoint.getLongitude();
                    Address addressInfo = new Address();
                    addressInfo.title = title;
                    addressInfo.address = snippet;
                    addressInfo.latLng = new LatLng(latitude, longitude);
                    if (i == 0) {
                        addressInfo.status = 1;
                        tvCurrentAddress.setText(title);
                    }
                    addresses.add(addressInfo);
                    addressSearch.add(addressInfo);
                    String ps = ",title=" + title + ",adName=" + adName + ",snippet=" + snippet;
                    Log.d("point", "point = " + ps);
                }
                adapter.notifyDataSetChanged();
                searchAdapter.loadAllDataCompleted();
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onClick(View v) {
        Integer position = (Integer) v.getTag();
        Intent intent = new Intent();
        intent.putExtra("address", isSearch?addressSearch.get(position).title:addresses.get(position).title);
        intent.putExtra("pointLat", isSearch?addressSearch.get(position).latLng.latitude:addresses.get(position).latLng.latitude);
        intent.putExtra("pointLon", isSearch?addressSearch.get(position).latLng.longitude:addresses.get(position).latLng.longitude);
        setResult(1002, intent);
        finish();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mLocationClient != null) {
            mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
        }
    }

    @Override
    public void loadMore() {
        pageNo += 1;
        query.setPageNum(pageNo);//设置查询页码
        poiSearch.searchPOIAsyn();
    }
}
