package com.example.za_zhujiangtao.zhupro.section_recycle;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.flowlayout.FlowLayout;
import com.example.za_zhujiangtao.zhupro.flowlayout.TagAdapter;
import com.example.za_zhujiangtao.zhupro.flowlayout.TagFlowLayout;
import com.example.za_zhujiangtao.zhupro.section_recycle.adapter.HeaderHolder;
import com.example.za_zhujiangtao.zhupro.section_recycle.adapter.SectionedRecyclerViewAdapter;
import com.example.za_zhujiangtao.zhupro.section_recycle.entity.HotelEntity;
import com.example.za_zhujiangtao.zhupro.section_recycle.utils.HotelUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyd10892 on 2016/8/23.
 */

public class MyHotelAdapter extends SectionedRecyclerViewAdapter<HeaderHolder, MyHotelAdapter.MyItemViewHolder, RecyclerView.ViewHolder> {
    private final static int MIN_COUNT = 4;
    public List<HotelEntity.TagsEntity> allTagList;
    private Context mContext;
    private LayoutInflater mInflater;
    private TagFlowLayout mTagFlowLayout;

    private SparseBooleanArray mBooleanMap;

    public MyHotelAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mBooleanMap = new SparseBooleanArray();
    }

    public void setData(List<HotelEntity.TagsEntity> allTagList) {
        this.allTagList = allTagList;
        notifyDataSetChanged();
    }

    @Override
    protected int getSectionCount() {
        return HotelUtils.isEmpty(allTagList) ? 0 : allTagList.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        Log.e("zjt", "getItemCountForSection section = " + section);
        return 1;
    }

    //是否有footer布局
    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected HeaderHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        return new HeaderHolder(mInflater.inflate(R.layout.hotel_title_item, parent, false));
    }


    @Override
    protected RecyclerView.ViewHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected MyItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        return new MyItemViewHolder(mInflater.inflate(R.layout.item_view_holder, parent, false));
    }


    @Override
    protected void onBindSectionHeaderViewHolder(final HeaderHolder holder, final int section) {
        Log.e("zjt", "onBindSectionHeaderViewHolder section = " + section);
        holder.openView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (section == 0 && allTagList.get(section).tagsName.equals("历史记录")) {
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

        if (section == 0 && allTagList.get(section).tagsName.equals("历史记录")) {
            holder.openView.setText("删除");
        } else {
            holder.openView.setText(mBooleanMap.get(section) ? "关闭" : "展开");
        }

        holder.titleView.setText(allTagList.get(section).tagsName);


    }


    @Override
    protected void onBindSectionFooterViewHolder(RecyclerView.ViewHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(MyItemViewHolder holder, int section, int position) {
        int sectionCnt = getItemCountForSection(section); //每个section折叠状态下显示多少
        Log.e("zjt", "onBindItemViewHolder section = " + section + ", position = " + position + ", sectionCnt = " + sectionCnt);
        if (position == 0) {
            List<String> names = new ArrayList<>();
            List<HotelEntity.TagsEntity.TagInfo> tagInfos = allTagList.get(section).tagInfoList;
            boolean isOpen = mBooleanMap.get(section);
            if (isOpen) {
                for (HotelEntity.TagsEntity.TagInfo tagInfo : tagInfos) {
                    names.add(tagInfo.tagName);
                }
            } else {
                int size = tagInfos.size();
                int minCnt = size >= MIN_COUNT ? MIN_COUNT : size;
                for (int i = 0; i < minCnt; i++) {
                    names.add(tagInfos.get(i).tagName);
                }
            }

            mTagFlowLayout = holder.tagFlowLayout;
            mTagFlowLayout.setAdapter(new TagAdapter<String>(names) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {
                    TextView tag = (TextView) mInflater.inflate(R.layout.search_tag_item_view, mTagFlowLayout, false);
                    tag.setText(s);
                    return tag;
                }

            });
        }
    }

    class MyItemViewHolder extends RecyclerView.ViewHolder {

        public TagFlowLayout tagFlowLayout;

        public MyItemViewHolder(View itemView) {
            super(itemView);
            tagFlowLayout = (TagFlowLayout) itemView.findViewById(R.id.tag_flow_layout);
        }
    }
}
