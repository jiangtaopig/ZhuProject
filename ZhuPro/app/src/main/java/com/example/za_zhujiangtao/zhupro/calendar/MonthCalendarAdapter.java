package com.example.za_zhujiangtao.zhupro.calendar;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/7/1
 */
public class MonthCalendarAdapter extends RecyclerView.Adapter<MonthCalendarAdapter.MonthCalendarHolder> {

    private List<Integer> mDatas;
    private int lastMonthEndIndex; // 上个月结束的index
    private int nextMonthStartIndex; // 下个月开始的 index

    public MonthCalendarAdapter() {
        mDatas = new ArrayList<>();
    }

    public void setDateStr(String dateStr) {
        calculateDate(dateStr);
    }

    private void calculateDate(String date) {
        DateBean dateBean = DateUtils.getMonthData(date);
        mDatas = dateBean.monthList;
        lastMonthEndIndex = dateBean.lastMonthEndIndex;
        nextMonthStartIndex = dateBean.nextMonthStartIndex;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MonthCalendarHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MonthCalendarHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.month_calendar_item, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MonthCalendarHolder monthCalendarHolder, int i) {
        monthCalendarHolder.bindData(i);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MonthCalendarHolder extends RecyclerView.ViewHolder {
        TextView dateTv;

        public MonthCalendarHolder(@NonNull View itemView) {
            super(itemView);
            dateTv = itemView.findViewById(R.id.date);
        }

        public void bindData(int position) {
            dateTv.setText(String.valueOf(mDatas.get(position)));
            if (position < lastMonthEndIndex || position >= nextMonthStartIndex) {
                Log.e("xxx 1", "position = " + position +",nextMonthStartIndex = "+nextMonthStartIndex);
                dateTv.setTextColor(itemView.getContext().getResources().getColor(R.color.color_999999));
            } else {
                Log.e("xxx 2", "position = " + position);
                dateTv.setTextColor(itemView.getContext().getResources().getColor(R.color.color_333333));
            }
        }
    }
}
