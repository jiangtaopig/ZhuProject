package com.example.za_zhujiangtao.zhupro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.za_zhujiangtao.zhupro.dragger_recycleview.DraggerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by za-zhujiangtao on 2018/12/13.
 */

public class DraggerRecycleviewActivity extends AppCompatActivity {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    private DraggerAdapter mDraggerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragger_recycleview_layout);

        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        mDraggerAdapter = new DraggerAdapter(this, mRecyclerView);
        mRecyclerView.setAdapter(mDraggerAdapter);

        List<String> data = new ArrayList<>();
        for (int i = 1; i < 7; i++){
            data.add("数据"+i);
        }
        mDraggerAdapter.setData(data);
    }
}
