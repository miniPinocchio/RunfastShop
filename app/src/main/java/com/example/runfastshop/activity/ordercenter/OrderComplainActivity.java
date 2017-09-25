package com.example.runfastshop.activity.ordercenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.order.OrderInfo;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;
import com.example.supportv1.utils.ValidateUtil;
import com.lljjcoder.citylist.Toast.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

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
    private OrderInfo mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_complain);
        ButterKnife.bind(this);
        getData();
    }

    public void getData() {
        mInfo = getIntent().getParcelableExtra("orderInfo");
    }

    @OnClick(R.id.btn_order_commit_info)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_order_commit_info:
                if (!TextUtils.isEmpty(mEtOrderComplaint.getText()) &&
                        mEtOrderComplaint.getText().toString().length() > 5) {
                    if (ValidateUtil.isEmail(mEtOrderComplaintEmail.getText().toString())) {
                        toCommitComments();
                    } else {
                        ToastUtils.showShortToast(this, "请填写正确的邮箱");
                    }
                } else {
                    ToastUtils.showShortToast(this, "投诉内容少于五个字");
                }
        }
    }

    private void toCommitComments() {
        Integer id = mInfo.getId();
        Integer businessId = mInfo.getBusinessId();
        User userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        String content = mEtOrderComplaint.getText().toString();
        String email = mEtOrderComplaintEmail.getText().toString();

        CustomApplication.getRetrofit().postOrderComplaint(userInfo.getId(), businessId, id,
                email, content).enqueue(this);
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
        CustomToast.INSTANCE.showToast(this, "网络异常");
    }


    /**
     * 解析数据
     *
     * @param data
     */
    private void ResolveData(String data) {
        try {
            JSONObject object = new JSONObject(data);
            boolean success = object.optBoolean("success");
            String msg = object.optString("msg");
            CustomToast.INSTANCE.showToast(this, msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
