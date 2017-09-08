package com.example.runfastshop.adapter.moneyadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.bean.BalanceInfo;

import java.util.List;

/**
 * 代金券适配
 * Created by 天上白玉京 on 2017/8/5.
 */

public class CashCouponAdapter extends RecyclerView.Adapter<CashCouponAdapter.CashCouponViewHolder>{

    private List<String> data;

    private Context context;

    public CashCouponAdapter(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public CashCouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cash_coupon_info,parent,false);
        CashCouponViewHolder holder = new CashCouponViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CashCouponViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    public class CashCouponViewHolder extends RecyclerView.ViewHolder{


        public CashCouponViewHolder(View itemView) {
            super(itemView);

        }
    }
}
