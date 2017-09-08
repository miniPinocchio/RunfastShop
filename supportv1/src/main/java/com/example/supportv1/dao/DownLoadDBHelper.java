package com.example.supportv1.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DownLoadDBHelper extends SQLiteOpenHelper {
    public static final String TABLE_DOWNLOAD = "fileDownload";
    //列名(文件url)
    public static final String COLUMN_FILEURL = "fileurl";
    //列名(文件地址)
    public static final String COLUMN_FILEPATH = "filepath";
    //列名(文件下载长度)
    public static final String COLUMN_DOWNLENGTH = "downlength";
    //列名(文件总长度)
    public static final String COLUMN_TOTALSIZE = "totalsize";
    //列名(线程id)
    public static final String COLUMN_THREADID = "threadId";
    //列名(下载状态)
    public static final String COLUMN_DOWNSTATUS = "downstatus";
    public static final String SQLCREATETABLE = "CREATE TABLE " + TABLE_DOWNLOAD + "(_id integer primary key autoincrement," 
            +COLUMN_THREADID+ " INTEGER,"
            + COLUMN_FILEURL + " varchar(200)," + COLUMN_FILEPATH + " varchar(100)," + COLUMN_DOWNLENGTH + " INTEGER," 
                    + COLUMN_TOTALSIZE + " INTEGER," + COLUMN_DOWNSTATUS + " INTEGER)";

	public DownLoadDBHelper(Context context) {
		super(context, "MultiDownLoad.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
	 		db.execSQL(SQLCREATETABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//  Auto-generated method stub

	}

}
