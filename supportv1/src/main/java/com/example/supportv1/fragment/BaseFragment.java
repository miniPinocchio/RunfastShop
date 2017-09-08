package com.example.supportv1.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.supportv1.assist.netWork.NetWorkHelpInterf;
import com.example.supportv1.assist.netWork.OFNetMessage;
import com.example.supportv1.assist.netWork.OFNetWorkThread;
import com.example.supportv1.bean.FileTypeObj;
import com.example.supportv1.constant.Consts;

import com.example.supportv1.utils.HttpUtil;

import java.util.Map;

public class BaseFragment extends Fragment implements NetWorkHelpInterf, OnTouchListener {
    // 后台通信工具类
    protected HttpUtil httpUtil;

    public String TAG = "BaseFragment";

    @Override
    public void onCreate(@Nullable
                         Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpUtil = new HttpUtil(getActivity(), this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.setOnTouchListener(this);
    }

    @Override
    public void onDestroy() {
        httpUtil.onDestroy();
        super.onDestroy();
    }

    /**
     * 显示提示Toast
     *
     * @param message
     * @see [类、类#方法、类#成员]
     */
    public void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    /***************************************************************
     * 以下是与后台通信相关方法
     ***************************************************************/

    public void cancelNet(String threadName) {
        httpUtil.cancelwhichNet(threadName);
    }

    public void netPost(String netName, String params, Class<?> beanType, String keystore) {
        httpUtil.netPost(netName, params, beanType, keystore);
    }

    public void netPost(String netName, String url, String params, Class<?> beanType, String keystore) {
        httpUtil.netPost(netName, url, params, beanType, keystore);
    }

    public void netPost(String netName, String params, Class<?> beanType, Object object, String keystore) {
        httpUtil.netPost(netName, Consts.APP_URL, params, beanType, object, keystore);
    }

    public void netPost(String netName, String url, String params, Class<?> beanType, OFNetWorkThread.NetProcessor netProcessor, String keystore) {
        httpUtil.netPost(netName, url, params, beanType, netProcessor, keystore);
    }

    public void netPost(String netName, String url, String params, Class<?> beanType, Object object, String keystore) {
        httpUtil.netPost(netName, url, params, beanType, object, keystore);
    }

    public void netPost(String netName, String url, Map<String, String> textParams, Map<String, FileTypeObj> fileParams, Class<?> beanType, String keystore) {
        httpUtil.netPost(netName, url, textParams, fileParams, beanType, keystore);
    }

    public void netGet(String netName, String url, Object object, String keystore) {
        httpUtil.netGet(netName, url, object, keystore);
    }

    /***************************************************************
     * 通信返回结果回调方法
     ***************************************************************/

    // 报文返回成功result=0
    @Override
    public void uiSuccess(OFNetMessage msg) {
    }

    // 报文返回成功result!=0
    @Override
    public void uiError(OFNetMessage msg) {

    }

    // 网络连接异常或返回报文无效JSON格式
    @Override
    public void uiFailure(OFNetMessage msg) {
        Log.e(TAG, msg.errors);
    }

    @Override
    public void uiFinish(OFNetMessage msg) {

    }

    @Override
    public void InterruptNet(String threadName) {

    }

    /**
     * 隐藏输入法
     */
    public void hideSoftInput() {
        if (getActivity().getCurrentFocus() != null) {
            if (getActivity().getCurrentFocus().getWindowToken() != null) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }

}
