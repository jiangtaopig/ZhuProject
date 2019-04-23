package com.example.za_zhujiangtao.zhupro;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.transitionseverywhere.TransitionManager;

import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class TransitionActivity extends AppCompatActivity {


    @BindView(R.id.group_top)
    LinearLayout mGroupTop;

    @BindView(R.id.image_view)
    ImageView mImageView;

    private boolean mIsImageVisible = true;
    private Disposable mDisposable;
    private MyHandler myHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //过渡动画的设置
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_transition_layout);
        ButterKnife.bind(this);

        //使用过渡动画Transition来作为Activity的出、入场动画
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setEnterTransition(new Slide().setDuration(500));
            getWindow().setExitTransition(new Slide().setDuration(500));
        }

        testMemoryLeak();

        myHandler = new MyHandler(TransitionActivity.this);
        myHandler.postDelayed(() -> {
            Message message = myHandler.obtainMessage();
            message.what = 1;
            message.obj = "测试内存泄漏的方法";
            myHandler.sendMessage(message);
        }, 5000);
    }

    @OnClick(R.id.click_btn)
    protected void testTransition() {
        TransitionManager.beginDelayedTransition(mGroupTop);
        if (mIsImageVisible) {
            mImageView.setVisibility(View.GONE);
        } else {
            mImageView.setVisibility(View.VISIBLE);
        }
        mIsImageVisible = !mIsImageVisible;
    }

    private void testMemoryLeak() {
        //如果不在 onDestroy  释放Observable（mDisposable.dispose()）则会导致内存泄漏
        mDisposable = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    Log.e("xxxx", "aLong = " + aLong);
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }


    /**
     * 1. 静态内部类不持有外部类的引用，而普通内部类和匿名内部类会持有外部类的引用
     * 2. 由于静态内部类无法引用外部类的属性和方法，所以将Activity传入；由于对Activity使用了弱引用，GC的时候发现是弱引用会立马回收，所以不会导致内存泄漏
     */
    private static class MyHandler extends Handler {
        private WeakReference<TransitionActivity> reference;

        public MyHandler(TransitionActivity activity) {
            reference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Log.e("TransitionActivity", "reference.get() = " + reference.get());
            if (reference.get() != null) { //以防GC的时候将Activity回收了，因为弱引用只要GC就会被回收；
                switch (msg.what) {
                    case 1:
                        Log.e("TransitionActivity", "msg = " + msg.obj);
                        reference.get().mImageView.setImageResource(R.drawable.wowan);
                }
            }
        }
    }
}


