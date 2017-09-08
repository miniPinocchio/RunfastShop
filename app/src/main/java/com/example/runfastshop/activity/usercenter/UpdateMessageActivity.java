package com.example.runfastshop.activity.usercenter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.VaUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 短信验证码修改
 */
public class UpdateMessageActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_new_password_again)
    EditText etNewPasswordAgain;
    @BindView(R.id.btn_save_password)
    Button btnSavePassword;

    private boolean codeIsEmpty;
    private boolean newIsEmpty;
    private boolean newAgainIsEmpty;
    private User userInfo;
    private int netType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_message);
        ButterKnife.bind(this);
        initData();
        setListener();
    }

    private void initData() {
        userInfo = UserService.getUserInfo();
    }

    private void setListener() {
        etOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())){
                    codeIsEmpty = true;
                }else {
                    codeIsEmpty = false;
                }
                if (codeIsEmpty && newIsEmpty && newAgainIsEmpty){
                    btnSavePassword.setBackgroundResource(R.drawable.shape_button_pay);
                }else {
                    btnSavePassword.setBackgroundResource(R.drawable.shape_button_pay_gary);
                }
            }
        });
        etNewPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())){
                    newIsEmpty = true;
                }else {
                    newIsEmpty = false;
                }
                if (codeIsEmpty && newIsEmpty && newAgainIsEmpty){
                    btnSavePassword.setBackgroundResource(R.drawable.shape_button_pay);
                }else {
                    btnSavePassword.setBackgroundResource(R.drawable.shape_button_pay_gary);
                }
            }
        });
        etNewPasswordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())){
                    newAgainIsEmpty = true;
                }else {
                    newAgainIsEmpty = false;
                }
                if (codeIsEmpty && newIsEmpty && newAgainIsEmpty){
                    btnSavePassword.setBackgroundResource(R.drawable.shape_button_pay);
                }else {
                    btnSavePassword.setBackgroundResource(R.drawable.shape_button_pay_gary);
                }
            }
        });
    }

    @OnClick({R.id.tv_get_code, R.id.btn_save_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_code:
                getAuthCode();
                break;
            case R.id.btn_save_password:
                editPassword();
                break;
        }
    }

    /***
     * 获取验证码
     */
    private void getAuthCode() {
        String accountName = userInfo.getMobile();
        //|| !VaUtils.isMobileNo(accountName) 手机号正则验证
        if (TextUtils.isEmpty(accountName) || !VaUtils.isMobileNo(accountName)) {
            CustomToast.INSTANCE.showToast(this, getString(R.string.please_input_correct_phone));
            return;
        }
        netType = 1;
        CustomApplication.getRetrofit().getEditPwdCode(accountName).enqueue(this);
    }

    /**
     * 密码修改
     */
    private void editPassword() {
        String oldPwd = etOldPassword.getText().toString().trim();
        String newPwd = etNewPassword.getText().toString().trim();
        String newPwdAgain = etNewPasswordAgain.getText().toString().trim();

        if (TextUtils.isEmpty(oldPwd)){
            CustomToast.INSTANCE.showToast(this,"旧密码不能为空");
            return;
        }
        if (TextUtils.isEmpty(newPwd)){
            CustomToast.INSTANCE.showToast(this,"旧密码不能为空");
            return;
        }
        if (!TextUtils.equals(newPwd,newPwdAgain)){
            CustomToast.INSTANCE.showToast(this,"两次新密码输入不一致");
            return;
        }
        netType = 2;
        Integer id = UserService.getUserInfo().getId();
        CustomApplication.getRetrofit().updatePassword(id,oldPwd, newPwd,1,newPwdAgain).enqueue(this);
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

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
