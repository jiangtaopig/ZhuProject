package com.example.za_zhujiangtao.zhupro.api;

/**
 * Created by za-zhujiangtao on 2018/11/7.
 */

public class ResponseBean {

    /**
     * code : 0
     * result : {"za_itid":"NaWhbwK8G4A13T8D085+gsZZkhDjfR3r9Mmp9fAhVEL0gAFVjx/BHjxDSqVCRc4Z7jb6BYHt15PnZ81Sj4fBbw==","encryId":"fGiKZ9XC42fmgy+uCwc4tQ==","parentEncryId":"5r+NKyp5c2cC8CZ8OpM0Kw==","name":"张发明","u":"3628002535198","companyCustId":"5r+NKyp5c2cC8CZ8OpM0Kw=="}
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
         * za_itid : NaWhbwK8G4A13T8D085+gsZZkhDjfR3r9Mmp9fAhVEL0gAFVjx/BHjxDSqVCRc4Z7jb6BYHt15PnZ81Sj4fBbw==
         * encryId : fGiKZ9XC42fmgy+uCwc4tQ==
         * parentEncryId : 5r+NKyp5c2cC8CZ8OpM0Kw==
         * name : 张发明
         * u : 3628002535198
         * companyCustId : 5r+NKyp5c2cC8CZ8OpM0Kw==
         */

        private String za_itid;
        private String encryId;
        private String parentEncryId;
        private String name;
        private String u;
        private String companyCustId;

        public String getZa_itid() {
            return za_itid;
        }

        public void setZa_itid(String za_itid) {
            this.za_itid = za_itid;
        }

        public String getEncryId() {
            return encryId;
        }

        public void setEncryId(String encryId) {
            this.encryId = encryId;
        }

        public String getParentEncryId() {
            return parentEncryId;
        }

        public void setParentEncryId(String parentEncryId) {
            this.parentEncryId = parentEncryId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getU() {
            return u;
        }

        public void setU(String u) {
            this.u = u;
        }

        public String getCompanyCustId() {
            return companyCustId;
        }

        public void setCompanyCustId(String companyCustId) {
            this.companyCustId = companyCustId;
        }
    }
}
