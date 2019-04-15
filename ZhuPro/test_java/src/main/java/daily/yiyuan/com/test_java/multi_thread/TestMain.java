package daily.yiyuan.com.test_java.multi_thread;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingDeque;

public class TestMain {

    private static final int CAPACITY = 5;

    public static void main(String [] args){
//        testSleep();
//        testConsumerAndProducer();
//        testWait();
        threadLocal();
        int i = 3;
        long j = 5;
    }


    private static void threadLocal(){
        final ThreadLocal<String> threadLocal = new ThreadLocal<String>(){
            @Override
            protected String initialValue() {
                return "i an initial value";
            }
        };

        System.out.println("main: "+threadLocal.get());

        try {
            Thread.currentThread().sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(){
            @Override
            public void run() {
                System.out.println("thread: "+threadLocal.get());
            }
        }.start();
    }

    private static void testThreadLocal(){
        final ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("hello world");

        System.out.println("main: "+threadLocal.get());

        Thread thread = new Thread(){
            @Override
            public void run() {
                super.run();
                System.out.println("thread: "+threadLocal.get());
            }
        };

        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main: "+threadLocal.get());
    }

    /**
     * sleep 不会导致锁行为改变，如果当前的线程拥有锁，那么调用sleep后不会让线程释放锁, 而wait方法则会释放锁
     */
    private static void testSleep(){

        new Thread("线程1"){
            @Override
            public void run() {
                Utils.getInstance().showMsg();
            }
        }.start();

        new Thread("线程2"){
            @Override
            public void run() {
                Utils.getInstance().showMsg();
            }
        }.start();
    }

    /**
     * 调用某个对象的wait方法会让当前线程阻塞，并且当前线程必须必须拥有此对象的锁
     * 调用wait后会释放锁，进入线程的等待状态
     */
    private static void testWait(){

        new Thread("线程1"){
            @Override
            public void run() {
                Utils.getInstance().waitFor();
            }
        }.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主线程休眠1s后");

        new Thread("线程2"){
            @Override
            public void run() {
                Utils.getInstance().notify_For();
            }
        }.start();

    }

    private static void testConsumerAndProducer(){
        LinkedBlockingDeque<Integer> blockingQueue = new LinkedBlockingDeque<Integer>(CAPACITY);
        //put()方法：类似于我们上面的生产者线程，容量达到最大时，自动阻塞。
        //take()方法：类似于我们上面的消费者线程，容量为0时，自动阻塞。

        Thread producer1 = new Producer("P-1", blockingQueue, CAPACITY);
        Thread producer2 = new Producer("P-2", blockingQueue, CAPACITY);
        Thread consumer1 = new Consumer("C1", blockingQueue, CAPACITY);
        Thread consumer2 = new Consumer("C2", blockingQueue, CAPACITY);
        Thread consumer3 = new Consumer("C3", blockingQueue, CAPACITY);

        producer1.start();
        producer2.start();
        consumer1.start();
        consumer2.start();
        consumer3.start();
    }

    /**
     * 生产者
     */
    public static class Producer extends Thread{
        private LinkedBlockingDeque<Integer> blockingQueue;
        String name;
        int maxSize;
        int i = 0;

        public Producer(String name, LinkedBlockingDeque<Integer> queue, int maxSize){
            super(name);
            this.name = name;
            this.blockingQueue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run(){
            while(true){
                try {
                    blockingQueue.put(i);
                    System.out.println("[" + name + "] Producing value : +" + i);
                    i++;

                    //暂停最多2秒
                    Thread.sleep(new Random().nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }


    /**
     * 消费者
     */
    public static class Consumer extends Thread{
        private LinkedBlockingDeque<Integer> blockingQueue;
        String name;
        int maxSize;

        public Consumer(String name, LinkedBlockingDeque<Integer> queue, int maxSize){
            super(name);
            this.name = name;
            this.blockingQueue = queue;
            this.maxSize = maxSize;
        }

        @Override
        public void run(){
            while(true){
                try {
                    int x = blockingQueue.take();
                    System.out.println("[" + name + "] Consuming : " + x);

                    //暂停最多2秒
                    Thread.sleep(new Random().nextInt(2000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


