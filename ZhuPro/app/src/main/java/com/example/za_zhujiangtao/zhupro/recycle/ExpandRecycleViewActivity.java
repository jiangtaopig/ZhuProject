package com.example.za_zhujiangtao.zhupro.recycle;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.float_window.DisplayUtil;

import java.util.Random;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/9
 */
public class ExpandRecycleViewActivity extends BaseActivity {
    private final static int ITEM_HEIGHT = DisplayUtil.dip2px(23);
    private final static int PADDING = DisplayUtil.dip2px(20);
    private final static int MARGIN = DisplayUtil.dip2px(4);
    private final static int MAX_HEIGHT = DisplayUtil.dip2px(334);

    private RecyclerView recyclerView;
    private RecycleAdapter recycleAdapter;
    private TextView sendMsg;
    private LinearLayout expandLayout;
    private View tagView;
    private TextView titleTv;
    private ImageView moreImg;
    private FrameLayout frameLayout;

    private int maxHeight;
    private int defaultHeight;
    private boolean isExpand;


    @Override
    protected int layoutId() {
        return R.layout.activity_expand_recycle_view_layout;
    }

    @Override
    protected void onInitLogic() {
        sendMsg = findViewById(R.id.sendMsg);
        recyclerView = findViewById(R.id.recycle_view);
        expandLayout = findViewById(R.id.expand_layout);
        tagView = findViewById(R.id.tag_view);
        titleTv = findViewById(R.id.txt_title);
        moreImg = findViewById(R.id.img_more);
        frameLayout = findViewById(R.id.frame_layout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new RecycleAdapter();
        recyclerView.setAdapter(recycleAdapter);

        expandLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                float x = recyclerView.getX();
                float y = recyclerView.getY();
                float frameLayoutX = frameLayout.getX();
                float frameLayoutY = frameLayout.getY();
                Log.e("TestRecycleViewActivity", " x = " + x + ", y = " + y + ", frameLayoutX = " + frameLayoutX + ", frameLayoutY = " + frameLayoutY);
                if (expandLayout.getHeight() >= defaultHeight) {
                    expandLayout.getLayoutParams().height = defaultHeight; // 设置 RecycleView 的最大高度
                    expandLayout.getViewTreeObserver().removeOnPreDrawListener(this);
                }
                return true;
            }
        });

        sendMsg.setOnClickListener(v -> {
            int random = new Random().nextInt(20) + 1;
            String name = "小安" + random;
            String msg = "消息" + random;
            addMessage(new MsgBean(name, msg));
        });

        maxHeight = (int) getResources().getDimension(R.dimen.dimen_300dp);
        defaultHeight = (int) getResources().getDimension(R.dimen.dimen_97dp);
        tagView.setBackground(getDrawable());
        titleTv.setTypeface(Typeface.DEFAULT_BOLD);

        moreImg.setOnClickListener(v -> {
            isExpand = !isExpand;
            performAnimation(expandLayout, defaultHeight, maxHeight, isExpand);
        });

    }

    private void performAnimation(View targetView, int start, int end, boolean expand) {
        Log.e("TestRecycleViewActivity", "start = " + start + ", end = " + end);
        ValueAnimator animator = ValueAnimator.ofInt(0, 100);
        animator.addUpdateListener(animation -> {
            float fraction = animation.getAnimatedFraction();
            int value;
            if (expand) {
                value = (int) (start + fraction * (end - start));
            } else {
                value = (int) (end - fraction * (end - start));
            }
            targetView.getLayoutParams().height = value;
            targetView.requestLayout();
        });

        ObjectAnimator rotate;
        if (expand) {
            rotate = ObjectAnimator.ofFloat(moreImg, "rotation", 0f, 180f);
        } else {
            rotate = ObjectAnimator.ofFloat(moreImg, "rotation", 180f, 0f);
        }
        rotate.setInterpolator(new BounceInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator, rotate);
        animatorSet.setDuration(250);
        animatorSet.start();
    }

    public void addMessage(MsgBean msgBean) {
        recycleAdapter.addData(msgBean);
        recycleAdapter.notifyDataSetChanged();
        int cnt = recycleAdapter.getItemCount();
        if (cnt > 3) {
            moreImg.setVisibility(View.VISIBLE);
            maxHeight = PADDING + MARGIN * (cnt - 1) + ITEM_HEIGHT * cnt;
            if (maxHeight > MAX_HEIGHT) {
                maxHeight = MAX_HEIGHT;
            }
            Log.e("TestRecycleViewActivity", "maxHeight = " + maxHeight);
        } else {
            moreImg.setVisibility(View.INVISIBLE);
        }
    }

    private GradientDrawable getDrawable() {
        GradientDrawable drawable = new GradientDrawable();
        float corner = getResources().getDimension(R.dimen.dimen_4dp);
        // 设置部分圆角：top-left, top-left; top-right, top-right; bottomRight, bottomRight; bottomLeft, bottomLeft;
        float[] corners = {corner, corner, 0, 0, 0, 0, corner, corner};
        drawable.setCornerRadii(corners);
        drawable.setColor(Color.parseColor("#00aecb"));
//        drawable.setColor(getResources().getColor(R.color.color_12b398));
        return drawable;
    }
}
