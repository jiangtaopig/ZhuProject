package com.example.za_zhujiangtao.zhupro.section_recycle;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.section_recycle.entity.ImageTypeData;
import com.example.za_zhujiangtao.zhupro.section_recycle.entity.MoreTypeBaseData;
import com.example.za_zhujiangtao.zhupro.section_recycle.entity.NormalTypeData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/1/9
 * <p>
 * RecycleView 多种 type
 */
public class RecycleViewMoreTypeActivity extends BaseActivity {

    @BindView(R.id.more_type_recycle_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.tt_tv)
    TextView mTv;

    private int mTvY;
    private int mLastY;

    private MoreTypeAdapter mMoreTypeAdapter;

    @Override
    protected int layoutId() {
        return R.layout.activity_recycleview_more_type_layout;
    }

    @Override
    protected void onInitLogic() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mMoreTypeAdapter = new MoreTypeAdapter();
        mRecyclerView.setAdapter(mMoreTypeAdapter);
        initData();

        "sss".replaceAll("ss", " ");

        mTv.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                int [] location = new int[2];
                mTv.getLocationOnScreen(location);
                int height = mTv.getHeight();
                mTvY = location[1];
                Log.e("RecycleViewMoreType", "x = " + location[0] + ", y = " + location[1]+ ", height = " + height);
                mTv.getViewTreeObserver().removeOnPreDrawListener(this);
                return true;
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
                Log.e("RecycleViewMoreType", "firstVisiblePosition = "+firstVisiblePosition);
                if (mMoreTypeAdapter.getItemCount() > 0) {
                    RecyclerView.ViewHolder holder = mRecyclerView.findViewHolderForAdapterPosition(2);
                    if (holder instanceof MoreTypeAdapter.NormalHolder) {
                        MoreTypeAdapter.NormalHolder normalHolder = (MoreTypeAdapter.NormalHolder) holder;
                        int[] location = new int[2];
                        normalHolder.itemView.getLocationInWindow(location);
                        mLastY = location[1];
                        Log.e("RecycleViewMoreType", "x = " + location[0] + ", y = " + location[1]);
                        if (mLastY <= mTvY){//RececleView 的指定item 滚动到 指定控件的位置，则指定控件透明度为完全不透明
                            mTv.setAlpha(1.0f);
                        }else {
                            mTv.setAlpha(0.3f);
                        }
                    }
                } else {
                    Log.e("RecycleViewMoreType", "not NormalHolder.....................");
                }
            }
        });


        mTv.setOnClickListener(v -> {
           LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            linearLayoutManager.scrollToPositionWithOffset(3, 0);
        });

    }

    private void initData() {
        List<MoreTypeBaseData> typeBaseDataList = new ArrayList<>();
        List<String> property1 = new ArrayList<>();
        property1.add("19平");
        property1.add("有早");

        List<String> tags1 = new ArrayList<>();
        tags1.add("HOT");
        tags1.add("协议价");

        List<String> property2 = new ArrayList<>();
        property2.add("19平");
        property2.add("wifi");
        property2.add("有早");

        List<String> tags2 = new ArrayList<>();
        tags2.add("HOT");
        tags2.add("仅剩一间");
        tags2.add("协议价");
        tags2.add(0, "xx");

        ImageTypeData imageTypeData1 = new ImageTypeData();
        imageTypeData1.tags = tags1;
        imageTypeData1.properties = property1;
        imageTypeData1.title = "红黑树";

        ImageTypeData imageTypeData2 = new ImageTypeData();
        imageTypeData2.tags = tags2;
        imageTypeData2.properties = property2;
        imageTypeData2.title = "即将上市";


        NormalTypeData normalTypeData1 = new NormalTypeData();
        normalTypeData1.tagList = tags2;
        normalTypeData1.propertyList = property2;
        normalTypeData1.price = "12345";
        normalTypeData1.cancelInfo = "不一定能取消";

        NormalTypeData normalTypeData2 = new NormalTypeData();
        normalTypeData2.tagList = tags1;
        normalTypeData2.propertyList = property1;
        normalTypeData2.price = "999";
        normalTypeData2.cancelInfo = "随时取消";

        typeBaseDataList.add(imageTypeData1);
        typeBaseDataList.add(normalTypeData1);
        typeBaseDataList.add(normalTypeData2);

        typeBaseDataList.add(imageTypeData2);
        typeBaseDataList.add(normalTypeData1);
        typeBaseDataList.add(normalTypeData2);
//
        typeBaseDataList.add(imageTypeData1);
        typeBaseDataList.add(normalTypeData1);
        typeBaseDataList.add(normalTypeData2);

        typeBaseDataList.add(imageTypeData2);
        typeBaseDataList.add(normalTypeData2);
        typeBaseDataList.add(normalTypeData1);

        mMoreTypeAdapter.setMoreTypeBaseDataList(typeBaseDataList);
    }
}
