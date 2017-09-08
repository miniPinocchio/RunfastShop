package com.example.runfastshop.activity.ordercenter;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.config.IntentConfig;
import com.lljjcoder.citylist.Toast.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author liuhui on 2017/08/29
 * @email liu594545591@126.com
 * @introduce 订单备注
 */
public class OrderRemarkActivity extends ToolBarActivity {

    @BindView(R.id.et_order_remark)
    EditText mEtOrderRemark;
    @BindView(R.id.btn_order_remark_commit)
    Button mBtnOrderRemarkCommit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_remark);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btn_order_remark_commit)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.btn_order_remark_commit:
                if (!TextUtils.isEmpty(mEtOrderRemark.getText())) {
                    Intent intent = new Intent();
                    intent.putExtra("order_remark", mEtOrderRemark.getText());
                    setResult(IntentConfig.REMARK_RESULT_CODE, intent);
                    finish();
                }else {
                    ToastUtils.showShortToast(this,"请填写备注内容");
                }
        }
    }
}
