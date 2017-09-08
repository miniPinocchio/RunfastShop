package com.example.runfastshop.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.runfastshop.R;
import com.example.runfastshop.activity.BusinessActivity;
import com.example.runfastshop.adapter.shopcaradater.FoodAdapter;
import com.example.runfastshop.adapter.shopcaradater.TypeAdapter;
import com.example.runfastshop.bean.FoodBean;
import com.example.runfastshop.bean.TypeBean;
import com.example.runfastshop.util.ViewUtils;
import com.example.runfastshop.view.AddWidget;
import com.example.runfastshop.view.ListContainer;
import com.example.runfastshop.view.SimpleDividerDecoration;
import com.shizhefei.fragment.LazyFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 商家
 * A simple {@link Fragment} subclass.
 */
public class BusinessFragment extends LazyFragment {

    public TypeAdapter typeAdapter;
    private RecyclerView recyclerView2;
    private LinearLayoutManager linearLayoutManager;
    private List<FoodBean> foodBeanList = new ArrayList<>();
    private List<TypeBean> types = new ArrayList<>();
    private boolean move;
    private int index;
    public FoodAdapter foodAdapter;
    private boolean sIsScrolling;


    public BusinessFragment() {
        // Required empty public constructor
    }


    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_business);
        //listContainer = (ListContainer) findViewById(R.id.listcontainer);
        //listContainer.setAddClick((BusinessActivity) getActivity(),(BusinessActivity) getActivity());
        initData();
        setAddClick((BusinessActivity) getActivity(),(BusinessActivity) getActivity());
    }

    private void initData() {
        RecyclerView recyclerView1 = (RecyclerView) findViewById(R.id.recycler1);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));
        typeAdapter = new TypeAdapter(types);
        View view = new View(getContext());
        view.setMinimumHeight(ViewUtils.dip2px(getContext(), 50));
        typeAdapter.addFooterView(view);
        typeAdapter.bindToRecyclerView(recyclerView1);
        recyclerView1.addItemDecoration(new SimpleDividerDecoration(getContext()));
        ((DefaultItemAnimator) recyclerView1.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView1.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                if (recyclerView2.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
                    typeAdapter.fromClick = true;
                    typeAdapter.setChecked(i);
                    String type = view.getTag().toString();
                    for (int ii = 0; ii < foodBeanList.size(); ii++) {
                        FoodBean typeBean = foodBeanList.get(ii);
                        if (typeBean.getType().equals(type)) {
                            index = ii;
                            moveToPosition(index);
                            break;
                        }
                    }
                }
            }
        });
        recyclerView2 = (RecyclerView) findViewById(R.id.recycler2);
        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView2.setLayoutManager(linearLayoutManager);
        ((DefaultItemAnimator) recyclerView2.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    private void moveToPosition(int n) {
        //先从RecyclerView的LayoutManager中获取第一项和最后一项的Position
        int firstItem = linearLayoutManager.findFirstVisibleItemPosition();
        int lastItem = linearLayoutManager.findLastVisibleItemPosition();
        //然后区分情况
        if (n <= firstItem) {
            //当要置顶的项在当前显示的第一个项的前面时
            recyclerView2.scrollToPosition(n);
        } else if (n <= lastItem) {
            //当要置顶的项已经在屏幕上显示时
            int top = recyclerView2.getChildAt(n - firstItem).getTop();
            recyclerView2.scrollBy(0, top);
        } else {
            //当要置顶的项在当前显示的最后一项的后面时
            recyclerView2.scrollToPosition(n);
            //这里这个变量是用在RecyclerView滚动监听里面的
            move = true;
        }
    }

    public void setAddClick(AddWidget.OnAddClick onAddClick, View.OnClickListener listener) {
        foodAdapter = new FoodAdapter(foodBeanList, onAddClick,getContext(),listener);
        View view = new View(getContext());
        view.setMinimumHeight(ViewUtils.dip2px(getContext(), 50));
        foodAdapter.addFooterView(view);
        foodAdapter.bindToRecyclerView(recyclerView2);
        final View stickView = findViewById(R.id.stick_header);
        final TextView tvStickyHeaderView = (TextView) stickView.findViewById(R.id.tv_header);
        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING || newState == RecyclerView.SCROLL_STATE_SETTLING) {
                    sIsScrolling = true;
                    Glide.with(getActivity()).pauseRequests();
                } else if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (sIsScrolling == true) {
                        Glide.with(getActivity()).resumeRequests();
                    }
                    sIsScrolling = false;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (move) {
                    move = false;
                    //获取要置顶的项在当前屏幕的位置，mIndex是记录的要置顶项在RecyclerView中的位置
                    int n = index - linearLayoutManager.findFirstVisibleItemPosition();
                    if (0 <= n && n < recyclerView.getChildCount()) {
                        //获取要置顶的项顶部离RecyclerView顶部的距离
                        int top = recyclerView.getChildAt(n).getTop();
                        //最后的移动
                        recyclerView.smoothScrollBy(0, top);
                    }
                } else {
                    View stickyInfoView = recyclerView.findChildViewUnder(stickView.getMeasuredWidth() / 2, 5);
                    if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                        tvStickyHeaderView.setText(String.valueOf(stickyInfoView.getContentDescription()));
                        typeAdapter.setType(String.valueOf(stickyInfoView.getContentDescription()));
                    }

                    View transInfoView = recyclerView.findChildViewUnder(stickView.getMeasuredWidth() / 2, stickView.getMeasuredHeight
                            () + 1);
                    if (transInfoView != null && transInfoView.getTag() != null) {
                        int transViewStatus = (int) transInfoView.getTag();
                        int dealtY = transInfoView.getTop() - stickView.getMeasuredHeight();
                        if (transViewStatus == FoodAdapter.HAS_STICKY_VIEW) {
                            if (transInfoView.getTop() > 0) {
                                stickView.setTranslationY(dealtY);
                            } else {
                                stickView.setTranslationY(0);
                            }
                        } else if (transViewStatus == FoodAdapter.NONE_STICKY_VIEW) {
                            stickView.setTranslationY(0);
                        }
                    }
                }
            }
        });
    }


    public FoodAdapter getFoodAdapter(){
        return foodAdapter;
    }
    public TypeAdapter getTypeAdapter(){
        return typeAdapter;
    }

    public List<FoodBean> getFoodBeanList(){
        return foodBeanList;
    }
    public List<TypeBean>  getTypeBeanList(){
        return types;
    }
}
