package com.example.za_zhujiangtao.zhupro;

import java.util.List;

/**
 * Created by za-zhujiangtao on 2018/5/25.
 */

public class Bean {


    /**
     * code : 0
     * result : {"totalItem":2,"needTotalItem":false,"pageSize":20,"currentPage":1,"startRow":0,"resultList":[{"id":996660442448109568,"custId":1460009,"custName":"测试二","facePath":"https://image.zuifuli.com/14/20180425/d390f406fd80647034d552f2bd5f2957","orgCustId":1288001,"location":"","isPublic":true,"status":1,"title":"我是一个快要","pic":"https://image.zuifuli.com/99/20180425/fb334ea2f3463e56f7ce734df340dc9c","password":"","recordVideo":false,"isFollowed":false,"isFavorited":true,"createTime":"2018-05-16 15:55:41","planStartTime":"2018-05-30 13:58:00","watchedUserCount":0,"followUsers":[1791004],"favoritedUsers":[2078001],"ecustId":"HnjWzYMPVHiP+1R8UIecjQ=="},{"id":989367004777988096,"custId":1460009,"custName":"测试二","facePath":"https://image.zuifuli.com/14/20180425/d390f406fd80647034d552f2bd5f2957","orgCustId":1288001,"location":"上海","isPublic":false,"status":1,"title":"我是denmm款打底裤","pic":"https://image.zuifuli.com/99/20180425/fb334ea2f3463e56f7ce734df340dc9c","needAuth":true,"recordVideo":false,"isFollowed":false,"isFavorited":false,"createTime":"2018-04-26 12:54:10","planStartTime":"2018-05-26 03:22:20","watchedUserCount":0,"followUsers":[1460004,1791004,1332504,1460009],"ecustId":"HnjWzYMPVHiP+1R8UIecjQ=="}],"up":false,"totalPage":1}
     */

    private String code;
    private ResultBean result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * totalItem : 2
         * needTotalItem : false
         * pageSize : 20
         * currentPage : 1
         * startRow : 0
         * resultList : [{"id":996660442448109568,"custId":1460009,"custName":"测试二","facePath":"https://image.zuifuli.com/14/20180425/d390f406fd80647034d552f2bd5f2957","orgCustId":1288001,"location":"","isPublic":true,"status":1,"title":"我是一个快要","pic":"https://image.zuifuli.com/99/20180425/fb334ea2f3463e56f7ce734df340dc9c","password":"","recordVideo":false,"isFollowed":false,"isFavorited":true,"createTime":"2018-05-16 15:55:41","planStartTime":"2018-05-30 13:58:00","watchedUserCount":0,"followUsers":[1791004],"favoritedUsers":[2078001],"ecustId":"HnjWzYMPVHiP+1R8UIecjQ=="},{"id":989367004777988096,"custId":1460009,"custName":"测试二","facePath":"https://image.zuifuli.com/14/20180425/d390f406fd80647034d552f2bd5f2957","orgCustId":1288001,"location":"上海","isPublic":false,"status":1,"title":"我是denmm款打底裤","pic":"https://image.zuifuli.com/99/20180425/fb334ea2f3463e56f7ce734df340dc9c","needAuth":true,"recordVideo":false,"isFollowed":false,"isFavorited":false,"createTime":"2018-04-26 12:54:10","planStartTime":"2018-05-26 03:22:20","watchedUserCount":0,"followUsers":[1460004,1791004,1332504,1460009],"ecustId":"HnjWzYMPVHiP+1R8UIecjQ=="}]
         * up : false
         * totalPage : 1
         */

        private int totalItem;
        private boolean needTotalItem;
        private int pageSize;
        private int currentPage;
        private int startRow;
        private boolean up;
        private int totalPage;
        private List<ResultListBean> resultList;

        public int getTotalItem() {
            return totalItem;
        }

        public void setTotalItem(int totalItem) {
            this.totalItem = totalItem;
        }

        public boolean isNeedTotalItem() {
            return needTotalItem;
        }

