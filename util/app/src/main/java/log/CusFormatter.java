package log;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Created by zzz on 8/16/2016.
 */

public class CusFormatter extends Formatter {

    Date dat = new Date();
    private static String format = "{0,date} {0,time}";
    private MessageFormat formatter;
    private Object[] args = new Object[1];

    CusFormatter() {
    }

    public synchronized String format(LogRecord var1) {
        String var2 = null;
        String var3 = null;
        int var4 = 0;
        boolean var5 = false;
        StackTraceElement[] var6 = (new Throwable()).getStackTrace();
        int var7 = var6.length;

        for(int var8 = 0; var8 < var7; ++var8) {
            StackTraceElement var9 = var6[var8];
            String var10 = var9.getClassName();
            if(var10.startsWith(Log.class.getName())) {
                var5 = true;
            } else if(var5) {
                var2 = var9.getClassName();
                var3 = var9.getMethodName();
                var4 = var9.getLineNumber();
                break;
            }
        }

        var1.setSourceClassName(var2);
        var1.setSourceMethodName(var3);
        StringBuffer var12 = new StringBuffer();
        this.dat.setTime(var1.getMillis());
        this.args[0] = this.dat;
        StringBuffer var13 = new StringBuffer();
        if(this.formatter == null) {
            this.formatter = new MessageFormat(format);
        }

        this.formatter.format(this.args, var13, (FieldPosition)null);
        var12.append(var13);
        var12.append("." + var1.getMillis() % 1000L);
        var12.append(" ");
        if(var1.getSourceClassName() != null) {
            var12.append(var1.getSourceClassName());
        } else {
            var12.append(var1.getLoggerName());
        }

        if(var1.getSourceMethodName() != null) {
            var12.append(" ");
            var12.append(var1.getSourceMethodName());
        }

        var12.append(" ");
        var12.append(var4);
        var12.append(" ");
        String var14 = this.formatMessage(var1);
        var12.append(var1.getLevel().getLocalizedName());
        var12.append(": ");
        var12.append(var14);
        var12.append("\n");
        if(var1.getThrown() != null) {
            try {
                StringWriter var15 = new StringWriter();
                PrintWriter var16 = new PrintWriter(var15);
                var1.getThrown().printStackTrace(var16);
                var16.close();
                var12.append(var15.toString());
            } catch (Exception var11) {
                var11.printStackTrace();
            }
        }

        return var12.toString();
    }
}
