package com.example.runfastshop.activity.usercenter;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.adapter.moneyadapter.CouponsAdapter;
import com.example.runfastshop.adapter.moneyadapter.MoneyAllAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 优惠券
 */
public class CouponActivity extends ToolBarActivity {

    @BindView(R.id.view_coupon_list)
    RecyclerView recyclerView;

    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            data.add("1");
        }
        CouponsAdapter allAdapter = new CouponsAdapter(data,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(allAdapter);
    }
}
