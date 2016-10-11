package com.dong.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.dong.R;
import com.dong.listener.FloatViewParamsListener;

import java.util.Random;

/**
 * Created by zengwendong on 16/5/5.
 */
public class StartActivity extends AppCompatActivity {

    private WindowManager.LayoutParams mWindowParams;
    private int[] girl_ids = new int[]{R.drawable.girl_a,R.drawable.bg_1};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ImageView iv_girl = (ImageView) findViewById(R.id.iv_girl);
        setGirl(iv_girl);
        findViewById(R.id.tv_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, com.dong.activity.HomeActivity.class));
                finish();
            }
        });


    }

    public void setGirl(ImageView girl) {
        //随机数
        int i = new Random().nextInt(2);
        girl.setImageResource(girl_ids[i]);
    }

    private class FloatViewListener implements FloatViewParamsListener {
        @Override
        public int getTitleHeight() {
            // 获取状态栏高度。不能在onCreate回调方法中获取
            Rect frame = new Rect();
            getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            int statusBarHeight = frame.top;

            int contentTop = getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
            int titleBarHeight = contentTop - statusBarHeight;

            return titleBarHeight;
        }

        @Override
        public WindowManager.LayoutParams getLayoutParams() {
            return mWindowParams;
        }
    }
}
