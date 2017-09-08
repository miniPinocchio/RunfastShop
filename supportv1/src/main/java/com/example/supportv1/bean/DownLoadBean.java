package com.example.supportv1.bean;

import com.google.gson.annotations.SerializedName;

public class DownLoadBean {
    /**
     * 下载地址
     */
    @SerializedName("down_url")
    private String down_url;
    /**
     * 文件路径
     */
    @SerializedName("file_path")
    private String file_path;
    /**
     * 下载大小
     */
    @SerializedName("downloadedLength")
    private int downloadedLength;
    /**
     * 总大小
     */
    @SerializedName("total_size")
    private int total_size;
    /**
     * 下载标志位
     */
    @SerializedName("download_flag")
    private int download_flag;
    /**
     * 线程id
     */
    @SerializedName("thread_id")
    private int thread_id;

    public DownLoadBean() {

    }

    public DownLoadBean(String down_url, String file_path, int downloadedLength, int total_size, int download_flag) {
        this.down_url = down_url;
        this.file_path = file_path;
        this.downloadedLength = download_flag;
        this.total_size = total_size;
        this.download_flag = download_flag;
    }

    public String getDown_url() {
        return down_url;
    }

    public void setDown_url(String down_url) {
        this.down_url = down_url;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public int getDownloadedLength() {
        return downloadedLength;
    }

    public void setDownloadedLength(int downloadedLength) {
        this.downloadedLength = downloadedLength;
    }

    public int getTotal_size() {
        return total_size;
    }

    public void setTotal_size(int total_size) {
        this.total_size = total_size;
    }

    public int getDownload_flag() {
        return download_flag;
    }

    public void setDownload_flag(int download_flag) {
        this.download_flag = download_flag;
    }

    public int getThread_id() {
        return thread_id;
    }

    public void setThread_id(int thread_id) {
        this.thread_id = thread_id;
    }
}
