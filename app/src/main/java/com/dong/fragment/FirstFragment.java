package com.dong.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dong.R;
import com.dong.adapter.TestAdapter;
import com.dong.progressbar.ProgressBarPopup;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zengwendong on 16/12/15.
 */
public class FirstFragment extends Fragment {

    private RecyclerView recyclerView;
    private TestAdapter testAdapter;

    private ProgressBarPopup progressBarPopup;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBarPopup = new ProgressBarPopup(getActivity());


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        testAdapter = new TestAdapter(getContext());

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(UUID.randomUUID().toString());
        }
        testAdapter.setData(list);
        recyclerView.setAdapter(testAdapter);
    }

}
