package daily.yiyuan.com.test_java.multi_thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池的源码解析示例
 */
public class TestPoolExecutor {

    public static void main(String[] args){
        ExecutorService executorService = new ThreadPoolExecutor(2, 4, 10, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
        executorService.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
        int COUNT_BITS = Integer.SIZE - 3;
        int RUNNING    = -1 << COUNT_BITS;
        System.out.println("RUNNING = "+RUNNING);
        System.out.println(RUNNING | 0);
        int CAPACITY   = (1 << COUNT_BITS) - 1;
        System.out.println("CAPACITY = "+CAPACITY);
        System.out.println(RUNNING | 0 & CAPACITY);
    }
}
