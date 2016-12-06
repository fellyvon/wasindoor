/**
 * @(#) DateUtils.java Copyright ?? Longshine Corporation. All rights reserved.
 */
package com.aiyc.framework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * 日期处理工具类
 */
public class DateUtils {
    private static final String simDateFormat = "yyyy-MM-dd";
    private static final int simDateFormatLength = simDateFormat.length();
    private static final String SimpleDateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    private static final String BJ_TIMEZONE = "GMT+8"; //北京时间标准时区

    /**
     * 日期和天数相加函数，返回相加后的日期
     *
     * @param date Date 日期
     * @param days int   相加的天数，负数表示相减
     * @return Date 相加后的日期
     */
    public static Date addDay(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }

    /**
     * 日期和小时数相加函数，返回相加后的日期
     *
     * @param date Date  日期
     * @param hour int   相加的小时数，负数表示相减
     * @return Date 相加后的日期
     */
    public static Date addHour(Date date, int hours) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hours);
        return cal.getTime();
    }

    /**
     * 日期和月数相加函数，返回相加后的日期
     *
     * @param date Date  日期
     * @param months int   相加的月数，负数表示相减
     * @return Date 相加后的日期
     */
    public static Date addMonth(Date date, int months) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, months);
        return cal.getTime();
    }

    /**
     * 日期和年数相加函数，返回相加后的日期
     *
     * @param date Date  日期
     * @param years int   相加的年数，负数表示相减
     * @return Date 相加后的日期
     */
    public static Date addYear(Date date, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }

    /**
     * 取的服务器北京时间（时区为GMT+8）字符串
     * @return 格式：YYYY-MM-DD HH：MIN：SEC 的字符串
     */
    public static final String getCurrentTime() {
        StringBuffer snow = new StringBuffer(50);
        Calendar cCreateTime = Calendar.getInstance();
        //---------显式设定时区begin------------
        long bjTimeRowOff = TimeZone.getTimeZone(BJ_TIMEZONE).getRawOffset();
        long defaultTimeRowOff = TimeZone.getDefault().getRawOffset();
        if (bjTimeRowOff != defaultTimeRowOff) { //默认时区是否与北京时间时区一致,如果两者不一致
            cCreateTime.setTimeZone(TimeZone.getTimeZone(BJ_TIMEZONE)); //设置时区为GMT+8
        }
        //--------end---------
        snow.append(cCreateTime.get(Calendar.YEAR));
        snow.append("-");
        snow.append(cCreateTime.get(Calendar.MONTH) + 1);
        snow.append("-");
        snow.append(cCreateTime.get(Calendar.DAY_OF_MONTH));
        snow.append(" ");
        snow.append(cCreateTime.get(Calendar.HOUR_OF_DAY));
        snow.append(":");
        snow.append(cCreateTime.get(Calendar.MINUTE));
        snow.append(":");
        snow.append(cCreateTime.get(Calendar.SECOND));
        return snow.toString();
    }

    /**
     * 取的服务器北京时间（时区为GMT+8）字符串。  格式 小时 : 分 :秒
     * @return 格式：HH：MIN：SEC 的字符串
     */
    public static final String getCurrentHMS() {
        StringBuffer snow = new StringBuffer(50);
        Calendar cCreateTime = Calendar.getInstance();
        //---------显式设定时区begin------------
        long bjTimeRowOff = TimeZone.getTimeZone(BJ_TIMEZONE).getRawOffset();
        long defaultTimeRowOff = TimeZone.getDefault().getRawOffset();
        if (bjTimeRowOff != defaultTimeRowOff) { //默认时区是否与北京时间时区一致,如果两者不一致
            cCreateTime.setTimeZone(TimeZone.getTimeZone(BJ_TIMEZONE)); //设置时区为GMT+8
        }
        //--------end---------

        snow.append(cCreateTime.get(Calendar.HOUR_OF_DAY));
        snow.append(":");
        snow.append(cCreateTime.get(Calendar.MINUTE));
        snow.append(":");
        snow.append(cCreateTime.get(Calendar.SECOND));
        return snow.toString();
    }

    /**
     * 取的服务器北京时间（时区为GMT+8）字符串
     * @return String  格式：YYYYMM
     */
    public static final String getCurrentYearMonth() {
        Calendar cld = Calendar.getInstance();
        //---------显式设定时区begin------------
        long bjTimeRowOff = TimeZone.getTimeZone(BJ_TIMEZONE).getRawOffset();
        long defaultTimeRowOff = TimeZone.getDefault().getRawOffset();
        if (bjTimeRowOff != defaultTimeRowOff) { //默认时区是否与北京时间时区一致,如果两者不一致
            cld.setTimeZone(TimeZone.getTimeZone(BJ_TIMEZONE)); //设置时区为GMT+8
        }
        //--------end---------

        String snf = Integer.toString(cld.get(Calendar.YEAR));
        String syf = Integer.toString(cld.get(Calendar.MONTH) + 1);
        if (syf.length() < 2) {
            return snf + '0' + syf;
        } else {
            return snf + syf;
        }
    }

    /**
     * 取的服务器北京时间（时区为GMT+8）字符串，格式：yyyy-mm-dd
     * @return String
     */
    public static final String getCurrentDateStr() {
        Calendar cld = Calendar.getInstance();
        //---------显式设定时区begin------------
        long bjTimeRowOff = TimeZone.getTimeZone(BJ_TIMEZONE).getRawOffset();
        long defaultTimeRowOff = TimeZone.getDefault().getRawOffset();
        if (bjTimeRowOff != defaultTimeRowOff) { //默认时区是否与北京时间时区一致,如果两者不一致
            cld.setTimeZone(TimeZone.getTimeZone(BJ_TIMEZONE)); //设置时区为GMT+8
        }
        //--------end---------

        String year = Integer.toString(cld.get(Calendar.YEAR));
        String month = Integer.toString(cld.get(Calendar.MONTH) + 1);
        if (month.length() == 1) {
            month = '0' + month;
        }
        String day = Integer.toString(cld.get(Calendar.DAY_OF_MONTH));
        if (day.length() == 1) {
            day = '0' + day;
        }
        return year + "-" + month + "-" + day;
    }

    /**
     * 取的服务器北京时间（时区为GMT+8）字符串
     * @return String  格式:yyyymmdd
     */
    public static final String getDateStr() {
        Calendar cld = Calendar.getInstance();
        //---------显式设定时区begin------------
        long bjTimeRowOff = TimeZone.getTimeZone(BJ_TIMEZONE).getRawOffset();
        long defaultTimeRowOff = TimeZone.getDefault().getRawOffset();
        if (bjTimeRowOff != defaultTimeRowOff) { //默认时区是否与北京时间时区一致,如果两者不一致
            cld.setTimeZone(TimeZone.getTimeZone(BJ_TIMEZONE)); //设置时区为GMT+8
        }
        //--------end---------

        String year = Integer.toString(cld.get(Calendar.YEAR));
        String month = Integer.toString(cld.get(Calendar.MONTH) + 1);
        if (month.length() == 1) {
            month = '0' + month;
        }
        String day = Integer.toString(cld.get(Calendar.DAY_OF_MONTH));
        if (day.length() == 1) {
            day = '0' + day;
        }
        return year + month + day;
    }

    /**
     * 取的服务器北京时间（时区为GMT+8）
     * @return java.sql.Date
     */
    public static java.sql.Date getCurrentSqlDate() {
        Calendar cCreateTime = Calendar.getInstance();
        //---------显式设定时区begin------------
        long bjTimeRowOff = TimeZone.getTimeZone(BJ_TIMEZONE).getRawOffset();
        long defaultTimeRowOff = TimeZone.getDefault().getRawOffset();
        //--------end---------
        return new java.sql.Date(cCreateTime.getTimeInMillis() +
                                 (bjTimeRowOff - defaultTimeRowOff));
    }

    /**
     * 字符串转换为普通的日期
     * @param str 要转换的字符串,可以是日期，也可带分秒，但必须符合日期的格式
     * @return 转换不成功不报错，返回null
     */
    public static java.util.Date strToDate(String str) {
        if (null != str && str.length() > 0) {
            try {
                if (str.length() <= simDateFormatLength) { // 只包含日期。
                    return (new SimpleDateFormat(simDateFormat)).parse(str);
                } else { // 包含日期时间
                    return (new SimpleDateFormat(SimpleDateTimeFormat)).parse(
                            str);
                }
            } catch (ParseException error) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * 字符串转换为sql的日期
     * @param str 要转换的字符串
     * @return 转换不成功返回null
     */
    public static java.sql.Date strToSqlDate(String str) {
        if (strToDate(str) == null || str.length() < 1) {
            return null;
        } else {
            return new java.sql.Date(strToDate(str).getTime());
        }
    }

    /**
     * 字符串按照指定的格式转换为普通的日期
     * @param str 要转换的字符串,可以是日期，也可带分秒，但必须符合日期的格式
     * @param format 自定义格式，比如：yyyymmdd,mm/dd/yy等
     * @return 转换不成功不报错，返回null
     */
    public static java.util.Date strToDate(String str, String format) {
        if (null != str && str.length() > 0) {
            try {
                return (new SimpleDateFormat(format)).parse(str);
            } catch (ParseException error) {
                return null;
            }
        } else {
            return null;
        }
    }

    /**
     * sql日期型转换为带时间的字符串  格式：yyyy-MM-dd HH:mm:ss
     * @param sqlDate
     * @return 字符串或者null
     */
    public static String dateTimeToStr(java.sql.Date sqlDate) {
        if (sqlDate == null) {
            return null;
        } else {
            return (new SimpleDateFormat(SimpleDateTimeFormat)).format(
                    sqlDate);
        }
    }

    /**
     * sql日期型转换为不带时间的字符串  格式：yyyy-MM-dd
     * @param sqlDate
     * @return 字符串或者null
     */
    public static String dateToStr(java.sql.Date sqlDate) {
        if (sqlDate == null) {
            return null;
        } else {
            return (new SimpleDateFormat(simDateFormat)).format(sqlDate);
        }
    }

    /**
     * 普通日期型转换为带时间的字符串 格式：yyyy-MM-dd HH:mm:ss
     * @param date
     * @return 字符串或者null
     */
    public static String dateTimeToStr(java.util.Date date) {
        if (date == null) {
            return null;
        } else {
            return (new SimpleDateFormat(SimpleDateTimeFormat)).format(date);
        }
    }

    /**
     * 普通日期型转换为不带时间的字符串 格式：yyyy-MM-dd
     * @param date
     * @return 字符串或者null
     */
    public static String dateToStr(java.util.Date date) {
        if (date == null) {
            return null;
        } else {
            return (new SimpleDateFormat(simDateFormat)).format(date);
        }
    }

    /**
     * 普通日期型转换sql日期
     * @param date
     * @return
     */
    public static java.sql.Date toSqlDate(java.util.Date date) {
        return new java.sql.Date(date.getTime());
    }

    /**
     * sql日期型转换普通日期
     * @param date
     * @return
     */
    public static java.util.Date toUtilDate(java.sql.Date date) {
        return new java.util.Date(date.getTime());
    }

    /**
     * 获得给定日期的年份
     * @param date 给定日期
     * @return int 年份
     * @throws NullPointerException 如果参数年份为null，抛出异常。
     */
    public int getYear(java.util.Date date) throws NullPointerException {
        if (date == null) {
            throw new NullPointerException("日期参数为null");
        } else {
            Calendar cld = Calendar.getInstance();
            cld.setTime(date);
            return cld.get(Calendar.YEAR);
        }
    }

    /**
     * 获得给定日期的月份
     * @param date 给定日期
     * @return int 月份（1-12）
     * @throws NullPointerException 如果参数年份为null，抛出异常。
     */
    public int getMonth(java.util.Date date) throws NullPointerException {
        if (date == null) {
            throw new NullPointerException("日期参数为null");
        } else {
            Calendar cld = Calendar.getInstance();
            cld.setTime(date);

            return 1 + cld.get(Calendar.MONTH);
        }
    }

    /**
     * 获取任意两个日期间的天数
     * @param tStartDate 起始日期,
     * @param tEndDate  结束日期
     * @return 天数
     */
    public static int getDayCount(Date tStartDate, Date tEndDate) {
        int iRetVal = 0;
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.
                                     getInstance();
        calendar.setTime(tStartDate);
        GregorianCalendar calendar2 = (GregorianCalendar) GregorianCalendar.
                                      getInstance();
        calendar2.setTime(tEndDate);
        int iMaxDays = 0;

        while (calendar.before(calendar2)) {
            if (calendar.isLeapYear(calendar.get(GregorianCalendar.YEAR))) {
                iMaxDays = 366;
            } else {
                iMaxDays = 365;
            }
            ++iRetVal;
            calendar.roll(GregorianCalendar.DAY_OF_YEAR, true);

            if (calendar.get(GregorianCalendar.DAY_OF_YEAR) == iMaxDays) {
                calendar.roll(GregorianCalendar.YEAR, 1);
                calendar.set(GregorianCalendar.MONTH, GregorianCalendar.JANUARY);
                calendar.set(GregorianCalendar.DAY_OF_MONTH, 1);
            }
        }
        return iRetVal;
    }

    /**
     * 传入yyyy-mm-dd的日期字符串返回yyyy-mm-dd 星期的形式
     */
    public static String getWeekNumberFromDate(String thedate) {
        String strReturn = "";
        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStringToParse = thedate;
        try {
            Date date = bartDateFormat.parse(dateStringToParse);
            SimpleDateFormat bartDateFormat2 = new SimpleDateFormat(
                    "yyyy-MM-dd EEEE");
            strReturn = bartDateFormat2.format(date);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return strReturn;
    }
    
//    /**
//     * 获取数据库服务器时间
//     * @param connName String 连接名
//     * @return Date
//     */
//    public static Date getDataBaseTime(String connName) {
//        return DBUtils.getConnectionDate(connName);
//    }
//    
//    /**
//     * 获取数据库服务器时间
//     *
//     * @return Date
//     */
//    public static Date getDataBaseTime() {
//        return DBUtils.getConnectionDate();
//    }
}
