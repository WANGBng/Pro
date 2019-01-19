package com.bwie.wang.bwm.Until;

/**
 * @author lenovo
 *         商品详情
 */
public class IncludeUntils {
    /**
     * 1.单例
     */
    private static IncludeUntils instance;

    public static IncludeUntils getInstance() {
        if (instance == null) {
            synchronized (IncludeUntils.class) {
                instance = new IncludeUntils();
            }
        }
        return instance;
    }

    private IncludeUntils() {

    }

    /**
     *点击商品,访问网络跳转到详情
     */


}
