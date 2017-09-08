package com.example.runfastshop.config;

import android.graphics.Bitmap;
import android.widget.ImageView;


import com.example.runfastshop.R;

import org.xutils.image.ImageOptions;

/**
 * Created by 天上白玉京 on 2017/6/20.
 */

public class NetConfig {

    //图片加载换存
    public static ImageOptions optionsPagerCache =
            new ImageOptions.Builder()
                    //设置图片的位图
                    .setConfig(Bitmap.Config.RGB_565)
                    //设置是否使用内存缓存
                    .setUseMemCache(false)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    //设置加载过程中的图片
                    .setLoadingDrawableId(R.drawable.icon_default_head)
                    //设置网络加载出错的图片
                    .setFailureDrawableId(R.drawable.icon_default_head)
                    //设置是否支持Gif动态图 false 是支持 true 是不支持
                    .setIgnoreGif(true)
                    .build();


    //图片加载换存
    public static ImageOptions optionsHeadImage =
            new ImageOptions.Builder()
                    //设置图片的位图
                    .setConfig(Bitmap.Config.RGB_565)
                    //设置是否使用内存缓存
                    .setUseMemCache(true)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    //设置加载过程中的图片
                    .setLoadingDrawableId(R.drawable.icon_default_head)
                    //设置网络加载出错的图片
                    .setFailureDrawableId(R.drawable.icon_default_head)
                    //设置是否支持Gif动态图 false 是支持 true 是不支持
                    .setIgnoreGif(true)
                    .build();
}
