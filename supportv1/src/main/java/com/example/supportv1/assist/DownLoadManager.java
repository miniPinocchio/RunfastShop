package com.example.supportv1.assist;

import com.example.supportv1.dao.DownLoadDBHelper;
import com.example.supportv1.dao.DownloadDao;
import com.example.supportv1.task.FileDownLoadTask;
import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import java.io.File;

/**
 * 断点续传(多线程下载)manager,可返回onDownload,onError,onFinish事件
 */
public class DownLoadManager {
    /**
     * 下载管理manager
     */
    static DownLoadManager mDownloadManager;
    /**
     * 保存下载数据库访问类
     */
    public DownloadDao mDownloadDao;
    /**
     * 文件现在线程
     */
    private FileDownLoadTask fileDownLoad;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 下载返回监听
     */
    private DownloadListener mDownloadListener;
    /**
     * 是否下载结束 标志位
     */
    private boolean is_finish = true;
    /**
     * 真正下载文件的url
     */
    private String down_urlnow;
    private DownloadTask task;

    public enum ERRORCODE {
        ERROR_BASE, FILE_CHANGE, URL_ERROR, FILE_DELETED;
    }

    /**
     * 私有的下载监听
     */
    private DownloadListener mSelfListener = new DownloadListener() {

        @Override
        public void onFinish(String filePath, String fileUrl, boolean is_pause) {
            is_finish = true;
            String down_url = mDownloadDao.getNewestDownLoadUrl();
            if (down_url != null && (!down_url.equals(""))) {
                startDownLoad(down_url);
            }
            mDownloadListener.onFinish(filePath, fileUrl, is_pause);
        }

        @Override
        public void onError(String fileUrl, String filePath, ERRORCODE error_code, String errorMsg) {
            is_finish = true;
            String down_url = mDownloadDao.getNewestDownLoadUrl();
            if (down_url != null && (!down_url.equals(""))) {
                startDownLoad(down_url);
            }
            mDownloadDao.deleteDownloading(fileUrl);
            File downLoadingFile = new File(filePath);
            if (downLoadingFile.exists()) {
                downLoadingFile.delete();
            }
            mDownloadListener.onError(fileUrl, filePath, error_code, errorMsg);

        }

        @Override
        public void onDownload(String fileUrl, int downloaded_size, int total_size) {
            mDownloadListener.onDownload(fileUrl, downloaded_size, total_size);

        }

    };
    /**
     * 目标目录
     */
    private String mdestination;
    private static DownLoadManager mLoadManager;

    /**
     * 单例模式
     *
     * @param context 上下文
     * @return DownLoadManager
     */
    public static DownLoadManager getInstance(Context context) {
        if (mLoadManager == null) {
            mLoadManager = new DownLoadManager(context);
        }
        return mLoadManager;
    }

    /**
     * 构造函数
     *
     * @param context 上下文
     */
    public DownLoadManager(Context context) {
        this.mContext = context;
        mDownloadDao = new DownloadDao(new DownLoadDBHelper(context));
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mdestination = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            Toast.makeText(mContext, "SD卡不存在或写保护!", Toast.LENGTH_LONG).show();
        }
    }

    public DownLoadManager(Context context, DownloadListener mDownloadListener) {
        this.mContext = context;
        mDownloadDao = new DownloadDao(new DownLoadDBHelper(context));
        this.mDownloadListener = mDownloadListener;
    }

    public void startDownLoad(String downloadUrl) {
        if (is_finish) {
            try {
                is_finish = false;
                down_urlnow = downloadUrl;
                task = new DownloadTask(downloadUrl);
                new Thread(task).start();
            } catch (Exception e) {
                mDownloadListener.onError(downloadUrl, "", ERRORCODE.ERROR_BASE, e.getMessage());
                e.printStackTrace();
            }
        } else {
            mDownloadDao.addNewDownLoad(downloadUrl, 2);

        }

    }

    public void stopDownLoad(String downloadUrl) {
        if (downloadUrl.equals(down_urlnow)) {
            fileDownLoad.isPause = true;
            is_finish = true;
            down_urlnow = "";
        } else {
            mDownloadDao.updateDownloaded(downloadUrl, false);
        }
    }

    public DownloadListener getmDownloadListener() {
        return mDownloadListener;
    }

    public void setmDownloadListener(DownloadListener mDownloadListener) {
        this.mDownloadListener = mDownloadListener;
    }

    public String getMdestination() {
        return mdestination;
    }

    public void setMdestination(String mdestination) {
        this.mdestination = mdestination;
    }

    private final class DownloadTask implements Runnable {
        private String murl;

        public DownloadTask(String down_url) throws Exception {
            this.murl = down_url;
        }

        @Override
        public void run() {

            try {
                fileDownLoad = new FileDownLoadTask(murl, mdestination, mContext);

                fileDownLoad.setMlistener(mSelfListener);
                fileDownLoad.download();
            } catch (Exception e) {
                mSelfListener.onError(murl, "", ERRORCODE.ERROR_BASE, e.getMessage());
            }

        }
    }

}
