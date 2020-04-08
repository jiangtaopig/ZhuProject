package com.example.za_zhujiangtao.zhupro.http;

import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.api.DataBase;
import com.example.za_zhujiangtao.zhupro.http.api.MyFunc1Subscriber;
import com.example.za_zhujiangtao.zhupro.http.api.MyObjFunc1;
import com.example.za_zhujiangtao.zhupro.http.test.TestApi;

import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
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
//            mTestApi.getMyData(4, 10)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Action1<DataBase>() {
//                        @Override
//                        public void call(DataBase dataBase) {
//                            if (dataBase != null){
//                                Log.e("test http", "size = "+dataBase.getData().size());
//                            }
//                        }
//                    });

            mTestApi.getMukeData(4, 10)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(new MyObjFunc1<>())
                    .subscribe(new MyFunc1Subscriber<List<DataBase.DataBean>>() {
                        @Override
                        public void onNext(List<DataBase.DataBean> dataBeans) {
                            if (dataBeans != null){

                            }
                        }
                    });

        });
    }
}
