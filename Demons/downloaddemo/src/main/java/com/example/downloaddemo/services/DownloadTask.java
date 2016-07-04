package com.example.downloaddemo.services;

/**
 * Created by hyl on 2016/7/2.
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.downloaddemo.DownloadApp;
import com.example.downloaddemo.downloaddb.ThreadDBOImpl;
import com.example.downloaddemo.downloaddb.ThreadDBOperation;
import com.example.downloaddemo.entities.DownloadThreadInfo;
import com.example.downloaddemo.entities.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * 下载任务类
 */
public class DownloadTask {

    private FileInfo mFileInfo;
    private Context context;
    private ThreadDBOperation mDao;
    // 总文件的下载进度
    private int mFinish = 0;
    public boolean isPause = false;

    public DownloadTask(FileInfo fileInfo, Context context) {
        this.mFileInfo = fileInfo;
        this.context = context;
        mDao = new ThreadDBOImpl(context);
    }


    public void download() {
        // 读取上次的线程下载信息
        List<DownloadThreadInfo> threadInfoList = mDao.getThreadInfo(mFileInfo.getUrl());
        DownloadThreadInfo threadInfo;
        if (threadInfoList.size() == 0) {
            threadInfo = new DownloadThreadInfo(0, mFileInfo.getUrl(), 0, mFileInfo.getLength(), 0);
        } else {
            threadInfo = threadInfoList.get(0);
        }
        new DownloadThread(threadInfo).start();
    }
    /**
     * 下载线程
     * 1.
     */
    class DownloadThread extends Thread {
        private DownloadThreadInfo downloadThreadInfo;
        public DownloadThread(DownloadThreadInfo downloadThreadInfo) {
            this.downloadThreadInfo = downloadThreadInfo;
        }

        @Override
        public void run() {
            // 第一次下载 插入下载信息
            if (!mDao.isExist(downloadThreadInfo.getUrl(), downloadThreadInfo.getId())) {
                mDao.insertThreadInfo(downloadThreadInfo);
            }
            RandomAccessFile raf = null;
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            // 设置远程的下载位置
            try {
                URL url = new URL(downloadThreadInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                int start = downloadThreadInfo.getStart() + downloadThreadInfo.getFinishpercent();
                // 线程的结束位置 如果是单个线程下载一个文件,这个结束的位置就是文件的最后位置,如果是多个文件下载
                // 比如一个100大小的文件,2个线程下载 一个是0-54,一个是55-99 ，那么第一个线程的结束位置是54,第二个是99
                connection.setRequestProperty("Range","bytes="+start+"-"+downloadThreadInfo.getEnd());
                // 文件的写入位置
                File file = new File(DownloadApp.getsAppContext().getExternalFilesDir("downloaddemo"), mFileInfo.getName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                mFinish += downloadThreadInfo.getFinishpercent();
                Intent intent = new Intent(DownloadServices.ACTION_UPDATEUI);
                Log.d("test", mFileInfo.getLength() + "");
                // 开始下载
                if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                    inputStream = connection.getInputStream();
                    byte[] buffer = new byte[1024 * 4];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while ((len = inputStream.read(buffer)) != -1) {
                        // 写入文件
                        raf.write(buffer, 0, len);
                        // 把下载进度发送到activity
                        mFinish += len;
                        Log.d("test", mFinish+ "文件大小");
                        if (System.currentTimeMillis() - time > 500) {
                            time = System.currentTimeMillis();
                            // 按百分比显示 不能用mFinish*100 导致int类型数据溢出
                            int x = mFileInfo.getLength() / 100;
                            intent.putExtra("finish", (mFinish / x));
                            context.sendBroadcast(intent);
                        }
                        if (isPause) {
                            mDao.updateThreadInfo(downloadThreadInfo.getUrl(), downloadThreadInfo.getId(), mFinish);
                            return;
                        }
                    }
                    // 下载完成后删除线程的信息
                    mDao.deleteThreadInfo(downloadThreadInfo.getUrl(), downloadThreadInfo.getId());
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    connection.connect();
                    inputStream.close();
                    raf.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
