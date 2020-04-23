package com.example.za_zhujiangtao.zhupro.define_view;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.define_view.recycle_view.LinearItemDecoration;
import com.example.za_zhujiangtao.zhupro.define_view.recycle_view.MyLayoutManager;
import com.example.za_zhujiangtao.zhupro.define_view.recycle_view.MyRecycleAdapter;
import com.example.za_zhujiangtao.zhupro.define_view.recycle_view.MyRecycleView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/4/20
 */
public class MyRecycleViewActivity extends BaseActivity {

    private MyRecycleView mRecyclerView;
    private MyRecycleAdapter mMyRecycleAdapter;
    RecyclerView.RecycledViewPool recycledViewPool;

    @Override
    protected int layoutId() {
        return R.layout.activity_my_recycle_view;
    }

    @Override
    protected void onInitLogic() {
        mRecyclerView = findViewById(R.id.my_recycle_view);
        //重写 LayoutManager 那么RecycleView 的高度要设置成 match_parent，或者重写 LayoutManager 的 onMeasure方法
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setLayoutListener(new MyRecycleView.OnLayoutListener() {
            @Override
            public void beforeLayout() {
                Log.e("MyRecycleViewActivity", "beforeLayout");
                try {
                    Field mRecycler =
                            Class.forName("android.support.v7.widget.RecyclerView").getDeclaredField("mRecycler");
                    mRecycler.setAccessible(true);
                    RecyclerView.Recycler recyclerInstance =
                            (RecyclerView.Recycler) mRecycler.get(mRecyclerView);

                    Class<?> recyclerClass = Class.forName(mRecycler.getType().getName());
                    Field mAttachedScrap = recyclerClass.getDeclaredField("mAttachedScrap");
                    mAttachedScrap.setAccessible(true);
                    mAttachedScrap.set(recyclerInstance, new ArrayListWrapper<RecyclerView.ViewHolder>());

                    ArrayList<RecyclerView.ViewHolder> mAttached =
                            (ArrayList<RecyclerView.ViewHolder>) mAttachedScrap.get(recyclerInstance);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterLayout() {
                Log.e("MyRecycleViewActivity", "afterLayout");
                showMessage(mRecyclerView);
            }
        });
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                Log.e("MyRecycleViewActivity", "onScrolled");
                showMessage(mRecyclerView);
            }
        });

        mMyRecycleAdapter = new MyRecycleAdapter();
        mMyRecycleAdapter.setList(generateData());
        mRecyclerView.setAdapter(mMyRecycleAdapter);
        mRecyclerView.addItemDecoration(new LinearItemDecoration(0, 0, 0, 5));

    }

    /**
     * 利用java反射机制拿到RecyclerView内的缓存并打印出来
     */
    private void showMessage(MyRecycleView rv) {
        try {
            Field mRecycler =
                    Class.forName("android.support.v7.widget.RecyclerView").getDeclaredField("mRecycler");
            mRecycler.setAccessible(true);
            RecyclerView.Recycler recyclerInstance = (RecyclerView.Recycler) mRecycler.get(rv);

            Class<?> recyclerClass = Class.forName(mRecycler.getType().getName());
            Field mViewCacheMax = recyclerClass.getDeclaredField("mViewCacheMax");
            Field mAttachedScrap = recyclerClass.getDeclaredField("mAttachedScrap");
            Field mCachedViews = recyclerClass.getDeclaredField("mCachedViews");
            Field mRecyclerPool = recyclerClass.getDeclaredField("mRecyclerPool");
            mViewCacheMax.setAccessible(true);
            mAttachedScrap.setAccessible(true);
            mCachedViews.setAccessible(true);
            mRecyclerPool.setAccessible(true);

            int mViewCacheSize = (int) mViewCacheMax.get(recyclerInstance);
            ArrayList<RecyclerView.ViewHolder> mAttached = (ArrayList<RecyclerView.ViewHolder>) mAttachedScrap.get(recyclerInstance);
            ArrayList<RecyclerView.ViewHolder> mCached = (ArrayList<RecyclerView.ViewHolder>) mCachedViews.get(recyclerInstance);
            RecyclerView.RecycledViewPool recycledViewPool =
                    (RecyclerView.RecycledViewPool) mRecyclerPool.get(recyclerInstance);

            Class<?> recyclerPoolClass = Class.forName(mRecyclerPool.getType().getName());

            Log.e("MyRecycleViewActivity", "mAttachedScrap（一缓） size is:" + mAttached.size() + ", \n" + "mCachedViews（二缓） max size is:" + mViewCacheSize + ","
                    + getMCachedViewsInfo(mCached) + getRVPoolInfo(recyclerPoolClass, recycledViewPool));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getRVPoolInfo(Class<?> aClass, RecyclerView.RecycledViewPool recycledViewPool) {
        try {
            Field mScrapField = aClass.getDeclaredField("mScrap");
            mScrapField.setAccessible(true);
            SparseArray mScrap = (SparseArray) mScrapField.get(recycledViewPool);

            Class<?> scrapDataClass =
                    Class.forName("android.support.v7.widget.RecyclerView$RecycledViewPool$ScrapData");
            Field mScrapHeapField = scrapDataClass.getDeclaredField("mScrapHeap");
            Field mMaxScrapField = scrapDataClass.getDeclaredField("mMaxScrap");
            mScrapHeapField.setAccessible(true);
            mMaxScrapField.setAccessible(true);
            String s = "\n mRecyclerPool（四缓） info:  ";
            for (int i = 0; i < mScrap.size(); i++) {
                ArrayList<RecyclerView.ViewHolder> item =
                        (ArrayList<RecyclerView.ViewHolder>) mScrapHeapField.get(mScrap.get(i));
                for (int j = 0; j < item.size(); j++) {
                    if (j == item.size() - 1) {
                        s += ">>> ";
                    } else if (j == 0) {
                        s += "mScrap[" + i + "] max size is:" + (mMaxScrapField.get(mScrap.get(i)));
                    }
                    s += "mScrap[" + i + "] 中的 mScrapHeap[" + j + "] info is:" + item.get(j) + "\n";
                }
            }
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return "  ";
        }
    }

    private String getMCachedViewsInfo(ArrayList<RecyclerView.ViewHolder> viewHolders) {
        String s = "mCachedViews（二缓） info:  ";
        if (viewHolders.size() > 0) {
            int i = 0;
            for (; i < viewHolders.size(); i++) {
                s += "\n mCachedViews[" + i + "] is " + viewHolders.get(i).toString();
            }

            // append
            if (i == 0) {
                s += "      ";
            } else if (i == 1) {
                s += "    ";
            } else if (i == 2) {
                s += "  ";
            }
        } else {
            s += "      ";
        }
        return s
                + " \n";
    }


    private List<String> generateData() {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            StringBuilder stringBuilder = new StringBuilder("这是第");
            stringBuilder.append(i).append("几行数据");
            list.add(stringBuilder.toString());
        }
        return list;
    }
}

class ArrayListWrapper<T> extends ArrayList<T> {
    public int maxSize = 0;
    public boolean canReset = true;
    private int lastSize = 0;

    @Override
    public boolean remove(Object o) {
        if (size() > maxSize) {
            maxSize = size();
            canReset = false;
        }
        if (size() == 0) {
            canReset = true;
        }
        return super.remove(o);
    }

    @Override
    public boolean add(T t) {
        if (canReset) {
            if (size() + 1 > lastSize) {
                maxSize = size() + 1;
            }
        }
        return super.add(t);
    }
}

