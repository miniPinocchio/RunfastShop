package com.example.runfastshop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.runfastshop.activity.BusinessActivity;
import com.example.runfastshop.bean.maintop.TopImage;
import com.example.runfastshop.data.ApiServiceFactory;
import com.example.runfastshop.data.IntentFlag;
import com.example.runfastshop.util.CustomToast;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.List;

/**
 * 轮播图适配
 * Created by 天上白玉京 on 2017/6/21.
 */

public class NormalAdapter extends StaticPagerAdapter {

    private Context mContext;
    private List<TopImage> imageUrls;

    public NormalAdapter(Context context, List<TopImage> imageUrls) {
        mContext = context;
        this.imageUrls = imageUrls;
    }

    @Override
    public View getView(ViewGroup container, final int position) {
        final TopImage topImage = imageUrls.get(position);
        SimpleDraweeView draweeView = new SimpleDraweeView(container.getContext());
        draweeView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        draweeView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(ApiServiceFactory.BASE_IMG_URL + topImage.getAdImages()))
//                .setImageRequest(ImageRequest.fromUri("http://www.gxptkc.com/upload/1488614499999.png"))
                .setImageRequest(ImageRequest.fromUri(ApiServiceFactory.BASE_IMG_URL + topImage.getAdImages()))
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
        //x.image().bind(view, imageUrls[position], NetConfig.optionsPagerCache);
        draweeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //广告类型0外卖1商城2一元购
                switch (topImage.getType()) {
                    case 0://外卖
                        Intent intent = new Intent(mContext, BusinessActivity.class);
                        intent.setFlags(IntentFlag.MAIN_TOP_PAGE);
                        intent.putExtra("business", topImage);
                        mContext.startActivity(intent);
                        break;
                    //TODO 商家和一元购 不知如何处理
                    case 1://商家
                        CustomToast.INSTANCE.showToast(mContext,"商家");
                        break;
                    case 2://一元购
                        CustomToast.INSTANCE.showToast(mContext,"一元购");
                        break;
                }
            }
        });
        return draweeView;
    }


    @Override
    public int getCount() {
        return imageUrls.size();
    }

}
