package com.example.za_zhujiangtao.zhupro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimeSelectActivity extends AppCompatActivity {

    @BindView(R.id.my_recycle_view)
    RecyclerView mRecyclerView;

    private GridAdapter mGridAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_select_layout);
        ButterKnife.bind(this);

        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(GridAdapter.ROW_NUMBER, StaggeredGridLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mGridAdapter = new GridAdapter(0, 23, gridLayoutManager);
        List<SelectTimeBean> selectTimeBeans = new ArrayList<>();
        SelectTimeBean bean1 = new SelectTimeBean(time2Long("2019.06.04-10:15:00"), time2Long("2019.06.04-11:30:00"));
        SelectTimeBean bean2 = new SelectTimeBean(time2Long("2019.06.04-12:00:00"), time2Long("2019.06.04-14:45:00"));
        selectTimeBeans.add(bean1);
        selectTimeBeans.add(bean2);
        mGridAdapter.setSelectTimeBeanList(selectTimeBeans);

        mRecyclerView.setAdapter(mGridAdapter);

        //滚动到指定位置，一定要是5的倍数，因为我们一列数据有5条
        ((StaggeredGridLayoutManager)mRecyclerView.getLayoutManager()).scrollToPositionWithOffset(80, 0);
    }

    private long time2Long(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    class GridAdapter extends RecyclerView.Adapter<GridAdapter.Holder> {

        public final static int ROW_NUMBER = 5;
        private int mStartTime;                 //选择的开始时间
        private int mEndTime;                   //选择的结束时间
        private long mSelectedStartTime;        //已被选择的开始时间
        private long mSelectedEndTime;          //已被选择的结束时间
        private int mSelectingStartPos = -1;    //选中的起始点
        private int mSelectingEndPos = -1;

        private List<SelectTimeBean> mSelectTimeBeanList;
        private StaggeredGridLayoutManager mGridLayoutManager;
        private List<Integer> mSelectingPosList = new ArrayList<>(); //现在正在选择的时间position集合

        public GridAdapter(int startTime, int endTime, StaggeredGridLayoutManager layoutManager) {
            this.mStartTime = startTime;
            this.mEndTime = endTime;
            mGridLayoutManager = layoutManager;
            mSelectTimeBeanList = new ArrayList<>();
        }

        public void setSelectedTime(long selectedStartTime, long selectedEndTime) {
            this.mSelectedStartTime = selectedStartTime;
            this.mSelectedEndTime = selectedEndTime;
        }

        public void setSelectTimeBeanList(List<SelectTimeBean> selectTimeBeanList) {
            mSelectTimeBeanList.clear();
            mSelectTimeBeanList.addAll(selectTimeBeanList);
        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(TimeSelectActivity.this).inflate(R.layout.time_select_item_view, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.bindData(position);
        }

        @Override
        public int getItemCount() {
            return (mEndTime - mStartTime + 1) * ROW_NUMBER;
        }

        class Holder extends RecyclerView.ViewHolder {

            @BindView(R.id.my_tv)
            TextView textView;

            public Holder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            public void bindData(int position) {
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) textView.getLayoutParams();
                if (position % ROW_NUMBER == 0) {
                    textView.setText(mStartTime + position / ROW_NUMBER + "");
                    textView.setBackground(null);
                    textView.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
                    textView.setOnClickListener(null);
                } else {
                    textView.setGravity(Gravity.CENTER);
                    textView.setText(position + "");

                    if (isSelectedPosition(position, mSelectTimeBeanList)) {
                        textView.setBackground(getResources().getDrawable(R.drawable.bg_selected_item_view));
                        textView.setOnClickListener(v -> {
                            Toast.makeText(TimeSelectActivity.this, position + "已被选择啦", Toast.LENGTH_SHORT).show();
                        });

                    } else {
                        textView.setBackground(getResources().getDrawable(R.drawable.bg_normal_item_view));
                        if (mSelectingPosList != null && mSelectingPosList.size() > 0) {
                            if (mSelectingPosList.contains(position)) {
                                textView.setBackground(getResources().getDrawable(R.drawable.bg_click_item_view));
                            }
                        }

                        textView.setOnClickListener(v -> {
                            if (mSelectingStartPos == -1) {
                                mSelectingStartPos = position;
                                textView.setBackground(getResources().getDrawable(R.drawable.bg_click_item_view));
                            } else if (mSelectingEndPos == -1) {
                                if (position <= mSelectingStartPos) {
                                    mSelectingEndPos = mSelectingStartPos;
                                    mSelectingStartPos = position;
                                } else {
                                    mSelectingEndPos = position;
                                }
                            } else if (mSelectingStartPos != -1 && mSelectingEndPos != -1) {//时间段确定后再选择时间那么得重新选取
                                resetSelectedPos();
                                notifyDataSetChanged();
                            }
                            if (mSelectingStartPos != -1 && mSelectingEndPos != -1) {
                                for (int i = mSelectingStartPos; i <= mSelectingEndPos; i++) {
                                    if (i % ROW_NUMBER != 0) {
                                        if (isSelectedPosition(i, mSelectTimeBeanList)) {//如果选择的时间包含已选择过的时间那么得重新选择时间
                                            Toast.makeText(TimeSelectActivity.this, "时间段选取不合法, 请重新选取", Toast.LENGTH_LONG).show();
                                            resetSelectedPos();
                                            notifyDataSetChanged();
                                            return;
                                        } else {
//                                            View view = mGridLayoutManager.findViewByPosition(i);
//                                            TextView textView = view.findViewById(R.id.my_tv);
//                                            textView.setBackground(getResources().getDrawable(R.drawable.bg_click_item_view));
                                            mSelectingPosList.add(i);
                                        }
                                    }
                                }
                                Log.e("TimeSelectActivity", "start: " + pos2Time(mSelectingStartPos, true) + ", end : " + pos2Time(mSelectingEndPos, false));
                                notifyDataSetChanged();
                            }
                        });
                    }
                }
                textView.setLayoutParams(params);
            }

            private void resetSelectedPos() {
                mSelectingPosList.clear();
                mSelectingStartPos = -1;
                mSelectingEndPos = -1;
            }

            private int time2Pos(long time) {
                int hour = getHour(time);
                int minutes = getMinutes(time);
                int pos = (hour - mStartTime) * ROW_NUMBER + minutes / 15 + 1;
                return pos;
            }

            private String pos2Time(int pos, boolean isStartPos) {
                int tmpHour = pos / ROW_NUMBER + mStartTime;
                String minutes = "";
                if (isStartPos) {
                    minutes = String.valueOf((pos % ROW_NUMBER - 1) * 15);
                } else {
                    int tmp = pos % ROW_NUMBER;
                    if (tmp == 4) {
                        tmpHour++;
                        minutes = "00";
                    } else {
                        minutes = String.valueOf((pos % ROW_NUMBER) * 15);
                    }
                }
                String hour = String.valueOf(tmpHour);
                if (hour.length() < 2) {
                    hour = "0" + hour;
                }
                if (minutes.length() < 2) {
                    minutes = "0" + minutes;
                }
                return hour + ":" + minutes;
            }

            private boolean isSelectedPosition(int position) {
                boolean isSelected = false;
                if (mSelectedEndTime != 0 && mSelectedStartTime != 0 && mSelectedEndTime > mSelectedStartTime) {
                    int startPos = time2Pos(mSelectedStartTime);
                    int endPos = time2Pos(mSelectedEndTime);

                    if (startPos > 0 && endPos > 0) {
                        if (position >= startPos && position < endPos) {
                            Log.e("TimeSelectActivity", "isSelectedPosition position = " + position);
                            isSelected = true;
                        }
                    }
                }
                return isSelected;
            }

            private boolean isSelectedPosition(int position, List<SelectTimeBean> selectTimeBeanList) {
                if (selectTimeBeanList != null && selectTimeBeanList.size() > 0) {
                    for (SelectTimeBean bean : selectTimeBeanList) {
                        int startPos = time2Pos(bean.getSelectedStartTime());
                        int endPos = time2Pos(bean.getSelectedEndTime());
                        if (startPos > 0 && endPos > 0) {
                            if (position >= startPos && position < endPos) {
//                                Log.e("TimeSelectActivity", "isSelectedPosition position = " + position);
                                return true;
                            }
                        }
                    }
                }
                return false;
            }

            private void date2String(long date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
                Date date1 = new Date(date);
                String time = sdf.format(date1);
            }

            private int getHour(long time) {
                //1559614500000 --- 2019.06.04-10:15:00
                //1559630700000 --- 2019.06.04-14:45:00
                Date date = new Date(time);
                if (date == null) {
                    return -1;
                }
                Calendar calendar = Calendar.getInstance(Locale.CHINESE);
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
//                Log.e("TimeSelectActivity", "getHour hour = " + hour);
                return hour;
            }

            private int getMinutes(long time) {
                Date date = new Date(time);
                if (date == null) {
                    return -1;
                }
                Calendar calendar = Calendar.getInstance(Locale.CHINESE);
                calendar.setTime(date);
                int minutes = calendar.get(Calendar.MINUTE);
//                Log.e("TimeSelectActivity", "getMinutes minutes = " + minutes);
                return minutes;
            }
        }
    }

    class SelectTimeBean {
        private long selectedStartTime;        //已被选择的开始时间
        private long selectedEndTime;          //已被选择的结束时间

        public SelectTimeBean(long selectedTimeBean, long selectedEndTime) {
            this.selectedStartTime = selectedTimeBean;
            this.selectedEndTime = selectedEndTime;
        }

        public long getSelectedStartTime() {
            return selectedStartTime;
        }

        public void setSelectedStartTime(long selectedStartTime) {
            this.selectedStartTime = selectedStartTime;
        }

        public long getSelectedEndTime() {
            return selectedEndTime;
        }

        public void setSelectedEndTime(long selectedEndTime) {
            this.selectedEndTime = selectedEndTime;
        }
    }
}
