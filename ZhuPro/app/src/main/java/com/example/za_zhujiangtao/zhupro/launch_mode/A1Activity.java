package com.example.za_zhujiangtao.zhupro.launch_mode;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.ArraySet;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import rx.Observable;
import rx.Subscriber;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/7/25
 */
public class A1Activity extends BaseActivity {

    @BindView(R.id.jump_2_b)
    Button jump2B;

    @BindView(R.id.a1_txt)
    TextView mTxt;

    @BindView(R.id.bind_service)
    Button mBindService;

    @BindView(R.id.start_activity_from_service)
    Button mStartActivityFromService;

    private MyBindService myBindService;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBindService.MyBinder myBinder = (MyBindService.MyBinder) service;
            myBindService = myBinder.getMyBindService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("A1Activity", "A onCreate");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e("A1Activity", "A onNewIntent");
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_a;
    }

    @Override
    protected void onInitLogic() {
        mTxt.setText("I am A1 Activity");
        jump2B.setText("Jump 2 A2Activity");
        jump2B.setOnClickListener(v -> {
            Intent intent = new Intent(A1Activity.this, A2Activity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });


        mBindService.setOnClickListener(v -> {
            Intent intent = new Intent(A1Activity.this, MyBindService.class);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        });

        mStartActivityFromService.setOnClickListener(v -> {
            if (myBindService != null) {
                myBindService.startMyActivity();
            }
        });

        Observable.zip(Observable.just(1), Observable.just("zz"), Pair::new)
                .subscribe(new Subscriber<Pair<Integer, String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Pair<Integer, String> integerStringPair) {
                        Integer integer = integerStringPair.first;
                        String s = integerStringPair.second;
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbindService(serviceConnection);
    }
}
