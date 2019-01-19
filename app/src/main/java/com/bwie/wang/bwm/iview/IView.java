package com.bwie.wang.bwm.iview;

public interface IView<T> {
    void setData(T t);

    void setError(String error);
}
