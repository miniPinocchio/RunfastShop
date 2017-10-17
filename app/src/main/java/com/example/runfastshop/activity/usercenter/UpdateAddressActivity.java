package com.example.runfastshop.activity.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.services.geocoder.RegeocodeAddress;
import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.Address;
import com.example.runfastshop.bean.address.AddressInfo;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 地址新增和修改
 */
public class UpdateAddressActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_phone)
    EditText etUserPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_house_number)
    EditText etHouseNumber;
    @BindView(R.id.btn_delete_address)
    Button mBtnDeleteAddress;
    private String mUserName;
    private String mUserPhone;
    private String mHouseNumber;
    private RegeocodeAddress mRegeocodeAddress;
    private Address mAddressLat;
    private String mAddress;
    private int mFlags;
    private AddressInfo mAddressInfo;
    private int mNetType;
    private String mTvAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_address);
        ButterKnife.bind(this);
        mFlags = getIntent().getFlags();
        if (mFlags == 1) {
            mAddressInfo = getIntent().getParcelableExtra("addressInfo");
            etUserName.setText(mAddressInfo.getName());
            etUserPhone.setText(mAddressInfo.getPhone());
            etHouseNumber.setText(mAddressInfo.getAddress());
            tvAddress.setText(mAddressInfo.getUserAddress());
        } else {
            mBtnDeleteAddress.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (mFlags == 0) {
            tvToolbarTitle.setText("新增地址");
        }
    }

    @OnClick({R.id.layout_select_address, R.id.btn_save_address, R.id.btn_delete_address})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_select_address:
                startActivityForResult(new Intent(this, AddressManagerActivity.class), 1001);
                break;
            case R.id.btn_save_address:
                if (!checkAvailable()) {
                    if (mFlags == 0) {
                        toAddAddress();
                    } else {
                        editAddress();
                    }
                }
                break;
            case R.id.btn_delete_address:
                deleteAddress();
                break;
        }
    }

    private boolean checkAvailable() {
        mUserName = etUserName.getText().toString();
        mUserPhone = etUserPhone.getText().toString();
        mHouseNumber = etHouseNumber.getText().toString();
        mTvAddress = tvAddress.getText().toString();
        if (TextUtils.isEmpty(mUserName)) {
            CustomToast.INSTANCE.showToast(this, "请填入收货人名称");
            return true;
        } else if (TextUtils.isEmpty(mUserPhone)) {
            CustomToast.INSTANCE.showToast(this, "请填入手机号");
            return true;
        } else if (TextUtils.isEmpty(mHouseNumber)) {
            CustomToast.INSTANCE.showToast(this, "请填入门牌号");
            return true;
        } else if (TextUtils.isEmpty(mTvAddress)) {
            CustomToast.INSTANCE.showToast(this, "请选择地址");
            return true;
        }
        return false;
    }

    /**
     * 修改地址
     */
    private void editAddress() {
        mNetType = 1;
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        if (mAddressLat != null) {
            CustomApplication.getRetrofit().postEditAddress(mAddressInfo.getId(), userInfo.getId()
                    , mUserName, mUserPhone, mAddress,
                    mHouseNumber, String.valueOf(mAddressLat.latLng.longitude), String.valueOf(mAddressLat.latLng.latitude),
                    mRegeocodeAddress.getProvince(), mRegeocodeAddress.getCity(), mRegeocodeAddress.getDistrict())
                    .enqueue(this);
        } else {
            CustomApplication.getRetrofit().postEditAddress(mAddressInfo.getId(), userInfo.getId()
                    , mUserName, mUserPhone, mAddress,
                    mHouseNumber, String.valueOf(mAddressInfo.getLongitude()), String.valueOf(mAddressInfo.getLatitude()),
                    mAddressInfo.getProvinceName(), mAddressInfo.getCityName(), mAddressInfo.getCountyName())
                    .enqueue(this);
        }
    }


    /**
     * 删除地址
     */
    private void deleteAddress() {
        mNetType = 2;
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null){
            return;
        }
        CustomApplication.getRetrofit().postDeleteAddress(mAddressInfo.getId(),userInfo.getId()).enqueue(this);
    }

    /**
     * 新增地址
     */
    private void toAddAddress() {
        mNetType = 0;
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null){
            return;
        }
//        Integer id = UserService.getUserInfo().getId();
        CustomApplication.getRetrofit().postAddAddress(userInfo.getId(), mUserName, mUserPhone, mAddress,
                mHouseNumber, String.valueOf(mAddressLat.latLng.longitude), String.valueOf(mAddressLat.latLng.latitude),
                mRegeocodeAddress.getProvince(), mRegeocodeAddress.getCity(), mRegeocodeAddress.getDistrict())
                .enqueue(this);
//        CustomToast.INSTANCE.showToast(this,"添加成功");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        mAddress = data.getStringExtra("address");
        mRegeocodeAddress = data.getParcelableExtra("addressInfo");
        mAddressLat = data.getParcelableExtra("addressLat");
        tvAddress.setText(mAddress);
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
        CustomToast.INSTANCE.showToast(this, "网络错误");
    }

    private void ResolveData(String data) {
        if (!TextUtils.isEmpty(data)) {
            if (mNetType == 0 || mNetType == 1) {
                try {
                    JSONObject object = new JSONObject(data);
                    boolean success = object.optBoolean("success");
                    String msg = object.optString("msg");
                    CustomToast.INSTANCE.showToast(this, "保存成功");
                    if (success) {
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (mNetType == 2) {
                CustomToast.INSTANCE.showToast(this, "删除成功");
                finish();
            }
        }
    }
}
