package daily.yiyuan.com.test_java.multi_thread;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2021/2/25
 * 场景
 * 1.早上；2.测试人员、产品经理、开发人员陆续的来公司上班；3.产品经理规划新需求；4.开发人员开发新需求功能；5.测试人员测试新功能。
 * <p>
 * 规划需求，开发需求新功能，测试新功能是一个有顺序的，我们把thread1看做产品经理，thread2看做开发人员，thread3看做测试人员。
 */
class TestReentrantLock {
    static boolean isT1Run = false;
    static boolean isT2Run = false;

    public static void main(String[] args) {

//        testConsumerAndProducer();
        print2NumAndOneChar(new char[]{'a', 'b', 'c'}, new int[]{1, 2, 3, 4, 5, 6});


    }

    private static void testConsumerAndProducer() {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition1 = reentrantLock.newCondition();
        Condition condition2 = reentrantLock.newCondition();

        Thread thread1 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    reentrantLock.lock();
                    System.out.println("产品经理来上班了。。。。");
                    System.out.println("产品经理提出新需求");
                    isT1Run = true;
                    condition1.signal();
                } finally {
                    reentrantLock.unlock();
                }
            }
        };

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    reentrantLock.lock();
                    System.out.println("开发人员来上班啦。。。。。");
                    if (!isT1Run) {
                        System.out.println("还没有新需求，休息下");
                        condition1.await();
                    }
                    System.out.println("开发人员开发新需求");
                    isT2Run = true;
                    condition2.signal();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }

            }
        };

        Thread thread3 = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    reentrantLock.lock();
                    System.out.println("测试人员来上班啦。。。");
                    if (!isT2Run) {
                        System.out.println("还没有任务，休息会");
                        condition2.await();
                    }
                    System.out.println("测试人员开始测试新需求");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        };

        System.out.println("早上。。。");
        thread3.start();
        thread2.start();
        thread1.start();
    }

    /**
     * 用2个线程，每输出2个数字再输出1个字符
     */
    private static void print2NumAndOneChar(char[] chars, int[] nums) {
        AtomicBoolean printNum = new AtomicBoolean(true);
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition numCondition = reentrantLock.newCondition();
        Condition charCondition = reentrantLock.newCondition();
        AtomicInteger cmt = new AtomicInteger(0);
        new Thread() {
            @Override
            public void run() {
                try {
                    reentrantLock.lock();
                    for (int i = 0; i < nums.length; i++) {
                        while (!printNum.get()) {
                            numCondition.await();
                        }
                        System.out.println("---  : " + nums[i]);
                        cmt.incrementAndGet();
                        if (cmt.get() == 2) {
                            printNum.set(false);
                            cmt.set(0);
                            charCondition.signalAll();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    reentrantLock.lock();
                    for (int i = 0; i < chars.length; i++) {
                        while (printNum.get())
                            charCondition.await();
                        System.out.println("****  : " + chars[i]);
                        printNum.set(true);
                        numCondition.signalAll();
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    reentrantLock.unlock();
                }
            }
        }.start();

    }


}
