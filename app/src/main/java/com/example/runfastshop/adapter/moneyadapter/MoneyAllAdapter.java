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

public class MoneyAllAdapter extends RecyclerView.Adapter<MoneyAllAdapter.MoneyAllViewHolder> {

    private List<String> data;

    private Context context;

    private View.OnClickListener mListener;

    public MoneyAllAdapter(List<String> data, Context context, View.OnClickListener listener) {
        this.data = data;
        this.context = context;
        mListener = listener;
    }

    @Override
    public MoneyAllViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_money_detail, parent, false);
        MoneyAllViewHolder holder = new MoneyAllViewHolder(view);
        view.setOnClickListener(mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MoneyAllViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class MoneyAllViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_make_money_title;
        private TextView tv_make_money_time;
        private TextView tv_make_money_num;

        public MoneyAllViewHolder(View itemView) {
            super(itemView);
            tv_make_money_title = (TextView) itemView.findViewById(R.id.tv_make_money_title);
            tv_make_money_time = (TextView) itemView.findViewById(R.id.tv_make_money_time);
            tv_make_money_num = (TextView) itemView.findViewById(R.id.tv_make_money_num);
        }
    }
}
