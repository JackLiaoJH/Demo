package com.liaojh.goldensunday;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * @author JackLiao
 * @ClassName: TimeUtils
 * @Description: TODO 时间工具类
 * @date 2015年7月28日 下午5:22:42
 */
public class TimeUtils
{
    /**
     * 格式化时间为：yyyy-MM-dd HH:mm:ss
     */
    public static final SimpleDateFormat DEFAULT_DATE_FORMAT          = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    /**
     * 格式化时间为：yyyy-MM-dd
     */
    public static final SimpleDateFormat DATE_FORMAT_DATE             = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.getDefault());
    /**
     * 格式化时间为：HH:mm:ss
     */
    public static final SimpleDateFormat DATE_FORMAT_TIME             = new SimpleDateFormat(
            "HH:mm:ss", Locale.getDefault());
    /**
     * 格式化时间为：HH:mm
     */
    public static final SimpleDateFormat DATE_FORMAT_TIME_HOUR_MIMUTE = new SimpleDateFormat(
            "HH:mm", Locale.getDefault());
    /**
     * 一天的总毫秒数
     */
    public static final float            MILLISECOND_OF_DAY           = 86400000L;

    private static final int DAY_WEEK_COUNT   = 7;
    private static final int HOUR_DAY_COUNT   = 23;
    private static final int MINUTE_DAY_COUNT = 60;
    private static final int SECOND_DAY_COUNT = 60;

    private TimeUtils()
    {
        throw new AssertionError();
    }

    /**
     * 根据format格式获取当前字符串时间
     */
    public static String getCurrentTime(SimpleDateFormat format)
    {
        return format.format(new Date());
    }

    /**
     * get current time in milliseconds, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @return
     */
    public static String getCurrentTimeInString()
    {
        return getTime(getCurrentTimeInLong());
    }

    /**
     * 根据给定的long时间，格式化成对应格式的字符串
     */
    public static String getLongTime(long milliseconds, SimpleDateFormat format)
    {
        return format.format(new Date(milliseconds));
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static long getCurrentTimeInLong()
    {
        return System.currentTimeMillis();
    }

    /**
     * long time to string, format is {@link #DEFAULT_DATE_FORMAT}
     *
     * @param timeInMillis
     * @return
     */
    public static String getTime(long timeInMillis)
    {
        return getLongTime(timeInMillis, DEFAULT_DATE_FORMAT);
    }

    /**
     * get current time in milliseconds
     *
     * @return
     */
    public static String getCurrentTimeInString(SimpleDateFormat dateFormat)
    {
        return getLongTime(getCurrentTimeInLong(), dateFormat);
    }

    /***
     * 获取距离今天delayTime天的时间
     *
     * @param delayTime 距离今天的天数，如：昨天为1
     * @return 返回格式化后的时间
     */
    public static String getTimeDelay(int delayTime, SimpleDateFormat format)
    {
        return getLongTime((long) (getCurrentTimeInLong() - delayTime * MILLISECOND_OF_DAY),
                format);
    }

    /***
     * 根据给给定的date判断是否是今天，date的格式为:2015-07-28
     */
    public static boolean isToday(String date)
    {
        if (!TextUtils.isEmpty(date))
        {
            return regexStr(date, "\\d{4}-\\d{2}-\\d{2}") ? TextUtils.equals(
                    getCurrentTime(DATE_FORMAT_DATE), date) : false;
        }
        return false;
    }

    public static boolean regexStr(String descStr, String patternStr)
    {
        return Pattern.compile(patternStr).matcher(descStr).find();
    }

    /**
     * <p>
     * 如果距离指定日超过一天的，返回距离指定日天数 (dd)
     * </p>
     * <p>
     * 如果当前是指定日或者距离指定日不超过一天的，返回距离当日结束还剩下多久 (HH:MM:SS)
     * </p>
     *
     * @param whenDay      指定日： <code>Calendar.SUNDAY</code> <code>Calendar.MONDAY</code>
     *                     <code>Calendar.TUESDAY</code> <code>Calendar.WEDNESDAY</code>
     *                     <code>Calendar.THURSDAY</code> <code>Calendar.FRIDAY</code>
     *                     <code>Calendar.SATURDAY</code>
     * @param isContainDay 是否包括今天在内，true：包括，false：不包括
     * @return
     */
    public static String disDay(int whenDay, boolean isContainDay)
    {
        String        timeFormat = "%02d";
        StringBuilder time       = new StringBuilder();
        Calendar      calendar   = Calendar.getInstance(Locale.getDefault());
        int           dayOfWeek  = calendar.get(Calendar.DAY_OF_WEEK);
//        if (whenDay - dayOfWeek == 1)
//        { // 明天
//            // 当前距离周日还有多久
//            int hour = calendar.get(Calendar.HOUR_OF_DAY);
//            int minute = calendar.get(Calendar.MINUTE);
//            int second = calendar.get(Calendar.SECOND);
//            time.append(String.format(timeFormat, hour)).append(":")
//                .append(String.format(timeFormat, minute))
//                .append(":").append(String.format(timeFormat, second));
//        }
//        else
//
        if (whenDay == dayOfWeek || whenDay - dayOfWeek == 1)
        { // 当天
            // 当前周日还剩下多久时间
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            time.append(String.format(timeFormat, HOUR_DAY_COUNT - hour)).append(":")
                .append(String.format(timeFormat, MINUTE_DAY_COUNT - minute)).append(":")
                .append(String.format(timeFormat, SECOND_DAY_COUNT - second));
        }
        else
        {
            // 距离周七超过1天
            int disSunday = Math
                    .abs(whenDay + (isContainDay ? (DAY_WEEK_COUNT - dayOfWeek) : (DAY_WEEK_COUNT - dayOfWeek - 1)));
            time.append(disSunday);
        }
        return time.toString();
    }

}