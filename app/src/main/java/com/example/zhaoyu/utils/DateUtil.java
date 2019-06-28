package com.example.zhaoyu.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

/**
 * Created by Ljw on 2019/5/23.
 */
public class DateUtil {
    public static Date minDate = null;

    static {
        Calendar c = Calendar.getInstance();
        c.set(1, 1900);
        c.set(5, 1);
        c.set(2, 0);
        minDate = new Date(c.getTime().getTime());
    }

    public DateUtil() {
    }

    public static java.util.Date nowDate() {
        return new java.util.Date();
    }

    public static String nowDateTimeToString() {
        return datetimeToString(nowDate());
    }

    public static String dateToString(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String datetimeToString(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String timeToString(java.util.Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    public static java.util.Date stringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            return sdf.parse(date);
        } catch (ParseException var3) {
            return toStringToDate(date);
        }
    }

    public static java.util.Date toStringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

        try {
            return sdf.parse(date);
        } catch (ParseException var3) {
            return null;
        }
    }

    public static java.util.Date getMinDate() {
        Calendar cale = Calendar.getInstance();
        cale.clear();
        cale.set(1900, 1, 1);
        return cale.getTime();
    }

    public static boolean isNull(java.util.Date date) {
        return date == null || date.compareTo(getMinDate()) == 0;
    }

    public static boolean isNotNull(java.util.Date date) {
        return date != null && date.compareTo(getMinDate()) != 0;
    }

