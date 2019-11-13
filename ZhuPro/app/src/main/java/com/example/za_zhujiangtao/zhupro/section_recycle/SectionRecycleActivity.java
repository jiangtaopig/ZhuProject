package com.example.za_zhujiangtao.zhupro.section_recycle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.section_recycle.adapter.HotelEntityAdapter;
import com.example.za_zhujiangtao.zhupro.section_recycle.entity.HotelEntity;
import com.example.za_zhujiangtao.zhupro.section_recycle.utils.JsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/11/13
 */
public class SectionRecycleActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private HotelEntityAdapter mAdapter;
    private MyHotelAdapter myHotelAdapter;

    @Override
    protected int layoutId() {
        return R.layout.activity_section_recycle_layout;
    }

    @Override
    protected void onInitLogic() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        myHotelAdapter = new MyHotelAdapter(this);
//        GridLayoutManager manager = new GridLayoutManager(this,4);
//        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter,manager));
        //设置header
        LinearLayoutManager manager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(myHotelAdapter);
        HotelEntity entity = JsonUtils.analysisJsonFile(this,"json");

        HotelEntity.TagsEntity historyTags = new HotelEntity.TagsEntity();
        historyTags.tagsName = "历史记录";
        List<HotelEntity.TagsEntity.TagInfo> tagInfoList = new ArrayList<>();
        for (int i = 1; i < 5; i++){
            HotelEntity.TagsEntity.TagInfo tagInfo = new HotelEntity.TagsEntity.TagInfo();
            tagInfo.tagName = "记录" + i;
            tagInfoList.add(tagInfo);
        }
        historyTags.tagInfoList = tagInfoList;
        entity.allTagsList.add(0, historyTags);

        myHotelAdapter.setData(entity.allTagsList);
    }
}
