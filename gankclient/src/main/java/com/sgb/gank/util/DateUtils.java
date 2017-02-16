package com.sgb.gank.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by panda on 16/9/7 下午3:44.
 */
public class DateUtils {

    private static final String FORMAT_STANDARD_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    private static final String FORMAT_DATE_TIME_WITHOUT_SECOND = "yyyy-MM-dd HH:mm";
    private static final String FORMAT_ONLY_DATE = "yyyy-MM-dd";
    private static final String FORMAT_ONLY_TIME = "HH:mm:ss";
    private static final String FORMAT_WEEK = "EEEE";
    private static final String FORMAT_SHOUT_WEEK = "EE";

    public static Date format(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STANDARD_DATE_TIME, Locale.getDefault());
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            return new Date();
        }
    }

    public static String getDateTime(String dateTimeStr) {
        Date date = format(dateTimeStr);
        return getDateTime(date);
    }

    public static String getDateTime(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_STANDARD_DATE_TIME, Locale.getDefault());
        return sdf.format(date);
    }
}
