package com.example.runfastshop.activity.ordercenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.application.CustomApplication;
import com.lljjcoder.citylist.Toast.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderComplainActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.et_order_complaint)
    EditText mEtOrderComplaint;
    @BindView(R.id.et_order_complaint_email)
    EditText mEtOrderComplaintEmail;
    @BindView(R.id.btn_order_commit_info)
    Button mBtnOrderCommitInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complain);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_order_commit_info)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_order_commit_info:
                if (!TextUtils.isEmpty(mEtOrderComplaint.getText()) &&
                        mEtOrderComplaint.getText().toString().length() > 5) {
                    if (!TextUtils.isEmpty(mEtOrderComplaintEmail.getText())) {
                        toCommitComments();
                    } else {
                        ToastUtils.showShortToast(this, "请填写邮箱");
                    }
                } else {
                    ToastUtils.showShortToast(this, "投诉内容少于五个字");
                }
        }
    }

    private void toCommitComments() {
        CustomApplication.getRetrofit().postMineComplaint("", "", "",
                mEtOrderComplaintEmail.getText().toString()).enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

    }
}
