package com.example.hyl.aapp;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hyl on 7/19/2016.
 */
public class MainApplication extends Application {

    private static List<Activity> mActivitys;
    private static Context sAppContext;
    private static MainApplication instance;


    public static MainApplication getInstance(){
        return instance;
    }

    public static Context getContext() {
        return sAppContext;
    }

    public static List<Activity> getmActivitys() {
        return mActivitys;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        mActivitys = new ArrayList<>();
        sAppContext = getApplicationContext();
        instance = this;
    }
}
