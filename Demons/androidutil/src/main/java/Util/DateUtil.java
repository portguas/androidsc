package Util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间操作类
 * 
 * @author Neusoft.E3
 * 
 */
@SuppressWarnings("unused")
public class DateUtil {
    public static final String FORMAT_TODAY = "今天 HH:mm";
    public static final String FORMAT_YESTERDAY = "昨天 HH:mm";
    public static final String FORMAT_THIS_YEAR = "M 月 d 日";
    public static final String FORMAT_OTHER_YEAR = "yyyy-M-d";
    public static final String FORMAT_YEAR_MONTH = "yyyy 年 M 月";
    public static final String FORMAT_FULL = "yyyy-MM-dd HH:mm";
    public static final String FORMAT_HISTORY = "M/d yyyy";
    public static final String FORMAT_AMPM = "HH:mm";
    public static final String FORMAT_WEATHER_UPDATE = "M-d HH:mm";

    private static final int DAY_MILLISECONDS = 86400000;
    private static final int HOUR_MILLISECONDS = 3600000;
    private static final int MINUTE_MILLISECONDS = 60000;
    private static final int SECOND_MILLISECONDS = 1000;
    private static final int YEAR_BASE = 1900;
    private static final String TAG = "DateUtils";

    /**
     * 返回历史记录组今天日期 (12/18 2012)
     */
    public static String getHistoryGroupToday() {
        return " (" + formatTime(getNowTime(), FORMAT_HISTORY) + ")";
    }

    /**
     * 返回历史记录组昨天日期 (12/17 2012)
     */
    public static String getHistoryGroupYestoday() {
        return " (" + formatTime((getNowTime() - DAY_MILLISECONDS), FORMAT_HISTORY) + ")";
    }

    /**
     * 返回 当天零点的时间long表示
     */
    public static long getTodayZero() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        return c.getTimeInMillis();
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     * 
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 给定一个日期long，判断该日期是否是 今天/昨天/3-7天前/7-30天前/更早
     * 
     * @param milliseconds
     * @return 0 - 今天 1 - 昨天 2 - 3-7天 3 - 更早
     */
    public static int getHistoryDateGroup(long milliseconds) {
        int result = 0;
        long todayZeroMilliSeconds = getTodayZero();
        double temp = div((milliseconds - todayZeroMilliSeconds), DAY_MILLISECONDS, 2);

        // LogUtil.w("qt", "getHistoryDateGroup..temp = " + temp
        // + ",(milliseconds - todayZeroMilliSeconds) = " + (milliseconds -
        // todayZeroMilliSeconds)) ;

        if (temp >= 1) {
            // 该情况一般为用户把时间调前了所致，暂时归为今天
            result = 0;
        } else if (temp >= 0) {
            // 今天
            result = 0;
        } else if (temp >= -1) {
            // 昨天
            result = 1;
        } else if (temp >= -7) {
            // 3-7天前
            result = 2;
        } else {
            // 更早
            result = 3;
        }

        return result;
    }

    /**
     * 使用程序内标准方式格式化时间
     * 
     * @param aSeconds
     * @return formatted string
     */
    public static String formatTime(long aSeconds) {
        return formatTime(aSeconds, FORMAT_FULL);
    }

    /**
     * 使用给定的formatter格式化时间
     *
     * @return
     */
    public static String formatTime(long milSeconds, String aFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(aFormat);
        Date date = new Date();
        date.setTime(milSeconds);
        String formatDate = sdf.format(date);

        return formatDate;
    }

    /**
     * 获取年数字
     * 
     * @param aSeconds
     * @return
     */
    public static int getYear(long aSeconds) {
        Date date = new Date();
        date.setTime(aSeconds * SECOND_MILLISECONDS);

        int year = date.getYear() + YEAR_BASE;

        return year;
    }

    /**
     * 获取月数字
     * 
     * @param aSeconds
     * @return
     */
    public static int getMonth(long aSeconds) {
        Date date = new Date();
        date.setTime(aSeconds * SECOND_MILLISECONDS);

        int month = date.getMonth() + 1;

        return month;
    }

    /**
     * 获取日数字
     * 
     * @param aSeconds
     * @return
     */
    public static int getDate(long aSeconds) {
        Date date = new Date();
        date.setTime(aSeconds * SECOND_MILLISECONDS);

        return date.getDate();
    }

    /**
     * 获取小时数字
     * 
     * @param aSeconds
     * @return
     */
    public static int getHour(long aSeconds) {
        Date date = new Date();
        date.setTime(aSeconds * SECOND_MILLISECONDS);

        return date.getHours();
    }

    /**
     * 获取分钟数字
     * 
     * @param aSeconds
     * @return
     */
    public static int getMinute(long aSeconds) {
        Date date = new Date();
        date.setTime(aSeconds * SECOND_MILLISECONDS);

        return date.getMinutes();
    }

    /**
     * 获取秒数字
     * 
     * @param aSeconds
     * @return
     */
    public static int getSecond(long aSeconds) {
        Date date = new Date();
        date.setTime(aSeconds * SECOND_MILLISECONDS);

        return date.getSeconds();
    }

