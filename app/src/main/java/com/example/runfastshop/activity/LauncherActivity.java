package com.example.runfastshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.example.runfastshop.R;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;
import com.example.runfastshop.util.MD5Util;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LauncherActivity extends AppCompatActivity implements Callback<String> {

    private User userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        userInfo = UserService.getUserInfo(this);
//        if (userInfo != null) {
//            startActivity(new Intent(LauncherActivity.this, MainActivity.class));
//            finish();
//        }
    }

    private void initData() {
        if (UserService.isAutoLogin()) {
            handler.sendEmptyMessageDelayed(2001, 2000);
        } else {
            handler.sendEmptyMessageDelayed(2002, 2000);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2001:
                    login();
                    break;
                case 2002:
                    startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
                    finish();
                    break;
            }
        }
    };
    private void login() {
        userInfo = UserService.getUserInfo(this);
        if (userInfo != null) {
            CustomApplication.getRetrofit().postLogin(userInfo.getMobile(), MD5Util.MD5(userInfo.getPassword()), 0).enqueue(this);
        } else {
            startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
            finish();
        }
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if (response.isSuccessful()) {
            String data = response.body();
            ResolveData(data);
        } else {
            CustomToast.INSTANCE.showToast(this, "请求失败");
            startActivity(new Intent(LauncherActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        startActivity(new Intent(LauncherActivity.this, MainActivity.class));
        finish();
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
            if (!success) {
                CustomToast.INSTANCE.showToast(this, msg);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }
            JSONObject app_cuser = object.getJSONObject("app_cuser");
            User user = GsonUtil.parseJsonWithGson(app_cuser.toString(), User.class);
            user.setPassword(userInfo.getPassword());
            UserService.saveUserInfo(user);
            startActivity(new Intent(LauncherActivity.this, MainActivity.class));
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
