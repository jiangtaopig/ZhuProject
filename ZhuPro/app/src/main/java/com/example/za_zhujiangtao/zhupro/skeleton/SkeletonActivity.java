package com.example.za_zhujiangtao.zhupro.skeleton;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/12/24
 */
public class SkeletonActivity extends BaseActivity {

    private SkeletonScreen skeletonScreen;

    @BindView(R.id.recycle_view)
    RecyclerView recyclerView;

    private TestAdapter testAdapter;

    @Override
    protected int layoutId() {
        return R.layout.activity_skeleton_layout;
    }

    @Override
    protected void onInitLogic() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        testAdapter = new TestAdapter();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                int lastVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                Log.e("zjt", "onScrolled firstVisibleItemPosition = " + firstVisibleItemPosition + ", lastVisibleItemPosition = " + lastVisibleItemPosition);
            }
        });

        skeletonScreen = Skeleton.bind(recyclerView)
                .adapter(testAdapter)
                .shimmer(true)//是否显示shimmer动画，默认显示
                .angle(20)//shimmer 动画角度，默认 20度
                .duration(1000)
                .frozen(true)//skeleton 显示时是否RecycleView 不可操作，默认不可操作
                .load(R.layout.skeleton_default_layout)
                .color(R.color.color_ffd7d7d7)
                .show();

        AndroidSchedulers.mainThread().createWorker().schedule(new Action0() {
            @Override
            public void call() {
                Log.e("zjt", "...............");
                List<ItemData> itemDataList = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    ItemData itemData = new ItemData();
                    itemData.date = 2010 + (i + 1) + "";
                    itemData.title = "我是title" + (i + 1);
                    itemData.subTitle = "我是subTitle" + (i + 1);
                    itemDataList.add(itemData);
                }
                testAdapter.setDataList(itemDataList);
                skeletonScreen.hide();// 关闭Skeleton，就会自动绑定Adapter，显示真正数据
            }
        }, 5000, TimeUnit.MILLISECONDS);

    }


}
