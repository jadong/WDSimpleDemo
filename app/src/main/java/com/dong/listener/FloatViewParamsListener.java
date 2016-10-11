package com.dong.listener;

import android.view.WindowManager;

/**
 * 当前视图用于获取参数
 * Created by zengwendong on 16/7/15.
 */
public interface FloatViewParamsListener {

    /**
     * 获取标题栏高度
     * 因为需要通过Window对象获取，所以使用此办法
     *
     * @return
     */
    int getTitleHeight();


    /**
     * 获取当前WindowManager.LayoutParams 对象
     *
     * @return
     */
    WindowManager.LayoutParams getLayoutParams();
}
