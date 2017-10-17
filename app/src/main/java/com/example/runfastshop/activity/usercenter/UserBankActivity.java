package com.example.runfastshop.activity.usercenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.ToolBarActivity;
import com.example.runfastshop.adapter.moneyadapter.CashUserNameAdapter;
import com.example.runfastshop.application.CustomApplication;
import com.example.runfastshop.bean.CashBankInfo;
import com.example.runfastshop.bean.user.User;
import com.example.runfastshop.config.UserService;
import com.example.runfastshop.util.CustomToast;
import com.example.runfastshop.util.CustomUtils;
import com.example.runfastshop.util.GsonUtil;
import com.example.runfastshop.view.PromptDialogFragment;
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
 * 提现账号列表
 */
public class UserBankActivity extends ToolBarActivity implements View.OnClickListener, Callback<String> {

    @BindView(R.id.view_money_list)
    RecyclerView recyclerView;

    private List<CashBankInfo> bankInfoList = new ArrayList<>();

    private CashUserNameAdapter adapter;
    private User userInfo;
    private int netType;
    private PromptDialogFragment dialogFragment;
    private CashBankInfo bankInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bank);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        dialogFragment = PromptDialogFragment.newInstance(getString(R.string.prompt), getString(R.string.delete_bank));
        dialogFragment.setLeftButton(getString(R.string.cancel),new GoToTripImpl());
        dialogFragment.setRightButton(getString(R.string.sure), new GoToTripImpl());
    }

    private void initData() {
        userInfo = UserService.getUserInfo(this);
        adapter = new CashUserNameAdapter(bankInfoList,this,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        getBankInfo();
    }

    @OnClick(R.id.tv_add_bank)
    public void onViewClicked() {
        startActivityForResult(new Intent(this,AddBankActivity.class),1001);
    }

    @Override
    public void onClick(View v) {
        if (v != null) {
            Integer position = (Integer) v.getTag();
            bankInfo = bankInfoList.get(position);
            CustomUtils.showDialogFragment(getSupportFragmentManager(), dialogFragment);
        }
    }

    /***
     * 进入行程的点击事件
     */
    private class GoToTripImpl implements PromptDialogFragment.OnClickApi {

        @Override
        public void onClick(DialogFragment dialogFragment, View view) {
            switch (view.getId()){
                case R.id.tv_ok:
                    if (bankInfo != null) {
                        deleteBankInfo(bankInfo.getId());
                    }
                    break;
                case R.id.tv_cancel:
                    dialogFragment.dismiss();
                    break;
            }

        }
    }

    /**
     * 提现账号
     * @param
     */
    private void getBankInfo() {
        if (userInfo == null){
            return;
        }
        netType = 1;
        CustomApplication.getRetrofit().getBankUser(userInfo.getId(),1).enqueue(this);
    }

    /**
     * 删除账号
     * @param
     */
    private void deleteBankInfo(Integer id) {
        if (userInfo == null){
            return;
        }
        netType = 2;
        CustomApplication.getRetrofit().deleteBankUser(userInfo.getId(),id).enqueue(this);
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
            if (netType == 2){
                boolean success = object.optBoolean("success");
                String msg= object.optString("msg");
                CustomToast.INSTANCE.showToast(this, msg);
                if (success){
                    getBankInfo();
                }
                return;
            }
            JSONArray banks = object.getJSONArray("rows");
            int length = banks.length();
            if (length <= 0){
                recyclerView.setVisibility(View.GONE);
                return;
            }
            bankInfoList.clear();
            for (int i = 0; i < length; i++) {
                JSONObject jsonObject = banks.getJSONObject(i);
                CashBankInfo bankInfo = GsonUtil.parseJsonWithGson(jsonObject.toString(),CashBankInfo.class);
                bankInfoList.add(bankInfo);
            }
            adapter.notifyDataSetChanged();
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
        getBankInfo();
    }
}
