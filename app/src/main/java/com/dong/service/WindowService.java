package com.dong.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.WindowManager;

import com.dong.view.MyView;

/**
 * Created by zengwendong on 16/7/14.
 */
public class WindowService extends Service {

    private WindowManager mWindowManager;// 窗口管理者
    private WindowManager.LayoutParams mParams;// 窗口的属性
    private MyView myView;
    private boolean flag = true;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        mWindowManager = (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams();
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 系统提示window
        mParams.format = PixelFormat.TRANSLUCENT;// 支持透明
        //mParams.format = PixelFormat.RGBA_8888;
        mParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;// 焦点
        mParams.width = 490;//窗口的宽和高
        mParams.height = 160;
        mParams.x = 0;//窗口位置的偏移量
        mParams.y = 0;
        //mParams.alpha = 0.1f;//窗口的透明度
        myView = new MyView(this);
        myView.setWindowManagerParams(mParams);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        if (flag) {
            flag = false;
            mWindowManager.addView(myView, mParams);//添加窗口
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        if (myView.getParent() != null)
            mWindowManager.removeView(myView);//移除窗口
        super.onDestroy();
    }

}
