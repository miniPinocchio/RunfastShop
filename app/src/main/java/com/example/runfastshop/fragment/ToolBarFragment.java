package com.example.runfastshop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.runfastshop.R;
import com.example.supportv1.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToolBarFragment extends BaseFragment {


    public ToolBarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.layout_title, container, false);
        return view;
    }

}
