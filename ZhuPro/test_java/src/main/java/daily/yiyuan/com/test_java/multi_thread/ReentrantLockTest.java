package daily.yiyuan.com.test_java.multi_thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/18
 */
public class ReentrantLockTest {
    public static void main(String[] args) {

        testLock();

        MyService1 myService1 = new MyService1();
        new WaitThread(myService1, "A").start();
        new WaitThread(myService1, "B").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        myService1.signalAll();
//        new SignalThread(myService1).start();
    }

    private static void testLock() {
        Service1 service1 = new Service1();
        new MyThread1(service1).start();
        new MyThread1(service1).start();
        new MyThread2(service1).start();
    }


}

class Service1 {
    private ReentrantLock lock = new ReentrantLock();

    public void A() {
        lock.lock();
        for (int i = 0; i < 3; i++) {
            System.out.println("线程 " + Thread.currentThread().getName()+"执行方法A" + " : " + i);
        }
        lock.unlock();
    }

    public void B(){
        lock.lock();
        System.out.println("线程 " + Thread.currentThread().getName()+"执行方法B");
        lock.unlock();
    }
}

class MyThread1 extends Thread {
    Service1 service1;

    public MyThread1(Service1 service1) {
        this.service1 = service1;
    }

    @Override
    public void run() {
        service1.A();
    }
}

class MyThread2 extends Thread {
    Service1 service1;

    public MyThread2(Service1 service1) {
        this.service1 = service1;
    }

    @Override
    public void run() {
        service1.B();
    }
}

//.....................................使用Condition........................
//使用wait 和 notify 必须要在同步代码块中，而使用 Condition 的 await 和 signal也需要先调用 ReentrantLock 的 lock方法

class MyService1{
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void await(){
        try {
            lock.lock();
            System.out.println("开始 wait");
            condition.await();
            System.out.println("wait 结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
            System.out.println("await unlock");
        }
    }

    public void signal(){
        try {
            lock.lock();
            System.out.println("开始 sigal");
            condition.signal();
            Thread.sleep(3 * 1000); //不是调用signal后，await的线程就立马执行，要等到lock代码块执行完后才会执行
            System.out.println("sigal 结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("signal unlock");
        }
    }

    public void awaitA(){
        try {
            lock.lock();
            System.out.println("线程 ： "+Thread.currentThread().getName()+" 开始 wait");
            condition.await();
            System.out.println("线程 ： "+Thread.currentThread().getName()+" wait 结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
            System.out.println("线程 ： "+Thread.currentThread().getName()+ " await unlock");
        }
    }

    public void awaitB(){
        try {
            lock.lock();
            System.out.println("线程 ： "+Thread.currentThread().getName()+" 开始 wait");
            condition.await();
            System.out.println("线程 ： "+Thread.currentThread().getName()+" wait 结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
            System.out.println("线程 ： "+Thread.currentThread().getName()+ " await unlock");
        }
    }

    public void signalAll(){
        try {
            lock.lock();
            System.out.println("开始 signalAll");
            condition.signalAll();
            Thread.sleep(3 * 1000); //不是调用signal后，await的线程就立马执行，要等到lock代码块执行完后才会执行
            System.out.println("signalAll 结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("signalAll unlock");
        }
    }
}

class WaitThread extends Thread{
    private MyService1 myService1;
    private String type;

    public WaitThread(MyService1 service1, String type){
        myService1 = service1;
        this.type = type;
    }

    @Override
    public void run() {
        if ("A".equals(type)){
            myService1.awaitA();
        }else if ("B".equals(type)){
            myService1.awaitB();
        }else {
            myService1.await();
        }
    }
}

class SignalThread extends Thread{

    private MyService1 myService1;
    public SignalThread(MyService1 service1){
        myService1 = service1;
    }

    @Override
    public void run() {
        myService1.signal();
    }
}
