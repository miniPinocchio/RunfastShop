package com.example.runfastshop.adapter.moneyadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.runfastshop.R;

import java.util.List;

/**
 * 银行卡列表适配
 * Created by 天上白玉京 on 2017/8/5.
 */

public class BankListAdapter extends RecyclerView.Adapter<BankListAdapter.BankListViewHolder>{

    private List<String> data;

    private Context context;

    public BankListAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public BankListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bank_info,parent,false);
        BankListViewHolder holder = new BankListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(BankListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    public class BankListViewHolder extends RecyclerView.ViewHolder{

        public BankListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
