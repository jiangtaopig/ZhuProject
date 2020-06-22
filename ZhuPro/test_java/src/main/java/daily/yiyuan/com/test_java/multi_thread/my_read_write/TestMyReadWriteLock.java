package daily.yiyuan.com.test_java.multi_thread.my_read_write;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/19
 */
public class TestMyReadWriteLock {
    MyReentrantReadWriteLock readWriteLock = new MyReentrantReadWriteLock();
    Lock readLock = readWriteLock.readLock();
    Lock writeLock = readWriteLock.writeLock();

    public void read() {
        try {
            System.out.println(Thread.currentThread().getName() + " 获取读锁");
            readLock.lock();
            writeLock.lock();
            Thread.sleep(3 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 释放读锁");
            readLock.unlock();
        }
    }

    public void write() {
        try {
            writeLock.lock();
            System.out.println(Thread.currentThread().getName() + " 获取写锁");
            // 同一个线程在获取写锁后可以再获取读锁， 锁降级；反过来，同一个线程获取读锁后是不可以再获取写锁
            read();
            Thread.sleep(5 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(Thread.currentThread().getName() + " 释放写锁");
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        TestMyReadWriteLock testMyReadWriteLock = new TestMyReadWriteLock();
        new Thread("A") {
            @Override
            public void run() {
                testMyReadWriteLock.read();
            }
        }.start();

        sleep10ms();

        new Thread("B") {
            @Override
            public void run() {
                System.out.println("---------------------获取写锁-----------------------");
                testMyReadWriteLock.write();
            }
        }.start();

//        sleep10ms();
//
//        new Thread("C"){
//            @Override
//            public void run() {
//                testMyReadWriteLock.read();
//            }
//        }.start();
    }

    private static void sleep10ms(){
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
