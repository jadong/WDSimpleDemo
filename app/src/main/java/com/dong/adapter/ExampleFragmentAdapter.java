package com.dong.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dong.fragment.FirstFragment;
import com.dong.fragment.WebViewFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengwendong on 16/12/13.
 */
public class ExampleFragmentAdapter extends FragmentPagerAdapter {

    private List<String> dataList = new ArrayList<>();

    private List<Fragment> fragmentList = new ArrayList<>();

    public ExampleFragmentAdapter(FragmentManager fm, List<String> dataList) {
        super(fm);
        if (dataList != null) {
            this.dataList.addAll(dataList);
        }
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == dataList.size() - 1) {
            fragment = new WebViewFragment();

        } else {
            fragment = new FirstFragment();
        }
        fragmentList.add(fragment);
        return fragment;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }
}
