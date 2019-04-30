package com.example.za_zhujiangtao.zhupro.api;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by za-zhujiangtao on 2018/6/20.
 */

public interface MyService {
    @GET("api/teacher")
    Observable<DataBase> getData(@Query("type") int type, @Query("num") int num);

    @POST("https://t-api.zuifuli.com/api/customer/v1/account/login")
    Observable<ResponseBean> login(@Body RequestParams requestParams);
}
