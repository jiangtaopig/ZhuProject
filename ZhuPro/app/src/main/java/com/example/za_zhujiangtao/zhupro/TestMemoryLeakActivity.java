package com.example.za_zhujiangtao.zhupro;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.launch_mode.B1Activity;
import com.example.za_zhujiangtao.zhupro.utils.Platform;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/3/9
 */
public class TestMemoryLeakActivity extends BaseActivity {

    @BindView(R.id.txt)
    TextView textView;

    @BindView(R.id.btn_jump)
    Button mJumpBtn;

    private MyHandler myHandler;
    private Subscription mDisposable;
    private int cnt;

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


        testMemoryLeak();

        mJumpBtn.setOnClickListener(v -> {

            Observable.just(1)
                    .map(integer -> 1 / 0)
                    .onErrorResumeNext(throwable -> {//会把错误“吃掉”，不会执行下面的onError，所以最好加log
                        Log.e("xxx", "error = " + throwable.fillInStackTrace());
                        return Observable.just(-1);
                    })
                    .subscribe(new Subscriber<Integer>() {
                        @Override
                        public void onCompleted() {
                            Log.e("xxx", "onCompleted");
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("xxx", "onError e = " + e.getMessage());
                        }

                        @Override
                        public void onNext(Integer integer) {
                            Log.e("xxx", "onNext integer = " + integer.toString());
                        }
                    });

        });

        boolean isMain = Platform.getInstance().isMain();
        Log.e("xxx", "isMain = " + isMain);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//                    Log.e("xxxxx", "leark cnt = "+(cnt++));
//                }
//            }
//        }).start();

        Observable.just(1)
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {

                    }
                });

        ThreadLocal<String> threadLocal = new ThreadLocal<>();
        threadLocal.set("xxx");

        List<Integer> list = new ArrayList<>();
        list.add(1);

        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("1");
        linkedList.add("2");
        linkedList.add("3");
        linkedList.remove("2");
        linkedList.peek();
        linkedList.peekFirst();
        linkedList.peekLast();
        linkedList.pollFirst();
        linkedList.push("f");
        linkedList.pop();
        linkedList.poll();
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
        Log.e("xxxx", ".....onDestroy .....");
//        mDisposable.unsubscribe();
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
