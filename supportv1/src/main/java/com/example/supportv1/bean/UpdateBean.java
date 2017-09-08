package com.example.supportv1.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @Description: App更新Bean
 */
public class UpdateBean implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @SerializedName("versionCode")
    private int versionCode = 0;//app版本号，暂不用

    @SerializedName("versionName")
    private String versionName;  //app版本名称 eg: 1.1.0

    @SerializedName("downloadUrl")
    private String downloadUrl;//app下载地址

    @SerializedName("updateLog")
    private String updateLog;//app更新日志，暂不用

    @SerializedName("appName")
    private String appName; //app名称英文 eg: weixin

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

}
