package daily.yiyuan.com.test_java.multi_thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/12/18
 */
class TestPool {

    public static void main(String[] args) {

      String res =  Integer.toBinaryString(-536870912);
      System.out.println("res = "+res +", x = "+Integer.toBinaryString(536870911));

        ThreadPoolExecutor executorService = new ThreadPoolExecutor(2, 2, 100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(2));
        Runnable runnable1 = () -> {
            System.out.println("任务1开始  threadNmae = " + Thread.currentThread().getName());
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务1结束 threadNmae = " + Thread.currentThread().getName());
        };

        Runnable runnable2 = () -> {
            System.out.println("异常任务2  start threadNmae = " + Thread.currentThread().getName());
            throw new RuntimeException("我又出bug啦，哈哈哈!!!!");
        };

        Runnable runnable3 = () -> {
            System.out.println("任务3开始  threadNmae = " + Thread.currentThread().getName());
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务3结束 threadNmae = " + Thread.currentThread().getName());
        };
        Runnable runnable4 = () -> {
            System.out.println("任务4开始  threadNmae = " + Thread.currentThread().getName());
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务4结束 threadNmae = " + Thread.currentThread().getName());
        };

        executorService.execute(runnable1);
        executorService.execute(runnable2);
        executorService.execute(runnable3);
        executorService.execute(runnable4);
        executorService.shutdown();
    }

}
