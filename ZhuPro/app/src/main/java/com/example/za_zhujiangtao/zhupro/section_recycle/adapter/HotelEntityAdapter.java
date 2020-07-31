package com.example.za_zhujiangtao.zhupro.section_recycle.adapter;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.section_recycle.entity.HotelEntity;
import com.example.za_zhujiangtao.zhupro.section_recycle.utils.HotelUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by lyd10892 on 2016/8/23.
 */

public class HotelEntityAdapter extends SectionedRecyclerViewAdapter<HeaderHolder, DescHolder, FooterHolder> {
    public List<HotelEntity.TagsEntity> allTagList;
    private LayoutInflater mInflater;
    private SparseBooleanArray mBooleanMap;

    public HotelEntityAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        allTagList = new ArrayList<>();
        mBooleanMap = new SparseBooleanArray();
    }

    public void setData(List<HotelEntity.TagsEntity> list) {
        allTagList.clear();
        allTagList.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    protected int getSectionCount() {
        int count = HotelUtils.isEmpty(allTagList) ? 0 : allTagList.size();
        Log.e("zjt", "getSectionCount size = " + count);
        return count;
    }

    //每个 section 中显示几个item
    @Override
    protected int getItemCountForSection(int section) {
        Log.e("zjt", "getItemCountForSection section = " + section);
        int count = allTagList.get(section).tagInfoList.size();
        // 默认展开4个
//        if (count >= 0 && !mBooleanMap.get(section)) {
//            count = count >= 4 ? 4 : count;
//        }

        if (!mBooleanMap.get(section)) {
            count = 0;
        }

        return HotelUtils.isEmpty(allTagList.get(section).tagInfoList) ? 0 : count;
    }

    //是否有footer布局
    @Override
    protected boolean hasFooterInSection(int section) {
        Log.e("zjt", "hasFooterInSection section = " + section);
        if (section == allTagList.size() -1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    protected HeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HeaderHolder(mInflater.inflate(R.layout.hotel_title_item, parent, false));
    }


    @Override
    protected FooterHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return new FooterHolder(mInflater.inflate(R.layout.footer_item_layout, parent, false));
    }

    @Override
    protected DescHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new DescHolder(mInflater.inflate(R.layout.hotel_desc_item, parent, false));
    }

    @Override
    protected void onBindSectionHeaderViewHolder(final HeaderHolder holder, final int section) {
        Log.e("zjt", "onBindSectionHeaderViewHolder section = " + section);
        holder.openView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (section == 0) {
                    allTagList.get(0).tagInfoList.clear();
                    notifyDataSetChanged();
                } else {
                    boolean isOpen = mBooleanMap.get(section);
                    String text = isOpen ? "展开" : "关闭";
                    mBooleanMap.put(section, !isOpen);
                    holder.openView.setText(text);
                    notifyDataSetChanged();
                }
            }
        });
        if (section == 0) {
            holder.openView.setText("删除");
        } else {
            holder.openView.setText(mBooleanMap.get(section) ? "关闭" : "展开");
        }
        holder.titleView.setText(allTagList.get(section).tagsName);
    }


    @Override
    protected void onBindSectionFooterViewHolder(FooterHolder holder, int section) {
//        Log.e("zjt", "onBindSectionFooterViewHolder section = " + section + ", size = " + allTagList.size());
//        if (section == allTagList.size() - 1) {
//            holder.setTxtVisibility(true);
//        } else {
//            holder.setTxtVisibility(false);
//        }
    }

    @Override
    protected void onBindItemViewHolder(DescHolder holder, int section, int position) {
        Log.e("zjt", "onBindItemViewHolder section = " + section + ", position = " + position);
        holder.descView.setText(allTagList.get(section).tagInfoList.get(position).tagName);
    }
}
