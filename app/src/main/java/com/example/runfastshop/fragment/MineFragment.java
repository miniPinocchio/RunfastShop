package com.example.runfastshop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.runfastshop.activity.LoginActivity;
import com.example.runfastshop.activity.usercenter.AboutActivity;
import com.example.runfastshop.activity.usercenter.AddressSelectActivity;
import com.example.runfastshop.activity.usercenter.ComplaintActivity;
import com.example.runfastshop.activity.usercenter.ConsultationActivity;
import com.example.runfastshop.activity.usercenter.CouponActivity;
import com.example.runfastshop.activity.usercenter.HelpCenterActivity;
import com.example.runfastshop.activity.usercenter.IntegralActivity;
import com.example.runfastshop.activity.usercenter.JoinBusinessActivity;
import com.example.runfastshop.activity.usercenter.UserInfoActivity;
import com.example.runfastshop.activity.usercenter.WalletActivity;
import com.example.runfastshop.bean.User;
import com.example.runfastshop.config.NetConfig;
import com.example.runfastshop.config.UserService;

import org.xutils.x;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {

    @BindView(R.id.iv_head)
    ImageView ivHead;
    Unbinder unbinder;
    @BindView(R.id.tv_user_name)
    TextView tvUserName;
    @BindView(R.id.tv_wallet_money)
    TextView tvWalletMoney;
    @BindView(R.id.tv_coupons_num)
    TextView tvCouponsNum;
    @BindView(R.id.tv_integral_num)
    TextView tvIntegralNum;
    private User userInfo;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void initData() {
        userInfo = UserService.getUserInfo();
        if (userInfo == null || userInfo.getId() == null) {
            clearUi();
            return;
        }
        updateUi();
    }

    /**
     * 重置页面
     */
    private void clearUi() {
        ivHead.setImageResource(R.drawable.icon_default_head);
        tvUserName.setText("登录/注册");
        tvWalletMoney.setText("0元");
        tvCouponsNum.setText("0个");
        tvIntegralNum.setText("0分");
    }

    /**
     * 更新页面
     */
    private void updateUi() {
        if (!TextUtils.isEmpty(userInfo.getPic())){
            x.image().bind(ivHead,userInfo.getPic(), NetConfig.optionsHeadImage);
        }
        tvUserName.setText(TextUtils.isEmpty(userInfo.getName())?userInfo.getMobile():userInfo.getName());
        tvWalletMoney.setText(userInfo.getShowremainder()+"元");
        tvCouponsNum.setText((userInfo.getRnum() == null)?"0个":(userInfo.getRnum()+"个"));
        tvIntegralNum.setText((userInfo.getScore() == null)?"0分":(userInfo.getScore()+"分"));
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.iv_head, R.id.layout_help_center, R.id.layout_my_wallet, R.id.layout_coupons, R.id.layout_integral, R.id.layout_address, R.id.layout_collection, R.id.layout_join, R.id.layout_complaint, R.id.layout_consulting, R.id.layout_about})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_head://头像
                if (userInfo == null || userInfo.getId() == null) {
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getContext(), UserInfoActivity.class));
                break;
            case R.id.layout_help_center://帮助中心
                startActivity(new Intent(getContext(), HelpCenterActivity.class));
                break;
            case R.id.layout_my_wallet://我的钱包余额
                startActivity(new Intent(getContext(), WalletActivity.class));
                break;
            case R.id.layout_coupons://优惠券
                startActivity(new Intent(getContext(), CouponActivity.class));
                break;
            case R.id.layout_integral://积分
                startActivity(new Intent(getContext(), IntegralActivity.class));
                break;
            case R.id.layout_address://地址管理
                startActivity(new Intent(getContext(), AddressSelectActivity.class));
                break;
            case R.id.layout_collection://收藏

                break;
            case R.id.layout_join://加盟
                startActivity(new Intent(getContext(), JoinBusinessActivity.class));
                break;
            case R.id.layout_complaint://投诉
                startActivity(new Intent(getContext(), ComplaintActivity.class));
                break;
            case R.id.layout_consulting://客服咨询
                startActivity(new Intent(getContext(), ConsultationActivity.class));
                break;
            case R.id.layout_about://关于
                startActivity(new Intent(getContext(), AboutActivity.class));
                break;
        }
    }

}