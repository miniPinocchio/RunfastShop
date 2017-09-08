package com.example.runfastshop.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.adapter.MessageAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.message.MessageInfo;
import com.example.runfastshop.bean.message.MessageInfos;
import com.example.runfastshop.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment implements Callback<String> {


    Unbinder unbinder;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.rv_message_list)
    RecyclerView mRvMessageList;
    private Integer page = 1;
    private List<MessageInfo> mInfoList;
    private MessageAdapter mAdapter;

    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        toolbarTitle.setText("消息");
        initData();
        getNetData();
        return view;
    }

    private void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRvMessageList.setLayoutManager(manager);
        mInfoList = new ArrayList<>();
        mAdapter = new MessageAdapter(getActivity(),mInfoList);
        mRvMessageList.setAdapter(mAdapter);
    }

    /**
     * 获取消息
     */
    private void getNetData() {
//        Integer id = UserService.getUserInfo().getId();
        CustomApplication.getRetrofit().postMessageList(1, page, 10).enqueue(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
        MessageInfos messageInfos = GsonUtil.parseJsonWithGson(data, MessageInfos.class);
        mInfoList.addAll(messageInfos.getMessge());
        mAdapter.notifyDataSetChanged();
    }
}
