package com.example.za_zhujiangtao.zhupro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

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

    Disposable mDisposable;


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
            testFlatMap();
        });

        mEndTime.setOnClickListener(v -> {
//            dispose();
        });

    }

    private void testRxjava2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.e(TAG, "Observable emit 1" + "\n");
                emitter.onNext(1);
                Log.e(TAG, "Observable emit 2" + "\n");
                emitter.onNext(2);
                Log.e(TAG, "Observable emit 3" + "\n");
                emitter.onNext(3);
                emitter.onComplete();
                Log.e(TAG, "Observable emit 4" + "\n");
                emitter.onNext(4);
            }
        }).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                integer += 10;
            }
        }).subscribe(new Observer<Integer>() {

            Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                Log.e(TAG, "integer = " + integer);
                if (integer == 2) {
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }
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
        Observable<List<String>> cache = Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                if (cacheList.size() > 3) {
                    emitter.onNext(cacheList);
                } else {
                    emitter.onComplete();
                }
            }
        });

        Observable<List<String>> net = Observable.create(new ObservableOnSubscribe<List<String>>() {
            @Override
            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                List<String> nets = new ArrayList<>();
                nets.add("a");
                nets.add("b");
                nets.add("c");
                emitter.onNext(nets);
                emitter.onComplete();
            }
        });

        Observable.concat(cache, net)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<String>>() {
                    @Override
                    public void accept(List<String> strings) throws Exception {
                        Log.e(TAG, "strings = " + strings);
                    }
                });

    }

    /**
     * zip 如果其中有一个 Observable 出错而不想影响其他的 Observable ，则使用 onErrorResumeNext
     */
    private void testZip() {
        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("1");
                emitter.onNext("2");
                String ss = "在";
                int a = Integer.valueOf(ss);
            }
        }).onErrorResumeNext(throwable -> {
//                    throwable.onNext("error");
            Log.e(TAG, "observable1 出错啦");
            return Observable.just("error");
        });

        Observable<String> observable2 = Observable.just("a", "n", "c");

        Observable.zip(observable1, observable2, (s1, s2) -> {
            return s1 + s2;
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String r) throws Exception {
                        Log.e(TAG, "r = " + r);
                    }
                });
    }

    private void testFlatMap(){
        List<String> nets = new ArrayList<>();
        nets.add("a");
        nets.add("b");
        nets.add("c");

        Observable.just(nets)
                .flatMap(new Function<List<String>, Observable<Test>>() {
                    @Override
                    public Observable<Test> apply(List<String> strings) throws Exception {
                        Test t = new Test();
                        t.setName(strings.get(0));
                        return Observable.just(t);
                    }
                }).subscribe(test -> {
                    Log.e(TAG, "name = "+ test.getName());
        });
    }

    private void dispose() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private void startTimer() {
        Observable.interval(0, 5000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(aLong -> {
                    Log.e(TAG, "aLong = " + aLong);
                    return Observable.just("a", "b", "c");

                }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "s = " + s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
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
