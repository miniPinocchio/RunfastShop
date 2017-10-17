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
import com.example.runfastshop.bean.coupon.CouponInfo;

import java.math.BigDecimal;
import java.util.List;

/**
 * 代金券适配
 * Created by 天上白玉京 on 2017/8/5.
 */

public class CashCouponAdapter extends RecyclerView.Adapter<CashCouponAdapter.CashCouponViewHolder>{

    private List<CouponInfo> data;

    private Context context;

    private View.OnClickListener listener;

    public CashCouponAdapter(List<CouponInfo> data, Context context,View.OnClickListener listener) {
        this.data = data;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public CashCouponViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cash_coupon_info,parent,false);
        CashCouponViewHolder holder = new CashCouponViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CashCouponViewHolder holder, int position) {
        CouponInfo couponInfo = data.get(position);
        if (couponInfo != null) {
            holder.tv_price.setText(couponInfo.getPrice().toString());
            holder.tv_name.setText(couponInfo.getName());
            holder.tv_full.setText("满"+couponInfo.getFull()+"元可用");

            holder.tv_get_coupon.setTag(position);
            holder.tv_get_coupon.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    public class CashCouponViewHolder extends RecyclerView.ViewHolder{

        TextView tv_price,tv_name,tv_full,tv_get_coupon;

        public CashCouponViewHolder(View itemView) {
            super(itemView);
            tv_price = (TextView) itemView.findViewById(R.id.tv_price);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_full = (TextView) itemView.findViewById(R.id.tv_full);
            tv_get_coupon = (TextView) itemView.findViewById(R.id.tv_get_coupon);
        }
    }
}
