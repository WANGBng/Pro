package com.bwie.wang.bwm.bean;

import com.bwie.wang.bwm.bean.find.Find_Address_List_Bean;

import java.util.List;

/**
 * 定义接收地址列表的具有表示符供EventBus识别的Bean类
 */
public class FALB {
    public int tatle;
    public List<Find_Address_List_Bean.ResultBean> mList;

    public int getTatle() {
        return tatle;
    }

    public void setTatle(int tatle) {
        this.tatle = tatle;
    }

    public List<Find_Address_List_Bean.ResultBean> getList() {
        return mList;
    }

    public void setList(List<Find_Address_List_Bean.ResultBean> list) {
        mList = list;
    }
}
