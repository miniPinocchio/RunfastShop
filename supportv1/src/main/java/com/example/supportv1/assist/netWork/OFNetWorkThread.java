package com.example.supportv1.assist.netWork;

import android.os.Process;
import android.text.TextUtils;

import com.example.supportv1.R;
import com.example.supportv1.app.BaseApplication;
import com.example.supportv1.bean.RspBaseBean;
import com.example.supportv1.utils.JsonUtil;
import com.example.supportv1.utils.LogUtil;
import com.example.supportv1.utils.StringUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * 网络请求子线程
 *
 * @auth bill
 * @date 2014年7月9日
 **/
public class OFNetWorkThread extends OFThreadApi {

    private static final String TAG = "OFNetWorkThread";

    private OFDispatcher mDispatcher;

    /**
     * 消息传递handle
     */
    private OFNetHandler mHandler;

    /**
     * 请求URL
     */
    private String mUrl;

    /**
     * 请求用的参数
     */
    private String mParam;

    /**
     * https证书名称，证书放在assets目录里
     */
    private String keystore;


    /**
     * 控制线程运行中断
     */
    public boolean mActive;

    /**
     * 联网方式
     */
    private enum NetMode {
        POST, GET
    }

    /**
     * 用于网络后台处理gson数据
     */
    private NetProcessor netProcessor;

    private NetMode mRequestMode = NetMode.POST;

    private OFNetMessage message = new OFNetMessage();


    public OFNetWorkThread(OFDispatcher dispatcher, String threadName, OFNetHandler handler) {
        this.mDispatcher = dispatcher;
        this.mTreadName = threadName;
        this.mHandler = handler;
    }

    public NetProcessor getNetProcessor() {
        return netProcessor;
    }

    public void setNetProcessor(NetProcessor netProcessor) {
        this.netProcessor = netProcessor;
    }

    /**
     * post 方式提交
     *
     * @param url      请求地址
     * @param beanType json转换的类
     * @param keystore https证书名称，证书放在assets目录里
     * @param object   联网绑定object，可填
     */
    public void post(String url, Class<?> beanType, String params, Object object, String keystore) {
        mActive = true;
        this.mRequestMode = NetMode.POST;
        this.mUrl = url;
        this.mParam = params;
        this.keystore = keystore;

        message.threadName = mTreadName;
        message.beanType = beanType;
        message.object = object;

        LogUtil.i(TAG, mParam);
    }

    /**
     * get 方式提交
     *
     * @param url
     *            请求地址
     * @param beanType
     *            json转换的类
     * @param keystore
     *            https证书名称，证书放在assets目录里
     * @param object
     *            联网绑定object，可填
     */

    /**
     * get 方式提交
     *
     * @param url      请求地址
     * @param object   联网绑定object，可填
     * @param params   请求参数以key=value&key=value形式，系统会将此参数加在请求地址？后面
     * @param keystore https证书名称，证书放在assets目录里
     */
    public void get(String url, Object object, String params, String keystore) {
        mActive = true;
        this.mRequestMode = NetMode.GET;
        this.mUrl = url;
        this.keystore = keystore;

        if (!TextUtils.isEmpty(params)) {
            this.mUrl += "?" + params;
        }

        message.threadName = mTreadName;
        message.object = object;

        LogUtil.i(TAG, mUrl);
    }

