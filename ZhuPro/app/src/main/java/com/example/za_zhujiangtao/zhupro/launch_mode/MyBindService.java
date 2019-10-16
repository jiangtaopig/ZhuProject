package com.example.za_zhujiangtao.zhupro.launch_mode;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/10/15
 */
public class MyBindService extends Service {

    private MyBinder myBinder = new MyBinder();

    @Nullable
    @android.support.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public void startMyActivity(){
        Intent intent = new Intent(MyBindService.this, A2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
    }

    public class MyBinder extends Binder{
        public MyBindService getMyBindService(){
            return MyBindService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MyBindService", "onCreate");
    }
}
