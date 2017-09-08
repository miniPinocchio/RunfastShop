package com.example.supportv1.assist.netWork;


import android.text.TextUtils;

import com.example.supportv1.utils.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

/**
 * 使用HttpUrlConnection进行Http和Https进行网络请求
 *
 * @author Sun.bl
 * @version [1.0, 2016/4/5]
 */
public class OFHttpUrlWork {


    public static final String TAG = "OFHttpUrlWork";

    public static SSLContext sSSLContext;

//    static {
//        CookieManager cookieManager = new CookieManager(new OFCookieStore(), CookiePolicy.ACCEPT_ALL);
//        CookieHandler.setDefault(cookieManager);
//    }


    /***
     * 获取POST请求的返回值
     *
     * @param url      请求URL
     * @param param    请求参数
     * @param keystore 证书
     * @return String数组，result[0]代表网络请求是否成功 0代表成功，result[1]为成功时服务器返回的json数据
     */
    public static String[] postResponse(String url, String param, String keystore) {

        if (url.toLowerCase().startsWith("https")) {
            return httpsPostResponse(url, param, keystore);
        }
        return httpPostResponse(url, param);
    }

    /**
     * Http的Post请求
     *
     * @param urlStr 请求URL
     * @param param  请求参数
     * @return String数组，result[0]代表网络请求是否成功 0代表成功，result[1]为成功时服务器返回的json数据
     */
    private static String[] httpPostResponse(String urlStr, String param) {

        if (urlStr == null || param == null) {
            throw new NullPointerException("POST:url or params is null");
        }

        long start = System.currentTimeMillis();
        String result[] = new String[2];

        HttpURLConnection httpURLConnection = null;
        OutputStream os = null;
        InputStream is = null;

        try {

            URL url = new URL(urlStr);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            System.setProperty("http.keepAlive", "false");

            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setUseCaches(false);

            httpURLConnection.setRequestProperty("Connection", "close");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            httpURLConnection.addRequestProperty("Accept", "application/json");

//            setConnectionCookie(httpURLConnection);

            os = httpURLConnection.getOutputStream();
            writeSteam(os, param);
            is = httpURLConnection.getInputStream();
            result[0] = "0";
            result[1] = readSteam(is);
        } catch (Exception e) {
            e.printStackTrace();
            result[0] = screenedException(e);
            LogUtil.e(TAG, "result=" + result[0] + " " + e);
        } finally {
            closeConnection(httpURLConnection, is, os);
            long end = System.currentTimeMillis();
            LogUtil.i(TAG, "network time=" + (end - start) + "ms");
        }

        return result;
    }

    /***
     * Https Post请求
     *
     * @param urlStr   请求地址
     * @param param    请求参数
     * @param keystore 证书
     * @return String数组，result[0]代表网络请求是否成功 0代表成功，result[1]为成功时服务器返回的json数据
     */
    private static String[] httpsPostResponse(String urlStr, String param, String keystore) {
        if (urlStr == null || param == null) {
            throw new NullPointerException("POST:url or params is null");
        }

        long start = System.currentTimeMillis();
        String result[] = new String[2];

        HttpsURLConnection httpsURLConnection = null;
        OutputStream os = null;
        InputStream is = null;

        try {

            URL url = new URL(urlStr);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            System.setProperty("http.keepAlive", "false");

            httpsURLConnection.setDoOutput(true);
            httpsURLConnection.setDoInput(true);
            httpsURLConnection.setRequestMethod("POST");
            httpsURLConnection.setConnectTimeout(10000);
            httpsURLConnection.setReadTimeout(10000);
            httpsURLConnection.setUseCaches(false);

            httpsURLConnection.setRequestProperty("Connection", "close");
            httpsURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpsURLConnection.addRequestProperty("Accept", "application/json");

            setConnectionCookie(httpsURLConnection);
            registerHttpsSSL(httpsURLConnection, keystore);

            os = httpsURLConnection.getOutputStream();
            writeSteam(os, param);

            is = httpsURLConnection.getInputStream();
            result[0] = "0";
            result[1] = readSteam(is);
            LogUtil.i(TAG, "get result=" + result[1]);
        } catch (Exception e) {
            e.printStackTrace();
            result[0] = screenedException(e);
            LogUtil.e(TAG, "result=" + result[0] + " " + e);
        } finally {
            closeConnection(httpsURLConnection, is, os);
            long end = System.currentTimeMillis();
            LogUtil.i(TAG, "network time=" + (end - start) + "ms");
        }

        return result;
    }

    /**
     * 发送Get请求，返回取得的信息。
     *
     * @param url 请求地址和参数
     * @return String数组，result[0]代表网络请求是否成功 0代表成功，result[1]为成功时服务器返回的json数据
     */
    public static String[] getResponse(String url, String keystore) {
        if (url.toLowerCase().startsWith("https")) { // https单向认证请求
            return httpsGetResponse(url, keystore);
        } else {// 正常http请求
            return httpGetResponse(url);
        }
    }


