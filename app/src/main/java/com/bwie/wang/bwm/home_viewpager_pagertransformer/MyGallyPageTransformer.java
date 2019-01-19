package com.bwie.wang.bwm.home_viewpager_pagertransformer;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * date:2019/1/12.
 *
 * @author 王丙均
 */

public class MyGallyPageTransformer implements ViewPager.PageTransformer {
    //最小规模
    private static final float min_scale = 0.85f;

    @Override   // 变换页面
    public void transformPage(@NonNull View view, float v) {
        //
        float scaleFactor = Math.max(min_scale, 1 - Math.abs(v));
        if (v < -1) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        } else if (v < 0) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        } else if (v >= 0 && v < 1) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        } else if (v >= 1) {
            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);
        }

    }
}
