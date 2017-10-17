package com.example.runfastshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.runfastshop.R;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;
import com.example.runfastshop.util.MD5Util;
import com.example.supportv1.utils.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.et_user_name)
    EditText etUserName;

    @BindView(R.id.et_user_password)
    EditText etUserPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_login, R.id.btn_register, R.id.tv_forgot_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.btn_register:
                startActivityForResult(new Intent(this, RegisterActivity.class), 1001);
                break;
            case R.id.tv_forgot_password:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
        }
    }

    /**
     * 账号注册
     */
    private void login() {
        String phone = etUserName.getText().toString().trim();
        String password = etUserPassword.getText().toString().trim();

        if (TextUtils.isEmpty(phone)) {
            CustomToast.INSTANCE.showToast(this, "请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            CustomToast.INSTANCE.showToast(this, "请输入密码");
            return;
        }
        LogUtil.d("password", MD5Util.MD5(password));
        CustomApplication.getRetrofit().postLogin(phone, MD5Util.MD5(password), 0).enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            String data = response.body();
            ResolveData(data);
        } else {
            CustomToast.INSTANCE.showToast(this, "请求失败");
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
            if (!success) {
                return;
            }
            JSONObject app_cuser = object.getJSONObject("app_cuser");
            User user = GsonUtil.parseJsonWithGson(app_cuser.toString(), User.class);
            user.setPassword(etUserPassword.getText().toString().trim());
            UserService.saveUserInfo(user);
            UserService.setAutoLogin("1");
            startActivity(new Intent(this,MainActivity.class));
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        String mobile = data.getStringExtra("mobile");
        String password = data.getStringExtra("password");
        etUserName.setText(mobile);
        etUserPassword.setText(password);
    }


}
