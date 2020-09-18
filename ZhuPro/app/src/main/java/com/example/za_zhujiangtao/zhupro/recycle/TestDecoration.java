package com.example.za_zhujiangtao.zhupro.recycle;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.za_zhujiangtao.zhupro.float_window.DisplayUtil;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/9/17
 */
public class TestDecoration extends RecyclerView.ItemDecoration {

    private final static int RADIUS = 10;
    private Paint mCirclePaint;
    private Paint mLinePaint;
    private Paint mPaint;
    private int mHeight = DisplayUtil.dip2px(3);

    public TestDecoration() {
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStrokeWidth(3);
        mCirclePaint.setStyle(Paint.Style.STROKE);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(Color.BLUE);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int cnt = parent.getChildCount();
        final int left = parent.getLeft();
        final int right = parent.getRight();
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        for (int i = 0; i < cnt; i++) {
//            drawCircleAndLine(c, parent, manager, i);
            final View childView = parent.getChildAt(i);
            final int bottom = childView.getTop(); // i != 0 时，getTop等于 top值加上在getItemOffsets中设置的outRect.top
            final int top = bottom - mHeight;
            Log.e("TestDecoration", "bottom = " + bottom + ", top = " + top);

            c.drawRect(left, top, right, bottom, mPaint);
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // 相当于在item的top上撑开3dp得的距离
        int position = parent.getChildAdapterPosition(view);
        if (position != 0) {
            //第一个item预留空间
            outRect.top = mHeight;
        }
//        outRect.left = DisplayUtil.dip2px(50);
    }

    private void drawCircleAndLine(@NonNull Canvas c, @NonNull RecyclerView parent, RecyclerView.LayoutManager manager, int i) {
        View child = parent.getChildAt(i);
        int cx = manager.getLeftDecorationWidth(child) / 2;// 获取left decoration的值
        int cy = child.getTop() + child.getHeight() / 2;
        c.drawCircle(cx, cy, RADIUS, mCirclePaint);

        int startX = cx;
        int startY = child.getTop();
        int endY = child.getTop() + child.getHeight() + mHeight; // 加上距离顶部的3dp, getItemOffsets 中设置的 top = 3dp
        Log.e("TestDecoration", "top = " + child.getTop() + ", height = " + child.getHeight());

        c.drawLine(startX, startY, startX, endY, mLinePaint);
    }
}
