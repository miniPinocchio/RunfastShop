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
import com.example.runfastshop.bean.enshrien.Enshrine;
import com.example.runfastshop.config.NetConfig;
import com.example.runfastshop.impl.constant.UrlConstant;

import org.xutils.x;

import java.util.List;

import static java.lang.Double.NaN;

/**
 * Created by huiliu on 2017/9/14.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class EnshrineAdapter  extends RecyclerView.Adapter<EnshrineAdapter.EnshrineViewHolder> {

    private List<Enshrine> data;

    private Context context;

    private View.OnClickListener listener;

    public EnshrineAdapter(List<Enshrine> data, Context context, View.OnClickListener listener) {
        this.data = data;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public EnshrineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_enshrien, parent, false);
        EnshrineViewHolder viewHolder = new EnshrineViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(EnshrineViewHolder holder, int position) {
        Enshrine enshrine = data.get(position);
        if (enshrine != null) {
            x.image().bind(holder.ivBusinessLogo, UrlConstant.ImageBaseUrl + enshrine.imgPath, NetConfig.optionsPagerCache);
            holder.tvBusinessName.setText(enshrine.shopname);
//            holder.tvSaleDistance.setText(String.valueOf(new DecimalFormat("#0.0").format(enshrine.distance)) + "km");
//            holder.tvBusinessLevel.setText(String.valueOf(enshrine.levelId));
//            holder.tvBusinessSaleNum.setText("月售" + String.valueOf(enshrine.salesnum) + "单");
            holder.tvSaleStartPay.setText(enshrine.startPay == NaN ? "¥ 0元起送" : "¥ " + String.valueOf(enshrine.startPay) + "起送");
//            holder.tvSalePrice.setText(enshrine.baseCharge == NaN ? "配送费¥0" : "配送费¥" + String.valueOf(enshrine.baseCharge));
//            holder.tvSaleTime.setText(enshrine.speed);
//            if (enshrine.isDeliver == 0) {
//                holder.ivCharge.setVisibility(View.VISIBLE);
//            } else {
//                holder.ivCharge.setVisibility(View.GONE);
//            }

//            List<BusinessExercise> alist = enshrine.alist;
//
//            if (alist != null) {
//                int size = alist.size();
//                if (size > 0) {
//                    for (int i = 0; i < alist.size(); i++) {
//                        BusinessExercise exercise = alist.get(i);
//                        switch (exercise.ptype) {
//                            case 1:
//                                holder.layoutSubPrice.setVisibility(View.VISIBLE);
//                                holder.tvSubPrice.setText(exercise.showname);
//                                break;
//                            case 2:
//                                break;
//                            case 3:
//                                break;
//                            case 4:
//                                holder.layoutDiscount.setVisibility(View.VISIBLE);
//                                holder.tvDiscount.setText(exercise.showname);
//                                break;
//                        }
//                    }
//                }
//            }

            holder.layoutItem.setTag(position);
            holder.layoutItem.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    public class EnshrineViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout layoutItem;
        public ImageView ivBusinessLogo, ivCharge;
        public TextView tvBusinessName, tvBusinessLevel, tvBusinessSaleNum, tvSaleDistance, tvSaleTime, tvSaleStartPay, tvSalePrice;

        //活动页
        public LinearLayout layoutSubPrice, layoutDiscount;
        public TextView tvSubPrice, tvDiscount;

        public EnshrineViewHolder(View itemView) {
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
