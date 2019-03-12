package com.example.za_zhujiangtao.zhupro;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.Subject;

/**
 * Created by za-zhujiangtao on 2019/2/27.
 */

public class AlarmService extends Service {
    private final static String TAG = "AlarmService";

    private static final String ACTION_ON = "com.example.za_zhujiangtao.zhupro.action.on";
    private static final String ACTION_OFF = "com.example.za_zhujiangtao.zhupro.action.off";
    private static final String ACTION_PUSH = "com.example.za_zhujiangtao.zhupro.action.push";


    private AlarmManager mAlarmManager;
    private PendingIntent mAlarmIntent;

    private Disposable mTimerDisposable;

    private TextToSpeech textToSpeech;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = textToSpeech.setLanguage(Locale.CHINA);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(getApplicationContext(), "数据丢失或不支持", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void speak(String txt){
        if (textToSpeech != null) {
            // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
            textToSpeech.setPitch(1.5f);
            //设定语速 ，默认1.0正常语速
            textToSpeech.setSpeechRate(1.2f);
            //朗读，注意这里三个参数的added in API level 4   四个参数的added in API level 21
            textToSpeech.speak(txt, TextToSpeech.QUEUE_ADD, null);
        }
    }

    /**
     * 开始轮询进程
     *
     * @param context
     */
    public static void startActionOn(Context context) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction(ACTION_ON);
        context.startService(intent);
    }

    /**
     * 结束轮询进程
     *
     * @param context
     */
    public static void startActionOff(Context context) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction(ACTION_OFF);
        context.startService(intent);
    }

    public static void pushArrival(Context context) {
        Intent intent = new Intent(context, AlarmService.class);
        intent.setAction(ACTION_PUSH);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            Log.e(TAG, "onStartCommand action = " + action);
            switch (action) {
                case ACTION_ON:
                    awakeAlarm();
//                    startTimer();

                    break;
                case ACTION_OFF:
                    powerOff();
                    break;
                case ACTION_PUSH:
                    speak("Yo yo check now，最商家收款35289元，好嗨哟,禤靐龘");
                    Observable.timer(300, TimeUnit.MILLISECONDS)
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    speak("哈哈哈哈");
                                }
                            });
                    break;
            }
        }
        return START_STICKY;
    }

    private void awakeAlarm() {
        stopWakeAlarm();
        mAlarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, this.getClass());
        intent.setAction(ACTION_ON);
        mAlarmIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (mAlarmManager != null) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
                mAlarmManager.setWindow(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), 3 * 60 * 1000, mAlarmIntent);
            }

        }
    }

    private void stopWakeAlarm() {
        if (mAlarmIntent != null) {
            if (mAlarmManager != null) {
                mAlarmManager.cancel(mAlarmIntent);
            }
            mAlarmIntent = null;
        }
    }

    private void startTimer(){
        Observable.interval(0,5000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subject<Long>() {
                    @Override
                    public boolean hasObservers() {
                        return false;
                    }

                    @Override
                    public boolean hasThrowable() {
                        return false;
                    }

                    @Override
                    public boolean hasComplete() {
                        return false;
                    }

                    @Override
                    public Throwable getThrowable() {
                        return null;
                    }

                    @Override
                    protected void subscribeActual(Observer<? super Long> observer) {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        mTimerDisposable = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.e(TAG, "aLong = "+aLong);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 关闭服务
     */
    private void powerOff() {
        stopWakeAlarm();
        stopSelf();
//        mTimerDisposable.dispose();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy");
        textToSpeech.stop(); // 不管是否正在朗读TTS都被打断
        textToSpeech.shutdown(); // 关闭，释放资源
    }
}
