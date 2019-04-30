package com.example.za_zhujiangtao.zhupro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;

/**
 * Created by za-zhujiangtao on 2019/2/27.
 */

public class TestAlarmManagerActivity extends AppCompatActivity {

    private final static String TAG = TestAlarmManagerActivity.class.getName();

    @BindView(R.id.begin)
    Button mBeginBtn;

    @BindView(R.id.cancel)
    Button mCancelBtn;

    @BindView(R.id.push)
    Button mPush;

    @BindView(R.id.start_time)
    Button mStartTime;

    @BindView(R.id.end_time)
    Button mEndTime;

    Subscription mDisposable;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_layout);
        ButterKnife.bind(this);

        mBeginBtn.setOnClickListener(v -> {
            AlarmService.startActionOn(this);
        });

        mCancelBtn.setOnClickListener(v -> {
            AlarmService.startActionOff(this);
        });

        mPush.setOnClickListener(v -> {
            AlarmService.pushArrival(this);
        });

        mStartTime.setOnClickListener(v -> {
//            startTimer();
//            testRxjava2();
//            testConcat();
//            testZip();
        });

        mEndTime.setOnClickListener(v -> {
//            dispose();
        });

    }


    /**
     * 想必在实际应用中，很多时候（对数据操作不敏感时）都需要我们先读取缓存的数据，如果缓存没有数据，再通过网络请求获取，随后在主线程更新我们的UI。
     * concat 操作符简直就是为我们这种需求量身定做。
     * 利用 concat 的必须调用 onComplete 后才能订阅下一个 Observable 的特性，我们就可以先读取缓存数据，倘若获取到的缓存数据不是我们想要的，再调用 onComplete() 以执行获取网络数据的 Observable，
     * 如果缓存数据能应我们所需，则直接调用 onNext()，防止过度的网络请求，浪费用户的流量。
     */
    private void testConcat() {
        List<String> cacheList = new ArrayList<>();
        cacheList.add("1");
        cacheList.add("2");
//        cacheList.add("3");
//        cacheList.add("4");


    }

    /**
     * zip 如果其中有一个 Observable 出错而不想影响其他的 Observable ，则使用 onErrorResumeNext
     */
    private void testZip() {
//        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
//
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("1");
//                emitter.onNext("2");
//                String ss = "在";
//                int a = Integer.valueOf(ss);
//            }
//        }).onErrorResumeNext(throwable -> {
////                    throwable.onNext("error");
//            Log.e(TAG, "observable1 出错啦");
//            return Observable.just("error");
//        });
//
//        Observable<String> observable2 = Observable.just("a", "n", "c");
//
//        Observable.zip(observable1, observable2, (s1, s2) -> {
//            return s1 + s2;
//        }).observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String r) throws Exception {
//                        Log.e(TAG, "r = " + r);
//                    }
//                });
    }


}

class Test{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
