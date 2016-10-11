package com.dong.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.dong.R;
import com.dong.util.AppUtils;

import java.util.Random;

/**
 * Created by zengwendong on 16/9/21.
 */
public class MetroLayout extends RelativeLayout {

    public MetroLayout(Context context) {
        super(context);
    }

    public MetroLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MetroLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context) {
        int width = AppUtils.dip2px(100);
        int height = AppUtils.dip2px(100);
        Random random = new Random();
        int leftMargin = AppUtils.getScreenWidth() - width;
        int topMargin = AppUtils.getScreenHeight() - height;

        for (int i = 0; i < 10; i++) {
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, height);
            layoutParams.leftMargin = random.nextInt(leftMargin);
            layoutParams.topMargin = random.nextInt(topMargin);
            View view = new View(context);
            view.setLayoutParams(layoutParams);
            if(i % 2 == 0){
                view.setBackgroundResource(R.color.black);
            }else {
                view.setBackgroundResource(R.color.gray);
            }
            addView(view);
        }
    }

}
