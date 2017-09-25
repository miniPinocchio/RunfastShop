package com.example.runfastshop.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2016/5/24.
 */

/**
 * 流式布局，从左到右，从上到下
 */
public class FlowLayout extends ViewGroup{

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 用于给内部子控件排版用的，子控件不排版的话将不会显示
     * 参数 l,t,r,b 用于计算当前容器的尺寸
     * @param changed
     * @param l 当前容器的 left
     * @param t 当前容器的 top
     * @param r 当前容器的 right
     * @param b 当前容器的 bottom
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        //最后的步骤，换行问题
        int selfWith = r - l;// 当前容器的宽度

        //排版
        int childCount = getChildCount();
        //使用 cl ct 来实现 控件位置的排版



        int lastLineMaxHeight = 0;
        int cl=10;
        int ct=10;

        for (int i = 0; i < childCount; i++) {
            //获取一个子控件
            View childAt = getChildAt(i);
            //在排版的时候，需要知道子控件的尺寸
            //已知，l，t  r= l+width  b=t+height
            int width = getLayoutParams().width;
            int height = getLayoutParams().height;
            //获取子控件测量之后的宽度
            int mW = childAt.getMeasuredWidth();

            //获取子控件测量之后的高度
            int mH = childAt.getMeasuredHeight();

            int cr= cl+ mW+ width;
            int cb= ct+ mH+ height;

            //超过就换行
            if (cr > selfWith) {
                cl = 10;
                //如果需要换行，那么就应该 ct+上一行的最高的高度
                ct = ct + lastLineMaxHeight;
                lastLineMaxHeight = mH;

                //换行之后，重新计算控件的右侧和下方位置
                cr = cl+ mW+ width;
                cb = ct+ mH+ height;
            }else{
                //不换行 计算当前行最大行高
                lastLineMaxHeight = Math.max(mH,lastLineMaxHeight);
            }
            //设置子控件的位置信息
            childAt.layout(cl, ct, cr, cb);
            cl = cr;//下一个子控件的左侧，是当前子控件的右侧
        }
    }

    /**
     * 测量自身，以及自身内部的子控件的尺寸；
     * 注意事项，一定是调用父类的方法，或者调用 setMeasuredDimension()
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //ViewGroup 内部就有直接可以使用的测量方法
        measureChildren(widthMeasureSpec,heightMeasureSpec);

        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }
}
