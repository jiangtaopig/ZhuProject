package com.example.za_zhujiangtao.zhupro.retrofit_rxjava;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/7/19
 */
public class RetrofitServiceManager {
    private static final int CONNECT_TIMEOUT = 20*1000;
    private static final int READ_TIMEOUT = CONNECT_TIMEOUT;
    private static final int WRITE_TIMEOUT = READ_TIMEOUT;

    private Retrofit retrofit;

    private RetrofitServiceManager(){
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(new HttpLoggingInterceptor(message -> {
                    Log.e("apiService :: ", message);
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
//                .addInterceptor(new HeaderInterceptor())
//                .addInterceptor(new SignInterceptor())
        .build();

         retrofit = new Retrofit.Builder()
                .baseUrl("http://www.imooc.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private static class SingletonHolder{
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    public  RetrofitServiceManager getInstance(){
        return SingletonHolder.INSTANCE;
    }

    /**
     * 获取对应的Service
     * @param service Service 的 class
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> service){
        return retrofit.create(service);
    }

}
