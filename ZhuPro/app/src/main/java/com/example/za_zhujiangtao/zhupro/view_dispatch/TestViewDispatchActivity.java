package com.example.za_zhujiangtao.zhupro.view_dispatch;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.za_zhujiangtao.zhupro.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by za-zhujiangtao on 2018/11/5.
 */

public class TestViewDispatchActivity extends Activity {

    MyLinearLayout myLinearLayout;
    MyButton myButton;
    MyTextView myTextView;
    EditText editText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_view_dispatch_layout);

        myLinearLayout = findViewById(R.id.my_layout);
        myButton = findViewById(R.id.my_btn);
        myTextView = findViewById(R.id.my_txt);
        editText = findViewById(R.id.edit_text);


        myLinearLayout.setOnClickListener(v -> {
            Toast.makeText(this, "My Layout", Toast.LENGTH_LONG).show();
            Log.d("view_dispatch", "MyLinearLayout click!!!!");
//            Subscription subscription = Observable.interval(0, 1, TimeUnit.SECONDS).take(10)
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Action1<Long>() {
//                        @Override
//                        public void call(Long aLong) {
//                            Log.e("xxxx", "aLong = " + aLong);
//                        }
//                    });


            Log.e("xxxx", "...start...");
            Observable.timer(2, TimeUnit.SECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Subscriber<Long>() {
//                        @Override
//                        public void onCompleted() {
//
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//
//                        }
//
//                        @Override
//                        public void onNext(Long aLong) {
//
//                            unsubscribe();
//                        }
//                    })
                    .subscribe(aLong -> Log.e("xxxx", "aLong = " + aLong));
        });

        myButton.setOnClickListener(v -> {
            Toast.makeText(this, "My Button", Toast.LENGTH_LONG).show();
            Log.d("view_dispatch", "MyButton click!!!!");


        });

//        myButton.setOnTouchListener((v, event) -> {
////                Log.e("view_dispatch", "onTouch");
//            return false;
//        });
    }

    private void testHash() {
        Map<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("Aa", "Aa");
        hashMap.put("BB", "BB");
        hashMap.put("AaAa", "AaAa");
        hashMap.put("AaBB", "AaBB");
        hashMap.put("BBAa", "BBAa");
        hashMap.put("BBBB", "BBBB");
        hashMap.put("AaAaAa", "AaAaAa");
        hashMap.put("AaAaBB", "AaAaBB");
        hashMap.put("AaBBAa", "AaBBAa");
        hashMap.put("AaBBBB", "AaBBBB");
        hashMap.put("BBAaAa", "BBAaAa");
        hashMap.put("BBAaBB", "BBAaBB");
        hashMap.put("BBBBAa", "BBBBAa");
        hashMap.put("BBBBBB", "BBBBBB");

    }

}
