package com.example.za_zhujiangtao.zhupro.calendar;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/2
 */
public class MonthCalendarActivity extends BaseActivity {
    private static final SimpleDateFormat mSdf = new SimpleDateFormat("yyyy-MM-dd");

    private TextView mDateTitle;
    private ViewPager mViewPager;

    private MonthCalendarPagerAdapter mMonthCalendarPagerAdapter;
    private List<MonthCalendarView> mMonthCalendarViewList;
    private MonthCalendarView mCurCalendarView;

    @Override
    protected int layoutId() {
        return R.layout.activity_month_calendar_layout;
    }

    @Override
    protected void onInitLogic()  {
        mDateTitle = findViewById(R.id.txt_date);
        mViewPager = findViewById(R.id.view_pager);
        try {
            Field field = ViewPager.class.getDeclaredField("mScroller");
            field.setAccessible(true);
            PagerScroller scroller = new PagerScroller(this);
            scroller.setDuration(120);
            field.set(mViewPager, scroller);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        mMonthCalendarViewList = new ArrayList<>();
        mMonthCalendarPagerAdapter = new MonthCalendarPagerAdapter(mMonthCalendarViewList);

        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(mMonthCalendarPagerAdapter);

        List<String> monthDataList = parseData();

        mMonthCalendarPagerAdapter.setMonthDataList(monthDataList);
        mViewPager.setCurrentItem(1, false);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.e("zhu_aron____", "onPageSelected i = " + i);
                if (i == 2) {
                    monthDataList.remove(0);
                    String nextMonth = DateUtils.getNextMonthData(monthDataList.get(monthDataList.size() - 1));
                    monthDataList.add(nextMonth);
                    mViewPager.removeAllViews();
                    mMonthCalendarPagerAdapter.setMonthDataList(monthDataList);
                    mViewPager.setCurrentItem(1, false);
                } else if (i == 0) {
                    monthDataList.remove(monthDataList.size() - 1);
                    String lastMonth = DateUtils.getLastMonthData(monthDataList.get(0));
                    monthDataList.add(0, lastMonth);
                    mViewPager.removeAllViews();
                    mMonthCalendarPagerAdapter.setMonthDataList(monthDataList);
                    mViewPager.setCurrentItem(1, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {
                Log.e("zhu_aron____", "onPageScrollStateChanged i = " + i);
                if (i == ViewPager.SCROLL_STATE_DRAGGING){// 表示按下且开始拖动，不拖动则不执行此状态
                    if (mCurCalendarView != null){
//                        mCurCalendarView.makeShadowVisible();
                    }
                }else if (i == ViewPager.SCROLL_STATE_SETTLING){ // 表示手指离开屏幕的状态
//                    mCurCalendarView.makeShadowInvisible();
                }
            }
        });
    }

    private List<String> parseData() {
        Date curDate = new Date();
        String curMonthDateStr = mSdf.format(curDate);
        String lastMonthDateStr = DateUtils.getLastMonthData(curMonthDateStr);
        String nextMonthDateStr = DateUtils.getNextMonthData(curMonthDateStr);
        List<String> monthDataList = new ArrayList<>();
        monthDataList.add(lastMonthDateStr);
        monthDataList.add(curMonthDateStr);
        monthDataList.add(nextMonthDateStr);
        return monthDataList;
    }

    class MonthCalendarPagerAdapter extends PagerAdapter {
        private List<String> monthDataList;
        List<MonthCalendarView> monthCalendarViewList;

        public MonthCalendarPagerAdapter(List<MonthCalendarView> calendarViewList) {
            monthDataList = new LinkedList<>();
            monthCalendarViewList = calendarViewList;
        }

        public void setMonthDataList(List<String> dataList) {
            monthDataList.clear();
            monthDataList.addAll(dataList);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return monthDataList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            Log.e("zhu_aron", "instantiateItem position = " + position);
            MonthCalendarView calendarView = new MonthCalendarView(container.getContext());
            calendarView.setMonthData(monthDataList.get(position));
            monthCalendarViewList.add(calendarView);
            container.addView(calendarView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return calendarView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(monthCalendarViewList.get(position));
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);
            Log.e("zhu_aron____", "setPrimaryItem position = " + position);
            mDateTitle.setText(monthDataList.get(position));
            mCurCalendarView = monthCalendarViewList.get(position);
        }
    }
}
