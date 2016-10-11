package com.dong.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.dong.R;
import com.dong.adapter.ImageAdapter;
import com.dong.base.BaseActivity;
import com.dong.util.AppUtils;
import com.dong.util.Callback;
import com.dong.util.ImageLoadUtil;
import com.dong.util.Invoker;
import com.nineoldandroids.view.ViewHelper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private GridView gv_img;
    private ImageAdapter adapter;
    private TextView tv_noimg;
    private ImageView iv_icon;
    private static final int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initDrawerLayout();
    }

    private void initDrawerLayout() {
        //设置可滑动范围
        //setViewDragEdgeSize();
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;

                if (drawerView.getTag().equals("LEFT")) {

                    float leftScale = 1 - 0.3f * scale;

                    //ViewHelper.setScaleX(mMenu, leftScale);
                    //ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);

                    ObjectAnimator.ofFloat(mMenu, "scaleX", leftScale);
                    ObjectAnimator.ofFloat(mMenu, "scaleY", leftScale);
                    /*ObjectAnimator.ofFloat(mMenu, "alpha", 0.6f + 0.4f * (1 - scale));
                    ObjectAnimator.ofFloat(mContent, "translationX", mMenu.getMeasuredWidth() * (1 - scale));
                    ObjectAnimator.ofFloat(mContent, "pivotX", 0);
                    ObjectAnimator.ofFloat(mContent, "pivotY", mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ObjectAnimator.ofFloat(mContent, "scaleX", rightScale);
                    ObjectAnimator.ofFloat(mContent, "scaleY", rightScale);*/

                } else {
                    ViewHelper.setTranslationX(mContent, -mMenu.getMeasuredWidth() * slideOffset);
                    ViewHelper.setPivotX(mContent, mContent.getMeasuredWidth());
                    ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);

                    /*ObjectAnimator.ofFloat(mContent, "translationX", -mMenu.getMeasuredWidth() * slideOffset);
                    ObjectAnimator.ofFloat(mContent, "pivotX", mContent.getMeasuredWidth());
                    ObjectAnimator.ofFloat(mContent, "pivotY", mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ObjectAnimator.ofFloat(mContent, "scaleX", rightScale);
                    ObjectAnimator.ofFloat(mContent, "scaleY", rightScale);*/
                }

            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
            }
        });
    }

    private void setViewDragEdgeSize() {
        Field mLeftDragger = null;
        try {
            mLeftDragger = mDrawerLayout.getClass().getDeclaredField("mLeftDragger");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        mLeftDragger.setAccessible(true);
        ViewDragHelper draggerObj = null;
        try {
            draggerObj = (ViewDragHelper) mLeftDragger.get(mDrawerLayout);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Field mEdgeSize = null;
        try {
            mEdgeSize = draggerObj.getClass().getDeclaredField("mEdgeSize");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        mEdgeSize.setAccessible(true);

        try {
            mEdgeSize.setInt(draggerObj, (int) (AppUtils.getScreenWidth() * 0.5));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        View view = findViewById(R.id.id_left_menu);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) (AppUtils.getScreenWidth() * 0.8);
        view.setLayoutParams(layoutParams);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerLayout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
        mDrawerLayout.setScrimColor(0x00ffffff);

        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        gv_img = (GridView) findViewById(R.id.gv_img);
        tv_noimg = (TextView) findViewById(R.id.iv_noimg);
        gv_img.setFastScrollEnabled(true);
        adapter = new ImageAdapter(this);
        gv_img.setAdapter(adapter);
        gv_img.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        iv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (!mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                    mDrawerLayout.openDrawer(Gravity.LEFT);
                }

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                //关闭侧滑页面
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            int hasReadStoragePermission = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasWriteStoragePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            List<String> permissions = new ArrayList<>();
            if (hasReadStoragePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
            }
        }

        loadImage();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_SOME_FEATURES_PERMISSIONS: {
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        AppUtils.toast("Permission Granted: " + permissions[i]);
                    } else if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        AppUtils.toast("Permission Denied: " + permissions[i]);
                    }
                }
            }
            break;
            default: {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        }
    }

    /**
     * 加载图片
     */
    private void loadImage() {
        new Invoker(new Callback() {
            @Override
            public boolean onRun() {
                adapter.addAll(AppUtils.getGalleryPhotos(HomeActivity.this));
                return adapter.isEmpty();
            }

            @Override
            public void onBefore() {
                // 转菊花
            }

            @Override
            public void onAfter(boolean b) {
                adapter.notifyDataSetChanged();
                if (b) {
                    tv_noimg.setVisibility(View.VISIBLE);
                } else {
                    tv_noimg.setVisibility(View.GONE);
                    String s = "file://" + adapter.getItem(0);
                    ImageLoadUtil.display(HomeActivity.this, s, iv_icon);
                }
                shake();
            }
        }).start();

    }

    private void shake() {
        iv_icon.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake));
    }
}
