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
 * on 2021/1/7
 * 测试 ScrollView 中嵌套 RecycleView 同向滑动的滑动冲突；
 * ScrollView 默认是拦截 MOVE 事件的
 */
public class TestInnerInterceptActivity extends BaseActivity {

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    private CacheAdapter cacheAdapter;

    @Override
    protected int layoutId() {
        return R.layout.activity_inner_intercept_layout;
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
    }
}
