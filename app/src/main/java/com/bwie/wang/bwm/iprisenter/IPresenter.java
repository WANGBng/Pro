package com.bwie.wang.bwm.iprisenter;


import java.util.Map;

/**
 * @author wangbingjun
 */
public interface IPresenter {

    void startRequest(Map<String, String> map, String path, Class clazz);

    void startGet(String path, Class clazz);

    void startPut(Map<String, String> map, String path, Class clazz);
}
