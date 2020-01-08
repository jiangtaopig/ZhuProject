package com.example.za_zhujiangtao.zhupro.section_recycle.entity;

import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/1/8
 */
public class RoomTypeEntity {
    public List<RoomType> roomTypeList;

    public static class RoomType{
        public String title;
        public String posterUrl;
        public List<String> properties;
        public List<String> tags;
        public List<RoomInfo> roomInfos;

        public static class RoomInfo{
            public List<String> propertieList;
            public List<String> tagList;
            public String cancel;
            public String price;
        }
    }
}
