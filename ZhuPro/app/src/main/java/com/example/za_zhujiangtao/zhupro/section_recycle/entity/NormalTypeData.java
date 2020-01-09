package com.example.za_zhujiangtao.zhupro.section_recycle.entity;

import java.util.List;

import static com.example.za_zhujiangtao.zhupro.section_recycle.MoreTypeAdapter.NORMAL_VIEW;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2020/1/9
 */
public class NormalTypeData extends MoreTypeBaseData {
   public List<String> tagList;
   public List<String> propertyList;
   public String cancelInfo;
   public String price;

   public NormalTypeData(){
      setHolderType(NORMAL_VIEW);
   }
}
