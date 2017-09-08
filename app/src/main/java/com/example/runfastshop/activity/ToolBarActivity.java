package com.example.runfastshop.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.runfastshop.R;
import com.example.supportv1.activity.BaseActivity;
import com.example.supportv1.assist.netWork.OFNetMessage;
import com.githang.statusbar.StatusBarCompat;


/**
 * 工具栏的Activity
 *
 * @author Sun.bl
 * @version [1.0, 2016/06/19]
 */
public class ToolBarActivity extends BaseActivity {

    protected Toolbar toolbar;
    protected TextView tvToolbarTitle;
    protected TextView rightTitle;
    protected TextView tvBadge;
    protected RelativeLayout layoutRight;
    protected ImageView ivSetting;
    protected RelativeLayout layoutRightImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("toolbar","toolCreate");
    }

//    /***
//     * 注册广播接受者
//     */
//    private void registerBroadCastReceiver() {
//        messageReceiver = new PushMessageReceiver(mUiHandler);
//        IntentFilter filter = new IntentFilter(ActionConstant.PUSH_CLIENT_ID);
//        filter.addAction(ActionConstant.PUSH_MESSAGE);
//        LocalBroadcastManager.getInstance(this).registerReceiver(messageReceiver, filter);
//    }
//
//    private Handler mUiHandler = new Handler() {
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case HandlerConstant.PUSH_OTHER_LOGIN: //其他人登录
//                    showOtherLoginDialog();
//                    break;
//                case HandlerConstant.PUSH_USER_DISABLE: //帐号禁用
//                    showUserDisableDialog();
//                    break;
//
//            }
//        }
//    };
//
//    /**
//     * 显示帐号禁用的dialog
//     */
//    private void showUserDisableDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getString(R.string.prompt));
//        builder.setMessage(getString(R.string.user_disable));
//        builder.setCancelable(false);
//        builder.setPositiveButton(R.string.ok, new ReLoginClickImpl(this));
//        builder.show();
//    }
//
//    /**
//     * 显示其他人登录的dialog
//     */
//    private void showOtherLoginDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getString(R.string.prompt));
//        builder.setMessage(getString(R.string.other_device_login));
//        builder.setCancelable(false);
//        builder.setPositiveButton(R.string.ok, new ReLoginClickImpl(this));
//        builder.show();
//    }

    @Override
    protected void onDestroy() {
        Log.d("toolbar","toolDestroy");
//        if (messageReceiver != null) {
//            LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
//            messageReceiver = null;
//        }
//        if (mUiHandler != null) {
//            mUiHandler.removeCallbacksAndMessages(null);
//            mUiHandler = null;
//        }
        super.onDestroy();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    private void toolBarInit() {
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary), true);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        rightTitle = (TextView) findViewById(R.id.tv_right_title);
        //tvBadge = (TextView) findViewById(R.id.tv_badge);
        ivSetting = (ImageView) findViewById(R.id.iv_setting_right);
        layoutRightImage = (RelativeLayout) findViewById(R.id.layout_right_image);
        layoutRight = (RelativeLayout) findViewById(R.id.layout_right_title);
        if (toolbar == null) {
            return;
        }
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.icon_back_black);
        tvToolbarTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        if (tvToolbarTitle == null) {
            return;
        }
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * 设置未读消息的数量
     * @param msg
     */
    public void setBadge(String msg){
        if (tvBadge == null){
            return;
        }
        tvBadge.setText(msg);
    }

    /**
     * 设置是否显示未读消息
     * @param isShow
     */
    public void isShowBadge(boolean isShow){
        if (isShow){
            tvBadge.setVisibility(View.VISIBLE);
            return;
        }
        tvBadge.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置是否显示右边消息
     * @param isShow
     */
    public void isShowRightMsg(boolean isShow){
        if (isShow){
            layoutRight.setVisibility(View.VISIBLE);
            return;
        }
        layoutRight.setVisibility(View.GONE);
    }

    /**
     * 设置右边title文字
     * @param msg
     */
    public void setRightMsg(String msg){
        if (rightTitle == null){
            return;
        }
        if (TextUtils.isEmpty(msg)){
            rightTitle.setText("");
            isShowRightMsg(false);
            return;
        }
        isShowRightMsg(true);
        rightTitle.setText(msg);
    }

    /**
     * 获取右边文字信息
     * @return
     */
    public String getRightMsg(){
        String rightMsg = rightTitle.getText().toString().trim();
        if (TextUtils.isEmpty(rightMsg)){
            return "";
        }
        return rightMsg;
    }

    /**
     * 显示右上角设置按钮
     */
    public void showSetting(){
        if (layoutRightImage != null) {
            layoutRightImage.setVisibility(View.VISIBLE);
        }
    }
    /**
     * 隐藏右上角设置按钮
     */
    public void dismmisSetting(){
        if (layoutRightImage != null) {
            layoutRightImage.setVisibility(View.GONE);
        }
    }
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolBarInit();
    }

    public void setContentView(View view) {
        super.setContentView(view);
        toolBarInit();
    }

    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        toolBarInit();
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        if (tvToolbarTitle == null) {
            return;
        }
        tvToolbarTitle.setText(title);
        super.onTitleChanged(title, color);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void uiFinish(OFNetMessage msg) {
        if (msg.responsebean != null){
            if (msg.responsebean.result == 12){
                Log.d("responsebean","msg.responsebean.result = "+msg.responsebean.result);
                //PushManager.getInstance().stopService(this.getApplicationContext());
                //showErrorDialog();
            }
        }
        super.uiFinish(msg);
    }

    /**
     * 显示连接错误的dialog
     */
//    private void showErrorDialog() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(getString(R.string.prompt));
//        builder.setMessage(getString(R.string.inter_error));
//        builder.setCancelable(false);
//        builder.setPositiveButton(R.string.ok, new ReLoginClickImpl(this));
//        builder.show();
//    }
}
