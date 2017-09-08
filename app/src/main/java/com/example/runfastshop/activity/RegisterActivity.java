package com.example.runfastshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.impl.NetResultThread;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.CustomUtils;
import com.example.runfastshop.util.VaUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.et_user_name)
    EditText etUserName;
    @BindView(R.id.et_user_password)
    EditText etUserPassword;
    @BindView(R.id.et_user_password_again)
    EditText etUserPasswordAgain;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_code)
    TextView tvGetCode;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private int netType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {

    }

    @OnClick({R.id.tv_code, R.id.btn_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_code:
                getAuthCode();
                break;
            case R.id.btn_register:
                registerUser();
                break;
        }
    }

    /***
     * 获取验证码
     */
    private void getAuthCode() {
        String accountName = etUserName.getText().toString().trim();
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
    private void registerUser() {
        String phone = etUserName.getText().toString().trim();
        String password = etUserPassword.getText().toString().trim();
        String passwordAgain = etUserPasswordAgain.getText().toString().trim();
        String code = etCode.getText().toString().trim();

        if (TextUtils.isEmpty(phone)){
            CustomToast.INSTANCE.showToast(this,"请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(code)){
            CustomToast.INSTANCE.showToast(this,"请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(password)){
            CustomToast.INSTANCE.showToast(this,"请输入密码");
            return;
        }
        if (!TextUtils.equals(password,passwordAgain)){
            CustomToast.INSTANCE.showToast(this,"两次密码输入不相同");
            return;
        }

        netType = 2;
        CustomApplication.getRetrofit().postRegister(phone,password,code).enqueue(this);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1002:
                    int second = msg.arg1;
                    tvGetCode.setText(second + "秒后可发送");
                    second -= 1;
                    if (second < 1) {
                        tvGetCode.setText("获取验证码");
                        //tvGetCode.setBackgroundResource(R.drawable.shape_button_orange);
                        tvGetCode.setEnabled(true);
                        return;
                    } else {
                        tvGetCode.setEnabled(false);
                    }
                    Message secondMsg = obtainMessage();
                    secondMsg.what = 1002;
                    secondMsg.arg1 = second;
                    sendMessageDelayed(secondMsg, 1000);
                    break;
            }
        }
    };

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
            if (!success){
                CustomToast.INSTANCE.showToast(this, msg);
                return;
            }
            if (netType == 1){
                Message message = handler.obtainMessage();
                message.what = 1002;
                message.arg1 = 59;
                message.sendToTarget();
            }
            if (netType == 2){
                CustomToast.INSTANCE.showToast(this,msg);
                String phone = etUserName.getText().toString().trim();
                String password = etUserPassword.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("mobile",phone);
                intent.putExtra("password",password);
                setResult(RESULT_OK,intent);
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){
            String data = response.body();
            Log.d("params", "response = " + data);
            ResolveData(data);
        }else {
            CustomToast.INSTANCE.showToast(this,"请求失败");
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        CustomToast.INSTANCE.showToast(this,"网络异常");
    }
}
