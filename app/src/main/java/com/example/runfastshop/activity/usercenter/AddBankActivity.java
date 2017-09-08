package com.example.runfastshop.activity.usercenter;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.lljjcoder.citypickerview.widget.CityPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加银行卡
 */
public class AddBankActivity extends ToolBarActivity {

    @BindView(R.id.et_bank_code)
    EditText etBankCode;
    @BindView(R.id.et_bank_user_name)
    EditText etBankUserName;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bank);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.layout_select_bank, R.id.btn_save_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_select_bank:
                showBankMode();
                break;
            case R.id.btn_save_password:
                break;
        }
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
                tvBankName.setText(province);
                tvBankName.setTextColor(getResources().getColor(R.color.color_address_black));
            }

            @Override
            public void onCancel() {
            }
        });
    }
}
