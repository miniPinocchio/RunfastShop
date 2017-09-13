package com.example.runfastshop.adapter.moneyadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.bean.coupon.CouponBean;

import java.util.List;

/**
 * 收支明细全部适配
 * Created by 天上白玉京 on 2017/8/5.
 */

public class CouponsAdapter extends RecyclerView.Adapter<CouponsAdapter.CouponsViewHolder> {

    private List<CouponBean> data;

    private Context context;

    private View.OnClickListener mListener;

    public CouponsAdapter(List<CouponBean> data, Context context, View.OnClickListener listener) {
        this.data = data;
        this.context = context;
        mListener = listener;
    }

    @Override
    public CouponsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coupon_info, parent, false);
        CouponsViewHolder holder = new CouponsViewHolder(view);
        view.setOnClickListener(mListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(CouponsViewHolder holder, int position) {
        CouponBean couponBean = data.get(position);
        holder.mTvCouponNumber.setText(String.valueOf(couponBean.getPrice()));
        holder.mTvCouponMin.setText("满" + String.valueOf(couponBean.getFull()) + "可用");
        //TODO 无配送类型字段
//        holder.mTvCouponDeliver.setText(String.valueOf(couponBean.getPrice()));

        holder.mTvCouponDate.setText(couponBean.getStart() + "至" + couponBean.getEnd());
        //TODO 已过期需要服务器返回 用户端存在手机系统时间非国际时间
        switch (couponBean.getUserd()) {
            case 1:
                holder.mTvCouponState.setText("已使用");
                break;
            case 0:
                holder.mTvCouponState.setText("未使用");
                break;
        }

        holder.mTvCouponLimitDate.setText(String.valueOf(couponBean.getPrice()));
        switch (couponBean.getRange1()) {
            case 1:
                holder.mTvCouponType.setText("通用优惠券");
                break;
            case 2:
                holder.mTvCouponType.setText("指定" + couponBean.getBusinessName());
                break;
            case 3:
                holder.mTvCouponType.setText("指定" + couponBean.getAgentName());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class CouponsViewHolder extends RecyclerView.ViewHolder {
        TextView mTvCouponNumber;
        TextView mTvCouponMin;
        TextView mTvCouponDeliver;
        TextView mTvCouponLimitDate;
        TextView mTvCouponState;
        TextView mTvCouponDate;
        TextView mTvCouponType;

        public CouponsViewHolder(View itemView) {
            super(itemView);
            mTvCouponNumber = (TextView) itemView.findViewById(R.id.tv_coupon_price);
            mTvCouponMin = (TextView) itemView.findViewById(R.id.tv_coupon_min);
            mTvCouponDeliver = (TextView) itemView.findViewById(R.id.tv_coupon_deliver);
            mTvCouponLimitDate = (TextView) itemView.findViewById(R.id.tv_coupon_limit_date);
            mTvCouponState = (TextView) itemView.findViewById(R.id.tv_coupon_state);
            mTvCouponDate = (TextView) itemView.findViewById(R.id.tv_coupon_date);
            mTvCouponType = (TextView) itemView.findViewById(R.id.tv_coupon_type);
        }
    }
}
