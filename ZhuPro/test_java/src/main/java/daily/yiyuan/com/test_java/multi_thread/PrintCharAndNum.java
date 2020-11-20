package daily.yiyuan.com.test_java.multi_thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/11/19
 */
public class PrintCharAndNum {

    public static void main(String[] args) throws InterruptedException {

//        synchronizeMethod();
        semaphoreMethod();

        Map<Integer, Integer> map = new HashMap<>();
        map.getOrDefault(1, 0);
    }


    private static void semaphoreMethod() {
        // Semaphore 的 release 会立即唤醒阻塞的线程， 且 release 可以 先于 acquire执行; LockSupport.unpark 也是可以先于 park执行

        Semaphore charS = new Semaphore(1);
        Semaphore numS = new Semaphore(0);


        Thread threadA = new Thread() {
            @Override
            public void run() {
                String[] strs = {"A", "B", "C", "D"};
                for (int i = 0; i < strs.length; i++) {
                    System.out.println("threadA" + " , i = " + i);
                    try {
                        charS.acquire();
                        System.out.println("----" + strs[i] + "-------");
                        numS.release();
                        Thread.sleep(2_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread threadB = new Thread() {
            @Override
            public void run() {
                String[] strs = {"1", "2", "3", "4"};
                for (int i = 0; i < strs.length; i++) {
                    try {
                        System.out.println("threadB" + " , i = " + i);
                        numS.acquire();
                        System.out.println("----" + strs[i] + "-------");

                        charS.release();
                        Thread.sleep(2_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        threadA.start();
        threadB.start();

    }

    private static void synchronizeMethod() throws InterruptedException {
        Object lock = new Object();
        AtomicBoolean printStr = new AtomicBoolean(true);

        Thread threadA = new Thread("Thread_A") {
            @Override
            public void run() {
                String[] strs = {"A", "B", "C", "D"};
                synchronized (lock) {
                    for (int i = 0; i < strs.length; i++) {
                        while (!printStr.get()) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("-----" + strs[i] + "----------");
                        printStr.set(false);
                        lock.notify();
                    }
                }
            }
        };

        Thread threadB = new Thread("Thread_B") {
            @Override
            public void run() {

                String[] vals = {"1", "2", "3", "4"};
                for (int i = 0; i < vals.length; i++) {
                    synchronized (lock) {
                        while (printStr.get()) {
                            try {
                                lock.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.println("-----" + vals[i] + "----------");
                        printStr.set(true);
                        lock.notify();
                    }
                }
            }
        };

        threadA.start();
        Thread.sleep(10);
        threadB.start();
    }
}
