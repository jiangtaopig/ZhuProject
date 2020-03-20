package com.example.za_zhujiangtao.zhupro;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/9
 */
public class TestMemoryLeakActivity extends BaseActivity {

    @BindView(R.id.txt)
    TextView textView;

    private MyHandler myHandler;
    private Subscription mDisposable;
    @Override
    protected int layoutId() {
        return R.layout.activity_memory_leak_layout;
    }

    @Override
    protected void onInitLogic() {

//        myHandler = new MyHandler(TestMemoryLeakActivity.this);
////        myHandler.postDelayed(() -> {
////            Message message = myHandler.obtainMessage();
////            message.what = 1;
////            message.obj = "测试内存泄漏的方法";
////            myHandler.sendMessage(message);
////        }, 5000);

        MainApplication.getRefWatcher(this).watch(this);

        testMemoryLeak();
        List<String> strings = new ArrayList<>();
        strings.add("ss");

    }

    private void testMemoryLeak() {
        //如果不在 onDestroy  释放Observable（mDisposable.dispose()）则会导致内存泄漏
        mDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Log.e("xxxx", "aLong = " + aLong);
                    textView.setText(String.valueOf(aLong));
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.unsubscribe();
    }


    /**
     * 1. 静态内部类不持有外部类的引用，而普通内部类和匿名内部类会持有外部类的引用
     * 2. 由于静态内部类无法引用外部类的属性和方法，所以将Activity传入；由于对Activity使用了弱引用，GC的时候发现是弱引用会立马回收，所以不会导致内存泄漏
     */
    private static class MyHandler extends Handler {
        private WeakReference<TestMemoryLeakActivity> reference;

        public MyHandler(TestMemoryLeakActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.e("TransitionActivity", "reference.get() = " + reference.get());
            if (reference.get() != null) { //以防GC的时候将Activity回收了，因为弱引用只要GC就会被回收；
                switch (msg.what) {
                    case 1:
                        Log.e("TransitionActivity", "msg = " + msg.obj);
//                        reference.get().mImageView.setImageResource(R.drawable.wowan);
                }
            }
        }
    }
}
