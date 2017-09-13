package com.example.runfastshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.adapter.BreakfastAdapter;
import com.example.runfastshop.adapter.LoadMoreAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.BusinessExercise;
import com.example.runfastshop.bean.BusinessInfo;
import com.example.runfastshop.data.IntentFlag;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.view.ZFlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProductActivity extends ToolBarActivity implements Callback<String>, LoadMoreAdapter.LoadMoreApi, View.OnClickListener, BGARefreshLayout.BGARefreshLayoutDelegate {

    @BindView(R.id.et_search_name)
    EditText etSearchName;
    @BindView(R.id.flow_layout)
    ZFlowLayout flowLayout;
    @BindView(R.id.rl_search_refresh)
    BGARefreshLayout mRefreshLayout;
    @BindView(R.id.view_search_list)
    RecyclerView recyclerViewList;
    @BindView(R.id.ll_search_history)
    LinearLayout search_history;

    private List<String> data = new ArrayList<>();
    private List<BusinessInfo> businessInfos = new ArrayList<>();
    private LoadMoreAdapter loadMoreAdapter;
    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        ButterKnife.bind(this);
        initData();
        initRefreshLayout();
        setListener();
        setData();
    }


    private void setData() {
        recyclerViewList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewList.setAdapter(loadMoreAdapter);
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
                businessInfos.clear();
                if (!TextUtils.isEmpty(s.toString())) {
                    searchGoods(s.toString());
                }
                loadMoreAdapter.notifyDataSetChanged();
            }
        });
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

    private void initData() {
        mRefreshLayout.setVisibility(View.GONE);
        data.add("鸡排");
        data.add("咖喱");
        data.add("麻辣烫");
        data.add("泡菜饼");
        data.add("肯德基宅急送");
        data.add("烧烤");
        data.add("烧烤");
        addItem(data);

        BreakfastAdapter breakfastAdapert = new BreakfastAdapter(businessInfos, this, this);
        loadMoreAdapter = new LoadMoreAdapter(this, breakfastAdapert);
        loadMoreAdapter.setLoadMoreListener(this);
    }

    @SuppressWarnings("ResourceType")
    public void addItem(List<String> data) {

        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 10, 10);// 设置边距

        for (int i = 0; i < data.size(); i++) {
            TextView textView = new TextView(this);
            textView.setText(data.get(i));
            textView.setBackgroundResource(R.drawable.search_history_background);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setTextColor(getResources().getColor(R.color.color_text_gray_two));
            //TODO:能否添加成功
            flowLayout.addView(textView, layoutParams);
        }
    }

    /**
     * 搜索商品
     */
    private void searchGoods(String name) {
        CustomApplication.getRetrofit().searchGoods(1, 10, 110.0, 23.0, name, 1, 4).enqueue(this);
    }

    @OnClick(R.id.tv_cancel_search)
    public void onViewClicked() {
        finish();
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
        mRefreshLayout.endRefreshing();
        CustomToast.INSTANCE.showToast(this, "网络异常");
    }

    private void ResolveData(String data) {
        try {
            JSONObject object = new JSONObject(data);
            JSONArray bus = object.getJSONArray("bus");
            if (bus == null || bus.length() <= 0) {
                mRefreshLayout.endRefreshing();
                loadMoreAdapter.loadAllDataCompleted();
                return;
            }
            search_history.setVisibility(View.GONE);
            mRefreshLayout.setVisibility(View.VISIBLE);
            if (page == 1) {
                businessInfos.clear();
                loadMoreAdapter.resetLoadState();
            }

            int length = bus.length();
            for (int i = 0; i < length; i++) {
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
            loadMoreAdapter.loadCompleted();
            mRefreshLayout.endRefreshing();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void loadMore() {
        page += 1;
        searchGoods(etSearchName.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_breakfast_item:
                Integer positionBusiness = (Integer) v.getTag();
                Intent intent = new Intent(this, BusinessActivity.class);
                intent.setFlags(IntentFlag.SEARCH_VIEW);
                intent.putExtra("search", businessInfos.get(positionBusiness).id);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        page = 1;
        searchGoods(etSearchName.getText().toString());
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }
}
