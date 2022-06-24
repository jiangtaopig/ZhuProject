package com.example.za_zhujiangtao.zhupro.recycle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import butterknife.BindView;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/12/25
 */
public class TestRecycleCacheActivity extends BaseActivity {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    @BindView(R.id.txt_tv)
    TextView titleTv;

    private CacheAdapter cacheAdapter;

    @Override
    protected int layoutId() {
        return R.layout.activity_recycle_cache_layout;
    }

    @Override
    protected void onInitLogic() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cacheAdapter = new CacheAdapter();
        recyclerView.setAdapter(cacheAdapter);

        List<String> dataList = new ArrayList<>();
        for (int i = 1; i < 20; i++) {
            dataList.add("数据" + i);
        }

        cacheAdapter.setDataList(dataList);

        titleTv.setOnClickListener(v -> {
            cacheAdapter.notifyDataSetChanged();
        });

        FutureTask<Integer> futureTask = new FutureTask<>(new MyWorker());
        new Thread(futureTask)
                .start();

    }

    class MyWorker implements Callable<Integer>{

        @Override
        public Integer call() throws Exception {
            Thread.sleep(3_000);
            return 34;
        }
    }
}
