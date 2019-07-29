package com.example.za_zhujiangtao.zhupro;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.za_zhujiangtao.zhupro.launch_mode.AActivity;
import com.example.za_zhujiangtao.zhupro.launch_mode.BActivity;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/7/26
 */
public class MyService extends Service {
    public static final String TAG = "MyService";

    private MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() executed");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class MyBinder extends Binder {

        public void jump2AActivity() {
            Log.d(TAG, "jump2AActivity() executed");
            Context context = getApplicationContext();
           boolean activity = context instanceof Activity;
            Intent intent = new Intent(context, AActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        public void jump2BActivity(){
            Log.d(TAG, "jump2BActivity() executed");
            Context context = getApplicationContext();
            Intent intent = new Intent(context, BActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

}
