package com.example.za_zhujiangtao.zhupro.http;

import android.text.TextUtils;


import com.example.za_zhujiangtao.zhupro.http.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fanjianfeng on 2017/2/27.
 */

@Module
public class MyHttpModule {
    private String mBaseUrl;
    private Converter.Factory mConverterFactory;
    private CallAdapter.Factory mAdapterFactory;
    private OkHttpClient mOkHttpClient;

    private MyHttpModule() {

    }

    @ApplicationScope
    @Provides
    OkHttpClient provideOkHttpClient() {
        return mOkHttpClient;
    }

    @ApplicationScope
    @Provides
    Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addConverterFactory(mConverterFactory)
                .addCallAdapterFactory(mAdapterFactory)
                .client(mOkHttpClient)
                .baseUrl(mBaseUrl)
                .build();
    }

    public static class Builder {
        private String baseUrl;
        private Converter.Factory converterFactory;
        private CallAdapter.Factory adapterFactory;
        private OkHttpClient okHttpClient;

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setConverterFactory(Converter.Factory converterFactory) {
            this.converterFactory = converterFactory;
            return this;
        }

        public Builder setAdapterFactory(CallAdapter.Factory adapterFactory) {
            this.adapterFactory = adapterFactory;
            return this;
        }

        public Builder setOkHttpClient(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
            return this;
        }

        public MyHttpModule build() {
            MyHttpModule httpModule = new MyHttpModule();
            if (TextUtils.isEmpty(baseUrl)) {
                throw new NullPointerException("baseUrl is empty!");
            }
            httpModule.mBaseUrl = baseUrl;
            if (converterFactory == null) {
                converterFactory = GsonConverterFactory.create();
            }
            httpModule.mConverterFactory = converterFactory;
            if (adapterFactory == null) {
                adapterFactory = RxJavaCallAdapterFactory.create();
            }
            httpModule.mAdapterFactory = adapterFactory;
            if (okHttpClient == null) {
                okHttpClient = new OkHttpClient.Builder()
                        .retryOnConnectionFailure(true)
                        .addNetworkInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build();
            }
            httpModule.mOkHttpClient = okHttpClient;
            return httpModule;
        }
    }
}
