package com.example.runfastshop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ordercenter.OrderDetailActivity;
import com.example.runfastshop.adapter.OrderListAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.order.OrderInfo;
import com.example.runfastshop.bean.order.OrderInfos;
import com.example.runfastshop.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 订单页
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment implements OrderListAdapter.OnClickListener, Callback<String> {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    Unbinder unbinder;
    @BindView(R.id.recycler_order)
    RecyclerView recyclerView;

    @BindView(R.id.layout_not_order)
    RelativeLayout layoutNotOrder;

    private List<OrderInfo> mOrderInfos = new ArrayList<>();
    private OrderListAdapter adapter;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbarTitle.setText("订单");
        initData();
        initEvent();
        getOrderList();
        return view;
    }

    private void initData() {
        if (mOrderInfos.size() <= 0) {
            layoutNotOrder.setVisibility(View.VISIBLE);
        }
        adapter = new OrderListAdapter(mOrderInfos, getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(adapter);
    }


    private void initEvent() {
//        recyclerView.addon
    }

    @Override
    public void onResume() {
        super.onResume();
        getOrderList();
    }

    /**
     *
     */
    private void getOrderList() {
//        UserService.getUserInfo().getId();
        CustomApplication.getRetrofit().postOrderList(488993, 1, 10).enqueue(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_place_order)
    public void onViewClicked() {

    }

    @Override
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
        intent.putExtra("orderInfo", mOrderInfos.get(position));
        startActivity(intent);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            ResolveData(data);
        }
    }


    @Override
    public void onFailure(Call<String> call, Throwable t) {

    }

    private void ResolveData(String data) {
        OrderInfos orderInfos = GsonUtil.parseJsonWithGson(data, OrderInfos.class);
        if (orderInfos.getRows().size() > 0) {
            layoutNotOrder.setVisibility(View.GONE);
        }
        mOrderInfos.addAll(orderInfos.getRows());
        adapter.notifyDataSetChanged();
    }
}
