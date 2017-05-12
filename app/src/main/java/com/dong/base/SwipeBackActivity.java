package com.dong.base;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.dong.R;

/**
 * Created by Eric on 15/3/3.
 */
public class SwipeBackActivity extends FragmentActivity implements SwipeBackLayout.SwipeBackListener {

    private SwipeBackLayout swipeBackLayout;
    private ImageView ivShadow;//渐变背景色
    private boolean isEnableSwipeBack = true;//是否启用SwipeBack
    private int scaledTouchSlop;//滑动的 制动距离

    @Override
    public void setContentView(int layoutResID) {
        if (isEnableSwipeBack()) {
            super.setContentView(getContainer());
            View view = LayoutInflater.from(this).inflate(layoutResID, null);
            swipeBackLayout.addView(view);
            swipeBackLayout.setEnablePullToBack(true);
        } else {
            super.setContentView(layoutResID);
        }
        scaledTouchSlop = ViewConfiguration.get(this).getScaledTouchSlop();
    }

    private View getContainer() {
        RelativeLayout container = new RelativeLayout(this);
        swipeBackLayout = new SwipeBackLayout(this);
        swipeBackLayout.setOnSwipeBackListener(this);
        ivShadow = new ImageView(this);
        ivShadow.setBackgroundColor(getResources().getColor(R.color.black_p50));
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        container.addView(ivShadow, params);
        container.addView(swipeBackLayout);
        return container;
    }

    public boolean isEnableSwipeBack() {
        return isEnableSwipeBack;
    }

    public void setEnableSwipeBack(boolean enableSwipeBack) {
        isEnableSwipeBack = enableSwipeBack;
    }

    public void setDragEdge(SwipeBackLayout.DragEdge dragEdge) {
        if (swipeBackLayout != null) {
            swipeBackLayout.setDragEdge(dragEdge);
        }
    }

    public SwipeBackLayout getSwipeBackLayout() {
        return swipeBackLayout;
    }

    @Override
    public void onViewPositionChanged(float fractionAnchor, float fractionScreen) {
        ivShadow.setAlpha(1 - fractionScreen);
    }

    float startX;
    float startY;
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
            case MotionEvent.ACTION_MOVE:
                fixTouchEvent(ev);
            case MotionEvent.ACTION_UP:

        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 修正 左右滑动 与 上下滑动事件
     * @param ev
     */
    private void fixTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        float y = ev.getY();
        float dx = x - startX;
        float dy = y - startY;
        if (swipeBackLayout != null) {
            if (Math.abs(dy)> Math.abs(dx)){
                swipeBackLayout.setEnablePullToBack(false);
            }else {
                swipeBackLayout.setEnablePullToBack(true);
            }
        }
    }
}
