package com.example.za_zhujiangtao.zhupro.http.test;

import com.example.za_zhujiangtao.zhupro.MainApplication;
import com.example.za_zhujiangtao.zhupro.api.DataBase;
import com.example.za_zhujiangtao.zhupro.http.api.BaseApiResult;

import java.util.List;

import retrofit2.http.Query;
import rx.Observable;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/4/8
 */
public class TestApi {
    TestService service;

    public TestApi() {
        service = MainApplication.getInstance().getApplicationComponent().getRetrofit().create(TestService.class);
    }

    public Observable<DataBase> getMyData(int type, int num){
        return service.getData(type, num);
    }

    public Observable<BaseApiResult<List<DataBase.DataBean>>> getMukeData( int type, int num){
        return service.getMukeData(type, num);
    }
}
