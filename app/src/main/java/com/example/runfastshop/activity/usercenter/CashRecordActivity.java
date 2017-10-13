package com.example.runfastshop.activity.usercenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.adapter.CashRecordAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.spend.AccountRecord;
import com.example.runfastshop.bean.spend.AccountRecords;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 提现记录
 */
public class CashRecordActivity extends ToolBarActivity implements View.OnClickListener, Callback<String> {

    @BindView(R.id.view_money_list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_no_cash_record)
    TextView mTvNoCashRecord;

    private List<AccountRecord> mRecordList;
    private CashRecordAdapter mAllAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_record);
        ButterKnife.bind(this);
        initData();
        getNetData();
    }

    private void getNetData() {
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        CustomApplication.getRetrofit().getListConsume().enqueue(this);
    }

    private void initData() {
        mRecordList = new ArrayList<>();
        mAllAdapter = new CashRecordAdapter(mRecordList, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAllAdapter);
    }

    @Override
    public void onClick(View v) {

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


    /**
     * 解析数据
     *
     * @param data
     */
    private void ResolveData(String data) {
        AccountRecords accountRecords = GsonUtil.parseJsonWithGson(data, AccountRecords.class);
        if (accountRecords != null && accountRecords.getRows().size() > 0) {
            mRecordList.addAll(accountRecords.getRows());
            mTvNoCashRecord.setVisibility(View.GONE);
            mAllAdapter.notifyDataSetChanged();
        } else {
            mTvNoCashRecord.setVisibility(View.VISIBLE);
        }
    }
}