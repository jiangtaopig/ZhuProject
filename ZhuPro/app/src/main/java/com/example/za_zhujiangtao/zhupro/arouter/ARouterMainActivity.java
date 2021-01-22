package com.example.za_zhujiangtao.zhupro.arouter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.za_zhujiangtao.zhupro.MyIntentService;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.api.ApiComponent;
import com.example.za_zhujiangtao.zhupro.api.ApiModule;
import com.example.za_zhujiangtao.zhupro.api.ApplicationComponent;
import com.example.za_zhujiangtao.zhupro.api.ApplicationModule;
import com.example.za_zhujiangtao.zhupro.api.DaggerApiComponent;
import com.example.za_zhujiangtao.zhupro.api.DaggerApplicationComponent;
import com.example.za_zhujiangtao.zhupro.api.DataBase;
import com.example.za_zhujiangtao.zhupro.api.MyApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class ARouterMainActivity extends AppCompatActivity {

    private final static String TAG = "ARouterMainActivity";

    @BindView(R.id.jump_btn)
    Button mJumpBtn;

    @BindView(R.id.url_jump_btn)
    Button mUrlJump;

    @Inject
    MyApi myApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_main_router_layout);
        ButterKnife.bind(this);
        ApplicationComponent applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

        ApiComponent component = DaggerApiComponent.builder()
                .apiModule(new ApiModule())
                .applicationComponent(applicationComponent)
                .build();
        component.inject(this);

        getData();

    }

    private void getData() {
        myApi.getMyData(4, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DataBase>() {
                    @Override
                    public void call(DataBase dataBase) {
                        if (dataBase != null) {
                            Log.e("ARouterMainActivity", "size = " + dataBase.getData().size());
                        }
                    }
                });

    }

    @OnClick(R.id.test_intent_service)
    protected void onJumpIntentService() {
        Intent intent = new Intent(this, MyIntentService.class);
        intent.putExtra("NAME", "ZZZZ");
        startService(intent);

    }

    private void testInterval() {
        Observable.timer(0, 3000, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .take(2)
                .subscribe(aLong -> {
                    Log.e("ARouterMainActivity", "testInterval , aLong = " + aLong);
                });

    }

    @OnClick(R.id.url_jump_btn)
    protected void urlJump() {
//        ARouter.getInstance().build("/test/webview")
//                .withString("url", "file:///android_asset/test.html")
//                .navigation();

        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.a1);
        int width = bm.getWidth();
        int height = bm.getHeight();

        Log.e("ARouterMainActivity", "width = " + width + ", height = " + height);

        int[] arrs = new int[]{1, 2, 3};
        List<Integer> list = new ArrayList<>();
        for (int i = 0, len = arrs.length; i < len; i++) {
            list.add(arrs[i]);
        }
//        testInterval();
    }

    @OnClick(R.id.jump_btn)
    protected void jumpClick() {
        User user = new User();
        user.setAge(22);
        user.setName("xx");
        user.setSex("male");
        ARouter.getInstance().build("/com/router_activity1")
                .withString("name", "zzjj")
                .withInt("age", 33)
                .withSerializable("bookInfo", new TestObj("盗墓笔记", "南派三叔"))
                .withObject("user", user)//用withObject必须要使用 SerializationService，本例中JsonServiceImpl 实现了该接口
                .navigation(this, new NavCallback() {
                    @Override
                    public void onArrival(Postcard postcard) {
                        Log.e(TAG, "onArrival 跳转成功");
                    }

                    @Override
                    public void onFound(Postcard postcard) {
                        super.onFound(postcard);
                        Log.e(TAG, "onFound");
                    }

                    @Override
                    public void onLost(Postcard postcard) {
                        super.onLost(postcard);
                        Log.e(TAG, "onLost");
                    }

                    @Override
                    public void onInterrupt(Postcard postcard) {
                        super.onInterrupt(postcard);
                        Log.e(TAG, "onInterrupt 拦截了");
                    }
                });
    }

    public static class TestObj implements Serializable {
        public String book;
        public String author;

        public TestObj(String book, String author) {
            this.book = book;
            this.author = author;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
    }
}


