package com.example.runfastshop.activity.usercenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.adapter.LoadMoreAdapter;
import com.example.runfastshop.adapter.moneyadapter.ScoreRecordAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.score.MyScore;
import com.example.runfastshop.bean.score.MyScores;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 积分
 */
public class IntegralActivity extends ToolBarActivity implements Callback<String>, View.OnClickListener, LoadMoreAdapter.LoadMoreApi {

    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.recycler_integral_detail)
    RecyclerView recyclerView;

    private List<MyScore> mMyScores;
    private User userInfo;
    private LoadMoreAdapter moreAdapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
        ButterKnife.bind(this);
        initData();
        getDataList();
    }

    /**
     * 积分明细获取
     */
    private void getDataList() {
        userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        String score = String.valueOf(userInfo.getScore());
        if (score.contains(".")){
            score = score.substring(0,score.indexOf("."));
        }
        tvIntegral.setText(score);
        CustomApplication.getRetrofit().getListScore(userInfo.getId(), page,10).enqueue(this);
    }

    private void initData() {
        mMyScores = new ArrayList<>();
        ScoreRecordAdapter mAllAdapter = new ScoreRecordAdapter(mMyScores, this, this);
        moreAdapter = new LoadMoreAdapter(this,mAllAdapter);
        moreAdapter.setLoadMoreListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(moreAdapter);
    }

    @OnClick(R.id.layout_integral_rule)
    public void onViewClicked() {
    }


    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        Log.d("params","isSuccessful = "+response.isSuccessful());
        if (response.isSuccessful()) {
            Log.d("params","response = "+data);
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
        if (!TextUtils.isEmpty(data)) {
            MyScores scores = GsonUtil.parseJsonWithGson(data, MyScores.class);
            if (scores.getRows() != null && scores.getRows().size() > 0) {
                mMyScores.addAll(scores.getRows());
                moreAdapter.loadCompleted();
            }else {
                moreAdapter.loadAllDataCompleted();
            }
        }
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void loadMore() {
        page += 1;
        getDataList();
    }
}
