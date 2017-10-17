package com.example.runfastshop.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.runfastshop.R;
import com.example.runfastshop.bean.BusinessExercise;
import com.example.runfastshop.bean.BusinessInfo;
import com.example.runfastshop.config.NetConfig;
import com.example.runfastshop.impl.constant.UrlConstant;

import org.xutils.x;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by quantan.liu on 2017/3/27.
 */

public class NearbyBusinessAdapter extends BaseQuickAdapter<BusinessInfo,BaseViewHolder> {

    public NearbyBusinessAdapter(List<BusinessInfo> data) {
        super(R.layout.item_business_info,data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BusinessInfo businessInfo) {
        helper.setText(R.id.tv_business_name,businessInfo.name);
        helper.setText(R.id.tv_business_levelId,String.valueOf(businessInfo.levelId));
        helper.setText(R.id.tv_sale_distance,String.valueOf(new DecimalFormat("#0.0").format(businessInfo.distance)) + "km");
        helper.setText(R.id.tv_business_sales_num,"月售" + String.valueOf(businessInfo.salesnum) + "单");
        helper.setText(R.id.tv_sale_startPay,businessInfo.startPay == null ? "¥ 0元起送" : "¥ " + String.valueOf(businessInfo.startPay) + "起送");
        helper.setText(R.id.tv_sale_time,businessInfo.speed);
        if (businessInfo.isDeliver == 0) {
            helper.setText(R.id.tv_sale_price,businessInfo.baseCharge == null ? "配送费¥0" : "配送费¥" + String.valueOf(businessInfo.baseCharge));
            helper.setVisible(R.id.iv_is_charge,true);
        } else {
            helper.setVisible(R.id.iv_is_charge,false);
            helper.setText(R.id.tv_sale_price,businessInfo.busshowps == null ? "配送费¥0" : "配送费¥" + String.valueOf(businessInfo.busshowps));
        }

        List<BusinessExercise> alist = businessInfo.alist;

        if (alist != null) {
            int size = alist.size();
            if (size > 0) {
                for (int i = 0; i < alist.size(); i++) {
                    BusinessExercise exercise = alist.get(i);
                    switch (exercise.ptype) {
                        case 1:
                            helper.setVisible(R.id.layout_sub_price,true);
                            helper.setText(R.id.tv_sub_price,exercise.showname);
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4:
                            helper.setVisible(R.id.layout_discount,true);
                            helper.setText(R.id.tv_discount,exercise.showname);
                            break;
                    }
                }
            }
        }
        x.image().bind((ImageView) helper.getView(R.id.iv_business_logo), UrlConstant.ImageBaseUrl + businessInfo.mini_imgPath, NetConfig.optionsPagerCache);
        //GlideUtils.loadImage(3,item.getImages().getLarge(), (ImageView) helper.getView(R.id.iv_item_movie_top));
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClickListener(businessInfo, helper.getView(R.id.layout_breakfast_item));
            }
        });

    }
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClickListener(BusinessInfo positionData, View view);}

}
