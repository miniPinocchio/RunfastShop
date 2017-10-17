package com.example.runfastshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.bean.BusinessExercise;
import com.example.runfastshop.bean.BusinessInfo;
import com.example.runfastshop.config.NetConfig;
import com.example.runfastshop.impl.constant.UrlConstant;

import org.xutils.x;

import java.text.DecimalFormat;
import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by 天上白玉京 on 2017/7/28.
 */

public class BreakfastAdapter extends RecyclerView.Adapter<BreakfastAdapter.BreakfastViewHolder> {

    private List<BusinessInfo> data;

    private Context context;

    private View.OnClickListener listener;

    public BreakfastAdapter(List<BusinessInfo> data, Context context, View.OnClickListener listener) {
        this.data = data;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public BreakfastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_business_info, parent, false);
        BreakfastViewHolder viewHolder = new BreakfastViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BreakfastViewHolder holder, int position) {
        BusinessInfo businessInfo = data.get(position);
        if (businessInfo != null) {
            x.image().bind(holder.ivBusinessLogo, UrlConstant.ImageBaseUrl + businessInfo.mini_imgPath, NetConfig.optionsPagerCache);
            holder.tvBusinessName.setText(businessInfo.name);
            holder.tvSaleDistance.setText(String.valueOf(new DecimalFormat("#0.0").format(businessInfo.distance)) + "km");
            holder.tvBusinessLevel.setText(String.valueOf(businessInfo.levelId));
            holder.tvBusinessSaleNum.setText("月售" + String.valueOf(businessInfo.salesnum) + "单");
            holder.tvSaleStartPay.setText(businessInfo.startPay == null ? "¥ 0元起送" : "¥ " + String.valueOf(businessInfo.startPay) + "起送");

            holder.tvSaleTime.setText(businessInfo.speed);
            if (businessInfo.isDeliver == 0) {
                holder.tvSalePrice.setText(businessInfo.baseCharge == null ? "配送费¥0" : "配送费¥" + String.valueOf(businessInfo.baseCharge));
                holder.ivCharge.setVisibility(View.VISIBLE);
            } else {
                holder.ivCharge.setVisibility(View.GONE);
                holder.tvSalePrice.setText(businessInfo.busshowps == null ? "配送费¥0" : "配送费¥" + String.valueOf(businessInfo.busshowps));
            }

            List<BusinessExercise> alist = businessInfo.alist;

            if (alist != null) {
                int size = alist.size();
                if (size > 0) {
                    for (int i = 0; i < alist.size(); i++) {
                        BusinessExercise exercise = alist.get(i);
                        switch (exercise.ptype) {
                            case 1:
                                holder.layoutSubPrice.setVisibility(View.VISIBLE);
                                holder.tvSubPrice.setText(exercise.showname);
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                holder.layoutDiscount.setVisibility(View.VISIBLE);
                                holder.tvDiscount.setText(exercise.showname);
                                break;
                        }
                    }
                }
            }

            holder.layoutItem.setTag(position);
            holder.layoutItem.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class BreakfastViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout layoutItem;
        public ImageView ivBusinessLogo, ivCharge;
        public TextView tvBusinessName, tvBusinessLevel, tvBusinessSaleNum, tvSaleDistance, tvSaleTime, tvSaleStartPay, tvSalePrice;

        //活动页
        public LinearLayout layoutSubPrice, layoutDiscount;
        public TextView tvSubPrice, tvDiscount;

        public BreakfastViewHolder(View itemView) {
            super(itemView);
            layoutItem = (LinearLayout) itemView.findViewById(R.id.layout_breakfast_item);

            ivBusinessLogo = (ImageView) itemView.findViewById(R.id.iv_business_logo);
            ivCharge = (ImageView) itemView.findViewById(R.id.iv_is_charge);

            tvBusinessName = (TextView) itemView.findViewById(R.id.tv_business_name);
            tvBusinessLevel = (TextView) itemView.findViewById(R.id.tv_business_levelId);
            tvBusinessSaleNum = (TextView) itemView.findViewById(R.id.tv_business_sales_num);
            tvSaleDistance = (TextView) itemView.findViewById(R.id.tv_sale_distance);
            tvSaleTime = (TextView) itemView.findViewById(R.id.tv_sale_time);
            tvSaleStartPay = (TextView) itemView.findViewById(R.id.tv_sale_startPay);
            tvSalePrice = (TextView) itemView.findViewById(R.id.tv_sale_price);
            tvSubPrice = (TextView) itemView.findViewById(R.id.tv_sub_price);
            tvDiscount = (TextView) itemView.findViewById(R.id.tv_discount);

            layoutSubPrice = (LinearLayout) itemView.findViewById(R.id.layout_sub_price);
            layoutDiscount = (LinearLayout) itemView.findViewById(R.id.layout_discount);
        }
    }
}
