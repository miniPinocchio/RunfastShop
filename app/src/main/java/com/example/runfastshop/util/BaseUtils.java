package com.example.runfastshop.util;


import android.content.Context;


import com.example.runfastshop.bean.FoodBean;
import com.example.runfastshop.bean.TypeBean;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BaseUtils {

	private static List<FoodBean> typeBeen;

	private static List<TypeBean> types;

	public static List<TypeBean> getTypes() {
		ArrayList<TypeBean> tList = new ArrayList<>();
//		for (int i = 0; i < 10; i++) {
//			TypeBean typeBean = new TypeBean();
//			typeBean.setName("类别" + i);
//			tList.add(typeBean);
//		}
		if (types == null){
			return tList;
		}else {
			return types;
		}

	}

	public static void setTypes(List<TypeBean> typeBeen){
		types = typeBeen;
	}

	public static List<FoodBean> getDatas(Context context) {
		ArrayList<FoodBean> fList = new ArrayList<>();
//		DecimalFormat df = new DecimalFormat("######0.0");
//		for (int i = 0; i < 100; i++) {
//			FoodBean foodBean = new FoodBean();
//			foodBean.setId(i);
//			foodBean.setName("食品--" + i + 1);
//			foodBean.setPrice(BigDecimal.valueOf((new Random().nextDouble() * 100)).setScale(1, BigDecimal.ROUND_HALF_DOWN));
//			foodBean.setSale("月售" + new Random().nextInt(100));
//			foodBean.setType("类别" + i / 10);
//			int resID = context.getResources().getIdentifier("food" + new Random().nextInt(8), "drawable", "com.k.neleme");
//			foodBean.setIcon(resID);
//			fList.add(foodBean);
//		}
		if (typeBeen == null){
			return fList;
		}else {
			return typeBeen;
		}
	}

	public static void setGoodData(List<FoodBean> fList){
		typeBeen = fList;
	}


	public static List<FoodBean> getOldData(){
		return typeBeen;
	}
}
