package com.example.runfastshop.activity.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 密码修改方式
 */
public class UpdatePasswordActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.layout_update_old_pwd, R.id.layout_update_code_pwd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_update_old_pwd:
                startActivity(new Intent(this,UpdateOldPwdActivity.class));
                break;
            case R.id.layout_update_code_pwd:
                startActivity(new Intent(this,UpdateMessageActivity.class));
                break;
        }
    }
}
