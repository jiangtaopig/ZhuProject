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

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/2
 */
public class TestLoopViewPagerActivity extends BaseActivity {

    private TextView mDateTv;
    private ViewPager mViewPager;

    @Override
    protected int layoutId() {
        return R.layout.activity_test_loop_viewpager_layout;
    }

    @Override
    protected void onInitLogic() {
        mDateTv = findViewById(R.id.txt_date);
        mViewPager = findViewById(R.id.view_pager);

        List<String> dataList = new ArrayList<>();
        dataList.add("0");
        dataList.add("1");
        dataList.add("2");

        mViewPager.setOffscreenPageLimit(2);
        MonthCalendarAdapter adapter = new MonthCalendarAdapter();
        mViewPager.setAdapter(adapter);
        adapter.setMonthDataList(dataList);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                Log.e("zhu_jt", "onPageSelected i = " + i);
                mViewPager.setCurrentItem(1, false);
                if (i == 2) {
                    dataList.remove(0);
                    String val = String.valueOf(Integer.valueOf(dataList.get(dataList.size() - 1)) + 1);
                    dataList.add(val);
                } else if (i == 0) {
                    dataList.remove(dataList.size() - 1);
                    String val = String.valueOf(Integer.valueOf(dataList.get(0)) - 1);
                    dataList.add(0, val);
                }
                mViewPager.removeAllViews();
                adapter.setMonthDataList(dataList);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        mViewPager.setCurrentItem(1, false);
    }

    class MonthCalendarAdapter extends PagerAdapter {

        private List<String> monthDataList;
        private List<ViewPagerItem> viewPagerItemList;

        public MonthCalendarAdapter() {
            monthDataList = new ArrayList<>();
            viewPagerItemList = new ArrayList<>();
        }

        public void setMonthDataList(List<String> monthDatas) {
            monthDataList.clear();
            monthDataList.addAll(monthDatas);
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
            Log.e("zhu_jt", "instantiateItem position = " + position);
            ViewPagerItem view = new ViewPagerItem(container.getContext());
            view.setTitle(monthDataList.get(position));
            container.addView(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            viewPagerItemList.add(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView(viewPagerItemList.get(position));
        }

        @Override
        public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            super.setPrimaryItem(container, position, object);
            Log.e("zhu_jt", "setPrimaryItem position = " + position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return POSITION_NONE;
        }
    }
}
