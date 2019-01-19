package com.bwie.wang.bwm.imodel;


import com.bwie.wang.bwm.Until.MineCallBack;
import com.bwie.wang.bwm.Until.OkHttpUntils;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;

public class IModelImp implements IModel {
    MineCallBack mMyCallBack;

    @Override
    public void requestData(Map<String, String> map, String path, final Class clazz, final MineCallBack myCallBack) {
        mMyCallBack = myCallBack;
        /**
         * TODO：post请求
         */
        OkHttpUntils.getInstance().post(path, map, new OkHttpUntils.CallBack() {
            @Override
            public void fail(String error) {
                if (mMyCallBack != null) {
                    mMyCallBack.myCallBackFailed(error);
                }
            }

            @Override
            public void success(String data) {
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                if (mMyCallBack != null) {
                    mMyCallBack.myCallBackSuccess(o);
                }
            }
        });
    }

    /**
     * TODO:qqqqqqqqqqqqqqqqqqqq
     *
     * @param path
     * @param myCallBack
     */
    @Override
    public void requestGet(String path, final Class clazz, MineCallBack myCallBack) {
        this.mMyCallBack = myCallBack;
        OkHttpUntils.getInstance().get(path, new OkHttpUntils.CallBack() {
            @Override
            public void fail(String error) {
                if (mMyCallBack != null) {
                    mMyCallBack.myCallBackFailed(error);
                }
            }

            @Override
            public void success(String data) {
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                if (mMyCallBack != null) {
                    mMyCallBack.myCallBackSuccess(o);
                }
            }
        });
    }

    /**
     * put请求
     *
     * @param map
     * @param path
     * @param clazz
     * @param myCallBack
     */
    @Override
    public void requestPut(Map<String, String> map, String path, final Class clazz, MineCallBack myCallBack) {
        Map<String, RequestBody> body = OkHttpUntils.getInstance().generateRequestBody(map);
        OkHttpUntils.getInstance().put(path, body, new OkHttpUntils.CallBack() {
            @Override
            public void fail(String error) {
                if (mMyCallBack != null) {
                    mMyCallBack.myCallBackFailed(error);
                }
            }

            @Override
            public void success(String data) {
                Gson gson = new Gson();
                Object o = gson.fromJson(data, clazz);
                if (mMyCallBack != null) {
                    mMyCallBack.myCallBackSuccess(o);
                }
            }
        });
    }


}
