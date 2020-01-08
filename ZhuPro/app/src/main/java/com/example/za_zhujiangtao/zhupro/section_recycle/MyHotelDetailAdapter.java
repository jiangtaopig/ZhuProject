package com.example.za_zhujiangtao.zhupro.section_recycle;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.float_window.DisplayUtil;
import com.example.za_zhujiangtao.zhupro.section_recycle.adapter.SectionedRecyclerViewAdapter;
import com.example.za_zhujiangtao.zhupro.section_recycle.entity.RoomTypeEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/1/8
 */
public class MyHotelDetailAdapter extends SectionedRecyclerViewAdapter<MyHotelDetailAdapter.MyHeaderHolder, MyHotelDetailAdapter.MyItemViewHolder, RecyclerView.ViewHolder> {

    private List<RoomTypeEntity.RoomType> roomTypeList;
    private SparseBooleanArray mBooleanMap;

    public MyHotelDetailAdapter(){
        roomTypeList = new ArrayList<>();
        mBooleanMap = new SparseBooleanArray();
    }

    public void setRoomTypeList(List<RoomTypeEntity.RoomType> list){
        roomTypeList.clear();
        roomTypeList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    protected int getSectionCount() {
        return roomTypeList.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        int cnt = roomTypeList.get(section).roomInfos.size();
        if (!mBooleanMap.get(section)){
            //默认 第一类数据显示一条子数据
//            if (section == 0){
//                return 1;
//            }
            return 0;
        }else {
            return cnt;
        }
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected MyHeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new MyHeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_header_layout, parent, false));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected MyItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new MyItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_hotel_item_layout, parent, false));
    }

    @Override
    protected void onBindSectionHeaderViewHolder(MyHeaderHolder holder, int section) {
        holder.bindHeaderData(section);
    }

    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(MyItemViewHolder holder, int section, int position) {
        holder.bindItemData(section, position);
    }

    public class MyHeaderHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView titleTv;
        TextView propertyTv;
        LinearLayout tagLayout;
        TextView showMoreTv;
        View splitLine;

        public MyHeaderHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.poster);
            titleTv = itemView.findViewById(R.id.title_tv);
            propertyTv = itemView.findViewById(R.id.property_tv);
            tagLayout = itemView.findViewById(R.id.tag_layout);
            showMoreTv = itemView.findViewById(R.id.show_more_tv);
            splitLine = itemView.findViewById(R.id.horizontal_split_line);
        }

        public void bindHeaderData(int section){
            RoomTypeEntity.RoomType roomType = roomTypeList.get(section);
            StringBuilder properties = new StringBuilder();
            if (roomType.properties != null && roomType.properties.size() > 0){
                for (String s : roomType.properties){
                    properties.append(s).append(" ");
                }
            }
            propertyTv.setText(properties.toString());
            titleTv.setText(roomType.title);

            tagLayout.removeAllViews();
            if (roomType.tags != null && roomType.tags.size() > 0){
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.rightMargin = DisplayUtil.dip2px(3);

                for (String tag : roomType.tags){
                    TextView textView = new TextView(itemView.getContext());
                    textView.setText(tag);
                    textView.setLayoutParams(params);
                    textView.setBackgroundColor(Color.parseColor("#ff0000"));
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);
                    textView.setPadding(1, 1, 1, 1);
                    textView.setGravity(Gravity.CENTER);
                    tagLayout.addView(textView);
                }
            }

            showMoreTv.setOnClickListener(v -> {
                boolean isShowMore = mBooleanMap.get(section);
                String val = isShowMore ? "收起" : "￥999起";
                mBooleanMap.put(section, !isShowMore);
                showMoreTv.setText(val);
                notifyDataSetChanged();
            });

            if (mBooleanMap.get(section)){//如果是展开数据，那么隐藏分割线
                splitLine.setVisibility(View.INVISIBLE);
            }else {
                if (section == roomTypeList.size() - 1){
                    splitLine.setVisibility(View.INVISIBLE);
                }else {
                    splitLine.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    public class MyItemViewHolder extends RecyclerView.ViewHolder{

        TextView propertyTv;
        TextView cancelTv;
        LinearLayout tagLayout;
        TextView buyTv;
        View splitLine;

        public MyItemViewHolder(@NonNull View itemView) {
            super(itemView);
            propertyTv = itemView.findViewById(R.id.property_tv);
            tagLayout = itemView.findViewById(R.id.tag_layout);
            cancelTv = itemView.findViewById(R.id.cancel_tv);
            buyTv = itemView.findViewById(R.id.buy_now);
            splitLine = itemView.findViewById(R.id.horizontal_split_line);
        }

        public void bindItemData(int section, int position){
            int cnt = getItemCountForSection(section);
            Log.e("xxxxxx", "section = "+section+", cnt = "+cnt+", position = "+position);
            RoomTypeEntity.RoomType roomType = roomTypeList.get(section);
            List<RoomTypeEntity.RoomType.RoomInfo> roomInfoList = roomType.roomInfos;
            if (roomInfoList != null && roomInfoList.size() > 0){
                RoomTypeEntity.RoomType.RoomInfo roomInfo = roomInfoList.get(position);
                StringBuilder properties = new StringBuilder();
                if (roomInfo.propertieList != null && roomInfo.propertieList.size() > 0){
                    for (String s : roomInfo.propertieList){
                        properties.append(s).append(" ");
                    }
                }
                propertyTv.setText(properties.toString());
                cancelTv.setText(roomInfo.cancel);

                if (position == cnt - 1){
                    splitLine.setVisibility(View.INVISIBLE);
                }else {
                    splitLine.setVisibility(View.VISIBLE);
                }
            }

        }
    }
}
