package daily.yiyuan.com.test_java;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/18
 */
public class TestReflection {
    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("1");
        data.add("2");
        data.add("3");
        try {

            Class thread = Thread.currentThread().getClass();
            Object object = null;
            Field threadLocals = null;
//        ThreadLocalMap threadLocalMap = null;
            try {
                object = thread.newInstance();
                threadLocals = object.getClass().getDeclaredField("threadLocals");
                threadLocals.setAccessible(true);
//            threadLocalMap = (ThreadLocalMap) threadLocals.get(object);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }


            Class c = Class.forName("daily.yiyuan.com.test_java.School");
            Object o = c.newInstance();
            //获取 private 变量 teachers 的用法
            Field field = o.getClass().getDeclaredField("teachers");
            field.setAccessible(true);

            List<String> teas = (List<String>) field.get(o);

            field.set(o, data);
            List<String> teas2 = (List<String>) field.get(o);
            System.out.println(o);
            //获取 public 变量 name 的用法
            Field field2 = c.getField("name");
            field2.set(o, "zjt");

            if (o instanceof School) {
                System.out.println("name = " + ((School) o).name);
                System.out.println(((School) o).getTeachers());
            }

            String info = (String) invokePrivateMethod(o, "setInfo", new Class[]{String.class, int.class},
                    "shanghai university", 20);
            System.out.println("info = " + info);

            String info2 = (String) getPrivateFieldValue(o, "info");
            System.out.println("info2 = " + info2);

            String[] leaders = {"a", "b", "c"};

            Field field3 = o.getClass().getDeclaredField("leaders");
            field3.setAccessible(true);
            field3.set(o, leaders);

            if (o instanceof School) {
                String[] lead = ((School) o).getLeaders();
                for (String l : lead) {
                    System.out.println("leaders : " + l);
                }

            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    //访问私有方法
    private static Object invokePrivateMethod(Object instance, String name, Class[] classes, Object arg1, Object arg2) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = instance.getClass().getDeclaredMethod(name, classes);
        method.setAccessible(true);
        return method.invoke(instance, arg1, arg2);
    }

    //获取私有变量的值
    private static Object getPrivateFieldValue(Object instance, String filedName) throws NoSuchFieldException, IllegalAccessException {
        Field field = instance.getClass().getDeclaredField(filedName);
        field.setAccessible(true);
        return field.get(instance);
    }
}

class School {
    public String name;
    private List<String> teachers;
    private String info;
    private final String[] leaders;

    School() {
        leaders = new String[3];
    }

    public String[] getLeaders() {
        return leaders;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<String> teachers) {
        this.teachers = teachers;
    }

    private String setInfo(String schoolName, int older) {
        info = schoolName + "-" + older;
        return info;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (teachers != null && teachers.size() > 0) {
            for (String v : teachers) {
                stringBuilder.append(v);
            }
        }
        return stringBuilder.toString();
    }
}