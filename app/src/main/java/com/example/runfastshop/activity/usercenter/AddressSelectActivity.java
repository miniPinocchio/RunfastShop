package com.example.runfastshop.activity.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.adapter.AddressSelectAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.address.AddressInfo;
import com.example.runfastshop.bean.address.AddressInfos;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.IntentConfig;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.data.IntentFlag;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 收货地址管理
 */
public class AddressSelectActivity extends ToolBarActivity implements Callback<String>,AddressSelectAdapter.OnItemClickListener {

    @BindView(R.id.recycler_address_list)
    RecyclerView recyclerView;

    private List<AddressInfo> mData;
    private AddressSelectAdapter mSelectAdapter;
    private int mFlags;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_select);
        ButterKnife.bind(this);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getNetData();
    }

    private void initData() {
        mFlags = getIntent().getFlags();
        mData = new ArrayList<>();
        mSelectAdapter = new AddressSelectAdapter(mData, this);
        mSelectAdapter.setOnItemClickListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mSelectAdapter);
    }

    @OnClick(R.id.layout_add_address)
    public void onViewClicked() {
        Intent intent = new Intent(this, UpdateAddressActivity.class);
        intent.setFlags(IntentFlag.ADD_ADDRESS);
        startActivity(intent);
    }

    private void getNetData() {
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        CustomApplication.getRetrofit().postListAddress(userInfo.getId()).enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            Log.d("params", "response = " + data);
            ResolveData(data);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        CustomToast.INSTANCE.showToast(this, "网络异常");
    }

    private void ResolveData(String data) {
        //处理数据 显示地址
        mData.clear();
        AddressInfos addressInfos = GsonUtil.parseJsonWithGson(data, AddressInfos.class);
        if (addressInfos.getRows().size() > 0) {
            mData.addAll(addressInfos.getRows());
            mSelectAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        AddressInfo addressInfo = mData.get(position);
        if (mFlags == IntentFlag.ORDER_ADDRESS){
            Intent intent = new Intent();
            intent.putExtra("addressInfo",addressInfo);
            setResult(IntentConfig.ADDRESS_SELECT,intent);
            finish();
        }else if (mFlags == IntentFlag.MANAGER_ADDRESS){
            Intent intent = new Intent(this, UpdateAddressActivity.class);
            intent.setFlags(IntentFlag.EDIT_ADDRESS);
            intent.putExtra("addressInfo", addressInfo);
            startActivity(intent);
        }
    }
}
