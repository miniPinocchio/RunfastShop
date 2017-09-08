package com.example.runfastshop.activity.usercenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.adapter.moneyadapter.CashRecordAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 积分
 */
public class IntegralActivity extends ToolBarActivity {

    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.recycler_integral_detail)
    RecyclerView recyclerView;

    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            data.add("1");
        }
        CashRecordAdapter allAdapter = new CashRecordAdapter(data,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(allAdapter);
    }

    @OnClick(R.id.layout_integral_rule)
    public void onViewClicked() {
    }
}
