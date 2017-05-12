package com.dong.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by zengwendong on 17/2/10.
 */
public class RoseView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder surfaceHolder;

    //最大帧数 (1000 / 30)
    private static final int DRAW_INTERVAL = 30;

    private Paint paint = new Paint();

    private DrawThread drawThread;

    public RoseView(Context context) {
        super(context);
        init();
    }

    private void init() {
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (null == drawThread) {
            drawThread = new DrawThread();
            drawThread.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (null != drawThread) {
            drawThread.stopThread();
        }
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
            }
        }
    }

    private class DrawThread extends Thread {
        public boolean isRunning = false;

        public DrawThread() {
            isRunning = true;
        }

        public void stopThread() {
            isRunning = false;
            boolean workIsNotFinish = true;
            while (workIsNotFinish) {
                try {
                    this.join();// 保证run方法执行完毕
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                workIsNotFinish = false;
            }
        }

        @Override
        public void run() {
            long deltaTime;
            long tickTime;
            tickTime = System.currentTimeMillis();
            while (isRunning) {
                Canvas canvas = null;
                try {
                    synchronized (surfaceHolder) {
                        canvas = surfaceHolder.lockCanvas();
                        canvas.drawColor(Color.WHITE);
                        dong(canvas);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != surfaceHolder) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
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
}