    public void cancelWork() {
        mActive = false;
        try {
            interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        if (!mActive) {
            mDispatcher.finished(this);
            return;
        }

        if (mHandler != null) {
            mHandler.sendStartMessage(message);
        }

        if (mRequestMode == NetMode.POST) {
            LogUtil.i(TAG, "before POST mActive is " + mActive);
            String remoteResult[] = OFHttpUrlWork.postResponse(mUrl, mParam, keystore);
            if (mActive) {
                LogUtil.i(TAG, "after POST");
                handResponse(remoteResult);
            } else {
                mDispatcher.finished(this);
                return;
            }
        } else if (mRequestMode == NetMode.GET) {
            LogUtil.i(TAG, "before GET mActive is " + mActive);
            String remoteResult[] = OFHttpUrlWork.getResponse(mUrl, keystore);
            if (mActive) {
                LogUtil.i(TAG, "after GET");
                handResponse(remoteResult);
            } else {
                mDispatcher.finished(this);
                return;
            }
        }

        if (mHandler != null) {
            mHandler.sendFinishMessage(message);
        }
        mDispatcher.finished(this);

        super.run();
    }

    // 发送响应消息到主线程
    public void handResponse(String remoteString[]) {

        if (remoteString[0].equals("0")) {
            handSuccess(remoteString[1]);
        } else {
            handFailure(parseError(remoteString[0]));
        }
    }

    private void handSuccess(String result) {
        LogUtil.i(TAG, result);

        if (mHandler != null) {

            if (mRequestMode == NetMode.GET) {
                message.results = result;
                mHandler.sendSuccessMessage(message);
            }

            if (mRequestMode == NetMode.POST) {
                // json 转换成 bean类
                Object bean = JsonUtil.json2Object(result, message.beanType);

                if (bean == null) {
                    LogUtil.e(TAG, "change json to bean error please check json format !!!!");
                    message.errors = getResourceString(R.string.of_json_error);
                    mHandler.sendFailureMessage(message);
                } else {
                    message.responsebean = (RspBaseBean) bean;
                    // message.responsebean.detailJsonString = new JsonParser().parse(result).getAsJsonObject().get("detail").getAsJsonObject().toString();
                    JsonElement element = new JsonParser().parse(result).getAsJsonObject().get("detail");
                    if (element == null) {
                        message.responsebean.detailJsonString = "detail not exists";
                    } else if (element.isJsonObject()) {
                        message.responsebean.detailJsonString = element.getAsJsonObject().toString();
                    } else if (element.isJsonArray()) {
                        message.responsebean.detailJsonString = element.getAsJsonArray().toString();
                    } else if (element.isJsonNull()) {
                        message.responsebean.detailJsonString = null;
                    } else if (element.isJsonPrimitive()) {
                        message.responsebean.detailJsonString = "detail is Json Primitive";
                    }
                    if (netProcessor != null) {
                        netProcessor.processBeanSuccess(bean);
                    }
                    mHandler.sendSuccessMessage(message);
                }

            }

        }
    }

    private void handFailure(String error) {
        if (mHandler != null) {
            message.errors = error;
            mHandler.sendFailureMessage(message);
        }
    }

    public static String parseError(String s) {
        String error = "";
        if (s.equals("UnsupportedEncodingException")) {
            error = error + getResourceString(R.string.of_unsupported_encoding);
        } else if (s.equals("ClientProtocolException")) {
            error = error + getResourceString(R.string.of_client_protocol);
        } else if (s.equals("ConnectException")) {
            error = error + getResourceString(R.string.of_connect_err);
        } else if (s.equals("ConnectTimeoutException")) {
            error = error + getResourceString(R.string.of_connect_timeout);
        } else if (s.equals("SocketTimeoutException")) {
            error = error + getResourceString(R.string.of_socket_timeout);
        } else if (s.equals("UnknownHostException")) {
            error = error + getResourceString(R.string.of_unknow_host);
        } else if (s.equals("OtherIOException")) {
            error = error + getResourceString(R.string.of_other_exception);
        } else {
            error = error + getResourceString(R.string.of_other_exception);
        }

        if (StringUtil.isBlank(error)) {
            error = getResourceString(R.string.of_connect_failed);
        }

        return error;
    }

    /**
     * 取得字符串资源值
     *
     * @param id 字符串资源ID
     * @return 字符串资源值
     */
    public static String getResourceString(int id) {

        return BaseApplication.APP_CONTEXT.getString(id);
    }

    /**
     * 后台接口允许介入网络请求中
     */
    public interface NetProcessor {
        void processBeanSuccess(Object bean);
    }
}
