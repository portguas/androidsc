package util;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by zzz on 8/16/2016.
 */

public class ComParam {

    private static final boolean DEBUG = false;
    private static final String TAG = ComParam.class.getSimpleName();

    public ComParam() {
    }

    public static String getCUID(Context var0) {
        String var1 = getDeviceId(var0);
        String var2 = DeviceId.getIMEI(var0);
        if(TextUtils.isEmpty(var2)) {
            var2 = "0";
        }

        StringBuffer var3 = new StringBuffer(var2);
        var2 = var3.reverse().toString();
        String var4 = var1 + "|" + var2;
        return var4;
    }

    private static String getDeviceId(Context var0) {
        return DeviceId.getDeviceID(var0);
    }

}
