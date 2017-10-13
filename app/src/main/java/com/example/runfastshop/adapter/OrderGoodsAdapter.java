package com.example.runfastshop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.bean.order.GoodsSellRecordChildren;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Devon on 2017/9/28.
 */

public class OrderGoodsAdapter extends BaseAdapter {
    private Activity context = null;
    private List<GoodsSellRecordChildren> orderGoodsList;

    public OrderGoodsAdapter(Activity context, List<GoodsSellRecordChildren> orderGoodsList) {
        this.context = context;
        this.orderGoodsList = orderGoodsList;
    }

    @Override
    public int getCount() {
        return orderGoodsList == null ? 0 : orderGoodsList.size();
    }

    @Override
    public GoodsSellRecordChildren getItem(int position) {
        return orderGoodsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        GoodsSellRecordChildren goodsSellRecordChildren = orderGoodsList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_order_detail_goods, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tvOrderDetailGoodName.setText(goodsSellRecordChildren.getGoodsSellName());
        viewHolder.tvOrderDetailGoodNum.setText("x" + goodsSellRecordChildren.getNum());
        viewHolder.tvOrderDetailGoodPrice.setText("¥ " + goodsSellRecordChildren.getPrice());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_order_detail_good_name)
        TextView tvOrderDetailGoodName;
        @BindView(R.id.tv_order_detail_good_num)
        TextView tvOrderDetailGoodNum;
        @BindView(R.id.tv_order_detail_good_price)
        TextView tvOrderDetailGoodPrice;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
