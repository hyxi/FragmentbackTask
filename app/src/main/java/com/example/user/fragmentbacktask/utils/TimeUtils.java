package com.example.user.fragmentbacktask.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by user on 2016/5/11.
 */
public class TimeUtils {

    /**
     * 将 当前时间 与 指定时间 做差运算，得到指定格式的返回值
     *
     * @param date 指定时间
     * @return 返回 ："XX秒前"、"XX分钟前"、"XX小时前"、或 "yyyy-MM-dd HH:mm:ss" 四种格式中的一种
     */
    public static String getCreateString(Date date) {
        return getCreateString(date, null, false);
    }

    /**
     * 将 当前时间 与 指定时间 做差运算，得到指定格式的返回值
     *
     * @param date        指定时间
     * @param template    返回值的一种指定格式
     * @param showJustNow 是否返回"刚刚"这种格式的返回值
     * @return 返回 ："刚刚"、"XX秒前"、"XX分钟前"、"XX小时前"、或 template(默认为:"yyyy-MM-dd HH:mm:ss") 五种格式中的一种
     */
    public static String getCreateString(Date date, String template, boolean showJustNow) {
        // 将参数date转换为Calendar格式
        Calendar calendarOld = Calendar.getInstance();
        if (date != null)
            calendarOld.setTime(date);
        // 返回值的格式
        if (StringUtils.isNullOrEmpty(template)) {
            template = "MM-dd HH:mm";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(template, Locale.US);
        // 当前系统时间
        Calendar calendarNow = Calendar.getInstance();
        if (calendarNow.get(Calendar.YEAR) - calendarOld.get(Calendar.YEAR) > 0) {
            SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
            return sdfYear.format(date);
        } else if (calendarNow.get(Calendar.MONTH) - calendarOld.get(Calendar.MONTH) > 0) {
            return sdf.format(date);
        } else if (calendarNow.get(Calendar.DAY_OF_MONTH) - calendarOld.get(Calendar.DAY_OF_MONTH) > 0) {
            return sdf.format(date);
        } else if (calendarNow.get(Calendar.HOUR_OF_DAY) - calendarOld.get(Calendar.HOUR_OF_DAY) > 0) {
            int i = calendarNow.get(Calendar.HOUR_OF_DAY) - calendarOld.get(Calendar.HOUR_OF_DAY);
            return i + "小时前";
        } else if (calendarNow.get(Calendar.MINUTE) - calendarOld.get(Calendar.MINUTE) > 0) {
            int i = calendarNow.get(Calendar.MINUTE) - calendarOld.get(Calendar.MINUTE);
            return i + "分钟前";
        } else if (calendarNow.get(Calendar.SECOND) - calendarOld.get(Calendar.SECOND) > 0) {
            int i = calendarNow.get(Calendar.SECOND) - calendarOld.get(Calendar.SECOND);
            return i + "秒前";
        } else if (showJustNow
                && calendarNow.get(Calendar.SECOND) - calendarOld.get(Calendar.SECOND) == 0) {
            return "刚刚";
        } else {
            return sdf.format(date);
        }
    }

}
