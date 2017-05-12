package com.dong.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;

import java.util.Map;

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

        paint.setColor(Color.BLUE);
        //canvas.translate(mTouchCurrX, mTouchCurrY);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(5);
        /*canvas.drawCircle(cx, cy, radius, paint);

        RectF rectF = new RectF(100, 400, 400, 700);
        canvas.drawArc(rectF, 0, 90, true, paint);

        paint.setColor(Color.RED);
        canvas.drawRoundRect(new RectF(100, 400, 400, 700),30,30, paint);

        RectF drawOvalR = new RectF(100, 800, 400, 1000);
        canvas.drawOval(drawOvalR, paint);

        Path path = new Path();
        path.moveTo(200,100);
        path.lineTo(400,100);
        path.lineTo(300,300);
        path.lineTo(200,100);

        canvas.drawPath(path,paint);

        paint.setColor(Color.GREEN);
        Path quadPath = new Path();
        quadPath.moveTo(100,800);
        quadPath.quadTo(300,600,500,800);
        canvas.drawPath(quadPath,paint);


        canvas.drawPoint(300,600,paint);
        canvas.drawLine(100,800,300,600,paint);
        canvas.drawLine(300,600,500,800,paint);*/

        dong(canvas);
    }

    private int f = 500;
    private int h = -250;

    private double[] p(double a, double b, double c) {
        if (c > 60)
            return new double[]{Math.sin(a * 7) * (13 + 5 / (.2 + Math.pow(b * 4, 4))) - Math.sin(b) * 50, b * f + 50, 625 + Math.cos(a * 7) * (13 + 5 / (.2 + Math.pow(b * 4, 4))) + b * 400, a * 1 - b / 2, a};

        double A = a * 2 - 1;
        double B = b * 2 - 1;
        if (A * A + B * B < 1) {
            if (c > 37) {
                int j = (int) c & 1;
                int n = (j == 1) ? 6 : 4;
                double o = .5 / (a + .01) + Math.cos(b * 125) * 3 - a * 300;
                double w = b * h;
                return new double[]{o * Math.cos(n) + w * Math.sin(n) + j * 610 - 390, o * Math.sin(n) - w * Math.cos(n) + 550 - j * 350, 1180 + Math.cos(B + A) * 99 - j * 300, .4 - a * .1 + Math.pow(1 - B * B, -h * 6) * .15 - a * b * .4 + Math.cos(a + b) / 5 + Math.pow(Math.cos((o * (a + 1) + (B > 0 ? w : -w)) / 25), 30) * .1 * (1 - B * B), o / 1e3 + .7 - o * w * 3e-6};
            }
            if (c > 32) {
                c = c * 1.16 - .15;
                double o = a * 45 - 20;
                double w = b * b * h;
                double z = o * Math.sin(c) + w * Math.cos(c) + 620;
                return new double[]{o * Math.cos(c) - w * Math.sin(c), 28 + Math.cos(B * .5) * 99 - b * b * b * 60 - z / 2 - h, z, (b * b * .3 + Math.pow((1 - (A * A)), 7) * .15 + .3) * b, b * .7};
            }
            double o = A * (2 - b) * (80 - c * 2);
            double w = 99 - Math.cos(A) * 120 - Math.cos(b) * (-h - c * 4.9) + Math.cos(Math.pow(1 - b, 7)) * 50 + c * 2;
            double z = o * Math.sin(c) + w * Math.cos(c) + 700;
            return new double[]{o * Math.cos(c) - w * Math.sin(c), B * 99 - Math.cos(Math.pow(b, 7)) * 50 - c / 3 - z / 1.35 + 450, z, (1 - b / 1.2) * .9 + a * .1, Math.pow((1 - b), 20) / 4 + .05};
        }

        return null;
    }

    private SparseArray<Double> m = new SparseArray<>();
    private void dong(Canvas canvas){
        double[] s = null;
        for (int i = 0; i < 1e4; i++)
            s = p(Math.random(), Math.random(), i % 46 / .74);
            if (s != null) {
               double z = s[2];
                int x = (int) (s[0] * f / z - h);
                int y = (int) (s[1] * f / z - h);
                int q;
                if (m.get(q = y * f + x) != 0 | m.get(q) > z){
                    m.put(q,z);
                    paint.setColor(Color.argb(0,~ (int)(s[3] * h),~ (int)(s[4] * h),~ (int)(s[3] * s[3] * -80)));
                    //a.fillStyle = "rgb(" + ~ (s[3] * h) + "," + ~ (s[4] * h) + "," + ~ (s[3] * s[3] * -80) + ")";
                    //a.fillRect(x, y, 1, 1)
                    canvas.drawRect(x, y,x+1,y+1,paint);
                    invalidate();
                }
            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        /*float x = event.getX();
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
        mTouchLastY = y;*/
        return true;
    }
}
