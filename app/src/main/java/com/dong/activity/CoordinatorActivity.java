package com.dong.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dong.R;
import com.dong.adapter.ExampleFragmentAdapter;
import com.dong.adapter.ScaleTransitionPagerTitleView;
import com.dong.base.BaseActivity;
import com.yalantis.phoenix.PullToRefreshView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * layout_scrollFlags 設置:
 * scroll - 想滚动就必须设置这个。
 * enterAlways - 实现quick return效果, 当向下移动时，立即显示View（比如Toolbar)。
 * exitUntilCollapsed - 向上滚动时收缩View，但可以固定Toolbar一直在上面。
 * enterAlwaysCollapsed - 当你的View已经设置minHeight属性又使用此标志时，你的View只能以最小高度进入，只有当滚动视图到达顶部时才扩大到完整高度
 * <p>
 * Created by zengwendong on 16/12/13.
 */
public class CoordinatorActivity extends BaseActivity {

    private MagicIndicator magic_indicator;
    private ViewPager viewPager;
    private Toolbar toolbar;

    private CommonNavigator commonNavigator;

    private static final String[] CHANNELS = new String[]{"不知火舞", "狄仁杰", "夏候惇", "橘右京"};
    private List<String> dataList = new ArrayList<>(Arrays.asList(CHANNELS));
    private ExampleFragmentAdapter exampleFragmentAdapter;
    private AppBarLayout appBarLayout;

    //private SuperSwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private TextView textView;

    private PullToRefreshView pull_to_refresh;

    @Override
    protected int getContentView() {
        return R.layout.activity_coordinator;
    }

    @Override
    protected void onCreate() {

        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        magic_indicator = (MagicIndicator) findViewById(R.id.magic_indicator);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        exampleFragmentAdapter = new ExampleFragmentAdapter(getSupportFragmentManager(), dataList);
        viewPager.setAdapter(exampleFragmentAdapter);
        viewPager.setOffscreenPageLimit(dataList.size());

        pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        /*swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.isTop(true);*/

        initMagicIndicatorSetting2();

        setListener();

    }

    private void setListener() {

        pull_to_refresh = (PullToRefreshView) findViewById(R.id.pull_to_refresh);
        pull_to_refresh.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pull_to_refresh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pull_to_refresh.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        /*swipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        swipeRefreshLayout.setTargetScrollWithLayout(true);
        swipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                progressBar.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                }, 2000);
            }

            @Override
            public void onPullDistance(int distance) {
                Log.i("CoordinatorActivity", "onPullDistance----" + distance);
                float alpha = distance / AppUtils.dip2px(50);
                alpha = alpha > 1 ? 1 : alpha < 0 ? 0 : alpha;
                Log.i("CoordinatorActivity", "onPullDistance---alpha---" + alpha);
                if (distance > 0) {
                    toolbar.setAlpha(1 - alpha);
                    //ObjectAnimator.ofFloat(toolbar,"alpha",0).setDuration(100).start();
                } else {
                    toolbar.setAlpha(1 - alpha);
                    //ObjectAnimator.ofFloat(toolbar,"alpha",1).setDuration(100).start();
                }
            }

            @Override
            public void onPullEnable(boolean enable) {
                textView.setText(enable ? "松开刷新" : "下拉刷新");
            }
        });*/

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                boolean enabled;
                if (verticalOffset >= 0) {
                    enabled = true;
                } else {
                    enabled = false;
                }
                pull_to_refresh.setEnabled(enabled);

                /*List<Fragment> fragmentList = exampleFragmentAdapter.getFragmentList();
                for (int i = 0; i < fragmentList.size(); i++) {
                    Fragment fragment = fragmentList.get(i);
                    if (fragment instanceof FirstFragment) {
                        FirstFragment firstFragment = (FirstFragment) fragment;
                        firstFragment.setRefreshStatus(isRefresh);
                    }
                }*/

            }
        });
    }

    /*private View createHeaderView() {
        View headerView = LayoutInflater.from(swipeRefreshLayout.getContext())
                .inflate(R.layout.layout_refresh_head_view, null);
        progressBar = (ProgressBar) headerView.findViewById(R.id.progressBar);
        textView = (TextView) headerView.findViewById(R.id.tv_text);
        textView.setText("下拉刷新");
        progressBar.setVisibility(View.GONE);
        return headerView;
    }*/

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            //progressBarPopup.show(this);
        }
    }


    private void initMagicIndicatorSetting1() {
        commonNavigator = new CommonNavigator(this);
        commonNavigator.setSkimOver(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return dataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(dataList.get(index));
                clipPagerTitleView.setTextColor(Color.parseColor("#f2c4c4"));
                clipPagerTitleView.setClipColor(Color.WHITE);
                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return clipPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;
            }
        });
        magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic_indicator, viewPager);
    }

    private void initMagicIndicatorSetting2() {
        magic_indicator.setBackgroundColor(Color.WHITE);
        commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return dataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ScaleTransitionPagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(dataList.get(index));
                simplePagerTitleView.setMinScale(0.8f);
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.parseColor("#616161"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#f57c00"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setRoundRadius(5);
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(1.6f));
                indicator.setLineHeight(UIUtil.dip2px(context, 2));
                indicator.setColors(Color.parseColor("#f57c00"));
                return indicator;
            }

            @Override
            public float getTitleWeight(Context context, int index) {
                if (index == 0) {
                    return 1.0f;
                } else if (index == 1) {
                    return 1.0f;
                } else {
                    return 1.0f;
                }
            }
        });
        magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic_indicator, viewPager);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (toolbar != null) {
            toolbar.setNavigationIcon(R.mipmap.icon_back);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            toolbar.setTitle("代表法律制裁你");
        }

    }
}