    /***
     * Http GET请求
     *
     * @param urlStr 访问地址
     * @return String数组，result[0]代表网络请求是否成功 0代表成功，result[1]为成功时服务器返回的json数据
     */
    private static String[] httpGetResponse(String urlStr) {
        if (urlStr == null) {
            throw new NullPointerException("GET:url is null");
        }
        long start = System.currentTimeMillis();
        String result[] = new String[2];

        HttpURLConnection httpURLConnection = null;
        InputStream is = null;

        try {
            URL url = new URL(urlStr);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            System.setProperty("http.keepAlive", "false");

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(10000);

            httpURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            httpURLConnection.addRequestProperty("Accept", "application/json");

            setConnectionCookie(httpURLConnection);

            is = httpURLConnection.getInputStream();
            result[1] = readSteam(is);
            result[0] = "0";
            LogUtil.i(TAG, "get result=" + result[1]);
        } catch (Exception e) {
            e.printStackTrace();
            result[0] = screenedException(e);
            LogUtil.e(TAG, "result=" + result[0] + " " + e);
        } finally {
            closeConnection(httpURLConnection, is, null);
            long end = System.currentTimeMillis();
            LogUtil.i(TAG, "network time=" + (end - start) + "ms");
        }

        return result;
    }

    /***
     * HTTPS GET请求
     *
     * @param urlStr   请求URL
     * @param keyStore 证书字符串
     * @return String数组，result[0]代表网络请求是否成功 0代表成功，result[1]为成功时服务器返回的json数据
     */
    private static String[] httpsGetResponse(String urlStr, String keyStore) {
        if (urlStr == null) {
            throw new NullPointerException("GET: URL is null");
        }
        long start = System.currentTimeMillis();
        String result[] = new String[2];

        HttpsURLConnection httpsURLConnection = null;
        InputStream is = null;

        try {
            URL url = new URL(urlStr);
            httpsURLConnection = (HttpsURLConnection) url.openConnection();
            System.setProperty("http.keepAlive", "false");

            httpsURLConnection.setRequestMethod("GET");
            httpsURLConnection.setConnectTimeout(10000);
            httpsURLConnection.setReadTimeout(10000);

            httpsURLConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            httpsURLConnection.addRequestProperty("Accept", "application/json");

            setConnectionCookie(httpsURLConnection);

            registerHttpsSSL(httpsURLConnection, keyStore);

            is = httpsURLConnection.getInputStream();
            result[1] = readSteam(is);
            result[0] = "0";
        } catch (Exception e) {
            e.printStackTrace();
            result[0] = screenedException(e);
            LogUtil.e(TAG, "result=" + result[0] + " " + e);
        } finally {
            closeConnection(httpsURLConnection, is, null);
            long end = System.currentTimeMillis();
            LogUtil.i(TAG, "network time=" + (end - start) + "ms");
        }

        return result;
    }


    /***
     * 将字符串写入到输出流中
     *
     * @param os     输出流
     * @param params 参数
     * @throws IOException
     */
    public static void writeSteam(OutputStream os, String params)
            throws IOException, NullPointerException {
        if (os == null || params == null) {
            throw new NullPointerException("输出流或者参数为空");
        }

//        BufferedOutputStream bos = new BufferedOutputStream(os);
        os.write(params.getBytes());
        os.flush();

//        bos.flush();
//        bos.close();
    }

    /**
     * 读取输入流中的数据
     *
     * @param is 输入流
     * @return 读取输入流得出的字符串 <code>null</code>输入流为空，则返回空
     * @throws IOException
     */
    public static String readSteam(InputStream is)
            throws IOException, NullPointerException {
        if (is == null) {
            throw new NullPointerException("输入流为空");
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];

        int len = is.read(buffer);

        while (len != -1) {
            baos.write(buffer, 0, len);
            len = is.read(buffer);
        }

        return baos.toString("UTF-8");
    }


