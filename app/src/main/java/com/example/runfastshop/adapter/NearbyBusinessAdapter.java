package com.example.runfastshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.runfastshop.R;
import com.example.runfastshop.bean.BusinessExercise;
import com.example.runfastshop.bean.BusinessInfo;
import com.example.runfastshop.bean.HomeDataItemBean;
import com.example.runfastshop.bean.mainmiddle.MiddleSort;
import com.example.runfastshop.bean.maintop.TopImage;
import com.example.runfastshop.bean.maintop.TopImage1;
import com.example.runfastshop.config.NetConfig;
import com.example.runfastshop.impl.constant.UrlConstant;
import com.example.runfastshop.view.recyclerview.HorizontalPageLayoutManager;
import com.example.runfastshop.view.recyclerview.PagingScrollHelper;
import com.jude.rollviewpager.RollPagerView;

import org.xutils.x;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by quantan.liu on 2017/3/27.
 */

public class NearbyBusinessAdapter extends RecyclerView.Adapter{

    private Context context;

    private List<HomeDataItemBean> data;

    private static final int MROWS = 2;//行数
    private static final int MCOLUMNS = 4;//列数

    public NearbyBusinessAdapter(Context context, List<HomeDataItemBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                View firstView = LayoutInflater.from(context).inflate(R.layout.item_home_first,parent,false);
                HomeFirstViewHolder homeHolder = new HomeFirstViewHolder(firstView);
                return homeHolder;
            case 1:
                View secondView = LayoutInflater.from(context).inflate(R.layout.item_home_second,parent,false);
                HomeSecondViewHolder secondHolder = new HomeSecondViewHolder(secondView);
                return secondHolder;
            case 2:
                View thirdView = LayoutInflater.from(context).inflate(R.layout.item_home_third,parent,false);
                HomeThirdViewHolder thirdHolder = new HomeThirdViewHolder(thirdView);
                return thirdHolder;
            case 3:
                View businessView = LayoutInflater.from(context).inflate(R.layout.item_business_info,parent,false);
                BusinessViewHolder businessHolder = new BusinessViewHolder(businessView);
                return businessHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeDataItemBean itemBean = data.get(position);
        if (holder instanceof HomeFirstViewHolder){
            HomeFirstViewHolder fisrtHolder = (HomeFirstViewHolder) holder;
            List<TopImage> imgurl = itemBean.imgurl;
            NormalAdapter mNormalAdapter = new NormalAdapter(context, imgurl);
            fisrtHolder.pagerView.setAdapter(mNormalAdapter);
        }
        else if (holder instanceof HomeSecondViewHolder){
            HomeSecondViewHolder secondHolder = (HomeSecondViewHolder) holder;
            //中部横向滚动
            List<MiddleSort> middleurl = itemBean.middleurl;
            PageScrollAdapter myAdapter = new PageScrollAdapter(context, middleurl);
            secondHolder.recyclerView.setAdapter(myAdapter);
            HorizontalPageLayoutManager horizontalPageLayoutManager = new HorizontalPageLayoutManager(MROWS, MCOLUMNS);
            secondHolder.recyclerView.setLayoutManager(horizontalPageLayoutManager);
            PagingScrollHelper scrollHelper = new PagingScrollHelper();
            scrollHelper.setUpRecycleView(secondHolder.recyclerView);
            //scrollHelper.setOnPageChangeListener(this);
        }
        else if (holder instanceof HomeThirdViewHolder){
            HomeThirdViewHolder thirdHolder = (HomeThirdViewHolder) holder;
            List<TopImage1> imgurl1 = itemBean.imgurl1;
            //底部滚动
            HorizontalPageLayoutManager horizontalPageLayoutManager1 = new HorizontalPageLayoutManager(1, 3);
            thirdHolder.recyclerView.setLayoutManager(horizontalPageLayoutManager1);
            BottomPageAdapter mBottomPageAdapter = new BottomPageAdapter(context, imgurl1);
            thirdHolder.recyclerView.setAdapter(mBottomPageAdapter);
        }

        else if (holder instanceof BusinessViewHolder){
            BusinessViewHolder businessHolder = (BusinessViewHolder) holder;
            final BusinessInfo businessInfos = itemBean.businessInfos;
            businessHolder.tv_business_name.setText(businessInfos.name);
            businessHolder.tv_business_levelId.setText(String.valueOf(businessInfos.levelId));
            businessHolder.rb_order_evaluate.setProgress(businessInfos.levelId);
            businessHolder.tv_sale_distance.setText(String.valueOf(new DecimalFormat("#0.0").format(businessInfos.distance)) + "km");
            businessHolder.tv_business_sales_num.setText("月售" + String.valueOf(businessInfos.salesnum) + "单");
            businessHolder.tv_sale_startPay.setText(businessInfos.startPay == null ? "¥ 0元起送" : "¥ " + String.valueOf(businessInfos.startPay) + "起送");
            businessHolder.tv_sale_time.setText(businessInfos.speed);

            if (businessInfos.isDeliver == 0) {
                businessHolder.tv_sale_price.setText(businessInfos.baseCharge == null ? "配送费¥0" : "配送费¥" + String.valueOf(businessInfos.baseCharge));
                businessHolder.iv_is_charge.setVisibility(View.VISIBLE);
            } else {
                businessHolder.tv_sale_price.setText(businessInfos.busshowps == null ? "配送费¥0" : "配送费¥" + String.valueOf(businessInfos.busshowps));
                businessHolder.iv_is_charge.setVisibility(View.GONE);
            }

            List<BusinessExercise> alist = businessInfos.alist;

            if (alist != null) {
                int size = alist.size();
                if (size > 0) {
                    for (int i = 0; i < alist.size(); i++) {
                        BusinessExercise exercise = alist.get(i);
                        switch (exercise.ptype) {
                            case 1:
                                businessHolder.layout_sub_price.setVisibility(View.VISIBLE);
                                businessHolder.tv_sub_price.setText(exercise.showname);
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                businessHolder.layout_discount.setVisibility(View.VISIBLE);
                                businessHolder.tv_discount.setText(exercise.showname);
                                break;
                        }
                    }
                }
            }
            x.image().bind(businessHolder.iv_business_logo, UrlConstant.ImageBaseUrl + businessInfos.mini_imgPath, NetConfig.optionsPagerCache);
            //GlideUtils.loadImage(3,item.getImages().getLarge(), (ImageView) helper.getView(R.id.iv_item_movie_top));
            businessHolder.layout_breakfast_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClickListener(businessInfos, v);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data == null?0:data.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 0;
        }
        if (position == 1){
            return 1;
        }
        if (position == 2){
            return 2;
        }
        return 3;
    }

