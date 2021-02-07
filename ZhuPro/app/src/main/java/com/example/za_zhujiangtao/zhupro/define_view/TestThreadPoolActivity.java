package com.example.za_zhujiangtao.zhupro.define_view;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Button;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.MainActivity;
import com.example.za_zhujiangtao.zhupro.NotificationActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.ThreadPoolExecutor;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import butterknife.BindView;

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

//        Executors.newCachedThreadPool();
//        Executors.newFixedThreadPool(2);
//        Executors.newScheduledThreadPool(2);
//        Executors.newSingleThreadExecutor();

        new Handler().postDelayed(() -> {

        }, 10_000);

        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();
        boolean f = synchronousQueue.offer("a");
        Log.e("xxx", "f = " + f);

        mTestThreadPool.setOnClickListener(v -> {
            ThreadPoolExecutor executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 100, TimeUnit.SECONDS, new SynchronousQueue<>());
            for (int i = 0; i < 5; i++) {
                int a = i;
                Runnable runnable = () -> {
                    Log.e("gggg", "任务" + (a + 1) + "开始");
                    try {
                        Thread.sleep(3_000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e("gggg", "任务" + (a + 1) + "结束");
                };
                executorService.execute(runnable);
            }
            executorService.shutdown();
//            executorService.setMaximumPoolSize(10);
//            executorService.setCorePoolSize(10);


        });

        mTestAwait.setOnClickListener(v -> {
            new Thread("---zhuThread1---") {
                @Override
                public void run() {
                    try {
                        Log.e("----zjt----", Thread.currentThread().getName() + " start");
                        reentrantLock.lock();
                        Log.e("----zjt----", Thread.currentThread().getName() + " lock and wait");
                        condition.await();
                        Log.e("----zjt----", Thread.currentThread().getName() + " wake by signal");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        Log.e("----zjt----", Thread.currentThread().getName() + " release lock");
                        reentrantLock.unlock();
                    }
                }
            }.start();


        });


        mTestSignal.setOnClickListener(v -> {
//            new Thread("---zhuThread2---") {
//                @Override
//                public void run() {
//                    try {
//                        Log.e("----zjt----", Thread.currentThread().getName() + " start");
//                        reentrantLock.lock();
//                        Log.e("----zjt----", Thread.currentThread().getName() + " lock and signal");
//                        condition.signal();
//                    } finally {
//                        Log.e("----zjt----", Thread.currentThread().getName() + " release lock");
//                        reentrantLock.unlock();
//                    }
//                }
//            }.start();

//            sendNotification();

            getTopActivity(this);

        });
    }

    private void sendNotification() {
        Intent intent = new Intent(TestThreadPoolActivity.this, NotificationActivity.class);
        PendingIntent pi = PendingIntent.getActivity(TestThreadPoolActivity.this, 0, intent, 0);
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(TestThreadPoolActivity.this)
                .setContentTitle("这是测试通知标题")  //设置标题
                .setContentText("这是测试通知内容") //设置内容
                .setWhen(System.currentTimeMillis())  //设置时间
                .setSmallIcon(R.mipmap.ic_launcher)  //设置小图标  只能使用alpha图层的图片进行设置
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))   //设置大图标
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build();
        manager.notify(1, notification);

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
        Log.e("test thread pool", "c = " + c + ", c2 = " + c2 + ", rs = " + rs);
        ;

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


    public String getTopActivity(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        Log.d("测试", "pkg:" + cn.getPackageName());//包名
        Log.d("测试", "cls:" + cn.getClassName());//包名加类名
        String[] res = cn.getClassName().split("\\.");
        String className = res[res.length - 1];
        Log.e("测试", "cls:" + className);//包名加类名
        return cn.getClassName();
    }
}
