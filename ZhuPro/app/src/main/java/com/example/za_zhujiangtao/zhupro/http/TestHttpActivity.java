package com.example.za_zhujiangtao.zhupro.http;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.api.DataBase;
import com.example.za_zhujiangtao.zhupro.api.ResponseBean;
import com.example.za_zhujiangtao.zhupro.http.api.BaseApiResult;
import com.example.za_zhujiangtao.zhupro.http.api.MyFunc1Subscriber;
import com.example.za_zhujiangtao.zhupro.http.api.MyObjFunc1;
import com.example.za_zhujiangtao.zhupro.http.test.TestApi;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
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

    private static final String[][] MIME_MapTable = {
            //{后缀名，    MIME类型}
            {".3gp", "video/3gpp"},
            {".apk", "application/vnd.android.package-archive"},
            {".asf", "video/x-ms-asf"},
            {".avi", "video/x-msvideo"},
            {".bin", "application/octet-stream"},
            {".bmp", "image/bmp"},
            {".c", "text/plain"},
            {".class", "application/octet-stream"},
            {".conf", "text/plain"},
            {".cpp", "text/plain"},
            {".doc", "application/msword"},
            {".docx", "application/msword"},
            {".exe", "application/octet-stream"},
            {".gif", "image/gif"},
            {".gtar", "application/x-gtar"},
            {".gz", "application/x-gzip"},
            {".h", "text/plain"},
            {".htm", "text/html"},
            {".html", "text/html"},
            {".jar", "application/java-archive"},
            {".java", "text/plain"},
            {".jpeg", "image/jpeg"},
            {".JPEG", "image/jpeg"},
            {".jpg", "image/jpeg"},
            {".js", "application/x-javascript"},
            {".log", "text/plain"},
            {".m3u", "audio/x-mpegurl"},
            {".m4a", "audio/mp4a-latm"},
            {".m4b", "audio/mp4a-latm"},
            {".m4p", "audio/mp4a-latm"},
            {".m4u", "video/vnd.mpegurl"},
            {".m4v", "video/x-m4v"},
            {".mov", "video/quicktime"},
            {".mp2", "audio/x-mpeg"},
            {".mp3", "audio/x-mpeg"},
            {".mp4", "video/mp4"},
            {".mpc", "application/vnd.mpohun.certificate"},
            {".mpe", "video/mpeg"},
            {".mpeg", "video/mpeg"},
            {".mpg", "video/mpeg"},
            {".mpg4", "video/mp4"},
            {".mpga", "audio/mpeg"},
            {".msg", "application/vnd.ms-outlook"},
            {".ogg", "audio/ogg"},
            {".pdf", "application/pdf"},
            {".png", "image/png"},
            {".pps", "application/vnd.ms-powerpoint"},
            {".ppt", "application/vnd.ms-powerpoint"},
            {".pptx", "application/vnd.ms-powerpoint"},
            {".prop", "text/plain"},
            {".rar", "application/x-rar-compressed"},
            {".rc", "text/plain"},
            {".rmvb", "audio/x-pn-realaudio"},
            {".rtf", "application/rtf"},
            {".sh", "text/plain"},
            {".tar", "application/x-tar"},
            {".tgz", "application/x-compressed"},
            {".txt", "text/plain"},
            {".wav", "audio/x-wav"},
            {".wma", "audio/x-ms-wma"},
            {".wmv", "audio/x-ms-wmv"},
            {".wps", "application/vnd.ms-works"},
            //{".xml",    "text/xml"},
            {".xml", "text/plain"},
            {".z", "application/x-compress"},
            {".zip", "application/zip"},
            {".pdf", "application/pdf"},
            {"", "*/*"}
    };

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

            requestByOkHttp();

            Gson gson = new Gson();

            Address address = new Address("上海", "虹口区 四川北路 中信大楼");
            User user = new User("朱大爷", "male", 32, address);
            Map<String, Object> map = gson.fromJson(gson.toJson(user), new TypeToken<HashMap<String, Object>>() {
            }.getType());

//            doLogin();
//            request2();
//            dynaticLogin();
//            testHandler();

//            downLoad();

//            dynamic3();

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

        Request request = new Request.Builder()
                .url("http://www.imooc.com/api/teacher?type=4&num=10")
                .build();
        client.newCall(request)
                .enqueue(new okhttp3.Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                        if (response != null) {
                            Log.e("xxx", "res >>> " + response.body().string());
                        }
                    }
                });
