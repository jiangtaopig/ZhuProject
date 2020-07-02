package com.example.za_zhujiangtao.zhupro.calendar;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.za_zhujiangtao.zhupro.R;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.TransitionManager;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/2
 */
public class MonthCalendarView extends LinearLayout {

    private RecyclerView mRecyclerView;
    private ViewGroup mContainerLayout;
    private View mShadowView;
    private MonthCalendarAdapter mMonthCalendarAdapter;

    public MonthCalendarView(Context context) {
        this(context, null);
    }

    public MonthCalendarView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthCalendarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(getContext()).inflate(R.layout.month_calendar_view, this, true);
        mRecyclerView = findViewById(R.id.recycle_view);
        mContainerLayout = findViewById(R.id.container_layout);
        mShadowView = findViewById(R.id.shadow_view);

        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 7));
        mMonthCalendarAdapter = new MonthCalendarAdapter();
        mRecyclerView.setAdapter(mMonthCalendarAdapter);
    }

    public void setMonthData(String monthStr){
        mMonthCalendarAdapter.setDateStr(monthStr);
    }

    public void makeShadowInvisible() {
        TransitionManager.beginDelayedTransition(mContainerLayout,
                new Fade().setInterpolator(new DecelerateInterpolator()));
        mShadowView.setVisibility(View.GONE);
    }

    public void makeShadowVisible() {
        TransitionManager.beginDelayedTransition(mContainerLayout,
                new Fade().setInterpolator(new DecelerateInterpolator()));
        mShadowView.setVisibility(View.VISIBLE);
    }
}
