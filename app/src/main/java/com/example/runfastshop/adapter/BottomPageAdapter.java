package com.example.runfastshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.BusinessActivity;
import com.example.runfastshop.bean.maintop.TopImage1;
import com.example.runfastshop.data.ApiServiceFactory;
import com.example.runfastshop.data.IntentFlag;
import com.example.runfastshop.util.CustomToast;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by huiliu on 2017/9/4.
 *
 * @email liu594545591@126.com
 * @introduce
 */
public class BottomPageAdapter extends RecyclerView.Adapter<BottomPageAdapter.BottomViewHolder>{

    private Context mContext;
    private List<TopImage1> data;

    public BottomPageAdapter(Context context, List<TopImage1> data) {
        mContext = context;
        this.data = data;
    }

    @Override
    public BottomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_bottom_scroll_page, parent, false);
        return new BottomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BottomViewHolder holder, int position) {
        final TopImage1 topImage1 = data.get(position);
        holder.tv_bottom_scroll_name.setText(topImage1.getAgentName());
        holder.tv_bottom_scroll_tag.setText(topImage1.getTitle());
        holder.iv_bottom_scroll.setScaleType(ImageView.ScaleType.FIT_XY);
        holder.iv_bottom_scroll.setImageURI(ApiServiceFactory.BASE_IMG_URL + topImage1.getAdImages());
        holder.ll_bottom_ad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topImage1.getAdType() == 4) {
                    Intent intent = new Intent(mContext, BusinessActivity.class);
                    intent.setFlags(IntentFlag.MAIN_BOTTOM_AD);
                    intent.putExtra("business", topImage1);
                    mContext.startActivity(intent);
                }else {
                    //TODO 只处理了外卖操作。此处商家操作待处理
                   CustomToast.INSTANCE.showToast(mContext,"活动暂未开放");
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

     class BottomViewHolder extends RecyclerView.ViewHolder {
         TextView tv_bottom_scroll_name;
         TextView tv_bottom_scroll_tag;
         SimpleDraweeView iv_bottom_scroll;
         LinearLayout ll_bottom_ad;

         public BottomViewHolder(View itemView) {
             super(itemView);
             tv_bottom_scroll_name = (TextView) itemView.findViewById(R.id.tv_bottom_scroll_name);
             tv_bottom_scroll_tag = (TextView) itemView.findViewById(R.id.tv_bottom_scroll_tag);
             iv_bottom_scroll = (SimpleDraweeView) itemView.findViewById(R.id.iv_bottom_scroll);
             ll_bottom_ad = (LinearLayout) itemView.findViewById(R.id.ll_bottom_ad);
         }
     }


}
