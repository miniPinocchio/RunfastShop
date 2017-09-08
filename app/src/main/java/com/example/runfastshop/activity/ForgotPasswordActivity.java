package com.example.runfastshop.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.VaUtils;

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
    private int netType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        ButterKnife.bind(this);
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
        netType = 1;
        CustomApplication.getRetrofit().getCode(accountName).enqueue(this);
    }

    /**
     * 账号注册
     */
    private void resetPassword() {
        String phone = mEtUserName.getText().toString().trim();
        String code = mEtCode.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            CustomToast.INSTANCE.showToast(this, "请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            CustomToast.INSTANCE.showToast(this, "请输入验证码");
            return;
        }
        netType = 2;
//        CustomApplication.getRetrofit().postRegister(phone, password, code).enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

    }
}
