package com.example.supportv1.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/12/1.
 */
public class TubatuAdapter extends RecyclingPagerAdapter{

    private List<String> mList;
    private Context mContext;
    private List<String> strList;

    public TubatuAdapter(Context context,List<String> strList) {
        mList = new ArrayList<>();
        mContext = context;
        this.strList = strList;

    }

    public void addAll(List<String> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup container) {
        TextView imageView = null;
        if (convertView == null) {
            imageView = new TextView(mContext);
        } else {
            imageView = (TextView) convertView;
        }
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        imageView.setTag(position);
        imageView.setText(mList.get(position));
        imageView.setGravity(Gravity.CENTER);
        imageView.setEllipsize(TextUtils.TruncateAt.END);
        imageView.setSingleLine(true);
        imageView.setTextSize(16);

        return imageView;
    }

    @Override
    public int getCount() {
        return mList.size();
    }
}
