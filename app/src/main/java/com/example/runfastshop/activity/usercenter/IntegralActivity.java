package com.example.runfastshop.activity.usercenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.adapter.moneyadapter.ScoreRecordAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.score.MyScore;
import com.example.runfastshop.bean.score.MyScores;
import com.example.runfastshop.config.UserService;
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
 * 积分
 */
public class IntegralActivity extends ToolBarActivity implements Callback<String>, View.OnClickListener {

    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.recycler_integral_detail)
    RecyclerView recyclerView;

    private List<MyScore> mMyScores;
    private ScoreRecordAdapter mAllAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
        ButterKnife.bind(this);
        initData();
        getNetData();
    }

    private void getNetData() {
        Integer id = UserService.getUserInfo().getId();
        CustomApplication.getRetrofit().getListScore(id).enqueue(this);
    }

    private void initData() {
        mMyScores = new ArrayList<>();
        mAllAdapter = new ScoreRecordAdapter(mMyScores, this, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAllAdapter);
    }

    @OnClick(R.id.layout_integral_rule)
    public void onViewClicked() {
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
        MyScores scores = GsonUtil.parseJsonWithGson(data, MyScores.class);
        if (scores.getRows().size() > 0) {
            mMyScores.addAll(scores.getRows());
            mAllAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
