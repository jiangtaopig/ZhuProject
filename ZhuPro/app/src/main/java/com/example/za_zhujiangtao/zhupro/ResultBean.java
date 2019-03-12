package com.example.za_zhujiangtao.zhupro;

import java.util.List;

/**
 * Created by za-zhujiangtao on 2019/1/17.
 */

public class ResultBean {

    List<DetailBean> mDetailBeanList;
    List<DetailBean> mRecommendBeanList;

    public List<DetailBean> getmDetailBeanList() {
        return mDetailBeanList;
    }

    public void setmDetailBeanList(List<DetailBean> mDetailBeanList) {
        this.mDetailBeanList = mDetailBeanList;
    }

    public List<DetailBean> getmRecommendBeanList() {
        return mRecommendBeanList;
    }

    public void setmRecommendBeanList(List<DetailBean> mRecommendBeanList) {
        this.mRecommendBeanList = mRecommendBeanList;
    }

    public static class DetailBean{
        private String name;
        private String phone;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
