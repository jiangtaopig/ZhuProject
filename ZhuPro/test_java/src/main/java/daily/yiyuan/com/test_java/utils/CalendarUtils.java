package daily.yiyuan.com.test_java.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/30
 */
public class CalendarUtils {
    private static final String[] weeks = {"日", "一", "二", "三", "四", "五", "六"};

    public static void main(String[] args) {
        String firstDay = getFirstDayOfMonth(2016, 2);
        String lastDay = getLastDayOfMonth(2016, 2);
        System.out.println(firstDay + "-------" + lastDay + "---------" + getCurDay());

//        for (int i = 1; i <= 12; i++) {
//            int maxDay = getMaxDay(2019, i);
//            System.out.println("month " + i + " maxDay is : " + maxDay);
//        }
//
//        getMonthData("2020-06-28");
//        getNextMonthData("2020-06-28");
//        getLastMonthData("2020-06-28");

//        getWeekDays("2020-07-08");
        getDays("2020-07-08", 10, 30);
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
     * @param dateStr
     * @return
     */
    public static List<String> getWeekDays(String dateStr) {
        List<String> days = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(sdf.parse(dateStr));
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if (week > 0) {
                for (int i = 1; i <= week; i++) {
                    calendar.add(Calendar.DATE, -1);
                    int tmp = calendar.get(Calendar.DAY_OF_MONTH);
                    days.add(0, String.valueOf(tmp));
                }
                calendar.add(Calendar.DATE, week);
            }
            days.add(String.valueOf(day));
            for (int i = 0, diff = 7 - week -1; i < diff; i++) {
                calendar.add(Calendar.DATE, 1);
                int tmp = calendar.get(Calendar.DAY_OF_MONTH);
                days.add(String.valueOf(tmp));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 获取 dateStr 之前preDays 和 之后 postDays 加起来的天数
     * @param dateStr
     * @param preDays
     * @param postDays
     * @return
     */
    public static List<String> getDays(String dateStr, int preDays, int postDays){
        List<String> days = new ArrayList<>();
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            calendar.setTime(sdf.parse(dateStr));
            for (int i = 0; i < preDays; i++){
                calendar.add(Calendar.DATE, -1); // 当前日期的前一天
                String date = sdf.format(calendar.getTime());
                days.add(0, date);
            }
            days.add(dateStr);
            calendar.add(Calendar.DATE, preDays);
            for (int i = 0; i < postDays; i++){
                calendar.add(Calendar.DATE, 1); // 当前日期的后一天
                String date = sdf.format(calendar.getTime());
                days.add(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

}
