package com.example.runfastshop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.runfastshop.R;
import com.example.runfastshop.adapter.moneyadapter.CashCouponAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 代金券页
 * A simple {@link Fragment} subclass.
 */
public class CashCouponsFragment extends Fragment {


    @BindView(R.id.recycler_cash_coupons)
    RecyclerView recyclerView;

    Unbinder unbinder;

    private List<String> data = new ArrayList<>();

    public CashCouponsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cash_coupons, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            data.add(""+i);
        }
        CashCouponAdapter adapter = new CashCouponAdapter(data,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
