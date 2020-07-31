package daily.yiyuan.com.test_java;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Creaeted by ${za.zhu.jiangtao}
 *  *  *  * on 2020/5/7
 */
public class TestCommon {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

    public static void main(String[] args) {
        testAtomic();
//        testSubList();
//        testListRemove();
//        testSimpleDateFormat();
        testSerializable();
    }

    /**
     * 为什么要禁止修改 serialVersionUID 的值
     */
    private static void testSerializable() {
        serialToFile("zjt");
        // 如果执行完 序列化后 即 将对象保存到文件后，再修改 User 的 serialVersionUID = 2L，那么下面的反序列化就会报错
        fileToObject("zjt");
    }

    private static void serialToFile(String fileName) {
        User user = new User();
        user.setName("zhujinagtao");
        user.setAge(31);

        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void fileToObject(String fileName) {
        File file = new File(fileName);
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            User user = (User) ois.readObject();
            System.out.println(user);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试 SimpleDateFormat 定义为 static ,在多线程中执行会导致结果不对
     */
    private static void testSimpleDateFormat() {
        Date date = new Date();
        String time = simpleDateFormat.format(date);
        System.out.println(time);

        ExecutorService service = new ThreadPoolExecutor(5, 200, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1024));
        // 使得 100 个线程执行完 再执行主线程的操作
        CountDownLatch countDownLatch = new CountDownLatch(100);
        Set<String> dates = Collections.synchronizedSet(new HashSet<>());
        for (int i = 0; i < 100; i++) {
            Calendar calendar = Calendar.getInstance();
            int finalI = i;
            service.execute(() -> {
                //每次循环时间都会增加一天
                //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss"); 注释1
//                synchronized (simpleDateFormat){ 注释2
                calendar.add(Calendar.DATE, finalI);
                String tmpDate = simpleDateFormat.format(calendar.getTime());
                dates.add(tmpDate);
                countDownLatch.countDown();
//                }
            });
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 如果是正常的话， dates 的 size 应该等于 100，而测试结果小于100，说明 simpleDateFormat 生成的时间有重复被 Set 过滤了
        System.out.println("size = " + dates.size());
        /**
         * 原因是 simpleDateFormat 的 format 方法会调用 属性 calendar 来保存时间，这时线程1调用 this.calendar.setTime(var1) 把时间设置为 2020-05-08 18-21-00
         * 没等线程1执行完剩下的操作，而线程2 this.calendar.setTime(var1) 把时间设置为 2020-05-09 18-21-00，这时候线程1 继续往下执行 获取的时间就是 2020-05-09 18-21-00了，
         * 导致程序结果出错
         */

        // 解决方法有3种：
        // 方法1 simpleDateFormat 定义在 注释1 的位置
        // 方法2 在注释2 的位置加锁
        // 方法3 使用ThreadLocal

        ThreadLocal<SimpleDateFormat> dateFormatThreadLocal = new ThreadLocal() {
            @Override
            protected Object initialValue() {// 初始值 在任意线程中都能获取到
                return new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
            }
        };

        // 用法 注意要把 dateFormatThreadLocal 定义在 线程池 的操作之前，然后 通过下面的方法 获取时间
        String d = dateFormatThreadLocal.get().format(new Date());
    }

    /**
     * 阿里不建议在 foreach 中进行 add remove 操作
     */
    private static void testListRemove() {

        List<String> list = new ArrayList<>();
        list.add("zhuiangtao");
        list.add("zhujiangtao");
        list.add("pig");
        list.add("ZHU");
        // foreach 其实调用的是 do while 和 Iterator 编译后的字节码如下:
        /**
         *  Iterator<String> iterator = list.iterator();
         *         do {
         *             if (!iterator.hasNext()) {
         *                 break;
         *             }
         *             String str = iterator.next();
         *             if (str.equals("zhujiangtao")){
         *                 list.remove(str);
         *             }
         *         } while (true);
         */

        for (String val : list) {
            if (val.equals("zhujiangtao")) {
                list.remove(val);
            }
        }
    }

    private static void testSubList() {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        List<String> subList = list.subList(1, 3);
        // 注意 对原集合元素进行add remove操作，会导致 subList 的 get add remove 迭代遍历发生 ConcurrentModificationException
        list.add("5");
        System.out.println("list = " + list);
        subList.get(0);
//        System.out.println("sublist = "+subList);
    }

    private static void testAtomic() {
        AtomicInteger count = new AtomicInteger();
        int val = count.addAndGet(2); // return update value 2
//       int val = count.getAndAdd(2); // return previous value 0
        count.get(); // return update value 2
        int res = count.getAndIncrement();
        System.out.println("count = " + val + ", res = "+res + ", get = "+count.get());
    }


    static class User implements Serializable {
        private static final long serialVersionUID = 1L;
        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "[ User name = " + this.name + ", age = " + this.age + " ]";
        }
    }
}
