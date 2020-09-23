package daily.yiyuan.com.test_java.multi_thread.future;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/9/22
 */
public class TestFutureTask {

    public static void main(String[] args) {

        ExecutorService service = Executors.newSingleThreadExecutor();
//        FutureTask<Integer> futureTask = new FutureTask<>(new Workers());

        FutureTask<Long> futureTask = new FutureTask<>(new Task2(1000000003));
        service.submit(futureTask);

        service.shutdown();// 关闭线程池
        cancelTask(futureTask, 1000);

        try {
            futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (CancellationException e) {
            System.out.println("任务被取消: time = " + System.currentTimeMillis());
        }

    }


    public static void cancelTask(FutureTask<Long> futureTask, int delayTime) {
        Runnable runnable = () -> {
            try {
                Thread.sleep(delayTime); // Thread.sleep 方法可以对中断作出响应，即 对 Thread.interrupted 的方法作出响应
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("取消正在执行的任务 time = " + System.currentTimeMillis());
            futureTask.cancel(true);
        };
        new Thread(runnable).start();
    }


}


class Workers implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("执行子线程操作 time " + System.currentTimeMillis());
        Thread.sleep(5 * 1000);
        int sum = 0;
        for (int i = 0; i < 100; i++) {
            sum += i;
        }
        System.out.println("sum = " + sum);
        return sum;
    }
}

class Task2 implements Callable<Long> {

    private final long num;

    public Task2(long num) {
        this.num = num;
    }

    @Override
    public Long call() throws Exception {
        long sum = 0;
        System.out.println("开始运算 time =" + System.currentTimeMillis());
        for (int i = 1; i < num; i++) {
            if (Thread.currentThread().isInterrupted()){ // 如果不在这里 加入中断判断且return，那么计算任务会继续执行下去； 上面的例子 因为  Thread.sleep 方法可以对中断作出响应，所以直接结束任务
                System.out.println("我被取消了 time >>> "+System.currentTimeMillis());
                return sum;
            }
            if (i % 2 == 0) {
                sum += sum;
            }
        }
        System.out.println("...sum =" + sum + ", time =" + System.currentTimeMillis());
        return sum;
    }
}

