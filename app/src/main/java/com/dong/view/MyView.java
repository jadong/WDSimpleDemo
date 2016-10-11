package com.dong.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import com.dong.entity.LineEntity;

import java.lang.reflect.Field;

/**
 * Created by zengwendong on 16/7/14.
 */
public class MyView extends SurfaceView implements SurfaceHolder.Callback {

    private MyThread myThread;
    private SurfaceHolder mHolder;
    //最大帧数 (1000 / 30)
    private static final int DRAW_INTERVAL = 10;

    private LineEntity lineEntity;

    private float xInScreen;

    private float yInScreen;

    private float xDownInScreen;

    private float yDownInScreen;

    private int statusBarHeight;

    private float xInView;

    private float yInView;

    private WindowManager windowManager;

    private WindowManager.LayoutParams mParams;

    public void setWindowManagerParams(WindowManager.LayoutParams mParams) {
        this.mParams = mParams;
    }

    public MyView(Context context) {
        super(context);

        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        this.mHolder = getHolder();
        this.mHolder.addCallback(this);// 设置生命周期回调接口的实现者
        this.lineEntity = new LineEntity();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (myThread == null) {
            myThread = new MyThread();
            myThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (myThread != null) {
            myThread.stopThread();
        }
    }

    class MyThread extends Thread {

        public boolean isRunning = false;

        public MyThread() {
            this.isRunning = true;
        }

        public void stopThread() {
            isRunning = false;
            boolean workIsNotFinish = true;
            while (workIsNotFinish) {
                try {
                    this.join();// 保证run方法执行完毕
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                workIsNotFinish = false;
            }
        }

        public void run() {
            long deltaTime;
            long tickTime;
            tickTime = System.currentTimeMillis();
            while (isRunning) {
                Canvas canvas = null;
                try {
                    synchronized (mHolder) {
                        canvas = mHolder.lockCanvas();
                        canvas.drawColor(Color.GRAY);
                        lineEntity.draw(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != mHolder && canvas != null) {
                        mHolder.unlockCanvasAndPost(canvas);
                    }
                }

                deltaTime = System.currentTimeMillis() - tickTime;
                if (deltaTime < DRAW_INTERVAL) {
                    try {
                        Thread.sleep(DRAW_INTERVAL - deltaTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                tickTime = System.currentTimeMillis();
            }

        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xInView = event.getX();
                yInView = event.getY();


                xDownInScreen = event.getRawX();
                yDownInScreen = event.getRawY() - getStatusBarHeight();

                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                break;
            case MotionEvent.ACTION_MOVE:
                xInScreen = event.getRawX();
                yInScreen = event.getRawY() - getStatusBarHeight();
                updateViewPosition();
                break;

        }
        return true;
    }

    private void updateViewPosition() {
        if (mParams != null) {
            mParams.x = (int) (xInScreen - xInView);
            mParams.y = (int) (yInScreen - yInView);
            windowManager.updateViewLayout(this, mParams);
        }

    }

    private int getStatusBarHeight() {
        if (statusBarHeight == 0) {
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object o = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = (Integer) field.get(o);
                statusBarHeight = getResources().getDimensionPixelSize(x);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusBarHeight;
    }

}
