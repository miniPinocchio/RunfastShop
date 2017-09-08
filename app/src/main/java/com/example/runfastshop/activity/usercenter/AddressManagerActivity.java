package com.example.runfastshop.activity.usercenter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
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
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.adapter.AddressManagerAdapter;
import com.example.runfastshop.adapter.AddressSearchAdapter;
import com.example.runfastshop.adapter.LoadMoreAdapter;
import com.example.runfastshop.bean.Address;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收货地址管理
 */
public class AddressManagerActivity extends ToolBarActivity implements AMap.OnMyLocationChangeListener, AMap.OnCameraChangeListener, GeocodeSearch.OnGeocodeSearchListener, View.OnClickListener, LoadMoreAdapter.LoadMoreApi {

    @BindView(R.id.map)
    MapView mMapView;
    AMap aMap;
    MyLocationStyle myLocationStyle;
    @BindView(R.id.iv_sign)
    ImageView ivSign;
    @BindView(R.id.view_address_list)
    RecyclerView recyclerView;

    @BindView(R.id.tv_location_city)
    TextView tvLocationCity;

    @BindView(R.id.et_search_name)
    EditText etSearchName;
    @BindView(R.id.tv_current_address)
    TextView tvCurrentAddress;
    @BindView(R.id.view_nearby_address)
    RecyclerView recyclerViewNearby;
    @BindView(R.id.layout_address_title)
    LinearLayout layoutAddressTitle;

    private List<Address> addresses = new ArrayList<>();
    private List<Address> addressSearch = new ArrayList<>();
    private boolean isFirstLocation = true;
    private AddressManagerAdapter managerAdapter;
    private Location myLocation;
    private LoadMoreAdapter searchAdapter;
    private GeocodeSearch geocodeSearch;
    private PoiSearch poiSearch;
    private boolean isSearch;
    private PoiSearch.Query query;
    private int pageNo = 1;
    private RegeocodeAddress mAddress;
    private Address mAddressInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manager);
        ButterKnife.bind(this);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        //这里以ACCESS_COARSE_LOCATION为例
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1001);//自定义的code
        }else {
            initMap();
        }
        initData();
        setData();
        setListener();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        initMap();
    }

    private void setData() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewNearby.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(managerAdapter);
        recyclerViewNearby.setAdapter(searchAdapter);
    }

    private void initData() {
        managerAdapter = new AddressManagerAdapter(addresses, this, this);
        AddressSearchAdapter adapter = new AddressSearchAdapter(addressSearch, this, this);
        searchAdapter = new LoadMoreAdapter(this,adapter);
        searchAdapter.setLoadMoreListener(this);
    }

    private void initMap() {

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        myLocationStyle.showMyLocation(true);//设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.icon_mylocation)));//
        myLocationStyle.anchor(0.5f, 0.5f);
        myLocationStyle.strokeColor(Color.argb(180, 63, 157, 226));
        myLocationStyle.radiusFillColor(Color.argb(100, 148, 200, 239));

        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.getUiSettings().setZoomControlsEnabled(false);
        //反编译地址查询
        geocodeSearch = new GeocodeSearch(this);
        geocodeSearch.setOnGeocodeSearchListener(this);

    }

    private void setListener() {
        aMap.setOnMyLocationChangeListener(this);
        aMap.setOnCameraChangeListener(this);
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
                poiSearch = new PoiSearch(AddressManagerActivity.this, query);
                poiSearch.setOnPoiSearchListener(poiSearchImp);
                poiSearch.searchPOIAsyn();
            }
        });
    }

    @Override
    public void loadMore() {
        pageNo += 1;
        query.setPageNum(pageNo);//设置查询页码
        poiSearch.searchPOIAsyn();
    }

    private PoiSearch.OnPoiSearchListener poiSearchImp  = new  PoiSearch.OnPoiSearchListener(){

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
                Log.d("地址信息", addressInfo.toString());
                Log.d("地址信息", ps);
            }
            searchAdapter.loadCompleted();
        }

        @Override
        public void onPoiItemSearched(PoiItem poiItem, int i) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    /**
     * 定位发生变化
     *
     * @param location
     */
    @Override
    public void onMyLocationChange(Location location) {
        if (location != null) {
            Log.d("location", "lat = " + location.getLatitude() + "，lng = " + location.getLongitude());
            if (isFirstLocation) {
                isFirstLocation = false;
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 12));
            }
            myLocation = location;
        }
    }

    /**
     * 地图状态发生变化
     *
     * @param cameraPosition
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.anim_location_icon);
        ivSign.startAnimation(animation);
        regeocodeSearch(cameraPosition.target.latitude, cameraPosition.target.longitude, 3000);
    }

    private void regeocodeSearch(double lat, double lng, float radius) {
        LatLonPoint point = new LatLonPoint(lat, lng);

        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(point, radius, GeocodeSearch.AMAP);
        geocodeSearch.getFromLocationAsyn(regeocodeQuery);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int rCode) {

        if (1000 == rCode) {
            mAddress = regeocodeResult.getRegeocodeAddress();
            String city = mAddress.getCity();
            String subLoc = mAddress.getDistrict();//区或县或县级市
            Log.d("city", "city = " + city + "，subLoc = " + subLoc);
            if (!TextUtils.isEmpty(city)) {
                tvLocationCity.setText(city);
            } else if (!TextUtils.isEmpty(subLoc)) {
                tvLocationCity.setText(subLoc);
            }
            List<PoiItem> pois = mAddress.getPois();//获取周围兴趣点
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
                    mAddressInfo = new Address();
                    mAddressInfo.title = title;
                    mAddressInfo.address = snippet;
                    mAddressInfo.latLng = new LatLng(latitude, longitude);
                    if (i == 0) {
                        mAddressInfo.status = 1;
                        tvCurrentAddress.setText(title);
                    }
                    addresses.add(mAddressInfo);
                    addressSearch.add(mAddressInfo);
                    String ps = ",title=" + title + ",adName=" + adName + ",snippet=" + snippet;
                    Log.d("地址信息", mAddressInfo.toString());
                    Log.d("地址信息", ps);
                }
                managerAdapter.notifyDataSetChanged();
                searchAdapter.loadAllDataCompleted();
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    /**
     * 地址选择
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v != null) {
            Integer position = (Integer) v.getTag();
            Intent intent = new Intent();
            intent.putExtra("address", isSearch?addressSearch.get(position).title:addresses.get(position).title);
            intent.putExtra("addressInfo",mAddress);
            intent.putExtra("addressLat",mAddressInfo);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_refresh, R.id.layout_address_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                isSearch = false;
                layoutAddressTitle.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_refresh:
                break;
            case R.id.layout_address_name:
                isSearch = true;
                layoutAddressTitle.setVisibility(View.GONE);
                regeocodeSearch(myLocation.getLatitude(), myLocation.getLongitude(), 3000);
                break;
        }
    }
}
