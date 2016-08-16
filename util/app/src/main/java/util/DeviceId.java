package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import android.content.Context;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import log.Log;
import security.AesUtil;
import security.Base64Util;
import security.MD5Util;

/**
 * Created by zzz on 8/16/2016.
 */

public class DeviceId {

    private static final String TAG = "DeviceId";
    private static final boolean DEBUG = false;
    private static final String KEY_DEVICE_ID = "com.baidu.deviceid";
    private static final String AES_KEY = "30212102dicudiab";
    private static final String OLD_EXT_DIR = "baidu";
    private static final String EXT_DIR = "backups/.SystemConfig";
    private static final String EXT_FILE = ".cuid";

    private DeviceId() {
    }

    public static String getDeviceID(Context var0) {
        checkPermission(var0, "android.permission.WRITE_SETTINGS");
        checkPermission(var0, "android.permission.READ_PHONE_STATE");
        checkPermission(var0, "android.permission.WRITE_EXTERNAL_STORAGE");
        DeviceId.IMEIInfo var1 = DeviceId.IMEIInfo.getIMEIInfo(var0);
        String var2 = var1.IMEI;
        boolean var3 = !var1.CAN_READ_AND_WRITE_SYSTEM_SETTINGS;
        String var4 = getAndroidId(var0);
        String var5 = "";
        if(var3) {
            return MD5Util.toMd5(("com.baidu" + var4).getBytes(), true);
        } else {
            String var6 = null;
            var5 = Settings.System.getString(var0.getContentResolver(), "com.baidu.deviceid");
            if(!TextUtils.isEmpty(var5)) {
                if(!isExternalFileExists()) {
                    setExternalDeviceId(var2, var5);
                }
            } else {
                var6 = MD5Util.toMd5(("com.baidu" + var2 + var4).getBytes(), true);
                var5 = Settings.System.getString(var0.getContentResolver(), var6);
                if(!TextUtils.isEmpty(var5)) {
                    Settings.System.putString(var0.getContentResolver(), "com.baidu.deviceid", var5);
                    setExternalDeviceId(var2, var5);
                }
            }

            if(TextUtils.isEmpty(var5)) {
                var5 = getExternalDeviceId(var2);
                if(!TextUtils.isEmpty(var5)) {
                    Settings.System.putString(var0.getContentResolver(), var6, var5);
                    Settings.System.putString(var0.getContentResolver(), "com.baidu.deviceid", var5);
                }
            }

            if(TextUtils.isEmpty(var5)) {
                String var7 = UUID.randomUUID().toString();
                var5 = MD5Util.toMd5((var2 + var4 + var7).getBytes(), true);
                Settings.System.putString(var0.getContentResolver(), var6, var5);
                Settings.System.putString(var0.getContentResolver(), "com.baidu.deviceid", var5);
                setExternalDeviceId(var2, var5);
            }

            return var5;
        }
    }

    public static String getIMEI(Context var0) {
        DeviceId.IMEIInfo var1 = DeviceId.IMEIInfo.getIMEIInfo(var0);
        return var1.IMEI;
    }

    public static String getAndroidId(Context var0) {
        String var2 = "";
        var2 = Settings.Secure.getString(var0.getContentResolver(), "android_id");
        if(TextUtils.isEmpty(var2)) {
            var2 = "";
        }

        return var2;
    }

    private static void checkPermission(Context var0, String var1) {
        int var2 = var0.checkCallingOrSelfPermission(var1);
        boolean var3 = var2 == 0;
        if(!var3) {
            throw new SecurityException("Permission Denial: requires permission " + var1);
        }
    }

    private static boolean isExternalFileExists() {
        File var0 = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid");
        return var0.exists();
    }

