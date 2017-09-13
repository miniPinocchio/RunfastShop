package com.example.runfastshop.fragment.walletfragmnet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.runfastshop.R;
import com.example.runfastshop.adapter.CashRecordAdapter;
import com.example.runfastshop.bean.spend.AccountRecord;
import com.example.runfastshop.bean.spend.AccountRecords;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 全部
 * A simple {@link Fragment} subclass.
 */
public class MoneyAllFragment extends Fragment implements View.OnClickListener {


    @BindView(R.id.view_money_list)
    RecyclerView recyclerView;
    Unbinder unbinder;

    private List<AccountRecord> data;
    private AccountRecords mRecords;

    public MoneyAllFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_money_all, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        data = new ArrayList<>();
        Bundle arguments = getArguments();
        if (arguments != null) {
            mRecords = arguments.getParcelable("record");
            if (mRecords.getRows() != null) {
                // TODO 处理是收入还是开支
                data.addAll(mRecords.getRows());
            }
        } else {
            recyclerView.setVisibility(View.GONE);
        }
        CashRecordAdapter allAdapter = new CashRecordAdapter(data, getActivity(), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(allAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {

    }
}
