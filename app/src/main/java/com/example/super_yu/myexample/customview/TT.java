package com.example.super_yu.myexample.customview;

import java.util.List;

/**
 * Created by super_yu on 2017/8/7.
 */

public class TT {


    /**
     * datas : [{"data":[{"name":"A1"},{"name":"A2"},{"name":"A3"},{"name":"A4"},{"name":"A5"}],"depart":"门诊部"},{"data":[{"name":"B1"},{"name":"B2"},{"name":"B3"}],"depart":"研发部"}]
     * name : 测试数据
     */

    private String name;
    private List<DatasBean> datas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class DatasBean {
        /**
         * data : [{"name":"A1"},{"name":"A2"},{"name":"A3"},{"name":"A4"},{"name":"A5"}]
         * depart : 门诊部
         */

        private String depart;
        private List<DataBean> data;

        public String getDepart() {
            return depart;
        }

        public void setDepart(String depart) {
            this.depart = depart;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * name : A1
             */

            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
