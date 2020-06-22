package daily.yiyuan.com.test_java.multi_thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/17
 */
public class TestReentrantReadWriteLock {
    ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    Lock readLock = readWriteLock.readLock();
    Lock writeLock = readWriteLock.writeLock();

    public void read() {
        try {
            readLock.lock();
            System.out.println(Thread.currentThread().getName() + " 获取读锁");
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
        TestReentrantReadWriteLock testReentrantReadWriteLock = new TestReentrantReadWriteLock();

        /**
         * 多个线程获取读锁 是异步的，即 A 和 B 都能够同时获取到读锁，
         * 读锁和写锁是同步的，即 写锁要等到读锁释放后才能获取到锁；读锁要等写锁释放锁后才能获取读锁
         *
         * 注意： 同一个线程在获取写锁后，在写锁还没释放之前依然可以获取读锁，这就是锁降级
         */

        new Thread("C"){
            @Override
            public void run() {
                testReentrantReadWriteLock.write();
            }
        }.start();

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread("A"){
            @Override
            public void run() {
                testReentrantReadWriteLock.read();
            }
        }.start();

        new Thread("B"){
            @Override
            public void run() {
                testReentrantReadWriteLock.read();
            }
        }.start();

        new Thread("C"){
            @Override
            public void run() {
                testReentrantReadWriteLock.write();
            }
        }.start();
    }
}
