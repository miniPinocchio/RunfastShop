package com.example.supportv1.activity;

import android.annotation.SuppressLint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.Toast;

import com.example.supportv1.assist.netWork.NetWorkHelpInterf;
import com.example.supportv1.assist.netWork.OFNetMessage;
import com.example.supportv1.assist.netWork.OFNetWorkThread;
import com.example.supportv1.bean.FileTypeObj;
import com.example.supportv1.constant.Consts;
import com.example.supportv1.utils.HttpUtil;
import com.example.supportv1.utils.LogUtil;

import java.util.Map;

/**
 * activity的基类
 *
 * @author sun.bl
 * @version [1.0, 2015-6-29]
 */
@SuppressLint("NewApi")
public class BaseActivity extends AppCompatActivity implements NetWorkHelpInterf {
    private boolean isFirstFocused = true;

    // 后台通信工具类
    protected HttpUtil httpUtil;

    public String TAG = "BaseActivity";

    @Override
    protected void onCreate(@Nullable
                            Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpUtil = new HttpUtil(this, this);
    }

    @Override
    protected void onDestroy() {
        httpUtil.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirstFocused && hasFocus) {
            isFirstFocused = false;
            onWindowInitialized();
        }
    }

    /**
     * 界面渲染完毕，可在这里进行初始化工作，建议在这里启动线程进行初始化工作，同时可以获取界面View的大小
     *
     * @see [onWindowFocusChanged]
     */
    public void onWindowInitialized() {

    }

    /**
     * 显示提示Toast
     *
     * @param message
     * @see [类、类#方法、类#成员]
     */
    public void showToastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
        LogUtil.e(TAG, msg.errors);
    }

    @Override
    public void uiFinish(OFNetMessage msg) {

    }

    @Override
    public void InterruptNet(String threadName) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

}
