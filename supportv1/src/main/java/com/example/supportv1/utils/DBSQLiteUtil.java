package com.example.supportv1.utils;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBSQLiteUtil
{
    private static final String TAG = "DBSQLiteUtil";

    @SuppressWarnings("unused")
    private Activity activity = null;

    /**
     * SQLite数据库实例
     */
    private SQLiteDatabase mDb = null;

    /**
     * 数据库创建帮手
     */
    private static CreateDBHelper mDbHelper = null;

    /**
     * 数据库版本号
     */
    private int version = 1;

    /**
     * 数据库名称
     */
    private String dbName = null;

    /**
     * 在创建数据库时初始化SQL
     */
    private String[] createSqls = null;

    /**
     * 更新数据库SQL
     */
    private String[] updateSqls = null;

    /**
     * 表示数据库实例的引用次数<br>
     * 只有当次数为0时，表示已经没有操作在引用，此时可以关闭SQLiteDatabase，释放内存<br>
     * 当次数>0时，表示有其它操作在引用，不关闭SQLiteDatabase
     */
    private int referenceCount = 0;

    /**
     * 构造函数
     * 
     * @param dbName
     *            数据库存名称
     */
    public DBSQLiteUtil(String dbName)
    {
        referenceCount = 0;
        this.dbName = dbName;
    }

    /**
     * 构造函数
     * 
     * @param dbName
     *            数据库存名称
     * @param secretKey
     *            密钥
     */
    public DBSQLiteUtil(String dbName, String secretKey)
    {
        referenceCount = 0;
        this.dbName = dbName;
    }

    /**
     * 构造函数
     * 
     * @param dbName
     *            数据库存名称
     * @param version
     *            版本库版本
     */
    public DBSQLiteUtil(String dbName, int version)
    {
        referenceCount = 0;
        this.dbName = dbName;
        this.version = version;
    }

    /**
     * 构造函数
     * 
     * @param dbName
     *            数据库存名称
     * @param version
     *            版本库版本
     * @param secretKey
     *            密钥
     */
    public DBSQLiteUtil(String dbName, int version, String secretKey)
    {
        referenceCount = 0;
        this.dbName = dbName;
        this.version = version;
    }

    /**
     * 得到数据库版本号
     * 
     * @return
     */
    public int getDBVersion()
    {
        return version;
    }

    /**
     * 得到数据库名称
     * 
     * @return
     */
    public String getDBName()
    {
        return dbName;
    }

    /**
     * 得到数据库实例，注意在使用前必须要调用open打开，用完close
     * 
     * @return SQLiteDatabase
     */
    public SQLiteDatabase getSQLiteDatabase()
    {
        return mDb;
    }

    /**
     * 打开或者创建一个指定名称的数据库
     * 
     * @param activity
     */
    public synchronized void open(Activity activity)
    {
        Log.i(TAG, "Open database '" + getDBName() + "' with Activiy referenceCount is " + referenceCount);
        if (activity == null)
        {
            Log.w(TAG, "activity is null");
        }
        this.activity = activity;
        if (mDbHelper == null)
        {
            mDbHelper = new CreateDBHelper(activity);
        }
        if (referenceCount == 0)
        {
            mDb = mDbHelper.getWritableDatabase();
        }
        referenceCount++;
    }

    /**
     * 打开或者创建一个指定名称的数据库
     * 
     * @param ctx
     */
    public synchronized void open(Context ctx)
    {
        Log.i(TAG, "Open database '" + getDBName() + "' with Context referenceCount is " + referenceCount);
        if (ctx == null)
        {
            Log.w(TAG, "Context is null");
        }
        if (mDbHelper == null)
        {
            mDbHelper = new CreateDBHelper(ctx);
        }
        if (referenceCount == 0)
        {
            mDb = mDbHelper.getWritableDatabase();
        }
        referenceCount++;
    }

    /**
     * 打开或者创建一个指定名称的数据库
     * 
     * @param activity
     * @param createSqls
     *            初始化SQL,在创建数据库时调用
     * @param updateSqls
     *            更新数据库SQL,在打开数据库版本不匹配时调用
     */
    public synchronized void open(Activity activity, String[] createSqls, String[] updateSqls)
    {
        Log.i(TAG, "Open database '" + getDBName() + "' with Activiy referenceCount is " + referenceCount);
        this.createSqls = createSqls;
        this.updateSqls = updateSqls;
        this.activity = activity;
        if (mDbHelper == null)
        {
            mDbHelper = new CreateDBHelper(activity);
        }
        if (referenceCount == 0)
        {
            mDb = mDbHelper.getWritableDatabase();
        }
        referenceCount++;
    }

    /**
     * 打开或者创建一个指定名称的数据库
     * 
     * @param ctx
     * @param createSqls
     *            初始化SQL,在创建数据库时调用
     * @param updateSqls
     *            更新数据库SQL,在打开数据库版本不匹配时调用
     */
    public synchronized void open(Context ctx, String[] createSqls, String[] updateSqls)
    {
        Log.i(TAG, "Open database '" + getDBName() + "' with Context  referenceCount is " + referenceCount);
        this.createSqls = createSqls;
        this.updateSqls = updateSqls;
        if (mDbHelper == null)
        {
            mDbHelper = new CreateDBHelper(ctx);
        }
        if (referenceCount == 0)
        {
            mDb = mDbHelper.getWritableDatabase();
        }
        referenceCount++;
    }

    /**
     * 关闭数据库
     */
    public synchronized void close()
    {
        referenceCount--;
        Log.i(TAG, "Close database referenceCount is " + referenceCount);
        if (referenceCount <= 0)
        {//只有引用为0，可以关闭
            if (mDb != null)
            {
                Log.i(TAG, "Close database '" + getDBName() + "'");
                mDb.close();
            }
        }
    }

    /**
     * 数据库创建内部类
     */
    private class CreateDBHelper extends SQLiteOpenHelper
    {

        public CreateDBHelper(Context ctx)
        {
            super(ctx, getDBName(), null, getDBVersion());
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            Log.i(TAG, "Create database '" + getDBName() + ", version:" + getDBVersion());
            executeBatch(createSqls, db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.i(TAG, "Upgrading database '" + getDBName() + "' from version " + getDBVersion() + " to " + newVersion);
            executeBatch(updateSqls, db);
        }

        /**
         * 批量执行Sql语句
         * 
         * @param sqls
         *            sql语句数组
         * @param db
         *            数据库实例
         */
        private void executeBatch(String[] sqls, SQLiteDatabase db)
        {
            if (sqls == null)
                return;

            db.beginTransaction();
            try
            {
                int len = sqls.length;
                for (int i = 0; i < len; i++)
                {
                    db.execSQL(sqls[i]);
                }
                db.setTransactionSuccessful();
            }
            catch (Exception e)
            {
                Log.e(TAG, e.getMessage());
                e.printStackTrace();
            }
            finally
            {
                db.endTransaction();
            }

        }
    }

    /**
     * 批量执行Sql语句,注意先要打开数据库，执行完要关闭数据库
     * 
     * @param sqls
     *            sql语句数组
     */
    public synchronized void executeBatch(String[] sqls)
    {
        if (sqls == null)
            return;
        try
        {
            mDb.beginTransaction();
            int len = sqls.length;
            for (int i = 0; i < len; i++)
            {
                mDb.execSQL(sqls[i]);
            }
            mDb.setTransactionSuccessful();
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            mDb.endTransaction();
        }

    }

    /**
     * 批量执行Sql语句,注意先要打开数据库，执行完要关闭数据库
     * 
     * @param sql
     *            sql语句数组
     */
    public synchronized boolean execute(String sql, Object[] params)
    {
        if (sql == null)
            return false;
        try
        {
            mDb.beginTransaction();
            mDb.execSQL(sql, params);
            mDb.setTransactionSuccessful();
            return true;
        }
        catch (Exception e)
        {
            Log.e(TAG, e.getMessage());
            e.printStackTrace();
        }
        finally
        {
            mDb.endTransaction();
        }
        return false;
    }

    /**
     * 判断数据库是否存在
     * 
     * @return true为存在 false不存在
     */
    public boolean isExistsDB(Context context)
    {
        return true;
    }
}
