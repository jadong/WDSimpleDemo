package com.dong.behavior;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by zengwendong on 16/12/13.
 */
public class ScrollBehavior extends CoordinatorLayout.Behavior<TextView> {

    public ScrollBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 确定所监听的视图
     */
    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, TextView child, View dependency) {
        Log.i("ScrollBehavior", "layoutDependsOn: "+dependency);
        return dependency instanceof RecyclerView;
    }

    /**
     * 被观察View变化的时候回调用的方法
     */
    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, TextView child, View dependency) {
        int offset = dependency.getScrollY();
        ViewCompat.offsetTopAndBottom(child, offset);
        return false;
    }

}
