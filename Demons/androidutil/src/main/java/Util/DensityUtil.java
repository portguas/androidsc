package Util;


import com.example.heyulong.androidutil.GApp;

import android.content.Context;

/**
 * Created by zhouruijun on 2016/4/27.
 */
public class DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int px1080ToCurrentPx( float pxValue) {

        float dp = pxValue / 3;
        return dip2px(GApp.getInstance().getContext(), dp);
    }

    public static int pxXhdpiToCurrentPx(float pxValue) {

        float dp = pxValue / 2;
        return dip2px( GApp.getInstance().getContext() , dp);
    }
}
