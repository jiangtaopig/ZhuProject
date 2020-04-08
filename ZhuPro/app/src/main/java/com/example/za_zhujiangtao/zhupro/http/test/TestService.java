package com.example.za_zhujiangtao.zhupro.http.test;

import com.example.za_zhujiangtao.zhupro.api.DataBase;
import com.example.za_zhujiangtao.zhupro.http.api.BaseApiResult;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/4/8
 */
public interface TestService {
    @GET("api/teacher")
    Observable<DataBase> getData(@Query("type") int type, @Query("num") int num);

    @GET("api/teacher")
    Observable<BaseApiResult<List<DataBase.DataBean>>> getMukeData(@Query("type") int type, @Query("num") int num);
}
