package com.example.runfastshop.activity.usercenter;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 原密码验证修改
 */
public class UpdateOldPwdActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.et_new_password_again)
    EditText etNewPasswordAgain;
    @BindView(R.id.btn_save_password)
    Button btnSavePassword;

    private boolean oldIsEmpty;
    private boolean newIsEmpty;
    private boolean newAgainIsEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_old_pwd);
        ButterKnife.bind(this);
        setListener();
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
                    oldIsEmpty = true;
                }else {
                    oldIsEmpty = false;
                }
                if (oldIsEmpty && newIsEmpty && newAgainIsEmpty){
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
                if (oldIsEmpty && newIsEmpty && newAgainIsEmpty){
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
                if (oldIsEmpty && newIsEmpty && newAgainIsEmpty){
                    btnSavePassword.setBackgroundResource(R.drawable.shape_button_pay);
                }else {
                    btnSavePassword.setBackgroundResource(R.drawable.shape_button_pay_gary);
                }
            }
        });
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
        Integer id = UserService.getUserId(getApplicationContext());
        CustomApplication.getRetrofit().updatePassword(id,oldPwd, newPwd,0,newPwdAgain).enqueue(this);
    }

    @OnClick(R.id.btn_save_password)
    public void onViewClicked() {
        editPassword();
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()){
            String data = response.body();
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
            CustomToast.INSTANCE.showToast(this,msg);
            if (success){
                finish();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
