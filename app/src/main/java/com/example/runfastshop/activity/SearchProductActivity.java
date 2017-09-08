package com.example.runfastshop.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.view.ZFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchProductActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.et_search_name)
    EditText etSearchName;
    @BindView(R.id.flow_layout)
    ZFlowLayout flowLayout;
    @BindView(R.id.recycler_search)
    RecyclerView recyclerSearch;

    private List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        ButterKnife.bind(this);
        initData();
        setListener();
    }

    private void setListener() {
        etSearchName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                searchGoods(s.toString());
            }
        });
    }

    private void initData() {
        data.add("鸡排");
        data.add("咖喱");
        data.add("麻辣烫");
        data.add("泡菜饼");
        data.add("肯德基宅急送");
        data.add("肯德基宅急送");
        data.add("肯德基宅急送");
        data.add("肯德基宅急送");
        data.add("肯德基宅急送");
        data.add("烧烤");
        data.add("烧烤");
        data.add("烧烤");
        data.add("烧烤");
        data.add("烧烤");
        addItem(data);

    }

    public void addItem(List<String> data) {

        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 10, 10);// 设置边距

        for (int i = 0; i < data.size(); i++) {
            TextView textView =new TextView(this);
            textView.setText(data.get(i));
            textView.setBackgroundResource(R.drawable.search_history_background);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            textView.setTextColor(getResources().getColor(R.color.color_text_gray_two));
            //TODO:能否添加成功
            flowLayout.addView(textView,layoutParams);
        }
    }

    /**
     * 搜索商品
     */
    private void searchGoods(String name) {
        CustomApplication.getRetrofit().searchGoods(name).enqueue(this);
    }

    @OnClick(R.id.tv_cancel_search)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        Log.d("params", "response = " + response.isSuccessful() + ",data = " + data);
        if (response.isSuccessful()) {
            ResolveData(data);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

    }

    private void ResolveData(String data) {

    }
}
