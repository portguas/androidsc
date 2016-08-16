package Util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

//import com.baidu.android.common.util.CommonParam;
//import com.baidupc.bonuspocket.BdApplication;
//import com.baidupc.bonuspocket.R;
//import com.baidupc.bonuspocket.prefs.PrefsTypes;
import com.example.heyulong.androidutil.GApp;
import com.example.heyulong.androidutil.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;

/**
 * 获取系统参数特性等工具
 */
public class BdSysUtil {

    private static String sUid;
    private static String mPackageName;
    private static String mVersionName;
    private static int mVersionCode;
    private static String mResolution;
    private static int mDisplayWidth;
    private static int mDisplayHeight;
    private static int mDisplayDensityDpi;

    static {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        mDisplayWidth = dm.widthPixels;
        mDisplayHeight = dm.heightPixels;
        mDisplayDensityDpi = dm.densityDpi;
    }

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth() {
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        int screenWidth;
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            screenWidth = Math.max(dm.widthPixels, dm.heightPixels);
        } else {
            screenWidth = Math.min(dm.widthPixels, dm.heightPixels);
        }
        return screenWidth;
    }

    /**
     * 通过获取手机运营商网络所在国家，为空则再从Locale获取国家，返回有可能为空
     *
     * @param context
     */
    public static String getCountryISOByNetworkOrLocale(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String countryISO = telephonyManager.getNetworkCountryIso().toLowerCase(Locale.US);

        if (!TextUtils.isEmpty(countryISO)) {
            return countryISO;
        }

//        countryISO = telephonyManager.getSimCountryIso().toLowerCase().trim();
//        if (!TextUtils.isEmpty(countryISO)) {
//            return countryISO;
//        }

        Resources resources = context.getResources();
        Locale locale = resources.getConfiguration().locale;
        countryISO = locale.getCountry().toLowerCase(Locale.US);

        return countryISO;
    }

    /**
     * 获得运营商注册的国家
     *
     * @param aContext
     * @return
     */
    @SuppressLint("DefaultLocale")
    public static String getSimCountry(Context aContext) {
        try {
            TelephonyManager tel = (TelephonyManager) aContext.getSystemService(Context.TELEPHONY_SERVICE);
            if (tel != null) {
                // http://www.iso.org/iso/country_codes/iso_3166_code_lists/country_names_and_code_elements
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

    /**
     * 判断是否MIUI系统
     *
     * @return
     */
    public static boolean isMiuiRom() {
        if (sMiuiVerion == null) {
            sMiuiVerion = getMiuiVersion();
        }
        if (TextUtils.isEmpty(sMiuiVerion)) {
            return false;
        } else {
            return true;
        }
    }

    private static String sMiuiVerion = null;

    private static String getMiuiVersion() {
        String line = null;
        BufferedReader reader = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop ro.miui.ui.version.name");
            reader = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = reader.readLine();
            return line;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return "";
    }


    private static Context getContext() {
        return GApp.getInstance().getContext();
    }

    /**
     * 获取app的包名 ：com.baidu.launcher.i18n
     */
    public static String getPackageName() {
        if (TextUtils.isEmpty(mPackageName)) {
            return getContext().getPackageName();
        }
        return mPackageName;
    }

    /**
     * 获取app的版本名
     */
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


    /**
     * 获取底部虚拟导航键高度 . 注意: 能取到大于0的值并不代表该手机有虚拟导航键
     *
     * @return
     */
    public static int getNavigationBarHeight() {
        Context context = GApp.getInstance().getContext();
        if (context == null) {
            return 0;
        }
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        int result = 0;
        Resources res = GApp.getInstance().getContext().getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        if (result <= 0) {
            result = BdResUtil.getDimen(R.dimen.status_bar_height_default);
        }
        return result;
    }

    public static boolean isNetworkAvailable(Context context) {
        try {
            ConnectivityManager connectivity =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity != null) {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getVersionCode() {
        PackageManager pm = getContext().getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(getContext().getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
//            L.printStackTrace(e);
        }
        return 0;
    }

//    public static String getCUID() {
//        String value = "";
//        try {
//            value = PreferencesUtil.getString(PrefsTypes.PREFS_KEY_CUID, "");
//            if (TextUtils.isEmpty(value)) {
//                value = CommonParam.getCUID(getContext());
//                PreferencesUtil.putString(PrefsTypes.PREFS_KEY_CUID, value);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (TextUtils.isEmpty(value)) {
//            value = "";
//        }
//        return value;
//    }
}
