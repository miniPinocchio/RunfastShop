package com.example.supportv1.utils;

import android.app.Activity;

import com.example.supportv1.assist.netWork.NetWorkHelpInterf;
import com.example.supportv1.assist.netWork.OFDispatcher;
import com.example.supportv1.assist.netWork.OFNetHandler;
import com.example.supportv1.assist.netWork.OFNetMessage;
import com.example.supportv1.assist.netWork.OFNetWorkFormPostThread;
import com.example.supportv1.assist.netWork.OFNetWorkThread;
import com.example.supportv1.bean.FileTypeObj;
import com.example.supportv1.constant.Consts;

import java.util.Map;

/**
 * HTTP请求处理类
 *
 * @date 2014年6月26日
 **/
public class HttpUtil {

    public Activity mActivity;

    protected OFDispatcher mDispatcher;

    // 报文请求响应回调接口实现类
    protected NetWorkHelpInterf mInterface;

    /**
     * 报文请求返回消息处理类
     */
    protected OFNetHandler mNetHandler = new OFNetHandler() {
        public void onSuccess(OFNetMessage msg) {
            if (msg.responsebean != null) {
                if (msg.responsebean.result == 0) {
                    mInterface.uiSuccess(msg);
                } else {
                    mInterface.uiError(msg);
                }
            } else {

                if (msg.rjson != null && msg.rjson.result == 0) {
                    mInterface.uiSuccess(msg);
                } else {
                    mInterface.uiError(msg);
                }

            }

        }

        public void onFailure(OFNetMessage msg) {
            mInterface.uiFailure(msg);
        }


        public void onFinish(OFNetMessage msg) {
            mInterface.uiFinish(msg);
        }

    };

    /**
     * 构造函数
     *
     * @param activity 调用的activity
     * @param interf   网络请求结果处理接口
     */
    public HttpUtil(Activity activity, NetWorkHelpInterf interf) {
        this.mActivity = activity;
        mInterface = interf;
        mDispatcher = new OFDispatcher();
    }

    /**
     * 构造函数
     *
     * @param className 调用的className
     * @param interf    网络请求结果处理接口
     */
    public HttpUtil(String className, NetWorkHelpInterf interf) {
        mInterface = interf;
//        mThreadGroup = new ThreadGroup(className);
        mDispatcher = new OFDispatcher();
    }

    /**
     * http或https post方式请求
     *
     * @param netName  请求的命令字即报文cmd命令
     * @param params   请求的JSON字符串
     * @param beanType 需要转换的Bean类型
     * @param keystore https证书名称，证书放在assets目录里
     */
    public void netPost(String netName, String params, Class<?> beanType, String keystore) {
        netPost(netName, Consts.APP_URL, params, beanType, null, keystore);
    }

    /**
     * http或https post方式请求
     *
     * @param netName  请求的命令字即报文cmd命令
     * @param url      请求的URL
     * @param params   请求的JSON字符串
     * @param beanType 需要转换的Bean类型
     * @param keystore https证书名称，证书放在assets目录里
     */
    public void netPost(String netName, String url, String params, Class<?> beanType, String keystore) {
        netPost(netName, url, params, beanType, null, null, keystore);
    }

    /**
     * http或https post方式请求
     *
     * @param netName      请求的命令字即报文cmd命令
     * @param url          请求的URL
     * @param params       请求的JSON字符串
     * @param beanType     需要转换的Bean类型
     * @param netProcessor 用于网络后台处理gson数据
     * @param keystore     https证书名称，证书放在assets目录里
     */
    public void netPost(String netName, String url, String params, Class<?> beanType, OFNetWorkThread.NetProcessor netProcessor, String keystore) {
        netPost(netName, url, params, beanType, null, netProcessor, keystore);
    }

    /**
     * http或https post方式请求
     *
     * @param netName  请求的命令字即报文cmd命令
     * @param url      请求的URL
     * @param params   请求的JSON字符串
     * @param beanType 需要转换的POJO类
     * @param object   绑定的对象，可不填
     * @param keystore https证书名称，证书放在assets目录里
     */
    public void netPost(String netName, String url, String params, Class<?> beanType, Object object, String keystore) {
        netPost(netName, url, params, beanType, object, null, keystore);
    }

    /**
     * http或https post方式请求
     *
     * @param netName      请求的命令字即报文cmd命令
     * @param url          请求的URL
     * @param params       请求的JSON字符串
     * @param beanType     需要转换的bean类型
     * @param object       绑定的对象，可不填
     * @param netProcessor 用于网络后台处理gson数据
     * @param keystore     https证书名称，证书放在assets目录里
     */
    public void netPost(String netName, String url, String params, Class<?> beanType, Object object, OFNetWorkThread.NetProcessor netProcessor, String keystore) {
        OFNetWorkThread net = new OFNetWorkThread(mDispatcher, netName, mNetHandler);
        net.setNetProcessor(netProcessor);
        net.post(url, beanType, params, object, keystore);
        mDispatcher.enqueue(net);
    }

    /**
     * http或https post方式请求，以字节流方式上传图片
     *
     * @param netName    请求的命令字即报文cmd命令
     * @param url        请求的URL
     * @param textParams 文本字段参数：cmd=完整报文结构
     * @param fileParams 文件字段参数：文件直接以流的方式与报文一并上传，文件的key与报文中的文件名一一对应，可以用文件名表示
     * @param beanType   返回报文需要转换的Bean类型
     * @param keystore   https证书名称，证书放在assets目录里
     */
    public void netPost(String netName, String url, Map<String, String> textParams, Map<String, FileTypeObj> fileParams, Class<?> beanType, String keystore) {
        OFNetWorkFormPostThread net = new OFNetWorkFormPostThread(mDispatcher, netName, mNetHandler);
        net.post(url, beanType, textParams, fileParams, keystore);
        mDispatcher.enqueue(net);
    }

    /**
     * http或https get方式请求
     *
     * @param netName  请求的命令字即报文cmd命令
     * @param url      请求的URL
     * @param object   绑定的对象，可不填
     * @param keystore https证书名称，证书放在assets目录里
     */
    public void netGet(String netName, String url, Object object, String keystore) {
        OFNetWorkThread net = new OFNetWorkThread(mDispatcher, netName, mNetHandler);
        net.get(url, object, "", keystore);
        mDispatcher.enqueue(net);
    }

    /**
     * 销毁指定的线程
     *
     * @param threadName 线程名
     */
    public void cancelwhichNet(String threadName) {
        mDispatcher.cancelTag(threadName);
    }

    /**
     * 销毁所有线程
     */
    public void cancelAllNet() {
        mDispatcher.cancelAll();
    }

    /**
     * 销毁所有线程
     */
    public void onDestroy() {
        cancelAllNet();
    }

}
