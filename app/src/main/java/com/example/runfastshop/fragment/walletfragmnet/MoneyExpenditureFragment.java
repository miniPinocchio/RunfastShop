package com.example.runfastshop.fragment.walletfragmnet;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.runfastshop.R;

/**
 * 支出
 * A simple {@link Fragment} subclass.
 */
public class MoneyExpenditureFragment extends Fragment {


    public MoneyExpenditureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_money_expenditure, container, false);
    }

}
