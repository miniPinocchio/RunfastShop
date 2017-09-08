package com.example.supportv1.dao;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.supportv1.bean.DownLoadBean;

import java.util.HashMap;
import java.util.Map;

public class DownloadDao {
    /**
     * 用于查询数据库
     */
    private DownLoadDBHelper dbHelper;
    /**
     * 获取主区域的SQL语句
     */
    private static final String SELECTDOWNLOADEDLEN = "SELECT threadId,downLength FROM fileDownload WHERE " + DownLoadDBHelper.COLUMN_FILEURL + "=?";
    private static int COL_INDEX = 0;
    private static int COL_THREADID = ++COL_INDEX;
    private static int COL_FILEURL = ++COL_INDEX;
    private static int COL_FILEPATH = ++COL_INDEX;
    private static int COL_DOWNLENGTH = ++COL_INDEX;
    private static int COL_TOTALSIZE = ++COL_INDEX;
    private static int COL_DOWNSTATUS = ++COL_INDEX;

    /**
     * 构造函数
     */
    public DownloadDao(DownLoadDBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    /**
     * 根据下载的url获取已经下载的大小
     *
     * @param downurl 下载的url
     * @return Map 下载的大小
     */
    @SuppressLint("UseSparseArrays")
    public Map<Integer, Integer> getDownloadedLength(String downurl) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = SELECTDOWNLOADEDLEN;
        Cursor cursor = db.rawQuery(sql, new String[]{downurl});
        Map<Integer, Integer> data = new HashMap<Integer, Integer>();
        while (cursor.moveToNext()) {
            data.put(cursor.getInt(0), cursor.getInt(1));
        }
        db.close();
        return data;
    }

