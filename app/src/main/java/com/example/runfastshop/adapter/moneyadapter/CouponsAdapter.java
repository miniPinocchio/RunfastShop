package com.example.runfastshop.adapter.moneyadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.runfastshop.R;

import java.util.List;

/**
 * 收支明细全部适配
 * Created by 天上白玉京 on 2017/8/5.
 */

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.CouponsViewHolder>{

    private List<String> data;

    private Context context;

    public CouponsAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public CouponsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coupon_info,parent,false);
        CouponsViewHolder holder = new CouponsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CouponsViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    public class CouponsViewHolder extends RecyclerView.ViewHolder{

        public CouponsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
