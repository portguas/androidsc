package com.example.multidownload;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by hyl on 2016/7/1.
 */
public class DownloadApp extends Application {

    private static DownloadApp instance;
    private static Context sAppContext;
    private static ExecutorService sExecutorService;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sAppContext = getApplicationContext();
        sExecutorService = Executors.newCachedThreadPool();
    }

    public static DownloadApp getInstance() {
        return instance;
    }

    public static Context getsAppContext() {
        return sAppContext;
    }

    public static ExecutorService getsExecutorService() {

        return sExecutorService;
    }
}
