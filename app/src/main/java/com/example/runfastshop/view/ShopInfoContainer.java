package com.example.runfastshop.view;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.util.ViewUtils;
import com.facebook.drawee.view.SimpleDraweeView;


public class ShopInfoContainer extends RelativeLayout {

	public TextView shop_name, shop_distribution, shop_sell, shop_send,shop_Time,shop_Explain;
	private ImageView shop_arrow;
	private SimpleDraweeView iv_shop;

	public ShopInfoContainer(Context context) {
		super(context);
	}

	public ShopInfoContainer(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		inflate(context, R.layout.layout_business_info, this);
		shop_name = (TextView) findViewById(R.id.tv_shop_name);
		shop_arrow = (ImageView) findViewById(R.id.iv_shop_arrow);
		shop_distribution = (TextView) findViewById(R.id.tv_shop_distribution);
		shop_sell = (TextView) findViewById(R.id.tv_shop_sell);
		shop_send = (TextView) findViewById(R.id.tv_shop_send);
		shop_Time = (TextView) findViewById(R.id.tv_send_time);
		shop_Explain = (TextView) findViewById(R.id.tv_shop_explain);
		ViewUtils.getBlurFresco(context, (SimpleDraweeView) findViewById(R.id.iv_shop_bg), "res:///" + R.drawable.icon_shop);
		iv_shop = (SimpleDraweeView) findViewById(R.id.iv_shop);
		ViewUtils.getFrescoController(context, iv_shop, "res:///" + R.drawable.icon_shop, 40, 40);
	}


	public void setWgAlpha(float alpha) {
		shop_distribution.setAlpha(alpha);
		shop_arrow.setAlpha(alpha);
		shop_sell.setAlpha(alpha);
		shop_send.setAlpha(alpha);
		shop_Time.setAlpha(alpha);
		shop_Explain.setAlpha(alpha);
		iv_shop.setAlpha(alpha);
	}
}
