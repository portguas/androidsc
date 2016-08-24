package com.personal.floatwindow;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

/**
 * Created by zzz on 8/23/2016.
 */

public class MyApplication extends Application {

    /**
     * 创建全局变量
     * 全局变量一般都比较倾向于创建一个单独的数据类文件，并使用static静态变量
     *
     * 这里使用了在Application中添加数据的方法实现全局变量
     * 注意在AndroidManifest.xml中的Application节点添加android:name=".MyApplication"属性
     *
     */
    private Context mAppContext;
    private WindowManager mWindowManager;
    private static MyApplication instance;
    private WindowManager.LayoutParams wmParams=new WindowManager.LayoutParams();

    public static MyApplication getInstance() {
        return instance;
    }

    public WindowManager.LayoutParams getWmParams(){
        return wmParams;
    }

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
        mAppContext = getApplicationContext();
        instance = this;
    }

    public WindowManager getWindowManager() {
        mWindowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        return mWindowManager;
    }

    public Context getContext() {
        return mAppContext;
    }
}
