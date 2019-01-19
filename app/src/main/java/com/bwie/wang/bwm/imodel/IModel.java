package com.bwie.wang.bwm.imodel;


import com.bwie.wang.bwm.Until.MineCallBack;

import java.util.Map;

public interface IModel {
    void requestData(Map<String, String> map, String path, Class clazz, MineCallBack myCallBack);

    void requestGet(String path, Class clazz, MineCallBack myCallBack);

    void requestPut(Map<String, String> map, String path, Class clazz, MineCallBack myCallBack);
}
