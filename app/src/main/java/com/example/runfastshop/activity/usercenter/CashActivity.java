package com.example.runfastshop.activity.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.CashBankInfo;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.GsonUtil;
import com.example.supportv1.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 提现
 */
public class CashActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.tv_bank_mode)
    TextView tvBankMode;
    @BindView(R.id.et_account_money)
    EditText etAccountMoney;
    @BindView(R.id.tv_wallet_money)
    TextView tvWalletMoney;

    private User userInfo;

    private int netType;

    private List<CashBankInfo> bankInfoList = new ArrayList<>();

    private Integer bankId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        userInfo = UserService.getUserInfo(this);
        if (userInfo == null) {
            return;
        }
        tvWalletMoney.setText(String.valueOf(userInfo.getRemainder()));
        getBankInfo();
    }

    @OnClick({R.id.tv_bank_mode, R.id.tv_cash_all, R.id.btn_cash_mode})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bank_mode:
                startActivityForResult(new Intent(this, SelectBankActivity.class).putExtra("bankInfo",(ArrayList)bankInfoList),1001);
                break;
            case R.id.tv_cash_all:
                etAccountMoney.setText(String.valueOf(userInfo.getRemainder()));
                break;
            case R.id.btn_cash_mode:
                sendCash();
                break;
        }
    }
    /**
     * 提现账号
     * @param
     */
    private void getBankInfo() {
        netType = 1;
        CustomApplication.getRetrofit().getWathdrawallList(userInfo.getId()).enqueue(this);
    }

    /**
     * 提现
     * @param
     */
    private void sendCash() {
        String money = etAccountMoney.getText().toString().trim();
        if (TextUtils.isEmpty(money)){
            CustomToast.INSTANCE.showToast(this,"请输入提现金额");
            return;
        }
        if (bankId == null){
            CustomToast.INSTANCE.showToast(this,"请选择提现银行卡");
            return;
        }
        netType = 2;
        CustomApplication.getRetrofit().getCashSend(userInfo.getId(),Double.parseDouble(money),2,bankId).enqueue(this);
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            Log.d("params","response = "+data);
            ResolveData(data);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        CustomToast.INSTANCE.showToast(this, "网络错误");
    }

    /**
     * 解析数据
     * @param data
     */
    private void ResolveData(String data) {
        LogUtil.d("修改", data);
        JSONObject object = null;
        try {
            object = new JSONObject(data);
            if (netType == 1){
                JSONArray banks = object.getJSONArray("banks");
                int length = banks.length();
                if (length <= 0){
                    tvBankMode.setText("请添加银行卡");
                    return;
                }
                for (int i = 0; i < length; i++) {
                    JSONObject jsonObject = banks.getJSONObject(i);
                    CashBankInfo bankInfo = GsonUtil.parseJsonWithGson(jsonObject.toString(),CashBankInfo.class);
                    if (i == 0){
                        bankInfo.setSelect(true);
                    }
                    bankInfoList.add(bankInfo);
                }
                String account = bankInfoList.get(0).getAccount();
                if (account.length() > 4){
                    account = account.substring(account.length()-4);
                }
                tvBankMode.setText(bankInfoList.get(0).getBanktype()+"("+account+")");
                bankId = bankInfoList.get(0).getId();
                return;
            }
            boolean success = object.optBoolean("success");
            String msg= object.optString("msg");
            CustomToast.INSTANCE.showToast(this, msg);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){
            return;
        }
        CashBankInfo bankInfo = (CashBankInfo) data.getSerializableExtra("bank");
        if (bankInfo != null) {
            int size = bankInfoList.size();
            for (int i = 0; i < size; i++) {
                if (bankInfoList.get(i).getId().equals(bankInfo.getId())){
                    bankInfoList.get(i).setSelect(true);
                }else {
                    bankInfoList.get(i).setSelect(false);
                }
            }
            String account = bankInfo.getAccount();
            if (account.length() > 4){
                account = account.substring(account.length()-4);
            }
            tvBankMode.setText(bankInfo.getBanktype()+"("+account+")");
            bankId = bankInfo.getId();
        }
    }
}
