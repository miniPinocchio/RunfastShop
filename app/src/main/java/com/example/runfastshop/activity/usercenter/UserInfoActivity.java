package com.example.runfastshop.activity.usercenter;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.data.IntentFlag;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserInfoActivity extends ToolBarActivity {

    @BindView(R.id.tv_address_button)
    TextView tvAddressButton;
    @BindView(R.id.tv_user_nickname)
    TextView tvUserNickname;
    @BindView(R.id.tv_user_phone)
    TextView tvUserPhone;
    @BindView(R.id.tv_user_email)
    TextView tvUserEmail;
    @BindView(R.id.tv_update_password)
    TextView tvUpdatePassword;
    @BindView(R.id.ll_nickname)
    LinearLayout mLlNickname;
    private User userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        userInfo = UserService.getUserInfo();
        updateUi();
    }

    private void updateUi() {
        tvUserNickname.setText(TextUtils.isEmpty(userInfo.getNickname()) ? userInfo.getMobile() : userInfo.getNickname());
        tvUserPhone.setText(TextUtils.isEmpty(userInfo.getMobile())? "添加" : userInfo.getMobile());
        tvUserEmail.setText(TextUtils.isEmpty(userInfo.getEmail())? "添加" : userInfo.getEmail());
    }

    @OnClick({R.id.tv_address_button, R.id.tv_user_email,R.id.tv_update_password, R.id.layout_exit, R.id.ll_nickname})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_address_button://地址管理
                startActivityForResult(new Intent(this, AddressSelectActivity.class), 1001);
                break;
            case R.id.tv_update_password://密码管理
                startActivity(new Intent(this, UpdatePasswordActivity.class));
                break;
            case R.id.layout_exit://
                exitLogin();
                break;
            case R.id.ll_nickname://昵称
                Intent intent = new Intent(this, ChangeNameActivity.class);
                intent.setFlags(IntentFlag.EDIT_NICKNAME);
                intent.putExtra("userInfo", userInfo);
                startActivityForResult(intent, 1002);
                break;
            case R.id.tv_user_email://邮箱
                intent = new Intent(this, ChangeNameActivity.class);
                intent.setFlags(IntentFlag.EDIT_EMAIL);
                intent.putExtra("userInfo", userInfo);
                startActivityForResult(intent, 1003);
                break;
        }
    }

    // 是否确定退出登录
    private void exitLogin() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.prompt));
        builder.setMessage("确定退出登录？");
        String ok = getResources().getString(R.string.update_tip_confrim);
        builder.setPositiveButton(ok, new ExitLoginImpl());
        builder.setNegativeButton(getString(R.string.cancel), null);
        Dialog alertDialog = builder.create();
        alertDialog.show();
    }


    /**
     * 退出登录
     */
    private class ExitLoginImpl implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            UserService.setAutoLogin("0");
            UserService.saveUserInfo(new User());
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == RESULT_OK) {
            initData();
        } else if (requestCode == 1002 && resultCode == RESULT_OK) {
            initData();
        } else if (requestCode == 1003 && resultCode == RESULT_OK) {
            initData();
        }
    }
}
