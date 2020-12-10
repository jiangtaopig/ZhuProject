package com.example.za_zhujiangtao.zhupro.http.test;

import com.example.za_zhujiangtao.zhupro.MainApplication;
import com.example.za_zhujiangtao.zhupro.api.DataBase;
import com.example.za_zhujiangtao.zhupro.api.RequestParams;
import com.example.za_zhujiangtao.zhupro.api.ResponseBean;
import com.example.za_zhujiangtao.zhupro.http.api.BaseApiResult;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Url;
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

    public  Observable<BaseApiResult<ResponseBean.ResultBean>> login(RequestParams requestParams){
        return service.login(requestParams);
    }

    public Observable<BaseApiResult<ResponseBean.ResultBean>> dynamicLogin(String url, RequestParams requestParams){
        return service.dynamicLogin(url, requestParams);
    }

   public Observable<BaseApiResult<ResponseBean.ResultBean>> dynamicLogin2(String url, Map requestParams){
        return service.dynamicLogin2(url, requestParams);
    }

    public Observable<ResponseBody> dynamicLogin3(String url, RequestBody requestBody){
        return service.dynamicLogin3(url, requestBody);
    }

    public  Observable<ResponseBody> downLoadFile(String url){
        return service.downLoadFile(url);
    }
}
