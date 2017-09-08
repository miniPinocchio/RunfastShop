package com.example.runfastshop.adapter.moneyadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.runfastshop.R;

import java.util.List;

/**
 * 收支明细全部适配
 * Created by 天上白玉京 on 2017/8/5.
 */

public class CashRecordAdapter extends RecyclerView.Adapter<CashRecordAdapter.CashRecordViewHolder>{

    private List<String> data;

    private Context context;

    public CashRecordAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public CashRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_money_detail,parent,false);
        CashRecordViewHolder holder = new CashRecordViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CashRecordViewHolder holder, int position) {
        holder.tvMoney.setTextColor(context.getResources().getColor(R.color.color_money_green));
    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    public class CashRecordViewHolder extends RecyclerView.ViewHolder{

        public TextView tvMoney;

        public CashRecordViewHolder(View itemView) {
            super(itemView);
            tvMoney = (TextView) itemView.findViewById(R.id.tv_money);
        }
    }
}
