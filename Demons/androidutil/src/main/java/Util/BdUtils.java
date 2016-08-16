package Util;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.util.Date;

//import com.baidu.android.common.util.CommonParam;
//import com.baidu.crabsdk.CrabSDK;
//import com.baidupc.bonuspocket.BdApplication;
//import com.baidupc.bonuspocket.R;

import com.example.heyulong.androidutil.GApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

public class BdUtils {

    private static float sDensity = 0f;

    public static float getScreenDensity(Context context) {
        if (sDensity == 0f) {
            DisplayMetrics dm = new DisplayMetrics();
            ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(dm);
            sDensity = dm.density;
        }
        return sDensity;
    }

    public static int dipToPx(Context context, int dip) {
        return (int) (dip * getScreenDensity(context) + 0.5f);
    }
    /*
     * 开启软键盘
     */
    public static void showInputMethod(Context context, View view) {
        if (context == null || view == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 关闭软键盘
     */
    public static boolean hideInputMethod(Context context, View view) {
        if (context == null || view == null) {
            return false;
        }

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive()) {
            return imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        return false;
    }

    /**
     * 是否已联网
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 检查app是否已经安装
     *
     * @param packageName
     * @return
     */
    public static boolean checkAppIsInstalled(Context context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return false;
        }
        PackageManager pm = context.getPackageManager();
        if (pm != null) {
            Intent intent = pm.getLaunchIntentForPackage(packageName);
            if (intent != null) {
                return true;
            }
        }
        return false;
    }

    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }


    public static String removeChinese(String source) {
        String s = source;
        s = s.replaceAll("(\\s[\u4E00-\u9FA5]+)|([\u4E00-\u9FA5]+)", "");
        return s;
    }

    /**
     * 图片圆背景
     *
     * @param bm
     * @return
     */
    public static Bitmap getCircleBitmap(Bitmap bm) {
        int min = Math.min(bm.getWidth(), bm.getHeight());
        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(target);
        canvas.drawCircle(min / 2, min / 2, min / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bm, 0, 0, paint);
        return target;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getSampleBitmap(String aFile, int maxSize) {
        try {

            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inJustDecodeBounds = true;
            FileDescriptor fd = new FileInputStream(aFile).getFD();
            BitmapFactory.decodeFileDescriptor(fd, null, op);

            int originalWidth = op.outWidth;
            int originalHeight = op.outHeight;

            byte limit = 2;

            int scrL = maxSize;

            int imgL = Math.min(originalWidth, originalHeight);
            while ((imgL / limit) > scrL) {
                limit++;
            }

            if (limit > 2) {
                op.inSampleSize = limit;
            } else {
                op.inSampleSize = 1;
            }

            op.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(aFile, op);
        } catch (Exception e) {
            return null;
        }
    }

    public enum GroupType {
        // 社交、照片、生活、视频&音乐、新闻&图书、购物、娱乐、游戏、工具、系统应用、其他
        Social, Photos, Lifestyle, Media, NewsBooks, Shopping, Entertainment, Games, Tools, Other, System
    }

    public static boolean isValidCategory(int category) {
        if (category >= 0 && category < GroupType.values().length) {
            return true;
        }
        return false;
    }

    public static boolean isMoboveeValidCategory(int category) {
        if (isValidCategory(category) && category != GroupType.System.ordinal()) {
            return true;
        }
        return false;
    }



    /**
     * 获取用户标识uid
     */
    private static String sUid;
    private static final String KEY_UID = "uid";

//    public static String getUid() {
//        if (TextUtils.isEmpty(sUid)) {
//            String uid = null;
//            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(GApp.getInstance().getContext());
//            if (sp != null) {
//                uid = sp.getString(KEY_UID, "");
//            }
//
//            if (TextUtils.isEmpty(uid)) {
//                try {
//                    uid = CommonParam.getCUID(GApp.getInstance().getContext());
//
//                    SharedPreferences.Editor editor = sp.edit();
//                    editor.putString(KEY_UID, uid);
//                    editor.apply();
//                } catch (Throwable e) {
//                    e.printStackTrace();
//                }
//            }
//
//            if (!TextUtils.isEmpty(uid)) {
//                try {
//                    sUid = uid.substring(0, uid.indexOf("|"));
//                } catch (Throwable e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//
//        return sUid;
//    }

    /**
     * 从manifest中读取渠道号
     */
    private static final String META_DATA_NAME = "CHANNEL";
    private static final String DEFAULT_CHANNEL = "gp";
    private static final String META_APP_NAME = "AppName";

    public static String readChannel() {
        try {
            ApplicationInfo appInfo =
                    GApp.getInstance().getContext().getPackageManager()
                            .getApplicationInfo(BdSysUtil.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(META_DATA_NAME).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return DEFAULT_CHANNEL;
    }

    public static String readAppName() {
        try {
            ApplicationInfo appInfo =
                    GApp.getInstance().getContext().getPackageManager()
                            .getApplicationInfo(BdSysUtil.getPackageName(), PackageManager.GET_META_DATA);
            return appInfo.metaData.getString(META_APP_NAME).toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return DEFAULT_CHANNEL;
    }

    public static String getGoldMoney(double money){
        String moneyFormat;
        try {
            if (money % 1.0 == 0) {
                moneyFormat = String.valueOf((int) money);
            } else {
                moneyFormat = String.valueOf(money);
                int n = moneyFormat.indexOf('.');
                if (n!=-1) {
                    String sub = moneyFormat.substring(n+1);
                    if( sub.length() >2 ){
                        moneyFormat = String.format("%.2f",money);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            moneyFormat = "";
        }
        return moneyFormat;
    }
}
