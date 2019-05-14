package com.example.za_zhujiangtao.zhupro;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;

import java.util.List;

/**
 * Created by za-zhujiangtao on 2019/3/12.
 */

public class MainApplication extends Application {

    private static final String TAG = "MainApplication";
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("MainApplication", ".............onCreate...............");
        mContext = getApplicationContext();
        initCloudChannel(this);
        initARouter();

        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoList = activityManager.getRunningAppProcesses();

        if (infoList != null && infoList.size() >0){
            for (ActivityManager.RunningAppProcessInfo info : infoList){
                Log.e("MainApplication", "processName = "+info.processName);
            }
        }
    }

    /**
     * 初始化云推送通道
     *
     * @param applicationContext
     */
    private void initCloudChannel(Context applicationContext) {
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

    private void initARouter(){
        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init( this ); // 尽可能早，推荐在Application中初始化
    }

    public static Context getContext(){
        return mContext;
    }

}