    private static String getExternalDeviceId(String var0) {
        if(TextUtils.isEmpty(var0)) {
            return "";
        } else {
            boolean var1 = false;
            String var2 = "";
            File var3 = new File(Environment.getExternalStorageDirectory(), "baidu/.cuid");
            if(!var3.exists()) {
                var3 = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid");
                var1 = true;
            }

            try {
                FileReader var4 = new FileReader(var3);
                BufferedReader var5 = new BufferedReader(var4);
                StringBuilder var6 = new StringBuilder();
                String var7 = null;

                while((var7 = var5.readLine()) != null) {
                    var6.append(var7);
                    var6.append("\r\n");
                }

                var5.close();
                String var8 = new String(AesUtil.decrypt("30212102dicudiab", "30212102dicudiab",
                        Base64Util.decode(var6.toString().getBytes())));
                String[] var9 = var8.split("=");
                if(var9 != null && var9.length == 2 && var0.equals(var9[0])) {
                    var2 = var9[1];
                }

                if(!var1) {
                    setExternalDeviceId(var0, var2);
                }
            } catch (FileNotFoundException var10) {
                ;
            } catch (IOException var11) {
                ;
            } catch (Exception var12) {
                ;
            }

            return var2;
        }
    }

    private static void setExternalDeviceId(String var0, String var1) {
        if(!TextUtils.isEmpty(var0)) {
            StringBuilder var2 = new StringBuilder();
            var2.append(var0);
            var2.append("=");
            var2.append(var1);
            File var3 = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig");
            File var4 = new File(var3, ".cuid");

            try {
                if(var3.exists() && !var3.isDirectory()) {
                    Random var5 = new Random();
                    File var6 = null;
                    File var7 = var3.getParentFile();
                    String var8 = var3.getName();

                    do {
                        var6 = new File(var7, var8 + var5.nextInt() + ".tmp");
                    } while(var6.exists());

                    var3.renameTo(var6);
                    var6.delete();
                }

                var3.mkdirs();
                FileWriter var11 = new FileWriter(var4, false);
                String var12 = Base64Util.encode(AesUtil.encrypt("30212102dicudiab", "30212102dicudiab", var2.toString()
                                                                                                              .getBytes()), "utf-8");
                var11.write(var12);
                var11.flush();
                var11.close();
            } catch (IOException var9) {
                ;
            } catch (Exception var10) {
                ;
            }

        }
    }

    static final class IMEIInfo {
        private static final String KEY_IMEI = "bd_setting_i";
        public final String IMEI;
        public final boolean CAN_READ_AND_WRITE_SYSTEM_SETTINGS;
        public static final String DEFAULT_TM_DEVICEID = "";

        private IMEIInfo(String var1, boolean var2) {
            this.IMEI = var1;
            this.CAN_READ_AND_WRITE_SYSTEM_SETTINGS = var2;
        }

        private static String getIMEI(Context var0, String var1) {
            String var2 = null;

            try {
                TelephonyManager var3 = (TelephonyManager)var0.getSystemService(Context.TELEPHONY_SERVICE);
                if(var3 != null) {
                    var2 = var3.getDeviceId();
                }
            } catch (Exception var4) {
                Log.e("DeviceId", "Read IMEI failed", var4);
            }

            var2 = imeiCheck(var2);
            if(TextUtils.isEmpty(var2)) {
                var2 = var1;
            }

            return var2;
        }

        static DeviceId.IMEIInfo getIMEIInfo(Context var0) {
            boolean var1 = false;
            String var2 = "";

            try {
                var2 = Settings.System.getString(var0.getContentResolver(), "bd_setting_i");
                if(TextUtils.isEmpty(var2)) {
                    var2 = getIMEI(var0, "");
                }

                Settings.System.putString(var0.getContentResolver(), "bd_setting_i", var2);
            } catch (Exception var4) {
                Log.e("DeviceId", "Settings.System.getString or putString failed", var4);
                var1 = true;
                if(TextUtils.isEmpty(var2)) {
                    var2 = getIMEI(var0, "");
                }
            }

            return new DeviceId.IMEIInfo(var2, !var1);
        }

        private static String imeiCheck(String var0) {
            return null != var0 && var0.contains(":")?"":var0;
        }
    }

}
