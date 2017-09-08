package com.example.supportv1.utils;

import android.annotation.SuppressLint;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.supportv1.R;
import com.example.supportv1.bean.UpdateBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;

/**
 * 应用程序更新工具包
 */
@SuppressLint("HandlerLeak")
public class AppVersionUpdateUtil {
    public final String TAG = AppVersionUpdateUtil.class.getSimpleName();

    private static final int NOTIFICATION_UPDATE_ID = 10;
    private static final int DOWN_NOSDCARD = 0;
    private static final int DOWN_UPDATE = 1;
    private static final int DOWN_OVER = 2;

    private static AppVersionUpdateUtil updateManager;

    private Context mContext;
    // 进度值
    private int progress;
    // 下载线程
    private Thread downLoadThread;
    // 终止标记
    private boolean interceptFlag;
    // 下载包保存路径
    private String savePath = "";
    // apk保存完整路径
    private String apkFilePath = "";
    // 临时下载文件路径
    private String tmpFilePath = "";
    // 下载文件大小
    @SuppressWarnings("unused")
    private String apkFileSize;
    // 已下载文件大小
    @SuppressWarnings("unused")
    private String tmpFileSize;
    private UpdateBean mUpdate;

    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotifyManager;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mBuilder.setProgress(100, progress, false);
                    progressDialog.setProgress(progress);
                    Log.d("progressDialog","progress = "+progress);
                    mNotifyManager.notify(NOTIFICATION_UPDATE_ID, mBuilder.build());
                    break;
                case DOWN_OVER:
                    mBuilder.setContentTitle("下载完成").setContentText("文件已下载完毕").setProgress(0, 0, false);
                    mNotifyManager.notify(NOTIFICATION_UPDATE_ID, mBuilder.build());
                    mNotifyManager.cancel(NOTIFICATION_UPDATE_ID);
                    progressDialog.dismiss();
                    installApk();
                    break;
                case DOWN_NOSDCARD:
                    Toast.makeText(mContext, "无法下载安装文件，请检查SD卡是否挂载", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    };
    private ProgressDialog progressDialog;

    /**
     * 显示下载进度对话框
     */
    public void showDownloadDialog() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setTitle("正在下载...");
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
    }

    /**
     * 得到下载工具类
     *
     * @return
     */
    public static AppVersionUpdateUtil getUpdateManager() {
        if (updateManager == null) {
            updateManager = new AppVersionUpdateUtil();
        }
        return updateManager;
    }

    /**
     * 设置更新参数bean
     *
     * @param update
     */
    public void setUpdateBean(UpdateBean update) {
        this.mUpdate = update;
    }

    /**
     * 开启下载线程开启下载安装包
     *
     * @param context
     */
    public void startDownload(Context context) {
        this.mContext = context;

        mNotifyManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setTicker("下载中...").setContentTitle("正在下载新版本").setContentText("下载进度").setSmallIcon(R.drawable.stat_sys_download);
        mBuilder.setAutoCancel(false);
        downloadApk();
        showDownloadDialog();
    }

    /**
     * 下载线程
     */
    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                String apkName = mUpdate.getAppName() + "_" + mUpdate.getVersionName() + ".apk";
                String tmpApk = mUpdate.getAppName() + "_" + mUpdate.getVersionName() + ".tmp";
                // 判断是否挂载了SD卡
                String storageState = Environment.getExternalStorageState();
                if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                    savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + mUpdate.getAppName() + "/update/";
                    File file = new File(savePath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    apkFilePath = savePath + apkName;
                    tmpFilePath = savePath + tmpApk;
                }

                // 没有挂载SD卡，无法下载文件
                if (StringUtil.isBlank(apkFilePath)) {
                    mHandler.sendEmptyMessage(DOWN_NOSDCARD);
                    return;
                }

                File ApkFile = new File(apkFilePath);

                // 是否已下载更新文件
                if (ApkFile.exists()) {
                    installApk();
                    return;
                }

                // 输出临时下载文件
                File tmpFile = new File(tmpFilePath);
                FileOutputStream fos = new FileOutputStream(tmpFile);

                URL url = new URL(mUpdate.getDownloadUrl());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();

                // 显示文件大小格式：2个小数点显示
                DecimalFormat df = new DecimalFormat("0.00");
                // 进度条下面显示的总文件大小
                apkFileSize = df.format((float) length / 1024 / 1024) + "MB";
                int count = 0;
                int lastRate = 0;
                byte buf[] = new byte[1024];
                do {
                    int numread = is.read(buf);
                    count += numread;
                    // 进度条下面显示的当前下载文件大小
                    tmpFileSize = df.format((float) count / 1024 / 1024) + "MB";
                    // 当前进度值
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    // 每百分之一更新一次UI
                    if (progress - lastRate >= 1) {
                        lastRate = progress;
                        mHandler.sendEmptyMessage(DOWN_UPDATE);
                    }
                    if (numread <= 0) {
                        // 下载完成 - 将临时下载文件转成APK文件
                        if (tmpFile.renameTo(ApkFile)) {
                            // 通知安装
                            mHandler.sendEmptyMessage(DOWN_OVER);
                        }
                        break;
                    }
                    fos.write(buf, 0, numread);
                }
                while (!interceptFlag);// 点击取消就停止下载
                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    /**
     * 下载apk
     */
    private void downloadApk() {
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }

    /**
     * 安装apk
     */
    private void installApk() {
        File apkfile = new File(apkFilePath);
        if (!apkfile.exists()) {
            return;
        }
        String systemModel = getSystemModel();
        String deviceBrand = getDeviceBrand();
        Log.d("model","systemModel = "+systemModel+",deviceBrand= "+deviceBrand);
        Uri downloadFileUri = null;

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // "net.csdn.blog.ruancoder.fileprovider"即是在清单文件中配置的authorities
            downloadFileUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName(), apkfile);
            // 给目标应用一个临时授权
            i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            downloadFileUri = Uri.parse("file://" + apkfile);
        }
        i.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
        mContext.startActivity(i);
        progressDialog.dismiss();
    }

    /**
     * 获取手机型号
     *
     * @return  手机型号
     */
    public static String getSystemModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取手机厂商
     *
     * @return  手机厂商
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }
}
