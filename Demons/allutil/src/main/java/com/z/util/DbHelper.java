package com.z.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zzz on 8/19/2016.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static String NAME = "download.db";
    private static final int VERSION = 1;

    private static final String SQL_DROP = "drop table if exists " + NAME;
    private static String createDB;

    public DbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    /**
     * Create a helper object to create, open, and/or manage a database.
     * This method always returns very quickly.  The database is not actually
     * created or opened until one of {@link #getWritableDatabase} or
     * {@link #getReadableDatabase} is called.
     *
     * @param context to use to open or create the database
     * @param name    of the database file, or null for an in-memory database
     * @param factory to use for creating cursor objects, or null for the default
     * @param version number of the database (starting at 1); if the database is older,
     *                {@link #onUpgrade} will be used to upgrade the database; if the database is
     *                newer, {@link #onDowngrade} will be used to downgrade the database
     */
    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /**
     * 设置数据库的名称
     * @param dbName
     */
    public static void setDbName(String dbName) {
        DbHelper.NAME = dbName;
    }

    public static void setCreateDB(String createDB) {
        DbHelper.createDB = createDB;
    }

    /**
     * Called when the database is created for the first time. This is where the
     * creation of tables and the initial population of the tables should happen.
     *
     * @param db The database.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        if (null == createDB) {
            // TODO 如果没有创建语句
        } else {
            db.execSQL(createDB);
        }
    }

    /**
     * @param db         The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (null != createDB) {
            db.execSQL(SQL_DROP);
            db.execSQL(createDB);
        }
    }
}
