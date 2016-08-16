package com.example.heyulong.androidutil;

import java.io.File;

import android.app.Application;
import android.content.Context;

/**
 * Created by heyulong on 8/16/2016.
 */

public class GApp extends Application {
    private static GApp instance;
    private static Context context;

    private boolean mDebug = false;

    /**
     * Called when the application is starting, before any activity, service,
     * or receiver objects (excluding content providers) have been created.
     * Implementations should be as quick as possible (for example using
     * lazy initialization of state) since the time spent in this function
     * directly impacts the performance of starting the first activity,
     * service, or receiver in a process.
     * If you override this method, be sure to call super.onCreate().
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = getApplicationContext();

        // 判断是否要打开debug模式
        try {
            File testDebug = new File("/sdcard/debug");
            if (testDebug.isFile() && testDebug.exists()) {
                mDebug = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static GApp getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }

    public boolean isDebug() {
        return mDebug;
    }
}
