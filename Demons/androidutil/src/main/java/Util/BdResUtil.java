package Util;


import com.example.heyulong.androidutil.GApp;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * 获取资源
 */
public class BdResUtil {

    private static Resources res = GApp.getInstance().getContext().getResources();

    /**
     * 取得 drawble 类型图片资源
     *
     * @param resID
     * @return
     */
    public static Drawable getDrawable(int resID) {
        return res.getDrawable(resID);
    }

    /**
     * 取得 color
     *
     * @param resID
     * @return
     */
    public static int getColor(int resID) {
        return res.getColor(resID);
    }

    /**
     * 取得 String []
     *
     * @param resID
     * @return
     */
    public static String[] getStrArray(int resID) {
        return res.getStringArray(resID);
    }

    /**
     * 取得 String
     *
     * @param resID
     * @return
     */
    public static String getStr(int resID) {
        return res.getString(resID);
    }

    /**
     * 从资源文件（单位为dip）取得控件大小（单位为pix）
     *
     * @param resID
     * @return
     */
    public static int getDimen(int resID) {
        return res.getDimensionPixelSize(resID);
    }

}
