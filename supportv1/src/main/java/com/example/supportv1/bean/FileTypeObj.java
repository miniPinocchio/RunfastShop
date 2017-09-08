package com.example.supportv1.bean;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class FileTypeObj {

    @SerializedName("file")
    public File file;//文件对象

    @SerializedName("type")
    public String type;//文件类型：jpn,png,bmp,gif,mp4等

    public FileTypeObj(String type, File file) {
        this.file = file;
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
