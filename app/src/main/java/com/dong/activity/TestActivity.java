package com.dong.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.dong.base.BaseActivity;
import com.dong.entity.LineEntity;
import com.dong.view.CustomView;
import com.dong.view.MyView;
import com.dong.view.PorterDuffLoadingView;
import com.dong.view.RoseView;

/**
 * Created by zengwendong on 16/7/11.
 */
public class TestActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        CustomView myView = new CustomView(this);
        setContentView(myView);
    }
}