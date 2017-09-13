package com.example.runfastshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.BreakfastActivity;
import com.example.runfastshop.bean.mainmiddle.MiddleSort;
import com.example.runfastshop.data.ApiServiceFactory;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by liuhui on 2016/11/8.
 */

public class PageScrollAdapter extends RecyclerView.Adapter<PageScrollAdapter.MyViewHolder> {

    private List<MiddleSort> data;

    private Context mContext;
    public PageScrollAdapter(Context context,List<MiddleSort> data) {
        this.data = data;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_home_middle, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final MiddleSort middleSort = data.get(position);
        holder.tv_home_middle_name.setText(middleSort.getTypename());
        holder.iv_home_middle_img.setImageURI(ApiServiceFactory.BASE_IMG_URL + middleSort.getIcon());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO  添加信息
                Intent intent = new Intent(mContext, BreakfastActivity.class);
                intent.putExtra("middleData",middleSort);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_home_middle_name;
        SimpleDraweeView iv_home_middle_img;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_home_middle_name = (TextView) itemView.findViewById(R.id.tv_home_middle_name);
            iv_home_middle_img = (SimpleDraweeView) itemView.findViewById(R.id.iv_home_middle_img);
        }
    }
}
