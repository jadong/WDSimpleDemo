package com.dong.activity;

import com.dong.R;
import com.dong.base.BaseActivity;
import com.dong.view.MetroLayout;

/**
 * Created by zengwendong on 16/9/21.
 */
public class MetroActivity extends BaseActivity {

    private MetroLayout rl_metro_layout;

    @Override
    protected int getContentView() {
        return R.layout.activity_metro_layout;
    }

    @Override
    protected void onCreate() {
        rl_metro_layout = (MetroLayout) findViewById(R.id.rl_metro_layout);
        rl_metro_layout.init(this);
    }
}
