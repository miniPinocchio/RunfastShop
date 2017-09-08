package com.example.supportv1.assist.netWork;

import android.annotation.SuppressLint;

import com.example.supportv1.R;
import com.example.supportv1.app.BaseApplication;
import com.example.supportv1.bean.FileTypeObj;
import com.example.supportv1.bean.RspBaseBean;
import com.example.supportv1.utils.JsonUtil;
import com.example.supportv1.utils.LogUtil;
import com.example.supportv1.utils.StringUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

@SuppressLint("TrulyRandom")
public class OFNetWorkFormPostThread extends OFThreadApi {

    private String TAG = "OFNetWorkFormPostThread";

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
     * https证书名称，证书放在assets目录里
     */
    @SuppressWarnings("unused")
    private String keystore;


    private OFNetMessage message = new OFNetMessage();

    private Map<String, String> textParams;// 文本参数

    private Map<String, FileTypeObj> fileParams;// 文件参数

    private static final int TIME_OUT = 10000;

    private static final int READ_TIME_OUT = 30000;

    private static final String BROWER_TYPE = "gA8NvTIuKn7Px0Z7DJ0DcIIgqjGL2h4l";

    private HttpURLConnection conn;

    private String boundary = "--------httppost";

    private DataOutputStream ds;

    private boolean isHttps = false;

    private int contentLen = -1;

    private URL url;

    private boolean mActive;


    public OFNetWorkFormPostThread(OFDispatcher dispatcher, String threadName, OFNetHandler handler) {
        this.mDispatcher = dispatcher;
        this.mHandler = handler;
        this.mTreadName = threadName;
    }

    /**
     * post 方式提交
     *
     * @param url      请求地址
     * @param beanType json转换的类
     * @param keystore https证书名称，证书放在assets目录里
     */
    public void post(String url, Class<?> beanType, Map<String, String> textParams, Map<String, FileTypeObj> fileParams, String keystore) {
        this.mUrl = url;
        if (url.startsWith("https")) {
            isHttps = true;
        }
        this.keystore = keystore;
        this.textParams = textParams;
        this.fileParams = fileParams;

        message.threadName = mTreadName;
        message.beanType = beanType;
        mActive = true;
//        start();
    }

