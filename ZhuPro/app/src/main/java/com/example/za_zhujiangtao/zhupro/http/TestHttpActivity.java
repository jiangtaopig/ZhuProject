package com.example.za_zhujiangtao.zhupro.http;

import android.util.Log;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.api.DataBase;
import com.example.za_zhujiangtao.zhupro.http.api.MyFunc1Subscriber;
import com.example.za_zhujiangtao.zhupro.http.api.MyObjFunc1;
import com.example.za_zhujiangtao.zhupro.http.test.TestApi;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/4/8
 */
public class TestHttpActivity extends BaseActivity {

    @BindView(R.id.txt_request_data)
    TextView mRequestTxt;

    private TestApi mTestApi;

    @Override
    protected int layoutId() {
        return R.layout.activity_test_http_layout;
    }

    @Override
    protected void onInitLogic() {
        mTestApi = new TestApi();

        mRequestTxt.setOnClickListener(v -> {
            //以下2种方式都可以，第2中封装了 请求结果
//            request1();
//            request2();
//            testRetrofit();

            Observable<Integer> integerObservable = Observable.just(1, 2, 3);
            integerObservable.subscribe(new Action1<Integer>() {
                @Override
                public void call(Integer integer) {
                    Log.e("TestHttpActivity", "call integer = "+integer);
                }
            });

        });
    }

    private void testRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.imooc.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        MyService myService = retrofit.create(MyService.class);
        Call<DataBase> call = myService.queryData(4, 6);
        call.enqueue(new Callback<DataBase>() {
            @Override
            public void onResponse(Call<DataBase> call, Response<DataBase> response) {
                if (response != null) {
                    Log.e("TestHttpActivity", "currentThread = "+Thread.currentThread().getName());
                }
            }

            @Override
            public void onFailure(Call<DataBase> call, Throwable t) {

            }
        });
    }

    private void request1() {
        mTestApi.getMyData(4, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<DataBase>() {
                    @Override
                    public void call(DataBase dataBase) {
                        if (dataBase != null) {
                            Log.e("test http", "size = " + dataBase.getData().size());
                        }
                    }
                });
    }

    private void request2() {
        mTestApi.getMukeData(4, 10)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new MyObjFunc1<>())
                .subscribe(new MyFunc1Subscriber<List<DataBase.DataBean>>() {
                    @Override
                    public void onNext(List<DataBase.DataBean> dataBeans) {
                        if (dataBeans != null) {

                        }
                    }
                });
    }

    private void requestByOkHttp() {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(msg -> {
                    Log.e("zjt test okhttp ", "msg >>> " + msg);
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        new Thread() {//接口请求需要在子线程中执行
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url("https://www.imooc.com/api/teacher?type=4&num=6")
                        .build();
                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    Log.e("xxx", "response = " + response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}


interface MyService {
    @GET("api/teacher")
    Call<DataBase> queryData(@Query("type") int type, @Query("num") int num);
}

