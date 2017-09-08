package com.example.supportv1.utils;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.example.supportv1.R;
import com.example.supportv1.widget.CircleImageView;

/**
 * 图片加载框架的工具类
 *
 * @author Sun.bl
 * @version [1.0, 2016/3/31]
 */
public class ImageLoaderUtil {

    static final String TAG = "ImageLoaderUtil";

    static int sLoadRes = R.drawable.default_small_pic;

    static int sErrRes = R.drawable.default_small_pic;

    private ImageLoaderUtil() {
        throw new AssertionError("this is util class");
    }

    /**
     * 设置默认参数
     *
     * @param loadRes 正在加载的图片
     * @param errRes  加载失败的图片
     */
    public static void setImageDefault(int loadRes, int errRes) {
        sLoadRes = loadRes;
        sErrRes = errRes;
    }

    /**
     * 加载本地资源图片
     *
     * @param context   上下文环境
     * @param imageView 要加载的图片控件
     * @param res       本地资源ID
     */
    public static void displayImage(Context context, ImageView imageView, int res) {
        DrawableTypeRequest request = Glide.with(context).load(res);
        request.into(imageView);
    }

    /**
     * 加载本地资源图片
     *
     * @param fragment  关联的Fragment
     * @param imageView 要加载的图片控件
     * @param res       本地资源ID
     */
    public static void displayImage(Fragment fragment, ImageView imageView, int res) {
        DrawableTypeRequest request = Glide.with(fragment).load(res);
        request.into(imageView);
    }

    /**
     * 加载图片
     *
     * @param context   上下文环境
     * @param imageView 要加载的图片控件
     * @param url       图片网络地址
     */
    public static void displayImage(Context context, ImageView imageView, String url) {
        displayImage(context, imageView, url, sLoadRes, sErrRes);
    }

    /**
     * @param context   上下文环境
     * @param imageView 要加载的图片控件
     * @param url       图片网络地址
     */
    public static void displayImageWithOutDefault(Context context, ImageView imageView, String url) {
        displayImage(context, imageView, url, -1, -1);
    }

    /***
     * 加载图片
     *
     * @param context   上下文环境
     * @param imageView 要加载的图片控件
     * @param url       图片网络地址
     * @param loadRes   正在加载的占位图
     * @param errRes    加载失败的占位图
     */
    public static void displayImage(Context context, ImageView imageView, String url, int loadRes, int errRes) {
        displayImage(context, imageView, url, -1, -1, loadRes, errRes);
    }


    /**
     * @param context   上下文环境
     * @param imageView 要加载的图片控件
     * @param url       图片网络地址
     * @param width     指定图片加载的宽度
     * @param height    指定图片加载的高度
     * @param loadRes   正在加载的占位图
     * @param errRes    加载失败的占位图
     */
    public static void displayImage(Context context, ImageView imageView, String url, int width, int height, int loadRes, int errRes) {
        if (context == null || imageView == null) {
            throw new NullPointerException("Context or imageView is null");
        }
        DrawableTypeRequest request = Glide.with(context).load(url);
        if (loadRes > 0) {
            request.placeholder(loadRes);
        }
        if (errRes > 0) {
            request.error(errRes);
        }
        if (width > 0 && height > 0) {
            request.override(width, height);
        }
        if (imageView instanceof CircleImageView) {
            request.dontAnimate();
        } else {
            request.crossFade();
        }
        request.into(imageView);
    }

    /**
     * 加载图片
     *
     * @param fragment  关联的Fragment
     * @param imageView 要加载的图片控件
     * @param url       图片网络地址
     */
    public static void displayImage(Fragment fragment, ImageView imageView, String url) {
        displayImage(fragment, imageView, url, sLoadRes, sErrRes);
    }


    /***
     * @param fragment  关联的Fragment
     * @param imageView 要加载的图片控件
     * @param url       图片网络地址
     */
    public static void displayImageWithOutDefault(Fragment fragment, ImageView imageView, String url) {
        displayImage(fragment, imageView, url, -1, -1);
    }

    /**
     * 加载图片
     *
     * @param fragment  关联的Fragment
     * @param imageView 要加载的图片控件
     * @param url       图片网络地址
     * @param loadRes   正在加载的占位图
     * @param errRes    加载失败的占位图
     */
    public static void displayImage(Fragment fragment, ImageView imageView, String url, int loadRes, int errRes) {
        displayImage(fragment, imageView, url, -1, -1, loadRes, errRes);
    }

