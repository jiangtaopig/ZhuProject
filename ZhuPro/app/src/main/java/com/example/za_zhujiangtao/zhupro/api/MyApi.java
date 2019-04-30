package com.example.za_zhujiangtao.zhupro.api;


import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by za-zhujiangtao on 2018/6/22.
 */

public class MyApi extends ApiProxy<MyService> {

    public MyApi(Retrofit retrofit) {
        super(retrofit);
    }

    public Observable<DataBase> getMyData(int type, int num){
        return service.getData(type, num);
    }

    public Observable<ResponseBean> login(RequestParams requestParams){
        return service.login(requestParams);
    }
}
