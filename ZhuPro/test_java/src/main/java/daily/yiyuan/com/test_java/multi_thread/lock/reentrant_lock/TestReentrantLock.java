package daily.yiyuan.com.test_java.multi_thread.lock.reentrant_lock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/9/16
 */
public class TestReentrantLock {
    public static void main(String[] args) {

        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("1");
        linkedList.remove();


        System.out.println("★☆");
        final MyService service = new MyService();
//        new Thread() {
//            @Override
//            public void run() {
//                int i = 0;
//                while (i < 10){
//                    service.producer();
//                    i++;
//                }
//            }
//        }.start();
//
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        new Thread() {
//            @Override
//            public void run() {
//                int i = 0;
//                while (i < 10){
//                    service.consumer();
//                    i++;
//                }
//            }
//        }.start();

        new Thread(){
            @Override
            public void run() {
                super.run();
                service.testConditionWait();
            }
        }.start();

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(){
            @Override
            public void run() {
                super.run();
                service.testConditionSignal();
            }
        }.start();
    }
}

class DataUtils{
    public static List<Integer> list = new ArrayList<>();
}

class MyService {
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition(); //condition必须在ReentrantLock同步代码块中执行
    private Condition conditionA = lock.newCondition();
    private Condition conditionB = lock.newCondition();

    public void testA() {
        lock.lock();
        System.out.println("testA thread >> " + Thread.currentThread().getName() + " start >>> " + System.currentTimeMillis());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("testA thread >> " + Thread.currentThread().getName() + " end >>> " + System.currentTimeMillis());
        lock.unlock();
    }

    public void testB() {
        lock.lock();
        System.out.println("testB thread >> " + Thread.currentThread().getName() + " start >>> " + System.currentTimeMillis());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("testB thread >> " + Thread.currentThread().getName() + " end >>> " + System.currentTimeMillis());
        lock.unlock();
    }

    public void testConditionWait() {
        try {
            lock.lock();
            System.out.println("testConditionWait thread >> " + Thread.currentThread().getName() + " start >>> " + System.currentTimeMillis());
            condition.await();
            System.out.println("testConditionWait thread >> " + Thread.currentThread().getName() + " end >>> " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }

    public void testConditionSignal() {
//        for (int i = 0; i < 5; i++) {
            try {
                lock.lock();
                System.out.println("testConditionSignal thread >> " + Thread.currentThread().getName() + " start >>> " + System.currentTimeMillis());
                condition.signal();
                Thread.sleep(2000);
                System.out.println("testConditionSignal thread >> " + Thread.currentThread().getName() + " end >>> " + System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
                System.out.println("testConditionSignal thread >> " + "unlock");
            }
//        }

    }

    //使用多个condition实现部分通知功能
    public void awaitA() {
        try {
            lock.lock();
            System.out.println("awaitA thread >> " + Thread.currentThread().getName() + " start >>> " + System.currentTimeMillis());
            conditionA.await();
            System.out.println("awaitA thread >> " + Thread.currentThread().getName() + " end >>> " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void awaitB() {
        try {
            lock.lock();
            System.out.println("awaitB thread >> " + Thread.currentThread().getName() + " start >>> " + System.currentTimeMillis());
            conditionB.await();
            System.out.println("awaitB thread >> " + Thread.currentThread().getName() + " end >>> " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signalAll_A() {
        try {
            lock.lock();
            System.out.println("signalAll_A thread >> " + Thread.currentThread().getName() + " start >>> " + System.currentTimeMillis());
            conditionA.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void signalAll_B() {
        try {
            lock.lock();
            System.out.println("signalAll_B thread  >> " + Thread.currentThread().getName() + " start >>> " + System.currentTimeMillis());
            conditionB.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 测试生产消费者
     */

    public void producer(){
        try {
            lock.lock();
            while (DataUtils.list.size() == 1){
                System.out.println("---producer await---");
                condition.await();
            }

            int val = new Random().nextInt(10);
            System.out.println("producer push val = "+val);
            DataUtils.list.add(val);
            condition.signal();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void consumer(){
        try {
            lock.lock();
            while (DataUtils.list.size() == 0){
                System.out.println("*** consumer await ***");
                condition.await();
            }
            System.out.println("consumer pop "+DataUtils.list.get(0));
            DataUtils.list.remove(0);
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