    class HomeFirstViewHolder extends RecyclerView.ViewHolder{

        RollPagerView pagerView;

        HomeFirstViewHolder(View itemView) {
            super(itemView);
            pagerView = (RollPagerView) itemView.findViewById(R.id.roll_view_pager);
        }
    }

    class HomeSecondViewHolder extends RecyclerView.ViewHolder{

        RecyclerView recyclerView;

         HomeSecondViewHolder(View itemView) {
            super(itemView);
             recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_home_middle);
        }
    }

    class HomeThirdViewHolder extends RecyclerView.ViewHolder{
        RecyclerView recyclerView;

         HomeThirdViewHolder(View itemView) {
            super(itemView);
             recyclerView = (RecyclerView) itemView.findViewById(R.id.rv_home_bottom);
        }
    }

    class BusinessViewHolder extends RecyclerView.ViewHolder{

        TextView tv_business_name,tv_business_levelId,tv_sale_distance,
                tv_business_sales_num,tv_sale_startPay,tv_sale_time,tv_sale_price,
                tv_sub_price,tv_discount;

        ImageView iv_is_charge,iv_business_logo;

        LinearLayout layout_sub_price,layout_discount,layout_breakfast_item;

        RatingBar rb_order_evaluate;

         BusinessViewHolder(View itemView) {
            super(itemView);
             tv_business_name = (TextView) itemView.findViewById(R.id.tv_business_name);
             tv_business_levelId = (TextView) itemView.findViewById(R.id.tv_business_levelId);
             tv_sale_distance = (TextView) itemView.findViewById(R.id.tv_sale_distance);
             tv_business_sales_num = (TextView) itemView.findViewById(R.id.tv_business_sales_num);
             tv_sale_startPay = (TextView) itemView.findViewById(R.id.tv_sale_startPay);
             tv_sale_time = (TextView) itemView.findViewById(R.id.tv_sale_time);
             tv_sale_price = (TextView) itemView.findViewById(R.id.tv_sale_price);
             tv_sub_price = (TextView) itemView.findViewById(R.id.tv_sub_price);
             tv_discount = (TextView) itemView.findViewById(R.id.tv_discount);

             iv_is_charge = (ImageView) itemView.findViewById(R.id.iv_is_charge);
             iv_business_logo = (ImageView) itemView.findViewById(R.id.iv_business_logo);

             rb_order_evaluate = (RatingBar) itemView.findViewById(R.id.rb_order_evaluate);

             layout_sub_price = (LinearLayout) itemView.findViewById(R.id.layout_sub_price);
             layout_discount = (LinearLayout) itemView.findViewById(R.id.layout_discount);
             layout_breakfast_item = (LinearLayout) itemView.findViewById(R.id.layout_breakfast_item);
        }
    }
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(BusinessInfo positionData, View view);}

}
