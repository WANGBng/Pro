package com.bwie.wang.bwm.Until;

public interface MineCallBack<T> {
    //接收的是String类型

    void myCallBackFailed(String e);

    void myCallBackSuccess(T t);
}