    /**
     * @param fragment  关联的Fragment
     * @param imageView 要加载的图片控件
     * @param url       图片网络地址
     * @param width     指定图片加载的宽度
     * @param height    指定图片加载的高度
     * @param loadRes   正在加载的占位图
     * @param errRes    加载失败的占位图
     */
    public static void displayImage(Fragment fragment, ImageView imageView, String url, int width, int height, int loadRes, int errRes) {
        if (fragment == null || imageView == null) {
            throw new NullPointerException("fragment or imageView is null");
        }
        DrawableTypeRequest request = Glide.with(fragment).load(url);
        if (loadRes > 0) {
            request.placeholder(loadRes);
        }
        if (errRes > 0) {
            request.error(errRes);
        }
        if (width > 0 && height > 0) {
            request.override(width, height);
        }
        if (width > 0 && height > 0) {
            request.override(width, height);
        }
        if (imageView instanceof CircleImageView) {
            request.dontAnimate();
        } else {
            request.crossFade();
        }
        request.into(imageView);
    }

    /***
     * @param context   上下文环境
     * @param imageView 要加载的图片控件
     * @param uri       图片URI地址
     */
    public static void displayImageWithOutDefault(Context context, ImageView imageView, Uri uri) {
        displayImage(context, imageView, uri, -1, -1);
    }

    /***
     * @param context   上下文环境
     * @param imageView 要加载的图片控件
     * @param uri       图片URI地址
     */
    public static void displayImage(Context context, ImageView imageView, Uri uri) {
        displayImage(context, imageView, uri, sLoadRes, sErrRes);
    }

    /***
     * @param context   上下文环境
     * @param imageView 要加载的图片控件
     * @param uri       图片URI地址
     * @param loadRes   正在加载的占位图
     * @param errRes    加载失败的占位图
     */
    public static void displayImage(Context context, ImageView imageView, Uri uri, int loadRes, int errRes) {
        displayImage(context, imageView, uri, -1, -1, loadRes, errRes);
    }

    /**
     * @param context   上下文环境
     * @param imageView 要加载的图片控件
     * @param uri       图片URI地址
     * @param width     指定图片加载的宽度
     * @param height    指定图片加载的高度
     * @param loadRes   正在加载的占位图
     * @param errRes    加载失败的占位图
     */
    public static void displayImage(Context context, ImageView imageView, Uri uri, int width, int height, int loadRes, int errRes) {
        if (context == null || imageView == null) {
            throw new NullPointerException("context or imageView is null");
        }
        DrawableTypeRequest request = Glide.with(context).load(uri);
        if (loadRes > 0) {
            request.placeholder(loadRes);
        }
        if (errRes > 0) {
            request.error(errRes);
        }
        if (width > 0 && height > 0) {
            request.override(width, height);
        }
        if (width > 0 && height > 0) {
            request.override(width, height);
        }
        if (imageView instanceof CircleImageView) {
            request.dontAnimate();
        } else {
            request.crossFade();
        }
        request.into(imageView);
    }


    /***
     * @param fragment  关联的Fragment
     * @param imageView 要加载的图片控件
     * @param uri       图片URI地址
     */
    public static void displayImageWithOutDefault(Fragment fragment, ImageView imageView, Uri uri) {
        displayImage(fragment, imageView, uri);
    }

    /***
     * @param fragment  关联的Fragment
     * @param imageView 要加载的图片控件
     * @param uri       图片URI地址
     */
    public static void displayImage(Fragment fragment, ImageView imageView, Uri uri) {
        displayImage(fragment, imageView, uri, sLoadRes, sErrRes);
    }

    /**
     * @param fragment  关联的Fragment
     * @param imageView 要加载的图片控件
     * @param uri       图片URI地址
     * @param loadRes   正在加载的占位图
     * @param errRes    加载失败的占位图
     */
    public static void displayImage(Fragment fragment, ImageView imageView, Uri uri, int loadRes, int errRes) {
        displayImage(fragment, imageView, uri, -1, -1, loadRes, errRes);
    }

    /***
     * @param fragment  关联的Fragment
     * @param imageView 要加载的图片控件
     * @param uri       图片URI地址
     * @param width     指定图片加载的宽度
     * @param height    指定图片加载的高度
     * @param loadRes   正在加载的占位图
     * @param errRes    加载失败的占位图
     */
    public static void displayImage(Fragment fragment, ImageView imageView, Uri uri, int width, int height, int loadRes, int errRes) {
        if (fragment == null || imageView == null) {
            throw new NullPointerException("fragment or imageView is null");
        }
        DrawableTypeRequest request = Glide.with(fragment).load(uri);
        if (loadRes > 0) {
            request.placeholder(loadRes);
        }
        if (errRes > 0) {
            request.error(errRes);
        }
        if (width > 0 && height > 0) {
            request.override(width, height);
        }
        if (imageView instanceof CircleImageView) {
            request.dontAnimate();
        } else {
            request.crossFade();
        }
        request.into(imageView);
    }


}
