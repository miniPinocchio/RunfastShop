package com.example.runfastshop.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.view.MaxHeightRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huiliu on 2017/9/13.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class OrderDetailAdapter extends RecyclerView.Adapter {

    private final int ORDER_MAN_STATE_VIEW = 1;//
    private final int ORDER_DETAIL_VIEW = 2;
    private final int ORDER_OTHER_INFO_VIEW = 3;

    private Context context;
    private List<String> list;

    public OrderDetailAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getItemViewType(int position) {
        if (list.size() == 0) {
            return ORDER_MAN_STATE_VIEW;
        } else if (list.size() == 1) {
            return ORDER_DETAIL_VIEW;
        } else if (list.size() == 2) {
            return ORDER_OTHER_INFO_VIEW;
        } else {
            return super.getItemViewType(position);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == ORDER_DETAIL_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail_middle, parent, false);
            return new MiddleViewHolder(view);
        } else if (viewType == ORDER_MAN_STATE_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail_header, parent, false);
            return new TopViewHolder(view);
        } else if (viewType == ORDER_OTHER_INFO_VIEW) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail_bottom, parent, false);
            return new BottomViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof TopViewHolder) {
            TopViewHolder viewHolder = (TopViewHolder) holder;
//            viewHolder.time.setText(list.get(position).getTime());
        } else if (holder instanceof MiddleViewHolder) {
            MiddleViewHolder viewHolder = (MiddleViewHolder) holder;
//            viewHolder.title.setText(list.get(position).getTitle());
        } else if (holder instanceof BottomViewHolder) {
            BottomViewHolder viewHolder = (BottomViewHolder) holder;
//            viewHolder.progressBar.setIndeterminate(true);
        }
    }


    @Override
    public int getItemCount() {
        return 3;
    }


    static class BottomViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_man_state)
        TextView mTvOrderManState;
        @BindView(R.id.tv_man_deliver_time)
        TextView mTvManDeliverTime;
        @BindView(R.id.btn_cancel_order)
        Button mBtnCancelOrder;
        @BindView(R.id.btn_contact_business)
        Button mBtnContactBusiness;
        @BindView(R.id.btn_contact_man)
        Button mBtnContactMan;

        public BottomViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class MiddleViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_order_detail_business_img)
        ImageView mIvOrderDetailBusinessImg;
        @BindView(R.id.tv_order_detail_business_name)
        TextView mTvOrderDetailBusinessName;
        @BindView(R.id.recycler_product_list)
        MaxHeightRecyclerView mRecyclerProductList;
        @BindView(R.id.tv_order_detail_price)
        TextView mTvOrderDetailPrice;
        @BindView(R.id.tv_order_detail_coupon_price)
        TextView mTvOrderDetailCouponPrice;
        @BindView(R.id.tv_order_detail_sub_price)
        TextView mTvOrderDetailSubPrice;

        MiddleViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


    static class TopViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_order_detail_deliver_type)
        TextView mTvOrderDetailDeliverType;
        @BindView(R.id.tv_order_detail_deliver_time)
        TextView mTvOrderDetailDeliverTime;
        @BindView(R.id.tv_order_detail_man_info)
        TextView mTvOrderDetailManInfo;
        @BindView(R.id.tv_order_detail_man_phone)
        TextView mTvOrderDetailManPhone;
        @BindView(R.id.tv_order_detail_number)
        TextView mTvOrderDetailNumber;
        @BindView(R.id.tv_order_detail_pat_type)
        TextView mTvOrderDetailPatType;
        @BindView(R.id.tv_order_detail_remark)
        TextView mTvOrderDetailRemark;

        TopViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}