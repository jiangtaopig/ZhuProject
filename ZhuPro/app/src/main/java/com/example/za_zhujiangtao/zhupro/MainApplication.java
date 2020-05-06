package com.example.za_zhujiangtao.zhupro;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.alibaba.sdk.android.push.register.GcmRegister;
import com.alibaba.sdk.android.push.register.HuaWeiRegister;
import com.example.za_zhujiangtao.zhupro.float_window.WindowUtil;
import com.example.za_zhujiangtao.zhupro.http.DaggerMyApplicationComponent;
import com.example.za_zhujiangtao.zhupro.http.IApplicationComponent;
import com.example.za_zhujiangtao.zhupro.http.MyApplicationComponent;
import com.example.za_zhujiangtao.zhupro.http.MyApplicationModule;
import com.example.za_zhujiangtao.zhupro.http.MyHttpModule;
import com.example.za_zhujiangtao.zhupro.http.OkHttpClientCreator;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.util.List;

/**
 * Created by za-zhujiangtao on 2019/3/12.
 */

public class MainApplication extends Application implements Application.ActivityLifecycleCallbacks, IApplicationComponent {

    private static final String TAG = "MainApplication";
    private static Context mContext;
    private int activityCount;
    private RefWatcher mRefWatcher;
    private MyApplicationComponent mApplicationComponent;
    private static MainApplication mInstance;

    private final String YU_GANG_SHUO_URL = "http://renyugang.io/post/75";
    private static WebView mWebView;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MainApplication", ".............onCreate...............");
        mContext = getApplicationContext();
        mInstance = this;
        initCloudChannel(this);
        initARouter();

        registerActivityLifecycleCallbacks(this);

        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoList = activityManager.getRunningAppProcesses();

        if (infoList != null && infoList.size() > 0) {
            for (ActivityManager.RunningAppProcessInfo info : infoList) {
                Log.e("MainApplication", "processName = " + info.processName);
            }
        }

        // 注册方法会自动判断是否支持华为系统推送，如不支持会跳过注册。
        HuaWeiRegister.register(this);
        //GCM/FCM辅助通道注册
        GcmRegister.register(this, "775137713200", "1:775137713200:android:913d54526211277d");

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        mRefWatcher = LeakCanary.install(this);

        initComponent();

//        initWebView();
    }

//    private void initWebView() {
//        mWebView = new WebView(this);
//        mWebView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return super.shouldOverrideUrlLoading(view, request);
//            }
//        });
//    }

//    public static WebView getWebView(){
//        return mWebView;
//    }

    public static void destroyWebView(WebView webView){
        if (webView != null){
            webView.loadDataWithBaseURL(null,"", "text/html", "utf-8", null);
            webView.clearHistory();
            webView.destroy();
        }
    }

    public static RefWatcher getRefWatcher(Context context) {
        MainApplication application = (MainApplication) context.getApplicationContext();
        return application.mRefWatcher;
    }

    public static MainApplication getInstance() {
        return mInstance;
    }


    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
        createNotificationChannel();
        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "init cloudchannel success");
            }

            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // 通知渠道的id
            String id = "1";
            // 用户可以看到的通知渠道的名字.
            CharSequence name = "notification channel";
            // 用户可以看到的通知渠道的描述
            String description = "notification description";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(id, name, importance);
            // 配置通知渠道的属性
            mChannel.setDescription(description);
            // 设置通知出现时的闪灯（如果 android 设备支持的话）
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            // 设置通知出现时的震动（如果 android 设备支持的话）
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            //最后在notificationmanager中创建该通知渠道
            mNotificationManager.createNotificationChannel(mChannel);
        }
    }

    private void initARouter() {
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    public static Context getContext() {
        return mContext;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        Log.e("zjt", "attachBaseContext  MultiDex.install start time >>> " + System.currentTimeMillis());
        MultiDex.install(this);
        Log.e("zjt", "attachBaseContext  MultiDex.install end time >>> " + System.currentTimeMillis());
    }

    private void initComponent() {
        mApplicationComponent = DaggerMyApplicationComponent.builder()
                .myApplicationModule(new MyApplicationModule(getApplicationContext()))
                .myHttpModule(new MyHttpModule.Builder()
                        .setBaseUrl("http://www.imooc.com/")
                        .setOkHttpClient(OkHttpClientCreator.create())
                        .build())
                .build();
    }

    @Override
    public MyApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        Log.e("MMMMMM", "........onActivityStarted.........");
        activityCount++;
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        activityCount--;
        if (activityCount == 0) {
            Log.e("MMMMMM", ".........activityCount........" + activityCount);
            WindowUtil.getInstance().dismissWindow();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }


}
