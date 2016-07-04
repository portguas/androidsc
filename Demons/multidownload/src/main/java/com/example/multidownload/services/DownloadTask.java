package com.example.multidownload.services;

/**
 * Created by hyl on 2016/7/2.
 */

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.multidownload.DownloadApp;
import com.example.multidownload.downloaddb.ThreadDBOImpl;
import com.example.multidownload.downloaddb.ThreadDBOperation;
import com.example.multidownload.entities.DownloadThreadInfo;
import com.example.multidownload.entities.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
    // 下载任务是否暂停的标识, 控制着所有的Thread的是否进行下载！
    public boolean isPause = false;
    private int mThreadCount = 1;

    // 下载的线程集合 管理下载的多个线程
    private List<DownloadThread> downloadThreads;

    public DownloadTask(FileInfo fileInfo, Context context, int threadcount) {
        this.mFileInfo = fileInfo;
        this.context = context;
        mThreadCount = threadcount;
        mDao = new ThreadDBOImpl(context);
    }


    /**
     * 开始下载之前会先获取需要下载的文件的长度,并记录到对应的文件类中
     */
    public void download() {
        // 读取上次的线程下载信息
        List<DownloadThreadInfo> threadInfoList = mDao.getThreadInfo(mFileInfo.getUrl());
        if (threadInfoList.size() == 0) {
            // 获取每个线程需要下载的长度
            int leng = mFileInfo.getLength() / mThreadCount;
            for (int i = 0; i < mThreadCount; i++) {
                DownloadThreadInfo threadInfo = new DownloadThreadInfo(i, mFileInfo.getUrl(),
                        leng * i, (i + 1) * leng - 1, 0);
                if (i == mThreadCount - 1) {
                    threadInfo.setEnd(mFileInfo.getLength());
                }
                // 添加到线程list
                threadInfoList.add(threadInfo);

                // 第一次下载 插入下载信息
                mDao.insertThreadInfo(threadInfo);
            }
        }
        downloadThreads = new ArrayList<>();
        // 启动多个下载线程进行下载
        for (DownloadThreadInfo info : threadInfoList) {
            DownloadThread downloadThread = new DownloadThread(info);
//            downloadThread.start();
            DownloadApp.getsExecutorService().execute(downloadThread);
            // 添加到线程管理
            downloadThreads.add(downloadThread);
        }
//        if (threadInfoList.size() == 0) {
//            threadInfo = new DownloadThreadInfo(0, mFileInfo.getUrl(), 0, mFileInfo.getLength(), 0);
//        } else {
//            threadInfo = threadInfoList.get(0);
//        }
//        new DownloadThread(threadInfo).start();
    }
    /**
     * 下载线程
     * 1.
     */
    class DownloadThread extends Thread {
        private DownloadThreadInfo downloadThreadInfo;
        // 表明当前线程是否结束
        public boolean isFinished = false;
        public DownloadThread(DownloadThreadInfo downloadThreadInfo) {
            this.downloadThreadInfo = downloadThreadInfo;
        }

        @Override
        public void run() {

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
                        if (System.currentTimeMillis() - time > mThreadCount * 500) {
                            time = System.currentTimeMillis();
                            // 按百分比显示 不能用mFinish*100 导致int类型数据溢出
                            int x = mFileInfo.getLength() / 100;
                            intent.putExtra("finish", (mFinish / x));
                            intent.putExtra("id", mFileInfo.getId());
                            context.sendBroadcast(intent);
                        }
                        if (isPause) {
                            mDao.updateThreadInfo(downloadThreadInfo.getUrl(), downloadThreadInfo.getId(), mFinish);
                            return;
                        }
                    }
                    // 标示当前线程已经下载完成
                    isFinished = true;

                    // 每结束一个线程,就去检查是否其他的线程也下载完了
                    checkAllThreadFinished();
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


    // 同一时刻只有一个线程来check 保证数据不会混乱
    /**
     * 检查所有线程的状态
     */
    private synchronized void checkAllThreadFinished() {
        boolean isAllDone = true;
        for (DownloadThread thread : downloadThreads) {
            if (!thread.isFinished) {
                isAllDone = false;
                break;
            }
        }
        if (isAllDone) {
            // 下载完成后删除线程的信息
            mDao.deleteThreadInfo(mFileInfo.getUrl());
            // 任务下载完了  广播给activity
            Intent intent = new Intent(DownloadServices.ACTION_DOWNLOADED);
            intent.putExtra("fileinfo", mFileInfo);
            context.sendBroadcast(intent);
        }
    }
}
