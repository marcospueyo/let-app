package com.mph.letapp.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {
    private static final String TAG = DateUtils.class.getSimpleName();

    public final static long SECONDS_HOUR = 3600;

    public final static long MILLISECONDS_HOUR = SECONDS_HOUR * 1000;

    public final static long MILLISECONDS_DAY = MILLISECONDS_HOUR * 24;

    public final static long MILLISECONDS_WEEK = MILLISECONDS_DAY * 7;

    public final static String FULL_DATE_FORMAT = "dd/MM/yyyy HH:mm";

    public final static String DATE_FORMAT = "dd/MM/yyyy";

    public final static String DATE_TEXT_FORMAT = "dd LLLL yyyy";

    public final static String TIME_FORMAT = "HH:mm:ss";

    public final static String SHORT_TIME_FORMAT = "HH:mm";

    public static final String LAST_MSG_DATE_FORMAT = "EE";

    private static String getFormat(String format) {
        if (format == null || format.equals("")) return FULL_DATE_FORMAT;
        return format;
    }

    public static String calendarToString(Calendar calendari, String format) {
        return dateToString(calendari.getTime(), format);
    }

    public static String dateToString(Date date, String format) {
        return dateToString(date, format, Locale.getDefault());
    }

    public static String dateToString(Date date, String format, Locale locale) {
        format = getFormat(format);
        SimpleDateFormat df = new SimpleDateFormat(format, locale);
        try {
            String formattedDate = df.format(date.getTime());
            return formattedDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date stringToDate(String date) {
        return stringToDate(date, null);
    }

    public static Date stringToDate(String date, String format) {
        return stringToDate(date, format, null);
    }

    public static Date stringToDate(String date, String format, TimeZone timeZone) {
        format = getFormat(format);
        SimpleDateFormat form = new SimpleDateFormat(format, Locale.getDefault());
        if (timeZone != null) {
            form.setTimeZone(timeZone);
        }
        Date d;
        try {
            d = form.parse(date);
        } catch (java.text.ParseException e) {
            d = null;
        }
        return d;
    }

    public static Calendar stringToCalendar(String date, String format) {
        Calendar c = Calendar.getInstance();
        c.setTime(stringToDate(date, format));
        return c;
    }

    public static Calendar calendarSetTime(long milliseconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(milliseconds);
        return c;
    }

    public static Calendar calendarSetTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }

    public static boolean isToday(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return isToday(c);
    }

    public static boolean isToday(Calendar calendar) {
        Calendar today = Calendar.getInstance();
        today.setTime(normalizeDate(today));
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.setTime(today.getTime());
        tomorrow.add(Calendar.DATE, 1);
        return calendar.before(tomorrow) && calendar.after(today) || calendar.equals(today);
    }

    public static Calendar normalizeCalendar(Calendar calendar) {
        return normalizeCalendar(calendar.getTime());
    }

    public static Calendar normalizeCalendar(Date date) {
        Calendar normalized = Calendar.getInstance();
        normalized.setTime(stringToDate(dateToString(date, "dd/MM/yyyy"), "dd/MM/yyyy"));
        return normalized;
    }

    public static Calendar normalizeCalendar() {
        return normalizeCalendar(new Date());
    }

    public static Date normalizeDate(Calendar calendar) {
        return normalizeCalendar(calendar).getTime();
    }

    public static Date normalizeDate(Date date) {
        return normalizeCalendar(date).getTime();
    }

    public static Date normalizeDate() {
        return normalizeDate(new Date());
    }

    public static String dateToStringISO8601(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",
                Locale.getDefault());
        return df.format(date);
    }

    public static Date stringToDateISO8601(String dateStr) {
        return stringToDateISO8601(dateStr, null);
    }

    public static Date stringToDateISO8601(String dateStr, TimeZone timeZone) {
        return stringToDate(dateStr, "yyyy-MM-dd'T'HH:mm:ssZ", timeZone);
    }

    public static Date addSecondToDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, 1);
        return calendar.getTime();
    }

    public static boolean dateIsInsideInterval(Date input, Date start, Date end) {
        return start.before(input) && end.after(input);
    }
}
