package com.example.runfastshop.adapter.shopcaradater;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.util.ViewUtils;
import com.shizhefei.view.indicator.IndicatorViewPager;

import java.util.List;

/**
 * Created by 天上白玉京 on 2017/8/3.
 */

public class ViewpagerAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {

    private LayoutInflater inflater;
    private int padding;
    private String[] tabs = new String[]{"商品", "评价","商家"};
    private Context mContext;

    private List<Fragment> mFragments;

    public ViewpagerAdapter(Context context,FragmentManager fragmentManager,List<Fragment> mFragments) {
        super(fragmentManager);
        mContext =context;
        this.mFragments = mFragments;
        inflater = LayoutInflater.from(mContext);
        padding = ViewUtils.dip2px(mContext, 20);
    }

    @Override
    public int getCount() {
        return tabs.length;
    }

    @Override
    public View getViewForTab(int i, View convertView, ViewGroup viewGroup) {
        convertView = inflater.inflate(R.layout.item_textview, viewGroup, false);
        TextView textView = (TextView) convertView;
        textView.setText(tabs[i]); //名称
        textView.setPadding(padding, 0, padding, 0);
        return convertView;
    }

    @Override
    public Fragment getFragmentForPage(int position) {
        return mFragments.get(position);
    }
}