    /**
     * 保持download信息
     *
     * @param downUrl    下载的url
     * @param downstatus 下载状态
     */
    public void addNewDownLoad(String downUrl, int downstatus) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            String selectSql = "select count (*) from fileDownload where fileurl =?";
            Cursor cursor = null;
            int count = 0;
            cursor = db.rawQuery(selectSql, new String[]{downUrl});
            while (cursor.moveToNext()) {
                count = cursor.getInt(0);

            }
            if (count <= 0) {
                String sql = "INSERT INTO fileDownload(fileurl,threadId,downstatus) values(?,?,?)";
                db.execSQL(sql, new Object[]{downUrl, 0, downstatus});
                db.execSQL(sql, new Object[]{downUrl, 1, downstatus});
                db.execSQL(sql, new Object[]{downUrl, 2, downstatus});
                db.setTransactionSuccessful();
            } else {
                String sql = "UPDATE fileDownload SET downstatus=? WHERE fileurl=?";
                db.execSQL(sql, new Object[]{downstatus, downUrl});
                db.setTransactionSuccessful();
            }
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 保存download信息
     *
     * @param downUrl    下载的url
     * @param total_size 下载总共的大小
     * @param downstatus 下载状态
     * @param filepath   文件路径
     */
    public void saveDownLoading(String downUrl, int total_size, int downstatus, String filepath) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            String selectSql = "select count (*) from fileDownload where fileurl =?";
            Cursor cursor = null;
            int count = 0;
            cursor = db.rawQuery(selectSql, new String[]{downUrl});
            while (cursor.moveToNext()) {
                count = cursor.getInt(0);

            }
            if (count <= 0) {
                String sql = "INSERT INTO fileDownload(fileurl,threadId,totalsize,downstatus,filepath) values(?,?,?,?,?)";
                db.execSQL(sql, new Object[]{downUrl, 0, total_size, downstatus, filepath});
                db.execSQL(sql, new Object[]{downUrl, 1, total_size, downstatus, filepath});
                db.execSQL(sql, new Object[]{downUrl, 2, total_size, downstatus, filepath});
                db.setTransactionSuccessful();
            } else {
                String sql = "UPDATE fileDownload SET totalsize =? ,downstatus =?,filepath =? WHERE fileurl=?";
                db.execSQL(sql, new Object[]{total_size, downstatus, filepath, downUrl});
                db.setTransactionSuccessful();
            }
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 更新正在下载的数据
     *
     * @param downLength 每个线程下载的数据(数组)
     * @param down_url   文件下载地址
     */
    public void updateDownloading(int[] downLength, String down_url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            String sql = "UPDATE fileDownload SET downlength=? WHERE threadId=? AND fileurl=?";
            for (int i = 0; i < downLength.length; i++) {
                db.execSQL(sql, new String[]{downLength[i] + "", i + "", down_url});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 更新下载完成,或者下载暂停的数据
     *
     * @param down_url  文件下载地址
     * @param is_finish 是否下载结束
     */
    public void updateDownloaded(String down_url, boolean is_finish) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            String sql = "UPDATE fileDownload SET downstatus=? WHERE fileurl=?";
            int downstatus = 0;
            if (is_finish) {
                downstatus = 3;
            }
            db.execSQL(sql, new String[]{downstatus + "", down_url});
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 查找下载的url是否已经存在
     *
     * @param fileUrl 文件下载地址
     * @return true:存在  false:不存在
     */
    public boolean queryIsexist(String fileUrl) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select count(*) from " + DownLoadDBHelper.TABLE_DOWNLOAD + " where " + DownLoadDBHelper.COLUMN_FILEURL + " =? ";
        Cursor cursor = null;
        cursor = db.rawQuery(sql, new String[]{fileUrl});
        while (cursor.moveToNext()) {
            if (cursor.getInt(0) > 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 查找对应url的数据
     *
     * @param down_url 文件下载地址
     * @return DownLoadData 数据库保存数据
     */
    public DownLoadBean getDownloadByUrl(String down_url) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * from " + DownLoadDBHelper.TABLE_DOWNLOAD + " where fileurl =?";
        Cursor cursor = db.rawQuery(sql, new String[]{down_url});
        DownLoadBean itemData = null;
        int downLoad_size = 0;
        while (cursor.moveToNext()) {
            downLoad_size += cursor.getInt(COL_DOWNLENGTH);
            if (itemData == null) {
                itemData = new DownLoadBean();
                initData(cursor, itemData);
            }
        }
        if (itemData != null) {
            itemData.setDownloadedLength(downLoad_size);
        }
        db.close();
        return itemData;
    }

    /**
     * 获取最新的等待下载的任务
     *
     * @return String  文件url
     */
    public String getNewestDownLoadUrl() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "select fileurl from " + DownLoadDBHelper.TABLE_DOWNLOAD + " where downstatus =2 limit 0,1";
        Cursor cursor = db.rawQuery(sql, null);
        String mdown_url = "";
        while (cursor.moveToNext()) {
            mdown_url = cursor.getString(0);
        }
        db.close();
        return mdown_url;
    }

    /**
     * 跟新下载任务的状态
     *
     * @param downUrl    文件url
     * @param downStatus 下载状态
     * @param total_size 文件大小
     */
    public void uploadDownloadFlag(String downUrl, int downStatus, int total_size) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            String sql = "";
            if (total_size == 0) {
                sql = "UPDATE fileDownload SET " + DownLoadDBHelper.COLUMN_DOWNSTATUS + " =? " + " where " + DownLoadDBHelper.COLUMN_FILEURL + " =? ";
                db.execSQL(sql, new Object[]{downStatus, downUrl});
            } else {
                sql = "UPDATE fileDownload SET " + DownLoadDBHelper.COLUMN_DOWNSTATUS + " =? , " + DownLoadDBHelper.COLUMN_TOTALSIZE + " =?  " + " where " + DownLoadDBHelper.COLUMN_FILEURL + "=?";
                db.execSQL(sql, new Object[]{downStatus, total_size, downUrl});
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    /**
     * 删除莫个下载任务
     *
     * @param down_url 文件url
     */
    public void deleteDownloading(String down_url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "DELETE FROM fileDownload WHERE fileUrl=?";
        db.execSQL(sql, new Object[]{down_url});
        db.close();
    }

    /**
     * 清空下载任务
     */
    public void clear() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "delete from " + DownLoadDBHelper.TABLE_DOWNLOAD;
        db.execSQL(sql);
        db.close();
    }

    /**
     * 数据库内容写人类
     *
     * @param cursor   数据库游标
     * @param itemData 数据保存类
     */
    private void initData(Cursor cursor, DownLoadBean itemData) {
        itemData.setThread_id(cursor.getInt(COL_THREADID));
        itemData.setDown_url(cursor.getString(COL_FILEURL));
        itemData.setFile_path(cursor.getString(COL_FILEPATH));
        itemData.setDownloadedLength(cursor.getInt(COL_DOWNLENGTH));
        itemData.setTotal_size(cursor.getInt(COL_TOTALSIZE));
        itemData.setDownload_flag(cursor.getInt(COL_DOWNSTATUS));
    }
}
