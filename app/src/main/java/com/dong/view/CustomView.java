package com.dong.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by zengwendong on 16/7/11.
 */
public class CustomView extends View {

    private Paint paint = new Paint();

    private float cx = 200, cy = 200;
    private int radius = 100;

    private float mTouchLastX;
    private float mTouchLastY;
    private float mTouchCurrX;
    private float mTouchCurrY;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLUE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLogic(canvas);
    }

    private void drawLogic(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        canvas.translate(mTouchCurrX, mTouchCurrY);
        canvas.drawCircle(cx, cy, radius, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mTouchCurrX = x;
                mTouchCurrY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                mTouchCurrX = x;
                mTouchCurrY = y;
                invalidate();
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        mTouchLastX = x;
        mTouchLastY = y;
        return true;
    }
}
