package com.example.runfastshop.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.runfastshop.R;
import com.example.runfastshop.fragment.MessageFragment;
import com.example.runfastshop.fragment.MineFragment;
import com.example.runfastshop.fragment.OrderFragment;
import com.example.runfastshop.fragment.TakeOutFoodFragment;
import com.example.runfastshop.util.ActivityManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ToolBarActivity implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.frame_container)
    FrameLayout frameContainer;

    @BindView(R.id.main_tab_bar)
    RadioGroup mainGroup;

    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ActivityManager.pushActivity(this);
        //toolbar.setNavigationIcon(null);
        initDate(savedInstanceState);
    }

    private void initDate(Bundle savedInstanceState) {
        //mFragments = mFragments;
        mFragments = new ArrayList<>();
        FragmentManager manager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            Fragment fragment = new TakeOutFoodFragment();
            mFragments.add(fragment);
            fragment = new MessageFragment();
            mFragments.add(fragment);
            fragment = new OrderFragment();
            mFragments.add(fragment);
            fragment = new MineFragment();
            mFragments.add(fragment);

            FragmentTransaction transaction = manager.beginTransaction();
            int index = 0;
            for (Fragment f : mFragments) {
                transaction.add(R.id.frame_container, f, "tag" + index);
                transaction.hide(f);
                index++;
            }
            transaction.show(mFragments.get(0));
            transaction.commit();
        } else {
            for (int i = 0; i < 4; i++) {
                String tag = "tag" + i;
                Fragment fragmentByTag = manager.findFragmentByTag(tag);
                if (fragmentByTag != null) {
                    mFragments.add(fragmentByTag);
                }
            }
        }
        if (mainGroup != null) {
            mainGroup.setOnCheckedChangeListener(this);
        }
    }

    private void chekedFragment(int index) {
        Bundle bundle = new Bundle();
        if (index >= 0 && index < mFragments.size()) {
            int size = mFragments.size();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            for (int i = 0; i < size; i++) {
                Fragment fragment = mFragments.get(i);
                if (index == i) {
                    transaction.show(fragment);
                } else {
                    transaction.hide(fragment);
                }
            }
            transaction.commit();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int index = 0;
        switch (checkedId) {
            case R.id.main_tab_food:
                index = 0;
                break;
            case R.id.main_tab_message:
                index = 1;
                break;
            case R.id.main_tab_order:
                index = 2;
                break;
            case R.id.main_tab_user:
                index = 3;
                break;
        }
        chekedFragment(index);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.popActivity(this);
    }
}
