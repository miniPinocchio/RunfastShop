package com.example.runfastshop.activity.usercenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.adapter.moneyadapter.BankListAdapter;
import com.example.runfastshop.adapter.moneyadapter.CouponsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BankActivity extends ToolBarActivity {

    @BindView(R.id.recycler_bank_list)
    RecyclerView recyclerView;

    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            data.add("1");
        }
        BankListAdapter bankListAdapter = new BankListAdapter(data,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(bankListAdapter);
    }

    @OnClick(R.id.layout_add_bank)
    public void onViewClicked() {
    }
}
