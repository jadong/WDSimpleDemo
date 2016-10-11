package com.dong.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dong.R;
import com.dong.activity.DetailActivity;
import com.dong.activity.FirstActivity;
import com.dong.activity.MetroActivity;
import com.dong.activity.RefreshActivity;
import com.dong.activity.TerminalActivity;
import com.dong.activity.TestActivity;
import com.dong.adapter.ActivityNameAdapter;
import com.dong.entity.ActivityName;
import com.dong.util.AppUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengwendong on 16/8/15.
 */
public class MainLeftFragment extends Fragment {

    private ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_left, container, false);

        lv = (ListView) view.findViewById(R.id.lv);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                AppUtils.toast("click " + position);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<ActivityName> list = new ArrayList<>();
        list.add(new ActivityName("FirstActivity", FirstActivity.class));
        list.add(new ActivityName("DetailActivity", DetailActivity.class));
        list.add(new ActivityName("RefreshActivity", RefreshActivity.class));
        list.add(new ActivityName("TestActivity", TestActivity.class));
        list.add(new ActivityName("TerminalActivity", TerminalActivity.class));
        list.add(new ActivityName("MetroActivity", MetroActivity.class));

        lv.setAdapter(new ActivityNameAdapter(getActivity(),list));
    }
}