//        new Thread() {//接口请求需要在子线程中执行
//            @Override
//            public void run() {
//                Request request = new Request.Builder()
//                        .url("http://t8.baidu.com/it/u=3571592872,3353494284&fm=79&app=86&f=JPEG?w=1200&h=1290")
//                        .method("POST", new FormBody.Builder().build())
//                        .build();
//                try {
//                    okhttp3.Response response = client.newCall(request).execute();
//                    Log.e("xxx", "response = " + response.body().string());
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();


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
                    public void onResponse(@NonNull okhttp3.Call call, okhttp3.Response response) throws IOException {

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

    private void dynamic3() {
        Map<String, String> map = new HashMap<>();
        map.put("phone", "18321810001");
        map.put("passwd", "1234567l");
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), new Gson().toJson(map));
        mTestApi.dynamicLogin3("https://t-api.zuifuli.com/api/customer/v1/account/login", body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyFunc1Subscriber<ResponseBody>() {

                    @Override
                    public void onNext(ResponseBody body) {
                        if (body != null) {
                            try {
                                String res = body.string();
                                Type type = new TypeToken<BaseApiResult<ResponseBean.ResultBean>>() {
                                }.getType();
                                BaseApiResult<ResponseBean.ResultBean> apiResult = new Gson().fromJson(res, type);
                                ResponseBean.ResultBean bean = apiResult.getResult();
                                bean.getName();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });
    }

    /**
     * 文件下载到本地根目录后，选择可以打开的文件
     */
    private void downLoad() {
        String url = "https://image.zuifuli.com/14/20200608/96302669b39302f0a5e701e234d6c202.pdf";
        mTestApi.downLoadFile(url)
                .flatMap(result -> {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "zhujiangtao";
                    // 关于文件和文件夹的操作一定要动态申请权限
                    File parentFile = new File(path);
                    if (!parentFile.exists()) {
                        parentFile.mkdirs();
                    }
                    int index = url.lastIndexOf("/");
                    String fileName = url.substring(index);
                    File file = new File(path + fileName);
                    InputStream inputStream = result.byteStream();
                    FileOutputStream fileOutputStream = null;
                    if (!file.exists()) {
                        try {
                            file.createNewFile();
                            fileOutputStream = new FileOutputStream(file);
                            byte[] bytes = new byte[1024];
                            int readLength = 0;
                            while ((readLength = inputStream.read(bytes)) != -1) {
                                fileOutputStream.write(bytes, 0, readLength);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                inputStream.close();
                                if (fileOutputStream != null)
                                    fileOutputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    return Observable.just(file.getAbsolutePath());
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Toast.makeText(getBaseContext(), s, Toast.LENGTH_LONG).show();
                    Log.e("TestHttp", "s = " + s);
                    openAndroidFile(s);
                });
    }

    public void openAndroidFile(String filepath) {
        Intent intent = new Intent();
        File file = new File(filepath);
        Uri contentUri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            contentUri = FileProvider.getUriForFile(this, "com.example.za_zhujiangtao.zhupro.fileProvider", file);
        } else {
            contentUri = Uri.fromFile(file);
        }
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//设置标记
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setAction(Intent.ACTION_VIEW);//动作，查看
        intent.setDataAndType(contentUri, getMIMEType(file));//设置类型
        startActivity(intent);
    }

    private String getMIMEType(File file) {
        String type = "*/*";
        String fName = file.getName();
        //获取后缀名前的分隔符"."在fName中的位置。
        int dotIndex = fName.lastIndexOf(".");
        if (dotIndex < 0)
            return type;
        /* 获取文件的后缀名 */
        String fileType = fName.substring(dotIndex, fName.length()).toLowerCase();
        if (fileType == null || "".equals(fileType))
            return type;
        //在MIME和文件类型的匹配表中找到对应的MIME类型。
        for (int i = 0; i < MIME_MapTable.length; i++) {
            if (fileType.equals(MIME_MapTable[i][0]))
                type = MIME_MapTable[i][1];
        }
        return type;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void testHandler() {
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };

        handler.post(() -> {

        });

        MessageQueue queue = handler.getLooper().getQueue();
        Log.e("hhhh", "queue = " + queue);
        // handler、looper、MessageQueue 与线程的关系：
        // 一个线程对应一个 Looper, 对应一个MessageQueue，对应多个Handler
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Handler h = new Handler();
                MessageQueue q = h.getLooper().getQueue();
                // 子线程中也可以往 主线程的 handler 中发送消息，所以 MessageQueue 中的 插入消息是有同步的。
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
                Log.e("hhhh", "子线程 q = " + q);
                Looper.loop();

            }
        }.start();
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



