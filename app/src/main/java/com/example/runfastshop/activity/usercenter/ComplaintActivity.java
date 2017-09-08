package com.example.runfastshop.activity.usercenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

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

/**
 * 投诉
 */
public class ComplaintActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.layout_right_title)
    RelativeLayout mLayoutRightTitle;
    @BindView(R.id.et_complaint_count)
    EditText mEtComplaintCount;
    @BindView(R.id.et_complaint_phone)
    EditText mEtComplaintPhone;
    @BindView(R.id.btn_commit_info)
    Button mBtnCommitInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.layout_right_title, R.id.btn_commit_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_right_title:
                ToastUtils.showShortToast(this, "开发中。。。");
                break;
            case R.id.btn_commit_info:
                if (!TextUtils.isEmpty(mEtComplaintCount.getText()) &&
                        mEtComplaintCount.getText().toString().length() > 5) {
                    if (!TextUtils.isEmpty(mEtComplaintPhone.getText())) {
                        toCommitComments();
                    } else {
                        ToastUtils.showShortToast(this, "请填写邮箱");
                    }
                } else {
                    ToastUtils.showShortToast(this, "投诉内容少于五个字");
                }
                break;
        }
    }

    /**
     * 提交投诉
     */
    private void toCommitComments() {
        CustomApplication.getRetrofit().postMineComplaint("", "", "",
                mEtComplaintPhone.getText().toString()).enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

    }
}
