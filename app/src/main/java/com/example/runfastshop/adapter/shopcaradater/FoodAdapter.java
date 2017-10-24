package com.example.runfastshop.adapter.shopcaradater;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.runfastshop.R;
import com.example.runfastshop.activity.ProductDetailActivity;
import com.example.runfastshop.bean.FoodBean;
import com.example.runfastshop.config.NetConfig;
import com.example.runfastshop.impl.constant.UrlConstant;
import com.example.runfastshop.view.AddWidget;

import org.xutils.x;

import java.util.List;

public class FoodAdapter extends BaseQuickAdapter<FoodBean, BaseViewHolder> {
	public static final int FIRST_STICKY_VIEW = 1;
	public static final int HAS_STICKY_VIEW = 2;
	public static final int NONE_STICKY_VIEW = 3;
	private List<FoodBean> flist;
	private AddWidget.OnAddClick onAddClick;
	private Context context;
	private View.OnClickListener listener;

	public FoodAdapter(@Nullable List<FoodBean> data, AddWidget.OnAddClick onAddClick, Context context,View.OnClickListener listener) {
		super(R.layout.item_food, data);
		flist = data;
		this.onAddClick = onAddClick;
		this.context = context;
		this.listener = listener;
	}

	@Override
	protected void convert(final BaseViewHolder helper, final FoodBean item) {
		helper.setText(R.id.tv_name, item.getName())
				.setText(R.id.tv_price, "¥" + item.getPrice())
				.setText(R.id.tv_summary, "月售"+item.getSale());
		helper.setVisible(R.id.layout_spec, item.getIsonly() == 1);
		helper.setVisible(R.id.tv_spec_num, item.getSelectCount() > 0)
				.setText(R.id.tv_spec_num,item.getSelectCount()+"");

		helper.setVisible(R.id.addwidget, item.getIsonly() == 0);

//		Glide.with(context)
//				.load( UrlConstant.ImageBaseUrl + item.getIcon())
//				.placeholder(R.drawable.icon_default_head)
//				.error(R.drawable.icon_default_head)
//				.centerCrop()
//				.diskCacheStrategy(DiskCacheStrategy.RESULT)
//				.into((ImageView) helper.getView(R.id.iv_food));
		x.image().bind((ImageView) helper.getView(R.id.iv_food), UrlConstant.ImageBaseUrl + item.getIcon(), NetConfig.optionsPagerCache);

		AddWidget addWidget = helper.getView(R.id.addwidget);
		addWidget.setData(this, helper.getAdapterPosition(), onAddClick);

		if (helper.getAdapterPosition() == 0) {
			helper.setVisible(R.id.stick_header, true)
					.setText(R.id.tv_header, item.getType())
					.setTag(R.id.food_main, FIRST_STICKY_VIEW);
		} else {
			if (!TextUtils.equals(item.getType(), flist.get(helper.getAdapterPosition() - 1).getType())) {
				helper.setVisible(R.id.stick_header, true)
						.setText(R.id.tv_header, item.getType())
						.setTag(R.id.food_main, HAS_STICKY_VIEW);
			} else {
				helper.setVisible(R.id.stick_header, false)
						.setTag(R.id.food_main, NONE_STICKY_VIEW);
			}
		}
		helper.getConvertView().setContentDescription(item.getType());

		helper.setTag(R.id.food_main,helper.getPosition());
		helper.setOnClickListener(R.id.food_main, listener);

		helper.setTag(R.id.layout_spec,helper.getPosition());
		helper.setOnClickListener(R.id.layout_spec, listener);
		//context.startActivity(new Intent(context, ProductDetailActivity.class).putExtra("food",item));
	}

}
