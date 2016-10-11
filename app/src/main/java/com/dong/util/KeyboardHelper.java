package com.dong.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘帮助类
 * Created by zengwendong on 16/8/24.
 */
public class KeyboardHelper {

    /**
     * 隐藏键盘--当前Activity的光标
     */
    public static void hideSoftKeyboard(Activity activity) {
        hideSoftKeyboard(activity, activity.getCurrentFocus());
    }

    /**
     * 隐藏键盘
     */
    public static void hideSoftKeyboard(Context context, View... views) {
        if (views == null) return;
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        for (View currentView : views) {
            if (null == currentView) continue;
            manager.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
            currentView.clearFocus();
        }
    }

    /**
     * 显示键盘
     */
    public static void showSoftKeyboard(Context context, View view) {
        if (view == null) return;
        InputMethodManager manager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        view.requestFocus();
        manager.showSoftInput(view, 0);
    }

    /**
     * 键盘状态改变监听,<br/>
     * 基于根布局高度变化.<br/>
     * 注意:在AndroidManifest文件中相应的Activity需要配置 <code>android:windowSoftInputMode</code><code>adjustResize</code>
     *
     * @param rootView 根布局
     * @param listener 键盘监听
     */
    public static void addKeyboardShowListener(final View rootView, final OnKeyboardShowListener listener) {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private boolean mIsKeyboardShown;
            private int mInitialHeightsDiff = -1;

            @Override
            public void onGlobalLayout() {
                Rect r = new Rect();
                rootView.getWindowVisibleDisplayFrame(r);

                int heightDiff = rootView.getRootView().getHeight() - (r.bottom - r.top);
                if (mInitialHeightsDiff == -1) {
                    mInitialHeightsDiff = heightDiff;
                }
                heightDiff -= mInitialHeightsDiff;

                if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
                    if (!mIsKeyboardShown) {
                        mIsKeyboardShown = true;
                        listener.onKeyboardShow(true);
                    }
                } else if (heightDiff < 50) {
                    if (mIsKeyboardShown) {
                        mIsKeyboardShown = false;
                        listener.onKeyboardShow(false);
                    }
                }
            }
        });
    }


    public static interface OnKeyboardShowListener {
        void onKeyboardShow(boolean show);
    }

    private KeyboardHelper() {
    }

}
