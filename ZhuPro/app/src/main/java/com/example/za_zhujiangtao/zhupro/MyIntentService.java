package com.example.za_zhujiangtao.zhupro;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyIntentService extends IntentService {

    public MyIntentService(){
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {//是在子线程中调用的
        String name = intent.getStringExtra("NAME");
        Log.e("MyIntentService","currentThread = "+Thread.currentThread().getName()+", NAME = "+name);
    }
}
