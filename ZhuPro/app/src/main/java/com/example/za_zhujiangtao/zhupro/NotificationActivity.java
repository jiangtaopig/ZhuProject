package com.example.za_zhujiangtao.zhupro;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.za_zhujiangtao.zhupro.define_view.TestThreadPoolActivity;

import butterknife.BindView;

import static android.support.v4.app.NotificationCompat.VISIBILITY_PUBLIC;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/12/23
 */
public class NotificationActivity extends BaseActivity {

    @BindView(R.id.txt_msg)
    TextView textView;

    private NotificationManager manager;

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
                    .setPriority(NotificationCompat.PRIORITY_MAX)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setVisibility(VISIBILITY_PUBLIC)
                    .setFullScreenIntent(pendingIntent, true)
                    .build();
            manager.notify(1, notification);

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(
//                NOTIFICATION_SERVICE);
        channel.setShowBadge(true);
        channel.enableLights(true);
        manager.createNotificationChannel(channel);
    }

}
