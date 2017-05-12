package com.dong.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.FrameLayout;

import com.dong.R;
import com.dong.base.BaseActivity;
import com.dong.view.RangeSeekBar;

public class FirstActivity extends BaseActivity {

    private CollapsingToolbarLayout collapsing_toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_first;
    }


    @Override
    public boolean isEnableSwipeBack() {
        return false;
    }

    @Override
    protected void onCreate() {
        collapsing_toolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        //collapsing_toolbar.setTitle("FirstActivity");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "hello world", Snackbar.LENGTH_SHORT).setAction("Action", null).show();
            }
        });

        FrameLayout fl_seekbar = (FrameLayout) findViewById(R.id.fl_seekbar);
        RangeSeekBar<Integer> rangeSeekBar = new RangeSeekBar<>(this);

        rangeSeekBar.setRangeValues(15, 3100);
        rangeSeekBar.setSelectedMinValue(120);
        rangeSeekBar.setSelectedMaxValue(888);
        fl_seekbar.addView(rangeSeekBar);

    }
}
