package daily.yiyuan.com.test_java.multi_thread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/17
 */
public class MyTask {

    public static void main(String[] args) {
//        testFuture();

//        stopThread1();

        MyThread2 myThread2 = new MyThread2();
        myThread2.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("开始中断");
        myThread2.interrupt();


    }

    private static void stopThread1() {
        MyThread myThread = new MyThread();
        myThread.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("停止线程");
        myThread.setStop();
    }

    private static void testFuture() {
        FutureTask<Integer> futureTask = new FutureTask<>(new Worker());
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(futureTask);
        executorService.shutdown();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("主线程开始获取子线程的结果");

        try {
            System.out.println("isCanceled ? " + futureTask.isCancelled());
            if (!futureTask.isCancelled()) {//判断任务是否取消，如果任务已经完成了 isCancelled返回false
                futureTask.cancel(true);//true 表示是否强制取消正在执行的任务，如果任务已经完成，返回false
            }
            if (futureTask.get() != null) {//如果任务还没有完成，get()方法会阻塞住，知道完成任务，返回结果。
                System.out.println("task运行结果" + futureTask.get() + ", time = " + System.currentTimeMillis());
            } else {
                System.out.println("future.get()未获取到结果");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class Worker implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("执行子线程操作 time " + System.currentTimeMillis());
        Thread.sleep(5 * 1000);
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        return sum;
    }
}

/**
 * 停止线程的2种方式
 */
class MyThread extends Thread {
    private volatile boolean stop; //要声明为volatile

    public void setStop() {
        this.stop = true;
    }

    @Override
    public void run() {
        int i = 0;
        try {
            while (!stop) {
                Thread.sleep(500);
                System.out.println(" i = " + i++);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyThread2 extends Thread {
    @Override
    public void run() {
        int i = 0;
        try {
            while (!isInterrupted()) {
                Thread.sleep(500);
                System.out.println(" i = " + (i++)+", state = "+getState());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


