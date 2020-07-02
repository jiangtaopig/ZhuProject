package com.example.za_zhujiangtao.zhupro.calendar;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/6/30
 */
public class TestCalendarActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private MonthCalendarAdapter mMonthCalendarAdapter;

    @Override
    protected int layoutId() {
        return R.layout.activity_calendar_layout;
    }

    @Override
    protected void onInitLogic() {
        mRecyclerView = findViewById(R.id.recycle_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 7));
        mMonthCalendarAdapter = new MonthCalendarAdapter();
        mRecyclerView.setAdapter(mMonthCalendarAdapter);


        mMonthCalendarAdapter.setDateStr("2020-07-01");
    }
}
