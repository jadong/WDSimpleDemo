package com.dong.activity;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dong.R;
import com.dong.adapter.ItemAdapter;
import com.dong.adapter.ItemBean;
import com.dong.base.BaseActivity;
import com.dong.base.SwipeBackLayout;
import com.dong.view.LoadMoreRecyclerView;
import com.dong.view.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by zengwendong on 16/7/19.
 */
public class RefreshActivity extends BaseActivity {

    private SuperSwipeRefreshLayout swipeRefreshLayout;
    private LoadMoreRecyclerView loadMoreRecyclerView;
    private LinearLayout ll_header;
    private ImageView iv_image;

    private GridLayoutManager gridLayoutManager;
    private LinearLayoutManager linearLayoutManager;
    private int mScrollY;
    private int mHeaderHeight;
    private int mHeaderTranslation;
    private ItemAdapter itemAdapter;

    int[] imgIds = new int[]{R.drawable.girl01, R.drawable.girl02, R.drawable.girl03, R.drawable.girl04, R.drawable.girl05};
    Random random = new Random();

    @Override
    protected int getContentView() {
        return R.layout.activity_refresh;
    }

    @Override
    protected void onCreate() {
        swipeRefreshLayout = (SuperSwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        loadMoreRecyclerView = (LoadMoreRecyclerView) findViewById(R.id.loadMoreRecyclerView);
        ll_header = (LinearLayout) findViewById(R.id.ll_header);
        iv_image = (ImageView) findViewById(R.id.iv_image);

        initHeaderViewHeight();
        //头布局
        View headerView = createHeaderView();
        final TextView refresh_tip = (TextView) headerView.findViewById(R.id.tv_refresh_text);
        final ProgressBar progressBar_refresh = (ProgressBar) headerView.findViewById(R.id.progressBar_refresh);
        progressBar_refresh.setVisibility(View.GONE);

        itemAdapter = new ItemAdapter(this);
        gridLayoutManager = new GridLayoutManager(this, 2);
        linearLayoutManager = new LinearLayoutManager(this);
        loadMoreRecyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type = itemAdapter.getItemViewType(position);
                switch (type) {
                    case ItemAdapter.TYPE_PLACEHOLDER:
                        return 2;
                    case ItemAdapter.TYPE_FOOTER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });


        loadMoreRecyclerView.setAdapter(itemAdapter);
        List<ItemBean> dataList = getData();
        itemAdapter.setData(dataList);

        setListener(headerView, refresh_tip, progressBar_refresh);

    }

    private void setListener(View headerView, final TextView refresh_tip, final ProgressBar progressBar_refresh) {
        loadMoreRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mScrollY += dy;
                scrollHeader(mScrollY);
                // scrollHeaderWithMove(dy);
            }
        });

        loadMoreRecyclerView.setLoadMoreListener(new LoadMoreRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        List<ItemBean> dataList = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            ItemBean itemBean = new ItemBean();
                            itemBean.title = "灬灬灬灬灬灬灬灬---" + i;
                            itemBean.imgId = imgIds[random.nextInt(5)];
                            dataList.add(itemBean);
                        }
                        itemAdapter.addData(dataList);
                        loadMoreRecyclerView.notifyMoreFinish(true);
                    }
                }, 1000);
            }
        });

        swipeRefreshLayout.setHeaderView(headerView);//添加下拉刷新头部view
        swipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

            @Override
            public void onRefresh() {//开始刷新
                refresh_tip.setText("正在刷新");
                progressBar_refresh.setVisibility(View.VISIBLE);
                System.out.println("RefreshActivity --> " + "onPullToRefresh 下拉刷新");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        List<ItemBean> dataList = getData();
                        itemAdapter.setData(dataList);
                    }
                }, 1000);
            }

            @Override
            public void onPullDistance(int distance) {//下拉距离
                System.out.println("RefreshActivity --> " + "onPullDistance 下拉刷新距离" + distance);

            }

            @Override
            public void onPullEnable(boolean enable) {//下拉过程中，下拉的距离是否足够出发刷新
                System.out.println("RefreshActivity --> " + "onPullEnable 下拉刷新距离是否足够 " + enable);
                refresh_tip.setText(enable ? "松开刷新" : "下拉刷新");

            }
        });
    }

    public View createHeaderView() {
        return getLayoutInflater().inflate(R.layout.layout_refresh_header_view, null);
    }


    /**
     * 按键响应，在WebView中查看网页时，按返回键的时候按浏览历史退回,如果不做此项处理则整个WebView返回退出
     */
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            // 返回键退回
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }*/
    @NonNull
    private List<ItemBean> getData() {
        List<ItemBean> dataList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            ItemBean itemBean = new ItemBean();
            itemBean.title = "灬灬灬灬灬灬灬灬---" + i;
            itemBean.imgId = imgIds[random.nextInt(5)];
            dataList.add(itemBean);
        }
        return dataList;

    }

    private void scrollHeader(int scrollY) {
        float translationY = Math.max(-scrollY, mHeaderTranslation);
        ll_header.setTranslationY(translationY);
        iv_image.setTranslationY(-translationY / 3);
        if (translationY == 0) {
            swipeRefreshLayout.isTop(true);
        } else {
            swipeRefreshLayout.isTop(false);
        }
    }

    int fixY = 0;

    /**
     * 实现任何位置滑动 更新 头布局位置
     *
     * @param dy
     */
    private void scrollHeaderWithMove(int dy) {
        fixY += dy;
        if (fixY > -mHeaderTranslation) {
            fixY = -mHeaderTranslation;
        } else if (fixY < 0) {
            fixY = 0;
        }
        ll_header.setTranslationY(-fixY);
        iv_image.setTranslationY(fixY / 3);

        System.out.println("RefreshActivity --> " + "fixY = " + fixY);
        System.out.println("RefreshActivity --> " + "dy = " + dy);

    }

    private void initHeaderViewHeight() {
        mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.header_height);
        int tabHeight = getResources().getDimensionPixelSize(R.dimen.tab_height);
        mHeaderTranslation = -mHeaderHeight + tabHeight;
    }
}
