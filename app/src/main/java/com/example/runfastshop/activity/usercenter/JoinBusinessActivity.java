package com.example.runfastshop.activity.usercenter;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.util.CustomToast;
import com.lljjcoder.citylist.Toast.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 加盟
 */
public class JoinBusinessActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.et_business_name)
    EditText mEtBusinessName;
    @BindView(R.id.et_business_phone)
    EditText mEtBusinessPhone;
    @BindView(R.id.et_business_usr_name)
    EditText mEtBusinessUsrName;
    @BindView(R.id.et_business_usr_address)
    EditText mEtBusinessUsrAddress;
    @BindView(R.id.et_business_remarks)
    EditText mEtBusinessRemarks;
    @BindView(R.id.et_business_join_address)
    EditText mEtBusinessJoinAddress;
    @BindView(R.id.btn_commit_info)
    Button mBtnCommitInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_business);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_commit_info)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_commit_info:
            if (TextUtils.isEmpty(mEtBusinessName.getText().toString())){
                ToastUtils.showShortToast(this,"请填入商家名称");
                return;
            }else if (TextUtils.isEmpty(mEtBusinessPhone.getText().toString())){
                ToastUtils.showShortToast(this,"请填入商家电话");
                return;
            }else if (TextUtils.isEmpty(mEtBusinessUsrAddress.getText().toString())){
                ToastUtils.showShortToast(this,"请填入商家地址");
                return;
            }else if (TextUtils.isEmpty(mEtBusinessJoinAddress.getText().toString())){
                ToastUtils.showShortToast(this,"请填入加盟地");
                return;
            }else {
                toJoinLeague();
            }
                break;
        }
    }

    /**
     * 加盟
     */
    private void toJoinLeague() {
        CustomApplication.getRetrofit().postJoinLeague(
                mEtBusinessName.getText().toString(),
                mEtBusinessPhone.getText().toString(),
                TextUtils.isEmpty(mEtBusinessUsrName.getText().toString())?"":mEtBusinessUsrName.getText().toString(),
                mEtBusinessUsrAddress.getText().toString(),
                TextUtils.isEmpty(mEtBusinessRemarks.getText().toString())?"":mEtBusinessRemarks.getText().toString(),
                mEtBusinessJoinAddress.getText().toString()
                ).enqueue(this);
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

    }

    private void ResolveData(String data) {
        try {
            JSONObject object = new JSONObject(data);
            String succ = object.optString("succ");
            CustomToast.INSTANCE.showToast(this,succ);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
