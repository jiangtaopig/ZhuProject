package com.example.za_zhujiangtao.zhupro.section_recycle.entity;

import java.util.List;

import static com.example.za_zhujiangtao.zhupro.section_recycle.MoreTypeAdapter.IMAGE_VIEW;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/1/9
 */
public class ImageTypeData extends MoreTypeBaseData {
    public String posterUrl;
    public String title;
    public List<String> properties;
    public List<String> tags;

    public ImageTypeData(){
        setHolderType(IMAGE_VIEW);
    }
}
