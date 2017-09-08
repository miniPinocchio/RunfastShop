package com.example.supportv1.task;

import android.content.Context;

import com.example.supportv1.assist.DownLoadManager;
import com.example.supportv1.assist.DownloadListener;
import com.example.supportv1.dao.DownLoadDBHelper;
import com.example.supportv1.dao.DownloadDao;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class FileDownLoadTask {
    public static final String TAG = "DownloadService";
    public static final int THREAD_SIZE = 3;
    /**
     * 用于查询数据库
     */
    public DownloadDao mDownloadDao;
    /**
     * 要下载的文件大小
     */
    public int fileSize;
    /**
     * 每条线程需要下载的数据量
     */
    private int block;
    /**
     * 保存文件地目录
     */
    private File savedFile;
    /**
     * 下载地址url
     */
    private String down_url;
    /**
     * 是否停止下载
     */
    public boolean isPause;
    /**
     * 下载线程数组
     */
    private MultiThreadDownloadTask[] threads;
    /**
     * 各线程已经下载的数据量
     */
    private Map<Integer, Integer> downloadedLength = new ConcurrentHashMap<Integer, Integer>();
    /**
     * 下载返回监听
     */
    private DownloadListener mlistener;
    /**
     * 目标目录
     */
    private String destination;

    /**
     * 构造函数
     *
     * @param down_url    下载地址
     * @param destination 目标目录
     * @param context     上下文
     */
    public FileDownLoadTask(String down_url, String destination, Context context) throws Exception {
        mDownloadDao = new DownloadDao(new DownLoadDBHelper(context));
        this.threads = new MultiThreadDownloadTask[THREAD_SIZE];
        this.down_url = down_url;
        this.destination = destination;

    }

    /**
     * 获取需要下载文件的文件名称
     *
     * @param conn HttpURLConnection
     */
    private String getFileName(HttpURLConnection conn) {
        String fileName = down_url.substring(down_url.lastIndexOf("/") + 1, down_url.length());
        if (fileName == null || "".equals(fileName.trim())) {
            String content_disposition = null;
            for (Entry<String, List<String>> entry : conn.getHeaderFields().entrySet()) {
                if ("content-disposition".equalsIgnoreCase(entry.getKey())) {
                    content_disposition = entry.getValue().toString();
                }
            }
            try {
                Matcher matcher = Pattern.compile(".*filename=(.*)").matcher(content_disposition);
                if (matcher.find())
                    fileName = matcher.group(1);
            } catch (Exception e) {
                fileName = UUID.randomUUID().toString() + ".tmp"; // 默认名
            }
        }
        return fileName;
    }

    /**
     * 开始下载文件
     */
    public void download() throws Exception {
        try {
            downloadedLength = mDownloadDao.getDownloadedLength(down_url);
            int old_size = 0;
            for (int i = 0; i < downloadedLength.size(); i++) {
                old_size += downloadedLength.get(i);
            }
            URL url = new URL(down_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");

            if (conn.getResponseCode() != 200) {
                mlistener.onError(down_url, "", DownLoadManager.ERRORCODE.URL_ERROR, "url 没有应答");
                return;
            }

            fileSize = conn.getContentLength();
            if (fileSize <= 0) {
                mlistener.onError(down_url, "", DownLoadManager.ERRORCODE.FILE_CHANGE, "文件大小为0");
                return;
            }
//            if(fileSize!=old_size){
//                mlistener.onError(down_url,DownLoadManager.ERRORCODE.FILE_CHANGE, "文件已经变化");
//            }
            String fileName = destination + "/" + getFileName(conn);
            // 构建一个同样大小的文件
            this.savedFile = new File(fileName);
            if (old_size == 0) {
                if (savedFile.exists()) {
                    String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
                    String newfileName = destination + "/" + UUID.randomUUID().toString() + suffix;
                    this.savedFile = new File(newfileName);
                } else {
                    savedFile.createNewFile();
                }
            } else {
                if (!savedFile.exists()) {
                    savedFile.createNewFile();
                    downloadedLength.clear();
                    for (int i = 0; i < downloadedLength.size(); i++) {
                        downloadedLength.put(i, 0);
                    }
                }
            }
            RandomAccessFile doOut = new RandomAccessFile(savedFile, "rwd");
            doOut.setLength(fileSize);
            doOut.close();
            // 计算每条线程需要下载的数据长度
            this.block = fileSize % THREAD_SIZE == 0 ? fileSize / THREAD_SIZE : fileSize / THREAD_SIZE + 1;
            // 查询已经下载的记录
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new MultiThreadDownloadTask(i, savedFile, block, down_url, downloadedLength.get(i), this);
            new Thread(threads[i]).start();
        }
        mDownloadDao.saveDownLoading(down_url, fileSize, 1, savedFile.getAbsolutePath());
        while (!isFinish()) {
            Thread.sleep(900);
            if (mlistener != null) {
                mlistener.onDownload(down_url, getDownloadedSize(threads), fileSize);
            }
            int[] currentSize = new int[]{threads[0].currentDownloadSize, threads[1].currentDownloadSize, threads[2].currentDownloadSize};
            mDownloadDao.updateDownloading(currentSize, down_url);
        }
        if (isFinish()) {
            if (!isPause) {
                for (int i = 0; i < THREAD_SIZE; i++) {
                    mDownloadDao.updateDownloaded(down_url, true);
                }

                mlistener.onFinish(savedFile.getAbsolutePath(), down_url, true);
            } else {
                mDownloadDao.updateDownloaded(down_url, false);
                mlistener.onFinish(savedFile.getAbsolutePath(), down_url, false);
            }
        }
    }

    /**
     * 获取已经下载的文件大小
     *
     * @param threads 下载线程数组
     * @return 已经下载文件大小    int
     */
    private int getDownloadedSize(MultiThreadDownloadTask[] threads) {
        int sum = 0;
        for (int len = threads.length, i = 0; i < len; i++) {
            sum += threads[i].currentDownloadSize;
        }
        return sum;
    }

    /**
     * 判断文件是否下载介绍
     *
     * @return boolean true:下载结束  false:下载未结束
     */
    public boolean isFinish() {
        try {
            for (int len = threads.length, i = 0; i < len; i++) {
                if (!threads[i].finished) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 设置下载监听,实现接口，可以获取onDownload，onError,onFinish 事件
     *
     * @param mlistener 下载监听
     */
    public void setMlistener(DownloadListener mlistener) {
        this.mlistener = mlistener;
    }

    /**
     * 获取下载监听
     *
     * @return mlistener 下载监听
     */
    public DownloadListener getDownloadListener() {
        return mlistener;
    }
}
