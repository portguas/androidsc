package com.example.multidownload.downloaddb;

/**
 * Created by hyl on 2016/7/2.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.multidownload.entities.DownloadThreadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库增查改删接口的实现类
 */
public class ThreadDBOImpl implements ThreadDBOperation{
    private DBHelper dbHelper;

    public ThreadDBOImpl(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    /**
     * 同一时间只能有一个线程对数据库进行操作，增,改,删
     * @param downloadThreadInfo
     */
    @Override
    public synchronized void insertThreadInfo(DownloadThreadInfo downloadThreadInfo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("insert into threadinfo(threadid, url, start, end, finishpercent) values(?, ?, ?, ?, ?)",
        new Object[]{downloadThreadInfo.getId(), downloadThreadInfo.getUrl(), downloadThreadInfo.getStart(),
                downloadThreadInfo.getEnd(), downloadThreadInfo.getFinishpercent()});
        db.close();
    }

    @Override
    public synchronized void deleteThreadInfo(String url) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from threadinfo where url = ?",
                new Object[]{url});
        db.close();
    }

    @Override
    public synchronized void updateThreadInfo(String url, int threadId, int finishpercent) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("update  threadinfo set finishpercent = ? where url = ? and threadid = ?",
                new Object[]{finishpercent, url, threadId});
        db.close();
    }

    @Override
    public List<DownloadThreadInfo> getThreadInfo(String url) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<DownloadThreadInfo> list = new ArrayList<>();
        Cursor cursor = db.rawQuery("select *from threadinfo where url = ?", new String[]{url});
        while (cursor.moveToNext()) {
            DownloadThreadInfo info = new DownloadThreadInfo();
            info.setId(cursor.getInt(cursor.getColumnIndex("threadid")));
            info.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            info.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            info.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            info.setFinishpercent(cursor.getInt(cursor.getColumnIndex("finishpercent")));
            list.add(info);
        }
        db.close();
        cursor.close();
        return list;
    }

    @Override
    public boolean isExist(String url, int threadId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select *from threadinfo where url = ? and threadid = ?", new String[]{url, threadId+""});
        boolean isExist = cursor.moveToNext();
        cursor.close();
        db.close();
        return isExist;
    }
}