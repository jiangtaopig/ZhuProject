package com.example.za_zhujiangtao.zhupro.http;

import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.api.DataBase;
import com.example.za_zhujiangtao.zhupro.api.RequestParams;
import com.example.za_zhujiangtao.zhupro.api.ResponseBean;
import com.example.za_zhujiangtao.zhupro.http.api.BaseApiResult;
import com.example.za_zhujiangtao.zhupro.http.api.MyFunc1Subscriber;
import com.example.za_zhujiangtao.zhupro.http.api.MyObjFunc1;
import com.example.za_zhujiangtao.zhupro.http.test.TestApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
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
                    Log.e("TestHttpActivity", "call integer = " + integer);
                }
            });

//            requestByOkHttp();

            Gson gson = new Gson();

            Address address = new Address("上海", "虹口区 四川北路 中信大楼");
            User user = new User("朱大爷", "male", 32, address);
            Map<String, Object> map = gson.fromJson(gson.toJson(user), new TypeToken<HashMap<String, Object>>() {
            }.getType());

//            doLogin();
//            request2();
            dynaticLogin();

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
                    Log.e("TestHttpActivity", "currentThread = " + Thread.currentThread().getName());
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
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new DownLoadResponseBody(originalResponse.body(), progress -> {
                                    Log.e("TestHttpActivity", "requestByOkHttp progress = " + progress);
                                })).build();
                    }
                })
                .addInterceptor(new HttpLoggingInterceptor(msg -> {
                    Log.e("zjt test okhttp ", "msg >>> " + msg);
                }).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        new Thread() {//接口请求需要在子线程中执行
            @Override
            public void run() {
                Request request = new Request.Builder()
                        .url("http://t8.baidu.com/it/u=3571592872,3353494284&fm=79&app=86&f=JPEG?w=1200&h=1290")
                        .method("POST", new FormBody.Builder().build())
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


    private void doLogin() {
//        RequestParams params = new RequestParams("18321810001", "1234567l");
//        mTestApi.login(params)
//                .map(new MyObjFunc1<>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MyFunc1Subscriber<ResponseBean.ResultBean>() {
//                    @Override
//                    public void onNext(ResponseBean.ResultBean resultBean) {
//                        if (resultBean != null){
//                            Toast.makeText(getApplicationContext(), resultBean.getName(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });


        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Response originalResponse = chain.proceed(chain.request());
                        return originalResponse.newBuilder()
                                .body(new DownLoadResponseBody(originalResponse.body(), progress -> {
                                    Log.e("TestHttpActivity", "requestByOkHttp progress = " + progress);
                                })).build();
                    }
                })
                .addInterceptor(chain -> { // 后台不接受健值对格式的数据，只能将数据转成json
                    Request originalReq = chain.request();
                    Request.Builder builder = originalReq.newBuilder();
                    if (originalReq.method().equals("POST")) {
                        RequestBody requestBody = originalReq.body();
                        if (requestBody instanceof FormBody) {
                            Map<String, Object> map = new HashMap<>();
                            FormBody formBody = (FormBody) requestBody;
                            for (int i = 0; i < formBody.size(); i++) {
                                map.put(formBody.encodedName(i), formBody.encodedValue(i));
                            }

                            if (map.size() > 0) {
                                Gson gson = new Gson();
                                String json = gson.toJson(map);
                                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), json);
                                builder.post(body);
                            }
                        }
                    }
                    return chain.proceed(builder.build());
                })
                .addInterceptor(new HttpLoggingInterceptor(msg -> {
                    Log.e("zjt test okhttp ", "msg >>> " + msg);
                })
                        .setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        RequestBody requestBody = new FormBody.Builder().add("phone", "18321810001")
                .add("passwd", "1234567l")
                .build();

//        MultipartBody.Builder urlBuilder = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM);
//
//        urlBuilder.addFormDataPart("phone", "18321810001")
//                .addFormDataPart("passwd", "1234567l");

        Request request = new Request.Builder().url("https://t-api.zuifuli.com/api/customer/v1/account/login")
                .post(requestBody)
                .build();
        client.newCall(request)
                .enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                        String res = response.body().string();
                        if (res != null) {

                        }
                    }
                });
    }

    private void dynaticLogin() {
//        RequestParams params = new RequestParams("18321810001", "1234567l");
//        mTestApi.dynamicLogin("https://t-api.zuifuli.com/api/customer/v1/account/login", params)
//                .map(new MyObjFunc1<>())
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new MyFunc1Subscriber<ResponseBean.ResultBean>() {
//                    @Override
//                    public void onNext(ResponseBean.ResultBean resultBean) {
//                        if (resultBean != null) {
//                            Toast.makeText(getApplicationContext(), resultBean.getName(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });

        Map<String, String> map = new HashMap<>();
        map.put("phone", "18321810001");
        map.put("passwd", "1234567l");

        mTestApi.dynamicLogin2("https://t-api.zuifuli.com/api/customer/v1/account/login", map)
                .map(new MyObjFunc1<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyFunc1Subscriber<ResponseBean.ResultBean>() {
                    @Override
                    public void onNext(ResponseBean.ResultBean resultBean) {
                        if (resultBean != null) {
                            Toast.makeText(getApplicationContext(), resultBean.getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }


    public class User {
        private String name;
        private String sex;
        private int age;
        private Address address;

        public User(String name, String sex, int age, Address address) {
            this.name = name;
            this.sex = sex;
            this.age = age;
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public String getSex() {
            return sex;
        }

        public int getAge() {
            return age;
        }

        public Address getAddress() {
            return address;
        }
    }

    public class Address {
        private String detail;
        private String city;

        public Address(String detail, String city) {
            this.detail = detail;
            this.city = city;
        }

        public String getDetail() {
            return detail;
        }

        public String getCity() {
            return city;
        }
    }

}


interface MyService {
    @GET("api/teacher")
    Call<DataBase> queryData(@Query("type") int type, @Query("num") int num);
}



