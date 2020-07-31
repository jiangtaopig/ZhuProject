package daily.yiyuan.com.test_java.multi_thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/30
 */
public class TestCondition {

    public static void main(String[] args) {
        Service service = new Service();
        new Thread("A") {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    service.get(i);
                }
            }
        }.start();

        new Thread("B") {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    service.set(i);
                }
            }
        }.start();

    }


}

class Service {
    private ReentrantLock reentrantLock = new ReentrantLock();
    private Condition condition = reentrantLock.newCondition();
    private boolean hasValue;

    public void set(int i) {
        try {
            reentrantLock.lock();
            System.out.println(Thread.currentThread().getName()+" set  - "+i);
            while (hasValue) {
                condition.await(); // 也会释放锁
            }
            System.out.println("★"); //☆
            hasValue = true;
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
            System.out.println(Thread.currentThread().getName()+"释放了锁");
        }
    }

    public void get(int i) {
        try {
            reentrantLock.lock();
            System.out.println(Thread.currentThread().getName()+" get  - "+i);
            while (!hasValue) {
                condition.await();
            }
            System.out.println("☆");
            hasValue = false;
            condition.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reentrantLock.unlock();
            System.out.println(Thread.currentThread().getName()+"释放了锁");
        }
    }
}
