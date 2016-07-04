package com.example.downloaddemo.services;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.StringRequest;
import com.example.downloaddemo.entities.FileInfo;
import com.example.downloaddemo.util.ApiController;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by hyl on 2016/7/1.
 */
public class DownloadServices  extends Service{

    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_DOWNLOAD = "ACTION_DOWNLOAD";
    public static final String ACTION_UPDATEUI = "ACTION_UPDATEUI";

    private DownloadTask mTask;

    public static final int MSG_INIT = 0;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 獲取Activity传送过来的数据
        if (ACTION_START == intent.getAction()) {
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            new Thread(new InitThread(fileInfo)).start();
        } else if (ACTION_STOP == intent.getAction()){
            FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            if (mTask != null) {
                mTask.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    /**
     * 初始化下载任务的线程,主要是为了获取下载的任务的文件的大小
     */
    public class InitThread implements Runnable {
        private FileInfo fileInfo;

        public InitThread(FileInfo fileInfo) {
            this.fileInfo = fileInfo;
         }

        @Override
        public void run() {

            /**
             * vollery 库不适合下载文件 一般用于请求图, 文字和json
             */
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;

            try {
                int length = -1;
                URL url = new URL(fileInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(3000);
                connection.setRequestMethod("GET");
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    length = connection.getContentLength();
                }
                if (length < 0) {
                    return;
                }
                File dir = new File(getPath());
                if (!dir.exists()) {
                    dir.mkdir();
                }
                File file = new File(dir, fileInfo.getName());
                try {
                    raf = new RandomAccessFile(file, "rwd");
                    raf.setLength(length);
                    fileInfo.setLength(length);
                    handler.obtainMessage(MSG_INIT, fileInfo).sendToTarget();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        raf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

           /* StringRequest request = new StringRequest(Request.Method.GET, fileInfo.getUrl(), new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Log.d("test", "11");
                    if (s.length() <= 0) {
                        return;
                    }
                    File dir = new File(getPath());
                    if (!dir.exists()) {
                        dir.mkdir();
                    }
                    File file = new File(dir, fileInfo.getName());
                    RandomAccessFile raf = null;
                    try {
                        raf = new RandomAccessFile(file, "rwd");
                        raf.setLength(s.length());
                        fileInfo.setLength(s.length());
                        handler.obtainMessage(MSG_INIT, fileInfo).sendToTarget();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        try {
                            raf.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Log.d("test", volleyError.getMessage());
                }
            }) {
                @Override
                public RetryPolicy getRetryPolicy() {
                    RetryPolicy retryPolicy = new DefaultRetryPolicy(3000, 3, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    return retryPolicy;
                }
            };
            ApiController.getInstance().addToRequestQueue(request);*/
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    // 启动下载任务
                    mTask = new DownloadTask(fileInfo, DownloadServices.this);
                    mTask.download();
                    break;

                default:
                    break;
            }
        }
    };

    public String getPath() {
        return getExternalFilesDir("downloaddemo").getAbsolutePath();
    }
}
