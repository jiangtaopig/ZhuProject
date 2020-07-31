package daily.yiyuan.com.test_java.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/30
 */
public class CalendarUtils {
    private static final String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};
    private static final String[] WEEK = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat FULL_SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args) {
        String firstDay = getFirstDayOfMonth(2016, 2);
        String lastDay = getLastDayOfMonth(2016, 2);
        System.out.println(firstDay + "-------" + lastDay + "---------" + getCurDay());

//        for (int i = 1; i <= 12; i++) {
//            int maxDay = getMaxDay(2019, i);
//            System.out.println("month " + i + " maxDay is : " + maxDay);
//        }
//
        getMonthData("2020-07-17");
//        getNextMonthData("2020-06-28");
//        getLastMonthData("2020-06-28");

        getWeekDays("2020-07-19");
        getDays("2020-07-08", 10, 30);
        getNextOrPre7Day("2020-07-14", 7);
        getNextOrPre7Day("2020-07-14", -7);
        getDays2("2020-07-14", 2, 4);

        getMonth("2020-07-31", 4, 4);

        getMonthDate("2020-11-17");

        getPreHour(new Date());
        getNextHour(new Date());
        getMonthAndDay(new Date());
        getWeek(new Date());

        System.out.println("---------------------------------------------------------------------");
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(new Date());

        String t1 = FULL_SDF.format(calendar.getTime());
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 11);
        String t2 = FULL_SDF.format(calendar.getTime());
        calendar.set(Calendar.DATE, 20);
        calendar.set(Calendar.YEAR, 2019);
        String t3 = FULL_SDF.format(calendar.getTime());
        calendar.set(Calendar.YEAR, 2019);

        calendar.set(Calendar.MONTH, 5);
        String t4 = FULL_SDF.format(calendar.getTime());

        System.out.println("t1 = " + t1 + ", t2 = " + t2 + ",t3 = " + t3 + ", t4 = "+t4);
        getDayAndWeek(new Date());
    }

    /**
     * 获取xx年xx月的第一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置月份
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        // 获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        // 设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime());
        return firstDayOfMonth;
    }

    /**
     * 获取xx年xx月的最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置年、月份
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());
        return lastDayOfMonth;
    }

    /**
     * 获取xx年xx月的最大天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMaxDay(int year, int month) {
        Calendar cal = Calendar.getInstance();
        // 设置年、月份
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    /**
     * 获取当前是几号
     *
     * @return
     */
    public static int getCurDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static List<Integer> getMonthData(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Integer> dateList = new ArrayList<>();
        Calendar cale = Calendar.getInstance();

        try {
            cale.setTime(sdf.parse(dateStr));
            cale.set(Calendar.DAY_OF_MONTH, 1);     //设置到每月的第一天
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
        if (size < 42) {
            int diff = 42 - size;
            for (int i = 1; i <= diff; i++) {
                dateList.add(i);
            }
        }
        System.out.println("year ：" + year + ", month : " + month + ", day = " + day + ", week = " + week);
        return dateList;
    }

    private static int getPreMonthLastDay(Calendar calendar) {
        calendar.add(Calendar.MONTH, -1);//获取当前时间上一个月
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));//获取最后一天
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DATE);
        return day;
    }

    private static int getMaxDayOfMonth(Calendar calendar) {
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        return maxDay;
    }

    private static boolean isCurMonth(String dataStr, SimpleDateFormat sdf) {
        boolean isCurMonth = false;
        try {
            Date date = sdf.parse(dataStr);
            Date curDate = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM");
            if (simpleDateFormat.format(date).equals(simpleDateFormat.format(curDate))) {
                isCurMonth = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
            if (!isCurMonth(dateStr, sdf)) { // 如果不是当前月
                cale.set(Calendar.DAY_OF_MONTH, 1);     //设置到每月的第一天
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
            if (!isCurMonth(dateStr, sdf)) { // 如果不是当前月
                cale.set(Calendar.DAY_OF_MONTH, 1);     //设置到每月的第一天
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
     * 获取当前周的7天
     *
     * @param dateStr
     * @return
     */
    public static Map<String, String> getWeekDays(String dateStr) {
        List<String> days = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<String, String> dateMap = new LinkedHashMap<>();
        try {
            calendar.setTime(sdf.parse(dateStr));
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if (week > 0) {
                for (int i = 1; i <= week; i++) {
                    calendar.add(Calendar.DATE, -1);
                    int tmp = calendar.get(Calendar.DAY_OF_MONTH);
                    days.add(0, String.valueOf(tmp));
                    dateList.add(0, sdf.format(calendar.getTime()));
                }
                calendar.add(Calendar.DATE, week);
            }
            days.add(String.valueOf(day));
            dateList.add(dateStr);
            for (int i = 0, diff = 7 - week - 1; i < diff; i++) {
                calendar.add(Calendar.DATE, 1);
                int tmp = calendar.get(Calendar.DAY_OF_MONTH);
                days.add(String.valueOf(tmp));
                dateList.add(sdf.format(calendar.getTime()));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 7; i++) {
            dateMap.put(days.get(i), dateList.get(i));
        }
        return dateMap;
    }

    /**
     * 获取 dateStr 之前preDays 和 之后 postDays 加起来的天数
     *
     * @param dateStr
     * @param preDays
     * @param postDays
     * @return
     */
    public static List<String> getDays(String dateStr, int preDays, int postDays) {
        List<String> days = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(sdf.parse(dateStr));
            for (int i = 0; i < preDays; i++) {
                calendar.add(Calendar.DATE, -1); // 当前日期的前一天
                String date = sdf.format(calendar.getTime());
                days.add(0, date);
            }
            days.add(dateStr);
            calendar.add(Calendar.DATE, preDays);
            for (int i = 0; i < postDays; i++) {
                calendar.add(Calendar.DATE, 1); // 当前日期的后一天
                String date = sdf.format(calendar.getTime());
                days.add(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public static String getNextOrPre7Day(String dateStr, int day) {
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(sdf.parse(dateStr));
            calendar.add(Calendar.DATE, day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String date = sdf.format(calendar.getTime());
        return date;
    }


    public static List<String> getDays2(String dateStr, int preDays, int postDays) {
        List<String> days = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(sdf.parse(dateStr));
            for (int i = 1; i <= preDays; i++) {
                calendar.add(Calendar.DATE, -7); // 当前日期的前一天
                String date = sdf.format(calendar.getTime());
                days.add(0, date);
            }
            days.add(dateStr);
            calendar.add(Calendar.DATE, preDays * 7);
            for (int i = 1; i <= postDays; i++) {
                calendar.add(Calendar.DATE, 7); // 当前日期的后一天
                String date = sdf.format(calendar.getTime());
                days.add(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public static List<String> getMonth(String dateStr, int last, int next) {
        List<String> days = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(sdf.parse(dateStr));
            for (int i = 1; i <= last; i++) {
                calendar.add(Calendar.MONTH, -1);//获取当前时间上一个月
                String date = sdf.format(calendar.getTime());
                days.add(0, date);
            }
            days.add(dateStr);
            calendar.add(Calendar.MONTH, last);// 设置为当前月
            for (int i = 1; i <= next; i++) {
                calendar.add(Calendar.MONTH, 1);//获取当前时间下一个月
                String date = sdf.format(calendar.getTime());
                days.add(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }


    public static DateBean getMonthDate(String dateStr) {
        DateBean dateBean = new DateBean();
        List<Integer> dateList = new ArrayList<>();
        List<String> dateStrList = new ArrayList<>();
        List<DayBean> dayBeanList = new ArrayList<>();
        Calendar cale = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            cale.setTime(sdf.parse(dateStr));
            cale.set(Calendar.DAY_OF_MONTH, 1);     //设置到每月的第一天
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int week = cale.get(Calendar.DAY_OF_WEEK) - 1;
        if (week > 0) {
            getPreMonthLastDay(cale, week, dayBeanList);
        }
        int maxDay = getMaxDayOfMonth(cale); // 获取本月的最大天数

        DayBean dayBean = new DayBean();//本月的第一天
        dayBean.day = cale.get(Calendar.DATE);
        dayBean.dateStr = SDF.format(cale.getTime());
        dayBeanList.add(dayBean);

        for (int i = 0; i < maxDay - 1; i++) {
            cale.add(Calendar.DATE, 1); // 当前日期的前1天
            DayBean tmp = new DayBean();
            tmp.day = cale.get(Calendar.DATE);
            tmp.dateStr = SDF.format(cale.getTime());
            dayBeanList.add(tmp);
        }
        cale.add(Calendar.DATE, -(maxDay - 1));
        int diff = 42 - dayBeanList.size();
        getNextMonthData(cale, diff, dayBeanList);
        return dateBean;
    }

    /**
     * 获取上个月的最后几天
     *
     * @param calendar
     * @param days
     * @return
     */
    private static List<DayBean> getPreMonthLastDay(Calendar calendar, int days, List<DayBean> dayBeanList) {
        calendar.add(Calendar.MONTH, -1);//获取当前时间上一个月
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));//获取最后一天
        int day = calendar.get(Calendar.DATE);
        DayBean dayBean = new DayBean();
        dayBean.day = day;
        dayBean.dateStr = SDF.format(calendar.getTime());
        dayBeanList.add(dayBean);
        getDayBeanList(days, calendar, dayBeanList);
        calendar.add(Calendar.DATE, days);
        return dayBeanList;
    }

    /**
     * 获取当前月的下一个月开始几天
     *
     * @return
     */
    public static List<DayBean> getNextMonthData(Calendar cale, int days, List<DayBean> dayBeanList) {
        cale.add(Calendar.MONTH, 1);//获取当前时间下一个月
        cale.set(Calendar.DAY_OF_MONTH, 1);     //设置到每月的第一天
        DayBean dayBean = new DayBean();
        dayBean.day = cale.get(Calendar.DATE);
        dayBean.dateStr = SDF.format(cale.getTime());
        dayBeanList.add(dayBean);
        for (int i = 0; i < days - 1; i++) {
            cale.add(Calendar.DATE, 1);
            DayBean tmp = new DayBean();
            tmp.day = cale.get(Calendar.DATE);
            tmp.dateStr = SDF.format(cale.getTime());
            dayBeanList.add(tmp);
        }
        cale.add(Calendar.DATE, -(days - 1));
        return dayBeanList;
    }

    private static void getDayBeanList(int days, Calendar cale, List<DayBean> dayBeanList) {
        for (int i = 0; i < days - 1; i++) {
            cale.add(Calendar.DATE, -1);
            DayBean tmp = new DayBean();
            tmp.day = cale.get(Calendar.DATE);
            tmp.dateStr = SDF.format(cale.getTime());
            dayBeanList.add(0, tmp);
        }
    }


    public static String getPreHour(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour + " : 00";
    }

    public static String getNextHour(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String time = FULL_SDF.format(calendar.getTime());
        System.out.println("hour = " + hour + ", time = " + time);
        return hour + " : 00";
    }

    public static String getMonthAndDay(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        System.out.println(month + "月" + day + "日");
        return month + "月" + day + "日";
    }

    public static String getWeek(Date date) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        System.out.println(WEEK[week]);
        return WEEK[week];
    }

    public static List<String> getDayAndWeek(Date date) {
        List<String> list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1); // 设置到每月的第一天
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int day = calendar.get(Calendar.DATE);
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        String str = day + "日 " + WEEK[week];
        list.add(str);
        for (int i = 0; i < maxDay - 1; i++) {
            calendar.add(Calendar.DATE, 1); // 下一天
            StringBuilder sb = new StringBuilder();
            day = calendar.get(Calendar.DATE);
            week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            sb.append(day)
                    .append("日 ")
                    .append(WEEK[week]);
            list.add(sb.toString());
        }
        return list;
    }

}
