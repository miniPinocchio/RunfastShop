package com.example.runfastshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.usercenter.UpdateMessageActivity;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.data.IntentFlag;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.VaUtils;
import com.example.supportv1.utils.ValidateUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.et_user_name)
    EditText mEtUserName;
    @BindView(R.id.et_code)
    EditText mEtCode;
    @BindView(R.id.tv_code)
    TextView mTvCode;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
        setListener();
    }

    private void setListener() {
        mEtUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString()) && ValidateUtil.isMobileNo(s.toString())) {
                    mBtnOk.setBackgroundResource(R.drawable.shape_button_pay);
                } else {
                    mBtnOk.setBackgroundResource(R.drawable.shape_button_pay_gary);
                }

            }
        });

    }

    @OnClick({R.id.tv_code, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                getAuthCode();
                break;
            case R.id.btn_ok:
                resetPassword();
                break;
        }
    }

    /***
     * 获取验证码
     */
    private void getAuthCode() {
        String accountName = mEtUserName.getText().toString().trim();
        //|| !VaUtils.isMobileNo(accountName) 手机号正则验证
        if (TextUtils.isEmpty(accountName) || !VaUtils.isMobileNo(accountName)) {
            CustomToast.INSTANCE.showToast(this, getString(R.string.please_input_correct_phone));
            return;
        }
        CustomApplication.getRetrofit().getForgetCode(accountName).enqueue(this);
    }

    /**
     * 重置密码
     */
    private void resetPassword() {
        String phone = mEtUserName.getText().toString().trim();
        String code = mEtCode.getText().toString().trim();

//        if (TextUtils.isEmpty(phone)) {
//            CustomToast.INSTANCE.showToast(this, "请输入手机号");
//            return;
//        }
//        if (TextUtils.isEmpty(code)) {
//            CustomToast.INSTANCE.showToast(this, "请输入验证码");
//            return;
//        }
        mIntent = new Intent(this, UpdateMessageActivity.class);
        mIntent.setFlags(IntentFlag.FORGOT_PWD);
        mIntent.putExtra("phone", phone);
        startActivity(mIntent);
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

    private void ResolveData(String data) {
        //验证码
        try {
            JSONObject object = new JSONObject(data);
            boolean success = object.getBoolean("success");
            String msg = object.getString("msg");
            if (success) {
                CustomToast.INSTANCE.showToast(this, msg);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
