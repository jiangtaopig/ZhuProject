package com.example.za_zhujiangtao.zhupro.city_select;

import java.util.List;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/11/14
 */
public class CityEntity {

    private List<CitysBean> citys;

    public List<CitysBean> getCitys() {
        return citys;
    }

    public void setCitys(List<CitysBean> citys) {
        this.citys = citys;
    }

    public static class CitysBean {
        /**
         * pinYin : government_of_amsterdam
         * code : 荷兰
         * firstAlphabet : a
         * name : 阿姆斯特丹
         * spell : amstd
         */

        private String pinYin;
        private String code;
        private String firstAlphabet;
        private String name;
        private String spell;

        public String getPinYin() {
            return pinYin;
        }

        public void setPinYin(String pinYin) {
            this.pinYin = pinYin;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getFirstAlphabet() {
            return firstAlphabet;
        }

        public void setFirstAlphabet(String firstAlphabet) {
            this.firstAlphabet = firstAlphabet;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSpell() {
            return spell;
        }

        public void setSpell(String spell) {
            this.spell = spell;
        }
    }
}
