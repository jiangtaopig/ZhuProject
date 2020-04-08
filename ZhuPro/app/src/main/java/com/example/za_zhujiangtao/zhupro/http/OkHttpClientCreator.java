package com.example.za_zhujiangtao.zhupro.http;


import android.util.Log;

import com.example.za_zhujiangtao.zhupro.api.LogInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpClientCreator {
    private static final int TIMEOUT = 20 * 1000;

    public static OkHttpClient create() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true);
//        if (HeaderManager.getInstance().getHeaders() != null && !HeaderManager.getInstance().getHeaders().isEmpty()) {
//            builder.addInterceptor(new HeaderInterceptor(HeaderManager.getInstance().getHeaders()));
//        }
        LogInterceptor interceptor = new LogInterceptor(new LogInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("...interceptor...", "message = "+message);
            }
        });
        interceptor.setLevel(LogInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        return builder.build();
    }
}
