package com.example.runfastshop.activity.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.activity.usercenter.AddBankActivity;
import com.example.runfastshop.adapter.moneyadapter.SelectBankAdapter;
import com.example.runfastshop.bean.BankSelectInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择银行卡
 */
public class SelectBankActivity extends ToolBarActivity implements View.OnClickListener {

    @BindView(R.id.recycler_bank_list)
    RecyclerView recyclerView;

    private List<BankSelectInfo> data = new ArrayList<>();
    private SelectBankAdapter bankAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bank);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        BankSelectInfo bank = new BankSelectInfo();
        bank.name = "农业银行(1234)";
        bank.isSelect = true;
        data.add(bank);
        bank = new BankSelectInfo();
        bank.name = "工商银行(1234)";
        data.add(bank);
        bank = new BankSelectInfo();
        bank.name = "使用新卡提现";
        data.add(bank);

        bankAdapter = new SelectBankAdapter(data,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(bankAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            Integer position = (Integer) v.getTag();
            if (position == data.size() -1){
                startActivity(new Intent(this,AddBankActivity.class));
                return;
            }
            for (int i = 0; i < data.size(); i++) {
                data.get(i).isSelect = false;
            }
            data.get(position).isSelect = true;
            bankAdapter.notifyDataSetChanged();

        }
    }
}
