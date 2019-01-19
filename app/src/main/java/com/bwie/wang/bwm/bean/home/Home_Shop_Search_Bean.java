package com.bwie.wang.bwm.bean.home;

import java.util.List;

public class Home_Shop_Search_Bean {
    public List<Home_Shop_List_Bean.ResultBean> result;

    public List<Home_Shop_List_Bean.ResultBean> getResult() {
        return result;
    }

    public void setResult(List<Home_Shop_List_Bean.ResultBean> result) {
        this.result = result;
    }

    public class ResultBean{
        public int commodityId;
        public String commodityName;
        public String masterPic;
        public int price;
        public int saleNum;

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getCommodityName() {
            return commodityName;
        }

        public void setCommodityName(String commodityName) {
            this.commodityName = commodityName;
        }

        public String getMasterPic() {
            return masterPic;
        }

        public void setMasterPic(String masterPic) {
            this.masterPic = masterPic;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSaleNum() {
            return saleNum;
        }

        public void setSaleNum(int saleNum) {
            this.saleNum = saleNum;
        }
    }
}
