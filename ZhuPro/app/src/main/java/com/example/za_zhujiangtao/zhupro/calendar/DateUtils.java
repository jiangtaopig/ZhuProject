package com.example.za_zhujiangtao.zhupro.calendar;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/1
 */
public class DateUtils {

    private static final int DAY_NUMBER = 42;
    private static final String[] WEEKS = {"日", "一", "二", "三", "四", "五", "六"};

    /**
     * 获取 dateStr 所在月的42天数据
     *
     * @param dateStr
     * @return
     */
    public static DateBean getMonthData(String dateStr) {
        DateBean dateBean = new DateBean();
        List<Integer> dateList = new ArrayList<>();
        Calendar cale = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cale.setTime(sdf.parse(dateStr));
            if (!isCurMonth(dateStr, sdf)) { // 如果不是当前月
                cale.set(Calendar.DAY_OF_MONTH, 1);     //设置到每月的第一天
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        int day = cale.get(Calendar.DATE);
        int week = cale.get(Calendar.DAY_OF_WEEK) - 1;
        int maxDay = getMaxDayOfMonth(cale); // 获取本月的最大天数
        if (week > 0) {
            int lastDay = getPreMonthLastDay(cale); // 获取上个月的最后一天
            for (int i = week - 1; i > -1; i--) {
                dateList.add(lastDay - i);
            }
        }
        for (int i = 1; i <= maxDay; i++) {
            dateList.add(i);
        }
        int size = dateList.size();
        dateBean.nextMonthStartIndex = size;
        if (size < DAY_NUMBER) {
            int diff = DAY_NUMBER - size;
            for (int i = 1; i <= diff; i++) {
                dateList.add(i);
            }
        }
        dateBean.monthList = dateList;
        dateBean.lastMonthEndIndex = week;
        Log.e("DateUtils", "year ：" + year + ", month : " + month + ", day = " + day + ", week = " + week);
        return dateBean;
    }

    /**
     * 获取上个月的最后一天
     *
     * @param calendar
     * @return
     */
    private static int getPreMonthLastDay(Calendar calendar) {
        calendar.add(Calendar.MONTH, -1);//获取当前时间上一个月
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));//获取最后一天
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DATE);
        return day;
    }

    /**
     * 获取某个月有多少天
     *
     * @param calendar
     * @return
     */
    private static int getMaxDayOfMonth(Calendar calendar) {
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    private static boolean isCurMonth(String dataStr, SimpleDateFormat sdf) {
        Date date = null;
        try {
            date = sdf.parse(dataStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isCurMonth(date);
    }

    /**
     * 是否是当前月
     *
     * @param date
     * @return
     */
    private static boolean isCurMonth(Date date) {
        if (date == null) {
            return false;
        }
        boolean isCurMonth = false;
        Date curDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
        if (simpleDateFormat.format(date).equals(simpleDateFormat.format(curDate))) {
            isCurMonth = true;
        }
        return isCurMonth;
    }

    /**
     * 获取当前月的下一个月日期
     *
     * @param dateStr
     * @return
     */
    public static String getNextMonthData(String dateStr) {
        Calendar cale = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cale.setTime(sdf.parse(dateStr));
            cale.add(Calendar.MONTH, 1);//获取当前时间下一个月
            if (!isCurMonth(cale.getTime())) { // 如果不是当前月
                cale.set(Calendar.DAY_OF_MONTH, 1);     //设置到每月的第一天
            } else {
                cale.setTime(new Date());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        int day = cale.get(Calendar.DATE);
        return sdf.format(cale.getTime());
    }

    /**
     * 获取当前月的上一个月日期
     *
     * @param dateStr
     * @return
     */
    public static String getLastMonthData(String dateStr) {
        Calendar cale = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cale.setTime(sdf.parse(dateStr));
            cale.add(Calendar.MONTH, -1);//获取当前时间上一个月
            if (!isCurMonth(cale.getTime())) { // 如果不是当前月
                cale.set(Calendar.DAY_OF_MONTH, 1);     //设置到每月的第一天
            } else {
                cale.setTime(new Date());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int year = cale.get(Calendar.YEAR);
        int month = cale.get(Calendar.MONTH) + 1;
        int day = cale.get(Calendar.DATE);
        return sdf.format(cale.getTime());
    }


}
