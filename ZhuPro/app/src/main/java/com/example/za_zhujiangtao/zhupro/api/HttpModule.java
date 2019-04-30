package com.example.za_zhujiangtao.zhupro.api;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by za-zhujiangtao on 2018/6/22.
 */

@Module
public class HttpModule {

    @Provides
    OkHttpClient provideOkHttpClient(){

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.readTimeout(20, TimeUnit.SECONDS);
        httpClientBuilder.connectTimeout(20, TimeUnit.SECONDS);
        LogInterceptor interceptor = new LogInterceptor(new LogInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("...interceptor...", "message = "+message);
            }
        });
        interceptor.setLevel(LogInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(interceptor);

        return httpClientBuilder.build();
    }

    @Provides
    Retrofit provideRetrofit(OkHttpClient okHttpClient){
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl("http://www.imooc.com/")
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

}
