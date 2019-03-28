package daily.yiyuan.com.test_java.multi_thread;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 可以实现类似计数器的功能；比方说有一个任务A，它要等其他4个任务执行完才能执行，此时就可以用CountDownLatch 来实现
 */
public class TestCountDownLatch {

    public static void main(String [] args){
        final CountDownLatch downLatch = new CountDownLatch(2);

        new Thread(){
            @Override
            public void run() {
                System.out.println("开始执行子任务"+Thread.currentThread().getName());
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("子任务执行结束"+Thread.currentThread().getName());
                downLatch.countDown();
            }
        }.start();

        new Thread(){
            @Override
            public void run() {
                System.out.println("开始执行子任务"+Thread.currentThread().getName());
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("子任务执行结束"+Thread.currentThread().getName());
                downLatch.countDown();
            }
        }.start();

        System.out.println("等待子任务执行完成...");
        try {
            downLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2个子任务执行完成，继续执行主线程任务");

    }
}
