package com.example.za_zhujiangtao.zhupro.section_recycle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.za_zhujiangtao.zhupro.BaseActivity;
import com.example.za_zhujiangtao.zhupro.R;
import com.example.za_zhujiangtao.zhupro.section_recycle.adapter.HotelEntityAdapter;
import com.example.za_zhujiangtao.zhupro.section_recycle.adapter.SectionedSpanSizeLookup;
import com.example.za_zhujiangtao.zhupro.section_recycle.entity.HotelEntity;
import com.example.za_zhujiangtao.zhupro.section_recycle.entity.RoomTypeEntity;
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
    private MyHotelDetailAdapter hotelDetailAdapter;

    @Override
    protected int layoutId() {
        return R.layout.activity_section_recycle_layout;
    }

    @Override
    protected void onInitLogic() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        setDetail();

//        showHotelList();
    }

    private void setDetail() {
        //设置header
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        hotelDetailAdapter = new MyHotelDetailAdapter();
        mRecyclerView.setAdapter(hotelDetailAdapter);

        List<RoomTypeEntity.RoomType> roomTypeList = new ArrayList<>();
        List<String> property1 = new ArrayList<>();
        property1.add("19平");
        property1.add("有早");

        List<String> tags1 = new ArrayList<>();
        tags1.add("HOT");
        tags1.add("协议价");

        List<RoomTypeEntity.RoomType.RoomInfo> roomInfos1 = new ArrayList<>();
        RoomTypeEntity.RoomType.RoomInfo roomInfo1 = new RoomTypeEntity.RoomType.RoomInfo();
        roomInfo1.cancel = "随时取消";
        roomInfo1.price = "123";
        roomInfo1.propertieList = property1;
        roomInfo1.tagList = tags1;

        roomInfos1.add(roomInfo1);

        RoomTypeEntity.RoomType.RoomInfo roomInfo2 = new RoomTypeEntity.RoomType.RoomInfo();
        roomInfo2.cancel = "不可取消";
        roomInfo2.price = "334";
        roomInfo2.propertieList = property1;
        roomInfo2.tagList = tags1;
        roomInfos1.add(roomInfo2);

        RoomTypeEntity.RoomType roomType1 = new RoomTypeEntity.RoomType();
        roomType1.title = "商务大床房1";
        roomType1.properties = property1;
        roomType1.tags = tags1;
        roomType1.roomInfos = roomInfos1;

        roomTypeList.add(roomType1);

        List<String> property2 = new ArrayList<>();
        property2.add("2人");
        property2.add("19平");
        property2.add("有早");

        List<String> tags2 = new ArrayList<>();
        tags2.add("仅剩一间");
        tags2.add("HOT");
        tags2.add("协议价");

        List<RoomTypeEntity.RoomType.RoomInfo> roomInfos2 = new ArrayList<>();
        RoomTypeEntity.RoomType.RoomInfo roomInfo3 = new RoomTypeEntity.RoomType.RoomInfo();
        roomInfo3.cancel = "取消";
        roomInfo3.price = "12333";
        roomInfo3.propertieList = property2;
        roomInfo3.tagList = tags2;

        roomInfos2.add(roomInfo3);

        RoomTypeEntity.RoomType.RoomInfo roomInfo4 = new RoomTypeEntity.RoomType.RoomInfo();
        roomInfo4.cancel = "不dd可取消";
        roomInfo4.price = "33334";
        roomInfo4.propertieList = property2;
        roomInfo4.tagList = tags2;
        roomInfos2.add(roomInfo4);

        RoomTypeEntity.RoomType roomType2 = new RoomTypeEntity.RoomType();
        roomType2.title = "商务大床房2";
        roomType2.properties = property2;
        roomType2.tags = tags2;
        roomType2.roomInfos = roomInfos2;

        roomTypeList.add(roomType2);

        hotelDetailAdapter.setRoomTypeList(roomTypeList);
    }

    private void showHotelList() {
        myHotelAdapter = new MyHotelAdapter(this);
        GridLayoutManager manager = new GridLayoutManager(this,4);
        mAdapter = new HotelEntityAdapter(this);
        manager.setSpanSizeLookup(new SectionedSpanSizeLookup(mAdapter, manager));


        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setLayoutManager(manager);
//        mRecyclerView.setAdapter(myHotelAdapter);
        HotelEntity entity = JsonUtils.analysisJsonFile(this,"json");

        mAdapter.setData(entity.allTagsList);

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

//        myHotelAdapter.setData(entity.allTagsList);
    }
}
