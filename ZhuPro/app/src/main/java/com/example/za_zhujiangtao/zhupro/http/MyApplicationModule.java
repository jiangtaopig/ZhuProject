package com.example.za_zhujiangtao.zhupro.http;

import android.content.Context;


import com.example.za_zhujiangtao.zhupro.http.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by fanjianfeng on 2017/2/24.
 */

@Module
public final class MyApplicationModule {
    private final Context mContext;

    public MyApplicationModule(Context context) {
        mContext = context;
    }

    @ApplicationScope
    @Provides
    Context provideContext() {
        return mContext;
    }
}