        public void setNeedTotalItem(boolean needTotalItem) {
            this.needTotalItem = needTotalItem;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getStartRow() {
            return startRow;
        }

        public void setStartRow(int startRow) {
            this.startRow = startRow;
        }

        public boolean isUp() {
            return up;
        }

        public void setUp(boolean up) {
            this.up = up;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }

        public List<ResultListBean> getResultList() {
            return resultList;
        }

        public void setResultList(List<ResultListBean> resultList) {
            this.resultList = resultList;
        }

        public static class ResultListBean {
            /**
             * id : 996660442448109568
             * custId : 1460009
             * custName : 测试二
             * facePath : https://image.zuifuli.com/14/20180425/d390f406fd80647034d552f2bd5f2957
             * orgCustId : 1288001
             * location :
             * isPublic : true
             * status : 1
             * title : 我是一个快要
             * pic : https://image.zuifuli.com/99/20180425/fb334ea2f3463e56f7ce734df340dc9c
             * password :
             * recordVideo : false
             * isFollowed : false
             * isFavorited : true
             * createTime : 2018-05-16 15:55:41
             * planStartTime : 2018-05-30 13:58:00
             * watchedUserCount : 0
             * followUsers : [1791004]
             * favoritedUsers : [2078001]
             * ecustId : HnjWzYMPVHiP+1R8UIecjQ==
             * needAuth : true
             */

            private long id;
            private int custId;
            private String custName;
            private String facePath;
            private int orgCustId;
            private String location;
            private boolean isPublic;
            private int status;
            private String title;
            private String pic;
            private String password;
            private boolean recordVideo;
            private boolean isFollowed;
            private boolean isFavorited;
            private String createTime;
            private String planStartTime;
            private int watchedUserCount;
            private String ecustId;
            private boolean needAuth;
            private List<Integer> followUsers;
            private List<Integer> favoritedUsers;

            public long getId() {
                return id;
            }

            public void setId(long id) {
                this.id = id;
            }

            public int getCustId() {
                return custId;
            }

            public void setCustId(int custId) {
                this.custId = custId;
            }

            public String getCustName() {
                return custName;
            }

            public void setCustName(String custName) {
                this.custName = custName;
            }

            public String getFacePath() {
                return facePath;
            }

            public void setFacePath(String facePath) {
                this.facePath = facePath;
            }

            public int getOrgCustId() {
                return orgCustId;
            }

            public void setOrgCustId(int orgCustId) {
                this.orgCustId = orgCustId;
            }

            public String getLocation() {
                return location;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public boolean isIsPublic() {
                return isPublic;
            }

            public void setIsPublic(boolean isPublic) {
                this.isPublic = isPublic;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public boolean isRecordVideo() {
                return recordVideo;
            }

            public void setRecordVideo(boolean recordVideo) {
                this.recordVideo = recordVideo;
            }

            public boolean isIsFollowed() {
                return isFollowed;
            }

            public void setIsFollowed(boolean isFollowed) {
                this.isFollowed = isFollowed;
            }

            public boolean isIsFavorited() {
                return isFavorited;
            }

            public void setIsFavorited(boolean isFavorited) {
                this.isFavorited = isFavorited;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getPlanStartTime() {
                return planStartTime;
            }

            public void setPlanStartTime(String planStartTime) {
                this.planStartTime = planStartTime;
            }

            public int getWatchedUserCount() {
                return watchedUserCount;
            }

            public void setWatchedUserCount(int watchedUserCount) {
                this.watchedUserCount = watchedUserCount;
            }

            public String getEcustId() {
                return ecustId;
            }

            public void setEcustId(String ecustId) {
                this.ecustId = ecustId;
            }

            public boolean isNeedAuth() {
                return needAuth;
            }

            public void setNeedAuth(boolean needAuth) {
                this.needAuth = needAuth;
            }

            public List<Integer> getFollowUsers() {
                return followUsers;
            }

            public void setFollowUsers(List<Integer> followUsers) {
                this.followUsers = followUsers;
            }

            public List<Integer> getFavoritedUsers() {
                return favoritedUsers;
            }

            public void setFavoritedUsers(List<Integer> favoritedUsers) {
                this.favoritedUsers = favoritedUsers;
            }
        }
    }
}
