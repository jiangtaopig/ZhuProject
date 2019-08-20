package daily.yiyuan.com.test_java;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

import daily.yiyuan.com.test_java.bean.Course;
import daily.yiyuan.com.test_java.bean.Student;
import daily.yiyuan.com.test_java.utils.CloneUtil;

public class TestMain2 {
    public static void main(String[] args) {
        Student s1 = new Student("zz", 23, "male");
        Course c1 = new Course();
        c1.setCourseName("math");
        c1.setCourseScore(99);
        s1.setCourse(c1);

        System.out.println(s1);

        //简单赋值是浅拷贝
        Student s2 = s1;
        s2.setName("ww");
        s2.setSex("female");

        System.out.println("s1 = " + s1);
        System.out.println("s2 = " + s2);

        //深拷贝
        Student s3 = CloneUtil.deepClone(s1);
        s3.setName("sssss");
        s3.setSex("mmale");

        System.out.println("s1 = " + s1);
        System.out.println("s3 = " + s3);

        time2Column("2019.06.04-10:15:00");
//        time2Column("2019.06.04-14:45:00");
        boolean flag = Pattern.matches("^/?.*", "z123");//开头是否有下划线?表示可以有或没有，.*表示匹配任意字符
        System.out.println("flag = "+flag);
    }

    private static void time2Column(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("date = " + date.getTime());
        date2String(date.getTime());
        getHour(date.getTime());
        setHourAndMinutes(date.getTime());

    }

    private static void date2String(long date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
        Date date1 = new Date(date);
        String time = sdf.format(date1);
        System.out.println("time = " + time);
    }

    private static void getHour(long time) {
        Date date = new Date(time);
        if (date == null) {
            return;
        }
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        System.out.println("hour = " + hour + ", minutes = " + minutes);
    }

    private static void setHourAndMinutes(long time){
        Date date = new Date(time);
        if (date == null) {
            return;
        }
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        System.out.println("year = "+year+", month = "+month+", day = "+day+", hour = "+hour+", minutes = "+minutes);

        calendar.set(year, month, day, 16, 30,0);
         year = calendar.get(Calendar.YEAR);
         int month1 = calendar.get(Calendar.MONTH);
         day = calendar.get(Calendar.DAY_OF_MONTH);
         hour = calendar.get(Calendar.HOUR_OF_DAY);
         minutes = calendar.get(Calendar.MINUTE);
        System.out.println("year = "+year+", month = "+month1+", day = "+day+", hour = "+hour+", minutes = "+minutes);
        date2String(calendar.getTimeInMillis());


        seconds2Date(date, 8*3600);
    }

    public static Date seconds2Date(Date date, int seconds){
        int hour = getHour(seconds);
        int minutes = getMinutes(seconds);
        Calendar calendar =  Calendar.getInstance(Locale.CHINESE);
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month, day, hour, minutes,0);
        return calendar.getTime();
    }

    public static int getHour(int time){
        return time / 3600;
    }

    public static int getMinutes(int time){
        return time % 3600 / 60;
    }

}