    @Override
    public void run() {
        super.run();

        if (!mActive) {
            mDispatcher.finished(this);
            return;
        }

        if (mHandler != null) {
            mHandler.sendStartMessage(message);
        }

        String remoteResult[] = send();
        if (!mActive) {
            mDispatcher.finished(this);
            return;
        }
        LogUtil.i(TAG, "after POST");
        handResponse(remoteResult);

        if (mHandler != null) {
            mHandler.sendFinishMessage(message);
        }
        mDispatcher.finished(this);
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

            // json 转换成 bean类
            Object bean = JsonUtil.json2Object(result, message.beanType);

            if (bean == null) {
                LogUtil.e(TAG, "change json to bean error please check json format !!!!");
                message.errors = getResourceString(R.string.of_json_error);
                mHandler.sendFailureMessage(message);
            } else {
                message.responsebean = (RspBaseBean) bean;
                // message.responsebean.detailJsonString = new JsonParser().parse(result).getAsJsonObject().get("detail").toString();
                JsonElement element = new JsonParser().parse(result).getAsJsonObject().get("detail");
                if (element.isJsonObject()) {
                    message.responsebean.detailJsonString = element.getAsJsonObject().toString();
                } else if (element.isJsonArray()) {
                    message.responsebean.detailJsonString = element.getAsJsonArray().toString();
                } else if (element.isJsonNull()) {
                    message.responsebean.detailJsonString = null;
                } else if (element.isJsonPrimitive()) {
                    message.responsebean.detailJsonString = "detail is Json Primitive";
                }
                mHandler.sendSuccessMessage(message);
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
     * send request information. firstly , initialize the connection , then wait the connect , and catch the exception , secondly , get outPutSream data ,
     * thirdly , handle the data , fourthly , get inPutStream data , and call write() way , Finally , disconnect.
     *
     * @return out.toByteArray()
     * @throws Exception send data exception
     */
    public String[] send() {

        long start = System.currentTimeMillis();
        String result[] = new String[2];

        InputStream in = null;
        BufferedInputStream bis = null;
        ByteArrayOutputStream bos = null;

        try {
            initConnection();
            conn.connect();

            ds = new DataOutputStream(conn.getOutputStream());
            writeFileParams();
            writeStringParams();
            paramsEnd();
            in = conn.getInputStream();
            contentLen = conn.getContentLength();
            bis = new BufferedInputStream(in);
            byte[] buffer = new byte[1024];
            bos = new ByteArrayOutputStream();
            int len = -1;
            while ((len = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            byte[] resultByte = bos.toByteArray();

            result[0] = "0";
            result[1] = new String(resultByte, "utf-8");

            long end = System.currentTimeMillis();
            LogUtil.i(TAG, "network time=" + (end - start) + "ms");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            result[0] = "UnsupportedEncodingException";
            LogUtil.e(TAG, "result=" + result[0] + " " + e);
        } catch (ProtocolException e) {
            e.printStackTrace();
            result[0] = "ProtocolException";
            LogUtil.e(TAG, "result=" + result[0] + " " + e);
        } catch (IOException e) {
            e.printStackTrace();
            if (e instanceof ConnectException) {// 不能在指定的ip和端口上建立连接
                result[0] = "ConnectException";
            } else if (e instanceof SocketTimeoutException) {// 读取数据超时
                result[0] = "SocketTimeoutException";
            } else if (e instanceof UnknownHostException) {// 服务器域名解析故障
                result[0] = "UnknownHostException";
            } else {
                result[0] = "OtherIOException";
            }
            LogUtil.e(TAG, "result=" + result[0] + " " + e);
        } catch (Exception e) {
            e.printStackTrace();
            result[0] = "OtherException";
            LogUtil.e(TAG, "result=" + result[0] + " " + e);
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (in != null) {
                    in.close();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * send request information. firstly , initialize the connection , then wait the connect , and catch the exception , secondly , get outPutSream data ,
     * thirdly , handle the data , fourthly , get inPutStream data , and call write() way , Finally , disconnect.
     *
     * @return out.toByteArray()
     * @throws Exception send data exception
     */
    public InputStream sendFromStream()
            throws IOException, NoSuchAlgorithmException, KeyManagementException, Exception {
        initConnection();
        conn.connect();
        ds = new DataOutputStream(conn.getOutputStream());
        writeFileParams();
        writeStringParams();
        paramsEnd();
        InputStream in = conn.getInputStream();
        contentLen = conn.getContentLength();
        return in;
    }

    /**
     * initialize connection.
     *
     * @throws Exception connection exception
     */
    private void initConnection()
            throws IOException, NoSuchAlgorithmException, KeyManagementException, Exception {
        url = new URL(mUrl);
        if (isHttps) {
            conn = getHttpsConnection(mUrl);
        } else {
            conn = getHttpConnection(mUrl);
        }

        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setConnectTimeout(TIME_OUT);
        conn.setReadTimeout(READ_TIME_OUT);// fix jira portrait 137
        conn.setRequestProperty("USER-AGENT", BROWER_TYPE);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
    }

    private HttpURLConnection getHttpConnection(String urlStr)
            throws MalformedURLException, IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        return conn;
    }

    private HttpsURLConnection getHttpsConnection(String urlStr)
            throws MalformedURLException, IOException, NoSuchAlgorithmException, KeyManagementException {
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setHostnameVerifier(new IgnoreHostnameVerifier());
        TrustManager[] tm = {new IgnoreCertificationTrustManger()};
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, tm, null);
        SSLSocketFactory ssf = sslContext.getSocketFactory();
        conn.setSSLSocketFactory(ssf);
        return conn;
    }

    @Override
    public void cancelWork() {

    }

    class IgnoreHostnameVerifier implements HostnameVerifier {
        public boolean verify(String arg0, SSLSession arg1) {
            return true;
        }
    }

    class IgnoreCertificationTrustManger implements X509TrustManager {

        private X509Certificate[] certificates;

        public void checkClientTrusted(X509Certificate certificates[], String authType)
                throws CertificateException {
            if (this.certificates == null) {
                this.certificates = certificates;
            }

        }

        public void checkServerTrusted(X509Certificate[] ax509certificate, String s)
                throws CertificateException {
            if (this.certificates == null) {
                this.certificates = ax509certificate;
            }
        }

        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

    }

    /**
     * write string parameter to request. new HashMap<String, String>() (Map<String, String>)instance , then instance calls the keySet() , return a set of the
     * keys contained in this Map , and new Set<String> keySet , calling iterator() , handle the data model.
     *
     * @throws Exception write bytes data exception
     */
    private void writeStringParams()
            throws IOException {
        Set<String> keySet = textParams.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            String name = it.next();
            String value = textParams.get(name);
            ds.writeBytes("--" + boundary + "\r\n");
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"\r\n");
            ds.writeBytes("\r\n");
            ds.writeBytes(encode(value) + "\r\n");
        }
    }

    /**
     * write file parameter to request. new HashMap<String, FileTypeObj>() (Map<String, FileTypeObj>)instance , then instance calls the keySet() , return a set
     * of the keys contained in this Map , and new Set<String> keySet , calling iterator() , handle the data model.
     *
     * @throws Exception write bytes data exception
     */
    private void writeFileParams()
            throws IOException {
        Set<String> keySet = fileParams.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext(); ) {
            String name = it.next();
            FileTypeObj fto = fileParams.get(name);
            File value = fto.file;
            ds.writeBytes("--" + boundary + "\r\n");
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + encode(value.getName()) + "\"\r\n");
            // ds.writeBytes("Content-Type: " + fto.type + "\r\n");
            ds.writeBytes("\r\n");
            ds.write(getBytes(value));
            ds.writeBytes("\r\n");
        }
    }

    /**
     * get file bytes data. get File instance , new FileInputStream(file) instance , new ByteArrayOutputStream() instance , then read data to memory , at last ,
     * close file.
     *
     * @param file request parameter file
     * @return out.toByteArray()
     * @throws Exception get bytes data exception
     */
    private byte[] getBytes(File file)
            throws IOException, FileNotFoundException {
        FileInputStream in = new FileInputStream(file);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = in.read(b)) != -1) {
            out.write(b, 0, n);
        }
        in.close();
        return out.toByteArray();
    }

    /**
     * write end data of parameter to request with DataOutputStream instance by calling writeBytes() way.
     *
     * @throws Exception write bytes data exception
     */
    private void paramsEnd()
            throws IOException {
        ds.writeBytes("--" + boundary + "--" + "\r\n");
        ds.writeBytes("\r\n");
    }

    /**
     * encode request value.
     *
     * @param value get the string value
     * @return URLEncoder.encode(value, "UTF-8")
     * @throws Exception encode string uri exception
     */
    private String encode(String value)
            throws UnsupportedEncodingException {
        return URLEncoder.encode(value, "UTF-8");
    }

    @SuppressWarnings("unused")
    private void trustAllHttpsCertificates()
            throws NoSuchAlgorithmException, KeyManagementException {

        // Create a trust manager that does not validate certificate chains:

        TrustManager[] trustAllCerts = new TrustManager[1];

        TrustManager tm = new miTM();

        trustAllCerts[0] = tm;

        SSLContext sc = SSLContext.getInstance("SSL");

        sc.init(null, trustAllCerts, null);

        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

    }

    public static class miTM implements TrustManager, X509TrustManager {
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }

        public void checkClientTrusted(X509Certificate[] certs, String authType)
                throws CertificateException {
            return;
        }
    }

    public int getContentLen() {
        return contentLen;
    }

    public HttpURLConnection getConn() {
        return conn;
    }

}
