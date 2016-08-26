package com.personal.processhold;

import com.orhanobut.logger.Logger;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

/**
 * Created by zzz on 8/18/2016.
 */

public class ZApplication extends Application {

    private static final String TAG = "ZApp";

    private static ZApplication instance;
    private static Context context;

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
        Logger.init(TAG);

        instance = this;
        context = getApplicationContext();

        Intent intent = new Intent(context, GrayServices.class);
        intent.setAction("StartGray");
        context.startService(intent);

    }

    public static ZApplication getInstance() {
        return instance;
    }

    public static Context getContext() {
        return context;
    }
}


