package com.example.runfastshop.activity.usercenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.adapter.EnshrineAdapter;
import com.example.runfastshop.adapter.LoadMoreAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.BusinessInfo;
import com.example.runfastshop.bean.enshrien.Enshrine;
import com.example.runfastshop.bean.enshrien.Enshrines;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyEnshrineActivity extends ToolBarActivity implements View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate, LoadMoreAdapter.LoadMoreApi, Callback<String> {

    @BindView(R.id.recycler_my_enshrine)
    RecyclerView recyclerViewList;
    @BindView(R.id.rl_my_enshrine)
    BGARefreshLayout mRefreshLayout;

    private LoadMoreAdapter loadMoreAdapter;
    private List<Enshrine> mEnshrines;
    private List<BusinessInfo> mBusinessInfos;
    private int page = 1;
    private EnshrineAdapter mEnshrineAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_enshrine);
        ButterKnife.bind(this);
        initData();
        getEnshrineData();
        initRefreshLayout();
        setData();
    }

    private void getEnshrineData() {
        User userInfo = UserService.getUserInfo(this);
        if (userInfo !=null) {
            CustomApplication.getRetrofit().getEnshrine(userInfo.getId()).enqueue(this);
        }
    }


    private void initData() {
        mEnshrines = new ArrayList<>();
        mBusinessInfos = new ArrayList<>();
        mEnshrineAdapter = new EnshrineAdapter(mEnshrines, this, this);
        loadMoreAdapter = new LoadMoreAdapter(this, mEnshrineAdapter);
        loadMoreAdapter.setLoadMoreListener(this);
    }

    private void setData() {
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewList.setAdapter(loadMoreAdapter);
    }


    private void initRefreshLayout() {
        // 为BGARefreshLayout 设置代理
        mRefreshLayout.setDelegate(this);
        BGAMeiTuanRefreshViewHolder meiTuanRefreshViewHolder = new BGAMeiTuanRefreshViewHolder(this, true);
        meiTuanRefreshViewHolder.setPullDownImageResource(R.mipmap.bga_refresh_mt_pull_down);
        meiTuanRefreshViewHolder.setChangeToReleaseRefreshAnimResId(R.drawable.bga_refresh_mt_change_to_release_refresh);
        meiTuanRefreshViewHolder.setRefreshingAnimResId(R.drawable.bga_refresh_mt_refreshing);
        mRefreshLayout.setRefreshViewHolder(meiTuanRefreshViewHolder);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mEnshrines.clear();
        getEnshrineData();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    @Override
    public void loadMore() {
        page += 1;
        getEnshrineData();
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        Log.d("params","Success = "+response.isSuccessful());
        if (response.isSuccessful()) {
            Log.d("params","response = "+data);
            ResolveData(data);
            return;
        }
        mRefreshLayout.endRefreshing();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        mRefreshLayout.endRefreshing();
        CustomToast.INSTANCE.showToast(this, "网络异常");
    }

    private void ResolveData(String data) {
        if (data != null) {
            Enshrines enshrines = GsonUtil.parseJsonWithGson(data, Enshrines.class);
            if (enshrines == null || enshrines.getEnshrine() == null || enshrines.getEnshrine().size() <= 0){
                loadMoreAdapter.loadAllDataCompleted();
                return;
            }
            mEnshrines.addAll(enshrines.getEnshrine());
            loadMoreAdapter.loadAllDataCompleted();
            mRefreshLayout.endRefreshing();
        }
    }
}
