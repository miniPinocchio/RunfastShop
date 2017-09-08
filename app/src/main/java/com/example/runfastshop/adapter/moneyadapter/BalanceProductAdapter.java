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
 * 结算商品列表适配
 * Created by 天上白玉京 on 2017/8/5.
 */

public class BalanceProductAdapter extends RecyclerView.Adapter<BalanceProductAdapter.HistorySearchViewHolder>{

    private List<BalanceInfo> data;

    private Context context;

    public BalanceProductAdapter(List<BalanceInfo> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public HistorySearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_list,parent,false);
        HistorySearchViewHolder holder = new HistorySearchViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(HistorySearchViewHolder holder, int position) {
        BalanceInfo balanceInfo = data.get(position);
        if (balanceInfo != null) {
            holder.tvProductName.setText(balanceInfo.name);
            holder.tvProductNum.setText(TextUtils.isEmpty(balanceInfo.number)?"":"x"+balanceInfo.number);

            if (balanceInfo.isDelete){
                holder.tvProductPrice.setTextColor(context.getResources().getColor(R.color.color_balance_price));
                holder.tvProductPrice.setText("- ¥ "+balanceInfo.price.intValue());
            }else {
                holder.tvProductPrice.setText("¥ "+balanceInfo.price.intValue());
            }
        }
    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    public class HistorySearchViewHolder extends RecyclerView.ViewHolder{

        public TextView tvProductName,tvProductNum,tvProductPrice;

        public HistorySearchViewHolder(View itemView) {
            super(itemView);
            tvProductName = (TextView) itemView.findViewById(R.id.tv_product_name);
            tvProductNum = (TextView) itemView.findViewById(R.id.tv_product_num);
            tvProductPrice = (TextView) itemView.findViewById(R.id.tv_product_price);
        }
    }
}
