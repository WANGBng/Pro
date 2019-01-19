package com.bwie.wang.bwm.bean.home;

import java.util.List;

public class Home_Shop_Comment_List_Bean {
    public List<ResulteBean> result;

    public List<ResulteBean> getResult() {
        return result;
    }

    public void setResult(List<ResulteBean> result) {
        this.result = result;
    }
    public class ResulteBean{
        public int commodityId;
        public String content;
        public Long createTime;
        public String headPic;
        public String image;
        public String nickName;
        public int userId;

        public int getCommodityId() {
            return commodityId;
        }

        public void setCommodityId(int commodityId) {
            this.commodityId = commodityId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Long createTime) {
            this.createTime = createTime;
        }

        public String getHeadPic() {
            return headPic;
        }

        public void setHeadPic(String headPic) {
            this.headPic = headPic;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }
}
