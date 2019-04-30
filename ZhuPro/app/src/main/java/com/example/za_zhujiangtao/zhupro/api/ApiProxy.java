package com.example.za_zhujiangtao.zhupro.api;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.net.ConnectivityManager;


import com.example.za_zhujiangtao.zhupro.MainApplication;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

import retrofit2.Retrofit;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by za-zhujiangtao on 2018/6/22.
 */

public abstract class ApiProxy<Service> {

    private static final int MAX_RETRY_TIME = 2;
    private static final int RETRY_DELAY_BASE_TIME = 2;
    public static final String TAG = "api";
    protected Service service;

    public ApiProxy(Retrofit retrofit) {
        service = (Service) retrofit.create(getServiceClass());
    }
//
    private Service getProxyService(final Service origin) {
        Class serviceClass = getServiceClass();
        //as default : subscribe on io thread instead of main thread
        return (Service) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[]{serviceClass},
                (proxy, method, args) -> {
                    if (accessNetwork()) {
                        return ((Observable) (method.invoke(origin, args)))
                                //防抖动，每个请求在500毫秒内只发送一次
                                .debounce(500, TimeUnit.MILLISECONDS)
                                //重试逻辑：延时重订阅，每次延时RETRY_DELAY_BASE_TIME ^ integer (integer范围：0~MAX_RETRY_TIME)秒，共重试MAX_RETRY_TIME次
                                .subscribeOn(Schedulers.io());
                    } else {
                        return Observable.error(new NetworkErrorException())
                                .subscribeOn(Schedulers.io());
                    }
                });
    }

    public boolean accessNetwork(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                MainApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getActiveNetworkInfo() != null;
    }


    private Class getServiceClass() {
        return (Class) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
}
