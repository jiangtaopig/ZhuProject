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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

        ExecutorService executorService = new ThreadPoolExecutor(2, 4, 10, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());

        Log.e("test thread pool", "ctl = " + ctl + ", RUNNING = " + RUNNING + ", SHUTDOWN = " + SHUTDOWN
                + ", STOP = " + STOP + ", TIDYING = " + TIDYING + ", TERMINATED = " + TERMINATED + ", CAPACITY = " + CAPACITY);

        Observable.just(new int[] {1, 2, 3})
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<int[]>() {
                    @Override
                    public void call(int[] ints) {

                    }
                });

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
        thread.start();


//        mTestThreadPool.setOnClickListener(v -> {
//
////            executorService.shutdown();
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Log.e("test thread pool", "---------- 执行run  ---------");
//                }
//            });
//
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Log.e("test thread pool", "---------- 执行run  ---------");
//                }
//            });
//
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        Thread.sleep(3000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Log.e("test thread pool", "---------- 执行run  ---------");
//                }
//            });
//        });

        mTestThreadPool.setOnClickListener(v -> {
            SparseArray<String> array = new SparseArray<>();
            array.put(4, "m");
            array.put(3, "b");
            array.put(5, "c");

            ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
            map.put("1", "a");

            Map<String, String> map1 = new HashMap<>();


        });
    }

    private void testForBreak(){
        retry:

        for (; ;){
            int i = new Random().nextInt(10)+1;
            Log.e("xxx", "i = "+i);
            if (i == 5){
                Log.e("xxx", "break");
                break retry; //break 跳出循环
            }
        }
    }
}