    public static java.util.Date stringToDateTime(String datetime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return sdf.parse(datetime);
        } catch (ParseException var3) {
            return toStringToDate(datetime);
        }
    }

    public static java.util.Date addDay(java.util.Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(5, days);
        return cal.getTime();
    }

    public static java.util.Date subtractMinute(int i, java.util.Date nowDateTime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDateTime);
        cal.add(12, i);
        return cal.getTime();
    }

    public static java.util.Date addSECOND(java.util.Date nowDateTime, int seconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDateTime);
        cal.add(13, seconds);
        return cal.getTime();
    }

    public static java.util.Date subtractDay(int i, java.util.Date sysDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(sysDate);
        cal.add(6, i);
        return cal.getTime();
    }

    public static int getWeekOfDate(java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }

        return calendar.get(7);
    }

    public static Date toSQLDate(Timestamp timestamp) {
        long milliseconds = timestamp.getTime() + (long)(timestamp.getNanos() / 1000000);
        return new Date(milliseconds);
    }

    public static Date toSQLDate(java.util.Date date) {
        return new Date(date.getTime());
    }

    public String getFormatedDateString(int timeZoneOffset) {
        if (timeZoneOffset > 13 || timeZoneOffset < -12) {
            timeZoneOffset = 0;
        }

        String[] ids = TimeZone.getAvailableIDs(timeZoneOffset * 60 * 60 * 1000);
        Object timeZone;
        if (ids.length == 0) {
            timeZone = TimeZone.getDefault();
        } else {
            timeZone = new SimpleTimeZone(timeZoneOffset * 60 * 60 * 1000, ids[0]);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone((TimeZone)timeZone);
        return sdf.format(new java.util.Date());
    }

    public static String dateToString(java.util.Date date, String _timeZone) {
        if (date == null) {
            return "";
        } else {
            TimeZone timeZone = null;
            if (StringUtil.isEmpty(_timeZone)) {
                timeZone = TimeZone.getDefault();
            } else {
                timeZone = TimeZone.getTimeZone(_timeZone);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(timeZone);
            return sdf.format(date);
        }
    }

    public static String dateToString1(java.util.Date date) {
        if (date == null) {
            return "";
        } else {
            TimeZone timeZone = null;
            timeZone = TimeZone.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            sdf.setTimeZone(timeZone);
            return sdf.format(date);
        }
    }

    public static String dateToString2(java.util.Date date) {
        if (date == null) {
            return "";
        } else {
            TimeZone timeZone = null;
            timeZone = TimeZone.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setTimeZone(timeZone);
            return sdf.format(date);
        }
    }

    public static String dateToString(Date date, String _timeZone) {
        return dateToString(new java.util.Date(date.getTime()), _timeZone);
    }

    public static String dateToString(Date date) {
        return dateToString(new java.util.Date(date.getTime()));
    }

    public static String timestampToString(java.util.Date date, String _timeZone) {
        if (date == null) {
            return "";
        } else {
            TimeZone timeZone = null;
            if (StringUtil.isEmpty(_timeZone)) {
                timeZone = TimeZone.getDefault();
            } else {
                timeZone = TimeZone.getTimeZone(_timeZone);
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(timeZone);
            return sdf.format(date);
        }
    }

    public static String timestampToString4SSS(java.util.Date date) {
        if (date == null) {
            return "";
        } else {
            TimeZone timeZone = TimeZone.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
            sdf.setTimeZone(timeZone);
            return sdf.format(date);
        }
    }

    public static String timestampToDateString(java.util.Date date) {
        if (date == null) {
            return "";
        } else {
            TimeZone timeZone = TimeZone.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setTimeZone(timeZone);
            return sdf.format(date);
        }
    }

    public static String timestampToString(java.util.Date date) {
        return timestampToString((java.util.Date)date, (String)null);
    }

    public static String timestampToString(Timestamp timestamp, String _timeZone) {
        long milliseconds = timestamp.getTime() + (long)(timestamp.getNanos() / 1000000);
        return timestampToString(new java.util.Date(milliseconds), _timeZone);
    }

    public static String timestampToString(Timestamp timestamp) {
        long milliseconds = timestamp.getTime() + (long)(timestamp.getNanos() / 1000000);
        return timestampToString(new java.util.Date(milliseconds));
    }

    public static String timestampToGMTString(Timestamp timestamp) {
        if (timestamp == null) {
            return "";
        } else {
            TimeZone timeZone = null;
            timeZone = TimeZone.getTimeZone("GMT:00");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(timeZone);
            long milliseconds = timestamp.getTime() + (long)(timestamp.getNanos() / 1000000);
            return sdf.format(new java.util.Date(milliseconds)) + " GMT";
        }
    }

    public static Date getMinSQLDate() {
        return minDate;
    }

    public static Timestamp getMinSQLTimestamp() {
        return new Timestamp(minDate.getTime());
    }

    public static java.util.Date addDate(java.util.Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + (long)day * 24L * 3600L * 1000L);
        return c.getTime();
    }

    public static Date addDate(Date date, int day) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillis(date) + (long)day * 24L * 3600L * 1000L);
        return new Date(c.getTime().getTime());
    }

    public static Timestamp addTimeStamp(Timestamp date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + (long)day * 24L * 3600L * 1000L);
        return new Timestamp(cal.getTime().getTime());
    }

    public static Timestamp decreaseSysDateTime(Timestamp date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + (long)day * 24L * 3600L * 1000L);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        Timestamp sysDateTime = new Timestamp(cal.getTime().getTime());
        return sysDateTime;
    }

    public static Timestamp addTimeStamp(Timestamp date, double day) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + (long)(day * 24.0D * 3600.0D * 1000.0D));
        return new Timestamp(cal.getTime().getTime());
    }

    public static Timestamp addTimeStampBySecond(Timestamp date, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + (long)second * 1000L);
        return new Timestamp(cal.getTime().getTime());
    }

    public static Timestamp addTimeStampByHour(Timestamp date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getMillis(new java.util.Date(date.getTime())) + (long)hour * 3600L * 1000L);
        return new Timestamp(cal.getTime().getTime());
    }

    public static int diffDate(java.util.Date date, java.util.Date date1) {
        return (int)((getMillis(date) - getMillis(date1)) / 86400000L);
    }

    public static int diffHour(java.util.Date start, java.util.Date end) {
        return (int)((getMillis(end) - getMillis(start)) / 3600000L);
    }

    public static int diffMinute(java.util.Date start, java.util.Date end) {
        return (int)((getMillis(end) - getMillis(start)) / 60000L);
    }

    public static long getMillis(java.util.Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }

    public static Date stringToDate(String value, String fmt) {
        SimpleDateFormat sd = new SimpleDateFormat(fmt);
        java.util.Date date = null;

        try {
            date = sd.parse(value);
            return new Date(date.getTime());
        } catch (ParseException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static Date stringToSqlDate(String value) {
        Date date = stringToDate(value, "yyyy-MM-dd");
        if (date == null) {
            date = stringToDate(value, "yyyyMMdd");
        }

        return date;
    }

    public static Date getUTCBeginDate() {
        return stringToSqlDate("19700101");
    }

    public static Timestamp stringToTimestamp(String value, String fmt) {
        SimpleDateFormat sd = new SimpleDateFormat(fmt);
        java.util.Date date = null;

        try {
            date = sd.parse(value);
            return new Timestamp(date.getTime());
        } catch (ParseException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static Timestamp stringToTimestamp(String value) {
        return stringToTimestamp(value, "yyyy-MM-dd HH:mm:ss");
    }

    public static Timestamp stringToTimestamp4SSS(String value) {
        return stringToTimestamp(value, "yyyy-MM-dd HH:mm:ss:SSS");
    }

    public static Timestamp sysDatetime() {
        return new Timestamp((new java.util.Date()).getTime());
    }

    public static Date sysDate() {
        return new Date((new java.util.Date()).getTime());
    }

    public static int currentYear() {
        return Calendar.getInstance().get(1);
    }

    public static int currentMonth() {
        return Calendar.getInstance().get(2) + 1;
    }

    public static int getHour(java.util.Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(11);
    }

    public static void main(String[] args) {
        Date date = stringToDate("2018-06-13", "yyyy-MM-dd");
        Date date1 = stringToSqlDate("2018-06-13");
        System.out.println(date);
    }
}
