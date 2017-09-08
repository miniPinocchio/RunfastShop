package com.example.runfastshop.activity.usercenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.adapter.moneyadapter.CashRecordAdapter;
import com.example.runfastshop.adapter.moneyadapter.MoneyAllAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 提现记录
 */
public class CashRecordActivity extends ToolBarActivity {

    @BindView(R.id.view_money_list)
    RecyclerView recyclerView;

    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_record);
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
}
