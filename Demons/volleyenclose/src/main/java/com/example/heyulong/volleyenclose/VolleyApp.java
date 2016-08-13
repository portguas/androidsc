package com.example.heyulong.volleyenclose;

import android.app.Application;
import android.content.Context;

/**
 * Created by heyulong on 8/9/2016.
 */

public class VolleyApp extends Application {

    private static VolleyApp instance;
    private  Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        context = getApplicationContext();
    }

    public static VolleyApp getInstance() {
        return instance;
    }

    public  Context getContext() {
        return context;
    }
}
