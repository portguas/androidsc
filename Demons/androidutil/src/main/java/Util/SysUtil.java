/*
 * Copyright (C) 2016 Baidu, Inc. All Rights Reserved.
 */
package Util;

import java.util.Locale;

import com.example.heyulong.androidutil.GApp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

public class SysUtil {

    private static String sUid;
    private static final String KEY_UID = "uid";
    private static String mPackageName;
    private static String mVersionName;
    private static int mVersionCode;
    private static int mDisplayWidth;
    private static int mDisplayHeight;
    private static float mDensity;

    static {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        mDisplayWidth = dm.widthPixels;
        mDisplayHeight = dm.heightPixels;
        mDensity = dm.density;
    }

//    /**
//     * 获取用户标识uid
//     */
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
//                    uid = CommonParam.getCUID(getContext());
//
//                    Editor editor = sp.edit();
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
     * 获得运营商注册的国家
     *
     * @param aContext
     *
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String getSimCountry(Context aContext) {
        try {
            TelephonyManager tel = (TelephonyManager) aContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (tel != null) {
                return tel.getSimCountryIso().toLowerCase().trim();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取国家代码
     *
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String getCountryCode() {
        String code = getSimCountry(GApp.getInstance().getContext());
        if (TextUtils.isEmpty(code)) {
            code = Locale.getDefault().getCountry().toLowerCase().trim();
        }
        return code;
    }

    /**
     * 获取语言
     *
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String getLanguage() {
        return Locale.getDefault().getLanguage().toLowerCase().trim();
    }

    private static Context getContext() {
        return GApp.getInstance().getContext();
    }

    public static String getPackageName() {
        if (TextUtils.isEmpty(mPackageName)) {
            return getContext().getPackageName();
        }
        return mPackageName;
    }

    public static String getVersionName() {
        if (TextUtils.isEmpty(mVersionName)) {
            String version = "1.0";
            try {
                version = getContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            } catch (Exception e) {
                e.printStackTrace();
            }
            mVersionName = version;
        }
        return mVersionName;
    }

    public static int getVersionCode() {
        try {
            mVersionCode = getContext().getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mVersionCode;
    }

    /**
     * 获取手机系统的版本的级别，比如a_19
     */
    public static String getSystemLevel() {
        return "a_" + Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机的屏幕宽度
     */
    public static int getDisplayWidth() {
        return mDisplayWidth;
    }

    /**
     * 获取手机的屏幕高度
     */
    public static int getDisplayHeight() {
        return mDisplayHeight;
    }

    public static float getDisplayDensity() {
        return mDensity;
    }

}
