package daily.yiyuan.com.test_java.multi_thread.lock;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/8/15
 */
public class ObjectLock {
    public static void main(String[] args) {

//        MyTask myTask = new MyTask();
//        MyThread1 thread1 = new MyThread1(myTask);
//        thread1.setName("MyThread1");
//        MyThread2 thread2 = new MyThread2(myTask);
//        thread1.setName("MyThread2");
//        thread1.start();
//        thread2.start();

//        User user = new User("test", 11);
//        MyTask2 myTask2 = new MyTask2(user);
//        TA ta = new TA(myTask2);
//        ta.setName("TA");
//
//        TB tb = new TB(myTask2);
//        tb.setName("TB");
//        ta.start();
//        try {
//            Thread.sleep(100);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
        printNum();

//        testSync();

    }

    private static void testSync() {
        TestService service = new TestService();
        ThA thA = new ThA(service);
        Thread A = new Thread(thA, "A");
        A.start();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ThB thB = new ThB(service);
        Thread B = new Thread(thB, "B");
        B.start();
    }

    private static void printNum() {
        final Num num = new Num();
        new Thread("AA") {
            @Override
            public void run() {
                while (num.count < 20) {
                    System.out.println("AA num.count = " + num.count);
                    synchronized (num) {
                        if (num.count % 2 != 0) {
                            System.out.println(Thread.currentThread().getName() + " >>> " + num.count);
                            num.count++;
                            num.notify();//或者 notifyAll(）
                        } else {
                            try {
                                System.out.println("AA wait" );
                                num.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

            }
        }.start();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread("BB") {
            @Override
            public void run() {

                while (num.count < 20) {
                    System.out.println("BB num.count = " + num.count);
                    synchronized (num) {
                        if (num.count % 2 == 0) {
                            System.out.println(Thread.currentThread().getName() + " >>> " + num.count);
                            num.count++;
                            num.notify();
                        } else {
                            try {
                                System.out.println("BB wait" );
                                num.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }.start();
    }
}

class MyTask2 {
    private User user;

    public MyTask2(User user) {
        this.user = user;
    }

    public void changeObjectLock() {
        synchronized (user) {
            System.out.println(Thread.currentThread().getName() + " enter" + ", time = " + System.currentTimeMillis());
            user.setName("xxxxx");//修改属性值
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " out");
        }
    }
}

class TA extends Thread {
    private MyTask2 myTask2;

    public TA(MyTask2 myTask2) {
        this.myTask2 = myTask2;
    }

    @Override
    public void run() {
        super.run();
        myTask2.changeObjectLock();
    }
}

class TB extends Thread {
    private MyTask2 myTask2;

    public TB(MyTask2 myTask2) {
        this.myTask2 = myTask2;
    }

    @Override
    public void run() {
        super.run();
        myTask2.changeObjectLock();
    }
}

class User {
    private String name;
    private int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

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
}

class Num {
    public int count;
}

class TestService{
    private boolean isContinueRun = true;
    public void runMethod(){
        Object lock = new Object();
        while (isContinueRun){
            synchronized (lock){// synchronized 可以将线程工作内存的变量与公共内存中的变量同步，类似于关键字 volatile

            }
        }
        System.out.println("停下来了");
    }

    public void stopMethod(){
        System.out.println("invoke  stopMethod");
        isContinueRun = false;
    }
}

class ThA implements Runnable{
    private TestService testService;
    public ThA(TestService service){
        this.testService = service;
    }
    @Override
    public void run() {
        testService.runMethod();
    }
}

class ThB implements Runnable{
    private TestService testService;
    public ThB(TestService service){
        this.testService = service;
    }
    @Override
    public void run() {
        testService.stopMethod();
    }
}