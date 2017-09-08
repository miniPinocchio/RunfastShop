package com.example.runfastshop.adapter.shopcaradater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.runfastshop.R;
import com.example.runfastshop.bean.FoodBean;
import com.example.runfastshop.view.AddWidget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 天上白玉京 on 2017/8/23.
 */

public class SpecCarAdapter extends RecyclerView.Adapter<SpecCarAdapter.SpecCarViewHolder>{

    private List<FoodBean> carFoods;

    private Context context;

    private UpdateSpecCountImp specCountImp;

    public SpecCarAdapter(List<FoodBean> carFoods, Context context,UpdateSpecCountImp specCountImp) {
        this.carFoods = carFoods;
        this.context = context;
        this.specCountImp = specCountImp;
    }

    public interface UpdateSpecCountImp{
        void add(View view,FoodBean foodBean);
        void sub(FoodBean fb);
    }

    @Override
    public SpecCarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_car_spec,parent,false);
        SpecCarViewHolder viewHolder = new SpecCarViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SpecCarViewHolder holder, int position) {
        final FoodBean foodBean = carFoods.get(position);
        if (foodBean != null) {
            holder.tvCarName.setText(foodBean.getName());
            holder.tvCarPrice.setText("¥" + foodBean.getPrice().multiply(BigDecimal.valueOf(foodBean.getSelectCount())));
            holder.tvCount.setText(foodBean.getSelectCount()+"");
            holder.tvSpec.setText(TextUtils.isEmpty(foodBean.getGoodsSpec())?"":foodBean.getGoodsSpec());
            holder.tvSpec.setVisibility(TextUtils.isEmpty(foodBean.getGoodsSpec())?View.GONE:View.VISIBLE);

            holder.tvType.setText(TextUtils.isEmpty(foodBean.getGoodsType())?"":foodBean.getGoodsType());
            holder.tvType.setVisibility(TextUtils.isEmpty(foodBean.getGoodsType())?View.GONE:View.VISIBLE);

            holder.tvTypeTwo.setText(TextUtils.isEmpty(foodBean.getGoodsTypeTwo())?"":foodBean.getGoodsTypeTwo());
            holder.tvTypeTwo.setVisibility(TextUtils.isEmpty(foodBean.getGoodsTypeTwo())?View.GONE:View.VISIBLE);

            holder.ivAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long selectCount = foodBean.getSelectCount();
                    selectCount++;
                    foodBean.setSelectCount(selectCount);
                    specCountImp.add(v,foodBean);
                }
            });
            holder.ivSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long selectCount = foodBean.getSelectCount();
                    if (selectCount == 0) {
                        return;
                    }
                    selectCount--;
                    foodBean.setSelectCount(selectCount);
                    specCountImp.sub(foodBean);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return carFoods == null?0:carFoods.size();
    }

    public class SpecCarViewHolder extends RecyclerView.ViewHolder{

        public TextView tvCarName,tvCarPrice,tvCount;
        public TextView tvSpec,tvType,tvTypeTwo;

        public ImageView ivSub,ivAdd;


        public SpecCarViewHolder(View itemView) {
            super(itemView);
            tvCarName = (TextView) itemView.findViewById(R.id.car_name);
            tvCarPrice = (TextView) itemView.findViewById(R.id.car_price);
            tvCount = (TextView) itemView.findViewById(R.id.tv_count_spec);

            tvSpec = (TextView) itemView.findViewById(R.id.tv_spec);
            tvType = (TextView) itemView.findViewById(R.id.tv_goods_type);
            tvTypeTwo = (TextView) itemView.findViewById(R.id.tv_goods_type_two);

            ivSub = (ImageView) itemView.findViewById(R.id.iv_sub_spec);
            ivAdd = (ImageView) itemView.findViewById(R.id.iv_add_spec);
        }
    }
}
