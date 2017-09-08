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
import com.example.runfastshop.bean.SortInfo;

import java.util.List;

/**
 * 排序选择适配
 * Created by 天上白玉京 on 2017/7/28.
 */

public class BreakfastSortAdapter extends RecyclerView.Adapter<BreakfastSortAdapter.BreakfastSortViewHolder>{

    private List<SortInfo> sortInfos;

    private Context context;

    private View.OnClickListener listener;

    public BreakfastSortAdapter(List<SortInfo> sortInfos, Context context, View.OnClickListener listener) {
        this.sortInfos = sortInfos;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public BreakfastSortViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_sort_select,parent,false);
        BreakfastSortViewHolder viewHolder = new BreakfastSortViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BreakfastSortViewHolder holder, int position) {
        SortInfo sortInfo = sortInfos.get(position);
        if (sortInfo != null) {
            holder.tvName.setText(sortInfo.name);
            if (sortInfo.isSelect){
                holder.tvName.setTextColor(context.getResources().getColor(R.color.color_orange_select));
                holder.ivSelect.setVisibility(View.VISIBLE);
            }else {
                holder.tvName.setTextColor(context.getResources().getColor(R.color.color_text_gray_two));
                holder.ivSelect.setVisibility(View.GONE);
            }
            holder.layoutSelect.setTag(position);
            holder.layoutSelect.setOnClickListener(listener);
        }
    }

    @Override
    public int getItemCount() {
        return sortInfos == null?0:sortInfos.size();
    }

    public class BreakfastSortViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout layoutSelect;
        public ImageView ivSelect;
        public TextView tvName;
        public BreakfastSortViewHolder(View itemView) {
            super(itemView);
            layoutSelect = (LinearLayout) itemView.findViewById(R.id.layout_sort_select);
            ivSelect = (ImageView) itemView.findViewById(R.id.iv_sort_select);
            tvName = (TextView) itemView.findViewById(R.id.tv_sort_name);
        }
    }
}
