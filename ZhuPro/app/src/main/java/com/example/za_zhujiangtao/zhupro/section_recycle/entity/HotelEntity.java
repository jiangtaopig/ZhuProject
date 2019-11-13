package com.example.za_zhujiangtao.zhupro.section_recycle.entity;

import java.util.List;

/**
 * Created by lyd10892 on 2016/8/23.
 */

public class HotelEntity {

    public List<TagsEntity> allTagsList;

    public static class TagsEntity {
        public String tagsName;
        public List<TagInfo> tagInfoList;

        public static class TagInfo {
            public String tagName;
        }
    }

}
