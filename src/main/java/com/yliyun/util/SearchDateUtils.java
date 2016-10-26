package com.yliyun.util;


import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.StrictISODateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class SearchDateUtils {

    public static String pattern = "yyyy-MM-dd HH:mm:ss";

    public static final String SEARCH_DATE_FORMAT_YYYY_MM_DD_T_HH_MM_SSSZZ = "yyyy-MM-dd'T'HH:mm:ssZZ";

    private static final DateTimeFormatter searchDateFormat = StrictISODateTimeFormat.dateTimeNoMillis();

    //new SimpleDateFormat(SEARCH_DATE_FORMAT_YYYY_MM_DD_T_HH_MM_SS_SSSZZ);

    private SearchDateUtils() {
    }

    /**
     * 获得默认的 date pattern
     */
    public static String getDatePattern() {
        return pattern;
    }

    /**
     * 返回预设Format的当前日期字符串
     */
    public static String getToday() {
        Date today = new Date();
        return format(today);
    }

    /**
     * 使用预设Format格式化Date成字符串
     */
    public static String format(Date date) {
        return date == null ? " " : format(date, getDatePattern());
    }

    /**
     * 使用参数Format格式化Date成字符串
     */
    public static String format(Date date, String pattern) {
        return date == null ? " " : new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 使用预设格式将字符串转为Date
     */
    public static Date parse(String strDate) throws ParseException {
        return parse(strDate, getDatePattern());
    }

    /**
     * 使用参数Format将字符串转为Date
     */
    public static Date parse(String strDate, String pattern) throws ParseException

    {
        if(strDate != null){

            return new SimpleDateFormat(pattern).parse(strDate);
        }
        return null;
    }


    public static Date getFormattedDate(String dateString) {
        return searchDateFormat.parseDateTime(dateString).toDate();
    }

    public static String formatDate(Date date) {
        return searchDateFormat.print(date.getTime());
    }
}