    public static String getFormattedDateString(long seconds) {
        return getFormattedDateString(seconds, true);
    }

    /**
     * 格式化时间
     * 
     * @param seconds
     * @param ignoreJudgeTimeZone
     * @return
     */
    public static String getFormattedDateString(long seconds, boolean ignoreJudgeTimeZone) {
        String formatter = "";

        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(seconds * SECOND_MILLISECONDS);
        if (!ignoreJudgeTimeZone) {
            date.add(Calendar.HOUR_OF_DAY, 8);
        }

        Calendar today = Calendar.getInstance();
        today.clear(Calendar.HOUR_OF_DAY);
        today.clear(Calendar.MINUTE);
        today.clear(Calendar.SECOND);

        Calendar yesterday = Calendar.getInstance();
        yesterday.clear(Calendar.HOUR_OF_DAY);
        yesterday.clear(Calendar.MINUTE);
        yesterday.clear(Calendar.SECOND);
        yesterday.roll(Calendar.DATE, false);
        // WebConfig.d(TAG, "seconds : " + seconds);
        // WebConfig.d(TAG, "today : " + today.getTime().toLocaleString());
        // WebConfig.d(TAG, "yesterday : " +
        // yesterday.getTime().toLocaleString());
        // WebConfig.d(TAG, "date : " + date.getTime().toLocaleString());

        if (date.get(Calendar.YEAR) != today.get(Calendar.YEAR)) {
            formatter = FORMAT_OTHER_YEAR;
        } else {
            if (date.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                    && date.get(Calendar.DATE) == today.get(Calendar.DATE)) {
                formatter = FORMAT_TODAY;
            } else {
                if (date.get(Calendar.MONTH) == today.get(Calendar.MONTH)
                        && date.get(Calendar.DATE) == today.get(Calendar.DATE) - 1) {
                    formatter = FORMAT_YESTERDAY;
                } else {
                    formatter = FORMAT_THIS_YEAR;
                }

            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat(formatter);
        return sdf.format(date.getTime());
    }

    /**
     * 获取当前时间
     * 
     * @return
     */
    public static long getNowTime() {
        return new Date().getTime();
    }

    /**
     * 获取当前时间
     * 
     * @return
     */
    public static String getNowTime(String format) {
        return formatTime(new Date().getTime(), format);
    }

    /**
     * 获取当前epoch时间
     * 
     * @return
     */
    public static long getNowTicks() {
        return getNowTime() / SECOND_MILLISECONDS;
    }

    /**
     * 两个日期间隔（ms）
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getDateDiff(long startTime, long endTime) {
        return endTime - startTime;
    }

    /**
     * 两个日期间隔（day）
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getDateDiffDays(long startTime, long endTime) {
        return (endTime - startTime) / 86400000;
    }

    /**
     * String (yyyy-MM-dd HH:mm:ss) to Date(Long)
     * 
     * @param dateString
     * @return
     */
    public static long parse(String dateString) {
        return parse(dateString, "yyyy-MM-dd HH:mm:ss");
    }

    public static long parse(String dateString, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date(2013, 1, 1).getTime();
        }
    }

    public static String getNowTimeWithLocale(String localeString) {
        return getFormattedDateStringWithLocale(new Date().getTime(), getLongLocaleFormat(localeString), new Locale(
                localeString));
    }

    public static String getNowTimeWithLocaleShort(String localeString) {
        return getFormattedDateStringWithLocale(new Date().getTime(), getShortLocaleFormat(localeString), new Locale(
                localeString));
    }

    public static String getFormattedDateStringWithLocale(long seconds, String localeString) {
        return getFormattedDateStringWithLocale(seconds, getLongLocaleFormat(localeString), new Locale(localeString));
    }

    public static String getFormattedDateStringWithLocale(long seconds, String format, Locale locale) {
        SimpleDateFormat sdf = new SimpleDateFormat(format, locale);
        return sdf.format(seconds);
    }

    public static String getLongLocaleFormat(String localeString) {
        String formatString = "dd MMM EE";
        if (localeString == "ja") {
            formatString = "MMMMdd日 EEEE";
        } else if (localeString == "id") {
            formatString = "EE dd MMM";
        }
        return formatString;
    }

    public static String getShortLocaleFormat(String localeString) {
        String formatString = "dd MMM";
        if (localeString == "ja") {
            formatString = "MMMMdd日";
        }
        return formatString;
    }

    /**
     * 是否同一天
     * 
     * @param milSeconds1
     * @param milSeconds2
     * @return
     */
    public static boolean isTheSameDay(long milSeconds1, long milSeconds2) {
        String day1 = formatTime(milSeconds1, "yyyy-M-d");
        String day2 = formatTime(milSeconds2, "yyyy-M-d");
        return day1.equals(day2);
    }

    public static int differentDays(Date date1,Date date2)
    {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1= cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if(year1 != year2)   //同一年
        {
            int timeDistance = 0 ;
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0)    //闰年
                {
                    timeDistance += 366;
                }
                else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2-day1) ;
        }
        else    //不同年
        {
            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2-day1;
        }
    }

}
