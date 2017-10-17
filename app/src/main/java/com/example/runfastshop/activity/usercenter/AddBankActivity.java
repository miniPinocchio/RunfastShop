package com.example.runfastshop.activity.usercenter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;
import com.lljjcoder.citypickerview.widget.CityPicker;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 添加银行卡
 */
public class AddBankActivity extends ToolBarActivity implements Callback<String> {

    @BindView(R.id.et_bank_code)
    EditText etBankCode;
    @BindView(R.id.et_bank_user_name)
    EditText etBankUserName;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.btn_save_password)
    Button btnSavePassword;

    private int netType;
    private boolean bankCodeIsEmpty;
    private boolean bankNameIsEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);
        ButterKnife.bind(this);
        initData();
        setListener();
    }

    private void setListener() {
        etBankCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    bankCodeIsEmpty = true;
                } else {
                    bankCodeIsEmpty = false;
                }
                String bankName = tvBankName.getText().toString().trim();

                if (bankCodeIsEmpty && bankNameIsEmpty && !TextUtils.equals(bankName,"开户行")) {
                    btnSavePassword.setBackgroundResource(R.drawable.shape_button_pay);
                } else {
                    btnSavePassword.setBackgroundResource(R.drawable.shape_button_pay_gary);
                }
            }
        });

        etBankUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    bankNameIsEmpty = true;
                } else {
                    bankNameIsEmpty = false;
                }
                String bankName = tvBankName.getText().toString().trim();
                if (bankCodeIsEmpty && bankNameIsEmpty &&!TextUtils.equals(bankName,"开户行")) {
                    btnSavePassword.setBackgroundResource(R.drawable.shape_button_pay);
                } else {
                    btnSavePassword.setBackgroundResource(R.drawable.shape_button_pay_gary);
                }
            }
        });
    }

    private void initData() {
        etBankCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {// 此处为得到焦点时的处理内容
                    String code = etBankCode.getText().toString().trim();
                    if (!TextUtils.isEmpty(code)) {
                        getBankName(code);
                    }
                }
            }
        });
    }

    @OnClick({R.id.btn_save_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_save_password:
                addBank();
                break;
        }
    }

    /**
     * 获取银行
     *
     * @param
     */
    private void getBankName(String code) {
        netType = 1;
        CustomApplication.getRetrofit().getBankName(code).enqueue(this);
    }

    /**
     * 添加银行卡
     *
     * @param
     */
    private void addBank() {
        User userInfo = UserService.getUserInfo(this);
        String code = etBankCode.getText().toString().trim();
        String bankName = tvBankName.getText().toString().trim();
        String userName = etBankUserName.getText().toString().trim();
        if (userInfo == null){
            return;
        }
        netType = 2;
        CustomApplication.getRetrofit().addBank(userInfo.getId(),userName,bankName,code).enqueue(this);
    }

    /**
     * 弹出选择方式
     */
    private void showBankMode() {
        CityPicker cityPicker = new CityPicker.Builder(this)
                .textSize(16)
                .setData(new String[]{"中国银行", "中国工商银行", "中国建设银行"})
                .title("支付方式")
                .titleBackgroundColor("#ffffff")
                .titleTextColor("#333333")
                .backgroundPop(0xa0333333)
                .confirTextColor("#fc9153")
                .cancelTextColor("#999999")
                .textColor(Color.parseColor("#333333"))
                .itemPadding(10)
                .onlyShowProvinceAndCity(true)
                .build();
        cityPicker.show();

        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                String province = citySelected[0];

            }

            @Override
            public void onCancel() {
            }
        });
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        String data = response.body();
        if (response.isSuccessful()) {
            Log.d("params", "response = " + data);
            ResolveData(data);
        }
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        CustomToast.INSTANCE.showToast(this, "网络错误");
    }

    /**
     * 解析数据
     *
     * @param data
     */
    private void ResolveData(String data) {
        JSONObject object = null;
        try {
            object = new JSONObject(data);
            if (netType == 1) {
                boolean success = object.optBoolean("success");
                if (!success) {
                    String msg = object.optString("msg");
                    CustomToast.INSTANCE.showToast(this, msg);
                    return;
                }
                String bankName = object.optString("bankName");
                tvBankName.setText(bankName);
                tvBankName.setTextColor(getResources().getColor(R.color.color_address_black));
                return;
            }

            boolean success = object.optBoolean("success");
            String msg = object.optString("msg");
            if (!success){
                CustomToast.INSTANCE.showToast(this, msg);
                return;
            }
            Intent intent = new Intent();
            setResult(RESULT_OK,intent);
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
