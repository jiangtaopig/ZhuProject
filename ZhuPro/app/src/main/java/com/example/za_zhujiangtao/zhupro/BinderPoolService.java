package com.example.za_zhujiangtao.zhupro;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class BinderPoolService extends Service {

    private IBinder binder = new BinderPool.BindPoolImpl();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
}
