package com.example.za_zhujiangtao.zhupro.arouter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.za_zhujiangtao.zhupro.R;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ARouterMainActivity extends AppCompatActivity {


    private final static String TAG = "ARouterMainActivity";

    @BindView(R.id.jump_btn)
    Button mJumpBtn;

    @BindView(R.id.url_jump_btn)
    Button mUrlJump;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_router_layout);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.url_jump_btn)
    protected void urlJump(){
        ARouter.getInstance().build("/test/webview")
                .withString("url", "file:///android_asset/test.html")
                .navigation();
    }

    @OnClick(R.id.jump_btn)
    protected void jumpClick(){
        User user = new User();
        user.setAge(22);
        user.setName("xx");
        user.setSex("male");
        ARouter.getInstance().build("/com/router_activity1")
                .withString("name", "zzjj")
                .withInt("age", 33)
                .withSerializable("bookInfo", new TestObj("盗墓笔记","南派三叔"))
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

    public static class TestObj implements Serializable{
        public String book;
        public String author;

        public TestObj(String book, String author){
            this.book = book;
            this.author = author;
        }
    }
}


