package com.example.za_zhujiangtao.zhupro.dragger_recycleview;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 带边线的网格装饰
 * <p>
 * author liu haonan on 2017/03/08
 */

public class GridDividerItemDecoration extends RecyclerView.ItemDecoration {
    private int mOffset;

    public GridDividerItemDecoration() {
        this(1);
    }

    public GridDividerItemDecoration(int offset) {
        mOffset = offset;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(mOffset, mOffset, mOffset, mOffset);
    }
}
