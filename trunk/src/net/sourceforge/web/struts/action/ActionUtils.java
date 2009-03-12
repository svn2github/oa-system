/* =====================================================================
 *
 * Copyright (c) Sourceforge INFORMATION TECHNOLOGY All rights reserved.
 *
 * =====================================================================
 */
package net.sourceforge.web.struts.action;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import com.shcnc.struts.form.DateFormatter;

/**
 * 为Action提供公用的方法
 * @author nicebean
 * @version 1.0 (2005-11-15)
 */
public class ActionUtils extends com.shcnc.struts.action.ActionUtils {
    private static DateFormatter dfDisplayDate = new DateFormatter(
            java.util.Date.class, "yyyy/MM/dd");

    private static DateFormatter df8CharsDate = new DateFormatter(
            java.util.Date.class, "yyyyMMdd");

    /**
     * 取得yyyyMMdd格式的系统日期
     * @return yyyyMMdd格式的日期字符串
     */
    public static String getTodayAs8Chars() {
        return get8CharsFromDate(new Date());
    }

    /**
     * 取得yyyy/MM/dd格式的系统日期
     * @return yyyy/MM/dd格式的日期字符串
     */
    public static String getTodayAsDisplayDate() {
        return getDisplayDateFromDate(new Date());
    }
    
    /**
     * 将yyyyMMdd格式的日期字符串转换为yyyy/MM/dd格式
     * @return yyyy/MM/dd格式的日期字符串
     */
    public static String getDisplayDateFrom8Chars(String date) {
        return date.substring(0, 4) + "/" + date.substring(4, 6) + "/"
                + date.substring(6, 8);
    }

    /**
     * 将yyyy/MM/dd格式的日期字符串转换为yyyyMMdd格式
     * @return yyyyMMdd格式的日期字符串
     */
    public static String get8CharsFromDisplayDate(String date) {
        return date.substring(0, 4) + date.substring(5, 7)
                + date.substring(8, 10);
    }

    /**
     * 将Date对象代表的日期转换为yyyyMMdd格式的字符串
     * @return yyyyMMdd格式的日期字符串
     */
    public static String get8CharsFromDate(Date date) {
        return df8CharsDate.format(date);
    }

    /**
     * 将yyyyMMdd格式的日期字符串转换Date对象
     * @return Date对象
     */
    public static Date getDateFrom8Chars(String s) {
        if (s.length() != 8) {
            throw new RuntimeException("date error");
        }
        return (Date) df8CharsDate.unformat(s);
    }

    /**
     * 将yyyy/MM/dd格式的日期字符串转换Date对象
     * @return Date对象
     */
    public static java.util.Date getDateFromDisplayDate(String s) {
        if (s.length() != 10) {
            throw new RuntimeException("date error");
        }
        return (Date) dfDisplayDate.unformat(s);
    }

    /**
     * 将Date对象代表的日期转换为yyyy/MM/dd格式的字符串
     * @return yyyy/MM/dd格式的日期字符串
     */
    public static String getDisplayDateFromDate(Date date) {
        return dfDisplayDate.format(date);
    }

    /**
     * 将yyyy/MM/dd格式的日期字符串转换成用于设置查询起始时间的Date对象，产生的Date对象的时间部分将设置为00:00:00。
     * @return Date对象
     */
    public static Date getQueryBeginDateFromDisplayDate(String s) {
    	Date date=getDateFromDisplayDate(s);
    	Calendar calendar=Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(Calendar.HOUR_OF_DAY,0);
    	calendar.set(Calendar.MINUTE,0);
    	calendar.set(Calendar.SECOND,0);
    	return calendar.getTime();
    }
    
    /**
     * 将yyyy/MM/dd格式的日期字符串转换成用于设置查询结束时间的Date对象，产生的Date对象的时间部分将设置为23:59:59。
     * @return Date对象
     */
    public static Date getQueryToDateFromDisplayDate(String s) {
    	Date date=getDateFromDisplayDate(s);
    	Calendar calendar=Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(Calendar.HOUR_OF_DAY,23);
    	calendar.set(Calendar.MINUTE,59);
    	calendar.set(Calendar.SECOND,59);
    	return calendar.getTime();
    }

    /**
     * 将给定的Date对象转换成用于设置查询起始时间的Date对象，产生的Date对象的时间部分将设置为00:00:00。
     * @return Date对象
     */
    public static Date getStartDate(Date date) {
    	Calendar calendar=Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(Calendar.HOUR_OF_DAY,0);
    	calendar.set(Calendar.MINUTE,0);
    	calendar.set(Calendar.SECOND,0);
    	return calendar.getTime();
    }
    
    /**
     * 将给定的Date对象转换成用于设置查询结束时间的Date对象，产生的Date对象的时间部分将设置为23:59:59。
     * @return Date对象
     */
    public static Date getExpireDate(Date date) {
    	Calendar calendar=Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.set(Calendar.HOUR_OF_DAY,23);
    	calendar.set(Calendar.MINUTE,59);
    	calendar.set(Calendar.SECOND,59);
    	return calendar.getTime();
    }

    public static BigDecimal parseBigDecimal(String amount) {
        if(amount==null) return null;
        try{
            return new BigDecimal(amount);
        }
        catch(Throwable t)
        {
            return null;
        }
    }
}