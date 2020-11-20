package com.example.za_zhujiangtao.zhupro.define_view;

import android.util.Log;
import android.util.SparseArray;
import android.widget.Button;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.ThreadPoolExecutor;
import com.example.za_zhujiangtao.zhupro.api.DataBase;
import com.example.za_zhujiangtao.zhupro.http.test.TestApi;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import butterknife.BindView;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/16
 */
public class TestThreadPoolActivity extends BaseActivity {

    @BindView(R.id.test_thread_pool)
    Button mTestThreadPool;

    @BindView(R.id.test_await)
    Button mTestAwait;

    @BindView(R.id.test_signal)
    Button mTestSignal;

    ReentrantLock reentrantLock = new ReentrantLock();
    Condition condition = reentrantLock.newCondition();

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3; //32 -3
    private static final int CAPACITY = (1 << COUNT_BITS) - 1; // 1左移29位 -1 , 2^29 - 1 = 536870911

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS; // -1 * 2^29 =  -536870912
    private static final int SHUTDOWN = 0 << COUNT_BITS; // 0
    private static final int STOP = 1 << COUNT_BITS; // 1 * 2^29 =  536870912
    private static final int TIDYING = 2 << COUNT_BITS; // 2 * 2^29 = 1073741824
    private static final int TERMINATED = 3 << COUNT_BITS;// 3 * 2^29 = 1610612736

    @Override
    protected int layoutId() {
        return R.layout.activity_thread_pool_layout;
    }

    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }


    private boolean compareAndIncrementWorkerCount(int expect) {
        return ctl.compareAndSet(expect, expect + 1);
    }


    @Override
    protected void onInitLogic() {


        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 100, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1));

        Log.e("test thread pool", "ctl = " + ctl + ", RUNNING = " + RUNNING + ", SHUTDOWN = " + SHUTDOWN
                + ", STOP = " + STOP + ", TIDYING = " + TIDYING + ", TERMINATED = " + TERMINATED + ", CAPACITY = " + CAPACITY);
        Log.e("test thread pool", "---------------------------------------------------------------------------------------------------");

        Runnable runnable1 = () -> {
            Log.e("test thread pool", "r1 start + threadNmae = " + Thread.currentThread().getName());
            long i = 0;
           while (true){
//               Log.e("test thread pool", "r1 start i = "+(i++));
               if (Thread.currentThread().isInterrupted()){
                   Log.e("test thread pool", "r1 threadNmae = " + Thread.currentThread().getName()+", hasInterrupted");
                   break;
               }
           }
            Log.e("test thread pool", "r1 end + threadNmae = " + Thread.currentThread().getName());
        };

        Runnable runnable2 = () -> {
            Log.e("test thread pool", "r2 start + threadNmae = " + Thread.currentThread().getName());
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.e("test thread pool", "r2 end+ threadNmae = " + Thread.currentThread().getName());
        };

        Runnable runnable3 = () -> {
            Log.e("test thread pool", "r3 start+ threadNmae = " + Thread.currentThread().getName());
            try {
                Thread.sleep(3_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            while (true){
//                Log.e("test thread pool", "r3  " );
//            }
            Log.e("test thread pool", "r3 end+ threadNmae = " + Thread.currentThread().getName());
        };


        mTestThreadPool.setOnClickListener(v -> {
//            testState();
            executorService.execute(runnable1);
//            executorService.execute(runnable2);
//            executorService.execute(runnable3);
//            executorService.shutdown();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            executorService.shutdownNow();
        });

        mTestAwait.setOnClickListener(v -> {

            new Thread("---zhuThread1---"){
                @Override
                public void run() {
                    try {
                        Log.e("----zjt----", Thread.currentThread().getName()+" start");
                        reentrantLock.lock();
                        Log.e("----zjt----", Thread.currentThread().getName()+" lock and wait");
                        condition.await();
                        Log.e("----zjt----", Thread.currentThread().getName()+" wake by signal");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Log.e("----zjt----", Thread.currentThread().getName()+" release lock");
                        reentrantLock.unlock();
                    }
                }
            }.start();

        });


        mTestSignal.setOnClickListener(v -> {

                    new Thread("---zhuThread2---"){
                        @Override
                        public void run() {
                            try {
                                Log.e("----zjt----", Thread.currentThread().getName()+" start");
                                reentrantLock.lock();
                                Log.e("----zjt----", Thread.currentThread().getName()+" lock and signal");
                                condition.signal();
                            } finally {
                                Log.e("----zjt----", Thread.currentThread().getName()+" release lock");
                                reentrantLock.unlock();
                            }
                        }
                    }.start();
    });


    }


    /**
     * 测试线程池的状态
     */
    private void testState() {
        int c = ctl.get();
        int threadCnt = workerCountOf(c);
        Log.e("test thread pool", "threadCnt = " + threadCnt);

        int c2 = ctl.get();
        int rs = runStateOf(c2);
        Log.e("test thread pool", "c = " + c + ", c2 = " + c2 + ", rs = " + rs);;

        if (compareAndIncrementWorkerCount(c2)) {
            int c3 = ctl.get();
            Log.e("test thread pool", "c3 = " + c3);
        }

        int rs2 = runStateOf(ctl.get());
        Log.e("test thread pool", "rs2 = " + rs2);
    }

    private void testArrayBlockingQueue() {

        // ArrayBlockingQueue 是不会主动扩容的，所以下面定义的 queue 长度是2，当尝试加入4条数据的时候就会报错 throw new IllegalStateException("Queue full");
        ArrayBlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(2);
        boolean b1 = blockingQueue.add("1");
        boolean b2 = blockingQueue.add("2");
        boolean b3 = blockingQueue.add("3");
        boolean b4 = blockingQueue.add("4");
        Log.e("test thread pool", "b1 = " + b1 + ", b2 =" + b2 + ", b3 = " + b3 + ", b4 = " + b4);
    }


    private void testReentrantLock(){

        Thread thread = new Thread("aa"){
            @Override
            public void run() {

            }
        };

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void testForBreak() {
        retry:
        for (; ; ) {
            int i = new Random().nextInt(10) + 1;
            Log.e("test thread pool", "i = " + i);
            if (i == 5) {
                Log.e("test thread pool", "break");
                break retry; //break 跳出循环,执行循环外面的逻辑
            }
        }
        Log.e("test thread pool", "跳出了循环");
    }
}
