package com.bwie.wang.bwm.iprisenter;


import com.bwie.wang.bwm.Until.MineCallBack;
import com.bwie.wang.bwm.iview.IView;

import java.util.Map;

import com.bwie.wang.bwm.imodel.IModelImp;

/**
 * @author wangbingjun
 */
public class IPrenserterImp implements IPresenter {
    IView mIView;
    IModelImp mIModelImp;

    public IPrenserterImp(IView IView) {
        mIView = IView;
        mIModelImp = new IModelImp();
    }

    @Override
    public void startRequest(Map<String, String> map, String path, Class clazz) {
        mIModelImp.requestData(map, path, clazz, new MineCallBack() {
            @Override   //失败
            public void myCallBackFailed(String e) {
                mIView.setError(e + "失败了");
            }

            @Override   //成功
            public void myCallBackSuccess(Object o) {
                mIView.setData(o);
            }
        });

    }

    //TODO:qqqqqqqq
    @Override
    public void startGet(String path, Class clazz) {
        mIModelImp.requestGet(path, clazz, new MineCallBack() {
            @Override
            public void myCallBackFailed(String e) {
                mIView.setError(e + "失败");
            }

            @Override
            public void myCallBackSuccess(Object o) {
                mIView.setData(o);
            }
        });
    }

    @Override
    public void startPut(Map<String, String> map, String path, Class clazz) {
        mIModelImp.requestPut(map, path, clazz, new MineCallBack() {
            @Override
            public void myCallBackFailed(String e) {
                mIView.setError(e);
            }

            @Override
            public void myCallBackSuccess(Object o) {
                mIView.setData(o);
            }
        });
    }


    /**
     * 解绑
     */
    public void onDelet() {
        if (mIModelImp != null) {
            mIModelImp = null;
        }
        if (mIView != null) {
            mIView = null;
        }
    }
}
