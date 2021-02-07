package com.example.za_zhujiangtao.zhupro;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.za_zhujiangtao.zhupro.define_view.TestThreadPoolActivity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/12/23
 */
public class NotificationActivity extends BaseActivity {

    @BindView(R.id.txt_msg)
    TextView textView;

    @BindView(R.id.txt_send)
    TextView mSendMsg;

    @BindView(R.id.txt_stop)
    TextView mStopMsg;

    private NotificationManager manager;

    private RemoteViews mRemoteViews;
    private int mCnt;

    private Subscription mSub;
    private MessageBean mMessageBean;
    private LinkedHashMap<String, MessageBean> mMessageBeanMap = new LinkedHashMap<>();

    @Override
    protected int layoutId() {
        return R.layout.activity_notification_layout;
    }

    @Override
    protected void onInitLogic() {

        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "chat";
            String channelName = "聊天消息";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);

            channelId = "subscribe";
            channelName = "订阅消息";
            importance = NotificationManager.IMPORTANCE_DEFAULT;
            createNotificationChannel(channelId, channelName, importance);
        }

        textView.setOnClickListener(v -> {
//            defaultNotificationStyle();

            AndroidSchedulers.mainThread().createWorker().schedule(() -> {
                showNotification();
            }, 1, TimeUnit.SECONDS);
        });

        mSendMsg.setOnClickListener(v -> {

            mSub = Observable.interval(500, TimeUnit.MILLISECONDS)
//                    .take(3)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        mMessageBean = new MessageBean();
                        mMessageBean.name = "msg ： " + aLong;
                        mMessageBean.id = "id_11111";

                        if (aLong == 1){
                            mMessageBean.id = "id_22222";
                        }

                        if (mMessageBeanMap.containsKey(mMessageBean.id))
                            mMessageBeanMap.get(mMessageBean.id).cnt++;
                        else {
                            mMessageBean.cnt++;
                            mMessageBeanMap.put(mMessageBean.id, mMessageBean);
                        }
                        Log.e("xxx", "add  >>> " + mMessageBean.cnt);
                    });
        });

        Observable.interval(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {

                    Iterator<Map.Entry<String, MessageBean>> iterator = mMessageBeanMap.entrySet().iterator();
                    while (iterator.hasNext()){
                        Map.Entry<String, MessageBean> entry = iterator.next();
                        Log.e("xxx", "msg : "+entry.getValue().cnt);
                        iterator.remove();
                    }

                });

        mStopMsg.setOnClickListener(v -> {
            if (!mSub.isUnsubscribed())
                mSub.unsubscribe();
        });

    }


    private void defaultNotificationStyle() {
        // 注意要显示悬浮通知栏需要在系统里面设置
        Intent intent = new Intent(NotificationActivity.this, TestThreadPoolActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationActivity.this, 0, intent, 0);
        Notification notification = new NotificationCompat.Builder(this, "chat")
                .setAutoCancel(true)
                .setContentTitle("收到聊天消息")
                .setContentText("今天晚上吃什么")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                //设置红色
                .setColor(Color.parseColor("#F00606"))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setTicker("xxxxx")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(Notification.DEFAULT_ALL)
                .setVisibility(VISIBILITY_PUBLIC)
                .setContentIntent(pendingIntent)
//                    .setFullScreenIntent(pendingIntent, true)// 此 API 会导致通知栏一直悬浮在上面不消失
                .build();
        manager.notify(1, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        channel.setShowBadge(true);
        channel.enableLights(true);
        manager.createNotificationChannel(channel);
    }


    private void showNotification() {
        mRemoteViews = new RemoteViews(getPackageName(), R.layout.notification_layout);
        Intent intent = new Intent(NotificationActivity.this, TestThreadPoolActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(NotificationActivity.this, 0, intent, 0);
//        mRemoteViews.setOnClickPendingIntent(R.id.notification_root, pendingIntent); //这个点击后在通知栏不会消失

        Glide.with(this)
                .asBitmap()
                .load("http://pic.netbian.com/uploads/allimg/190902/152344-1567409024d50f.jpg")
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        mRemoteViews.setImageViewBitmap(R.id.img_logo, resource);
                        mCnt++;
                        mRemoteViews.setTextViewText(R.id.txt_title, "最福利的消息啊" + mCnt);
                        mRemoteViews.setTextViewText(R.id.txt_abstract, "犯贱贱是个大傻B" + mCnt);
                        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "chat")
//                                .setAutoCancel(true)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setCustomContentView(mRemoteViews)
                                .setContentIntent(pendingIntent)
                                .build();
                        manager.notify(2, notification);
                    }
                });


    }

    public static class MessageBean {
        public String name;
        public String id;
        public int cnt;
    }

}
