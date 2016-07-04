package com.example.multidownload.downloaddb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by hyl on 2016/7/2.
 * 设置为单例 保证读写数据库安全
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "download.db";
    private static final int VERSION = 1;
    private static final String SQL_CREATE = "create table threadinfo(_id integer primary key autoincrement," +
            "threadid integer, url text, start integer, end integer, finishpercent integer)";

    private static final String SQL_DROP = "drop table if exists threadinfo";

    private static DBHelper dbHelper = null;
    private DBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DROP);
        db.execSQL(SQL_CREATE);
    }
}
