package Util;


import com.example.heyulong.androidutil.GApp;

import android.util.Log;
import android.widget.Toast;

/**
 * Created by zhouruijun on 2016/7/21.
 */
public class LogHelper {
    private static final String TAG = "bdbonuspocket";

    // 参数: 类的this指针  , 日志内容
    public static <T> void printLog(T c, String info) {
        printLog(c, info, false);
    }

    // 参数: 类的this指针  , 日志内容 , 错误类型
    public static <T> void printLog(T c, String info, boolean error) {

        if (GApp.getInstance().isDebug()) {

            try {
                if (error) {
                    Log.e(TAG, "[" + c.getClass().getName() + "] :" + info);
                } else {
                    Log.d(TAG, "[" + c.getClass().getName() + "] :" + info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
