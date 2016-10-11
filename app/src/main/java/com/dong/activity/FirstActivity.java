package com.dong.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.dong.R;
import com.dong.base.BaseActivity;

public class FirstActivity extends BaseActivity {

    private CollapsingToolbarLayout collapsing_toolbar;

    @Override
    protected int getContentView() {
        return R.layout.activity_first;
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
    }
}
