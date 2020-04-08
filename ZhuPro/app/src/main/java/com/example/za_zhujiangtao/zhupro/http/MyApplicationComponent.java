package com.example.za_zhujiangtao.zhupro.http;

import android.content.Context;


import com.example.za_zhujiangtao.zhupro.http.scope.ApplicationScope;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by fanjianfeng on 2017/3/30.
 */

@ApplicationScope
@Component(modules = {MyApplicationModule.class, MyHttpModule.class})
public interface MyApplicationComponent {
    Context getContext();

    Retrofit getRetrofit();

    OkHttpClient getOkHttpClient();
}
