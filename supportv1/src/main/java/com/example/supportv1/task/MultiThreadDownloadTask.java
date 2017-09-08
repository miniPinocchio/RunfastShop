package com.example.supportv1.task;



import com.example.supportv1.assist.DownLoadManager;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


public final class MultiThreadDownloadTask implements Runnable {
    public int threadId;
    private RandomAccessFile savedFile;
    private String down_url;
    /* 当前已下载量 */
    public int currentDownloadSize = 0;
    /* 下载状态 */
    public boolean finished;
    /* 用于监视下载状态 */
    private final FileDownLoadTask downloadService;
    /* 线程下载任务的起始点 */
    public int start;
    /* 线程下载任务的结束点 */
    private int end;
    private String file_path;

    public MultiThreadDownloadTask(int id, File savedFile, int block, String path, Integer downlength, FileDownLoadTask downloadService) throws Exception {
        this.threadId = id;
        this.down_url = path;
        if (downlength != null) this.currentDownloadSize = downlength;
        file_path = savedFile.getAbsolutePath();
        this.savedFile = new RandomAccessFile(savedFile, "rwd");
        this.downloadService = downloadService;
        start = id * block + currentDownloadSize;
        end = (id + 1) * block;
    }

    @Override
    public void run() {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(down_url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Range", "bytes=" + start + "-" + end);

            InputStream in = conn.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            savedFile.seek(start);
            while (!downloadService.isPause && (len = in.read(buffer)) != -1) {
                savedFile.write(buffer, 0, len);
                currentDownloadSize += len;
            }
            savedFile.close();
            in.close();
            conn.disconnect();
            finished = true;
        } catch (Exception e) {
            downloadService.getDownloadListener().onError(down_url, file_path, DownLoadManager.ERRORCODE.ERROR_BASE, "网络请求失败");
            e.printStackTrace();
        }
    }
}
