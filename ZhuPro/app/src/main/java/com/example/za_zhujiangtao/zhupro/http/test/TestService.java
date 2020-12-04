package com.example.za_zhujiangtao.zhupro.http.test;

import com.example.za_zhujiangtao.zhupro.api.DataBase;
import com.example.za_zhujiangtao.zhupro.api.RequestParams;
import com.example.za_zhujiangtao.zhupro.api.ResponseBean;
import com.example.za_zhujiangtao.zhupro.http.api.BaseApiResult;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
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

    @POST("https://t-api.zuifuli.com/api/customer/v1/account/login")
    Observable<BaseApiResult<ResponseBean.ResultBean>> login(@Body RequestParams requestParams);

    @POST
    Observable<BaseApiResult<ResponseBean.ResultBean>> dynamicLogin(@Url String url, @Body RequestParams requestParams);

    @POST
    Observable<BaseApiResult<ResponseBean.ResultBean>> dynamicLogin2(@Url String url, @Body Map requestParams);

    @GET
    @Streaming
    Observable<ResponseBody> downLoadFile(@Url String url);


}