    /**
     * 获取Key管理数组
     *
     * @param passWord 证书密码
     * @param keyBks   证书流
     * @return Key管理数组
     */
    public static KeyManager[] getKeyManagerArray(String passWord, InputStream keyBks) {
        if (keyBks == null || passWord == null) {
            LogUtil.w(TAG, "KeyManager:passWord or InputStream is null point");
            return null;
        }

        try {
            //初始化keystore
            KeyStore clientKeyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            clientKeyStore.load(keyBks, passWord.toCharArray());

            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, passWord.toCharArray());

            return keyManagerFactory.getKeyManagers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获取信任证书数组
     *
     * @param certificates 证书流
     * @return 信任证书管理器数组
     */
    public static TrustManager[] getTrustManagerArray(InputStream certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            Certificate ca = certificateFactory.generateCertificate(certificates);
            keyStore.setCertificateEntry("ca", ca);

            String algorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(algorithm);
            trustManagerFactory.init(keyStore);

            return trustManagerFactory.getTrustManagers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /***
     * 设置SSL证书
     *
     * @param keyManagers   KEY证书管理器
     * @param trustManagers 证书管理器
     */
    public static SSLContext initSSLContext(KeyManager[] keyManagers, TrustManager[] trustManagers) {

        if (keyManagers == null && trustManagers == null) {
            LogUtil.e(TAG, "KeyManager & TrustManager is all null point");
            return null;
        }
        try {
//            SSLContext sslContext = SSLContext.getInstance("TLSv1", "AndroidOpenSSL");
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, trustManagers, null);
            return sslContext;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /***
     * 甄别异常类型
     *
     * @param e 异常
     * @return 异常名称
     */
    private static String screenedException(Exception e) {

        String exception;
        if (e instanceof MalformedURLException) {
            exception = "MalformedURLException";
        } else if (e instanceof ProtocolException) {
            exception = "ProtocolException";
        } else if (e instanceof ConnectException) {
            exception = "ConnectException";
        } else if (e instanceof SocketException) {
            exception = "SocketException";
        } else if (e instanceof UnknownHostException) {
            exception = "UnknownHostException";
        } else if (e instanceof NullPointerException) {
            exception = "NullPointerException";
        } else {
            exception = "otherException";
        }

        return exception;
    }

    /***
     * 关闭连接
     *
     * @param urlConnection 网络连接
     * @param inputStream   输入流
     * @param outputStream  输出流
     */
    private static void closeConnection(HttpURLConnection urlConnection, InputStream inputStream, OutputStream outputStream) {
        //关闭网络连接
        if (urlConnection != null) {
            urlConnection.disconnect();
            urlConnection = null;
        }
        try {
            //关闭输入流
            if (inputStream != null) {
                inputStream.close();
                inputStream = null;
            }
            //关闭输出流
            if (outputStream != null) {
                outputStream.close();
                outputStream = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 为Https注册证书，优先传递进来的证书，如果没有就使用默认的，默认没有就不注册
     *
     * @param urlConnection Https连接
     * @param keyStore      证书字符串
     */
    private static void registerHttpsSSL(HttpsURLConnection urlConnection, String keyStore) {
        //参数判断
        if (urlConnection == null) {
            return;
        }

        //根据传递证书构造SSL
        SSLContext sslContext = null;
        if (!TextUtils.isEmpty(keyStore)) {
            InputStream inputStream = new ByteArrayInputStream(keyStore.getBytes());
            TrustManager[] trustManagers = getTrustManagerArray(inputStream);
            sslContext = initSSLContext(null, trustManagers);
        }
        //判断是SSL
        sslContext = sslContext == null ? sSSLContext : sslContext;
        if (sslContext == null) {
            return;
        }
        urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());
    }

    /***
     * 为请求设置Cookie
     *
     * @param urlConnection 请求连接
     * @throws URISyntaxException URI转换异常
     * @throws IOException
     */
    private static void setConnectionCookie(HttpURLConnection urlConnection) throws URISyntaxException, IOException {

        if (urlConnection == null) {
            return;
        }

        CookieManager cookieManager = (CookieManager) CookieHandler.getDefault();
        if (cookieManager == null) {
            return;
        }

        URL url = urlConnection.getURL();
        if (url == null) {
            return;
        }

        URI uri = url.toURI();
        Map<String, List<String>> cookieMap = cookieManager.get(uri, urlConnection.getRequestProperties());
        List<String> cookieList = cookieMap.get("Cookie");
        if (cookieList == null || cookieList.isEmpty()) {
            return;
        }
        String cookieStr = cookieList.get(0);
        if (TextUtils.isEmpty(cookieStr)) {
            return;
        }
        urlConnection.addRequestProperty("Cookie", cookieStr);

    }

    /**
     * 加载连接中的Cookie
     *
     * @param urlConnection 连接
     * @throws URISyntaxException URI转换异常
     * @throws IOException
     * @deprecated 只要设置了CookieHandler，系统会自动去存储Cookie
     */
    @Deprecated /*unused*/
    private static void loadConnectionCookie(HttpURLConnection urlConnection) throws URISyntaxException, IOException {

        if (urlConnection == null) {
            return;
        }

        CookieManager cookieManager = (CookieManager) CookieHandler.getDefault();
        if (cookieManager == null) {
            return;
        }

        URL url = urlConnection.getURL();
        if (url == null) {
            return;
        }

        URI uri = url.toURI();

        cookieManager.put(uri, urlConnection.getHeaderFields());
    }


}
