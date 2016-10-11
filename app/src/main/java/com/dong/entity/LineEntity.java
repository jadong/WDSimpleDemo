package com.dong.entity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Created by zengwendong on 16/7/14.
 */
public class LineEntity {

    private Paint paint;
    private int strokeWidth = 50;
    private int line;
    private int maxLine;
    private int add = 50;
    private int startX, startY, stopX = add, stopY = add;

    //屏幕宽高
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    private int[] colors = {Color.RED, Color.YELLOW, Color.GREEN, Color.MAGENTA, Color.CYAN, Color.WHITE, Color.LTGRAY};
    private Random random = new Random();

    public LineEntity() {
        paint = new Paint();// 创建画笔
        paint.setAntiAlias(true);// 打开抗锯齿
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(strokeWidth);
        maxLine = SCREEN_HEIGHT / strokeWidth;
    }

    private void setLinePosition() {

        startX = stopX;
        stopX += add;
        if (startX >= SCREEN_WIDTH) {
            startX = 0;
            stopX = add;
            line++;
        }
        startY = stopY = line * strokeWidth;
        if (startY >= SCREEN_HEIGHT) {
            startY = 0;
        }
        if ((line + 1) == maxLine) {//已经是最后一行
            int color = colors[random.nextInt(colors.length)];
            paint.setColor(color);
            line = 0;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawLine(startX, startY, stopX, stopY, paint);
        setLinePosition();
    }
}
