package com.aptx.utils;

import com.google.common.base.Strings;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.ParseException;
import java.util.Date;
import java.util.regex.Pattern;

public abstract class TimeUtil {
    public static final String STRING_FORMAT_DATE = "yyyy-MM-dd";
    public static final String STRING_FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String STRING_FORMAT_TIME_ONLY = "HH:mm:ss";
    public static final String STRING_FORMAT_MILLIS = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final DateTimeFormatter FORMAT_DATE = DateTimeFormat.forPattern(STRING_FORMAT_DATE);
    public static final DateTimeFormatter FORMAT_TIME = DateTimeFormat.forPattern(STRING_FORMAT_TIME);
    public static final DateTimeFormatter FORMAT_TIME_ONLY = DateTimeFormat.forPattern(STRING_FORMAT_TIME_ONLY);
    public static final DateTimeFormatter FORMAT_MILLIS = DateTimeFormat.forPattern(STRING_FORMAT_MILLIS);

    public static final Pattern PATTERN_LONG = Pattern.compile("[0-9]+");
    public static final Pattern PATTERN_DATE = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
    public static final Pattern PATTERN_TIME = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}");
    public static final Pattern PATTERN_TIME_ONLY = Pattern.compile("[0-9]{2}:[0-9]{2}:[0-9]{2}");
    public static final Pattern PATTERN_MILLIS = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}\\.[0-9]{3}");

    public static DateTime dateTime(Date date) {
        return new DateTime(date);
    }

    /******************** Object to Date & DateTime ********************/
    public static boolean isTime(Object obj) {
        if(obj == null) {
            return false;
        }
        else if(obj instanceof String) {
            return isStringTime((String) obj);
        }
        else if(obj instanceof Date) {
            return true;
        }
        else if(obj instanceof DateTime) {
            return true;
        }
        else if(obj instanceof java.sql.Date) {
            return true;
        }
        else if(obj instanceof java.sql.Time) {
            return true;
        }
        else if(obj instanceof java.sql.Timestamp) {
            return true;
        }
        else {
            return false;
        }
    }

    public static Date toDate(String string, String pattern) {
        DateTime dateTime = toDateTime(string, pattern);
        if(dateTime != null) {
            return dateTime.toDate();
        }
        else {
            return null;
        }
    }

    public static DateTime toDateTime(String string, String pattern) {
        DateTimeFormatter format = DateTimeFormat.forPattern(pattern);
        return format.parseDateTime(string);
    }

    public static Date toDate(Object obj) {
        DateTime dateTime = toDateTime(obj);
        if(dateTime != null) {
            return dateTime.toDate();
        }
        else {
            return null;
        }
    }

    public static DateTime toDateTime(Object obj) {
        DateTime dateTime = null;
        if(obj == null) {
            dateTime = null;
        }
        else if(obj instanceof String) {
            try {
                dateTime = parseDateTime((String) obj);
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if(obj instanceof Date) {
            long ts = ((Date) obj).getTime();
            dateTime = new DateTime(ts);
        }
        else if(obj instanceof DateTime) {
            dateTime = (DateTime) obj;
        }
        else if(obj instanceof java.sql.Date) {
            long ts = ((java.sql.Date) obj).getTime();
            dateTime = new DateTime(ts);
        }
        else if(obj instanceof java.sql.Time) {
            long ts = ((java.sql.Time) obj).getTime();
            dateTime = new DateTime(ts);
        }
        else if(obj instanceof java.sql.Timestamp) {
            long ts = ((java.sql.Timestamp) obj).getTime();
            dateTime = new DateTime(ts);
        }
        return dateTime;
    }

    private static boolean isStringTime(String string) {
        if(Strings.isNullOrEmpty(string)) {
            return false;
        }
        if(PATTERN_DATE.matcher(string).matches()) {
            return true;
        }
        else if(PATTERN_TIME.matcher(string).matches()) {
            return true;
        }
        else if(PATTERN_MILLIS.matcher(string).matches()) {
            return true;
        }
        else if(PATTERN_TIME_ONLY.matcher(string).matches()) {
            return true;
        }
        else {
            return false;
        }
    }

    private static Date parseDate(String string) throws ParseException {
        DateTime dateTime = parseDateTime(string);
        if(dateTime != null) {
            return dateTime.toDate();
        }
        else {
            return null;
        }
    }

    private static DateTime parseDateTime(String string) throws ParseException {
        DateTime dateTime = null;
        if(Strings.isNullOrEmpty(string)) {
            dateTime = null;
        }
        if(PATTERN_DATE.matcher(string).matches()) {
            dateTime = FORMAT_DATE.parseDateTime(string);
        }
        else if(PATTERN_TIME.matcher(string).matches()) {
            dateTime = FORMAT_TIME.parseDateTime(string);
        }
        else if(PATTERN_MILLIS.matcher(string).matches()) {
            dateTime = FORMAT_MILLIS.parseDateTime(string);
        }
        else if(PATTERN_TIME_ONLY.matcher(string).matches()) {
            dateTime = FORMAT_TIME_ONLY.parseDateTime(string);
        }
        else if(PATTERN_LONG.matcher(string).matches()) {
            dateTime = new DateTime(Long.valueOf(string));
        }
        return dateTime;
    }

    /******************** Date & DateTime to String ********************/
    public static String format(Date date, String pattern) {
        return DateTimeFormat.forPattern(pattern).print(new DateTime(date));
    }

    public static String dateFormat(Date date) {
        return FORMAT_DATE.print(new DateTime(date));
    }

    public static String timeFormat(Date date) {
        return FORMAT_TIME.print(new DateTime(date));
    }

    public static String dateFormat(DateTime dateTime) {
        return dateTime.toString(STRING_FORMAT_DATE);
    }

    public static String timeFormat(DateTime dateTime) {
        return dateTime.toString(STRING_FORMAT_TIME);
    }

    /******************** Start and End of DateTime ********************/
    public static DateTime startOfYear(DateTime dateTime) {
        return new DateTime(dateTime.getYear(), 1, 1, 0, 0, 0, 0);
    }

    public static DateTime endOfYear(DateTime dateTime) {
        return new DateTime(dateTime.getYear(), 12, 31, 23, 59, 59, 999);
    }

    public static DateTime startOfMonth(DateTime dateTime) {
        return new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), 1, 0, 0, 0, 0);
    }

    public static DateTime endOfMonth(DateTime dateTime) {
        DateTime startOfNextMonth = startOfMonth(dateTime).plusMonths(1);
        return startOfNextMonth.minusMillis(1);
    }

    public static DateTime startOfWeek(DateTime dateTime) {
        DateTime startOfDay = startOfDay(dateTime);
        return startOfDay.withDayOfWeek(1);
    }

    public static DateTime endOfWeek(DateTime dateTime) {
        DateTime startOfNextWeek = startOfWeek(dateTime).plusWeeks(1);
        return startOfNextWeek.minusMillis(1);
    }

    public static DateTime startOfDay(DateTime dateTime) {
        return new DateTime(dateTime.getYear(), dateTime.getMonthOfYear(), dateTime.getDayOfMonth(), 0, 0, 0, 0);
    }

    public static DateTime endOfDay(DateTime dateTime) {
        DateTime startOfNextDay = startOfDay(dateTime).plusDays(1);
        return startOfNextDay.minusMillis(1);
    }
/*
    public static Date startOfDay(Date date) {
        if(date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static Date endOfDay(Date date) {
        if(date == null) {
            return null;
        }
        Date startOfDay = startOfDay(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startOfDay);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        calendar.add(Calendar.MILLISECOND, -1);
        return calendar.getTime();
    }
*/
}
