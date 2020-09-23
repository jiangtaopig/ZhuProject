package com.example.za_zhujiangtao.zhupro.recycle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/9/17
 */
public class ItemDecorationActivity extends BaseActivity {

    @BindView(R.id.recycle_view)
    RecyclerView mRecyclerView;

    private ItemAdapter mAdapter;

    @Override
    protected int layoutId() {
        return R.layout.activity_item_decoration_layout;
    }

    @Override
    protected void onInitLogic() {
        mAdapter = new ItemAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new TestDecoration());
        mRecyclerView.setAdapter(mAdapter);

        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 5; i++){
            dataList.add("数据 ： "+i);
        }

        mAdapter.setList(dataList);

    }
}
