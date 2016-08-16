package log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;

/**
 * Created by zzz on 8/16/2016.
 */

public final class Log {
    private static boolean sLogEnabled = true;
    private static boolean sLog2File = false;
    private static Logger sFilelogger;
    public static final int FILE_LIMETE = 10485760;
    public static final int FILE_NUMBER = 2;

    private Log() {
    }

    public static void v(String var0, String var1) {
        if(sLogEnabled) {
            if(sLog2File && sFilelogger != null) {
                sFilelogger.log(Level.INFO, var0 + ": " + var1);
            } else {
                android.util.Log.v(var0, var1);
            }
        }

    }

    public static void v(String var0, String var1, Throwable var2) {
        v(var0, var1 + '\n' + getStackTraceString(var2));
    }

    public static void i(String var0, String var1) {
        if(sLogEnabled) {
            if(sLog2File && sFilelogger != null) {
                sFilelogger.log(Level.INFO, var0 + ": " + var1);
            } else {
                android.util.Log.i(var0, var1);
            }
        }

    }

    public static void i(String var0, String var1, Throwable var2) {
        i(var0, var1 + '\n' + getStackTraceString(var2));
    }

    public static void d(String var0, String var1) {
        if(sLogEnabled) {
            if(sLog2File && sFilelogger != null) {
                sFilelogger.log(Level.INFO, var0 + ": " + var1);
            } else {
                android.util.Log.d(var0, var1);
            }
        }

    }

    public static void d(String var0, String var1, Throwable var2) {
        d(var0, var1 + '\n' + getStackTraceString(var2));
    }

    public static void w(String var0, String var1) {
        if(sLogEnabled) {
            if(sLog2File && sFilelogger != null) {
                sFilelogger.log(Level.WARNING, var0 + ": " + var1);
            } else {
                android.util.Log.w(var0, var1);
            }
        }

    }

    public static void w(String var0, String var1, Throwable var2) {
        w(var0, var1 + '\n' + getStackTraceString(var2));
    }

    public static void e(String var0, String var1) {
        if(sLogEnabled) {
            if(sLog2File && sFilelogger != null) {
                sFilelogger.log(Level.SEVERE, var0 + ": " + var1);
            } else {
                android.util.Log.e(var0, var1);
            }
        }

    }

    public static void e(String var0, Throwable var1) {
        String var2 = getStackTraceString(var1);
        e(var0, var2);
    }

    public static void e(String var0, String var1, Throwable var2) {
        var1 = var1 + '\n' + getStackTraceString(var2);
        e(var0, var1);
    }

    public static String getStackTraceString(Throwable var0) {
        if(var0 == null) {
            return "";
        } else {
            StringWriter var1 = new StringWriter();
            PrintWriter var2 = new PrintWriter(var1);
            var0.printStackTrace(var2);
            return var1.toString();
        }
    }

    private static String getLogFileName() {
        int var0 = Process.myPid();
        String var1 = getProcessNameForPid(var0);
        if(TextUtils.isEmpty(var1)) {
            var1 = "FileLog";
        }

        var1 = var1.replace(':', '_');
        return var1;
    }

    private static String getProcessNameForPid(int var0) {
        String var1 = "/proc/" + var0 + "/cmdline";
        String var2 = "/proc/" + var0 + "/status";
        String var3 = "";

        try {
            File var4 = new File(var1);
            BufferedReader var5 = new BufferedReader(new FileReader(var4));
            String var6 = null;
            var6 = var5.readLine();
            int var7;
            if(!TextUtils.isEmpty(var6)) {
                var7 = var6.indexOf(0);
                var3 = var6.substring(0, var7);
            } else {
                var4 = new File(var2);
                var5 = new BufferedReader(new FileReader(var4));

                for(var6 = var5.readLine(); var6 != null; var6 = var5.readLine()) {
                    if(var6.startsWith("Name:")) {
                        var7 = var6.indexOf("\t");
                        if(var7 >= 0) {
                            var3 = var6.substring(var7 + 1);
                        }
                        break;
                    }
                }
            }

            var5.close();
        } catch (Exception var8) {
            var8.printStackTrace();
        }

        return var3;
    }

    public static void setLogEnabled(boolean var0) {
        sLogEnabled = var0;
    }

    /**
     * save the log to local file
     * @param var0
     */
    public static void setLog2File(boolean var0) {
        sLog2File = var0;
        if(sLog2File && sFilelogger == null) {
            String var1 = getLogFileName();
            String var2 = (new File(Environment.getExternalStorageDirectory(), var1)).getAbsolutePath();

            try {
                FileHandler var3 = new FileHandler(var2 + "_%g.log", FILE_LIMETE, FILE_NUMBER, true);
                var3.setFormatter(new CusFormatter());
                sFilelogger = Logger.getLogger(var1);
                sFilelogger.setLevel(Level.ALL);
                sFilelogger.addHandler(var3);
            } catch (SecurityException var5) {
                var5.printStackTrace();
            } catch (IOException var6) {
                var6.printStackTrace();
            }
        }

    }
}
