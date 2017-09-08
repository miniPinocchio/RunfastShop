package com.example.runfastshop.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.runfastshop.R;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */

public class BusinessAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    private List<String> mStrings;

    public BusinessAdapter(FragmentManager fm, List<Fragment> mFragments, List<String> mStrings) {
        super(fm);
        this.mFragments = mFragments;
        this.mStrings = mStrings;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        int ret = 0;
        if(mFragments!=null){
            ret = mFragments.size();
        }
        return ret;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mStrings.get(position);
    }
}
