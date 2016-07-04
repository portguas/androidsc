package com.example.downloaddemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by hyl on 2016/7/1.
 */
public class DownloadApp extends Application {

    private static DownloadApp instance;
    private static Context sAppContext;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sAppContext = getApplicationContext();
    }

    public static DownloadApp getInstance() {
        return instance;
    }

    public static Context getsAppContext() {
        return sAppContext;
    }
}
