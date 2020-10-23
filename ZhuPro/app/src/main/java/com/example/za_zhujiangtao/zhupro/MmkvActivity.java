package com.example.za_zhujiangtao.zhupro;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.za_zhujiangtao.zhupro.utils.SharePreferenceUtils;
import com.tencent.mmkv.MMKV;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/9/24
 */
public class MmkvActivity extends BaseActivity {

    private MMKV mmkv;

    @Override
    protected int layoutId() {
        return R.layout.activity_mmkv_layout;
    }

    @Override
    protected void onInitLogic() {
        mmkv = MMKV.mmkvWithID("zhujiangtao", MMKV.MULTI_PROCESS_MODE); // 多进程访问
    }

    public void add(View view) {
        boolean success = mmkv.encode("Name", "朱江涛");
        mmkv.encode("Age", 23);
        mmkv.encode("IsMale", true);
        if (success) {
            Toast.makeText(this, "success", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "failed", Toast.LENGTH_LONG).show();
        }

    }

    public void query(View view) {
        String name = mmkv.decodeString("Name", "no name");
        int age = mmkv.decodeInt("Age", -1);
        boolean male = mmkv.decodeBool("IsMale");
        Toast.makeText(this, name + ", age = " + age + ", male = " + male, Toast.LENGTH_LONG).show();
    }

    public void delete(View view) {
        if (mmkv.containsKey("IsMale"))
            mmkv.removeValueForKey("IsMale");
    }

    public void addBySp(View view) {
        SharePreferenceUtils.putString(this, "phone", "18817593333");
        SharePreferenceUtils.putString(this, "password", "1234567l");
    }

    public void queryFromSp(View view) {
        String phone = SharePreferenceUtils.getString(this, "phone");
        String pwd = SharePreferenceUtils.getString(this, "password");
        Toast.makeText(this, "phone = " + phone + ", pwd = " + pwd, Toast.LENGTH_LONG).show();
    }

    public void importFromSP(View view) {
        MMKV preferences = MMKV.mmkvWithID(SharePreferenceUtils.DATA_NAME);
        // 迁移旧数据
        {
            SharedPreferences old_man = getSharedPreferences(SharePreferenceUtils.DATA_NAME, MODE_PRIVATE);
            preferences.importFromSharedPreferences(old_man);
            old_man.edit().clear().commit();
        }
        String phone = preferences.decodeString("phone");
        Toast.makeText(this, "phone = " + phone, Toast.LENGTH_LONG).show();

    }

    public void testThread(View view) {
//        testCountDownLatch();
//        testInterrupt();
        ReentrantLock reentrantLock = new ReentrantLock();
        reentrantLock.tryLock();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, () -> {
            Log.e("cyclicBarrier", "2个线程都到执行了await方法了 >>>> "+Thread.currentThread().getName());
        });

         new Thread("A"){
            @Override
            public void run() {
                Log.e("cyclicBarrier", "ThreadA start wait");
                try {
                    cyclicBarrier.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("cyclicBarrier", "ThreadA end wait");
            }
        }.start();

        new Thread("B"){
            @Override
            public void run() {
                Log.e("cyclicBarrier", "ThreadB start wait");
                try {
                    cyclicBarrier.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("cyclicBarrier", "ThreadB end wait");
            }
        }.start();

        try {
            Thread.sleep(20);
            Log.e("cyclicBarrier", "再次执行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread("C"){
            @Override
            public void run() {
                Log.e("cyclicBarrier", "ThreadC start wait");
                try {
                    cyclicBarrier.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("cyclicBarrier", "ThreadC end wait");
            }
        }.start();

        new Thread("D"){
            @Override
            public void run() {
                Log.e("cyclicBarrier", "ThreadD start wait");
                try {
                    cyclicBarrier.await();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("cyclicBarrier", "ThreadD end wait");
            }
        }.start();

    }

    private void testInterrupt() {
        Thread thread = new Thread("Thread_A") {
            @Override
            public void run() {
//                for (int i = 0; i < Integer.MAX_VALUE; i++){
//                    Log.e("xxxxx", " i = "+i);
//                    try {
//                        Thread.sleep(30);
//                    } catch (InterruptedException e) {
//                        Log.e("xxxxx", "111   "+e.fillInStackTrace());
//                        e.printStackTrace();
//                        break;
//                    }
//                }
                int cnt = 0;
                while (true) {
                    Log.e("xxxxx", " cnt = " + cnt++);
                    if (isInterrupted()) {
                        Log.e("xxxxx", "111   interrupt");
                        break;
                    }
                }
            }
        };
        thread.start();

        try {
            Thread.sleep(1_000);
            thread.interrupt();
        } catch (InterruptedException e) {
            Log.e("xxxxx", "222   " + e.fillInStackTrace());
            e.printStackTrace();
        }
    }

    private void testCountDownLatch() {
        CountDownLatch countDownLatch = new CountDownLatch(2);

        new Thread("A") {
            @Override
            public void run() {
                Log.e("countdownlatch", "thread name " + Thread.currentThread().getName() + ", start time >>> " + System.currentTimeMillis());
                try {
                    Thread.sleep(3_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
                Log.e("countdownlatch", "thread name " + Thread.currentThread().getName() + ", end time >>> " + System.currentTimeMillis());
            }
        }.start();

        new Thread("B") {
            @Override
            public void run() {
                Log.e("countdownlatch", "thread name " + Thread.currentThread().getName() + ", start time >>> " + System.currentTimeMillis());
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
                Log.e("countdownlatch", "thread name " + Thread.currentThread().getName() + ", end time >>> " + System.currentTimeMillis());
            }
        }.start();

        try {
            Log.e("countdownlatch", "thread name " + Thread.currentThread().getName() + ", start time >>> " + System.currentTimeMillis());
            countDownLatch.await();
            Log.e("countdownlatch", "thread name " + Thread.currentThread().getName() + ", end time >>> " + System.currentTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
