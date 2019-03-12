package com.example.za_zhujiangtao.zhupro.v_layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.za_zhujiangtao.zhupro.v_layout.MyAdapter.AD_TYPE;
import static com.example.za_zhujiangtao.zhupro.v_layout.MyAdapter.NORMAL_TYPE;

/**
 * Created by za-zhujiangtao on 2019/1/2.
 */

public class TestVlayoutActivity extends AppCompatActivity {

    @BindView(R.id.my_recycle)
    RecyclerView mRecyclerView;

    private VirtualLayoutManager mVirtualLayoutManager;
    private DelegateAdapter mDelegateAdapter;
    private List<DelegateAdapter.Adapter> mAdapters;
    private RecyclerView.RecycledViewPool mViewPool;


    private MyAdapter mMyAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vlayout_layout);
        ButterKnife.bind(this);

        mVirtualLayoutManager = new VirtualLayoutManager(this);
        mRecyclerView.setLayoutManager(mVirtualLayoutManager);
        mViewPool = new RecyclerView.RecycledViewPool();
        mViewPool.setMaxRecycledViews(0, 10);
        mRecyclerView.setRecycledViewPool(mViewPool);
        mDelegateAdapter = new DelegateAdapter(mVirtualLayoutManager, true);
        mRecyclerView.setAdapter(mDelegateAdapter);
        mAdapters = new ArrayList<>();

        mMyAdapter = new MyAdapter(this, new LinearLayoutHelper());
        mAdapters.add(mMyAdapter);
        mDelegateAdapter.setAdapters(mAdapters);

        List<TestData> testData = new ArrayList<>();
        for (int i = 0; i < 10; i++){
            TestData data = new TestData();
            data.title = "我收钱啊"+(i+1);
            if (i % 2 == 0){
                data.type = NORMAL_TYPE;
            }else {
                data.type = AD_TYPE;
            }
            testData.add(data);
        }

        mMyAdapter.setList(testData);

    }
}
