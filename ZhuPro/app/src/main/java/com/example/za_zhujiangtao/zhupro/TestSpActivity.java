package com.example.za_zhujiangtao.zhupro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.example.za_zhujiangtao.zhupro.bean.ResultBean;
import com.example.za_zhujiangtao.zhupro.utils.NavigatorUtils;
import com.example.za_zhujiangtao.zhupro.utils.SharePreferenceUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by za-zhujiangtao on 2019/1/4.
 */

public class TestSpActivity extends AppCompatActivity {

    @BindView(R.id.add)
    Button mAdd;

    @BindView(R.id.stop_task)
    Button mStopTask;

    private Subscription mSubscription;

    private List<String> mList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_sp_layout);
        ButterKnife.bind(this);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = "zjt|";
                mList.add(val);
                Gson gson = new Gson();
                String vv = gson.toJson(mList);

                String spV = SharePreferenceUtils.getString(TestSpActivity.this, "nna");
                SharePreferenceUtils.putString(TestSpActivity.this, "nna", vv);
//                parseJson();
//                testMerge();

                boolean isShow = NavigatorUtils.checkNavigationBarShow(TestSpActivity.this);
                doTask();
            }
        });

        mStopTask.setOnClickListener(v -> {
            boolean isUnsubscribed = mSubscription.isUnsubscribed();
            Log.e("zjt", "isUnsubscribed = " + isUnsubscribed);
            if (!isUnsubscribed) {
                //取消任务
                mSubscription.unsubscribe();
            }
        });

        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY, "iSvsHxbYXGNQ4Iiv9cFAmiIt");
    }

    private void doTask() {
        Log.e("zjt", "doTask" );
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
        mSubscription = Observable.just("1")
                .map(s -> {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return "耗时的工作";
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.e("zjt", "s = " + s);
                });
    }


    private void testMerge() {
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");

        List<String> list2 = new ArrayList<>();
        list2.add("4");
        list2.add("5");
        list2.add("6");

        Observable.merge(Observable.from(list1), Observable.from(list2))
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {

                        Log.e("xxx", "onNext s = " + s);
                    }
                });

        ResultBean resultBean = new ResultBean();
        List<ResultBean.DetailBean> detailBeans = new ArrayList<>();
        ResultBean.DetailBean detailBean1 = new ResultBean.DetailBean();
        detailBean1.setName("d1");

        ResultBean.DetailBean detailBean2 = new ResultBean.DetailBean();
        detailBean2.setName("d2");

        detailBeans.add(detailBean1);
        detailBeans.add(detailBean2);

        resultBean.setmDetailBeanList(detailBeans);

        List<ResultBean.DetailBean> recommendBean = new ArrayList<>();
        ResultBean.DetailBean r1 = new ResultBean.DetailBean();
        r1.setName("r1");

        ResultBean.DetailBean r2 = new ResultBean.DetailBean();
        r2.setName("r2");
        recommendBean.add(r1);
        recommendBean.add(r2);

        resultBean.setmRecommendBeanList(recommendBean);

        Observable.merge(Observable.from(resultBean.getmDetailBeanList()), Observable.from(resultBean.getmRecommendBeanList()))
                .map(detailbean -> {
                    detailbean.setPhone("123");
                    return detailbean;
                }).count()
                .map(integer -> {
                    return resultBean;
                }).subscribe(resultBean1 -> {
            if (resultBean1 != null) {
                resultBean1.getmDetailBeanList();
            }
        });

        Observable.from(list1)
                .flatMap(s -> {
                    return Observable.just(s);
                }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });


    }

    private void parseJson() {
        String jsonStr = "{ \"code\": \"0\", \"result\": { \"sessionId\": \"BrXu71oB7SZh\", \"msgId\": \"m3f24703c6c3942fd9c429b33a4f59d00\", \"date\": 1547195554623, \"type\": \"INTENT\", \"intent\": { \"id\": 7083, \"name\": \"ATTENDANCE\" }, \"entities\": [], \"conversation\": { \"source\": \"我要打卡\", \"status\": \"END\", \"cards\": [ { \"cardType\": \"TEXT_IMAGE_LIST\", \"content\": { \"list\": [ { \"banner\": \"\", \"title\": \"食堂\", \"subTitle\": \"加班餐\", \"keyDescription\": \"食堂\", \"description\": \"\", \"url\": \"\" }, { \"banner\": \"\", \"title\": \"滴滴出行\", \"subTitle\": \"打车\", \"keyDescription\": \"约车\", \"description\": \"\", \"url\": \"\" } ] } }, { \"cardType\": \"TEXT\", \"content\": { \"text\": \"fdsfdsfds\" } } ], \"context\": { \"params\": { \"latitude\": \"12341234123\", \"daka\": \"true\" } } } } }";

        Gson gson = new Gson();
        gson.fromJson(jsonStr, Bean.class);

        String json2 = "{\n" +
                "\t\t\t\"source\": \"我要打卡\",\n" +
                "\t\t\t\"status\": \"END\",\n" +
                "\t\t\t\"cards\": [{\n" +
                "\t\t\t\t\"cardType\": \"TEXT_IMAGE_LIST\",\n" +
                "\t\t\t\t\"content\": {\n" +
                "\t\t\t\t\t\"list\": [{\n" +
                "\t\t\t\t\t\t\"banner\": \"\",\n" +
                "\t\t\t\t\t\t\"title\": \"食堂\",\n" +
                "\t\t\t\t\t\t\"subTitle\": \"加班餐\",\n" +
                "\t\t\t\t\t\t\"keyDescription\": \"食堂\",\n" +
                "\t\t\t\t\t\t\"description\": \"\",\n" +
                "\t\t\t\t\t\t\"url\": \"\"\n" +
                "\t\t\t\t\t}, {\n" +
                "\t\t\t\t\t\t\"banner\": \"\",\n" +
                "\t\t\t\t\t\t\"title\": \"滴滴出行\",\n" +
                "\t\t\t\t\t\t\"subTitle\": \"打车\",\n" +
                "\t\t\t\t\t\t\"keyDescription\": \"约车\",\n" +
                "\t\t\t\t\t\t\"description\": \"\",\n" +
                "\t\t\t\t\t\t\"url\": \"\"\n" +
                "\t\t\t\t\t}]\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}, {\n" +
                "\t\t\t\t\"cardType\": \"TEXT\",\n" +
                "\t\t\t\t\"content\": {\n" +
                "\t\t\t\t\t\"text\": \"fdsfdsfds\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}],\n" +
                "\t\t\t\"context\": {\n" +
                "\t\t\t\t\"params\": {\n" +
                "\t\t\t\t\t\"latitude\": \"12341234123\",\n" +
                "\t\t\t\t\t\"daka\": \"true\"\n" +
                "\t\t\t\t}\n" +
                "\t\t\t}\n" +
                "\t\t}";

        Bean2 bean2 = gson.fromJson(json2, Bean2.class);
        List<Bean2.Card> cards = bean2.cards;
        for (Bean2.Card card : cards) {
            if (card.cardType.equals("TEXT_IMAGE_LIST")) {
                JSONObject object = card.content;
                if (object != null) {
                    JSONArray array = object.getJSONArray("list");
                    JSONObject object1 = array.getJSONObject(0);
                    String str = object1.toJSONString();
                    ImageAndText imageAndText = gson.fromJson(str, ImageAndText.class);//
                    array.size();
                }
            }
        }
    }

    class Bean {
        String code;
        JSONObject result;
    }

    class Bean2 {
        String source;
        List<Card> cards;

        class Card {
            String cardType;
            JSONObject content;
        }
    }

    public static class ImageAndText {
        String banner;
        String title;
        String subTitle;
        String keyDescription;
        String description;
        String url;
    }
}
